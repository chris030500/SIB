package ahorrosbienestar.sib.std.menuproducto.reglasAsignacion.model;

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
@Table(name="TBL_PARAMS_REGLAS_ASIGNACION")
public class ParametrosReglasAsignacion {
	
	@Column(name = "PK_PARMS_REGLA")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_params_regla;
	
	@OneToOne
	@JoinColumn(name = "FK_ID_REGLA")
	private ReglasAsignacion fk_id_regla;
	
	@Column(name = "I_TIPO_ASIGNADO", columnDefinition = "TINYINT(1) DEFAULT 1")
	private int i_tipo_asignado;
	
	@Column(name = "I_PORCENTAJE", columnDefinition = "INT")
	private int i_porcentaje;
	
	@Column(name = "I_TIPO_CLIENTE", columnDefinition = "TINYINT(1)")
	private int i_tipo_cliente;
	
	@Column(name = "I_TIPO_CARTERA", columnDefinition = "TINYINT(1)")
	private int i_tipo_cartera;
	
	@Column(name = "I_PRIORIDAD", columnDefinition = "TINYINT(1)")
	private int i_prioridad;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1) DEFAULT 1")
	private int i_estatus;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_registro;
	
	@OneToOne
	@JoinColumn(name = "I_USUARIO_REGISTRO")
	private Usuario i_usuario_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_MOD", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_mod;
	
	@OneToOne
	@JoinColumn(name = "I_USUARIO_MOD", updatable = true)
	private Usuario i_usuario_mod;

	public int getPk_params_regla() {
		return pk_params_regla;
	}

	public void setPk_params_regla(int pk_params_regla) {
		this.pk_params_regla = pk_params_regla;
	}

	public ReglasAsignacion getFk_id_regla() {
		return fk_id_regla;
	}

	public void setFk_id_regla(ReglasAsignacion fk_id_regla) {
		this.fk_id_regla = fk_id_regla;
	}

	public int getI_tipo_asignado() {
		return i_tipo_asignado;
	}

	public void setI_tipo_asignado(int i_tipo_asignado) {
		this.i_tipo_asignado = i_tipo_asignado;
	}

	public int getI_porcentaje() {
		return i_porcentaje;
	}

	public void setI_porcentaje(int i_porcentaje) {
		this.i_porcentaje = i_porcentaje;
	}

	public int getI_tipo_cliente() {
		return i_tipo_cliente;
	}

	public void setI_tipo_cliente(int i_tipo_cliente) {
		this.i_tipo_cliente = i_tipo_cliente;
	}

	public int getI_tipo_cartera() {
		return i_tipo_cartera;
	}

	public void setI_tipo_cartera(int i_tipo_cartera) {
		this.i_tipo_cartera = i_tipo_cartera;
	}

	public int getI_prioridad() {
		return i_prioridad;
	}

	public void setI_prioridad(int i_prioridad) {
		this.i_prioridad = i_prioridad;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public Usuario getI_usuario_registro() {
		return i_usuario_registro;
	}

	public void setI_usuario_registro(Usuario i_usuario_registro) {
		this.i_usuario_registro = i_usuario_registro;
	}

	public LocalDate getDt_fecha_mod() {
		return dt_fecha_mod;
	}

	public void setDt_fecha_mod(LocalDate dt_fecha_mod) {
		this.dt_fecha_mod = dt_fecha_mod;
	}

	public Usuario getI_usuario_mod() {
		return i_usuario_mod;
	}

	public void setI_usuario_mod(Usuario i_usuario_mod) {
		this.i_usuario_mod = i_usuario_mod;
	}

}
