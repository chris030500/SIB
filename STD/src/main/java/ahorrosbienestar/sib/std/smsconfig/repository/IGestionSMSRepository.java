package ahorrosbienestar.sib.std.smsconfig.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.smsconfig.model.GestionSMS;

@Repository
public interface IGestionSMSRepository extends JpaRepository<GestionSMS, Long> {
	
	@Modifying
	@Transactional
	@Query(value = "SET lc_time_names = 'es_ES'", nativeQuery = true)
	void setLocaleSpanish();

	
	@Query(value = "SELECT T1.PK_PROCESOID AS pkIdProceso, "
			+ "DATE_FORMAT(T1.DT_FECHA_INICIAL, '%m') AS mes, "
			+ "UPPER(MONTHNAME(T1.DT_FECHA_INICIAL)) AS nomMes, "
			+ "CASE WHEN T1.I_TP_PROCESO = 2 AND T3.I_IND_SOLCRE_CAMPANIA = 2 AND T3.FK_VC_TP_CREDITO = '' THEN '' "
			+ "WHEN T1.I_TP_PROCESO = 1 AND T3.I_IND_SOLCRE_CAMPANIA = 1 AND T3.FK_VC_TP_CREDITO = '' THEN 'CAMPAÑA' "
			+ "ELSE T4.VC_DESCRIPCION END AS tipoCredito, "
			+ "T2.VC_MENSAJE_ENVIAR AS mensajeSMS, "
			+ "CASE WHEN T2.I_IND_LIM_ENVIO_SMS = 1 THEN 'SI' "
			+ "WHEN T2.I_IND_LIM_ENVIO_SMS = 0 THEN 'NO' "
			+ "ELSE '' "
			+ "END AS limiteSMS, "
			+ "T1.I_SMS_ENVIAR AS numSmsEnviar, "
			+ "T1.I_SMS_PEND_ENVIAR AS numSmsPendientes, "
			+ "T1.I_SMS_ENVIOS AS numSmsEnvios, "
			+ "T1.I_ESTATUS_PROCESO AS idEstatus, "
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
			+ "LEFT JOIN SIB.TBL_CAT_TIPO_CREDITO T4 ON T3.FK_VC_TP_CREDITO = T4.PK_VC_TP_CREDITO "
			+ "WHERE T1.I_TP_PROCESO = :tipoProceso "
			+ "AND T3.I_IND_SOLCRE_CAMPANIA = :isCampania "
			+ "AND T3.FK_VC_TP_CREDITO = :tipoCredito "
			+ "ORDER BY mes ASC ", nativeQuery = true)
	public List<Map<String, Object>> getGestionesSMSByTipoCredito(Integer tipoProceso, Integer isCampania, String tipoCredito);
	
	@Query(value = "SELECT T1.PK_PROCESOID AS idProceso, "
			+ "T1.FK_ID_CONFIG_SMS AS idConfigMensual, "
			+ "CASE WHEN T3.I_TP_PROCESO = 2 AND T3.I_IND_SOLCRE_CAMPANIA = 2 AND T3.FK_VC_TP_CREDITO = '' THEN '' "
			+ "WHEN T3.I_TP_PROCESO = 1 AND T3.I_IND_SOLCRE_CAMPANIA = 1 AND T3.FK_VC_TP_CREDITO = '' THEN 'CAMPAÑA' "
			+ "ELSE T4.VC_DESCRIPCION END AS tipoCredito, "
			+ "DATE_FORMAT(T1.DT_FECHA_INICIAL, '%m') AS mes, "
			+ "UPPER(MONTHNAME(T1.DT_FECHA_INICIAL)) AS nomMes, "
			+ "T2.I_NUM_ENVIOS_SMS AS numSMS, "
			+ "T2.I_IND_LIM_ENVIO_SMS AS limiteSMS, "
			+ "T2.VC_MENSAJE_ENVIAR AS mensaje "
			+ "FROM SIB.TBL_PROCESO_ENVIO_SMS T1 "
			+ "INNER JOIN SIB.TBL_CONFIG_SMS_MENSUAL T2 ON T2.PK_ID_CONFIG_SMS_MENSUAL = T1.FK_ID_CONFIG_SMS "
			+ "INNER JOIN SIB.TBL_CONFIG_SMS T3 ON T3.PK_ID_CONFIG_SMS = T2.FK_ID_CONFIG_SMS "
			+ "LEFT JOIN SIB.TBL_CAT_TIPO_CREDITO T4 ON T4.PK_VC_TP_CREDITO = T3.FK_VC_TP_CREDITO "
			+ "WHERE T3.I_TP_PROCESO = :tipoProceso "
			+ "AND T1.PK_PROCESOID = :idProcess ", nativeQuery = true)
	public Optional<Map<String, Object>> getCurrentProcessSMS(Integer tipoProceso, Long idProcess);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_PROCESO_ENVIO_SMS "
			+ "SET I_ESTATUS_PROCESO = 2 "
			+ "WHERE FK_ID_CONFIG_SMS = :idConfigSMSMensual "
			+ "AND I_ESTATUS_PROCESO = 3 ", nativeQuery = true)
	public void updateProcesoSMSByidConfigMensual(Long idConfigSMSMensual);

}
