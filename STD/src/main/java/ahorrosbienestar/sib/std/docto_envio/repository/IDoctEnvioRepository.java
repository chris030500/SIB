package ahorrosbienestar.sib.std.docto_envio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;

@Repository
public interface IDoctEnvioRepository extends JpaRepository<DoctEnvio, Integer> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_DOCTO_ENVIO SET I_TOTAL_HOJAS = :totalHojas WHERE PK_ID_DOCTO_ENVIO = :idDoctoEnvio", nativeQuery = true)
	public int totalHojas(@Param("totalHojas") Integer totalHojas, @Param("idDoctoEnvio") Integer idDoctoEnvio);

	@Query(value = "SELECT * FROM SIB.TBL_DOCTO_ENVIO WHERE PK_ID_DOCTO_ENVIO = :idDoctoEnvio", nativeQuery = true)
	public DoctEnvio obtenerDoctoEnvio(@Param("idDoctoEnvio") Integer idDoctoEnvio);

	@Query(value = "SELECT * FROM TBL_DOCTO_ENVIO WHERE FK_SOLICITUD_ENVIO = :envioId AND NOT I_ESTATUS = 3;", nativeQuery = true)
	public List<DoctEnvio> listadoDoctEnvioActivos(@Param("envioId") Integer envioId);
	
	@Query(value = "SELECT DE.* FROM TBL_DOCTO_ENVIO DE "
			+ " JOIN TBL_SOLICITUD_DE_ENVIO SDE ON SDE.PK_ID_SOLICITUD_ENVIO = DE.FK_SOLICITUD_ENVIO "
			+ " WHERE SDE.PK_ID_SOLICITUD_ENVIO = :envioID", nativeQuery = true)
	public List<DoctEnvio> listaDocumentosPorSolicitudEnvio(@Param("envioID") Integer envioID);
}
