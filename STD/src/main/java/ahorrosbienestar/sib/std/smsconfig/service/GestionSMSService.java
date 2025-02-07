package ahorrosbienestar.sib.std.smsconfig.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.smsconfig.model.GestionSMS;
import ahorrosbienestar.sib.std.smsconfig.repository.IGestionSMSRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Service
public class GestionSMSService {
	
	private static final Logger logger = Logger.getLogger(GestionSMSService.class);
	
	@Autowired
	private IGestionSMSRepository gestionRepository;
	
    @Autowired
    private UsuarioService userService;
    
	@Autowired
	private DetalleGestionSMSService detalleService;
	
	public List<Map<String, Object>> getGestionesSMSByTipoCredito(Integer tipoProceso, String tipoCredito) {
		gestionRepository.setLocaleSpanish(); // Configurar el idioma
		return gestionRepository.getGestionesSMSByTipoCredito(tipoProceso, tipoCredito);
	}
	
	public ResponseEntity<Map<String, Object>> getAllGestionesSMS(Integer tipoProceso, String tipoCredito) {
		
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		
		try {
			List<Map<String, Object>> gestionesSMS = getGestionesSMSByTipoCredito(tipoProceso, tipoCredito);
			
			respuesta.put("gestiones", (gestionesSMS != null && !gestionesSMS.isEmpty()) ? gestionesSMS : Collections.emptyList());
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_LOGGER_ERROR + e.getMessage();
			respuesta.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
		
	}
	
	public Optional<Map<String, Object>> getCurrentProcessSMS(Integer tipoProceso, Integer idProcess) {
		gestionRepository.setLocaleSpanish();
		return gestionRepository.getCurrentProcessSMS(tipoProceso, idProcess);
	}
	
	public GestionSMS updatedProcessStatus(Integer idProcess, Integer status, Integer idUser) {
		try {
			
			GestionSMS procesoSMS = gestionRepository.findById(idProcess)
					.orElseThrow(() -> new EntityNotFoundException("GestiónSMS no encontrado con id " + idProcess));
			
			if (procesoSMS != null) {
				if (status == 4) {
					LocalDateTime currentDateTime = LocalDateTime.now();
					procesoSMS.setEstatusProceso(status);
					procesoSMS.setFechaCancelacion(currentDateTime);
					procesoSMS.setUserCancela(idUser);
					return gestionRepository.save(procesoSMS);
					
				} else {
					procesoSMS.setEstatusProceso(status);
					return gestionRepository.save(procesoSMS);
				}
			} 
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
		}
		
		return null;
	}
	
    public ResponseEntity<GestionSMS> updateEstatusProceso(Map<String, Object> payload) {
        try {
        	
            Integer idSMSProceso = (Integer) payload.get("idSMSProceso");
            Integer estatus = (Integer) payload.get("estatus");
            
	        Optional<Usuario> usuario = userService.obtenerUsuarioAutenticado();
	        if (usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
			
			Integer idUsuario = usuario.get().getPk_id_usuario();
            GestionSMS updatedGestionSMS = updatedProcessStatus(idSMSProceso, estatus, idUsuario);
            
            if (updatedGestionSMS == null) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
        	updateDetalleGestionSMS(idSMSProceso, estatus);
            return ResponseEntity.ok(updatedGestionSMS);
            
        } catch (Exception e) {
        	logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    private void updateDetalleGestionSMS(Integer idProceeso, Integer estatus) {
    	if (estatus == 4) {
    		detalleService.updateEnviarMensaje(idProceeso, "N");
    	} else {
    		detalleService.updateEnviarMensaje(idProceeso, "S");
    	}
    }

}
