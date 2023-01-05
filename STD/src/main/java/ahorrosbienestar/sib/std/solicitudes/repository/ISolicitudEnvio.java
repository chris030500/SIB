package ahorrosbienestar.sib.std.solicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;


@Repository
public interface ISolicitudEnvio extends JpaRepository<SolicitudEnvio, Integer>{
	
	@Query(value = "SELECT * FROM SIB.TBL_SOLICITUD_DE_ENVIO WHERE I_ESTATUS = 1", nativeQuery = true)
	public List<SolicitudEnvio> estatusSolicAutorizacion();
	
	//Obtenemos la ultima solicitud por fecha de registro
	@Query(value = "SELECT * FROM SIB.TBL_SOLICITUD_DE_ENVIO WHERE I_NO_SOLICITUD_CORE = :solicitud AND I_ESTATUS = 1 ORDER BY DT_FECHA_REGISTRO DESC LIMIT 1", nativeQuery = true)
	public List<SolicitudEnvio> obtenerUltimaSolicitudFecha(@Param("solicitud") Integer solicitud);
	
	
	
}
