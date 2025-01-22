package ahorrosbienestar.sib.std.marketing.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.marketing.model.GestionSMS;

@Repository
public interface IGestionSMSRepository extends JpaRepository<GestionSMS, Integer> {
	
	@Modifying
	@Transactional
	@Query(value = "SET lc_time_names = 'es_ES'", nativeQuery = true)
	void setLocaleSpanish();

	
	@Query(value = "SELECT T1.PK_PROCESOID AS pkIdProceso, "
			+ "DATE_FORMAT(T1.DT_FECHA_INICIAL, '%m') AS mes, "
			+ "UPPER(MONTHNAME(T1.DT_FECHA_INICIAL)) AS nomMes, "
			+ "IFNULL(T3.VC_DESCRIPCION, 'CAMPAÑA') AS tipoCredito, "
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
			+ "LEFT JOIN SIB.TBL_CONFIG_SMS T2 ON T1.FK_ID_CONFIG_SMS = T2.PK_ID_CONFIG_SMS "
			+ "LEFT JOIN SIB.TBL_CAT_TIPO_CREDITO T3 ON T2.FK_VC_TP_CREDITO = T3.PK_VC_TP_CREDITO "
			+ "WHERE T1.I_TP_PROCESO = 1 "
			+ "AND T2.FK_VC_TP_CREDITO = :tipoCredito "
			+ "ORDER BY mes ASC ", nativeQuery = true)
	public List<Map<String, Object>> getGestionesSMSByTipoCredito(String tipoCredito);
	
	@Query(value = "SELECT T1.PK_PROCESOID AS idProceso, "
			+ "T3.VC_DESCRIPCION AS tipoCredito, "
			+ "T1.DT_FECHA_INICIAL AS fechaInicial, "
			+ "T1.DT_FECHA_FINAL AS fechaFinal, "
			+ "T1.I_SMS_ENVIAR AS numSMS, "
			+ "T2.I_IND_LIM_ENVIO_SMS AS limiteSMS "
			+ "FROM SIB.TBL_PROCESO_ENVIO_SMS T1 "
			+ "INNER JOIN SIB.TBL_CONFIG_SMS T2 ON T2.PK_ID_CONFIG_SMS = T1.FK_ID_CONFIG_SMS "
			+ "LEFT JOIN SIB.TBL_CAT_TIPO_CREDITO T3 "
			+ "ON T3.PK_VC_TP_CREDITO = T2.FK_VC_TP_CREDITO "
			+ "WHERE T2.I_TP_PROCESO = 1 "
			+ "AND T1.PK_PROCESOID = :idProcess", nativeQuery = true)
	public Optional<Map<String, Object>> getCurrentProcessSMS(Integer idProcess);

}
