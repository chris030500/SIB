package ahorrosbienestar.sib.std.smsconfig.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.smsconfig.model.ConfiguracionSMSMensual;
import ahorrosbienestar.sib.std.smsconfig.repository.IConfiguracionSMSMensualRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Service
public class ConfiguracionSMSMensualService {
	
	private static final Logger logger =
	        LoggerFactory.getLogger(ConfiguracionSMSMensualService.class);
	
	@Autowired
	private IConfiguracionSMSMensualRepository confMensualRepository;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private GestionSMSService gestionService;
	
	public ConfiguracionSMSMensual updatedConfigMensual(Long idConfigMensual, Integer limiteSMS, Integer maximoSMS ,Integer idUser) {
		try {
			
			ConfiguracionSMSMensual configMensual = confMensualRepository.findById(idConfigMensual).orElse(null);
			
			if (configMensual != null && limiteSMS == 1) {
				configMensual.setLimiteSMS(limiteSMS);
				configMensual.setNumSMS(maximoSMS);
				configMensual.setUserModifica(idUser);
				
				return confMensualRepository.save(configMensual);
			}
			
			if (configMensual != null && limiteSMS == 0) {
				configMensual.setLimiteSMS(limiteSMS);
				configMensual.setNumSMS(0);
				configMensual.setUserModifica(idUser);
				
				gestionService.updateSMSDisponibles(idConfigMensual);
				
				return confMensualRepository.save(configMensual);
			}
			
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
		}
		
		return null;
	}
	
    public ResponseEntity<ConfiguracionSMSMensual> updateConfigMensual(Map<String, Object> payload) {
        try {
            Long idConfigMensual = ((Number) payload.get("idSMSConfigMensual")).longValue();
            Integer limiteSMS  = (Integer) payload.get("limiteSMS");
            Integer maxSMS = (Integer) payload.get("maxSMS");
            
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
            ConfiguracionSMSMensual updatedConfigMensual = updatedConfigMensual(idConfigMensual, limiteSMS, maxSMS, idUsuario);
            
            if (updatedConfigMensual == null) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
            gestionService.updateProcesoByConfigMensual(idConfigMensual);
            return ResponseEntity.ok(updatedConfigMensual);
        } catch (Exception e) {
        	logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
