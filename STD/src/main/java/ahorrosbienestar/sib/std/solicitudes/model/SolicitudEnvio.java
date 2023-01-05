package ahorrosbienestar.sib.std.solicitudes.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_SOLICITUD_DE_ENVIO")
public class SolicitudEnvio {

	@Column(name = "PK_ID_SOLICITUD_ENVIO")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "solicitud_envio_seq")
	@SequenceGenerator(name = "solicitud_envio_seq", sequenceName = "solicitud_envio_seq", initialValue = 1, allocationSize = 1)
	private int pk_id_solicitud_envio;

	@Column(name = "I_NO_SOLICITUD_CORE")
	private int i_no_solicitud_core;

	@Column(name = "I_PRODUCTO_ID")
	private int i_producto_id;

	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;

	@Column(name = "VC_OBSER_PETICION", columnDefinition = "VARCHAR(200)")
	private String vc_obser_peticion;

	@Column(name = "VC_OBSER_AUTORIZA", columnDefinition = "VARCHAR(200)")
	private String vc_obser_autoriza;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_AUTORIZACION")
	private LocalDate dt_fecha_autorizacion;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_ENVIO")
	private LocalDate dt_fecha_envio;

	@Column(name = "VC_HORA_ENVIO", columnDefinition = "VARCHAR(45)")
	private String vc_hora_envio;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_CANCELACION")
	private LocalDate dt_fecha_cancelacion;

	@Column(name = "VC_HORA_CANCELACION", columnDefinition = "VARCHAR(45)")
	private String vc_hora_cancelacion;

	@OneToOne
	@JoinColumn(name = "FK_USR_REGISTRA")
	private Usuario fk_usr_registra;

	@OneToOne
	@JoinColumn(name = "FK_USR_AUTORIZA")
	private Usuario fk_usr_autoriza;

	@OneToOne
	@JoinColumn(name = "FK_USR_CANCELA")
	private Usuario fk_usr_cancela;

	public int getPk_id_solicitud_envio() {
		return pk_id_solicitud_envio;
	}

	public void setPk_id_solicitud_envio(int pk_id_solicitud_envio) {
		this.pk_id_solicitud_envio = pk_id_solicitud_envio;
	}

	public int getI_no_solicitud_core() {
		return i_no_solicitud_core;
	}

	public void setI_no_solicitud_core(int i_no_solicitud_core) {
		this.i_no_solicitud_core = i_no_solicitud_core;
	}

	public int getI_producto_id() {
		return i_producto_id;
	}

	public void setI_producto_id(int i_producto_id) {
		this.i_producto_id = i_producto_id;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public String getVc_obser_peticion() {
		return vc_obser_peticion;
	}

	public void setVc_obser_peticion(String vc_obser_peticion) {
		this.vc_obser_peticion = vc_obser_peticion;
	}

	public String getVc_obser_autoriza() {
		return vc_obser_autoriza;
	}

	public void setVc_obser_autoriza(String vc_obser_autoriza) {
		this.vc_obser_autoriza = vc_obser_autoriza;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public LocalDate getDt_fecha_autorizacion() {
		return dt_fecha_autorizacion;
	}

	public void setDt_fecha_autorizacion(LocalDate dt_fecha_autorizacion) {
		this.dt_fecha_autorizacion = dt_fecha_autorizacion;
	}

	public LocalDate getDt_fecha_envio() {
		return dt_fecha_envio;
	}

	public void setDt_fecha_envio(LocalDate dt_fecha_envio) {
		this.dt_fecha_envio = dt_fecha_envio;
	}

	public String getVc_hora_envio() {
		return vc_hora_envio;
	}

	public void setVc_hora_envio(String vc_hora_envio) {
		this.vc_hora_envio = vc_hora_envio;
	}

	public LocalDate getDt_fecha_cancelacion() {
		return dt_fecha_cancelacion;
	}

	public void setDt_fecha_cancelacion(LocalDate dt_fecha_cancelacion) {
		this.dt_fecha_cancelacion = dt_fecha_cancelacion;
	}

	public String getVc_hora_cancelacion() {
		return vc_hora_cancelacion;
	}

	public void setVc_hora_cancelacion(String vc_hora_cancelacion) {
		this.vc_hora_cancelacion = vc_hora_cancelacion;
	}

	public Usuario getFk_usr_registra() {
		return fk_usr_registra;
	}

	public void setFk_usr_registra(Usuario fk_usr_registra) {
		this.fk_usr_registra = fk_usr_registra;
	}

	public Usuario getFk_usr_autoriza() {
		return fk_usr_autoriza;
	}

	public void setFk_usr_autoriza(Usuario fk_usr_autoriza) {
		this.fk_usr_autoriza = fk_usr_autoriza;
	}

	public Usuario getFk_usr_cancela() {
		return fk_usr_cancela;
	}

	public void setFk_usr_cancela(Usuario fk_usr_cancela) {
		this.fk_usr_cancela = fk_usr_cancela;
	}

	
	
}
