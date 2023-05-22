package ahorrosbienestar.sib.std.correoCSO.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="TBL_CORREO_CSO")
public class CorreoCSO {
	
	@Column(name = "PK_ID_CORREO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="correoCSO_seq")
	@SequenceGenerator(name = "correoCSO_seq", sequenceName = "correoCSO_seq", initialValue = 1, allocationSize=1)
	private int pk_id_correo_notificacion;
	
	@JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
	@Column(name = "DT_FECHA_REGISTRO")
	private Date dt_fecha_registro;
	
	@Column(name = "VC_CONTENIDO_CORREO", columnDefinition = "VARCHAR(600)")
	private String vc_contenido_correo;
	
	@Column(name = "VC_CORREO_DESTINO", columnDefinition = "VARCHAR(100)")
	private String vc_correo_destino;
	
	@Column(name = "I_ENVIO", columnDefinition = "INT")
	private int i_envio;

	public int getPk_id_correo_notificacion() {
		return pk_id_correo_notificacion;
	}

	public void setPk_id_correo_notificacion(int pk_id_correo_notificacion) {
		this.pk_id_correo_notificacion = pk_id_correo_notificacion;
	}

	public Date getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(Date dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public String getVc_contenido_correo() {
		return vc_contenido_correo;
	}

	public void setVc_contenido_correo(String vc_contenido_correo) {
		this.vc_contenido_correo = vc_contenido_correo;
	}

	public String getVc_correo_destino() {
		return vc_correo_destino;
	}

	public void setVc_correo_destino(String vc_correo_destino) {
		this.vc_correo_destino = vc_correo_destino;
	}

	public int getI_envio() {
		return i_envio;
	}

	public void setI_envio(int i_envio) {
		this.i_envio = i_envio;
	}
	
}
