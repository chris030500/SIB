package ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;



@Entity
public class ParametrosReglas {
	
	
	@Id
	@Column(name = "PK_ID")
	private int pk_id;
	
	@Column(name = "FK_PARAMS_REGLA")
	private int fk_params_regla;
	
	@Column(name = "VC_DESC_CARTERA")
	private String vc_desc_cartera;
	
	@Column(name = "VC_DESC_CLIENTE")
	private String vc_desc_cliente;
	
	@Column(name = "VC_DESC_ASIGNADO")
	private String vc_desc_asignado;
	
	@Column(name = "I_PRIORIDAD")
	private int i_prioridad;

	@Column(name = "VC_USUARIO_REGISTRO")
	private String vc_usuario_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@Column(name = "VC_USUARIO_MOD")
	private String vc_usuario_mod;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_MOD")
	private LocalDate dt_fecha_mod;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;

	public int getPk_id() {
		return pk_id;
	}

	public void setPk_id(int pk_id) {
		this.pk_id = pk_id;
	}

	public int getFk_params_regla() {
		return fk_params_regla;
	}

	public void setFk_params_regla(int fk_params_regla) {
		this.fk_params_regla = fk_params_regla;
	}

	public String getVc_desc_cartera() {
		return vc_desc_cartera;
	}

	public void setVc_desc_cartera(String vc_desc_cartera) {
		this.vc_desc_cartera = vc_desc_cartera;
	}

	public String getVc_desc_cliente() {
		return vc_desc_cliente;
	}

	public void setVc_desc_cliente(String vc_desc_cliente) {
		this.vc_desc_cliente = vc_desc_cliente;
	}

	public String getVc_desc_asignado() {
		return vc_desc_asignado;
	}

	public void setVc_desc_asignado(String vc_desc_asignado) {
		this.vc_desc_asignado = vc_desc_asignado;
	}

	public int getI_prioridad() {
		return i_prioridad;
	}

	public void setI_prioridad(int i_prioridad) {
		this.i_prioridad = i_prioridad;
	}

	public String getVc_usuario_registro() {
		return vc_usuario_registro;
	}

	public void setVc_usuario_registro(String vc_usuario_registro) {
		this.vc_usuario_registro = vc_usuario_registro;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public String getVc_usuario_mod() {
		return vc_usuario_mod;
	}

	public void setVc_usuario_mod(String vc_usuario_mod) {
		this.vc_usuario_mod = vc_usuario_mod;
	}

	public LocalDate getDt_fecha_mod() {
		return dt_fecha_mod;
	}

	public void setDt_fecha_mod(LocalDate dt_fecha_mod) {
		this.dt_fecha_mod = dt_fecha_mod;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}
	
}
