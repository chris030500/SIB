package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;

public interface ReporteClientesOpeRepository
extends JpaRepository<ReporteClientesOpeModel, Integer> {
	@Modifying
	@Query("UPDATE ReporteClientesOpeModel r SET r.campoActualizado = :valor WHERE r.pkId = :id")
	void actualizarCampo(Long id, String valor);
}