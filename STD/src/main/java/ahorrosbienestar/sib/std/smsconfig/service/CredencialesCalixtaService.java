package ahorrosbienestar.sib.std.smsconfig.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.smsconfig.model.CredencialesCalixta;
import ahorrosbienestar.sib.std.smsconfig.repository.ICredencialesCalixtaRepository;

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
	
	public List<CredencialesCalixta> getDatosCalixta(Integer tipoCredencial) {
		return credencialesRepository.getDatosCalixta(tipoCredencial);
	}
	
	public ResponseEntity<Map<String, String>> saveCredencialesCalixta(Map<String, String> credenciales, Integer tipoCredencial) {
		
		List<CredencialesCalixta> credencialesCalixta = credencialesRepository.getDatosCalixta(tipoCredencial);
		List<CredencialesCalixta> updatedCredenciales = new ArrayList<>();
		
		for (Map.Entry<String, String> entry : credenciales.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			CredencialesCalixta calixta = credencialesCalixta.stream()
					.filter(c -> c.getNombreCredencial().equalsIgnoreCase(key))
					.findFirst()
					.orElse(null); 
			
			if (calixta != null) {
				calixta.setValorCredencial(value);
				updatedCredenciales.add(calixta);
			}
		}
		
		credencialesRepository.saveAll(updatedCredenciales);
		
	    // Crear un objeto JSON con la respuesta
	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Credenciales guardadas con éxito");

	    return ResponseEntity.ok(response);
	}

}
