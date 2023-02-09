package ahorrosbienestar.sib.std.solicitudes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;

@Repository
public interface ISolicitudEnvio extends JpaRepository<SolicitudEnvio, Integer>{
	
	@Query(value = "SELECT * FROM SIB.TBL_SOLICITUD_DE_ENVIO WHERE I_ESTATUS = 1 \r\n"
			+ "OR I_ESTATUS = 2 ORDER BY I_ESTATUS DESC, DT_FECHA_REGISTRO DESC", nativeQuery = true)
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
	
	String OBTENER_ULTIMA_FECHA_SOLICITUD = "SELECT * FROM SIB.TBL_SOLICITUD_DE_ENVIO WHERE I_NO_SOLICITUD_CORE = :solicitud "
										  + " AND I_ESTATUS = 1 ORDER BY DT_FECHA_REGISTRO DESC LIMIT 1";
	
	String OBTENER_ULTIMA_FECHA_ESTATUS = "SELECT * FROM SIB.TBL_SOLICITUD_DE_ENVIO WHERE I_NO_SOLICITUD_CORE = :solicitud "
										+ " AND I_ESTATUS = :estatus ORDER BY DT_FECHA_REGISTRO DESC LIMIT 1";
	

	@Query(value = OBTENER_ULTIMA_FECHA_SOLICITUD, nativeQuery = true)
	public SolicitudEnvio obtenerUltimaSolicitudFecha(@Param("solicitud") Integer solicitud);
	
	@Query(value = OBTENER_ULTIMA_FECHA_ESTATUS, nativeQuery = true)
	public SolicitudEnvio obtenerUltimaSolicitudFecha(@Param("solicitud") Integer solicitud,
													  @Param("estatus") Integer estatus);

	// Consulta que muestra el modelo de una solicitud de envio por la solicitud
	// core
	@Query(value = "SELECT * FROM SIB.TBL_SOLICITUD_DE_ENVIO WHERE PK_ID_SOLICITUD_ENVIO = :envioID", nativeQuery = true)
	public SolicitudEnvio obtenerSolicitudEnvio(@Param("envioID") Integer envioID);
	
	//Validar la solicitud de envio despues de realizar la validacion de documentos
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_SOLICITUD_DE_ENVIO SET I_ESTATUS = :estatus WHERE PK_ID_SOLICITUD_ENVIO = :idSolicitud", nativeQuery = true)
	public int validarSoliEnvio(@Param("estatus") Integer estatus, @Param("idSolicitud") Integer idSolicitud);
	
	//Agregar el requisitionID
	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_SOLICITUD_DE_ENVIO SET VC_REQUISITION_ID = :requisitionId WHERE PK_ID_SOLICITUD_ENVIO = :soliEnvio", nativeQuery = true)
	public int agregarRequisitionId(@Param("requisitionId") String requisitionId, @Param("soliEnvio") Integer soliEnvio);
	
	
	// Consulta que trae todas las solicitudes de un credito
	@Query(value = "SELECT * FROM SIB.TBL_SOLICITUD_DE_ENVIO WHERE I_NO_SOLICITUD_CORE = :solicitudId ORDER BY PK_ID_SOLICITUD_ENVIO DESC", nativeQuery = true)
	public List<SolicitudEnvio> obtenerTodasLasSolicitudes(@Param("solicitudId") Integer solicitudId);

}
