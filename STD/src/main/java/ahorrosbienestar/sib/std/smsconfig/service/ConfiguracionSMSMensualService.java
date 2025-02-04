package ahorrosbienestar.sib.std.smsconfig.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.smsconfig.model.ConfiguracionSMSMensual;
import ahorrosbienestar.sib.std.smsconfig.repository.IConfiguracionSMSMensualRepository;

@Service
public class ConfiguracionSMSMensualService {
	
	private static final Logger logger = Logger.getLogger(ConfiguracionSMSMensualService.class);
	
	@Autowired
	private IConfiguracionSMSMensualRepository confMensualRepository;
	
	public ConfiguracionSMSMensual updateConfigMensual(Integer idConfigMensual, Integer limiteSMS, Integer maximoSMS ,Integer idUser) {
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
}
