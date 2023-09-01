package ahorrosbienestar.sib.std.prevencion_pld.relacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.prevencion_pld.relacion.model.RelacionPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.relacion.repository.RelacionPldRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RelacionPldService {
	
	@Autowired
	private RelacionPldRepository relacionPldRepository;
	
	public RelacionPldModel findById(Integer id) {
		return relacionPldRepository.findById(id).orElse(null);
	}
	
	public RelacionPldModel save(RelacionPldModel model) {
		return relacionPldRepository.save(model);
	}

}
