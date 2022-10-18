package ahorrosbienestar.sib.std.roles.model;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TBL_CAT_ROLl")
public class Rol {
	
	@Column(name = "PK_ID_ROLL")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pk_id_roll;
	
	@Column(name = "VC_NOMBRE_ROL")
	private String vc_nombre_rol;
	
	@Column(name = "VC_DESCRIPCION_ROL")
	private String vc_descripcion_rol;
	
	@Column(name = "DT_FECHA_REGISTRO")
	private Date dt_fecha_registro;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;
	
	@Column(name = "VC_FECHA_BAJA")
	private Date vc_fecha_baja;
	
}
