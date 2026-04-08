package ahorrosbienestar.sib.std.credito.campaniastemporales.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.campaniastemporales.model.CampaniasTemporalesModel;
import ahorrosbienestar.sib.std.credito.campaniastemporales.repository.CampaniasTemporalesRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CampaniasTemporalesService {
	
	@Autowired
	private CampaniasTemporalesRepository campaniasTemporalesRepository;
	
	public CampaniasTemporalesModel save(CampaniasTemporalesModel model) {
		return campaniasTemporalesRepository.save(model);
	}
	
}
