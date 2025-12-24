package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.HistReportesAuditoria;

@Repository
public interface IHistReportesAuditoriaRepository extends JpaRepository<HistReportesAuditoria, Integer> {
	
	@Query(value = "SELECT DISTINCT T1.I_ANIO_GENERACION " +
			"FROM (SELECT I_ANIO_GENERACION " +
	        "FROM SIB.TBL_HISTORIAL_REPORTES_AUDITORIAS " +
	        "WHERE I_ESTATUS = 1 " +
	        "GROUP BY I_ANIO_GENERACION " +
	        "UNION ALL " +
	        "SELECT 2024 as I_Anio_Auditoria) as T1 ",
	       nativeQuery = true)
	public List<Integer> obtenerAñoAuditorias();
	
}
