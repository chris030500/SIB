package ahorrosbienestar.sib.std.cobranza.procesoCarga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.cobranza.procesoCarga.model.ProcesoCargaModel;
import ahorrosbienestar.sib.std.cobranza.procesoCarga.repository.ProcesoCargaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProcesoCargaService {
	
	@Autowired
	private ProcesoCargaRepository procesoCargaRepository;
	
	public ProcesoCargaModel findById(Integer id) {
		return procesoCargaRepository.findById(id).orElse(null);
	}
	
	public ProcesoCargaModel save(ProcesoCargaModel model) {
		return procesoCargaRepository.save(model);
	}

}
