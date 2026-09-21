package ahorrosbienestar.sib.std.smsconfig.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.smsconfig.model.GestionSMS;
import ahorrosbienestar.sib.std.smsconfig.repository.IGestionSMSRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Service
public class GestionSMSService {
	
	private static final Logger logger =
	        LoggerFactory.getLogger(GestionSMSService.class);
	
	@Autowired
	private IGestionSMSRepository gestionRepository;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	public List<Map<String, Object>> getGestionesSMSByTipoCredito(Integer tipoProceso, Integer isCampania, String tipoCredito) {
		gestionRepository.setLocaleSpanish(); // Configurar el idioma
		return gestionRepository.getGestionesSMSByTipoCredito(tipoProceso, isCampania, tipoCredito);
	}
	
	public ResponseEntity<Map<String, Object>> getAllGestionesSMS(Integer tipoProceso, Integer isCampania, String tipoCredito) {
		
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		
		try {
			List<Map<String, Object>> gestionesSMS = getGestionesSMSByTipoCredito(tipoProceso, isCampania, tipoCredito);
			
			respuesta.put("gestiones", (gestionesSMS != null && !gestionesSMS.isEmpty()) ? gestionesSMS : Collections.emptyList());
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_LOGGER_ERROR + e.getMessage();
			respuesta.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
		
	}
	
	public Optional<Map<String, Object>> getCurrentProcessSMS(Integer tipoProceso, Long idProcess) {
		gestionRepository.setLocaleSpanish();
		return gestionRepository.getCurrentProcessSMS(tipoProceso, idProcess);
	}
	
	public void updateProcesoByConfigMensual(Long idConfigSMSMensual) {
		try {
			logger.info("Obteniendo Gestión SMS mediante el id de configuración mensual.");
			gestionRepository.updateProcesoSMSByidConfigMensual(idConfigSMSMensual);
			
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
		}
	}
	
	public void updateSMSDisponibles(Long idConfigSMSMensual) {
		try {
			logger.info("Obteniendo Gestión SMS mediante el id de configuración mensual.");
			gestionRepository.updateEnviosDisponibles(idConfigSMSMensual);
			
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
		}
	}
	
	public GestionSMS updatedProcessStatus(Long idProcess, Integer status, Integer idUser) {
		try {
			
			GestionSMS procesoSMS = gestionRepository.findById(idProcess)
					.orElseThrow(() -> new EntityNotFoundException("GestiónSMS no encontrado con id " + idProcess));
			
			if (procesoSMS != null) {
				logger.info("Obteniendo gestión SMS a modificar: " + procesoSMS.toString());
				if (status == 4) {
					logger.info("Cambio de estatus a: " + status + "(Cancelado)");
					LocalDateTime currentDateTime = LocalDateTime.now();
					procesoSMS.setEstatusProceso(status);
					procesoSMS.setFechaCancelacion(currentDateTime);
					procesoSMS.setUserCancela(idUser);
					logger.info("Gestión SMS actualizada: " + procesoSMS.toString());
					
					return gestionRepository.save(procesoSMS);
					
				} else {
					logger.info("Cambio de estatus a: " + status + "(Reiniciado)");
					procesoSMS.setEstatusProceso(status);
					logger.info("Gestión SMS actualizada: " + procesoSMS.toString());
					
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
        	
            Long idSMSProceso = ((Number) payload.get("idSMSProceso")).longValue();
            Integer estatus = (Integer) payload.get("estatus");
            
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (!(principal instanceof UserDetails)) {
				logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
			}
			
			UserDetails userDetails = (UserDetails) principal;
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
	        if (usuario.get(0) == null) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	        }
			
			Integer idUsuario = usuario.get(0).getPk_id_usuario();
            GestionSMS updatedGestionSMS = updatedProcessStatus(idSMSProceso, estatus, idUsuario);
            
            logger.info("La gestión del SMS modificado: " + updatedGestionSMS.toString());
            
            return ResponseEntity.ok(updatedGestionSMS);
            
        } catch (Exception e) {
        	logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
