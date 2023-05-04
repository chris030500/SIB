package ahorrosbienestar.sib.std.folios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.folios.model.Observaciones;

@Repository
public interface IObservacionRepository  extends JpaRepository<Observaciones, Integer>{


	@Query(value = "SELECT * FROM TBL_OBSERVACIONESXFOLIO WHERE I_FOLIO=:idFolio order by PK_ID_OBSERVACION desc ", nativeQuery = true)
	List<Observaciones> findAllFolio(Integer idFolio);


	
}
