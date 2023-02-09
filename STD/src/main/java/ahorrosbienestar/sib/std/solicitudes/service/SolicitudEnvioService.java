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

	public List<SolicitudEnvio> estatusSolicAutorizacion() {
		return iSolicitudEnvio.estatusSolicAutorizacion();
	}

	public Optional<SolicitudEnvio> findById(Integer id) {
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

}
