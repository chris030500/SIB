package ahorrosbienestar.sib.std.sccb.solicitudesCredito.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_SCCB_DATOS_SOLICITUD")
public class DatosSolicitudCredito {
	@Id
	@Column(name = "PK_I_ROW_GUID")
	private Integer pk_i_row_guid;
	
	@Column(name = "I_SOLICITUD_ID")
	private Integer i_solicitud_id;
	
	@Column(name="I_EMPLEADO_RECIBE_ID")
	private Integer i_empleado_recibe_id;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_RECEPCION")
	private Date dt_fecha_recepcion;
	
	@Column(name = "I_CENTRO_ID")
	private Integer i_centro_id;
	
	@Column(name = "I_NUM_CONTRATO")
	private Integer i_num_contrato;
	
	@Column(name = "I_CREDITO_ID")
	private Integer i_credito_id;
	
	@Column(name = "I_AC_DEP_RESPALDO")
	private Integer i_ac_dep_respaldo;
	
	@Column(name = "I_PRODUCTO_ID")
	private Integer i_producto_id;
	
	@Column(name = "I_EMPLEADO_ANALIZA_ID")
	private Integer i_empleado_analiza_id;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_INICIO_TRAMITE")
	private Date dt_fecha_inicio_tramite;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_AUTORIZACION")
	private Date dt_fecha_autorizacion;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_RECONSIDERACION")
	private Date dt_fecha_reconsideracion;
	
	@Column(name = "I_ETAPA")
	private Integer i_etapa;
	
	@Column(name = "VC_DESCRIPCION_ETAPA")
	private String vc_descripcion_etapa;
	
	@Column(name = "I_ESTATUS")
	private Integer i_estatus;
	
	@Column(name = "VC_DESCRIPCION_ESTATUS")
	private String vc_descripcion_estatus;
	
	@Column(name = "I_IMPORTE_CREDITO")
	private Integer i_importe_credito;
	
	@Column(name = "I_PLAZO")
	private Integer i_plazo;
	
	@Column(name = "I_FRECUENCIA_AMORTIZACION")
	private Integer i_frecuencia_amortizacion;
	
	@Column(name = "I_NUMERO_TANTOS")
	private Integer i_numero_tantos;
	
	@Column(name= "I_FINALIDAD")
	private Integer i_finalidad;
	
	@Column(name = "FL_TASA_NORMAL")
	private Float fl_tasa_normal;
	
	@Column(name = "I_TASA_MORATORIA")
	private Integer i_tasa_moratoria;
	
	@Column(name = "I_NUM_ABONOS_DIFERIDOS")
	private Integer i_num_abonos_diferidos;
	
	@Column(name = "I_PRODUCTO_ID_1")
	private Integer i_producto_id_1;
	
	@Column(name = "I_PERSONA_ID")
	private Integer i_persona_id;
	
	@Column(name = "I_NO_CLIENTE")
	private Integer i_no_cliente;
	
	@Column(name= "VC_PERSONA")
	private String vc_persona;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_RESULTADO")
	private Date dt_fecha_resultado;
	
	@Column(name = "VC_NOMBRE_COMPLETO")
	private String vc_nombre_completo;
	
	@Column(name = "I_IND_SEGURO")
	private Integer i_ind_seguro;
	
	@Column(name = "I_ID_EMPRESA")
	private Integer i_id_empresa;
	
	@Column(name = "I_ACUERDO_OPERATIVA")
	private Integer i_acuerdo_operativa;
	
	@Column(name = "I_NUM_EMP_PROMOTOR")
	private Integer i_num_emp_promotor;
	
	@Column(name = "I_IND_ASEGURADO")
	private Integer i_ind_asegurado;
	
	@Column(name = "I_TIPO_CLIENTE")
	private Integer i_tipo_cliente;
	
	@Column(name = "VC_DESCRIPCION_CLIENTE")
	private String vc_descripcion_cliente;
	
	@Column(name = "I_EMPLEADO_CAPTURA_ID")
	private Integer i_empleado_captura_id;
	
	@Column(name = "VC_NOMBRE_CENTRO")
	private String vc_nombre_centro;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_INICIO_CREDITO")
	private Date dt_fecha_inicio_credito;
	
	@Column(name = "I_IMPORTE")
	private Integer i_importe;
	
	@Column(name = "I_NUM_EMP_ASESOR")
	private Integer i_num_emp_asesor;
	
	@Column(name = "I_NUM_EMP_COORDINADOR")
	private Integer i_num_emp_coordinador;
	
	@Column(name = "I_IND_CMP_CRD")
	private Integer i_ind_cmp_crd;
	
	@Column(name = "DT_FECHA_NACIMIENTO")
	private Date dt_fecha_nacimiento;
	
	@Column(name = "I_VALOR_COMISION")
	private Integer i_valor_comision;
	
	@Column(name = "I_VALOR_AVALUO")
	private Integer i_valor_avaluo;
	
	@Column(name = "FL_CMSN_ASE_FIN")
	private Float fl_cmsn_ase_fin;
	
	@Column(name = "I_ID_SOLICITUD_IN")
	private Integer i_id_solicitud_in;
	
	@Column(name = "I_COMISION_APERTURA")
	private Integer i_comision_apertura;
	
	@Column(name = "I_TIPO_ROL_PERSONA")
	private Integer i_tipo_rol_persona;
	
	@Column (name = "I_TARIFA_GARANTIA")
	private Integer i_tarifa_garantia;
	
	@Column(name = "I_NO_CLIENTE_SAFI")
	private Integer i_no_cliente_safi;
	
	@Column(name = "I_CREDITO_ID_SAFI")
	private Long i_credito_id_safi;
	
	@Column(name = "VC_OBSERVACIONES")
	private String vc_observaciones;
	
	@Column(name = "VC_OBSERVACIONES_CC")
	private String vc_observaciones_cc;
	
	@Column(name = "VC_NOMBRE_DESTINO")
	private String vc_nombre_destino;
	
	@Column(name = "I_DEP_EN_RESPALDO")
	private Integer i_dep_en_respaldo;
	
	@Column(name = "I_CMSN_IMP_CREDITO")
	private Integer i_cmsn_imp_credito;
	
	@Column(name = "VC_DESCRIPCION_FRECUENCIA")
	private String vc_descripcion_frecuencia;
	
	@Column(name = "I_REVISION_VEHICULAR")
	private Integer i_revision_vehicular;

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

	public Integer getI_empleado_recibe_id() {
		return i_empleado_recibe_id;
	}

	public void setI_empleado_recibe_id(Integer i_empleado_recibe_id) {
		this.i_empleado_recibe_id = i_empleado_recibe_id;
	}

	public Date getDt_fecha_recepcion() {
		return dt_fecha_recepcion;
	}

	public void setDt_fecha_recepcion(Date dt_fecha_recepcion) {
		this.dt_fecha_recepcion = dt_fecha_recepcion;
	}

	public Integer getI_centro_id() {
		return i_centro_id;
	}

	public void setI_centro_id(Integer i_centro_id) {
		this.i_centro_id = i_centro_id;
	}

	public Integer getI_num_contrato() {
		return i_num_contrato;
	}

	public void setI_num_contrato(Integer i_num_contrato) {
		this.i_num_contrato = i_num_contrato;
	}

	public Integer getI_credito_id() {
		return i_credito_id;
	}

	public void setI_credito_id(Integer i_credito_id) {
		this.i_credito_id = i_credito_id;
	}

	public Integer getI_ac_dep_respaldo() {
		return i_ac_dep_respaldo;
	}

	public void setI_ac_dep_respaldo(Integer i_ac_dep_respaldo) {
		this.i_ac_dep_respaldo = i_ac_dep_respaldo;
	}

	public Integer getI_producto_id() {
		return i_producto_id;
	}

	public void setI_producto_id(Integer i_producto_id) {
		this.i_producto_id = i_producto_id;
	}

	public Integer getI_empleado_analiza_id() {
		return i_empleado_analiza_id;
	}

	public void setI_empleado_analiza_id(Integer i_empleado_analiza_id) {
		this.i_empleado_analiza_id = i_empleado_analiza_id;
	}

	public Date getDt_fecha_inicio_tramite() {
		return dt_fecha_inicio_tramite;
	}

	public void setDt_fecha_inicio_tramite(Date dt_fecha_inicio_tramite) {
		this.dt_fecha_inicio_tramite = dt_fecha_inicio_tramite;
	}

	public Date getDt_fecha_autorizacion() {
		return dt_fecha_autorizacion;
	}

	public void setDt_fecha_autorizacion(Date dt_fecha_autorizacion) {
		this.dt_fecha_autorizacion = dt_fecha_autorizacion;
	}

	public Date getDt_fecha_reconsideracion() {
		return dt_fecha_reconsideracion;
	}

	public void setDt_fecha_reconsideracion(Date dt_fecha_reconsideracion) {
		this.dt_fecha_reconsideracion = dt_fecha_reconsideracion;
	}

	public Integer getI_etapa() {
		return i_etapa;
	}

	public void setI_etapa(Integer i_etapa) {
		this.i_etapa = i_etapa;
	}

	public String getVc_descripcion_etapa() {
		return vc_descripcion_etapa;
	}

	public void setVc_descripcion_etapa(String vc_descripcion_etapa) {
		this.vc_descripcion_etapa = vc_descripcion_etapa;
	}

	public Integer getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(Integer i_estatus) {
		this.i_estatus = i_estatus;
	}

	public String getVc_descripcion_estatus() {
		return vc_descripcion_estatus;
	}

	public void setVc_descripcion_estatus(String vc_descripcion_estatus) {
		this.vc_descripcion_estatus = vc_descripcion_estatus;
	}

	public Integer getI_importe_credito() {
		return i_importe_credito;
	}

	public void setI_importe_credito(Integer i_importe_credito) {
		this.i_importe_credito = i_importe_credito;
	}

	public Integer getI_plazo() {
		return i_plazo;
	}

	public void setI_plazo(Integer i_plazo) {
		this.i_plazo = i_plazo;
	}

	public Integer getI_frecuencia_amortizacion() {
		return i_frecuencia_amortizacion;
	}

	public void setI_frecuencia_amortizacion(Integer i_frecuencia_amortizacion) {
		this.i_frecuencia_amortizacion = i_frecuencia_amortizacion;
	}

	public Integer getI_numero_tantos() {
		return i_numero_tantos;
	}

	public void setI_numero_tantos(Integer i_numero_tantos) {
		this.i_numero_tantos = i_numero_tantos;
	}

	public Integer getI_finalidad() {
		return i_finalidad;
	}

	public void setI_finalidad(Integer i_finalidad) {
		this.i_finalidad = i_finalidad;
	}

	public Float getFl_tasa_normal() {
		return fl_tasa_normal;
	}

	public void setFl_tasa_normal(Float fl_tasa_normal) {
		this.fl_tasa_normal = fl_tasa_normal;
	}

	public Integer getI_tasa_moratoria() {
		return i_tasa_moratoria;
	}

	public void setI_tasa_moratoria(Integer i_tasa_moratoria) {
		this.i_tasa_moratoria = i_tasa_moratoria;
	}

	public Integer getI_num_abonos_diferidos() {
		return i_num_abonos_diferidos;
	}

	public void setI_num_abonos_diferidos(Integer i_num_abonos_diferidos) {
		this.i_num_abonos_diferidos = i_num_abonos_diferidos;
	}

	public Integer getI_producto_id_1() {
		return i_producto_id_1;
	}

	public void setI_producto_id_1(Integer i_producto_id_1) {
		this.i_producto_id_1 = i_producto_id_1;
	}

	public Integer getI_persona_id() {
		return i_persona_id;
	}

	public void setI_persona_id(Integer i_persona_id) {
		this.i_persona_id = i_persona_id;
	}

	public Integer getI_no_cliente() {
		return i_no_cliente;
	}

	public void setI_no_cliente(Integer i_no_cliente) {
		this.i_no_cliente = i_no_cliente;
	}

	public String getVc_persona() {
		return vc_persona;
	}

	public void setVc_persona(String vc_persona) {
		this.vc_persona = vc_persona;
	}

	public Date getDt_fecha_resultado() {
		return dt_fecha_resultado;
	}

	public void setDt_fecha_resultado(Date dt_fecha_resultado) {
		this.dt_fecha_resultado = dt_fecha_resultado;
	}

	public String getVc_nombre_completo() {
		return vc_nombre_completo;
	}

	public void setVc_nombre_completo(String vc_nombre_completo) {
		this.vc_nombre_completo = vc_nombre_completo;
	}

	public Integer getI_ind_seguro() {
		return i_ind_seguro;
	}

	public void setI_ind_seguro(Integer i_ind_seguro) {
		this.i_ind_seguro = i_ind_seguro;
	}

	public Integer getI_id_empresa() {
		return i_id_empresa;
	}

	public void setI_id_empresa(Integer i_id_empresa) {
		this.i_id_empresa = i_id_empresa;
	}

	public Integer getI_acuerdo_operativa() {
		return i_acuerdo_operativa;
	}

	public void setI_acuerdo_operativa(Integer i_acuerdo_operativa) {
		this.i_acuerdo_operativa = i_acuerdo_operativa;
	}

	public Integer getI_num_emp_promotor() {
		return i_num_emp_promotor;
	}

	public void setI_num_emp_promotor(Integer i_num_emp_promotor) {
		this.i_num_emp_promotor = i_num_emp_promotor;
	}

	public Integer getI_ind_asegurado() {
		return i_ind_asegurado;
	}

	public void setI_ind_asegurado(Integer i_ind_asegurado) {
		this.i_ind_asegurado = i_ind_asegurado;
	}

	public Integer getI_tipo_cliente() {
		return i_tipo_cliente;
	}

	public void setI_tipo_cliente(Integer i_tipo_cliente) {
		this.i_tipo_cliente = i_tipo_cliente;
	}

	public String getVc_descripcion_cliente() {
		return vc_descripcion_cliente;
	}

	public void setVc_descripcion_cliente(String vc_descripcion_cliente) {
		this.vc_descripcion_cliente = vc_descripcion_cliente;
	}

	public Integer getI_empleado_captura_id() {
		return i_empleado_captura_id;
	}

	public void setI_empleado_captura_id(Integer i_empleado_captura_id) {
		this.i_empleado_captura_id = i_empleado_captura_id;
	}

	public String getVc_nombre_centro() {
		return vc_nombre_centro;
	}

	public void setVc_nombre_centro(String vc_nombre_centro) {
		this.vc_nombre_centro = vc_nombre_centro;
	}

	public Date getDt_fecha_inicio_credito() {
		return dt_fecha_inicio_credito;
	}

	public void setDt_fecha_inicio_credito(Date dt_fecha_inicio_credito) {
		this.dt_fecha_inicio_credito = dt_fecha_inicio_credito;
	}

	public Integer getI_importe() {
		return i_importe;
	}

	public void setI_importe(Integer i_importe) {
		this.i_importe = i_importe;
	}

	public Integer getI_num_emp_asesor() {
		return i_num_emp_asesor;
	}

	public void setI_num_emp_asesor(Integer i_num_emp_asesor) {
		this.i_num_emp_asesor = i_num_emp_asesor;
	}

	public Integer getI_num_emp_coordinador() {
		return i_num_emp_coordinador;
	}

	public void setI_num_emp_coordinador(Integer i_num_emp_coordinador) {
		this.i_num_emp_coordinador = i_num_emp_coordinador;
	}

	public Integer getI_ind_cmp_crd() {
		return i_ind_cmp_crd;
	}

	public void setI_ind_cmp_crd(Integer i_ind_cmp_crd) {
		this.i_ind_cmp_crd = i_ind_cmp_crd;
	}

	public Date getDt_fecha_nacimiento() {
		return dt_fecha_nacimiento;
	}

	public void setDt_fecha_nacimiento(Date dt_fecha_nacimiento) {
		this.dt_fecha_nacimiento = dt_fecha_nacimiento;
	}

	public Integer getI_valor_comision() {
		return i_valor_comision;
	}

	public void setI_valor_comision(Integer i_valor_comision) {
		this.i_valor_comision = i_valor_comision;
	}

	public Integer getI_valor_avaluo() {
		return i_valor_avaluo;
	}

	public void setI_valor_avaluo(Integer i_valor_avaluo) {
		this.i_valor_avaluo = i_valor_avaluo;
	}

	public Float getFl_cmsn_ase_fin() {
		return fl_cmsn_ase_fin;
	}

	public void setFl_cmsn_ase_fin(Float fl_cmsn_ase_fin) {
		this.fl_cmsn_ase_fin = fl_cmsn_ase_fin;
	}

	public Integer getI_id_solicitud_in() {
		return i_id_solicitud_in;
	}

	public void setI_id_solicitud_in(Integer i_id_solicitud_in) {
		this.i_id_solicitud_in = i_id_solicitud_in;
	}

	public Integer getI_comision_apertura() {
		return i_comision_apertura;
	}

	public void setI_comision_apertura(Integer i_comision_apertura) {
		this.i_comision_apertura = i_comision_apertura;
	}

	public Integer getI_tipo_rol_persona() {
		return i_tipo_rol_persona;
	}

	public void setI_tipo_rol_persona(Integer i_tipo_rol_persona) {
		this.i_tipo_rol_persona = i_tipo_rol_persona;
	}

	public Integer getI_tarifa_garantia() {
		return i_tarifa_garantia;
	}

	public void setI_tarifa_garantia(Integer i_tarifa_garantia) {
		this.i_tarifa_garantia = i_tarifa_garantia;
	}

	public Integer getI_no_cliente_safi() {
		return i_no_cliente_safi;
	}

	public void setI_no_cliente_safi(Integer i_no_cliente_safi) {
		this.i_no_cliente_safi = i_no_cliente_safi;
	}

	public Long getI_credito_id_safi() {
		return i_credito_id_safi;
	}

	public void setI_credito_id_safi(Long i_credito_id_safi) {
		this.i_credito_id_safi = i_credito_id_safi;
	}

	public String getVc_observaciones() {
		return vc_observaciones;
	}

	public void setVc_observaciones(String vc_observaciones) {
		this.vc_observaciones = vc_observaciones;
	}

	public String getVc_observaciones_cc() {
		return vc_observaciones_cc;
	}

	public void setVc_observaciones_cc(String vc_observaciones_cc) {
		this.vc_observaciones_cc = vc_observaciones_cc;
	}

	public String getVc_nombre_destino() {
		return vc_nombre_destino;
	}

	public void setVc_nombre_destino(String vc_nombre_destino) {
		this.vc_nombre_destino = vc_nombre_destino;
	}

	public Integer getI_dep_en_respaldo() {
		return i_dep_en_respaldo;
	}

	public void setI_dep_en_respaldo(Integer i_dep_en_respaldo) {
		this.i_dep_en_respaldo = i_dep_en_respaldo;
	}

	public Integer getI_cmsn_imp_credito() {
		return i_cmsn_imp_credito;
	}

	public void setI_cmsn_imp_credito(Integer i_cmsn_imp_credito) {
		this.i_cmsn_imp_credito = i_cmsn_imp_credito;
	}

	public String getVc_descripcion_frecuencia() {
		return vc_descripcion_frecuencia;
	}

	public void setVc_descripcion_frecuencia(String vc_descripcion_frecuencia) {
		this.vc_descripcion_frecuencia = vc_descripcion_frecuencia;
	}

	public Integer getI_revision_vehicular() {
		return i_revision_vehicular;
	}

	public void setI_revision_vehicular(Integer i_revision_vehicular) {
		this.i_revision_vehicular = i_revision_vehicular;
	}

	@Override
	public String toString() {
		return "DatosSolicitudCredito [pk_i_row_guid=" + pk_i_row_guid + ", i_solicitud_id=" + i_solicitud_id
				+ ", i_empleado_recibe_id=" + i_empleado_recibe_id + ", dt_fecha_recepcion=" + dt_fecha_recepcion
				+ ", i_centro_id=" + i_centro_id + ", i_num_contrato=" + i_num_contrato + ", i_credito_id="
				+ i_credito_id + ", i_ac_dep_respaldo=" + i_ac_dep_respaldo + ", i_producto_id=" + i_producto_id
				+ ", i_empleado_analiza_id=" + i_empleado_analiza_id + ", dt_fecha_inicio_tramite="
				+ dt_fecha_inicio_tramite + ", dt_fecha_autorizacion=" + dt_fecha_autorizacion
				+ ", dt_fecha_reconsideracion=" + dt_fecha_reconsideracion + ", i_etapa=" + i_etapa
				+ ", vc_descripcion_etapa=" + vc_descripcion_etapa + ", i_estatus=" + i_estatus
				+ ", vc_descripcion_estatus=" + vc_descripcion_estatus + ", i_importe_credito=" + i_importe_credito
				+ ", i_plazo=" + i_plazo + ", i_frecuencia_amortizacion=" + i_frecuencia_amortizacion
				+ ", i_numero_tantos=" + i_numero_tantos + ", i_finalidad=" + i_finalidad + ", fl_tasa_normal="
				+ fl_tasa_normal + ", i_tasa_moratoria=" + i_tasa_moratoria + ", i_num_abonos_diferidos="
				+ i_num_abonos_diferidos + ", i_producto_id_1=" + i_producto_id_1 + ", i_persona_id=" + i_persona_id
				+ ", i_no_cliente=" + i_no_cliente + ", vc_persona=" + vc_persona + ", dt_fecha_resultado="
				+ dt_fecha_resultado + ", vc_nombre_completo=" + vc_nombre_completo + ", i_ind_seguro=" + i_ind_seguro
				+ ", i_id_empresa=" + i_id_empresa + ", i_acuerdo_operativa=" + i_acuerdo_operativa
				+ ", i_num_emp_promotor=" + i_num_emp_promotor + ", i_ind_asegurado=" + i_ind_asegurado
				+ ", i_tipo_cliente=" + i_tipo_cliente + ", vc_descripcion_cliente=" + vc_descripcion_cliente
				+ ", i_empleado_captura_id=" + i_empleado_captura_id + ", vc_nombre_centro=" + vc_nombre_centro
				+ ", dt_fecha_inicio_credito=" + dt_fecha_inicio_credito + ", i_importe=" + i_importe
				+ ", i_num_emp_asesor=" + i_num_emp_asesor + ", i_num_emp_coordinador=" + i_num_emp_coordinador
				+ ", i_ind_cmp_crd=" + i_ind_cmp_crd + ", dt_fecha_nacimiento=" + dt_fecha_nacimiento
				+ ", i_valor_comision=" + i_valor_comision + ", i_valor_avaluo=" + i_valor_avaluo + ", fl_cmsn_ase_fin="
				+ fl_cmsn_ase_fin + ", i_id_solicitud_in=" + i_id_solicitud_in + ", i_comision_apertura="
				+ i_comision_apertura + ", i_tipo_rol_persona=" + i_tipo_rol_persona + ", i_tarifa_garantia="
				+ i_tarifa_garantia + ", i_no_cliente_safi=" + i_no_cliente_safi + ", i_credito_id_safi="
				+ i_credito_id_safi + ", vc_observaciones=" + vc_observaciones + ", vc_observaciones_cc="
				+ vc_observaciones_cc + ", vc_nombre_destino=" + vc_nombre_destino + ", i_dep_en_respaldo="
				+ i_dep_en_respaldo + ", i_cmsn_imp_credito=" + i_cmsn_imp_credito + ", vc_descripcion_frecuencia="
				+ vc_descripcion_frecuencia + ", i_revision_vehicular=" + i_revision_vehicular + "]";
	}
	
}
