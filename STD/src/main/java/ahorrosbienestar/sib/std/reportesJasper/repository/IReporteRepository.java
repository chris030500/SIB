package ahorrosbienestar.sib.std.reportesJasper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;

@Repository
public interface IReporteRepository extends JpaRepository<ReporteJasper, Integer>{
	
	@Query(value = "SELECT * FROM TBL_CAT_REPORTES_JASPER WHERE UPPER(VC_NOMBRE_REPORTE)=:nombreReporte", nativeQuery = true)
	public ReporteJasper findByNombre(@Param("nombreReporte")  String nombreReporte);
}
