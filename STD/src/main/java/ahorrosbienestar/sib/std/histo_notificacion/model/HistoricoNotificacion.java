package ahorrosbienestar.sib.std.histo_notificacion.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import ahorrosbienestar.sib.std.roles.model.Rol;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;

@Entity
@Table(name = "TBL_HISTORICO_NOTIFICACION")
public class HistoricoNotificacion {

	@Column(name = "PK_ID_HISTORICO_NOTIFICACION")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hist_notificacion_seq")
	@SequenceGenerator(name = "hist_notificacion_seq", sequenceName = "hist_notificacion_seq", initialValue = 1, allocationSize = 1)
	private int pk_id_historico_notificacion;

	@Column(name = "VC_CORREO")
	private String vc_correo;

	@Column(name = "VC_MENSAJE")
	private String vc_mensaje;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;

	@Column(name = "VC_HORA_REGISTRO", columnDefinition = "VARCHAR(45)")
	private String vc_hora_registro;

	@Column(name = "I_IND_ENVIO", columnDefinition = "TINYINT(1)")
	private int i_ind_envio;

	@ManyToOne
	@JoinColumn(name = "FK_SOLICITUD_ENVIO")
	private SolicitudEnvio fk_solicitud_envio;

	public int getPk_id_historico_notificacion() {
		return pk_id_historico_notificacion;
	}

	public void setPk_id_historico_notificacion(int pk_id_historico_notificacion) {
		this.pk_id_historico_notificacion = pk_id_historico_notificacion;
	}

	public String getVc_correo() {
		return vc_correo;
	}

	public void setVc_correo(String vc_correo) {
		this.vc_correo = vc_correo;
	}

	public String getVc_mensaje() {
		return vc_mensaje;
	}

	public void setVc_mensaje(String vc_mensaje) {
		this.vc_mensaje = vc_mensaje;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public String getVc_hora_registro() {
		return vc_hora_registro;
	}

	public void setVc_hora_registro(String vc_hora_registro) {
		this.vc_hora_registro = vc_hora_registro;
	}

	public int getI_ind_envio() {
		return i_ind_envio;
	}

	public void setI_ind_envio(int i_ind_envio) {
		this.i_ind_envio = i_ind_envio;
	}

	public SolicitudEnvio getFk_solicitud_envio() {
		return fk_solicitud_envio;
	}

	public void setFk_solicitud_envio(SolicitudEnvio fk_solicitud_envio) {
		this.fk_solicitud_envio = fk_solicitud_envio;
	}
}
