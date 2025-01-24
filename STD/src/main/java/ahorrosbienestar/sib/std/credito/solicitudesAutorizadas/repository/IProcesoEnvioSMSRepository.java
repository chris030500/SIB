package ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.model.ProcesoEnvioSMS;

public interface IProcesoEnvioSMSRepository extends JpaRepository<ProcesoEnvioSMS, Integer> {
	
	@Modifying
	@Transactional
	@Query(value = "SET lc_time_names = 'es_ES'", nativeQuery = true)
	void setLocaleSpanish();
	
	@Query(value = "SELECT T1.PK_PROCESOID AS pkIdProceso, "
			+ "DATE_FORMAT(T1.DT_FECHA_INICIAL, '%m') AS mes, "
			+ "UPPER(MONTHNAME(T1.DT_FECHA_INICIAL)) AS nomMes, "
			+ "T2.VC_MENSAJE_ENVIAR AS mensajeSMS, "
			+ "T1.I_SMS_ENVIAR AS numSmsEnviar, "
			+ "T1.I_SMS_PEND_ENVIAR AS numSmsPendientes, "
			+ "T1.I_SMS_ENVIOS AS numSmsEnvios, "
			+ "CASE WHEN T1.I_ESTATUS_PROCESO = 1 THEN 'POR INICIAR' "
			+ "WHEN T1.I_ESTATUS_PROCESO = 2 THEN 'EN PROCESO' "
			+ "WHEN T1.I_ESTATUS_PROCESO = 3 THEN 'FINALIZADO' "
			+ "WHEN T1.I_ESTATUS_PROCESO = 4 THEN 'CANCELADO' "
			+ "WHEN T1.I_ESTATUS_PROCESO = 5 THEN 'REVISAR' "
			+ "WHEN T1.I_ESTATUS_PROCESO = 6 THEN 'REINICIAR' "
			+ "END AS estatusProceso "
			+ "FROM SIB.TBL_PROCESO_ENVIO_SMS T1 "
			+ "LEFT JOIN SIB.TBL_CONFIG_SMS_MENSUAL T2 ON T1.FK_ID_CONFIG_SMS = T2.PK_ID_CONFIG_SMS_MENSUAL "
			+ "LEFT JOIN SIB.TBL_CONFIG_SMS T3 ON T2.FK_ID_CONFIG_SMS = T3.PK_ID_CONFIG_SMS "
			+ "WHERE T1.I_TP_PROCESO = 2 "
			+ "ORDER BY mes ASC ", nativeQuery = true)
	public List<Map<String, Object>> getAllGestionesSMS();
	
	@Query(value = "SELECT T1.PK_PROCESOID AS idProceso, "
			+ "T1.FK_ID_CONFIG_SMS AS idConfigMensual, "
			+ "DATE_FORMAT(T1.DT_FECHA_INICIAL, '%m') AS mes, "
			+ "UPPER(MONTHNAME(T1.DT_FECHA_INICIAL)) AS nomMes, "
			+ "T2.I_NUM_ENVIOS_SMS AS numSMS, "
			+ "T2.I_IND_LIM_ENVIO_SMS AS limiteSMS, "
			+ "T2.VC_MENSAJE_ENVIAR AS mensaje "
			+ "FROM SIB.TBL_PROCESO_ENVIO_SMS T1 "
			+ "INNER JOIN SIB.TBL_CONFIG_SMS_MENSUAL T2 ON T2.PK_ID_CONFIG_SMS_MENSUAL = T1.FK_ID_CONFIG_SMS "
			+ "INNER JOIN SIB.TBL_CONFIG_SMS T3 ON T3.PK_ID_CONFIG_SMS = T2.FK_ID_CONFIG_SMS "
			+ "WHERE T3.I_TP_PROCESO = 2 "
			+ "AND T1.PK_PROCESOID = :idProcess ", nativeQuery = true)
	public Optional<Map<String, Object>> getCurrentProcessSMS(Integer idProcess);

}
