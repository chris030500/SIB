package ahorrosbienestar.sib.std.smsconfig.service;

import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.smsconfig.model.ConfiguracionSMSMensual;
import ahorrosbienestar.sib.std.smsconfig.repository.IConfiguracionSMSMensualRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Service
public class ConfiguracionSMSMensualService {
	
	private static final Logger logger = Logger.getLogger(ConfiguracionSMSMensualService.class);
	
	@Autowired
	private IConfiguracionSMSMensualRepository confMensualRepository;
	
    @Autowired
    private UsuarioService userService;
	
	public ConfiguracionSMSMensual updatedConfigMensual(Integer idConfigMensual, Integer limiteSMS, Integer maximoSMS ,Integer idUser) {
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
				configMensual.setUserModifica(idUser);
				
				return confMensualRepository.save(configMensual);
			}
			
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
		}
		
		return null;
	}
	
    public ResponseEntity<ConfiguracionSMSMensual> updateConfigMensual(Map<String, Object> payload) {
        try {
            Integer idSMSProceso = (Integer) payload.get("idSMSConfigMensual");
            Integer limiteSMS  = (Integer) payload.get("limiteSMS");
            Integer maxSMS = (Integer) payload.get("maxSMS");
			
	        Optional<Usuario> usuario = userService.obtenerUsuarioAutenticado();
	        if (usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	        
			Integer idUsuario = usuario.get().getPk_id_usuario();
            ConfiguracionSMSMensual updatedConfigMensual = updatedConfigMensual(idSMSProceso, limiteSMS, maxSMS, idUsuario);
            
            if (updatedConfigMensual == null) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
            return ResponseEntity.ok(updatedConfigMensual);
        } catch (Exception e) {
        	logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
