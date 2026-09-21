package ahorrosbienestar.sib.std.solicitudes.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudesRestContoller {

	private static final Logger logger = Logger.getLogger(SolicitudesRestContoller.class);
	
	private SolicitudEnvioService solicitudEnvioService;

	@Autowired
	public SolicitudesRestContoller(SolicitudEnvioService solicitudEnvioService) {
		this.solicitudEnvioService = solicitudEnvioService;
	}
	
	@GetMapping("/{solicitud}")
	public ResponseEntity<?> findBySolicitud(@PathVariable (name ="solicitud") Integer solicitud,PageRequest pageRequest) {
		try {

			return ResponseEntity.ok(solicitudEnvioService.findBySolicitud(solicitud));
		} catch (Exception e) {
			logger.error("Error inesperado durante la operación.", e);
		}
		return null;
	}
	@GetMapping("/{fechaInicio}/{fechaFin}")
	public ResponseEntity<?> findBySolicitud(@PathVariable String fechaInicio,@PathVariable String fechaFin) {
		try {

			return ResponseEntity.ok(solicitudEnvioService.findByRangoFechas(fechaInicio,fechaFin));
		} catch (Exception e) {
			logger.error("Error inesperado durante la operación.", e);
		}
		return null;
	}
}
