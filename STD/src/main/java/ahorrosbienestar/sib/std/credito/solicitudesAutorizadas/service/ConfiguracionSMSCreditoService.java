package ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.model.ConfiguracionSMSCredito;
import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.repository.IConfiguracionSMSCreditoRepository;

@Service
public class ConfiguracionSMSCreditoService {
	
	private static final Logger logger = Logger.getLogger(ConfiguracionSMSCreditoService.class);
	
	@Autowired
	private IConfiguracionSMSCreditoRepository configSMSRepository;
	
	/*public Optional<ConfiguracionSMS> getCurrentSMSConfig(String tipoCredito, Integer isCampania) {
		return configSMSRepository.getCurrentSmsConfig(tipoCredito, isCampania);
	}*/
	
	public Optional<ConfiguracionSMSCredito> getCurrentSMSConfig(Integer idSMSConfig) {
		return configSMSRepository.getCurrentSMSConfig(idSMSConfig);
	}
	
	public List<Map<String, Object>> getAllSMSConfig() {
		return configSMSRepository.getAllSMSConfig();
	}
	
	public ConfiguracionSMSCredito updateCurrentSMSText(Integer idSMSConfig, Integer idUsuario, String texto, Integer status) {
		try {
			ConfiguracionSMSCredito configuracionSMS = configSMSRepository.getCurrentSMSConfig(idSMSConfig).orElse(null);
			
			if (configuracionSMS != null) {
				LocalDateTime currentDateTime = LocalDateTime.now();
				configuracionSMS.setMensaje(texto);
				configuracionSMS.setActivo(status);
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
