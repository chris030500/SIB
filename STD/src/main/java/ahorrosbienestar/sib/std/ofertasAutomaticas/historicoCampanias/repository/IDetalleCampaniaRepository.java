package ahorrosbienestar.sib.std.ofertasAutomaticas.historicoCampanias.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.ofertasAutomaticas.historicoCampanias.model.DetalleCampania;

@Repository
public interface IDetalleCampaniaRepository extends JpaRepository<DetalleCampania, Integer>{
	
	@Query(value = "SELECT * FROM SIB.TBL_DETALLE_CAMPANIA WHERE I_TIPO_CAMPANIA_ID in (1,2)", nativeQuery = true)
	List<DetalleCampania> obtDetalleVIP();
	
	@Query(value = "SELECT * FROM SIB.TBL_DETALLE_CAMPANIA WHERE I_TIPO_CAMPANIA_ID = 5", nativeQuery = true)
	List<DetalleCampania> obtDetalleProspector();
	
	@Query(value = "SELECT * FROM SIB.TBL_DETALLE_CAMPANIA WHERE I_TIPO_CAMPANIA_ID = 4", nativeQuery = true)
	List<DetalleCampania> obtDetalleFidNom();
	
	@Query(value = "SELECT * FROM SIB.TBL_DETALLE_CAMPANIA WHERE I_TIPO_CAMPANIA_ID = 3", nativeQuery = true)
	List<DetalleCampania> obtDetalleFidRen();
}
