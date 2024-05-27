package ahorrosbienestar.sib.std.sccb.solicitudesCredito.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.DatosSolicitudCredito;

@Repository
public interface IDatosSolicitudCreditoRepository extends JpaRepository<DatosSolicitudCredito, Integer> {
	
	@Query(value = "SELECT * "
			+ "FROM TBL_SCCB_DATOS_SOLICITUD "
			+ "WHERE I_SOLICITUD_ID = :idSolicitud ", nativeQuery = true)
	public Optional<DatosSolicitudCredito> getDatosSolicitudCredito(@Param("idSolicitud") Integer idSolicitud);
	
}
