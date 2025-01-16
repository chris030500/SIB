package ahorrosbienestar.sib.std.marketing.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.marketing.model.ConfiguracionSMS;
import ahorrosbienestar.sib.std.marketing.repository.IConfiguracionSMSRepository;

@Service
public class ConfiguracionSMSService {
	
	private static final Logger logger = Logger.getLogger(ConfiguracionSMSService.class);
	
	@Autowired
	private IConfiguracionSMSRepository configSMSRepository;
	
	public Optional<ConfiguracionSMS> getCurrentSMSConfig(String tipoCredito, Integer isCampania) {
		return configSMSRepository.getCurrentSmsConfig(tipoCredito, isCampania);
	}
	
	public List<Map<String, Object>> getAllSMSConfig() {
		return configSMSRepository.getAllSMSConfig();
	}
	
	public ConfiguracionSMS updateCurrentSMSText(String tipoCredito, Integer isCampania, Integer idUsuario, String texto) {
		try {
			ConfiguracionSMS configuracionSMS = configSMSRepository.getCurrentSmsConfig(tipoCredito, isCampania).orElse(null);
			
			if (configuracionSMS != null) {
				LocalDateTime currentDateTime = LocalDateTime.now();
				configuracionSMS.setMensaje(texto);
				configuracionSMS.setFechaModificacion(currentDateTime);
				configuracionSMS.setUserModifica(idUsuario);
				
				return configSMSRepository.save(configuracionSMS);
			}
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
		}
		
		return null;
	}
}
