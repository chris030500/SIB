package ahorrosbienestar.sib.std.smsconfig.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.smsconfig.model.DetalleGestionSMS;

@Repository
public interface IDetalleGestionSMSRepository extends JpaRepository<DetalleGestionSMS, Long> {
	
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_DET_PROCESO_ENVIO_SMS "
			+ "SET VC_ENVIAR_MSN = :enviarSMS "
			+ "WHERE FK_PROCESOID = :idProceso "
			+ "AND I_ESTATUS_ENVIO IS NULL "
			+ "AND DT_FECHA_RESPUESTA IS NULL ", nativeQuery = true)
	public void updateEnviarMensaje(Long idProceso, String enviarSMS);

}
