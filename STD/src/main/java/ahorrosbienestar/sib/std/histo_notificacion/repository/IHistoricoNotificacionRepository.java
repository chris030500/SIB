package ahorrosbienestar.sib.std.histo_notificacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.histo_notificacion.model.HistoricoNotificacion;

@Repository
public interface IHistoricoNotificacionRepository extends JpaRepository<HistoricoNotificacion, Integer>{
	
}
