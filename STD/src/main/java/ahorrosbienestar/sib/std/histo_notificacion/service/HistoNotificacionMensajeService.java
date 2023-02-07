package ahorrosbienestar.sib.std.histo_notificacion.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.histo_notificacion.model.HistoricoNotificacion;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;

@Service
public class HistoNotificacionMensajeService {
	
	@Autowired
	private HistoricoNotificacionService historicoNotificacionService;
	
	public void enviarNotificacionMensaje(String mensaje, LocalDate fecha, String hora, SolicitudEnvio sEnvio) {
		
		HistoricoNotificacion historicoNotificacion = new HistoricoNotificacion();
		historicoNotificacion.setVc_mensaje(mensaje);
		historicoNotificacion.setDt_fecha_registro(fecha);
		historicoNotificacion.setVc_hora_registro(hora);
		historicoNotificacion.setI_ind_envio(0);
		historicoNotificacion.setFk_solicitud_envio(sEnvio);
		
		historicoNotificacionService.save(historicoNotificacion);
		
	}

}
