package ahorrosbienestar.sib.std.solicitudes.service;

import java.util.List;
import java.util.Optional;

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
	
	public List<SolicitudEnvio> estatusSolicAutorizacion(){
		return iSolicitudEnvio.estatusSolicAutorizacion();
	}
	
	public Optional<SolicitudEnvio> findById(Integer id){
		return iSolicitudEnvio.findById(id);
	}
	
	//Metodo para obtener la última solicitud de envio registrada.
	public  List<SolicitudEnvio> obtenerUltimaSolicitudFecha(Integer solicitud){
		return iSolicitudEnvio.obtenerUltimaSolicitudFecha(solicitud);
	}

}
