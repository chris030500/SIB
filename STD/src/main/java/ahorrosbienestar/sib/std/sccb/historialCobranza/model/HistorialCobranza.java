package ahorrosbienestar.sib.std.sccb.historialCobranza.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_SCCB_HISTORIAL_COBRANZA")
public class HistorialCobranza {
	
	@Id
	@Column(name = "PK_I_ROW_GUID")
	private Integer pk_i_row_guid;
	
	@Column(name = "I_SOLICITUD_ID")
	private Integer i_solicitud_id;
	
	@Column(name = "VC_DEUDOR")
	private String vc_deudor;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_CARTERA")
	private Date dt_fecha_cartera;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_ULT_MOVTO")
	private Date dt_fecha_ult_movto;
	
	@Column(name = "I_FACTURAS")
	private Integer i_facturas;
	
	@Column(name = "FL_IMPORTE")
	private Float fl_importe;
	
	@Column(name = "FL_SALDO")
	private Float fl_saldo;
	
	@Column(name = "I_DIAS_MORA")
	private Integer i_dias_mora;
	
	@Column(name = "FL_INTERES_NORMAL")
	private Float fl_interes_normal;
	
	@Column(name = "FL_IVA")
	private Float fl_iva;
	
	@Column(name = "FL_INTERES_MORAT")
	private Float fl_interes_morat;
	
	@Column(name = "FL_IVA_INTERES_MORAT")
	private Float fl_iva_interes_morat;
	
	@Column(name = "FL_COMISION")
	private Float fl_comision;
	
	@Column(name = "FL_ATRASO")
	private Float fl_atraso;
	
	@Column(name = "VC_NOMBRE_CENTRO")
	private String vc_nombre_centro;
	
	@Column(name = "FL_TASA_NORMAL")
	private Float fl_tasa_normal;
	
	@Column(name = "FL_TASA_MORATORIA")
	private Float fl_tasa_moratoria;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_INICIO")
	private Date dt_fecha_inicio;
	
	@Column(name = "I_PLAZO")
	private Integer i_plazo;
	
	@Column(name = "VC_CALIFICACION")
	private String vc_calificacion;
	
	@Column(name = "I_EMPLEADO_ANALIZA_ID")
	private Integer i_empleado_analiza_id;
	
	@Column(name = "VC_FRECUENCIA")
	private String vc_frecuencia;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_VENCIMIENTO")
	private Date dt_fecha_vencimiento;
	
	@Column(name = "I_BANDA")
	private Integer i_banda;
	
	@Column(name = "VC_RANGO_BANDA")
	private String vc_rango_banda;
	
	@Column(name = "VC_TARIFA")
	private String vc_tarifa;
	
	@Column(name = "I_ESTATUS_ACTIVO_ID")
	private Integer i_estatus_activo_id;
	
	@Column(name = "VC_ESTATUS_ACTIVO")
	private String vc_estatus_activo;
	
	@Column(name = "VC_TIPO_CREDITO")
	private String vc_tipo_credito;
	
	@Column(name = "VC_DESPACHO")
	private String vc_despacho;
	
	@Column(name = "VC_EN_COBRANZA_JUDICIAL")
	private String vc_en_cobranza_judicial;
	
	@Column(name = "VC_INTENCIONALIDAD")
	private String vc_intencionalidad;
	
	@Column(name = "I_ID_EXP_JUDICIAL")
	private Integer i_id_exp_judicial;
	
	@Column(name = "I_ID_DESPACHO")
	private Integer i_id_despacho;
	
	@Column(name = "I_ID_EJECUTIVO")
	private Integer i_id_ejecutivo;
	
	@Column(name = "VC_NOM_ANALISTA")
	private String vc_nom_analista;
	
	@Column(name = "VC_ETAPA")
	private String vc_etapa;
	
	@Column(name = "I_EXPEDIENTE_ID")
	private Integer i_expediente_id;
	
	@Column(name = "VC_NEGOCIADOR")
	private String vc_negociador;
	
	@Column(name = "FL_GASTOS")
	private Float fl_gastos;
	
	@Column(name = "I_COD_ESTATUS_PETICION")
	private Integer i_cod_estatus_peticion;
	
	@Column(name = "I_IND_DESO_INTERNO")
	private Integer i_ind_desp_interno;

	public Integer getPk_i_row_guid() {
		return pk_i_row_guid;
	}

	public void setPk_i_row_guid(Integer pk_i_row_guid) {
		this.pk_i_row_guid = pk_i_row_guid;
	}

	public Integer getI_solicitud_id() {
		return i_solicitud_id;
	}

	public void setI_solicitud_id(Integer i_solicitud_id) {
		this.i_solicitud_id = i_solicitud_id;
	}

	public String getVc_deudor() {
		return vc_deudor;
	}

	public void setVc_deudor(String vc_deudor) {
		this.vc_deudor = vc_deudor;
	}

	public Date getDt_fecha_cartera() {
		return dt_fecha_cartera;
	}

	public void setDt_fecha_cartera(Date dt_fecha_cartera) {
		this.dt_fecha_cartera = dt_fecha_cartera;
	}

	public Date getDt_fecha_ult_movto() {
		return dt_fecha_ult_movto;
	}

	public void setDt_fecha_ult_movto(Date dt_fecha_ult_movto) {
		this.dt_fecha_ult_movto = dt_fecha_ult_movto;
	}

	public Integer getI_facturas() {
		return i_facturas;
	}

	public void setI_facturas(Integer i_facturas) {
		this.i_facturas = i_facturas;
	}

	public Float getFl_importe() {
		return fl_importe;
	}

	public void setFl_importe(Float fl_importe) {
		this.fl_importe = fl_importe;
	}

	public Float getFl_saldo() {
		return fl_saldo;
	}

	public void setFl_saldo(Float fl_saldo) {
		this.fl_saldo = fl_saldo;
	}

	public Integer getI_dias_mora() {
		return i_dias_mora;
	}

	public void setI_dias_mora(Integer i_dias_mora) {
		this.i_dias_mora = i_dias_mora;
	}

	public Float getFl_interes_normal() {
		return fl_interes_normal;
	}

	public void setFl_interes_normal(Float fl_interes_normal) {
		this.fl_interes_normal = fl_interes_normal;
	}

	public Float getFl_iva() {
		return fl_iva;
	}

	public void setFl_iva(Float fl_iva) {
		this.fl_iva = fl_iva;
	}

	public Float getFl_interes_morat() {
		return fl_interes_morat;
	}

	public void setFl_interes_morat(Float fl_interes_morat) {
		this.fl_interes_morat = fl_interes_morat;
	}

	public Float getFl_iva_interes_morat() {
		return fl_iva_interes_morat;
	}

	public void setFl_iva_interes_morat(Float fl_iva_interes_morat) {
		this.fl_iva_interes_morat = fl_iva_interes_morat;
	}

	public Float getFl_comision() {
		return fl_comision;
	}

	public void setFl_comision(Float fl_comision) {
		this.fl_comision = fl_comision;
	}

	public Float getFl_atraso() {
		return fl_atraso;
	}

	public void setFl_atraso(Float fl_atraso) {
		this.fl_atraso = fl_atraso;
	}

	public String getVc_nombre_centro() {
		return vc_nombre_centro;
	}

	public void setVc_nombre_centro(String vc_nombre_centro) {
		this.vc_nombre_centro = vc_nombre_centro;
	}

	public Float getFl_tasa_normal() {
		return fl_tasa_normal;
	}

	public void setFl_tasa_normal(Float fl_tasa_normal) {
		this.fl_tasa_normal = fl_tasa_normal;
	}

	public Float getFl_tasa_moratoria() {
		return fl_tasa_moratoria;
	}

	public void setFl_tasa_moratoria(Float fl_tasa_moratoria) {
		this.fl_tasa_moratoria = fl_tasa_moratoria;
	}

	public Date getDt_fecha_inicio() {
		return dt_fecha_inicio;
	}

	public void setDt_fecha_inicio(Date dt_fecha_inicio) {
		this.dt_fecha_inicio = dt_fecha_inicio;
	}

	public Integer getI_plazo() {
		return i_plazo;
	}

	public void setI_plazo(Integer i_plazo) {
		this.i_plazo = i_plazo;
	}

	public String getVc_calificacion() {
		return vc_calificacion;
	}

	public void setVc_calificacion(String vc_calificacion) {
		this.vc_calificacion = vc_calificacion;
	}

	public Integer getI_empleado_analiza_id() {
		return i_empleado_analiza_id;
	}

	public void setI_empleado_analiza_id(Integer i_empleado_analiza_id) {
		this.i_empleado_analiza_id = i_empleado_analiza_id;
	}

	public String getVc_frecuencia() {
		return vc_frecuencia;
	}

	public void setVc_frecuencia(String vc_frecuencia) {
		this.vc_frecuencia = vc_frecuencia;
	}

	public Date getDt_fecha_vencimiento() {
		return dt_fecha_vencimiento;
	}

	public void setDt_fecha_vencimiento(Date dt_fecha_vencimiento) {
		this.dt_fecha_vencimiento = dt_fecha_vencimiento;
	}

	public Integer getI_banda() {
		return i_banda;
	}

	public void setI_banda(Integer i_banda) {
		this.i_banda = i_banda;
	}

	public String getVc_rango_banda() {
		return vc_rango_banda;
	}

	public void setVc_rango_banda(String vc_rango_banda) {
		this.vc_rango_banda = vc_rango_banda;
	}

	public String getVc_tarifa() {
		return vc_tarifa;
	}

	public void setVc_tarifa(String vc_tarifa) {
		this.vc_tarifa = vc_tarifa;
	}

	public Integer getI_estatus_activo_id() {
		return i_estatus_activo_id;
	}

	public void setI_estatus_activo_id(Integer i_estatus_activo_id) {
		this.i_estatus_activo_id = i_estatus_activo_id;
	}

	public String getVc_estatus_activo() {
		return vc_estatus_activo;
	}

	public void setVc_estatus_activo(String vc_estatus_activo) {
		this.vc_estatus_activo = vc_estatus_activo;
	}

	public String getVc_tipo_credito() {
		return vc_tipo_credito;
	}

	public void setVc_tipo_credito(String vc_tipo_credito) {
		this.vc_tipo_credito = vc_tipo_credito;
	}

	public String getVc_despacho() {
		return vc_despacho;
	}

	public void setVc_despacho(String vc_despacho) {
		this.vc_despacho = vc_despacho;
	}

	public String getVc_en_cobranza_judicial() {
		return vc_en_cobranza_judicial;
	}

	public void setVc_en_cobranza_judicial(String vc_en_cobranza_judicial) {
		this.vc_en_cobranza_judicial = vc_en_cobranza_judicial;
	}

	public String getVc_intencionalidad() {
		return vc_intencionalidad;
	}

	public void setVc_intencionalidad(String vc_intencionalidad) {
		this.vc_intencionalidad = vc_intencionalidad;
	}

	public Integer getI_id_exp_judicial() {
		return i_id_exp_judicial;
	}

	public void setI_id_exp_judicial(Integer i_id_exp_judicial) {
		this.i_id_exp_judicial = i_id_exp_judicial;
	}

	public Integer getI_id_despacho() {
		return i_id_despacho;
	}

	public void setI_id_despacho(Integer i_id_despacho) {
		this.i_id_despacho = i_id_despacho;
	}

	public Integer getI_id_ejecutivo() {
		return i_id_ejecutivo;
	}

	public void setI_id_ejecutivo(Integer i_id_ejecutivo) {
		this.i_id_ejecutivo = i_id_ejecutivo;
	}

	public String getVc_nom_analista() {
		return vc_nom_analista;
	}

	public void setVc_nom_analista(String vc_nom_analista) {
		this.vc_nom_analista = vc_nom_analista;
	}

	public String getVc_etapa() {
		return vc_etapa;
	}

	public void setVc_etapa(String vc_etapa) {
		this.vc_etapa = vc_etapa;
	}

	public Integer getI_expediente_id() {
		return i_expediente_id;
	}

	public void setI_expediente_id(Integer i_expediente_id) {
		this.i_expediente_id = i_expediente_id;
	}

	public String getVc_negociador() {
		return vc_negociador;
	}

	public void setVc_negociador(String vc_negociador) {
		this.vc_negociador = vc_negociador;
	}

	public Float getFl_gastos() {
		return fl_gastos;
	}

	public void setFl_gastos(Float fl_gastos) {
		this.fl_gastos = fl_gastos;
	}

	public Integer getI_cod_estatus_peticion() {
		return i_cod_estatus_peticion;
	}

	public void setI_cod_estatus_peticion(Integer i_cod_estatus_peticion) {
		this.i_cod_estatus_peticion = i_cod_estatus_peticion;
	}

	public Integer getI_ind_desp_interno() {
		return i_ind_desp_interno;
	}

	public void setI_ind_desp_interno(Integer i_ind_desp_interno) {
		this.i_ind_desp_interno = i_ind_desp_interno;
	}

	@Override
	public String toString() {
		return "HistorialCobranza [pk_i_row_guid=" + pk_i_row_guid + ", i_solicitud_id=" + i_solicitud_id
				+ ", vc_deudor=" + vc_deudor + ", dt_fecha_cartera=" + dt_fecha_cartera + ", dt_fecha_ult_movto="
				+ dt_fecha_ult_movto + ", i_facturas=" + i_facturas + ", fl_importe=" + fl_importe + ", fl_saldo="
				+ fl_saldo + ", i_dias_mora=" + i_dias_mora + ", fl_interes_normal=" + fl_interes_normal + ", fl_iva="
				+ fl_iva + ", fl_interes_morat=" + fl_interes_morat + ", fl_iva_interes_morat=" + fl_iva_interes_morat
				+ ", fl_comision=" + fl_comision + ", fl_atraso=" + fl_atraso + ", vc_nombre_centro=" + vc_nombre_centro
				+ ", fl_tasa_normal=" + fl_tasa_normal + ", fl_tasa_moratoria=" + fl_tasa_moratoria
				+ ", dt_fecha_inicio=" + dt_fecha_inicio + ", i_plazo=" + i_plazo + ", vc_calificacion="
				+ vc_calificacion + ", i_empleado_analiza_id=" + i_empleado_analiza_id + ", vc_frecuencia="
				+ vc_frecuencia + ", dt_fecha_vencimiento=" + dt_fecha_vencimiento + ", i_banda=" + i_banda
				+ ", vc_rango_banda=" + vc_rango_banda + ", vc_tarifa=" + vc_tarifa + ", i_estatus_activo_id="
				+ i_estatus_activo_id + ", vc_estatus_activo=" + vc_estatus_activo + ", vc_tipo_credito="
				+ vc_tipo_credito + ", vc_despacho=" + vc_despacho + ", vc_en_cobranza_judicial="
				+ vc_en_cobranza_judicial + ", vc_intencionalidad=" + vc_intencionalidad + ", i_id_exp_judicial="
				+ i_id_exp_judicial + ", i_id_despacho=" + i_id_despacho + ", i_id_ejecutivo=" + i_id_ejecutivo
				+ ", vc_nom_analista=" + vc_nom_analista + ", vc_etapa=" + vc_etapa + ", i_expediente_id="
				+ i_expediente_id + ", vc_negociador=" + vc_negociador + ", fl_gastos=" + fl_gastos
				+ ", i_cod_estatus_peticion=" + i_cod_estatus_peticion + ", i_ind_desp_interno=" + i_ind_desp_interno
				+ "]";
	}
	
	
}
