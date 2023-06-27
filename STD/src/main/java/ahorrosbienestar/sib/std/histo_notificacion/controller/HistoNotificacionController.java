package ahorrosbienestar.sib.std.histo_notificacion.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.histo_notificacion.service.HistoNotificacionMensajeService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;

@RestController
public class HistoNotificacionController {

	@Autowired
	private HistoNotificacionMensajeService histoNotificacionMensajeService;
	
	@Autowired
	private SolicitudEnvioService solicitudEnvioService;
	
	@PostMapping("notificacion")
	public ResponseEntity<?> guardarMensaje(@RequestParam (name="mensaje") String mensaje,
				@RequestParam(name = "indEnvio") Integer indEnvio,
				@RequestParam(name = "sEnvio") Integer sEnvio
			){
		
		Optional<SolicitudEnvio> soliEnvio = solicitudEnvioService.findById(sEnvio);
		
		
		return ResponseEntity.ok(histoNotificacionMensajeService.enviarNotificacionMensaje(mensaje, soliEnvio.get(), indEnvio));
		//return ResponseEntity.ok("ok");
	}
	
}
