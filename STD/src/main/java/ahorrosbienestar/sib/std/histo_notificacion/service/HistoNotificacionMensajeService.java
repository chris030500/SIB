package ahorrosbienestar.sib.std.histo_notificacion.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.histo_notificacion.model.HistoricoNotificacion;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;

@Service
public class HistoNotificacionMensajeService {

	@Autowired
	private HistoricoNotificacionService historicoNotificacionService;

	public HistoricoNotificacion enviarNotificacionMensaje(String mensaje, SolicitudEnvio sEnvio, int indEnvio) {

		// Establecemos el formato de la fecha
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Obtenemos la fecha en String
		String fechaActual = dateFormat.format(Calendar.getInstance().getTime());
		// Obtenemos la fecha actual con el formato yyyy-MM-dd
		LocalDate localDateHoy = LocalDate.parse(fechaActual);

		DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
		String hora = dateFormat2.format(new Date());

		HistoricoNotificacion historicoNotificacion = new HistoricoNotificacion();
		historicoNotificacion.setVc_mensaje(mensaje);
		historicoNotificacion.setDt_fecha_registro(localDateHoy);
		historicoNotificacion.setVc_hora_registro(hora);
		historicoNotificacion.setI_ind_envio(indEnvio);
		historicoNotificacion.setFk_solicitud_envio(sEnvio);

		// historicoNotificacionService.save(historicoNotificacion);
		return historicoNotificacionService.save(historicoNotificacion);
	}

}
