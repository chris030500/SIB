package ahorrosbienestar.sib.std.sccb.expedientes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_SCCB_EXPEDIENTE_CREDITO")
public class ExpedienteCredito {
	
	
	@Id
	@Column(name = "PK_I_ROW_GUID")
	private Integer pk_i_row_guid;
	
	@Column(name = "I_EXPEDIENTE_ID")
	private Integer i_expediente_id;
	
	@Column(name = "I_TIPO_EXPEDIENTE_ID")
	private Integer i_tipo_expediente_id;
	
	@Column(name = "VC_TIPO_EXPEDIENTE")
	private String vc_tipo_expediente;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_CREACION")
	private Date dt_fecha_creacion;
	
	@Column(name = "I_NO_CLIENTE")
	private Integer i_no_cliente;
	
	@Column(name = "VC_NOMBRE_CLIENTE")
	private String vc_nombre_cliente;
	
	@Column(name = "I_SOLICITUD_ID")
	private Integer i_solicitud_id;
	
	@Column(name = "I_CREDITO_ID")
	private Integer i_credito_id;
	
	@Column(name = "I_NO_CLIENTE_SAFI")
	private Integer i_no_cliente_safi;
	
	@Column(name = "I_NO_CREDITO_SAFI")
	private Long i_no_credito_safi;
	
	@Column(name = "I_ESTATUS_ID")
	private Integer i_estatus_id;
	
	@Column(name = "VC_ESTATUS_DESC_ESTATUS")
	private String vc_desc_estatus;
	
	@Column(name = "I_SITUACION_ID")
	private Integer i_situacion_id;
	
	@Column(name = "VC_DESC_SITUACION")
	private String vc_desc_situacion;

	public Integer getPk_i_row_guid() {
		return pk_i_row_guid;
	}

	public void setPk_i_row_guid(Integer pk_i_row_guid) {
		this.pk_i_row_guid = pk_i_row_guid;
	}

	public Integer getI_expediente_id() {
		return i_expediente_id;
	}

	public void setI_expediente_id(Integer i_expediente_id) {
		this.i_expediente_id = i_expediente_id;
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

	public Date getDt_fecha_creacion() {
		return dt_fecha_creacion;
	}

	public void setDt_fecha_creacion(Date dt_fecha_creacion) {
		this.dt_fecha_creacion = dt_fecha_creacion;
	}

	public Integer getI_no_cliente() {
		return i_no_cliente;
	}

	public void setI_no_cliente(Integer i_no_cliente) {
		this.i_no_cliente = i_no_cliente;
	}

	public String getVc_nombre_cliente() {
		return vc_nombre_cliente;
	}

	public void setVc_nombre_cliente(String vc_nombre_cliente) {
		this.vc_nombre_cliente = vc_nombre_cliente;
	}

	public Integer getI_solicitud_id() {
		return i_solicitud_id;
	}

	public void setI_solicitud_id(Integer i_solicitud_id) {
		this.i_solicitud_id = i_solicitud_id;
	}

	public Integer getI_credito_id() {
		return i_credito_id;
	}

	public void setI_credito_id(Integer i_credito_id) {
		this.i_credito_id = i_credito_id;
	}

	public Integer getI_no_cliente_safi() {
		return i_no_cliente_safi;
	}

	public void setI_no_cliente_safi(Integer i_no_cliente_safi) {
		this.i_no_cliente_safi = i_no_cliente_safi;
	}

	public Long getI_no_credito_safi() {
		return i_no_credito_safi;
	}

	public void setI_no_credito_safi(Long i_no_credito_safi) {
		this.i_no_credito_safi = i_no_credito_safi;
	}
	
	public Integer getI_estatus_id() {
		return i_estatus_id;
	}

	public void setI_estatus_id(Integer i_estatus_id) {
		this.i_estatus_id = i_estatus_id;
	}

	public String getVc_desc_estatus() {
		return vc_desc_estatus;
	}

	public void setVc_desc_estatus(String vc_desc_estatus) {
		this.vc_desc_estatus = vc_desc_estatus;
	}

	public Integer getI_situacion_id() {
		return i_situacion_id;
	}

	public void setI_situacion_id(Integer i_situacion_id) {
		this.i_situacion_id = i_situacion_id;
	}

	public String getVc_desc_situacion() {
		return vc_desc_situacion;
	}

	public void setVc_desc_situacion(String vc_desc_situacion) {
		this.vc_desc_situacion = vc_desc_situacion;
	}

	@Override
	public String toString() {
		return "ExpedienteCredito [pk_i_row_guid=" + pk_i_row_guid + ", i_expediente_id=" + i_expediente_id
				+ ", i_tipo_expediente_id=" + i_tipo_expediente_id + ", vc_tipo_expediente=" + vc_tipo_expediente
				+ ", dt_fecha_creacion=" + dt_fecha_creacion + ", i_no_cliente=" + i_no_cliente + ", vc_nombre_cliente="
				+ vc_nombre_cliente + ", i_solicitud_id=" + i_solicitud_id + ", i_credito_id=" + i_credito_id
				+ ", i_no_cliente_safi=" + i_no_cliente_safi + ", i_no_credito_safi=" + i_no_credito_safi
				+ ", i_estatus_id=" + i_estatus_id + ", vc_desc_estatus=" + vc_desc_estatus + ", i_situacion_id="
				+ i_situacion_id + ", vc_desc_situacion=" + vc_desc_situacion + "]";
	}
	
	
	
}
