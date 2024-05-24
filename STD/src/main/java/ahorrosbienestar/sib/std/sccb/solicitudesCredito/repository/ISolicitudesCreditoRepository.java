package ahorrosbienestar.sib.std.sccb.solicitudesCredito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.SolicitudesCredito;

@Repository
public interface ISolicitudesCreditoRepository extends JpaRepository<SolicitudesCredito, Integer> {
	@Query(value = "SELECT * "
			+ "FROM TBL_SCCB_SOLICITUDES "
			+ "WHERE I_CREDITO_ID = :idCredito", nativeQuery = true)
	public List<SolicitudesCredito> getSolicitudesByIdCredito(@Param("idCredito") Integer idCredito);
	
	@Query(value = "SELECT * "
			+ "FROM TBL_SCCB_SOLICITUDES "
			+ "WHERE I_NO_CLIENTE = :noCliente", nativeQuery = true)
	public List<SolicitudesCredito> getSolicitudesBynoCliente(@Param("noCliente") Integer noCliente);
}
