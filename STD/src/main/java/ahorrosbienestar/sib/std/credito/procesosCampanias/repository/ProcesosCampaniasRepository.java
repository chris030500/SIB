package ahorrosbienestar.sib.std.credito.procesosCampanias.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.procesosCampanias.model.ProcesosCampaniasModel;

@Repository

public interface ProcesosCampaniasRepository extends JpaRepository<ProcesosCampaniasModel, Integer> {

	@Query(value = "SELECT * FROM SIB.TBL_PROCESO_CAMPANIA " +
		       "WHERE I_ID_TIPO_CAMPANIA in ( :tipo) " +
		       "AND (DT_FECHA_EXTRACCION BETWEEN :inicio AND :fin " +
		       "OR DT_FECHA_INICIO_VIGENCIA BETWEEN :inicio AND :fin)", nativeQuery = true)
		List<ProcesosCampaniasModel> buscarPorFiltros(
		   @Param("inicio") LocalDate inicio,
		   @Param("fin") LocalDate fin,
		   @Param("tipo") String tipo
		);
	
	
	/*@Query(value = "SELECT * FROM SIB.TBL_PROCESO_CAMPANIA " +
		       "WHERE (DT_FECHA_EXTRACCION BETWEEN :inicio AND :fin " +
		       "OR DT_FECHA_INICIO_VIGENCIA BETWEEN :inicio AND :fin) " +
		       "AND I_ID_TIPO_CAMPANIA IN (:tipos)", nativeQuery = true)
		List<ProcesosCampaniasModel> buscarPorFiltros(
		    @Param("inicio") LocalDate inicio,
		    @Param("fin") LocalDate fin,
		    @Param("tipos") List<Integer> tipos
		);*/
	
}
