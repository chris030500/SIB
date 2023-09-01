package ahorrosbienestar.sib.std.prevencion_pld.bitacora.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ahorrosbienestar.sib.std.prevencion_pld.bitacora.model.BitacoraPldModel;

@Repository
public interface BitacoraPldRepository extends JpaRepository<BitacoraPldModel, Integer> {
	
	@Query(value = "SELECT * FROM SIB.TBL_BITACORA_QEQ"
			+ " ORDER BY PK_ID_BITACORA DESC;", nativeQuery = true)
	public List<BitacoraPldModel> todoBitacora();

}
