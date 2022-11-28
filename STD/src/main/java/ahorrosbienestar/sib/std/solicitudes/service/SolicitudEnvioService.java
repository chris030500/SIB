package ahorrosbienestar.sib.std.solicitudes.service;

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
	 

}
