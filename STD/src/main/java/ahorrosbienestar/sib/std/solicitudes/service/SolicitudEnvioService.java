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
	
	//Metodo para mostrar la lista de las solicitudes estatus (1 y 2).
	public List<SolicitudEnvio> estatusSolicAutorizacion(){
		return iSolicitudEnvio.estatusSolicAutorizacion();
	}
	
	//Metodo para buscar por id la solicitud.
	public Optional<SolicitudEnvio> findById(Integer id){
		return iSolicitudEnvio.findById(id);
	}
	
	//Metodo para obtener la última solicitud de envio registrada.
	public  List<SolicitudEnvio> obtenerUltimaSolicitudFecha(Integer solicitud){
		return iSolicitudEnvio.obtenerUltimaSolicitudFecha(solicitud);
	}
	
	//Metodo para validar la solicitud de envio 
	// 3 = Autorizacion de documentos digitales
	// 2 = Rechazo de documentos
	public int validarSoliEnvio(Integer estatus, Integer idSolicitud) {
		return iSolicitudEnvio.validarSoliEnvio(estatus, idSolicitud);
	}

}
