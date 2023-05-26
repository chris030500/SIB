package ahorrosbienestar.sib.std.autorizacion.descargas.docto_video.model;

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

import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;

@Entity
@Table(name = "TBL_DOCTO_VIDEO")
public class DoctoVideoModel {
	
	@Id
	@Column(name = "PK_ID_DOCTO_VIDEO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_docto_video_seq")
	@SequenceGenerator(name = "tbl_docto_video_seq", sequenceName = "tbl_docto_video_seq", initialValue = 1, allocationSize = 1)
	private int pk_id_docto_envio;
	
	@Column(name = "DT_FECHA_ENVIO")
	private LocalDate dt_fecha_envio;
	
	@Column(name = "VC_RUTA_DOCTO")
	private String vc_ruta_docto;
	
	@Column(name = "I_ESTATUS")
	private int i_estatus;
	
	//@OneToMany
	//@OneToOne
	//@ManyToOne
	//@OneToOne
	@ManyToOne
	@JoinColumn(name = "FK_SOLICITUD_ENVIO")
	private SolicitudEnvio fk_solicitud_envio;

	public int getPk_id_docto_envio() {
		return pk_id_docto_envio;
	}

	public void setPk_id_docto_envio(int pk_id_docto_envio) {
		this.pk_id_docto_envio = pk_id_docto_envio;
	}

	public LocalDate getDt_fecha_envio() {
		return dt_fecha_envio;
	}

	public void setDt_fecha_envio(LocalDate dt_fecha_envio) {
		this.dt_fecha_envio = dt_fecha_envio;
	}

	public String getVc_ruta_docto() {
		return vc_ruta_docto;
	}

	public void setVc_ruta_docto(String vc_ruta_docto) {
		this.vc_ruta_docto = vc_ruta_docto;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public SolicitudEnvio getFk_solicitud_envio() {
		return fk_solicitud_envio;
	}

	public void setFk_solicitud_envio(SolicitudEnvio fk_solicitud_envio) {
		this.fk_solicitud_envio = fk_solicitud_envio;
	}

}
