package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model.ProcesoGestion;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.repository.IProcesoGestionRepository;

@Service
public class ProcesoGestionService {

	private IProcesoGestionRepository iProcesoGestionRepository;
	
	@Autowired
	public ProcesoGestionService(IProcesoGestionRepository iProcesoGestionRepository) {
		this.iProcesoGestionRepository = iProcesoGestionRepository;
	}

	public List<ProcesoGestion> findAll() {
		return iProcesoGestionRepository.findAll();
	}

	public Optional<ProcesoGestion> findById(Integer id) {
		return iProcesoGestionRepository.findById(id);
	}

	public int actParams(Integer procesoGestionID, String dComienzoProceso, String dFinalProceso,
			String hComienzoProceso, String hFinalProceso, String mPeriodoEjecucion) {
	
		return iProcesoGestionRepository.actParams(procesoGestionID, dComienzoProceso, dFinalProceso,
				hComienzoProceso, hFinalProceso, mPeriodoEjecucion);
	}

	

}
