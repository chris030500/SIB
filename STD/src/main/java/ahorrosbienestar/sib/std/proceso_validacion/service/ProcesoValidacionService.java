package ahorrosbienestar.sib.std.proceso_validacion.service;

import org.springframework.beans.factory.annotation.Autowired;

import ahorrosbienestar.sib.std.proceso_validacion.repository.IProcesoValidacionRepository;

public class ProcesoValidacionService {
	
	private IProcesoValidacionRepository iProcesoValidacionRepository;
	
	
	@Autowired
	public ProcesoValidacionService(IProcesoValidacionRepository iProcesoValidacionRepository) {
		this.iProcesoValidacionRepository = iProcesoValidacionRepository;
	}


}
