package ahorrosbienestar.sib.std.ofertasAutomaticas.historicoCampanias.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.ofertasAutomaticas.historicoCampanias.model.DetalleCampania;
import ahorrosbienestar.sib.std.ofertasAutomaticas.historicoCampanias.repository.IDetalleCampaniaRepository;

@Service
public class DetalleCampaniaService {
	private IDetalleCampaniaRepository iDetalleCampaniaRepository;

	@Autowired
	public DetalleCampaniaService(IDetalleCampaniaRepository iDetalleCampaniaRepository) {
		this.iDetalleCampaniaRepository = iDetalleCampaniaRepository;
	}

	public List<DetalleCampania> findAll() {
		return iDetalleCampaniaRepository.findAll();
	}
	
	public List<DetalleCampania> findDetalleVIP(Integer id) {
		return iDetalleCampaniaRepository.obtDetalleVIP();
	}
	
	public List<DetalleCampania> findDetallePros(Integer id) {
		return iDetalleCampaniaRepository.obtDetalleProspector();
	}
	
	public List<DetalleCampania> findDetalleFidNom(Integer id) {
		return iDetalleCampaniaRepository.obtDetalleFidNom();
	}
	
	public List<DetalleCampania> findDetalleFidRen(Integer id) {
		return iDetalleCampaniaRepository.obtDetalleFidRen();
	}
}
