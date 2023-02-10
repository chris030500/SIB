package ahorrosbienestar.sib.std.documentos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.documentos.model.Documento;

@Repository
public interface IDocumentoRepository extends JpaRepository<Documento, Integer> {
	

	@Query(value = "SELECT r.PK_ID_DOCUMENTO, r.VC_NOMBRE_DOC FROM TBL_CAT_DOCUMENTO as r WHERE r.PK_ID_DOCUMENTO = ?", nativeQuery = true)
	public Documento findByIdDocumento(Integer documento);
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_DOCUMENTO WHERE PK_ID_DOCUMENTO = :idDocumento", nativeQuery = true)
	public List<Documento> findByPkIdDocumento(@Param("idDocumento") Integer idDocumento);

	@Query(value = "SELECT COUNT(*) AS NUMERO FROM TBL_CAT_DOCUMENTO  WHERE TRIM(VC_NOMBRE_DOC) = :nombreDocumento ", nativeQuery = true)
	public int existeDocumento(@Param("nombreDocumento") String nombreDocumento);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CAT_DOCUMENTO SET I_ESTATUS = 2, DT_FECHA_BAJA = now() WHERE PK_ID_DOCUMENTO = :id_documento", nativeQuery = true)
	public int bajaDocumento(Integer id_documento);

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CAT_DOCUMENTO SET I_ESTATUS = 1, DT_FECHA_BAJA = null WHERE PK_ID_DOCUMENTO = :id_documento", nativeQuery = true)
	public int reactivarDocumento(Integer id_documento);

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_CAT_DOCUMENTO SET VC_NOMBRE_DOC = :nombreDocumento WHERE PK_ID_DOCUMENTO = :id_documento", nativeQuery = true)
	public int editarDocumento(@Param("id_documento") Integer id_documento, @Param("nombreDocumento") String nombreDocumento);

	@Query(value = "SELECT * FROM TBL_CAT_DOCUMENTO  WHERE I_ESTATUS = 1", nativeQuery = true)
	public List<Documento> todosDocumentos();

}
