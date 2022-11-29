package ahorrosbienestar.sib.std.docto_envio.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.documentos.model.Documento;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_DOCTO_ENVIO")
public class DoctEnvio {
	
	@Column(name = "PK_ID_DOCTO_ENVIO")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "docto_envio_seq")
	@SequenceGenerator(name = "docto_envio_seq", sequenceName = "docto_envio_seq", initialValue = 1, allocationSize = 1)
	private int pk_id_docto_envio;
	
	@ManyToOne
	@JoinColumn(name = "FK_SOLICITUD_ENVIO")
	private SolicitudEnvio fk_solicitud_envio;
	
	@ManyToOne
	@JoinColumn(name = "FK_CAT_DOCUMENTO")
	private Documento fk_cat_documento;
	
	@Column(name = "I_TP_DOCTO_ENVIO")
	private int i_tp_docto_envio;
	
	@Column(name = "VC_RUTA_DOCTO")
	private String vc_ruta_docto;
	
	@Column(name = "I_TOTAL_HOJAS")
	private int i_total_hojas;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;
	
	@OneToOne
	@JoinColumn(name = "PK_USR_REGISTRA")
	private Usuario pk_usr_registra;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_CANCELACION")
	private LocalDate dt_fecha_cancelacion;
	
	@OneToOne
	@JoinColumn(name = "PK_USR_CANCELA")
	private Usuario pk_usr_cancela;

	public int getPk_id_docto_envio() {
		return pk_id_docto_envio;
	}

	public void setPk_id_docto_envio(int pk_id_docto_envio) {
		this.pk_id_docto_envio = pk_id_docto_envio;
	}

	public SolicitudEnvio getFk_solicitud_envio() {
		return fk_solicitud_envio;
	}

	public void setFk_solicitud_envio(SolicitudEnvio fk_solicitud_envio) {
		this.fk_solicitud_envio = fk_solicitud_envio;
	}

	public Documento getFk_cat_documento() {
		return fk_cat_documento;
	}

	public void setFk_cat_documento(Documento fk_cat_documento) {
		this.fk_cat_documento = fk_cat_documento;
	}

	public int getI_tp_docto_envio() {
		return i_tp_docto_envio;
	}

	public void setI_tp_docto_envio(int i_tp_docto_envio) {
		this.i_tp_docto_envio = i_tp_docto_envio;
	}

	public String getVc_ruta_docto() {
		return vc_ruta_docto;
	}

	public void setVc_ruta_docto(String vc_ruta_docto) {
		this.vc_ruta_docto = vc_ruta_docto;
	}

	public int getI_total_hojas() {
		return i_total_hojas;
	}

	public void setI_total_hojas(int i_total_hojas) {
		this.i_total_hojas = i_total_hojas;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public Usuario getPk_usr_registra() {
		return pk_usr_registra;
	}

	public void setPk_usr_registra(Usuario pk_usr_registra) {
		this.pk_usr_registra = pk_usr_registra;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public LocalDate getDt_fecha_cancelacion() {
		return dt_fecha_cancelacion;
	}

	public void setDt_fecha_cancelacion(LocalDate dt_fecha_cancelacion) {
		this.dt_fecha_cancelacion = dt_fecha_cancelacion;
	}

	public Usuario getPk_usr_cancela() {
		return pk_usr_cancela;
	}

	public void setPk_usr_cancela(Usuario pk_usr_cancela) {
		this.pk_usr_cancela = pk_usr_cancela;
	}
	
	
}
