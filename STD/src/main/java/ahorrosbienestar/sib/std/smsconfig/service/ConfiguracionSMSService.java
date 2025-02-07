package ahorrosbienestar.sib.std.smsconfig.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.smsconfig.model.ConfiguracionSMS;
import ahorrosbienestar.sib.std.smsconfig.repository.IConfiguracionSMSRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Service
public class ConfiguracionSMSService {
	
	private static final Logger logger = Logger.getLogger(ConfiguracionSMSService.class);
	
	@Autowired
	private IConfiguracionSMSRepository configSMSRepository;
	
    @Autowired
    private UsuarioService userService;
	
	public Optional<ConfiguracionSMS> getCurrentSMSConfig(Integer idSMSConfig) {
		return configSMSRepository.getCurrentSMSConfig(idSMSConfig);
	}
	
	public List<Map<String, Object>> getSMSConfigByStatusSolicitud(String statusSolicitud) {
		return configSMSRepository.getSMSConfigByStatusSolicitud(statusSolicitud);
	}
	
	public ConfiguracionSMS updateCurrentSMSText(Integer idSMSConfig, Integer idUsuario, String texto, Integer status) {
		try {
			ConfiguracionSMS configuracionSMS = configSMSRepository.getCurrentSMSConfig(idSMSConfig).orElse(null);
			
			if (configuracionSMS != null) {
				LocalDateTime currentDateTime = LocalDateTime.now();
				configuracionSMS.setMensaje(texto);
				configuracionSMS.setActivo(status);
				configuracionSMS.setFechaModificacion(currentDateTime);
				configuracionSMS.setUserModifica(idUsuario);
				
				return configSMSRepository.save(configuracionSMS);
			}
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
		}
		
		return null;
	}
	
    public ResponseEntity<ConfiguracionSMS> updateSMSText(Map<String, Object> payload) {
        try {
            Integer idSMSConfig = (Integer) payload.get("idSMSConfig");
            String texto = (String) payload.get("texto");
            Integer estatus = (Integer) payload.get("estatus");
            
	        Optional<Usuario> usuario = userService.obtenerUsuarioAutenticado();
	        if (usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
			
			Integer idUsuario = usuario.get().getPk_id_usuario();
            ConfiguracionSMS updatedConfig = updateCurrentSMSText(idSMSConfig, idUsuario, texto, estatus);
            
            if (updatedConfig == null) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
            return ResponseEntity.ok(updatedConfig); 
        } catch (Exception e) {
        	logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	public ResponseEntity<Map<String, Object>> getAllSMSConfig(String statusSolicitud) {
		
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		
		try {
			List<Map<String, Object>> plantillasSMS = getSMSConfigByStatusSolicitud(statusSolicitud);
			respuesta.put("plantillas", (plantillasSMS != null && !plantillasSMS.isEmpty()) ? plantillasSMS : Collections.emptyList());
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_LOGGER_ERROR + e.getMessage();
			respuesta.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
		
	}
}
