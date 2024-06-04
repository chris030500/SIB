package ahorrosbienestar.sib.std.sccb.solicitudesCredito.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_SCCB_SOLICITUDES")
public class SolicitudesCredito {
	
	@Id
	@Column(name = "PK_I_ROW_GUID")
	private Integer pk_i_row_guid;
	
	@Column(name = "I_SOLICITUD_ID")
	private Integer i_solicitud_id;
	
	@Column(name = "I_CREDITO_ID")
	private Integer i_credito_id;
	
	@Column(name = "VC_NOMBRE_CLIENTE")
	private String vc_nombre_cliente;
	
	@Column(name = "I_NO_CLIENTE")
	private Integer i_no_cliente;
	
	@Column(name = "VC_ROL")
	private String vc_rol;
	
	@Column(name = "I_CENTRO")
	private Integer i_centro;
	
	@Column(name = "VC_ETAPA")
	private String vc_etapa;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;
	
	@Column(name = "FL_MONTO")
	private Float fl_monto;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_RECEPCION")
	private Date dt_fecha_recepcion;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_INICIO_TRAMITE")
	private Date dt_fecha_inicio_tramite;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_AUTORIZACION")
	private Date dt_fecha_autorizacion;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_DESEMBOLSO")
	private Date dt_fecha_desembolso;
	
	@Column(name = "I_NO_CLIENTE_SAFI")
	private Integer i_no_cliente_safi;
	
	@Column(name = "I_CREDITO_ID_SAFI")
	private Long i_credito_id_safi;
	
	@Column(name = "VC_NOMBRE_CENTRO")
	private String vc_nombre_centro;

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

	public Integer getI_credito_id() {
		return i_credito_id;
	}

	public void setI_credito_id(Integer i_credito_id) {
		this.i_credito_id = i_credito_id;
	}

	public String getVc_nombre_cliente() {
		return vc_nombre_cliente;
	}

	public void setVc_nombre_cliente(String vc_nombre_cliente) {
		this.vc_nombre_cliente = vc_nombre_cliente;
	}

	public Integer getI_no_cliente() {
		return i_no_cliente;
	}

	public void setI_no_cliente(Integer i_no_cliente) {
		this.i_no_cliente = i_no_cliente;
	}

	public String getVc_rol() {
		return vc_rol;
	}

	public void setVc_rol(String vc_rol) {
		this.vc_rol = vc_rol;
	}

	public Integer getI_centro() {
		return i_centro;
	}

	public void setI_centro(Integer i_centro) {
		this.i_centro = i_centro;
	}

	public String getVc_etapa() {
		return vc_etapa;
	}

	public void setVc_etapa(String vc_etapa) {
		this.vc_etapa = vc_etapa;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}

	public Float getFl_monto() {
		return fl_monto;
	}

	public void setFl_monto(Float fl_monto) {
		this.fl_monto = fl_monto;
	}
	
	

	public Date getDt_fecha_recepcion() {
		return dt_fecha_recepcion;
	}

	public void setDt_fecha_recepcion(Date dt_fecha_recepcion) {
		this.dt_fecha_recepcion = dt_fecha_recepcion;
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

	public Date getDt_fecha_desembolso() {
		return dt_fecha_desembolso;
	}

	public void setDt_fecha_desembolso(Date dt_fecha_desembolso) {
		this.dt_fecha_desembolso = dt_fecha_desembolso;
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

	public String getVc_nombre_centro() {
		return vc_nombre_centro;
	}

	public void setVc_nombre_centro(String vc_nombre_centro) {
		this.vc_nombre_centro = vc_nombre_centro;
	}

	@Override
	public String toString() {
		return "SolicitudesCredito [pk_i_row_guid=" + pk_i_row_guid + ", i_solicitud_id=" + i_solicitud_id
				+ ", i_credito_id=" + i_credito_id + ", vc_nombre_cliente=" + vc_nombre_cliente + ", i_no_cliente="
				+ i_no_cliente + ", vc_rol=" + vc_rol + ", i_centro=" + i_centro + ", vc_etapa=" + vc_etapa
				+ ", vc_estatus=" + vc_estatus + ", fl_monto=" + fl_monto + ", dt_fecha_recepcion=" + dt_fecha_recepcion
				+ ", dt_fecha_inicio_tramite=" + dt_fecha_inicio_tramite + ", dt_fecha_autorizacion="
				+ dt_fecha_autorizacion + ", dt_fecha_desembolso=" + dt_fecha_desembolso + ", i_no_cliente_safi="
				+ i_no_cliente_safi + ", i_credito_id_safi=" + i_credito_id_safi + ", vc_nombre_centro="
				+ vc_nombre_centro + "]";
	}
	
	
	

}
