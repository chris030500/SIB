package ahorrosbienestar.sib.std.sccb.solicitudesCredito.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_SCCB_SOLICITUDES")
public class SolicitudesCredito {
	@Column(name = "I_SOLICITUD_ID")
	private int i_solicitud_id;
	
	@Column(name = "I_CREDITO_ID")
	private int i_credito_id;
	
	@Column(name = "VC_NOMBRE_CLIENTE")
	private String vc_nombre_cliente;
	
	@Column(name = "I_NO_CLIENTE")
	private int i_no_cliente;
	
	@Column(name = "VC_ROL")
	private String vc_rol;
	
	@Column(name = "I_CENTRO")
	private int i_centro;
	
	@Column(name = "VC_ETAPA")
	private String vc_etapa;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;
	
	@Column(name = "FL_MONTO")
	private float fl_monto;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_RECEPCION")
	private LocalDate vc_fecha_recepcion;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_INICIO_TRAMITE")
	private LocalDate vc_fecha_inicio_tramite;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_AUTORIZACION")
	private LocalDate vc_fecha_autorizacion;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_DESEMBOLSO")
	private LocalDate vc_fecha_desembolso;
	
	@Column(name = "VC_NOMBRE_CENTRO")
	private String vc_nombre_centro;
	
	public int getI_solicitud_id() {
		return i_solicitud_id;
	}

	public void setI_solicitud_id(int i_solicitud_id) {
		this.i_solicitud_id = i_solicitud_id;
	}

	public int getI_credito_id() {
		return i_credito_id;
	}

	public void setI_credito_id(int i_credito_id) {
		this.i_credito_id = i_credito_id;
	}

	public String getVc_nombre_cliente() {
		return vc_nombre_cliente;
	}

	public void setVc_nombre_cliente(String vc_nombre_cliente) {
		this.vc_nombre_cliente = vc_nombre_cliente;
	}

	public int getI_no_cliente() {
		return i_no_cliente;
	}

	public void setI_no_cliente(int i_no_cliente) {
		this.i_no_cliente = i_no_cliente;
	}

	public String getVc_rol() {
		return vc_rol;
	}

	public void setVc_rol(String vc_rol) {
		this.vc_rol = vc_rol;
	}

	public int getI_centro() {
		return i_centro;
	}

	public void setI_centro(int i_centro) {
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

	public float getFl_monto() {
		return fl_monto;
	}

	public void setFl_monto(float fl_monto) {
		this.fl_monto = fl_monto;
	}

	public LocalDate getVc_fecha_recepcion() {
		return vc_fecha_recepcion;
	}

	public void setVc_fecha_recepcion(LocalDate vc_fecha_recepcion) {
		this.vc_fecha_recepcion = vc_fecha_recepcion;
	}

	public LocalDate getVc_fecha_inicio_tramite() {
		return vc_fecha_inicio_tramite;
	}

	public void setVc_fecha_inicio_tramite(LocalDate vc_fecha_inicio_tramite) {
		this.vc_fecha_inicio_tramite = vc_fecha_inicio_tramite;
	}

	public LocalDate getVc_fecha_autorizacion() {
		return vc_fecha_autorizacion;
	}

	public void setVc_fecha_autorizacion(LocalDate vc_fecha_autorizacion) {
		this.vc_fecha_autorizacion = vc_fecha_autorizacion;
	}

	public LocalDate getVc_fecha_desembolso() {
		return vc_fecha_desembolso;
	}

	public void setVc_fecha_desembolso(LocalDate vc_fecha_desembolso) {
		this.vc_fecha_desembolso = vc_fecha_desembolso;
	}

	public String getVc_nombre_centro() {
		return vc_nombre_centro;
	}

	public void setVc_nombre_centro(String vc_nombre_centro) {
		this.vc_nombre_centro = vc_nombre_centro;
	}
		
	
}
