package ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.model.ConfiguracionCalixta;
import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.repository.IConfiguracionCalixtaRepository;

@Service
public class ConfiguracionCalixtaService {
	
	@Autowired
	private IConfiguracionCalixtaRepository calixtaRepository;
	
	public List<ConfiguracionCalixta> getCredenciales() {
		return calixtaRepository.findAll();
	}
	
	public int searchConfiguracion(String nombreConfig, String username) {
		return calixtaRepository.searchConfiguracion(nombreConfig, username);
	}
	
	public List<ConfiguracionCalixta> getDatosCalixta() {
		return calixtaRepository.getDatosCalixta();
	}
	
	public void saveCredencialesCalixta(List<ConfiguracionCalixta> credenciales) {
		calixtaRepository.saveAll(credenciales);
	}

}
