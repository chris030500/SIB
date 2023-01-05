package ahorrosbienestar.sib.std.documentos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.documentos.model.Documento;
import ahorrosbienestar.sib.std.roles.model.Rol;

@Repository
public interface IDocumentoRepository extends JpaRepository<Documento, Integer> {
	

	@Query(value = "SELECT r.PK_ID_DOCUMENTO, r.VC_NOMBRE_DOC FROM TBL_CAT_DOCUMENTO as r WHERE r.PK_ID_DOCUMENTO = ?1", nativeQuery = true)
	public Documento findByIdDocumento(Integer documento);
	
	@Query(value = "SELECT * FROM SIB.TBL_CAT_DOCUMENTO WHERE PK_ID_DOCUMENTO = :idDocumento", nativeQuery = true)
	public List<Documento> findByPkIdDocumento(@Param("idDocumento") Integer idDocumento);

}
