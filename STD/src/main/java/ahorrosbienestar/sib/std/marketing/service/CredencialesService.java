package ahorrosbienestar.sib.std.marketing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.marketing.model.Credenciales;
import ahorrosbienestar.sib.std.marketing.repository.ICredencialesRepository;

@Service
public class CredencialesService {
	
	@Autowired
	private ICredencialesRepository credencialesRepository;
	
	public List<Credenciales> getCredenciales() {
		return credencialesRepository.findAll();
	}
	
	public int searchConfiguracion(String nombreConfig, String username) {
		return credencialesRepository.searchConfiguracion(nombreConfig, username);
	}
	
	public List<Credenciales> getDatosCalixta() {
		return credencialesRepository.getDatosCalixta();
	}
	
	public void saveCredencialesCalixta(List<Credenciales> credenciales) {
		credencialesRepository.saveAll(credenciales);
	}

}
