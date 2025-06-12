package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;

 
@Repository

public interface TiposCampaniasRepository extends JpaRepository<TiposCampaniasModel, Integer> {
	 
	//@Query(value = "SELECT VC_TIPO_CAMPANIA FROM SIB.TBL_TIPOS_CAMPANIA WHERE VC_TIPO_CAMPANIA LIKE %:valor%", nativeQuery = true)
	@Query(value = "SELECT PK_ID_TIPO_CAMPANIA, CONCAT(PK_ID_TIPO_CAMPANIA, ' - ', VC_TIPO_CAMPANIA) AS VC_TIPO_CAMPANIA"
			+ " FROM SIB.TBL_TIPOS_CAMPANIA"
			+ " WHERE CONCAT(PK_ID_TIPO_CAMPANIA, ' - ', VC_TIPO_CAMPANIA) LIKE %:valor%", nativeQuery = true)
	List<Object[]> listaCampanias(@Param("valor") String valor);
	
	@Query(value = "SELECT * FROM SIB.TBL_TIPOS_CAMPANIA WHERE PK_ID_TIPO_CAMPANIA = :pk_id_tipo_campania", nativeQuery = true)
	TiposCampaniasModel mostrarCampania(@Param("pk_id_tipo_campania") Integer pk_id_tipo_campania);
	
	//@Query(value = "SELECT VC_TIPO_CAMPANIA FROM SIB.TBL_TIPOS_CAMPANIA WHERE VC_TIPO_CAMPANIA LIKE %:valor%", nativeQuery = true)
	//public List<TiposCampaniasModel> listaCampanias(@Param("valor") String valor);
	
	@Modifying
	@Transactional // Necesario para ejecutar updates
	@Query(value = "UPDATE SIB.TBL_TIPOS_CAMPANIA SET "
		+ "I_TIPO_CAMPANIA = :i_tipo_campania, "
        + "I_VENTANA_ANIOS = :i_ventana_anios, "
        + "I_EDAD_MAXIMA = :i_edad_maxima, "
        + "I_EDAD_MINIMA = :i_edad_minima, "
        + "D_MONTO_MINIMO = :d_monto_minimo, "
        + "D_MONTO_MAXIMO = :d_monto_maximo, "
        + "I_MORA_MAXIMA = :i_mora_maxima, "
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
        + "VC_ES_RENOVACION_OFERTA = :vc_es_renovacion_oferta, "
        + "I_VIGENCIA_OFERTA = :i_vigencia_oferta, "
        + "D_TASA_ORDINARIA_OFERTA = :d_tasa_ordinaria_oferta, "
        + "I_ACTIVA_OFERTA = :i_activa_oferta, "
        + "D_MONTO_MAXIMO_OFERTA = :d_monto_maximo_oferta "
        + "WHERE PK_ID_TIPO_CAMPANIA = :pk_id_tipo_campania", nativeQuery = true)
	int actualizarCampania(
	    @Param("pk_id_tipo_campania") Integer pk_id_tipo_campania,
	    @Param("i_tipo_campania") Integer i_tipo_campania,
	    @Param("i_ventana_anios") Integer i_ventana_anios,
	    @Param("i_edad_maxima") Integer i_edad_maxima,
	    @Param("i_edad_minima") Integer i_edad_minima,
	    @Param("d_monto_minimo") Double d_monto_minimo,
	    @Param("d_monto_maximo") Double d_monto_maximo,
	    @Param("i_mora_maxima") Integer i_mora_maxima,
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
	    @Param("vc_es_renovacion_oferta") String vc_es_renovacion_oferta,
	    @Param("i_vigencia_oferta") Integer i_vigencia_oferta,
	    @Param("d_tasa_ordinaria_oferta") Double d_tasa_ordinaria_oferta,
	    @Param("i_activa_oferta") Integer i_activa_oferta,
	    @Param("d_monto_maximo_oferta") Double d_monto_maximo_oferta
	);
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO SIB.TBL_TIPOS_CAMPANIA ("
	        + "VC_TIPO_CAMPANIA, I_TIPO_CAMPANIA, I_VENTANA_ANIOS, I_EDAD_MAXIMA, I_EDAD_MINIMA, D_MONTO_MINIMO, D_MONTO_MAXIMO, "
	        + "I_MORA_MAXIMA, D_PORC_CUBIERTO_CAPITAL, I_DIAS_GRACIA, I_DIAS_MORA, "
	        + "I_PLAZO_MAXIMO_ANIOS, I_MINIMO_VIDA_CREDITO, I_VIGENCIA_BC, I_VIGENCIA_CAMPANIA, "
	        + "I_CREDITOS_LIQUIDADOS, VC_ES_RENOVACION, I_CONVIVENCIA, VC_PRODUCTOS, "
	        + "I_PROD_CREDITO_ID_OFERTA, D_PORC_INC_OFERTA, VC_ES_RENOVACION_OFERTA, "
	        + "I_VIGENCIA_OFERTA, D_TASA_ORDINARIA_OFERTA, I_ACTIVA_OFERTA, D_MONTO_MAXIMO_OFERTA"
	        + ") VALUES ("
	        + ":vc_tipo_campania, :i_tipo_campania, :i_ventana_anios, :i_edad_maxima, :i_edad_minima, :d_monto_minimo, :d_monto_maximo, "
	        + ":i_mora_maxima, :d_porc_cubierto_capital, :i_dias_gracia, :i_dias_mora, "
	        + ":i_plazo_maximo_anios, :i_minimo_vida_credito, :i_vigencia_bc, :i_vigencia_campania, "
	        + ":i_creditos_liquidados, :vc_es_renovacion, :i_convivencia, :vc_productos, "
	        + ":i_prod_credito_id_oferta, :d_porc_inc_oferta, :vc_es_renovacion_oferta, "
	        + ":i_vigencia_oferta, :d_tasa_ordinaria_oferta, :i_activa_oferta, :d_monto_maximo_oferta"
	        + ")", nativeQuery = true)
	int insertarCampania(
			@Param("vc_tipo_campania") String vc_tipo_campania,
			@Param("i_tipo_campania") Integer i_tipo_campania,
	        @Param("i_ventana_anios") Integer i_ventana_anios,
	        @Param("i_edad_maxima") Integer i_edad_maxima,
	        @Param("i_edad_minima") Integer i_edad_minima,
	        @Param("d_monto_minimo") Double d_monto_minimo,
	        @Param("d_monto_maximo") Double d_monto_maximo,
	        @Param("i_mora_maxima") Integer i_mora_maxima,
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
	        @Param("vc_es_renovacion_oferta") String vc_es_renovacion_oferta,
	        @Param("i_vigencia_oferta") Integer i_vigencia_oferta,
	        @Param("d_tasa_ordinaria_oferta") Double d_tasa_ordinaria_oferta,
	        @Param("i_activa_oferta") Integer i_activa_oferta,
	        @Param("d_monto_maximo_oferta") Double d_monto_maximo_oferta
	);
 
}
