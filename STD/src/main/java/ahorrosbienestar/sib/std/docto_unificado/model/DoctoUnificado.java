package ahorrosbienestar.sib.std.docto_unificado.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_DOCTO_UNIFICADO")
public class DoctoUnificado {
	
	@Column(name = "PK_ID_DOCTO_UNIFICADO")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "docto_unificado_seq")
	@SequenceGenerator(name = "docto_unificado_seq", sequenceName = "docto_unificado_seq", initialValue = 1, allocationSize = 1)
	private int pk_id_docto_unificado;
	
	@ManyToOne
	@JoinColumn(name = "FK_ID_SOLICITUD_ENVIO")
	private SolicitudEnvio fk_id_solicitud_envio;
	
	@Column(name = "I_TOTAL_HOJAS")
	private int i_total_hojas;
	
	@Column(name = "VC_RUTA_ARCHIVO_PDF")
	private String vc_ruta_archivo_pdf;
	
	@Column(name = "VC_RUTA_ARCHIVO_XML")
	private String vc_ruta_archivo_xml;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;
	
	@Column(name = "VC_REQUISITION", columnDefinition = "VARCHAR(255)")
	private String vc_requisition;
	
	@OneToOne
	@JoinColumn(name = "PK_USR_REGISTRA")
	private Usuario pk_usr_registra;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_ENVIO")
	private LocalDate dt_fecha_envio;

	public int getPk_id_docto_unificado() {
		return pk_id_docto_unificado;
	}

	public void setPk_id_docto_unificado(int pk_id_docto_unificado) {
		this.pk_id_docto_unificado = pk_id_docto_unificado;
	}

	public SolicitudEnvio getFk_id_solicitud_envio() {
		return fk_id_solicitud_envio;
	}

	public void setFk_id_solicitud_envio(SolicitudEnvio fk_id_solicitud_envio) {
		this.fk_id_solicitud_envio = fk_id_solicitud_envio;
	}

	public int getI_total_hojas() {
		return i_total_hojas;
	}

	public void setI_total_hojas(int i_total_hojas) {
		this.i_total_hojas = i_total_hojas;
	}

	public String getVc_ruta_archivo_pdf() {
		return vc_ruta_archivo_pdf;
	}

	public void setVc_ruta_archivo_pdf(String vc_ruta_archivo_pdf) {
		this.vc_ruta_archivo_pdf = vc_ruta_archivo_pdf;
	}

	public String getVc_ruta_archivo_xml() {
		return vc_ruta_archivo_xml;
	}

	public void setVc_ruta_archivo_xml(String vc_ruta_archivo_xml) {
		this.vc_ruta_archivo_xml = vc_ruta_archivo_xml;
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

	public LocalDate getDt_fecha_envio() {
		return dt_fecha_envio;
	}

	public void setDt_fecha_envio(LocalDate dt_fecha_envio) {
		this.dt_fecha_envio = dt_fecha_envio;
	}

	public String getVc_requisition() {
		return vc_requisition;
	}

	public void setVc_requisition(String vc_requisition) {
		this.vc_requisition = vc_requisition;
	}
	
	
	
	

}
