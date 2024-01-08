package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model.ProcesoGestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface IProcesoGestionRepository extends JpaRepository<ProcesoGestion, Integer>{
	
	@Query(value = "SELECT * FROM SIB.TBL_PROCESO_GESTION_OA", nativeQuery = true)
	public List<ProcesoGestion> obtProcesoGestion();

	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_PROCESO_GESTION_OA "
			+ "SET VC_DIA_INICIO_PROCESO =:dComienzoProceso "
			+ ",VC_DIA_FIN_PROCESO=:dFinalProceso "
			+ ",VC_HORA_INICIO_PROCESO =:hComienzoProceso "
			+ ",VC_HORA_FIN_PROCESO=:hFinalProceso "
			+ ",VC_PERIODO_EJECUCION_PROCESO=:mPeriodoEjecucion "
			+ " WHERE PK_ID_GESTION=:procesoGestionID ", nativeQuery = true)
	public int actParams(@Param("procesoGestionID") Integer procesoGestionID,@Param("dComienzoProceso") String dComienzoProceso,@Param("dFinalProceso") String dFinalProceso,
			@Param("hComienzoProceso") String hComienzoProceso,@Param("hFinalProceso") String hFinalProceso,@Param("mPeriodoEjecucion") String mPeriodoEjecucion);
	
}
