package ahorrosbienestar.sib.std.sccb.solicitudesCredito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.SolicitudesCredito;

@Repository
public interface ISolicitudesCreditoRepository extends JpaRepository<SolicitudesCredito, Integer> {
	
	@Query(value = "SELECT I_SOLICITUD_ID, I_CREDITO_ID, VC_NOMBRE_CLIENTE, I_NO_CLIENTE, VC_ROL, VC_NOMBRE_CENTRO, "
			+ "VC_ETAPA, VC_ESTATUS, FL_MONTO, DT_FECHA_RECEPCION, DT_FECHA_INICIO_TRAMITE, DT_FECHA_AUTORIZACION, "
			+ "DT_FECHA_DESEMBOLSO "
			+ "FROM TBL_SCCB_SOLICITUDES "
			+ "WHERE I_CREDITO_ID LIKE %:idCredito% "
			+ "AND I_NO_CLIENTE LIKE %:noCliente% "
			+ "AND VC_NOMBRE_CLIENTE LIKE %:nomCliente%", nativeQuery = true)
	public List<SolicitudesCredito> getSolicitudesCredito(
			@Param("idCredito") int idCredito,
			@Param("noCliente") int noCliente,
			@Param("nomCliente") String nomCliente
			);
}
	
