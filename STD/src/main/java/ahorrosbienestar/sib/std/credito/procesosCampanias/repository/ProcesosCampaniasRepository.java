package ahorrosbienestar.sib.std.credito.procesosCampanias.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	@Query(value = "SELECT COUNT(*) " +
            "FROM SIB.TBL_PROCESO_CAMPANIA " +
            "WHERE I_ID_ESTATUS_CAMPANIA = 1 " +
            "AND I_BANDERA_EXTRACCION = 1 " +
            "AND I_ESTATUS_REG = 1",
    nativeQuery = true)
	int contarProcesosExtraccion();
	
	@Query(value = "SELECT COUNT(*) " +
            "FROM SIB.TBL_PROCESO_CAMPANIA " +
            "WHERE I_ID_TIPO_CAMPANIA = :tipoCampania " +
            "AND I_ID_ESTATUS_CAMPANIA = 1 " +
            "AND I_BANDERA_EXTRACCION = 2 " +
            "AND I_ESTATUS_REG = 1",
       nativeQuery = true)
	int contarProcesosExtraccionFin(@Param("tipoCampania") int tipoCampania);
	
	@Query(value = "select min(pk_id_proceso_campania) "
			+ "from SIB.TBL_PROCESO_CAMPANIA "
			+ "where i_id_tipo_campania = :tipoCampania "
			+ "and i_estatus_reg = 1 "
			+ "and i_id_estatus_campania = 1 "
			+ "and i_bandera_extraccion = 2",
       nativeQuery = true)
	int reporteBuroCredito(@Param("tipoCampania") int tipoCampania);
	
	@Modifying
	@Transactional // Necesario para ejecutar updates
	@Query(value = "UPDATE SIB.TBL_PROCESO_CAMPANIA SET "
		+ "I_ESTATUS_REG = :i_estatus_reg "
        + "WHERE PK_ID_PROCESO_CAMPANIA = :pk_id_proceso_campania", nativeQuery = true)
	int actualizarProcesoCampania(
	    @Param("pk_id_proceso_campania") Integer pk_id_proceso_campania,
	    @Param("i_estatus_reg") Integer i_estatus_reg
	);
	
}
