package ahorrosbienestar.sib.std.solicitudes.hallazgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.solicitudes.hallazgo.model.HallazgoSolicitud;
import ahorrosbienestar.sib.std.solicitudes.hallazgo.repository.IHallazgoSolicitud;

@Service
public class HallazgoSolicitudService {
	
	private IHallazgoSolicitud iHallazgoSolicitud;
	
	@Autowired
	public HallazgoSolicitudService (IHallazgoSolicitud iHallazgoSolicitud) {
		this.iHallazgoSolicitud = iHallazgoSolicitud;
	}
	
	public HallazgoSolicitud save (HallazgoSolicitud hallazgoSolicitud) {
		return iHallazgoSolicitud.save(hallazgoSolicitud);
	}

}
