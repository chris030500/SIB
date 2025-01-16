package ahorrosbienestar.sib.std.marketing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.marketing.model.CredencialesCalixta;
import ahorrosbienestar.sib.std.marketing.repository.ICredencialesCalixtaRepository;

@Service
public class CredencialesCalixtaService {
	
	@Autowired
	private ICredencialesCalixtaRepository credencialesRepository;
	
	public List<CredencialesCalixta> getCredenciales() {
		return credencialesRepository.findAll();
	}
	
	public int searchConfiguracion(String nombreConfig, String username) {
		return credencialesRepository.searchConfiguracion(nombreConfig, username);
	}
	
	public List<CredencialesCalixta> getDatosCalixta() {
		return credencialesRepository.getDatosCalixta();
	}
	
	public void saveCredencialesCalixta(List<CredencialesCalixta> credenciales) {
		credencialesRepository.saveAll(credenciales);
	}

}
