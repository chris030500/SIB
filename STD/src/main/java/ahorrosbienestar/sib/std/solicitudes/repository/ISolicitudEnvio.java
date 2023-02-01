package ahorrosbienestar.sib.std.solicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
@Repository
public interface ISolicitudEnvio extends JpaRepository<SolicitudEnvio, Integer>{

	
	@Query(value = "SELECT * FROM TBL_SOLICITUD_DE_ENVIO AS u WHERE u.I_NO_SOLICITUD_CORE = :solicitud", nativeQuery = true)
	public List<SolicitudEnvio> findBySolicitud(@Param("solicitud") Integer solicitud);

	@Query(value = "SELECT * FROM TBL_SOLICITUD_DE_ENVIO AS u WHERE u.DT_FECHA_REGISTRO BETWEEN :fechaInicio AND :fechaFin", nativeQuery = true)
	public List<SolicitudEnvio> findByRangoFechas(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);

}
