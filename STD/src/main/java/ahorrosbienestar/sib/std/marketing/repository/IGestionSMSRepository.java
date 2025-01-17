package ahorrosbienestar.sib.std.marketing.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.marketing.model.GestionSMS;

@Repository
public interface IGestionSMSRepository extends JpaRepository<GestionSMS, Integer> {
	
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
			+ "WHERE I_TP_PROCESO = 1 "
			+ "ORDER BY fechaInicial DESC ", nativeQuery = true)
	public List<Map<String, Object>> getAllGestionesSMS();

}
