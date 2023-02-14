package ahorrosbienestar.sib.std.usuarios.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import ahorrosbienestar.sib.std.roles.model.Rol;

@Getter
@Setter
@Entity
@Table(name="TBL_CAT_USUARIOS",
		indexes = {@Index(name = "fk_Usuario_Rol1_idx",  columnList="rol_idRol ASC", unique = false),
					@Index(name = "nombre_usuario_UNIQUE", columnList = "VC_NOMBRE_USUARIO DESC", unique = true)})
public class Usuario {
	
	@Column(name = "PK_ID_USUARIO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="usuario_seq")
	@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", initialValue = 2, allocationSize=1)
	private int pk_id_usuario;
	
	@Column(name = "VC_NOMBRE_USUARIO", columnDefinition = "VARCHAR(45)")
	private String vc_nombre_usuario;
	
	@Column(name = "I_ESTATUS_USUARIO", columnDefinition = "TINYINT(1)")
	private int i_estatus_usuario;
	
	@Column(name = "I_ESTATUS_SESION", columnDefinition = "TINYINT(1)")
	private int i_estatus_sesion;
	
	@Column(name = "I_ESTATUS_CUENTA", columnDefinition = "TINYINT(1)")
	private int i_estatus_cuenta;
	
	@Column(name = "I_INTENTOS_FALLIDOS")
	private int i_intentos_fallidos;
	
	@Column(name = "I_ID_EMPLEADO", columnDefinition = "BIGINT")
	private int i_id_empleado;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_ULTIMO_INGRESO")
	private LocalDate dt_fecha_ultimo_ingreso;
	
	//Tiene relacion con TBL_CAT_ROLI que es 1:1
	@OneToOne
	@JoinColumn(name = "Rol_idRol")
	private Rol rol_idRol;

	public int getPk_id_usuario() {
		return pk_id_usuario;
	}

	public void setPk_id_usuario(int pk_id_usuario) {
		this.pk_id_usuario = pk_id_usuario;
	}

	public String getVc_nombre_usuario() {
		return vc_nombre_usuario;
	}

	public void setVc_nombre_usuario(String vc_nombre_usuario) {
		this.vc_nombre_usuario = vc_nombre_usuario;
	}

	public int getI_estatus_usuario() {
		return i_estatus_usuario;
	}

	public void setI_estatus_usuario(int i_estatus_usuario) {
		this.i_estatus_usuario = i_estatus_usuario;
	}

	public int getI_estatus_sesion() {
		return i_estatus_sesion;
	}

	public void setI_estatus_sesion(int i_estatus_sesion) {
		this.i_estatus_sesion = i_estatus_sesion;
	}

	public int getI_estatus_cuenta() {
		return i_estatus_cuenta;
	}

	public void setI_estatus_cuenta(int i_estatus_cuenta) {
		this.i_estatus_cuenta = i_estatus_cuenta;
	}
	

	public int getI_intentos_fallidos() {
		return i_intentos_fallidos;
	}

	public void setI_intentos_fallidos(int i_intentos_fallidos) {
		this.i_intentos_fallidos = i_intentos_fallidos;
	}

	public Rol getRol_idRol() {
		return rol_idRol;
	}

	public void setRol_idRol(Rol rol_idRol) {
		this.rol_idRol = rol_idRol;
	}

	public int getI_id_empleado() {
		return i_id_empleado;
	}

	public void setI_id_empleado(int i_id_empleado) {
		this.i_id_empleado = i_id_empleado;
	}

	public LocalDate getDt_fecha_ultimo_ingreso() {
		return dt_fecha_ultimo_ingreso;
	}

	public void setDt_fecha_ultimo_ingreso(LocalDate dt_fecha_ultimo_ingreso) {
		this.dt_fecha_ultimo_ingreso = dt_fecha_ultimo_ingreso;
	}

	
	
	
	//Tiene relacion con TBL_CAT_EMPLEADOS que es 1:1
	/*@OneToOne
	@JoinColumn(name = "empleados_num_fk")
	private Empleado empleados_num_fk;*/
	
	
	
	
	
}
