package ahorrosbienestar.sib.std.planAccion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.planAccion.model.PlanAccion;

@Repository
public interface IPlanAccionRepository extends JpaRepository<PlanAccion, Integer> {

	
	

	@Query(value = "SELECT * FROM TBL_PLAN_ACCION WHERE I_OBSERVACION=:idObservacion order by DT_FECHA_INICIO desc ", nativeQuery = true)
	List<PlanAccion> findAllActividades(Integer idObservacion);

}
