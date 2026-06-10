package ahorrosbienestar.sib.std.credito.tiposCampanias.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.tiposCampanias.model.TiposCampaniasModel;

 
@Repository

public interface TiposCampaniasRepository extends JpaRepository<TiposCampaniasModel, Integer> {
	 
	//@Query(value = "SELECT VC_TIPO_CAMPANIA FROM SIB.TBL_TIPOS_CAMPANIA WHERE VC_TIPO_CAMPANIA LIKE %:valor%", nativeQuery = true)
	@Query(value = "SELECT PK_ID_TIPO_CAMPANIA, CONCAT(PK_ID_TIPO_CAMPANIA, ' - ', VC_TIPO_CAMPANIA) AS VC_TIPO_CAMPANIA"
			+ " FROM SIB.TBL_TIPOS_CAMPANIA"
			+ " WHERE I_TIPO_CAMPANIA = 2"
			+ " AND CONCAT(PK_ID_TIPO_CAMPANIA, ' - ', VC_TIPO_CAMPANIA) LIKE %:valor%", nativeQuery = true)
	List<Object[]> listaCampanias(@Param("valor") String valor);
	
	@Query(value = "SELECT * FROM SIB.TBL_TIPOS_CAMPANIA WHERE PK_ID_TIPO_CAMPANIA = :pk_id_tipo_campania", nativeQuery = true)
	TiposCampaniasModel mostrarCampania(@Param("pk_id_tipo_campania") Integer pk_id_tipo_campania);
	
	//@Query(value = "SELECT VC_TIPO_CAMPANIA FROM SIB.TBL_TIPOS_CAMPANIA WHERE VC_TIPO_CAMPANIA LIKE %:valor%", nativeQuery = true)
	//public List<TiposCampaniasModel> listaCampanias(@Param("valor") String valor);
	
	@Query(value = "SELECT PK_ID_TIPO_CAMPANIA, VC_TIPO_CAMPANIA FROM SIB.TBL_TIPOS_CAMPANIA WHERE I_ESTATUS = :valor", nativeQuery = true)
	List<TiposCampaniasModel> listaTiposCampanias(@Param("valor") Integer valor);	

	@Query(value = "SELECT MAX(I_VIGENCIA_CAMPANIA) FROM SIB.TBL_TIPOS_CAMPANIA WHERE I_ESTATUS = 1", nativeQuery = true)
	Integer obtenerMaximaVigenciaCampaniaActiva();
	
	@Modifying
	@Transactional // Necesario para ejecutar updates
	@Query(value = "UPDATE SIB.TBL_TIPOS_CAMPANIA SET "
		+ "I_TIPO_CAMPANIA = :i_tipo_campania, "
        + "I_VENTANA_ANIOS = :i_ventana_anios, "
        + "I_VENTANA_ANIOS_MAYOR = :i_ventana_anios_mayor, "
        + "I_EDAD_MAXIMA = :i_edad_maxima, "
        + "I_EDAD_MINIMA = :i_edad_minima, "
        + "D_MONTO_MINIMO = :d_monto_minimo, "
        + "D_MONTO_MAXIMO = :d_monto_maximo, "
        + "D_PORC_CUBIERTO_CAPITAL = :d_porc_cubierto_capital, "
        + "I_DIAS_GRACIA = :i_dias_gracia, "
        + "I_DIAS_MORA = :i_dias_mora, "
        + "I_PLAZO_MAXIMO_ANIOS = :i_plazo_maximo_anios, "
        + "I_MINIMO_VIDA_CREDITO = :i_minimo_vida_credito, "
        + "I_VIGENCIA_BC = :i_vigencia_bc, "
        + "I_VIGENCIA_CAMPANIA = :i_vigencia_campania, "
        + "I_CREDITOS_LIQUIDADOS = :i_creditos_liquidados, "
        + "VC_ES_RENOVACION = :vc_es_renovacion, "
        + "I_CONVIVENCIA = :i_convivencia, "
        + "VC_PRODUCTOS = :vc_productos, "
        + "I_PROD_CREDITO_ID_OFERTA = :i_prod_credito_id_oferta, "
        + "D_PORC_INC_OFERTA = :d_porc_inc_oferta, "
        + "D_TASA_ORDINARIA_OFERTA = :d_tasa_ordinaria_oferta, "
        + "I_ACTIVA_OFERTA = :i_activa_oferta, "
        + "D_MONTO_MAXIMO_OFERTA = :d_monto_maximo_oferta, "
        + "I_MINIMO_VIDA_CREDITO_R = :i_minimo_vida_credito_r, "
        + "DT_FECHA_REGISTRO = :dt_fecha_registro, "
        + "I_ESTATUS = :i_estatus, "
        + "I_TIPO_BURO = :i_tipo_buro "
        //+ "I_USER_REG = :i_user_reg "
        + "WHERE PK_ID_TIPO_CAMPANIA = :pk_id_tipo_campania", nativeQuery = true)
	int actualizarCampania(
	    @Param("pk_id_tipo_campania") Integer pk_id_tipo_campania,
	    @Param("i_tipo_campania") Integer i_tipo_campania,
	    @Param("i_ventana_anios") Integer i_ventana_anios,
	    @Param("i_ventana_anios_mayor") Integer i_ventana_anios_mayor,
	    @Param("i_edad_maxima") Integer i_edad_maxima,
	    @Param("i_edad_minima") Integer i_edad_minima,
	    @Param("d_monto_minimo") Double d_monto_minimo,
	    @Param("d_monto_maximo") Double d_monto_maximo,
	    @Param("d_porc_cubierto_capital") Double d_porc_cubierto_capital,
	    @Param("i_dias_gracia") Integer i_dias_gracia,
	    @Param("i_dias_mora") Integer i_dias_mora,
	    @Param("i_plazo_maximo_anios") Integer i_plazo_maximo_anios,
	    @Param("i_minimo_vida_credito") Integer i_minimo_vida_credito,
	    @Param("i_vigencia_bc") Integer i_vigencia_bc,
	    @Param("i_vigencia_campania") Integer i_vigencia_campania,
	    @Param("i_creditos_liquidados") Integer i_creditos_liquidados,
	    @Param("vc_es_renovacion") String vc_es_renovacion,
	    @Param("i_convivencia") Integer i_convivencia,
	    @Param("vc_productos") String vc_productos,
	    @Param("i_prod_credito_id_oferta") Integer i_prod_credito_id_oferta,
	    @Param("d_porc_inc_oferta") Double d_proc_inc_oferta,
	    @Param("d_tasa_ordinaria_oferta") Double d_tasa_ordinaria_oferta,
	    @Param("i_activa_oferta") Integer i_activa_oferta,
	    @Param("d_monto_maximo_oferta") Double d_monto_maximo_oferta,
	    @Param("i_minimo_vida_credito_r") Integer i_minimo_vida_credito_r,
	    @Param("i_estatus") Integer i_estatus,
	    @Param("dt_fecha_registro") LocalDate dt_fecha_registro,
	    @Param("i_tipo_buro") Integer i_tipo_buro
	    //@Param("i_user_reg") Integer i_user_reg
	);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO SIB.TBL_TIPOS_CAMPANIA ("
	        + "VC_TIPO_CAMPANIA, I_TIPO_CAMPANIA, I_VENTANA_ANIOS, I_VENTANA_ANIOS_MAYOR, I_EDAD_MAXIMA, I_EDAD_MINIMA, D_MONTO_MINIMO, D_MONTO_MAXIMO, "
	        + "D_PORC_CUBIERTO_CAPITAL, I_DIAS_GRACIA, I_DIAS_MORA, "
	        + "I_PLAZO_MAXIMO_ANIOS, I_MINIMO_VIDA_CREDITO, I_VIGENCIA_BC, I_VIGENCIA_CAMPANIA, "
	        + "I_CREDITOS_LIQUIDADOS, VC_ES_RENOVACION, I_CONVIVENCIA, VC_PRODUCTOS, "
	        + "I_PROD_CREDITO_ID_OFERTA, D_PORC_INC_OFERTA, "
	        + "D_TASA_ORDINARIA_OFERTA, I_ACTIVA_OFERTA, D_MONTO_MAXIMO_OFERTA, "
	        +"I_MINIMO_VIDA_CREDITO_R , I_ESTATUS, DT_FECHA_REGISTRO, I_TIPO_BURO"
	        + ") VALUES ("
	        + ":vc_tipo_campania, :i_tipo_campania, :i_ventana_anios, :i_ventana_anios_mayor, :i_edad_maxima, :i_edad_minima, :d_monto_minimo, :d_monto_maximo, "
	        + ":i_mora_maxima, :d_porc_cubierto_capital, :i_dias_gracia, :i_dias_mora, "
	        + ":i_plazo_maximo_anios, :i_minimo_vida_credito, :i_vigencia_bc, :i_vigencia_campania, "
	        + ":i_creditos_liquidados, :vc_es_renovacion, :i_convivencia, :vc_productos, "
	        + ":i_prod_credito_id_oferta, :d_porc_inc_oferta, :vc_es_renovacion_oferta, "
	        + ":i_vigencia_oferta, :d_tasa_ordinaria_oferta, :i_activa_oferta, :d_monto_maximo_oferta, :i_minimo_vida_credito_r, "
	        + ":i_estatus, :dt_fecha_registro, :i_tipo_buro "
	        + ")", nativeQuery = true)
	int insertarCampania(
			@Param("vc_tipo_campania") String vc_tipo_campania,
			@Param("i_tipo_campania") Integer i_tipo_campania,
	        @Param("i_ventana_anios") Integer i_ventana_anios,
	        @Param("i_ventana_anios_mayor") Integer i_ventana_anios_mayor,
	        @Param("i_edad_maxima") Integer i_edad_maxima,
	        @Param("i_edad_minima") Integer i_edad_minima,
	        @Param("d_monto_minimo") Double d_monto_minimo,
	        @Param("d_monto_maximo") Double d_monto_maximo,
	        @Param("d_porc_cubierto_capital") Double d_porc_cubierto_capital,
	        @Param("i_dias_gracia") Integer i_dias_gracia,
	        @Param("i_dias_mora") Integer i_dias_mora,
	        @Param("i_plazo_maximo_anios") Integer i_plazo_maximo_anios,
	        @Param("i_minimo_vida_credito") Integer i_minimo_vida_credito,
	        @Param("i_vigencia_bc") Integer i_vigencia_bc,
	        @Param("i_vigencia_campania") Integer i_vigencia_campania,
	        @Param("i_creditos_liquidados") Integer i_creditos_liquidados,
	        @Param("vc_es_renovacion") String vc_es_renovacion,
	        @Param("i_convivencia") Integer i_convivencia,
	        @Param("vc_productos") String vc_productos,
	        @Param("i_prod_credito_id_oferta") Integer i_prod_credito_id_oferta,
	        @Param("d_porc_inc_oferta") Double d_porc_inc_oferta,
	        @Param("d_tasa_ordinaria_oferta") Double d_tasa_ordinaria_oferta,
	        @Param("i_activa_oferta") Integer i_activa_oferta,
	        @Param("d_monto_maximo_oferta") Double d_monto_maximo_oferta,
	        @Param("i_minimo_vida_credito_r") Integer i_minimo_vida_credito_r,
	        @Param("i_estatus") Integer i_estatus,
	        @Param("dt_fecha_registro") LocalDate dt_fecha_registro,
	        @Param("i_tipo_buro") Integer i_tipo_buro
	);


	
	@Query(value = "SELECT PK_ID_TIPO_CAMPANIA, CONCAT(PK_ID_TIPO_CAMPANIA, ' - ', VC_TIPO_CAMPANIA) AS VC_TIPO_CAMPANIA"
			+ " FROM SIB.TBL_TIPOS_CAMPANIA"
			+ " WHERE I_ESTATUS = 1"
			//+ " AND VC_TIPO_CAMPANIA LIKE %:valorBusqueda%", nativeQuery = true)
			+ " AND CONCAT(PK_ID_TIPO_CAMPANIA, ' - ', VC_TIPO_CAMPANIA) LIKE %:valorBusqueda%", nativeQuery = true)
	List<Object[]> listaCampaniasActivas(@Param("valorBusqueda") String valorBusqueda);
	
	@Query(value = "SELECT PK_ID_TIPO_CAMPANIA, CONCAT(PK_ID_TIPO_CAMPANIA, ' - ', VC_TIPO_CAMPANIA) AS VC_TIPO_CAMPANIA"
			+ " FROM SIB.TBL_TIPOS_CAMPANIA"
			+ " WHERE I_TIPO_CAMPANIA=1"
			//+ " AND VC_TIPO_CAMPANIA LIKE %:valorBusqueda%", nativeQuery = true)
			+ " AND CONCAT(PK_ID_TIPO_CAMPANIA, ' - ', VC_TIPO_CAMPANIA) LIKE %:valorBusqueda%", nativeQuery = true)
	List<Object[]> buscarCampaniasTemporales(@Param("valorBusqueda") String valorBusqueda);
	
	@Query(value = "SELECT PK_ID_TIPO_CAMPANIA, VC_TIPO_CAMPANIA "
			+ " FROM SIB.TBL_TIPOS_CAMPANIA"
			+ " WHERE I_TIPO_CAMPANIA = 2"
			+ " AND I_ESTATUS = 1 ", nativeQuery = true)
	List<Object[]> listaCampaniasRecurrentes();


	@Query(value = "SELECT IFNULL(COUNT(*), 0) as numero FROM SIB.TBL_TIPOS_CAMPANIA WHERE UPPER(VC_TIPO_CAMPANIA) LIKE UPPER(CONCAT('%', :nombreCampania, '%')) "
			+ "and PK_ID_TIPO_CAMPANIA <> :idcampania", nativeQuery = true)
	int existeNombre(String nombreCampania, int idcampania);

	@Query(value = "SELECT t1.PK_ID_TIPO_CAMPANIA AS idCampaniaSeleccionada "
	        + ",t1.VC_TIPO_CAMPANIA AS nombreCampania "
	        + ",t1.I_VIGENCIA_CAMPANIA AS vigenciaMeses "
	        + ",DATE_FORMAT(NULLIF(t2.DT_FECHA_REGISTRO,'1900-01-01'),'%Y-%m-%d') AS fechaRegistro "
	        + ",DATE_FORMAT(NULLIF(t2.DT_FECHA_ENVIO_MC,'1900-01-01'),'%Y-%m-%d') AS fechaEnvioMC "
	        + ",DATE_FORMAT(NULLIF(t2.DT_FECHA_FIN_VIGENCIA,'1900-01-01'),'%Y-%m-%d') AS fechaFin "
	        + ",t1.I_ESTATUS AS estatusCampania "
	        + ",CASE WHEN t1.I_TIPO_CAMPANIA = 2 THEN 1 ELSE 0 END AS recurrente "
	        + "FROM SIB.TBL_TIPOS_CAMPANIA t1 "
	        + "INNER JOIN SIB.TBL_PROCESO_CAMPANIA t2 ON t2.I_ID_TIPO_CAMPANIA = t1.PK_ID_TIPO_CAMPANIA "
	        + "WHERE t1.PK_ID_TIPO_CAMPANIA = :valorCampaniaID",
	        nativeQuery = true)
	List<Object[]> obtenerCampañaRegistrada(@Param("valorCampaniaID") int valorCampaniaID);

	@Query(value = "select VC_CODIGO_CAM AS CODIGOCAMPANIA "
	        + ",t1.PK_ID_PROCESO_CAMPANIA as IDPROCESOCAMPANIA "
	        + ",UPPER(t2.VC_ESTATUS) AS ESTATUS "
	        + ",t1.I_ID_ESTATUS_CAMPANIA as IDESTATUS "
	        + ",DATE_FORMAT(NULLIF(t1.DT_FECHA_EXTRACCION,'1900-01-01'),'%Y-%m-%d') AS DT_FECHA_EXTRACCION "
	        + ",DATE_FORMAT(NULLIF(t1.DT_FECHA_INICIO_VIGENCIA,'1900-01-01'),'%Y-%m-%d')  AS DT_FECHA_INICIO "
	        + ",DATE_FORMAT(NULLIF(t1.DT_FECHA_FIN_VIGENCIA,'1900-01-01'),'%Y-%m-%d') AS DT_FECHA_FIN "
	        + ",DATE_FORMAT(NULLIF(t1.DT_FECHA_ENVIO_MC,'1900-01-01'),'%Y-%m-%d') AS DT_FECHA_ENVIO_MC "
	        + ",DATE_FORMAT(NULLIF(t1.DT_FECHA_REGISTRO,'1900-01-01'),'%Y-%m-%d')  AS DT_FECHA_REGISTRO "
	        + ",t3.VC_NOMBRE_USUARIO as nombreusuario "
	        + "FROM SIB.TBL_PROCESO_CAMPANIA t1 "
	        + "inner join SIB.TBL_ESTATUS_CAMPANIA t2 on t2.PK_ID_ESTATUS_CAMPANIA = t1.I_ID_ESTATUS_CAMPANIA "
	        + "INNER JOIN SIB.TBL_CAT_USUARIOS t3 on t3.PK_ID_USUARIO = t1.I_USER_REG "
	        + "where t1.I_ID_TIPO_CAMPANIA = :idcampania "
	        + "GROUP BY t1.VC_CODIGO_CAM, "
	        + "t1.PK_ID_PROCESO_CAMPANIA, "
	        + "t2.VC_ESTATUS, "
	        + "t1.I_ID_ESTATUS_CAMPANIA, "
	        + "t1.DT_FECHA_EXTRACCION, "
	        + "t1.DT_FECHA_INICIO_VIGENCIA, "
	        + "t1.DT_FECHA_FIN_VIGENCIA, "
	        + "t1.DT_FECHA_ENVIO_MC, "
	        + "t1.DT_FECHA_REGISTRO "
	        + "ORDER BY t1.PK_ID_PROCESO_CAMPANIA DESC ",
	        nativeQuery = true)
	List<Object[]> obtProcesos(Integer idcampania);

	@Query(value = "SELECT VC_CODIGO_CAM FROM SIB.TBL_PROCESO_CAMPANIA "
			+ " where I_ID_TIPO_CAMPANIA = :valorCampaniaID "
			+ " order by PK_ID_PROCESO_CAMPANIA desc "
			+ " limit 1", nativeQuery = true)
	String obtProceso(int valorCampaniaID);

	@Query(value = "SELECT IFNULL(COUNT(*), 0) as numero FROM SIB.TBL_TIPOS_CAMPANIA WHERE UPPER(VC_TIPO_CAMPANIA) LIKE UPPER(CONCAT('%', :nombreCampania, '%')) ", nativeQuery = true)
	int existeNombreCamp(String nombreCampania);
	
	
	
 
}
