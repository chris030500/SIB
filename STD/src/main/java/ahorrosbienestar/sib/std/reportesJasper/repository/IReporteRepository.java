package ahorrosbienestar.sib.std.reportesJasper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;

@Repository
public interface IReporteRepository extends JpaRepository<ReporteJasper, Integer>{

	@Query(value = "SELECT * FROM SIB.TBL_CAT_REPORTES_JASPER WHERE VC_NOMBRE_REPORTE =:nombre", nativeQuery = true)
	public ReporteJasper buscarReporte(String nombre);
	
}
