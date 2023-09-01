package ahorrosbienestar.sib.std.prevencion_pld.exclusion.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.exclusion.model.ValoresExclusionModel;
import ahorrosbienestar.sib.std.prevencion_pld.exclusion.repository.ValoresExclusionRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ValoresExclusionService {

	@Autowired
	private ValoresExclusionRepository valoresExclusionRepository;
	
	public List<ValoresExclusionModel> listadoExclusion() {
		return valoresExclusionRepository.listadoExclusion();
	}
	
}
