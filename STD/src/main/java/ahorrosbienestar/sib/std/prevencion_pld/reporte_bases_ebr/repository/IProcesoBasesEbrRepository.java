package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.ProcesoBasesEbr;

@Repository
public interface IProcesoBasesEbrRepository extends JpaRepository<ProcesoBasesEbr, Integer> {
	@Query(value = "SELECT MAX(PK_PROCESO_EBR_ID) FROM SIB.TBL_PLD_PROCESO_BASES_EBR", nativeQuery = true)
	public Integer findMaxProceso();
	
    @Query(value = "SELECT SUB.procesoId, SUB.fechaGeneracion, SUB.usuarioVobo, SUB.usuarioEjecuto, "
    		+ "SUB.fechaVobo, SUB.periodo " +
    		"FROM (SELECT T1.PK_PROCESO_EBR_ID as procesoId, " +
            "DATE_FORMAT(T1.DT_FECHA_EJECUCION, '%d/%m/%Y %H:%i:%s') AS fechaGeneracion, " +
            "T2.VC_NOMBRE_USUARIO AS usuarioVobo, " +
            "T3.VC_NOMBRE_USUARIO AS usuarioEjecuto, " +
            "DATE_FORMAT(T1.DT_FECHA_VOBO, '%d/%m/%Y %H:%i:%s') AS fechaVobo, " +
            "T1.I_ANIO_AUDITORIA AS periodo, " +
            "ROW_NUMBER() OVER (PARTITION BY T1.I_ANIO_AUDITORIA ORDER BY T1.DT_FECHA_VOBO DESC) AS rn " +
            "FROM SIB.TBL_PLD_PROCESO_BASES_EBR T1 " +
            "INNER JOIN SIB.TBL_CAT_USUARIOS T2 ON T1.I_USUARIO_VOBO = T2.PK_ID_USUARIO " +
            "INNER JOIN SIB.TBL_CAT_USUARIOS T3 ON T1.I_USUARIO_EJECUTO = T3.PK_ID_USUARIO " +
            "WHERE T1.I_ESTATUS_VOBO = 1) SUB " +
            "WHERE rn = 1",
    nativeQuery = true)
	public List<Map<String, Object>> listaHistoricoBases();
}
