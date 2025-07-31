package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.ExclisionesClientesModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.repository.ExclusionClienteRepository;

@Service
public class ExclusionClienteService {
	
	 @Autowired
	    private ExclusionClienteRepository repository;

		public void guardarExclusion(ExclisionesClientesModel cliente) {
	        repository.save(cliente);
	    }
}
