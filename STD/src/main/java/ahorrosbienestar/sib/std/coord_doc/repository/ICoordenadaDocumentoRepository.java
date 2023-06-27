package ahorrosbienestar.sib.std.coord_doc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.coord_doc.model.CoordenadaDocumento;

@Repository
public interface ICoordenadaDocumentoRepository extends JpaRepository<CoordenadaDocumento, Integer>{
	
	@Query(value = "SELECT * FROM SIB.TBL_COORD_DOC WHERE FK_ID_DOCUMENTO = :idDocumento", nativeQuery = true)
	public List<CoordenadaDocumento> obtenerCoordenadaDocto(@Param("idDocumento") Integer idDocumento);
}
