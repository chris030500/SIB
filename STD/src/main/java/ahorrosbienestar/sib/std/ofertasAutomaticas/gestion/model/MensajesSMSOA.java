package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name="TBL_MENSAJES_SMS_AO")
public class MensajesSMSOA {
	@Column(name = "PK_ID_MENSAJE")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_id_mensaje;
	
	@Column(name = "FK_ID_ASIGNACION", columnDefinition = "INT")
	private int fk_id_asignacion;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1) DEFAULT 1")
	private int i_estatus;
	
	@Column(name = "VC_DESC_MENSAJE", columnDefinition = "LONGTEXT")
	private String vc_desc_mensaje;
		
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_registro;
	
	@OneToOne
	@JoinColumn(name = "I_USUARIO_MOD", updatable = true)
	private Usuario i_usuario_mod;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_MOD", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_mod;

	public int getPk_id_mensaje() {
		return pk_id_mensaje;
	}

	public void setPk_id_mensaje(int pk_id_mensaje) {
		this.pk_id_mensaje = pk_id_mensaje;
	}

	public int getFk_id_asignacion() {
		return fk_id_asignacion;
	}

	public void setFk_id_asignacion(int fk_id_asignacion) {
		this.fk_id_asignacion = fk_id_asignacion;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public String getVc_desc_mensaje() {
		return vc_desc_mensaje;
	}

	public void setVc_desc_mensaje(String vc_desc_mensaje) {
		this.vc_desc_mensaje = vc_desc_mensaje;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public Usuario getI_usuario_mod() {
		return i_usuario_mod;
	}

	public void setI_usuario_mod(Usuario i_usuario_mod) {
		this.i_usuario_mod = i_usuario_mod;
	}

	public LocalDate getDt_fecha_mod() {
		return dt_fecha_mod;
	}

	public void setDt_fecha_mod(LocalDate dt_fecha_mod) {
		this.dt_fecha_mod = dt_fecha_mod;
	}
}

