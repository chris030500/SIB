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
	private int pk_id_tipo_campania;
 
	@Column(name = "VC_TIPO_CAMPANIA")
	private String vc_tipo_campania;
 
	@Column(name = "I_VENTANA_ANIOS")
	private int i_ventana_anios;
 
	@Column(name = "I_EDAD_MAXIMA")
	private int i_edad_maxima;
	
	@Column(name = "D_MONTO_MINIMO")
	private double d_monto_minimo;
	
	@Column(name = "D_MONTO_MAXIMO")
	private double d_monto_maximo;
	
	@Column(name = "VC_PLAZO_ID")
	private String vc_plazo_id;
	
	@Column(name = "D_PORC_CUBIERTO_CAPITAL")
	private double d_porc_cubierto_capital;
	
	@Column(name = "I_MINIMO_VIDA_CREDITO")
	private int i_minimo_vida_credito;
	
	@Column(name = "I_DIAS_MORA")
	private int i_dias_mora;
	
	@Column(name = "I_CREDITOS_LIQUIDADOS")
	private int i_creditos_liquidados;
	
	@Column(name = "I_SCORE_BC")
	private int i_score_bc;
	
	@Column(name = "VC_ES_RENOVACION")
	private String vc_es_renovacion;
	
	@Column(name = "VC_PRODUCTOS")
	private String vc_productos;
	
	@Column(name = "I_VIGENCIA_CAMPANIA")
	private int i_vigencia_campania;
	
	@Column(name = "I_DIAS_GRACIA")
	private int i_dias_gracia;
	
	@Column(name = "I_PROD_CREDITO_ID_OFERTA")
	private int i_prod_credito_id_oferta;
	
	@Column(name = "D_PORC_INC_OFERTA")
	private double d_proc_inc_oferta;
	
	@Column(name = "D_MONTO_MAXIMO_OFERTA")
	private double d_monto_maximo_oferta;
	
	@Column(name = "D_TASA_ORDINARIA_OFERTA")
	private double d_tasa_ordinaria_oferta;
	
	@Column(name = "D_TASA_MORATORIA_OFERTA")
	private double d_tasa_moratoria_oferta;
	
	@Column(name = "D_TASA_COMP_OFERTA")
	private double d_tasa_comp_oferta;
	
	@Column(name = "VC_INC_CODEUDOR_OFERTA")
	private String vc_inc_codeudor_oferta;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;
	
	@Column(name = "I_TIPO_CAMPANIA")
	private int i_tipo_campania;
	
	@Column(name = "I_PLAZO_MAXIMO")
	private int i_plazo_maximo;
	
	@Column(name = "I_CONVIVENCIA")
	private int i_convivencia;
	
	@Column(name = "VC_ES_RENOVACION_OFERTA")
	private String vc_es_renovacion_oferta;
	
	@Column(name = "I_ACTIVA_OFERTA")
	private int i_activa_oferta;
	
	@Column(name = "I_VIGENCIA_OFERTA")
	private int i_vigencia_oferta;
	
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@Column(name = "I_USER_REG")
	private int i_user_reg;
 
	public int getPk_id_tipo_campania() {
		return pk_id_tipo_campania;
	}

	public void setPk_id_tipo_campania(int pk_id_tipo_campania) {
		this.pk_id_tipo_campania = pk_id_tipo_campania;
	}

	public String getVc_tipo_campania() {
		return vc_tipo_campania;
	}

	public void setVc_tipo_campania(String vc_tipo_campania) {
		this.vc_tipo_campania = vc_tipo_campania;
	}

	public int getI_ventana_anios() {
		return i_ventana_anios;
	}

	public void setI_ventana_anios(int i_ventana_anios) {
		this.i_ventana_anios = i_ventana_anios;
	}

	public int getI_edad_maxima() {
		return i_edad_maxima;
	}

	public void setI_edad_maxima(int i_edad_maxima) {
		this.i_edad_maxima = i_edad_maxima;
	}

	public double getD_monto_minimo() {
		return d_monto_minimo;
	}

	public void setD_monto_minimo(double d_monto_minimo) {
		this.d_monto_minimo = d_monto_minimo;
	}

	public double getD_monto_maximo() {
		return d_monto_maximo;
	}

	public void setD_monto_maximo(double d_monto_maximo) {
		this.d_monto_maximo = d_monto_maximo;
	}

	public String getVc_plazo_id() {
		return vc_plazo_id;
	}

	public void setVc_plazo_id(String vc_plazo_id) {
		this.vc_plazo_id = vc_plazo_id;
	}

	public double getD_porc_cubierto_capital() {
		return d_porc_cubierto_capital;
	}

	public void setD_porc_cubierto_capital(double d_porc_cubierto_capital) {
		this.d_porc_cubierto_capital = d_porc_cubierto_capital;
	}

	public int getI_minimo_vida_credito() {
		return i_minimo_vida_credito;
	}

	public void setI_minimo_vida_credito(int i_minimo_vida_credito) {
		this.i_minimo_vida_credito = i_minimo_vida_credito;
	}

	public int getI_dias_mora() {
		return i_dias_mora;
	}

	public void setI_dias_mora(int i_dias_mora) {
		this.i_dias_mora = i_dias_mora;
	}

	public int getI_creditos_liquidados() {
		return i_creditos_liquidados;
	}

	public void setI_creditos_liquidados(int i_creditos_liquidados) {
		this.i_creditos_liquidados = i_creditos_liquidados;
	}

	public int getI_score_bc() {
		return i_score_bc;
	}

	public void setI_score_bc(int i_score_bc) {
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

	public int getI_vigencia_campania() {
		return i_vigencia_campania;
	}

	public void setI_vigencia_campania(int i_vigencia_campania) {
		this.i_vigencia_campania = i_vigencia_campania;
	}

	public int getI_dias_gracia() {
		return i_dias_gracia;
	}

	public void setI_dias_gracia(int i_dias_gracia) {
		this.i_dias_gracia = i_dias_gracia;
	}

	public int getI_prod_credito_id_oferta() {
		return i_prod_credito_id_oferta;
	}

	public void setI_prod_credito_id_oferta(int i_prod_credito_id_oferta) {
		this.i_prod_credito_id_oferta = i_prod_credito_id_oferta;
	}

	public double getD_proc_inc_oferta() {
		return d_proc_inc_oferta;
	}

	public void setD_proc_inc_oferta(double d_proc_inc_oferta) {
		this.d_proc_inc_oferta = d_proc_inc_oferta;
	}

	public double getD_monto_maximo_oferta() {
		return d_monto_maximo_oferta;
	}

	public void setD_monto_maximo_oferta(double d_monto_maximo_oferta) {
		this.d_monto_maximo_oferta = d_monto_maximo_oferta;
	}

	public double getD_tasa_ordinaria_oferta() {
		return d_tasa_ordinaria_oferta;
	}

	public void setD_tasa_ordinaria_oferta(double d_tasa_ordinaria_oferta) {
		this.d_tasa_ordinaria_oferta = d_tasa_ordinaria_oferta;
	}

	public double getD_tasa_moratoria_oferta() {
		return d_tasa_moratoria_oferta;
	}

	public void setD_tasa_moratoria_oferta(double d_tasa_moratoria_oferta) {
		this.d_tasa_moratoria_oferta = d_tasa_moratoria_oferta;
	}

	public double getD_tasa_comp_oferta() {
		return d_tasa_comp_oferta;
	}

	public void setD_tasa_comp_oferta(double d_tasa_comp_oferta) {
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

	public int getI_tipo_campania() {
		return i_tipo_campania;
	}

	public void setI_tipo_campania(int i_tipo_campania) {
		this.i_tipo_campania = i_tipo_campania;
	}

	public int getI_plazo_maximo() {
		return i_plazo_maximo;
	}

	public void setI_plazo_maximo(int i_plazo_maximo) {
		this.i_plazo_maximo = i_plazo_maximo;
	}

	public int getI_convivencia() {
		return i_convivencia;
	}

	public void setI_convivencia(int i_convivencia) {
		this.i_convivencia = i_convivencia;
	}

	public String getVc_es_renovacion_oferta() {
		return vc_es_renovacion_oferta;
	}

	public void setVc_es_renovacion_oferta(String vc_es_renovacion_oferta) {
		this.vc_es_renovacion_oferta = vc_es_renovacion_oferta;
	}

	public int getI_activa_oferta() {
		return i_activa_oferta;
	}

	public void setI_activa_oferta(int i_activa_oferta) {
		this.i_activa_oferta = i_activa_oferta;
	}

	public int getI_vigencia_oferta() {
		return i_vigencia_oferta;
	}

	public void setI_vigencia_oferta(int i_vigencia_oferta) {
		this.i_vigencia_oferta = i_vigencia_oferta;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public int getI_user_reg() {
		return i_user_reg;
	}

	public void setI_user_reg(int i_user_reg) {
		this.i_user_reg = i_user_reg;
	}
	
}
