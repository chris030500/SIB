package ahorrosbienestar.sib.std.planAccion.model;

import java.time.LocalDate;

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
@Table(name="TBL_PLAN_ACCION")
public class PlanAccion {
	
	@Column(name = "PK_ID_PLANACCION")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="tbl_planAccion_seq")
	@SequenceGenerator(name = "tbl_planAccion_seq", sequenceName = "tbl_planAccion_seq", initialValue = 1, allocationSize=1)
	private int pk_id_planaccion;
	
	@Column(name = "I_OBSERVACION", columnDefinition = "INT")
	private int i_observacion;
	
	@Column(name = "VC_DESC_ACTIVIDAD", columnDefinition = "VARCHAR(400)")
	private String vc_desc_actividad;
		
	@Column(name = "I_RESPONSABLE", columnDefinition = "INT")
	private int i_responsable;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_INICIO")
	private LocalDate dt_fecha_inicio;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_VENCIMIENTO")
	private LocalDate dt_fecha_vencimiento;
	
	@Column(name = "I_ESTATUS", columnDefinition = "INT")
	private int i_estatus;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_ASGINACION")
	private LocalDate dt_fecha_asignacion;
	
	@Column(name = "I_USUARIO_REGISTRO", columnDefinition = "INT")
	private int i_usuario_registro;
	
	@Column(name = "I_USUARIO_MOD", columnDefinition = "INT")
	private int i_usuario_mod;
		
	@Column(name = "I_USUARIO_ELIMINO", columnDefinition = "INT")
	private int i_usuario_elimino;
	
	@Column(name = "I_CONFIRMACION_ENCARGADO", columnDefinition = "INT")
	private int i_confirmacion_encargado;
	
	@Column(name = "I_CONFIRMACION_RESPONSABLE", columnDefinition = "INT")
	private int i_confirmacion_responsable;

	public int getPk_id_planaccion() {
		return pk_id_planaccion;
	}

	public void setPk_id_planaccion(int pk_id_planaccion) {
		this.pk_id_planaccion = pk_id_planaccion;
	}

	public int getI_observacion() {
		return i_observacion;
	}

	public void setI_observacion(int i_observacion) {
		this.i_observacion = i_observacion;
	}

	public String getVc_desc_actividad() {
		return vc_desc_actividad;
	}

	public void setVc_desc_actividad(String vc_desc_actividad) {
		this.vc_desc_actividad = vc_desc_actividad;
	}

	public int getI_responsable() {
		return i_responsable;
	}

	public void setI_responsable(int i_responsable) {
		this.i_responsable = i_responsable;
	}

	public LocalDate getDt_fecha_inicio() {
		return dt_fecha_inicio;
	}

	public void setDt_fecha_inicio(LocalDate dt_fecha_inicio) {
		this.dt_fecha_inicio = dt_fecha_inicio;
	}

	public LocalDate getDt_fecha_vencimiento() {
		return dt_fecha_vencimiento;
	}

	public void setDt_fecha_vencimiento(LocalDate dt_fecha_vencimiento) {
		this.dt_fecha_vencimiento = dt_fecha_vencimiento;
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

	public LocalDate getDt_fecha_asignacion() {
		return dt_fecha_asignacion;
	}

	public void setDt_fecha_asignacion(LocalDate dt_fecha_asignacion) {
		this.dt_fecha_asignacion = dt_fecha_asignacion;
	}

	public int getI_usuario_registro() {
		return i_usuario_registro;
	}

	public void setI_usuario_registro(int i_usuario_registro) {
		this.i_usuario_registro = i_usuario_registro;
	}

	public int getI_usuario_mod() {
		return i_usuario_mod;
	}

	public void setI_usuario_mod(int i_usuario_mod) {
		this.i_usuario_mod = i_usuario_mod;
	}

	public int getI_usuario_elimino() {
		return i_usuario_elimino;
	}

	public void setI_usuario_elimino(int i_usuario_elimino) {
		this.i_usuario_elimino = i_usuario_elimino;
	}

	public int getI_confirmacion_encargado() {
		return i_confirmacion_encargado;
	}

	public void setI_confirmacion_encargado(int i_confirmacion_encargado) {
		this.i_confirmacion_encargado = i_confirmacion_encargado;
	}

	public int getI_confirmacion_responsable() {
		return i_confirmacion_responsable;
	}

	public void setI_confirmacion_responsable(int i_confirmacion_responsable) {
		this.i_confirmacion_responsable = i_confirmacion_responsable;
	}
}
