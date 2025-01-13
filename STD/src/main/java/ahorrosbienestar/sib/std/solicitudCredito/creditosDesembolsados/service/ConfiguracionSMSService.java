package ahorrosbienestar.sib.std.solicitudCredito.creditosDesembolsados.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.solicitudCredito.creditosDesembolsados.model.ConfiguracionSMS;
import ahorrosbienestar.sib.std.solicitudCredito.creditosDesembolsados.repository.IConfiguracionSMSRepository;

@Service
public class ConfiguracionSMSService {
	
	@Autowired
	private IConfiguracionSMSRepository configSMSRepository;
	
	public Optional<ConfiguracionSMS> getCurrentSMSConfig(String tipoCredito, Integer isCampania) {
		return configSMSRepository.getCurrentSmsConfig(tipoCredito, isCampania);
	}

}
