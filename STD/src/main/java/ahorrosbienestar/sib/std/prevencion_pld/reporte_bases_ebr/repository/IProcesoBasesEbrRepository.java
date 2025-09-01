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
	
    @Query(value = "SELECT T1.PK_PROCESO_EBR_ID as pk_proceso_id, " +
            "DATE_FORMAT(T1.DT_FECHA_EJECUCION, '%d-%m-%Y %H:%i:%s') AS dt_fecha_generacion, " +
            "T2.VC_NOMBRE_USUARIO AS vc_usuario, " +
            "CASE WHEN T1.I_ESTATUS_VOBO = 1 THEN 'SI' ELSE '' END AS vc_estatus_vobo, " +
            "DATE_FORMAT(T1.DT_FECHA_VOBO, '%d-%m-%Y %H:%i:%s') AS dt_fecha_vobo, " +
            "CONCAT(DATE_FORMAT(T1.DT_FECHA_INICIO, '%d-%m-%Y'), ' - ', DATE_FORMAT(T1.DT_FECHA_FIN, '%d-%m-%Y')) AS vc_periodo " +
            "FROM SIB.TBL_PLD_PROCESO_BASES_EBR T1 " +
            "INNER JOIN SIB.TBL_CAT_USUARIOS T2 ON T1.I_USUARIO_VOBO = T2.PK_ID_USUARIO " +
            "WHERE T1.I_ESTATUS_VOBO = 1",
    nativeQuery = true)
	public List<Map<String, Object>> listaHistoricoBases();
}
