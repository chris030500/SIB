package ahorrosbienestar.sib.std.filesCSO.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ahorrosbienestar.sib.std.filesCSO.model.ArchivosFolios;

public interface IArchivosFoliosRepository extends JpaRepository<ArchivosFolios, Integer> {

	@Query(value = "SELECT * FROM SIB.TBL_ARCHIVOS_FOLIOS  WHERE I_FOLIO = :idFolio ", nativeQuery = true)
	public List<ArchivosFolios> obtArchivos(@Param("idFolio") Integer idFolio);

}
