package ahorrosbienestar.sib.std.solicitudCredito.creditosDesembolsados.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.solicitudCredito.creditosDesembolsados.model.ConfiguracionCredenciales;
import ahorrosbienestar.sib.std.solicitudCredito.creditosDesembolsados.repository.IConfiguracionCredencialesRepository;

@Service
public class ConfiguracionCredencialesService {

	@Autowired
	private IConfiguracionCredencialesRepository credencialesRepository;
	
	public List<ConfiguracionCredenciales> getCredenciales() {
		return credencialesRepository.findAll();
	}
	
	public int searchConfiguracion(String nombreConfig, String username) {
		return credencialesRepository.searchConfiguracion(nombreConfig, username);
	}
}
