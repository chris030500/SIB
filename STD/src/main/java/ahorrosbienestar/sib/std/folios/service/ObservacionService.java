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

	public int modObservacion(String obsOficio,Integer idObservacion, Integer idAreaAtencion, Integer idResponsableAtencion,
			String idFechaVencimiento, String desObservacion, Integer usuarioMod, Integer usuarioElimino) {
		
		return iObservacionRepository.modObservacion(idObservacion,idAreaAtencion,idResponsableAtencion,idFechaVencimiento,desObservacion,usuarioMod,usuarioElimino,obsOficio);
	}

	public int inactivarRegistro(Integer idObservacion, Integer usuarioElimina) {
		
		return iObservacionRepository.inactivarRegistro(idObservacion,usuarioElimina);
	}
	
}
