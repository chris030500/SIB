package ahorrosbienestar.sib.std.credito.tiposCampanias.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.credito.tiposCampanias.model.ExclisionesClientesModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.repository.ExclusionClienteRepository;

@Service
public class ExclusionClienteService {
	
	 @Autowired
	    private ExclusionClienteRepository repository;

		public void guardarExclusion(ExclisionesClientesModel cliente) {
	        repository.save(cliente);
	    }
		
		@Transactional
	    public boolean actualizarExcCliente(ExclisionesClientesModel cliente) {
	        int filasAfectadas = repository.actualizarExcCliente(
	        		cliente.getIdCliente(),
	        		cliente.getEstatusCliente()
	            
	        );
	        return filasAfectadas > 0;
	    }
}
