package ahorrosbienestar.sib.std.usuarios.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pk_id_usuario;
	
	@Column(name = "VC_NOMBRE_USUARIO")
	private String vc_nombre_usuario;
	
	@Column(name = "VC_ESTATUS_USUARIO")
	private int vc_estatus_usuario;
	
	@Column(name = "VC_ESTATUS_SESION")
	private boolean vc_estatus_sesion;
	
	@Column(name = "VC_ESTATUS_CUENTA")
	private boolean vc_estatus_cuenta;
	
	@Column(name = "I_INTENTOS_FALLIDOS")
	private int i_intentos_fallidos;
	
	//Tiene relacion con TBL_CAT_ROLI que es 1:1
	@OneToOne
	@JoinColumn(name = "Rol_idRol")
	private Rol rol_idRol;
	
	//Tiene relacion con TBL_CAT_EMPLEADOS que es 1:1
	/*@OneToOne
	@JoinColumn(name = "empleados_num_fk")
	private Empleado empleados_num_fk;*/
	
	
	
	
	
}
