package ahorrosbienestar.sib.std.sccb.solicitudesCredito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.SolicitudesCredito;

@Repository
public interface ISolicitudesCreditoRepository extends JpaRepository<SolicitudesCredito, Integer> {
	
	@Query(value = "SELECT T1.*, T2.DT_FECHA_INICIO_TRAMITE AS DT_INICIO_TRAMITE "
			+ "FROM TBL_SCCB_SOLICITUDES T1 "
			+ "JOIN TBL_SCCB_DATOS_SOLICITUD T2 "
			+ "ON T2.I_SOLICITUD_ID = T1.I_SOLICITUD_ID "
			+ "WHERE T1.I_CREDITO_ID = :idCredito "
			+ "ORDER BY T1.I_SOLICITUD_ID ", nativeQuery = true)
	public List<SolicitudesCredito> getByIdCreditoSolicitudes(@Param("idCredito") Integer idCredito);
	
	@Query(value = "SELECT * "
			+ "FROM TBL_SCCB_SOLICITUDES "
			+ "WHERE I_NO_CLIENTE = :noCliente ", nativeQuery = true)
	public List<SolicitudesCredito> getByNoClienteSolicitudes(@Param("noCliente") Integer noCliente);
	
	@Query(value = "SELECT * "
			+ "FROM TBL_SCCB_SOLICITUDES "
			+ "WHERE VC_NOMBRE_CLIENTE = :nomCliente ", nativeQuery = true)
	public List<SolicitudesCredito> getByNomClienteSolicitudes(@Param("nomCliente") String nomCliente);
}
