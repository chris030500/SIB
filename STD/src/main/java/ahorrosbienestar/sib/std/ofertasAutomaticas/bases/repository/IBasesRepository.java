package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;

@Repository
public interface IBasesRepository extends JpaRepository<Bases, Integer>{
	
	@Query(value = "SELECT * FROM SIB.TBL_BASES_OA where I_ESTATUS_BASE = 1", nativeQuery = true)
	public List<Bases> obtBasesOA();

	@Modifying
	@Transactional
	@Query(value = "UPDATE SIB.TBL_BASES_OA "
			+ "SET VC_DIA_INICIO_PROCESO =:dComienzoProceso "
			+ ",VC_DIA_FIN_PROCESO=:dFinalProceso "
			+ ",VC_HORA_INICIO_PROCESO =:hComienzoProceso "
			+ ",VC_HORA_FIN_PROCESO=:hFinalProceso "
			+ ",VC_PERIODO_EJECUCION_PROCESO=:mPeriodoEjecucion "
			+ ",VC_RUTA_PROCESO=:rutaArchivo "
			+ " WHERE PK_ID_BASE=:basesID ", nativeQuery = true)
	public int actParams(@Param("basesID") Integer basesID,@Param("dComienzoProceso") String dComienzoProceso,@Param("dFinalProceso") String dFinalProceso,@Param("hComienzoProceso") String hComienzoProceso,
			@Param("hFinalProceso") String hFinalProceso, @Param("mPeriodoEjecucion") String mPeriodoEjecucion, @Param("rutaArchivo") String rutaArchivo);

	@Query(value = "SELECT * FROM SIB.TBL_BASES_OA WHERE PK_ID_BASE = :id", nativeQuery = true)
	public Bases obtBase(@Param("id") Integer id);

}
