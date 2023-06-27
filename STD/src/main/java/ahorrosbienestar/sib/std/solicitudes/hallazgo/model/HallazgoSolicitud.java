package ahorrosbienestar.sib.std.solicitudes.hallazgo.model;

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
@Table(name = "TBL_HALLAZGO_SOLICITUD")
public class HallazgoSolicitud {
	
	@Column(name = "PK_ID_HALLAZGO")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hallazgo_solicitud_seq")
	@SequenceGenerator(name = "hallazgo_solicitud_seq", sequenceName = "hallazgo_solicitud_seq", 
						initialValue = 1, allocationSize = 1)
	private int pk_id_hallazgo_solicitud;
	
	@Column(name = "FK_ID_DOCTO_ENVIO")
	private int fk_id_docto_envio;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;
	
	@OneToOne
	@JoinColumn(name = "PK_USR_REGISTRA")
	private Usuario pk_usr_registra;
	
	@OneToOne
	@JoinColumn(name = "PK_USR_REVISA")
	private Usuario pk_usr_revisa;
	
	@Column(name = "VC_COMENTARIOS")
	private String vc_comentarios;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_RESPUESTA")
	private LocalDate dt_fecha_respuesta;

	public int getPk_id_hallazgo_solicitud() {
		return pk_id_hallazgo_solicitud;
	}

	public void setPk_id_hallazgo_solicitud(int pk_id_hallazgo_solicitud) {
		this.pk_id_hallazgo_solicitud = pk_id_hallazgo_solicitud;
	}

	public int getFk_id_docto_envio() {
		return fk_id_docto_envio;
	}

	public void setFk_id_docto_envio(int fk_id_docto_envio) {
		this.fk_id_docto_envio = fk_id_docto_envio;
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

	public Usuario getPk_usr_revisa() {
		return pk_usr_revisa;
	}

	public void setPk_usr_revisa(Usuario pk_usr_revisa) {
		this.pk_usr_revisa = pk_usr_revisa;
	}

	public String getVc_comentarios() {
		return vc_comentarios;
	}

	public void setVc_comentarios(String vc_comentarios) {
		this.vc_comentarios = vc_comentarios;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public LocalDate getDt_fecha_respuesta() {
		return dt_fecha_respuesta;
	}

	public void setDt_fecha_respuesta(LocalDate dt_fecha_respuesta) {
		this.dt_fecha_respuesta = dt_fecha_respuesta;
	}

	
	
	

}
