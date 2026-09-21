package ahorrosbienestar.sib.std.solicitudes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;

@Controller
public class SolicitudEnvioController {
	
	@Autowired
	private SolicitudEnvioService solicitudEnvioService;
	
	@GetMapping("solicitudEnvio/solicitudes")
	public String mostrarSolicitudes(Model model){
		model.addAttribute("solicitudes", solicitudEnvioService.estatusSolicAutorizacion());
		return "validacionDocumentos/index";
	}
	
	@GetMapping("solicitudEnvio/verSolicitud/{solicitudEnvio}")
	public String verSolicitud(Model model, @PathVariable Integer solicitudEnvio) {
		SolicitudEnvio solicitud = new SolicitudEnvio();
		
		Optional<SolicitudEnvio> optionalSolicitudEnvio = solicitudEnvioService.findById(solicitudEnvio);
		solicitud = optionalSolicitudEnvio.get();
		model.addAttribute("solicitud", solicitud);
		model.addAttribute("solicitudEnvio", solicitudEnvio);
		return "validacionDocumentos/verSolicitud";
	}
	
	
	
}
