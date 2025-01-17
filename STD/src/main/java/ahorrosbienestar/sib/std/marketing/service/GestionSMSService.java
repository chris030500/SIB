package ahorrosbienestar.sib.std.marketing.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.marketing.repository.IGestionSMSRepository;

@Service
public class GestionSMSService {
	
	@Autowired
	private IGestionSMSRepository gestionRepository;
	
	public List<Map<String, Object>> getAllGestionesSMS() {
		return gestionRepository.getAllGestionesSMS();
	}

}
