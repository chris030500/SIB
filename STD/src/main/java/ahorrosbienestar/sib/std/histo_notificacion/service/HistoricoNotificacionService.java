package ahorrosbienestar.sib.std.histo_notificacion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ahorrosbienestar.sib.std.histo_notificacion.model.HistoricoNotificacion;
import ahorrosbienestar.sib.std.histo_notificacion.repository.IHistoricoNotificacionRepository;

@Service
public class HistoricoNotificacionService {
	@Autowired
	private IHistoricoNotificacionRepository iHistoricoNotificacionRepository;

	@Autowired
	public HistoricoNotificacionService(IHistoricoNotificacionRepository iHistoricoNotificacionRepository) {
		this.iHistoricoNotificacionRepository = iHistoricoNotificacionRepository;
		}
	
	public HistoricoNotificacion save(HistoricoNotificacion historicoNotificacion) {
		return iHistoricoNotificacionRepository.save(historicoNotificacion);
	}
	
}
