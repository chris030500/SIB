package ahorrosbienestar.sib.std.solicitudes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.repository.ISolicitudEnvio;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;

@Service
public class SolicitudEnvioService {

	private ISolicitudEnvio iSolicitudEnvio;

	@Autowired
	public SolicitudEnvioService(ISolicitudEnvio iSolicitudEnvio) {
		this.iSolicitudEnvio = iSolicitudEnvio;
	}

	// Metodo para guardar la solicitud de envio
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
	//Metodo para mostrar la lista de las solicitudes estatus (1 y 2).
	public List<SolicitudEnvio> estatusSolicAutorizacion(){
		return iSolicitudEnvio.estatusSolicAutorizacion();
	}


	//Metodo para buscar por id la solicitud.
	public Optional<SolicitudEnvio> findById(Integer id){
		return iSolicitudEnvio.findById(id);
	}

	public List<SolicitudEnvio> solicitudesDisponibles(Integer solicitudID, Integer estatus) {
		if (solicitudID != null)
			return iSolicitudEnvio.solicitudesDisponibles(solicitudID, estatus);
		else
			return iSolicitudEnvio.solicitudesDisponibles(estatus);
	}

	// Metodo para obtener la última solicitud de envio registrada.
	public SolicitudEnvio obtenerUltimaSolicitudFecha(Integer solicitud, Integer estatus) {
		if (estatus == null)
			return iSolicitudEnvio.obtenerUltimaSolicitudFecha(solicitud);
		else 
			return iSolicitudEnvio.obtenerUltimaSolicitudFecha(solicitud, estatus);
	}

	// Servicio que regresa el modelo de la solicitud de envio.
	public SolicitudEnvio obtenerSolicitudEnvio(Integer envioID) {
		return iSolicitudEnvio.obtenerSolicitudEnvio(envioID);
	}
	
	// Servicio que obtiene todas las solicitudes de un credito
	public List<SolicitudEnvio> obtenerTodasLasSolicitudes(Integer solicitudID) {
		return iSolicitudEnvio.obtenerTodasLasSolicitudes(solicitudID);
	}
	
	//Metodo para validar la solicitud de envio 
	// 3 = Autorizacion de documentos digitales
	// 2 = Rechazo de documentos
	public int validarSoliEnvio(Integer estatus, Integer idSolicitud) {
		return iSolicitudEnvio.validarSoliEnvio(estatus, idSolicitud);
	}
	
	public int agregarRequisitionID(String requisitionId, Integer soliEnvio) {
		return iSolicitudEnvio.agregarRequisitionId(requisitionId, soliEnvio);
	}

}
