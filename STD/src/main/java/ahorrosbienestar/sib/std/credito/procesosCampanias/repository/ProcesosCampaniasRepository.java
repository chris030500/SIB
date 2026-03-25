package ahorrosbienestar.sib.std.credito.procesosCampanias.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.procesosCampanias.dto.ProcesosCampaniasDTO;
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
	
	/*@Query(value = "select t1.pk_id_proceso_campania " +
					",t1.dt_fecha_extraccion " +
					",t4.vc_nombre_usuario " +
					",t2.vc_tipo_campania "  + 
					",t1.dt_fecha_inicio_vigencia " +
					",t1.dt_fecha_fin_vigencia " +
					",t3.vc_estatus " +
					"from SIB.TBL_PROCESO_CAMPANIA t1 " +
					"inner join SIB.TBL_TIPOS_CAMPANIA t2 " +
					"on t1.I_ID_TIPO_CAMPANIA = t2.PK_ID_TIPO_CAMPANIA " +
					"inner join SIB.TBL_ESTATUS_CAMPANIA t3 " +
					"on t3.PK_ID_ESTATUS_CAMPANIA = t1.I_ID_ESTATUS_CAMPANIA " +
					"inner join SIB.TBL_CAT_USUARIOS t4 " +
					"on t4.PK_ID_USUARIO = t1.I_USER_REG" +
					"WHERE I_ID_TIPO_CAMPANIA in ( :tipo) " +
				    "AND (DT_FECHA_EXTRACCION BETWEEN :inicio AND :fin " +
				    "OR DT_FECHA_FIN_VIGENCIA BETWEEN :inicio AND :fin)", nativeQuery = true)
	List<Object[]> listaProcesosCampania(@Param("inicio") LocalDate inicio,
										   @Param("fin") LocalDate fin,
										   @Param("tipo") String tipo);*/
	
	/*@Query(value = "SELECT t1.pk_id_proceso_campania, " +
            "t1.dt_fecha_extraccion, " +
            "t4.vc_nombre_usuario, " +
            "t2.vc_tipo_campania, " +
            "t1.dt_fecha_inicio_vigencia, " +
            "t1.dt_fecha_fin_vigencia, " +
            "t3.vc_estatus " +
            "FROM SIB.TBL_PROCESO_CAMPANIA t1 " +
            "INNER JOIN SIB.TBL_TIPOS_CAMPANIA t2 ON t1.I_ID_TIPO_CAMPANIA = t2.PK_ID_TIPO_CAMPANIA " +
            "INNER JOIN SIB.TBL_ESTATUS_CAMPANIA t3 ON t3.PK_ID_ESTATUS_CAMPANIA = t1.I_ID_ESTATUS_CAMPANIA " +
            "INNER JOIN SIB.TBL_CAT_USUARIOS t4 ON t4.PK_ID_USUARIO = t1.I_USER_REG " +
            "WHERE t1.I_ID_TIPO_CAMPANIA IN (:tipos) " +
            "AND (t1.DT_FECHA_EXTRACCION BETWEEN :inicio AND :fin " +
            "OR t1.DT_FECHA_FIN_VIGENCIA BETWEEN :inicio AND :fin)" +
            "AND t1.I_ID_ESTATUS_CAMPANIA = 1",
    nativeQuery = true)
	List<Object[]> listaProcesosCampania(@Param("inicio") LocalDate inicio,
                                  @Param("fin") LocalDate fin,
                                  @Param("tipos") List<Integer> tipos);*/
	
	@Query(value = "select t.pk_id_proceso_campania\r\n"
			+ "      ,t.dt_fecha_extraccion\r\n"
			+ "      ,t.vc_nombre_usuario\r\n"
			+ "      ,t.vc_tipo_campania\r\n"
			+ "     ,IFNULL(date(t5.dt_fecha_inicio_vigencia), date('1900-01-01')) AS dt_fecha_inicio_vigencia\r\n"
			+ "     ,IFNULL(date(t5.dt_fecha_fin_vigencia), date('1900-01-01')) AS dt_fecha_fin_vigencia\r\n"
			+ "      ,t.vc_estatus\r\n"
			+ "from(\r\n"
			+ "      SELECT min(t1.pk_id_proceso_campania) pk_id_proceso_campania\r\n"
			+ "            ,t2.vc_tipo_campania\r\n"
			+ "            ,t1.vc_codigo_cam\r\n"
			+ "            ,GROUP_CONCAT(CAST(t3.vc_estatus AS CHAR) ORDER BY t3.pk_id_estatus_campania SEPARATOR ', ') AS vc_estatus\r\n"
			+ "            ,min(date(t1.dt_fecha_extraccion)) dt_fecha_extraccion\r\n"
			+ "            ,GROUP_CONCAT(CAST(t4.vc_nombre_usuario AS CHAR) ORDER BY t4.vc_nombre_usuario SEPARATOR ', ') AS vc_nombre_usuario\r\n"
			+ "      FROM SIB.TBL_PROCESO_CAMPANIA t1 \r\n"
			+ "      INNER JOIN SIB.TBL_TIPOS_CAMPANIA t2 \r\n"
			+ "      ON t1.I_ID_TIPO_CAMPANIA = t2.PK_ID_TIPO_CAMPANIA \r\n"
			+ "      INNER JOIN SIB.TBL_ESTATUS_CAMPANIA t3 \r\n"
			+ "      ON t3.PK_ID_ESTATUS_CAMPANIA = t1.I_ID_ESTATUS_CAMPANIA \r\n"
			+ "      INNER JOIN SIB.TBL_CAT_USUARIOS t4 \r\n"
			+ "      ON t4.PK_ID_USUARIO = t1.I_USER_REG \r\n"
			+ "      WHERE t1.I_ID_TIPO_CAMPANIA IN (:tipos)\r\n"
			+ "      AND (date(t1.DT_FECHA_EXTRACCION) BETWEEN :inicio AND :fin\r\n"
			+ "          OR date(t1.DT_FECHA_FIN_VIGENCIA) BETWEEN :inicio AND :fin\r\n"
			+ "          )\r\n"
			+ "      GROUP BY \r\n"
			+ "          t2.vc_tipo_campania\r\n"
			+ "          ,t1.vc_codigo_cam\r\n"
			+ ")t\r\n"
			+ "left join SIB.TBL_PROCESO_CAMPANIA t5\r\n"
			+ "on t.vc_codigo_cam = t5.vc_codigo_cam\r\n"
			+ "and t5.I_ID_ESTATUS_CAMPANIA = 5\r\n"
			+ "order by t.pk_id_proceso_campania",
    nativeQuery = true)
	List<Object[]> listaProcesosCampania(@Param("inicio") LocalDate inicio,
                                  @Param("fin") LocalDate fin,
                                  @Param("tipos") List<Integer> tipos);
	
	
	/*@Query(value = "SELECT * FROM SIB.TBL_PROCESO_CAMPANIA " +
		       "WHERE (DT_FECHA_EXTRACCION BETWEEN :inicio AND :fin " +
		       "OR DT_FECHA_INICIO_VIGENCIA BETWEEN :inicio AND :fin) " +
		       "AND I_ID_TIPO_CAMPANIA IN (:tipos)", nativeQuery = true)
		List<ProcesosCampaniasModel> buscarPorFiltros(
		    @Param("inicio") LocalDate inicio,
		    @Param("fin") LocalDate fin,
		    @Param("tipos") List<Integer> tipos
		);*/
	
	@Query(value = "select SUM(t.cant) AS total "
			+ "from( "
			+ "SELECT COUNT(*) cant "
			+ "FROM SIB.TBL_PROCESO_CAMPANIA t1 "
			+ "INNER JOIN SIB.TBL_TIPOS_CAMPANIA t2 "
			+ "ON t1.I_ID_TIPO_CAMPANIA = t2.PK_ID_TIPO_CAMPANIA "
			+ "WHERE t1.I_ID_ESTATUS_CAMPANIA = 1 "
			+ "AND t1.I_BANDERA_EXTRACCION = 1 "
			+ "AND t1.I_ESTATUS_REG = 1 "
			+ "AND t2.I_TIPO_CAMPANIA = 2 "
			+ "union all "
			+ "SELECT COUNT(*) cant "
			+ "FROM SIB.TBL_PROCESO_CAMPANIA "
			+ "WHERE I_ID_TIPO_CAMPANIA = :tipoCampania "
			+ "and I_ID_ESTATUS_CAMPANIA in(1,2,3,4) "
			+ "AND I_BANDERA_EXTRACCION in(1,2) "
			+ "AND I_ESTATUS_REG = 1 "
			+ "union all "
			+ "SELECT COUNT(*) cant "
			+ "FROM SIB.TBL_PROCESO_CAMPANIA "
			+ "WHERE I_ID_TIPO_CAMPANIA = :tipoCampania "
			+ "and I_ID_ESTATUS_CAMPANIA in(5) "
			+ "AND I_BANDERA_EXTRACCION in(1) "
			+ "AND I_ESTATUS_REG = 1 "
			+ " union all "
			+ " SELECT EXISTS ( "
		    + "    SELECT 1 "
		    + "    FROM SIB.TBL_PROCESO_CAMPANIA t1 "
		    + "    INNER JOIN SIB.TBL_TIPOS_CAMPANIA t2 "
		    + "    ON t1.I_ID_TIPO_CAMPANIA = t2.PK_ID_TIPO_CAMPANIA "
		    + "    WHERE t1.I_ID_TIPO_CAMPANIA = :tipoCampania "
		    + "    AND t2.I_TIPO_CAMPANIA = 1 "
		    + "  ) AS cant "
		    + " union all "
		    + "SELECT COUNT(*) cant"
		    + "FROM SIB.TBL_TIPOS_CAMPANIA "
		    + "WHERE PK_ID_TIPO_CAMPANIA = :tipoCampania "
		    + "AND I_TIPO_CAMPANIA = 2 "
		    + "and I_VENTANA_ANIOS is null "
			+ ")t",
    nativeQuery = true)
	int contarProcesosExtraccion(@Param("tipoCampania") int tipoCampania);
	
	@Query(value = "SELECT COUNT(*) " +
            "FROM SIB.TBL_PROCESO_CAMPANIA " +
            "WHERE I_ID_ESTATUS_CAMPANIA = 4 " +
            "AND I_BANDERA_EXTRACCION = 1 " +
            "AND I_ESTATUS_REG = 1",
    nativeQuery = true)
	int contarProcesosDepuracionBc();
	
	@Query(value = "SELECT COUNT(*) " +
            "FROM SIB.TBL_PROCESO_CAMPANIA " +
            "WHERE I_ID_TIPO_CAMPANIA = :tipoCampania " +
            "AND I_ID_ESTATUS_CAMPANIA = :estatus " +
            "AND I_BANDERA_EXTRACCION = 2 " +
            "AND I_ESTATUS_REG = 1",
       nativeQuery = true)
	int contarProcesosExtraccionFin(@Param("tipoCampania") int tipoCampania,
									@Param("estatus") int estatus);
	
	@Query(value = "select min(t1.pk_id_proceso_campania) "
			+ "from SIB.TBL_PROCESO_CAMPANIA t1 \r\n"
			+ "where t1.i_id_tipo_campania = :tipoCampania "
			+ "and t1.i_estatus_reg = 1 "
			+ "and t1.i_id_estatus_campania = 1 "
			+ "and t1.i_bandera_extraccion = 2 ",
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
	
	@Query(value = "select min(pk_id_proceso_campania) "
			+ "from SIB.TBL_PROCESO_CAMPANIA "
			+ "where i_id_tipo_campania = :tipoCampania "
			+ "and i_estatus_reg = 1 "
			+ "and i_id_estatus_campania = :estatusCampania "
			+ "and i_bandera_extraccion = :banderaCampania",
       nativeQuery = true)
	int procesoCamEstatus(
			@Param("tipoCampania") int tipoCampania,
			@Param("estatusCampania") int estatusCampania,
			@Param("banderaCampania") int banderaCampania);
	
	@Query(value = "select VC_CODIGO_CAM "
			+ "from SIB.TBL_PROCESO_CAMPANIA "
			+ "where pk_id_proceso_campania = :procesoCampania ",
       nativeQuery = true)
	String consultaCodigoCam(@Param("procesoCampania") int procesoCampania);
	
	/*@Query(value = "select t1.PK_ID_PROCESO_CAMPANIA as pk_id_proceso_campania, "
			+ "        t1.DT_FECHA_REGISTRO as dt_fecha_extraccion, "
			+ "        t3.VC_NOMBRE_USUARIO as vc_nombre_usuario, "
			+ "        t4.VC_TIPO_CAMPANIA as vc_tipo_campania, "
			+ "        t2.DT_FECHA_REGISTRO as dt_fecha_inicio_vigencia, "
			+ "        t1.DT_FECHA_FIN_VIGENCIA as dt_fecha_fin_vigencia, "
			+ "        t5.VC_ESTATUS as vc_estatus "
			+ "from SIB.TBL_PROCESO_CAMPANIA t1 "
			+ "inner join SIB.TBL_PROCESO_CAMPANIA t2 "
			+ "on t1.I_ID_TIPO_CAMPANIA = t2.I_ID_TIPO_CAMPANIA "
			+ "inner join SIB.TBL_CAT_USUARIOS t3 "
			+ "on t3.PK_ID_USUARIO = t2.I_USER_REG "
			+ "inner join SIB.TBL_TIPOS_CAMPANIA t4 "
			+ "on t4.PK_ID_TIPO_CAMPANIA = t2.I_ID_TIPO_CAMPANIA "
			+ "inner join SIB.TBL_ESTATUS_CAMPANIA t5 "
			+ "on t5.PK_ID_ESTATUS_CAMPANIA = t2.I_ID_ESTATUS_CAMPANIA "
			+ "where t1.I_ID_TIPO_CAMPANIA = :tipoCampania "
			+ "and t1.VC_CODIGO_CAM = t2.VC_CODIGO_CAM "
			+ "and t1.I_ID_ESTATUS_CAMPANIA = 1 "
			+ "and t2.I_ID_ESTATUS_CAMPANIA = 2 "
			+ "and t1.PK_ID_PROCESO_CAMPANIA = (select IFNULL(max(PK_ID_PROCESO_CAMPANIA),0) "
			+ "                                  from SIB.TBL_PROCESO_CAMPANIA "
			+ "                                  where I_ID_TIPO_CAMPANIA = :tipoCampania "
			+ "                                  and I_ID_ESTATUS_CAMPANIA = 1)",
			nativeQuery = true)
	List<Object[]> listaFechasCampania(@Param("tipoCampania") Integer tipoCampania);*/
	
	@Query(value = "select t1.PK_ID_PROCESO_CAMPANIA as pk_id_proceso_campania, "
			+ "			      t1.DT_FECHA_REGISTRO as dt_fecha_extraccion, "
			+ "			       t3.VC_NOMBRE_USUARIO as vc_nombre_usuario, "
			+ "			        t4.VC_TIPO_CAMPANIA as vc_tipo_campania, "
			+ "         ("
			+ "          SELECT IFNULL(t2.DT_FECHA_REGISTRO, DATE('1900-01-01'))"
			+ "          FROM SIB.TBL_PROCESO_CAMPANIA t2"
			+ "          WHERE t2.I_ID_TIPO_CAMPANIA = t1.I_ID_TIPO_CAMPANIA"
			+ "            AND t2.VC_CODIGO_CAM = t1.VC_CODIGO_CAM"
			+ "            AND t2.I_ID_ESTATUS_CAMPANIA = 2"
			+ "          LIMIT 1"
			+ "        ) AS dt_fecha_inicio_vigencia,"
			+ "			        t1.DT_FECHA_FIN_VIGENCIA as dt_fecha_fin_vigencia, "
			+ "			        t5.VC_ESTATUS as vc_estatus "
			+ "			from SIB.TBL_PROCESO_CAMPANIA t1 "
			+ "			inner join SIB.TBL_CAT_USUARIOS t3 "
			+ "			on t3.PK_ID_USUARIO = t1.I_USER_REG "
			+ "			inner join SIB.TBL_TIPOS_CAMPANIA t4 "
			+ "			on t4.PK_ID_TIPO_CAMPANIA = t1.I_ID_TIPO_CAMPANIA "
			+ "			inner join SIB.TBL_ESTATUS_CAMPANIA t5 "
			+ "			on t5.PK_ID_ESTATUS_CAMPANIA = t1.I_ID_ESTATUS_CAMPANIA "
			+ "			where t1.I_ID_TIPO_CAMPANIA = :tipoCampania"
			+ "			and t1.I_ID_ESTATUS_CAMPANIA = 1"
			+ "			and t1.PK_ID_PROCESO_CAMPANIA = (select IFNULL(max(PK_ID_PROCESO_CAMPANIA),0) "
			+ "			                                  from SIB.TBL_PROCESO_CAMPANIA "
			+ "			                                  where I_ID_TIPO_CAMPANIA = :tipoCampania"
			+ "			                                  and I_ID_ESTATUS_CAMPANIA = 1)",
			nativeQuery = true)
	List<Object[]> listaFechasCampania(@Param("tipoCampania") Integer tipoCampania);
	
	 @Modifying
	    @Transactional
	    @Query(value = "CALL SIB.SP_CAM_DEPURACION_BC(:tipoCampania, :procesoCampania)", nativeQuery = true)
	    void ejecutarDepuracionBC(@Param("tipoCampania") Integer tipoCampania,
	                              @Param("procesoCampania") Integer procesoCampania);
	 
	 @Modifying
	    @Transactional
	    @Query(value = "CALL SIB.SP_DEP_CAM_EXTRACCIONCAMPCRED(:tipoCampania)", nativeQuery = true)
	    void ejecutarArchivoMc(@Param("tipoCampania") Integer tipoCampania);
	 
	 @Modifying
	    @Transactional
	    @Query(value = "CALL SIB.SP_CAM_CANCELAR_PROCESO(:tipoCampania, :estatusCampania, :userReg)", nativeQuery = true)
	    void ejecutarCancelar(@Param("tipoCampania") Integer tipoCampania,
	    		@Param("estatusCampania") Integer estatusCampania,
	    		@Param("userReg") Integer userReg);
	
}
