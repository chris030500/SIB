package ahorrosbienestar.sib.std.sccb.solicitudesCredito.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.SolicitudesCredito;

@Repository
public interface ISolicitudesCreditoRepository extends JpaRepository<SolicitudesCredito, Integer> {
	
	@Query(value = "SELECT T1.I_SOLICITUD_ID AS solicitud_id, T1.I_CREDITO_ID AS credito_id, "
			+ "T1.VC_NOMBRE_CLIENTE AS nombre_cliente, T1.I_NO_CLIENTE AS no_cliente, "
			+ "T1.VC_ROL AS rol, T1.VC_NOMBRE_CENTRO AS nombre_centro, T1.VC_ETAPA AS etapa, "
			+ "T1.VC_ESTATUS AS estatus, T1.FL_MONTO AS monto, T1.DT_FECHA_RECEPCION AS fecha_recepcion, "
			+ "T1.DT_FECHA_AUTORIZACION AS fecha_autorizacion, T1.DT_FECHA_DESEMBOLSO AS fecha_desembolso, "
			+ "T1.DT_FECHA_INICIO_TRAMITE AS fecha_inicio_tramite "
			+ "FROM SIB.TBL_SCCB_SOLICITUDES T1 "
			+ "LEFT JOIN SIB.TBL_SCCB_DATOS_SOLICITUD T2 ON T2.I_CREDITO_ID = T1.I_CREDITO_ID "
			+ "WHERE T1.I_CREDITO_ID = :idCredito "
			+ "ORDER BY T1.I_SOLICITUD_ID ", nativeQuery = true)
	public List<Map<String, Object>> getByIdCreditoSolicitudes(@Param("idCredito") Integer idCredito);
	
	@Query(value = "SELECT T1.I_SOLICITUD_ID AS solicitud_id, T1.I_CREDITO_ID AS credito_id, "
			+ "T1.VC_NOMBRE_CLIENTE AS nombre_cliente, T1.I_NO_CLIENTE AS no_cliente, "
			+ "T1.VC_ROL AS rol, T1.VC_NOMBRE_CENTRO AS nombre_centro, T1.VC_ETAPA AS etapa, "
			+ "T1.VC_ESTATUS AS estatus, T1.FL_MONTO AS monto, T1.DT_FECHA_RECEPCION AS fecha_recepcion, "
			+ "T1.DT_FECHA_AUTORIZACION AS fecha_autorizacion, T1.DT_FECHA_DESEMBOLSO AS fecha_desembolso, "
			+ "T1.DT_FECHA_INICIO_TRAMITE AS fecha_inicio_tramite "
			+ "FROM SIB.TBL_SCCB_SOLICITUDES T1 "
			+ "LEFT JOIN SIB.TBL_SCCB_DATOS_SOLICITUD T2 ON T2.I_CREDITO_ID = T1.I_CREDITO_ID "
			+ "WHERE T1.I_NO_CLIENTE = :noCliente "
			+ "ORDER BY T1.I_SOLICITUD_ID ", nativeQuery = true)
	public List<Map<String, Object>> getByNoClienteSolicitudes(@Param("noCliente") Integer noCliente);
	
	@Query(value = "SELECT T1.I_SOLICITUD_ID AS solicitud_id, T1.I_CREDITO_ID AS credito_id, "
			+ "T1.VC_NOMBRE_CLIENTE AS nombre_cliente, T1.I_NO_CLIENTE AS no_cliente, "
			+ "T1.VC_ROL AS rol, T1.VC_NOMBRE_CENTRO AS nombre_centro, T1.VC_ETAPA AS etapa, "
			+ "T1.VC_ESTATUS AS estatus, T1.FL_MONTO AS monto, T1.DT_FECHA_RECEPCION AS fecha_recepcion, "
			+ "T1.DT_FECHA_AUTORIZACION AS fecha_autorizacion, T1.DT_FECHA_DESEMBOLSO AS fecha_desembolso, "
			+ "T1.DT_FECHA_INICIO_TRAMITE AS fecha_inicio_tramite "
			+ "FROM SIB.TBL_SCCB_SOLICITUDES T1 "
			+ "LEFT JOIN SIB.TBL_SCCB_DATOS_SOLICITUD T2 ON T2.I_CREDITO_ID = T1.I_CREDITO_ID "
			+ "WHERE T1.VC_NOMBRE_CLIENTE = :nomCliente "
			+ "AND T1.I_CREDITO_ID = :idCredito "
			+ "ORDER BY T1.I_SOLICITUD_ID ", nativeQuery = true)
	public List<Map<String, Object>> getByNomClienteAndIdCreditoSolicitudes(
			@Param("nomCliente") String nomCliente,
			@Param("idCredito") Integer idCredito);
	
	@Query(value = "SELECT T1.VC_NOMBRE_CLIENTE AS nombre_cliente, T1.I_CREDITO_ID AS credito_id, "
	        + "T1.VC_ROL AS rol "
	        + "FROM SIB.TBL_SCCB_SOLICITUDES T1 "
	        + "WHERE T1.VC_NOMBRE_CLIENTE LIKE CONCAT('%', :nomCliente, '%') "
	        + "LIMIT 100",
	        nativeQuery = true)
	public List<Map<String, Object>> getNamesSolicitudes(
	        @Param("nomCliente") String nomCliente);
}
