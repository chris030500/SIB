package ahorrosbienestar.sib.std.solicitudes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.repository.ISolicitudEnvio;
@Service
public class SolicitudEnvioService {
	
	private ISolicitudEnvio iSolicitudEnvio;
	
	@Autowired
	public SolicitudEnvioService(ISolicitudEnvio iSolicitudEnvio) {
		this.iSolicitudEnvio = iSolicitudEnvio;
	}
	
	//Metodo para guardar la solicitud de envio
	public SolicitudEnvio save(SolicitudEnvio solicitudEnvio) {
		return iSolicitudEnvio.save(solicitudEnvio);
	}
	
	//Metodo para guardar la solicitud de envio
	public List<SolicitudEnvio> findAll() {
		return iSolicitudEnvio.findAll();
	}


	public List<SolicitudEnvio> findBySolicitud(Integer solicitud) {
		// TODO Auto-generated method stub
		return iSolicitudEnvio.findBySolicitud(solicitud);
	}

	public List<SolicitudEnvio> findByRangoFechas(String fechaInicio, String fechaFin) {
		// TODO Auto-generated method stub
		return iSolicitudEnvio.findByRangoFechas(fechaInicio,fechaFin);
	}
}
