package ahorrosbienestar.sib.std.prevencion_pld.formato.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.prevencion_pld.formato.model.FormatoPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.formato.repository.FormatoPldRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FormatoPldService {
	
	@Autowired
	private FormatoPldRepository formatoPldRepository;
	
	public FormatoPldModel findById(Integer id) {
		return formatoPldRepository.findById(id).orElse(null);
	}
	
	public FormatoPldModel save(FormatoPldModel model) {
		return formatoPldRepository.save(model);
	}

}
