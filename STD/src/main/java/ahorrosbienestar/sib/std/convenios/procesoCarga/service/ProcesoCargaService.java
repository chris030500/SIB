package ahorrosbienestar.sib.std.convenios.procesoCarga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.convenios.procesoCarga.model.ProcesoCargaModel;
import ahorrosbienestar.sib.std.convenios.procesoCarga.repository.ProcesoCargaRepository;
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
