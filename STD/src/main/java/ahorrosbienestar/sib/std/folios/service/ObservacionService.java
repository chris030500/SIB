package ahorrosbienestar.sib.std.folios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.folios.model.Observaciones;
import ahorrosbienestar.sib.std.folios.repository.IObservacionRepository;

@Service
public class ObservacionService {
	
	private IObservacionRepository iObservacionRepository;
	
	@Autowired
	public ObservacionService(IObservacionRepository iObservacionRepository) {
		this.iObservacionRepository = iObservacionRepository;
	}
	
	public Observaciones save(Observaciones altaObservacion) {
		return iObservacionRepository.save(altaObservacion);
	}

	public int modObservacion(Integer idObservacion, Integer idAreaAtencion, Integer idResponsableAtencion,
			String idFechaVencimiento, String desObservacion) {
		
		return iObservacionRepository.modObservacion(idObservacion,idAreaAtencion,idResponsableAtencion,idFechaVencimiento,desObservacion);
	}
	
}
