package ahorrosbienestar.sib.std.cobranza.detalleCarga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.cobranza.detalleCarga.model.DetalleCargaModel;
import ahorrosbienestar.sib.std.cobranza.detalleCarga.repository.DetalleCargaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DetalleCargaService {
	
	@Autowired
	private DetalleCargaRepository detalleCargaRepository;
	
	public DetalleCargaModel findById(Integer id) {
		return detalleCargaRepository.findById(id).orElse(null);
	}
	
	public DetalleCargaModel save(DetalleCargaModel model) {
		return detalleCargaRepository.save(model);
	}

}
