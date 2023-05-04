package ahorrosbienestar.sib.std.folios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.folios.model.Folios;

@Repository
public interface IFolioRepository extends JpaRepository<Folios, Integer>{

	@Query(value = "SELECT COUNT(*) AS NUMERO FROM SIB.TBL_FOLIOS  WHERE I_FOLIO = :noficio ", nativeQuery = true)
	public int existeFolio(@Param("noficio") Integer noficio);
}
