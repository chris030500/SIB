package ahorrosbienestar.sib.std.solicitudes.hallazgo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.solicitudes.hallazgo.model.HallazgoSolicitud;
import ahorrosbienestar.sib.std.solicitudes.hallazgo.service.HallazgoSolicitudService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@RestController
public class HallazgoSolicitudRestController {

	@Autowired
	private HallazgoSolicitudService hallazgoSolicitudService;

	@Autowired
	private HallazgoSolicitudRestController(HallazgoSolicitudService hallazgoSolicitudService) {
		this.hallazgoSolicitudService = hallazgoSolicitudService;
	}
	
	@Autowired
	private UsuarioService usuarioService;

	protected final Log logger = LogFactory.getLog(this.getClass());

	@PostMapping("/hallazgoSolicitud/guardarHallazgo")
	public ResponseEntity<?> guardarHallazgo(
			@RequestParam(name = "idDoctoEnvio", required = false) Integer idDoctoEnvio,
			@RequestParam(name = "comentario", required = false) String comentario) {

		// Obtenemos el usuario autentificado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// Obtenemos el nombre del usuario autentificado
		Usuario usu = usuarioService.findByNombre(auth.getName());
		// Establecemos el formato de la fecha
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Obtenemos la fecha en String
		String fechaActual = dateFormat.format(Calendar.getInstance().getTime());
		// Obtenemos la fecha actual con el formato yyyy-MM-dd
		LocalDate localDateHoy = LocalDate.parse(fechaActual);

		HallazgoSolicitud hs = new HallazgoSolicitud();

		hs.setFk_id_docto_envio(idDoctoEnvio);
		hs.setVc_comentarios(comentario);
		hs.setI_estatus(1);
		hs.setPk_usr_registra(usu);
		hs.setDt_fecha_registro(localDateHoy);
		
		System.out.println("idDocumento: " + idDoctoEnvio);
		System.out.println("comentario: " + comentario);
		System.out.println("estatus:" + 1);
		System.out.println("usr_registra: " + usu);
		System.out.println("fecha registro:" + fechaActual);
		
		//hs = hallazgoSolicitudService.save(hs);
		//return ResponseEntity.ok(hs);
		return ResponseEntity.ok("ok");
	}

}
