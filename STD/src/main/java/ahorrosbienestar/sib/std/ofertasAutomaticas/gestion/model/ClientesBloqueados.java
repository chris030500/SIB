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
@Table(name="TBL_CLIENTES_BLOQUEADOS_AO")
public class ClientesBloqueados {
	
	@Column(name = "PK_ID_BLOQUEO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_id_bloqueo;
	
	@Column(name = "I_CLIENTEID", columnDefinition = "INT")
	private int i_clienteid;
	
	@OneToOne
	@JoinColumn(name = "I_USUARIO_REG", updatable = true)
	private Usuario i_usuario_reg;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_registro;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1) DEFAULT 2")
	private int i_estatus;
	
	@Column(name = "I_TIPO_BLOQUEO", columnDefinition = "TINYINT(1) DEFAULT 1")
	private int i_tipo_bloqueo;
	
	@OneToOne
	@JoinColumn(name = "I_USUARIO_MOD", updatable = true)
	private Usuario i_usuario_mod;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_MOD", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_mod;

	public int getPk_id_bloqueo() {
		return pk_id_bloqueo;
	}

	public void setPk_id_bloqueo(int pk_id_bloqueo) {
		this.pk_id_bloqueo = pk_id_bloqueo;
	}

	public int getI_clienteid() {
		return i_clienteid;
	}

	public void setI_clienteid(int i_clienteid) {
		this.i_clienteid = i_clienteid;
	}

	public Usuario getI_usuario_reg() {
		return i_usuario_reg;
	}

	public void setI_usuario_reg(Usuario i_usuario_reg) {
		this.i_usuario_reg = i_usuario_reg;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public int getI_tipo_bloqueo() {
		return i_tipo_bloqueo;
	}

	public void setI_tipo_bloqueo(int i_tipo_bloqueo) {
		this.i_tipo_bloqueo = i_tipo_bloqueo;
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
