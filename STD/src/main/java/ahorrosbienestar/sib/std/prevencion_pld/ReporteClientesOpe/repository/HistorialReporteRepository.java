package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.HistorialReporte;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialReporteRepository extends JpaRepository<HistorialReporte, Integer> {


	@Query(value = "SELECT * FROM SIB.TBL_HISTORIAL_REPORTES_AUDITORIAS WHERE I_ESTATUS =1", nativeQuery = true)
	public List<HistorialReporte> procesoVobo();
}
 