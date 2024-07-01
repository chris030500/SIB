package ahorrosbienestar.sib.std.sccb.solicitudesCredito.repository;


import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.DatosSolicitudCredito;

@Repository
public interface IDatosSolicitudCreditoRepository extends JpaRepository<DatosSolicitudCredito, Integer> {
	
	@Query(value = "SELECT DISTINCT T1.I_SOLICITUD_ID AS solicitud_id, T1.I_CREDITO_ID AS credito_id, "
			+ "T1.I_AC_DEP_RESPALDO AS ac_dep_respaldo, T1.I_ACUERDO_OPERATIVA AS acuerdo_operativa, "
			+ "T1.DT_FECHA_RECEPCION AS fecha_recepcion, T1.DT_FECHA_RESULTADO AS fecha_resultado, "
			+ "T1.DT_FECHA_INICIO_TRAMITE AS fecha_inicio_tramite, "
			+ "T1.DT_FECHA_RECONSIDERACION AS fecha_reconsideracion, T1.I_VALOR_AVALUO AS valor_avaluo, "
			+ "T1.I_NO_CLIENTE AS no_cliente, T1.VC_NOMBRE_COMPLETO AS nombre_completo, "
			+ "T1.VC_DESCRIPCION_CLIENTE AS desc_cliente, T1.I_IMPORTE_CREDITO AS importe_credito, T1.I_PLAZO AS plazo, "
			+ "T1.I_NUMERO_TANTOS AS num_tantos, T1.FL_CMSN_ASE_FIN AS cmsn_ase_fin, T1.FL_TASA_NORMAL AS tasa_normal, "
			+ "T1.I_TASA_MORATORIA AS tasa_morat, T1.I_NUM_ABONOS_DIFERIDOS AS num_abonos_diferidos, "
			+ "T1.VC_DESCRIPCION_ETAPA AS desc_etapa, T1.VC_DESCRIPCION_ESTATUS AS desc_estatus, "
			+ "T1.I_CENTRO_ID AS centro_id, T1.VC_NOMBRE_CENTRO AS nombre_centro, T1.VC_OBSERVACIONES AS observaciones, "
			+ "T1.VC_OBSERVACIONES_CC AS observaciones_cc, T1.I_CMSN_IMP_CREDITO AS cmsn_imp_credito, "
			+ "T1.I_DEP_EN_RESPALDO AS dep_en_respaldo, T1.I_REVISION_VEHICULAR AS revision_vehicular, "
			+ "T1.VC_DESCRIPCION_FRECUENCIA AS desc_frecuencia, T1.VC_NOMBRE_DESTINO AS nombre_destino, "
			+ "T2.DT_FECHA_DESEMBOLSO AS fecha_desembolso, T2.DT_FECHA_AUTORIZACION AS fecha_autorizacion, "
			+ "T3.VC_NOMBRE_PRODUCTO AS nombre_producto, "
			+ "T4.VC_NOMBRE_EMPLEADO AS nombre_empleado_captura, T5.VC_NOMBRE_EMPLEADO AS nombre_empleado_recibe, "
			+ "T6.VC_NOMBRE_EMPLEADO AS nombre_empleado_analiza "
			+ "FROM SIB.TBL_SCCB_DATOS_SOLICITUD T1 "
			+ "LEFT JOIN SIB.TBL_SCCB_SOLICITUDES T2 ON T1.I_SOLICITUD_ID = T2.I_SOLICITUD_ID "
			+ "LEFT JOIN SIB.TBL_SCCB_PRODUCTOS T3 ON T1.I_PRODUCTO_ID = T3.PK_I_PRODUCTOID "
			+ "LEFT JOIN SIB.TBL_SCCB_EMPLEADOS T4 ON T1.I_EMPLEADO_CAPTURA_ID = T4.PK_I_EMPLEADOID "
			+ "LEFT JOIN SIB.TBL_SCCB_EMPLEADOS T5 ON T1.I_EMPLEADO_RECIBE_ID = T5.PK_I_EMPLEADOID "
			+ "LEFT JOIN SIB.TBL_SCCB_EMPLEADOS T6 ON T1.I_EMPLEADO_ANALIZA_ID = T6.PK_I_EMPLEADOID "
			+ "WHERE T1.I_SOLICITUD_ID = :idSolicitud ", nativeQuery = true)
	public Optional<Map<String, Object>> getDatosSolicitudCredito(@Param("idSolicitud") Integer idSolicitud);
	
	@Query(value = "SELECT * "
			+ "FROM SIB.TBL_SCCB_DATOS_SOLICITUD "
			+ "WHERE I_CREDITO_ID = :idCredito ", nativeQuery = true)
	public Optional<DatosSolicitudCredito> getDatosSolicitudByIdCredito(@Param("idCredito") Integer idCredito);
}
