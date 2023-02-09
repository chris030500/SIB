package ahorrosbienestar.sib.std.solicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;

@Repository
public interface ISolicitudEnvio extends JpaRepository<SolicitudEnvio, Integer> {

	@Query(value = "SELECT * FROM SIB.TBL_SOLICITUD_DE_ENVIO WHERE I_ESTATUS = 1", nativeQuery = true)
	public List<SolicitudEnvio> estatusSolicAutorizacion();

	// Lista que recupera todas las solicitudes
	
	// Por ID y estatus
	String SOLICITUD_ESTATUS = "SELECT * FROM TBL_SOLICITUD_DE_ENVIO WHERE I_NO_SOLICITUD_CORE = :solicitudID AND I_ESTATUS = :estatus";

	//Por estatus
	String ESTATUS = "SELECT * FROM TBL_SOLICITUD_DE_ENVIO WHERE I_ESTATUS = :estatus";

	@Query(value = SOLICITUD_ESTATUS, nativeQuery = true)
	public List<SolicitudEnvio> solicitudesDisponibles(@Param("solicitudID") Integer solicitudID,
													   @Param("estatus") Integer estatus);
	
	@Query(value = ESTATUS, nativeQuery = true)
	public List<SolicitudEnvio> solicitudesDisponibles(@Param("estatus") Integer estatus);

	// Obtenemos la ultima solicitud por fecha de registro
	@Query(value = "SELECT * FROM SIB.TBL_SOLICITUD_DE_ENVIO WHERE I_NO_SOLICITUD_CORE = :solicitud "
			+ " AND I_ESTATUS = :estatus ORDER BY DT_FECHA_REGISTRO DESC LIMIT 1", nativeQuery = true)
	public SolicitudEnvio obtenerUltimaSolicitudFecha(@Param("solicitud") Integer solicitud,
			@Param("estatus") Integer estatus);

	// Consulta que muestra el modelo de una solicitud de envio por la solicitud
	// core
	@Query(value = "SELECT * FROM SIB.TBL_SOLICITUD_DE_ENVIO WHERE PK_ID_SOLICITUD_ENVIO = :envioID", nativeQuery = true)
	public SolicitudEnvio obtenerSolicitudEnvio(@Param("envioID") Integer envioID);
	
	
	// Consulta que trae todas las solicitudes de un credito
	@Query(value = "SELECT * FROM SIB.TBL_SOLICITUD_DE_ENVIO WHERE I_NO_SOLICITUD_CORE = :solicitudId ORDER BY PK_ID_SOLICITUD_ENVIO DESC", nativeQuery = true)
	public List<SolicitudEnvio> obtenerTodasLasSolicitudes(@Param("solicitudId") Integer solicitudId);

}
