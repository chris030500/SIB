package ahorrosbienestar.sib.std.sccb.historialCobranza.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_SCCB_HISTORIAL_ASIGNACION")
public class HistorialAsignacion {
	
	@Id
	@Column(name = "PK_I_ROW_GUID")
	private Integer pk_i_row_guid;
	
	@Column(name = "I_CREDITO_ID")
	private Integer i_credito_id;
	
	@Column(name = "I_EXPEDIENTE_ID")
	private Integer i_expediente_id;
	
	@Column(name = "VC_EDO_ACUERDO")
	private String vc_edo_acuerdo;
	
	@Column(name = "I_AC_ORIGINAL")
	private Integer i_ac_original;
	
	@Column(name = "I_EXP_ANTERIOR")
	private Integer i_exp_anterior;
	
	@Column(name = "I_TIPO_EXPEDIENTE_ID")
	private Integer i_tipo_expediente_id;
	
	@Column(name = "VC_TIPO_EXPEDIENTE")
	private String vc_tipo_expediente;
	
	@Column(name = "I_NEGOCIADOR_ASIGNADO")
	private Integer i_negociador_asignado;
	
	@Column(name = "VC_NOMBRE_NEGOCIADOR")
	private String vc_nombre_negociador;
	
	@Column(name = "I_EMPLEADO_CREO")
	private Integer i_empleado_creo;
	
	@Column(name = "VC_NOMBRE_CREADOR")
	private String vc_nombre_creador;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_REGISTRO")
	private Date dt_fecha_registro;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_CIERRE")
	private Date dt_fecha_cierre;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;
	
	@Column(name = "I_ID_EXP_JUDICIAL")
	private Integer i_id_exp_judicial;
	
	@Column(name = "VC_NUM_EXP_JUDICIAL")
	private String vc_num_exp_judicial;
	
	@Column(name = "I_EMP_ASIG_REASIG")
	private Integer i_emp_asig_reasig;
	
	@Column(name = "VC_NOM_EMP_ASIG_REASIG")
	private String vc_nom_emp_asig_reasig;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_ASIG_REASIG")
	private Date dt_fecha_asig_reasig;
	
	@Column(name = "I_ESTATUS_EXPEDIENTE")
	private Integer i_estatus_expediente;
	
	@Column(name = "I_IND_DESP_INTERNO")
	private Integer i_ind_desp_interno;
	
	@Column(name = "I_CREDITO_ID_SAFI")
	private Long i_credito_id_safi;

	public Integer getPk_i_row_guid() {
		return pk_i_row_guid;
	}

	public void setPk_i_row_guid(Integer pk_i_row_guid) {
		this.pk_i_row_guid = pk_i_row_guid;
	}

	public Integer getI_credito_id() {
		return i_credito_id;
	}

	public void setI_credito_id(Integer i_credito_id) {
		this.i_credito_id = i_credito_id;
	}

	public Integer getI_expediente_id() {
		return i_expediente_id;
	}

	public void setI_expediente_id(Integer i_expediente_id) {
		this.i_expediente_id = i_expediente_id;
	}

	public String getVc_edo_acuerdo() {
		return vc_edo_acuerdo;
	}

	public void setVc_edo_acuerdo(String vc_edo_acuerdo) {
		this.vc_edo_acuerdo = vc_edo_acuerdo;
	}

	public Integer getI_ac_original() {
		return i_ac_original;
	}

	public void setI_ac_original(Integer i_ac_original) {
		this.i_ac_original = i_ac_original;
	}

	public Integer getI_exp_anterior() {
		return i_exp_anterior;
	}

	public void setI_exp_anterior(Integer i_exp_anterior) {
		this.i_exp_anterior = i_exp_anterior;
	}

	public Integer getI_tipo_expediente_id() {
		return i_tipo_expediente_id;
	}

	public void setI_tipo_expediente_id(Integer i_tipo_expediente_id) {
		this.i_tipo_expediente_id = i_tipo_expediente_id;
	}

	public String getVc_tipo_expediente() {
		return vc_tipo_expediente;
	}

	public void setVc_tipo_expediente(String vc_tipo_expediente) {
		this.vc_tipo_expediente = vc_tipo_expediente;
	}

	public Integer getI_negociador_asignado() {
		return i_negociador_asignado;
	}

	public void setI_negociador_asignado(Integer i_negociador_asignado) {
		this.i_negociador_asignado = i_negociador_asignado;
	}

	public String getVc_nombre_negociador() {
		return vc_nombre_negociador;
	}

	public void setVc_nombre_negociador(String vc_nombre_negociador) {
		this.vc_nombre_negociador = vc_nombre_negociador;
	}

	public Integer getI_empleado_creo() {
		return i_empleado_creo;
	}

	public void setI_empleado_creo(Integer i_empleado_creo) {
		this.i_empleado_creo = i_empleado_creo;
	}

	public String getVc_nombre_creador() {
		return vc_nombre_creador;
	}

	public void setVc_nombre_creador(String vc_nombre_creador) {
		this.vc_nombre_creador = vc_nombre_creador;
	}

	public Date getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(Date dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public Date getDt_fecha_cierre() {
		return dt_fecha_cierre;
	}

	public void setDt_fecha_cierre(Date dt_fecha_cierre) {
		this.dt_fecha_cierre = dt_fecha_cierre;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}

	public Integer getI_id_exp_judicial() {
		return i_id_exp_judicial;
	}

	public void setI_id_exp_judicial(Integer i_id_exp_judicial) {
		this.i_id_exp_judicial = i_id_exp_judicial;
	}

	public String getVc_num_exp_judicial() {
		return vc_num_exp_judicial;
	}

	public void setVc_num_exp_judicial(String vc_num_exp_judicial) {
		this.vc_num_exp_judicial = vc_num_exp_judicial;
	}

	public Integer getI_emp_asig_reasig() {
		return i_emp_asig_reasig;
	}

	public void setI_emp_asig_reasig(Integer i_emp_asig_reasig) {
		this.i_emp_asig_reasig = i_emp_asig_reasig;
	}

	public String getVc_nom_emp_asig_reasig() {
		return vc_nom_emp_asig_reasig;
	}

	public void setVc_nom_emp_asig_reasig(String vc_nom_emp_asig_reasig) {
		this.vc_nom_emp_asig_reasig = vc_nom_emp_asig_reasig;
	}

	public Date getDt_fecha_asig_reasig() {
		return dt_fecha_asig_reasig;
	}

	public void setDt_fecha_asig_reasig(Date dt_fecha_asig_reasig) {
		this.dt_fecha_asig_reasig = dt_fecha_asig_reasig;
	}

	public Integer getI_estatus_expediente() {
		return i_estatus_expediente;
	}

	public void setI_estatus_expediente(Integer i_estatus_expediente) {
		this.i_estatus_expediente = i_estatus_expediente;
	}

	public Integer getI_ind_desp_interno() {
		return i_ind_desp_interno;
	}

	public void setI_ind_desp_interno(Integer i_ind_desp_interno) {
		this.i_ind_desp_interno = i_ind_desp_interno;
	}

	public Long getI_credito_id_safi() {
		return i_credito_id_safi;
	}

	public void setI_credito_id_safi(Long i_credito_id_safi) {
		this.i_credito_id_safi = i_credito_id_safi;
	}

	@Override
	public String toString() {
		return "HistorialAsignacion [pk_i_row_guid=" + pk_i_row_guid + ", i_credito_id=" + i_credito_id
				+ ", i_expediente_id=" + i_expediente_id + ", vc_edo_acuerdo=" + vc_edo_acuerdo + ", i_ac_original="
				+ i_ac_original + ", i_exp_anterior=" + i_exp_anterior + ", i_tipo_expediente_id="
				+ i_tipo_expediente_id + ", vc_tipo_expediente=" + vc_tipo_expediente + ", i_negociador_asiganado="
				+ i_negociador_asignado + ", vc_nombre_negociador=" + vc_nombre_negociador + ", i_empleado_creo="
				+ i_empleado_creo + ", vc_nombre_creador=" + vc_nombre_creador + ", dt_fecha_registro="
				+ dt_fecha_registro + ", dt_fecha_cierre=" + dt_fecha_cierre + ", vc_estatus=" + vc_estatus
				+ ", i_id_exp_judicial=" + i_id_exp_judicial + ", vc_num_exp_judicial=" + vc_num_exp_judicial
				+ ", i_emp_asig_reasig=" + i_emp_asig_reasig + ", vc_nom_emp_asig_reasig=" + vc_nom_emp_asig_reasig
				+ ", dt_fecha_asig_reasig=" + dt_fecha_asig_reasig + ", i_estatus_expediente=" + i_estatus_expediente
				+ ", i_ind_desp_interno=" + i_ind_desp_interno + ", i_credito_id_safi=" + i_credito_id_safi + "]";
	}
	
	
}

