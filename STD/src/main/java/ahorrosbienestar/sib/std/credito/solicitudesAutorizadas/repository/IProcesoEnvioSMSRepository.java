package ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.model.ProcesoEnvioSMS;

public interface IProcesoEnvioSMSRepository extends JpaRepository<ProcesoEnvioSMS, Integer> {
	
	@Query(value = "SELECT PK_PROCESOID AS pkIdProceso, "
			+ "DATE_FORMAT(DT_FECHA_INICIAL, '%d-%m-%Y') AS fechaInicial, "
			+ "DATE_FORMAT(DT_FECHA_FINAL, '%d-%m-%Y') AS fechaFinal, "
			+ "I_SMS_ENVIAR AS numSmsEnviar, I_SMS_PEND_ENVIAR AS numSmsPendientes, "
			+ "I_SMS_ENVIOS AS numSmsEnvios, "
			+ "CASE WHEN I_ESTATUS_PROCESO = 1 THEN 'POR INICIAR' "
			+ "WHEN I_ESTATUS_PROCESO = 2 THEN 'EN PROCESO' "
			+ "WHEN I_ESTATUS_PROCESO = 3 THEN 'FINALIZADO' "
			+ "WHEN I_ESTATUS_PROCESO = 4 THEN 'CANCELADO' "
			+ "WHEN I_ESTATUS_PROCESO = 5 THEN 'REVISAR' "
			+ "WHEN I_ESTATUS_PROCESO = 6 THEN 'REINICIAR' "
			+ "END AS estatusProceso "
			+ "FROM SIB.TBL_PROCESO_ENVIO_SMS "
			+ "WHERE I_TP_PROCESO = 2 "
			+ "ORDER BY fechaInicial DESC ", nativeQuery = true)
	public List<Map<String, Object>> getAllGestionesSMS();
	
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
			+ "WHERE T2.I_TP_PROCESO = 2 "
			+ "AND T1.PK_PROCESOID = :idProcess", nativeQuery = true)
	public Optional<Map<String, Object>> getCurrentProcessSMS(Integer idProcess);

}
