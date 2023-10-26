package ahorrosbienestar.sib.std.ofertasAutomaticas.procesoBases.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.ofertasAutomaticas.procesoBases.model.ProcesoBase;
import ahorrosbienestar.sib.std.ofertasAutomaticas.procesoBases.repository.IProcesoBaseRepository;

@Service
public class ProcesoService {
	private IProcesoBaseRepository iProcesoBaseRepository;
	
	@Autowired
	public ProcesoService(IProcesoBaseRepository iProcesoBaseRepository) {
		this.iProcesoBaseRepository = iProcesoBaseRepository;
	}
	
	public ProcesoBase save(ProcesoBase altaProceso) {
		return iProcesoBaseRepository.save(altaProceso);
	}

}
