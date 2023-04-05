package ahorrosbienestar.sib.std.autorizacion.controller;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.autorizacion.descargas.DescargaRestController;
import ahorrosbienestar.sib.std.autorizacion.descargas.dto.GeolocalizacionDTO;
import ahorrosbienestar.sib.std.docto_envio.service.DoctEnvioService;
import ahorrosbienestar.sib.std.histo_notificacion.model.HistoricoNotificacion;
import ahorrosbienestar.sib.std.histo_notificacion.service.HistoricoNotificacionService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AutorizacionRestController {

	// variable estatus de autorizar
	private final static int ESTATUS_AUTORIZAR = 6;

	// variable estatus de rechazar
	private final static int ESTATUS_RECHAZAR = 7;

	@Autowired
	private SolicitudEnvioService solicitudEnvioService;

	@Autowired
	private DoctEnvioService doctEnvioService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private HistoricoNotificacionService historicoNotificacionService;
	
	@Autowired
	private DescargaRestController descargaRestController;

	// Metodo para autorizar el estatus de una solicitud a autorizado/rechazado
	@RequestMapping(value = { "/autorizar_la_solicitud" }, method = RequestMethod.GET)
	public GeolocalizacionDTO cambiarEstatusSolicitud(@RequestParam("tipoEstatus") String tipoEstatus,
			@RequestParam("envioID") Integer envioID, @RequestParam("observaciones") String observaciones)
			throws ParseException, IOException {
		
		//
		GeolocalizacionDTO responseDTO = new GeolocalizacionDTO();

		// Autentificación para obtener el nombre del usuario logueado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Obtenemos el usuario logueado
		Usuario usuario = usuarioService.findByNombre(auth.getName());

		// Crear Fecha
		Date diaActual = new Date();

		// Formato fecha
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/mm/dd");

		// Fecha actual
		String fecha = formatoFecha.format(diaActual);
		Date fechaActual = formatoFecha.parse(fecha);
		LocalDate fechaLocal = fechaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		// Formato para hora
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

		// Hora actual
		String horaActual = formatoHora.format(diaActual);

		// Solicitud envio
		SolicitudEnvio solicitudEnvio = solicitudEnvioService.obtenerSolicitudEnvio(envioID);

		// si es autorizar
		if (tipoEstatus.equals("autorizar")) {

			// Seteamos datos de la solicitud de envio
			solicitudEnvio.setI_estatus(ESTATUS_AUTORIZAR);
			solicitudEnvio.setVc_obser_autoriza(observaciones);
			solicitudEnvio.setFk_usr_autoriza(usuario);
			solicitudEnvio.setDt_fecha_autorizacion(fechaLocal);
			solicitudEnvioService.save(solicitudEnvio);

			// Seteamos datos de los documentos de envio
			doctEnvioService.listadoDoctEnvioActivos(solicitudEnvio.getPk_id_solicitud_envio()).forEach(doct -> {
				doct.setI_estatus(ESTATUS_AUTORIZAR);
				doctEnvioService.save(doct);
			});
			
			// Llamamos al método de crear carpeta, descomprimir zip, y guardar la info del archivo JSON.
			responseDTO = descargaRestController.crearCarpeta(envioID);

			// si es rechazar
		} else if (tipoEstatus.equals("rechazar")) {

			// Seteamos datos de la solicitud de envio
			solicitudEnvio.setI_estatus(ESTATUS_RECHAZAR);
			solicitudEnvio.setVc_obser_cancelacion(observaciones);
			solicitudEnvio.setFk_usr_cancela(usuario);
			solicitudEnvio.setDt_fecha_cancelacion(fechaLocal);
			solicitudEnvio.setVc_hora_cancelacion(horaActual);
			solicitudEnvioService.save(solicitudEnvio);

			// Seteamos datos de los documentos de envio
			doctEnvioService.listadoDoctEnvioActivos(solicitudEnvio.getPk_id_solicitud_envio()).forEach(doct -> {
				doct.setI_estatus(ESTATUS_RECHAZAR);
				doct.setPk_usr_cancela(usuario);
				doct.setDt_fecha_cancelacion(fechaLocal);
				doctEnvioService.save(doct);
			});

		}

		// Llamamos el método para enviar el correo
		agregarNotificacion(tipoEstatus, fechaLocal, horaActual, envioID, observaciones);

		return responseDTO;
	}

	// Método que manda notificacion al correo
	public void agregarNotificacion(String tipo, LocalDate fecha, String hora, Integer envioID, String motivo) {

		HistoricoNotificacion model = new HistoricoNotificacion();

		model.setDt_fecha_registro(fecha);
		model.setVc_hora_registro(hora);
		// Seteamos el modelo de la solicitud de envio
		SolicitudEnvio solicitudEnvio = solicitudEnvioService.obtenerSolicitudEnvio(envioID);
		model.setFk_solicitud_envio(solicitudEnvio);

		if (tipo.equals("autorizar"))
			model.setVc_mensaje("Se ha autorizado la solicitud de envio núm " + envioID + ". Motivo:  " + motivo);
		else if (tipo.equals("rechazar"))
			model.setVc_mensaje("Se ha rechazado la solicitud de envio núm " + envioID + ". Motivo:  " + motivo);

		historicoNotificacionService.save(model);
	}

}
