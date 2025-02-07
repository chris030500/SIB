package ahorrosbienestar.sib.std.marketing.creditosdesembolsados.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.marketing.creditosdesembolsados.repository.IConfigCreditosDesembolsadosRepository;

@Service
public class ConfigCreditosDesembolsadosService {
	
	@Autowired
	private IConfigCreditosDesembolsadosRepository configRepository;
	
	public int searchConfiguracion(String nombreConfig, String usuario) {
		return configRepository.searchConfiguracion(nombreConfig, usuario);
	}
}
