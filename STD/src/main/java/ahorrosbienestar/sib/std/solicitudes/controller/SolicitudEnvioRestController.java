package ahorrosbienestar.sib.std.solicitudes.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@RestController
public class SolicitudEnvioRestController {

	private SolicitudEnvioService solicitudEnvioService;

	@Autowired
	public SolicitudEnvioRestController(SolicitudEnvioService solicitudEnvioService) {
		this.solicitudEnvioService = solicitudEnvioService;
	}

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/solicitudEnvio/guardarSolicitudEnvio")
	public ResponseEntity<?> guardarSolicitudEnvio(
			@RequestParam(name = "solicitud", required = false) Integer solicitud,
			@RequestParam(name = "producto", required = false) Integer producto,
			@RequestParam(name = "comentarioValidacion", required = false) String comentarioValidacion,
			@RequestParam(name = "fEnvio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fEnvio,
			@RequestParam(name = "hEnvio", required = false) String hEnvio,
			@RequestParam(name = "fCancelacion", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fCancelacion,
			@RequestParam(name = "hCancelacion", required = false) String hCancelacion) throws IOException {

		// System.out.println("Obtenemos el valor de la solicitud:" + solicitud);
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

		SolicitudEnvio sEnvio = new SolicitudEnvio();
		sEnvio.setFk_usr_registra(usu);
		sEnvio.setDt_fecha_registro(localDateHoy);
		sEnvio.setI_no_solicitud_core(solicitud);
		sEnvio.setI_producto_id(producto);
		sEnvio.setVc_obser_peticion(comentarioValidacion);
		sEnvio.setDt_fecha_envio(fEnvio);
		sEnvio.setVc_hora_envio(hEnvio);
		sEnvio.setDt_fecha_cancelacion(fCancelacion);
		sEnvio.setVc_hora_cancelacion(hCancelacion);
		sEnvio.setI_estatus(1);

		sEnvio = solicitudEnvioService.save(sEnvio);

		// Aqui vamos a almacenar los archivos PDF para asocialos con la solicitud de
		// envio
		return ResponseEntity.ok(sEnvio);
	}

	@GetMapping("solicitudEnvio/verSolicitudes")
	public List<SolicitudEnvio> estatusSolicAutorizacion() {
		return solicitudEnvioService.estatusSolicAutorizacion();
	}

	@GetMapping("solicitudEnvio/obtenerSolicitudFecha/{solicitud}")
	public ResponseEntity<?> obtenerUltimaSolicitudFecha(@PathVariable(name = "solicitud") Integer solicitud) {
		return ResponseEntity.ok(solicitudEnvioService.obtenerUltimaSolicitudFecha(solicitud));
	}


	@GetMapping("solicitudEnvio/visualizarArchivo/")
	public ResponseEntity<InputStreamResource> visualizarArchivo(
			@RequestParam(name = "nombreArchivo") String nombreArchivo
			) throws FileNotFoundException {
		//String nombreArchivo = "C:/Users/osvaldo.escamilla/Documents/home/CoordFirma/AnexosContrato.pdf";
		System.out.println("Entro en visualizarArchivo");
		System.out.println("Archivo: " + nombreArchivo);
//		String filePath = "/path/to/file/";
//		String fileName = "fileName.pdf";
		File file = new File(nombreArchivo);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline;filename=" + nombreArchivo);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/pdf")).body(resource);
	}

}
