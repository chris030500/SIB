package ahorrosbienestar.sib.std.cancelacion.controlller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@EnableScheduling
public class CancelacionRestController {

	// variable estatus cancelación manual
	private final static int ESTATUS_CANCELAR_MANUAL = 4;

	// variable estatus cancelación automática
	private final static int ESTATUS_CANCELAR_AUTO = 5;

	// variable estatus buscar solicitud sin consumir WS
	private final static int ESTATUS_BUSCAR = 1;

	// variable estatus buscar solicitud para consumir WS
	private final static int ESTATUS_BUSCAR_WS = 3;

	@Autowired
	private SolicitudEnvioService solicitudEnvioService;
	@Autowired
	private DoctEnvioService doctEnvioService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private HistoricoNotificacionService historicoNotificacionService;

	// Método que cancela de manera manual
	@RequestMapping(value = { "/cancelar_solicitud_envio" }, method = RequestMethod.GET)
	public HttpStatus cancelarEstatusDeSolicitud(@RequestParam("envioID") Integer envioID,
			@RequestParam("motivo") String motivo) throws ParseException {

		// Autentificación para obtener el nombre del usuario logueado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Obtenemos el usuario logueado
		Usuario usuario = usuarioService.findByNombre(auth.getName());

		// Crear Fecha
		Date diaActual = new Date();

		// Formato para hora
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
		// Hora actual
		String hora = formatoHora.format(diaActual);

		// Formato fecha
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/mm/dd");

		// Fecha actual
		String fecha = formatoFecha.format(diaActual);
		Date fechaActual = formatoFecha.parse(fecha);
		LocalDate fechaLocal = fechaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		// Cancelamos la solicitud envio
		SolicitudEnvio model = solicitudEnvioService.obtenerSolicitudEnvio(envioID);
		model.setI_estatus(ESTATUS_CANCELAR_MANUAL);
		model.setVc_obser_cancelacion(motivo);
		model.setFk_usr_cancela(usuario);
		solicitudEnvioService.save(model);

		// Cancelamos los doct envio
		doctEnvioService.listadoDoctEnvioActivos(model.getPk_id_solicitud_envio()).forEach(doct -> {
			doct.setI_estatus(ESTATUS_CANCELAR_MANUAL);
			doct.setPk_usr_cancela(usuario);
			doct.setDt_fecha_cancelacion(fechaLocal);
			doctEnvioService.save(doct);
		});

		// Mandar mensaje
		agregarNotificacion(fechaLocal, hora, model.getPk_id_solicitud_envio(), motivo);

		return HttpStatus.OK;
	}

	// Método que cancela de manera automatica
	@Scheduled(cron = "0 0 15 * * ?")
	public void cancelacionAutomatica() throws ParseException {

		// Autentificación para obtener el nombre del usuario logueado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Obtenemos el usuario logueado
		Usuario usuario = usuarioService.findByNombre(auth.getName());

		// Fecha actual
		Date diaActual = new Date();

		// Formato para hora
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

		// Hora actual
		String hora = formatoHora.format(diaActual);
		Date horaActual = formatoHora.parse(hora);

		// Formato fecha
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/mm/dd");

		// Fecha actual
		String fecha = formatoFecha.format(diaActual);
		Date fechaActual = formatoFecha.parse(fecha);
		LocalDate fechaLocal = fechaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		// Listas de solicitudes de envío con los estatus de Nivel de sistema y WS
		List<SolicitudEnvio> listaNS = new ArrayList<SolicitudEnvio>();
		List<SolicitudEnvio> listaWS = new ArrayList<SolicitudEnvio>();

		// Preguntamos si hay solicitudes en ambos
		listaNS = solicitudEnvioService.solicitudesDisponibles(null, ESTATUS_BUSCAR);
		listaWS = solicitudEnvioService.solicitudesDisponibles(null, ESTATUS_BUSCAR_WS);

		// Nivel de sistema
		listaNS.forEach(model -> {

			// Fecha cancelacion
			Date dateModel = Date
					.from(model.getDt_fecha_cancelacion().atStartOfDay(ZoneId.systemDefault()).toInstant());

			// Hora cancelacion
			Date horaModel = null;
			try {
				horaModel = formatoHora.parse(model.getVc_hora_cancelacion());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (diaActual.after(dateModel)) {

				if (horaActual.after(horaModel)) {

					// cumple con la fecha y la hora
					System.out.println(true);

					// Cancelamos la solicitud envio
					model.setI_estatus(ESTATUS_CANCELAR_AUTO);
					model.setVc_obser_cancelacion("Cancelación por proceso automático.");
					model.setFk_usr_cancela(usuario);
					solicitudEnvioService.save(model);

					// Cancelamos los doct envio
					doctEnvioService.listadoDoctEnvioActivos(model.getPk_id_solicitud_envio()).forEach(doct -> {
						doct.setI_estatus(ESTATUS_CANCELAR_AUTO);
						doct.setPk_usr_cancela(usuario);
						doct.setDt_fecha_cancelacion(fechaLocal);
						doctEnvioService.save(doct);
					});
				} else
					// cumple con la fecha, pero no con la hora
					System.out.println(false);
			} else
				// no cumple con la fecha
				System.out.println(false);

			// Mandar correo
			agregarNotificacion(fechaLocal, hora, model.getPk_id_solicitud_envio(),
					"Cancelación por proceso automático.");

		});

		
		// Nivel de sistema y WS
		listaWS.forEach(model -> {

			// Fecha cancelacion
			Date dateModel = Date
					.from(model.getDt_fecha_cancelacion().atStartOfDay(ZoneId.systemDefault()).toInstant());

			// Hora cancelacion
			Date horaModel = null;
			try {
				horaModel = formatoHora.parse(model.getVc_hora_cancelacion());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (diaActual.after(dateModel)) {

				if (horaActual.after(horaModel)) {

					// cumple con la fecha y la hora
					System.out.println(true);

					// Cancelamos la solicitud envio
					model.setI_estatus(ESTATUS_CANCELAR_AUTO);
					model.setVc_obser_cancelacion("Cancelación por proceso automático.");
					model.setFk_usr_cancela(usuario);
					solicitudEnvioService.save(model);

					// Cancelamos los doct envio
					doctEnvioService.listadoDoctEnvioActivos(model.getPk_id_solicitud_envio()).forEach(doct -> {
						doct.setI_estatus(ESTATUS_CANCELAR_AUTO);
						doct.setPk_usr_cancela(usuario);
						doct.setDt_fecha_cancelacion(fechaLocal);
						doctEnvioService.save(doct);
					});
				} else
					// cumple con la fecha, pero no con la hora
					System.out.println(false);
			} else
				// no cumple con la fecha
				System.out.println(false);

			// Mandar correo
			agregarNotificacion(fechaLocal, hora, model.getPk_id_solicitud_envio(), "Cancelación por proceso automático.");

		});

	}

	// Llenar notificacion de correo
	public void agregarNotificacion(LocalDate fecha, String hora, Integer envioID, String motivo) {
		// Obtenemos el modelo de la solicitud de envio
		SolicitudEnvio solicitudEnvio = solicitudEnvioService.obtenerSolicitudEnvio(envioID);
		// Creamos el modelo del historico notificacion
		HistoricoNotificacion model = new HistoricoNotificacion();
		model.setDt_fecha_registro(fecha);
		model.setVc_mensaje("Se ha cancelado la solicitud de envio núm " + envioID + ". Motivo:  " + motivo);
		model.setVc_hora_registro(hora);
		model.setFk_solicitud_envio(solicitudEnvio);
		// Guardamos el modelo
		historicoNotificacionService.save(model);
	}

}
