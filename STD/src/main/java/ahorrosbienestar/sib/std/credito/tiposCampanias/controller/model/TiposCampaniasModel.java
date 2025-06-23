package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model;

import javax.persistence.Entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_TIPOS_CAMPANIA")
//@Table(name = "TBL_TIPOS_CAMPANIA", indexes = {@Index(name = "idx_nombrecompleto", columnList = "VC_TIPO_CAMPANIA")})


public class TiposCampaniasModel {

	@Id
	@Column(name = "PK_ID_TIPO_CAMPANIA")
	private Integer pk_id_tipo_campania;
 
	@Column(name = "VC_TIPO_CAMPANIA")
	private String vc_tipo_campania;
 
	@Column(name = "I_VENTANA_ANIOS")
	private Integer i_ventana_anios;
 
	@Column(name = "I_EDAD_MAXIMA")
	private Integer i_edad_maxima;
	
	@Column(name = "D_MONTO_MINIMO")
	private Double d_monto_minimo;
	
	@Column(name = "D_MONTO_MAXIMO")
	private Double d_monto_maximo;
	
	@Column(name = "VC_PLAZO_ID")
	private String vc_plazo_id;
	
	@Column(name = "D_PORC_CUBIERTO_CAPITAL")
	private Double d_porc_cubierto_capital;
	
	@Column(name = "I_MINIMO_VIDA_CREDITO")
	private Integer i_minimo_vida_credito;
	
	@Column(name = "I_DIAS_MORA")
	private Integer i_dias_mora;
	
	@Column(name = "I_CREDITOS_LIQUIDADOS")
	private Integer i_creditos_liquidados;
	
	@Column(name = "I_SCORE_BC")
	private Integer i_score_bc;
	
	@Column(name = "VC_ES_RENOVACION")
	private String vc_es_renovacion;
	
	@Column(name = "VC_PRODUCTOS")
	private String vc_productos;
	
	@Column(name = "I_VIGENCIA_CAMPANIA")
	private Integer i_vigencia_campania;
	
	@Column(name = "I_DIAS_GRACIA")
	private Integer i_dias_gracia;
	
	@Column(name = "I_PROD_CREDITO_ID_OFERTA")
	private Integer i_prod_credito_id_oferta;
	
	@Column(name = "D_PORC_INC_OFERTA")
	private Double d_porc_inc_oferta;
	
	@Column(name = "D_MONTO_MAXIMO_OFERTA")
	private Double d_monto_maximo_oferta;
	
	@Column(name = "D_TASA_ORDINARIA_OFERTA")
	private Double d_tasa_ordinaria_oferta;
	
	@Column(name = "D_TASA_MORATORIA_OFERTA")
	private Double d_tasa_moratoria_oferta;
	
	@Column(name = "D_TASA_COMP_OFERTA")
	private Double d_tasa_comp_oferta;
	
	@Column(name = "VC_INC_CODEUDOR_OFERTA")
	private String vc_inc_codeudor_oferta;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;
	
	@Column(name = "I_TIPO_CAMPANIA")
	private Integer i_tipo_campania;
	
	@Column(name = "I_PLAZO_MAXIMO")
	private Integer i_plazo_maximo;
	
	@Column(name = "I_CONVIVENCIA")
	private Integer i_convivencia;
	
	@Column(name = "VC_ES_RENOVACION_OFERTA")
	private String vc_es_renovacion_oferta;
	
	@Column(name = "I_ACTIVA_OFERTA")
	private Integer i_activa_oferta;
	
	@Column(name = "I_VIGENCIA_OFERTA")
	private Integer i_vigencia_oferta;
	
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@Column(name = "I_USER_REG")
	private Integer i_user_reg;
	
	@Column(name = "I_EDAD_MINIMA")
	private Integer i_edad_minima;
	
	@Column(name = "I_MORA_MAXIMA")
	private Integer i_mora_maxima;
	
	@Column(name = "I_PLAZO_MAXIMO_ANIOS")
	private Integer i_plazo_maximo_anios;
	
	@Column(name = "I_VIGENCIA_BC")
	private Integer i_vigencia_bc;

	public Integer getPk_id_tipo_campania() {
		return pk_id_tipo_campania;
	}

	public void setPk_id_tipo_campania(Integer pk_id_tipo_campania) {
		this.pk_id_tipo_campania = pk_id_tipo_campania;
	}

	public String getVc_tipo_campania() {
		return vc_tipo_campania;
	}

	public void setVc_tipo_campania(String vc_tipo_campania) {
		this.vc_tipo_campania = vc_tipo_campania;
	}

	public Integer getI_ventana_anios() {
		return i_ventana_anios;
	}

	public void setI_ventana_anios(Integer i_ventana_anios) {
		this.i_ventana_anios = i_ventana_anios;
	}

	public Integer getI_edad_maxima() {
		return i_edad_maxima;
	}

	public void setI_edad_maxima(Integer i_edad_maxima) {
		this.i_edad_maxima = i_edad_maxima;
	}

	public Double getD_monto_minimo() {
		return d_monto_minimo;
	}

	public void setD_monto_minimo(Double d_monto_minimo) {
		this.d_monto_minimo = d_monto_minimo;
	}

	public Double getD_monto_maximo() {
		return d_monto_maximo;
	}

	public void setD_monto_maximo(Double d_monto_maximo) {
		this.d_monto_maximo = d_monto_maximo;
	}

	public String getVc_plazo_id() {
		return vc_plazo_id;
	}

	public void setVc_plazo_id(String vc_plazo_id) {
		this.vc_plazo_id = vc_plazo_id;
	}

	public Double getD_porc_cubierto_capital() {
		return d_porc_cubierto_capital;
	}

	public void setD_porc_cubierto_capital(Double d_porc_cubierto_capital) {
		this.d_porc_cubierto_capital = d_porc_cubierto_capital;
	}

	public Integer getI_minimo_vida_credito() {
		return i_minimo_vida_credito;
	}

	public void setI_minimo_vida_credito(Integer i_minimo_vida_credito) {
		this.i_minimo_vida_credito = i_minimo_vida_credito;
	}

	public Integer getI_dias_mora() {
		return i_dias_mora;
	}

	public void setI_dias_mora(Integer i_dias_mora) {
		this.i_dias_mora = i_dias_mora;
	}

	public Integer getI_creditos_liquidados() {
		return i_creditos_liquidados;
	}

	public void setI_creditos_liquidados(Integer i_creditos_liquidados) {
		this.i_creditos_liquidados = i_creditos_liquidados;
	}

	public Integer getI_score_bc() {
		return i_score_bc;
	}

	public void setI_score_bc(Integer i_score_bc) {
		this.i_score_bc = i_score_bc;
	}

	public String getVc_es_renovacion() {
		return vc_es_renovacion;
	}

	public void setVc_es_renovacion(String vc_es_renovacion) {
		this.vc_es_renovacion = vc_es_renovacion;
	}

	public String getVc_productos() {
		return vc_productos;
	}

	public void setVc_productos(String vc_productos) {
		this.vc_productos = vc_productos;
	}

	public Integer getI_vigencia_campania() {
		return i_vigencia_campania;
	}

	public void setI_vigencia_campania(Integer i_vigencia_campania) {
		this.i_vigencia_campania = i_vigencia_campania;
	}

	public Integer getI_dias_gracia() {
		return i_dias_gracia;
	}

	public void setI_dias_gracia(Integer i_dias_gracia) {
		this.i_dias_gracia = i_dias_gracia;
	}

	public Integer getI_prod_credito_id_oferta() {
		return i_prod_credito_id_oferta;
	}

	public void setI_prod_credito_id_oferta(Integer i_prod_credito_id_oferta) {
		this.i_prod_credito_id_oferta = i_prod_credito_id_oferta;
	}

	public Double getD_porc_inc_oferta() {
		return d_porc_inc_oferta;
	}

	public void setD_porc_inc_oferta(Double d_porc_inc_oferta) {
		this.d_porc_inc_oferta = d_porc_inc_oferta;
	}

	public Double getD_monto_maximo_oferta() {
		return d_monto_maximo_oferta;
	}

	public void setD_monto_maximo_oferta(Double d_monto_maximo_oferta) {
		this.d_monto_maximo_oferta = d_monto_maximo_oferta;
	}

	public Double getD_tasa_ordinaria_oferta() {
		return d_tasa_ordinaria_oferta;
	}

	public void setD_tasa_ordinaria_oferta(Double d_tasa_ordinaria_oferta) {
		this.d_tasa_ordinaria_oferta = d_tasa_ordinaria_oferta;
	}

	public Double getD_tasa_moratoria_oferta() {
		return d_tasa_moratoria_oferta;
	}

	public void setD_tasa_moratoria_oferta(Double d_tasa_moratoria_oferta) {
		this.d_tasa_moratoria_oferta = d_tasa_moratoria_oferta;
	}

	public Double getD_tasa_comp_oferta() {
		return d_tasa_comp_oferta;
	}

	public void setD_tasa_comp_oferta(Double d_tasa_comp_oferta) {
		this.d_tasa_comp_oferta = d_tasa_comp_oferta;
	}

	public String getVc_inc_codeudor_oferta() {
		return vc_inc_codeudor_oferta;
	}

	public void setVc_inc_codeudor_oferta(String vc_inc_codeudor_oferta) {
		this.vc_inc_codeudor_oferta = vc_inc_codeudor_oferta;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}

	public Integer getI_tipo_campania() {
		return i_tipo_campania;
	}

	public void setI_tipo_campania(Integer i_tipo_campania) {
		this.i_tipo_campania = i_tipo_campania;
	}

	public Integer getI_plazo_maximo() {
		return i_plazo_maximo;
	}

	public void setI_plazo_maximo(Integer i_plazo_maximo) {
		this.i_plazo_maximo = i_plazo_maximo;
	}

	public Integer getI_convivencia() {
		return i_convivencia;
	}

	public void setI_convivencia(Integer i_convivencia) {
		this.i_convivencia = i_convivencia;
	}

	public String getVc_es_renovacion_oferta() {
		return vc_es_renovacion_oferta;
	}

	public void setVc_es_renovacion_oferta(String vc_es_renovacion_oferta) {
		this.vc_es_renovacion_oferta = vc_es_renovacion_oferta;
	}

	public Integer getI_activa_oferta() {
		return i_activa_oferta;
	}

	public void setI_activa_oferta(Integer i_activa_oferta) {
		this.i_activa_oferta = i_activa_oferta;
	}

	public Integer getI_vigencia_oferta() {
		return i_vigencia_oferta;
	}

	public void setI_vigencia_oferta(Integer i_vigencia_oferta) {
		this.i_vigencia_oferta = i_vigencia_oferta;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public Integer getI_user_reg() {
		return i_user_reg;
	}

	public void setI_user_reg(Integer i_user_reg) {
		this.i_user_reg = i_user_reg;
	}

	public Integer getI_edad_minima() {
		return i_edad_minima;
	}

	public void setI_edad_minima(Integer i_edad_minima) {
		this.i_edad_minima = i_edad_minima;
	}

	public Integer getI_mora_maxima() {
		return i_mora_maxima;
	}

	public void setI_mora_maxima(Integer i_mora_maxima) {
		this.i_mora_maxima = i_mora_maxima;
	}

	public Integer getI_plazo_maximo_anios() {
		return i_plazo_maximo_anios;
	}

	public void setI_plazo_maximo_anios(Integer i_plazo_maximo_anios) {
		this.i_plazo_maximo_anios = i_plazo_maximo_anios;
	}

	public Integer getI_vigencia_bc() {
		return i_vigencia_bc;
	}

	public void setI_vigencia_bc(Integer i_vigencia_bc) {
		this.i_vigencia_bc = i_vigencia_bc;
	}

}
