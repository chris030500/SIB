package ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table( name = "TBL_ESTADOS_REPUB" )
public class EstadosRepub {
	
	@Id
	@Column( name = "PK_I_ESTADO_ID" )
	private Integer pk_i_estado_id;
	
	@Column( name = "I_EMPRESA_ID" )
	private Integer i_empresa_id;
	
	@Column( name = "VC_NOMBRE" )
	private String vc_nombre;
	
	@Column( name = "VC_EQ_BURO_CRED" )
	private String vc_eq_buro_cred;
	
	@Column( name = "VC_EQ_CIR_CRE" )
	private String vc_eq_cir_cre;
	
	@Column( name = "C_CLAVE" )
	private char c_clave;
	
	@Column( name = "I_PUNTOS_PLD" )
	private Integer i_puntos_pld;
	
	@Column( name = "I_USUARIO" )
	private Integer i_usuario;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column( name = "DT_FECHA_ACTUAL" )
	private Date dt_fecha_actual;
	
	@Column( name = "VC_DIRECCION_IP" )
	private String vc_direccion_ip;
	
	@Column( name = "VC_PROGRAMA_ID" )
	private String vc_programa_id;
	
	@Column( name = "I_SUCURSAL" )
	private Integer i_sucursal;
	
	@Column( name = "I_NUM_TRANSACCION" )
	private Integer i_num_transaccion;

	public Integer getPk_i_estado_id() {
		return pk_i_estado_id;
	}

	public void setPk_i_estado_id(Integer pk_i_estado_id) {
		this.pk_i_estado_id = pk_i_estado_id;
	}

	public Integer getI_empresa_id() {
		return i_empresa_id;
	}

	public void setI_empresa_id(Integer i_empresa_id) {
		this.i_empresa_id = i_empresa_id;
	}

	public String getVc_nombre() {
		return vc_nombre;
	}

	public void setVc_nombre(String vc_nombre) {
		this.vc_nombre = vc_nombre;
	}

	public String getVc_eq_buro_cred() {
		return vc_eq_buro_cred;
	}

	public void setVc_eq_buro_cred(String vc_eq_buro_cred) {
		this.vc_eq_buro_cred = vc_eq_buro_cred;
	}

	public String getVc_eq_cir_cre() {
		return vc_eq_cir_cre;
	}

	public void setVc_eq_cir_cre(String vc_eq_cir_cre) {
		this.vc_eq_cir_cre = vc_eq_cir_cre;
	}

	public char getC_clave() {
		return c_clave;
	}

	public void setC_clave(char c_clave) {
		this.c_clave = c_clave;
	}

	public Integer getI_puntos_pld() {
		return i_puntos_pld;
	}

	public void setI_puntos_pld(Integer i_puntos_pld) {
		this.i_puntos_pld = i_puntos_pld;
	}

	public Integer getI_usuario() {
		return i_usuario;
	}

	public void setI_usuario(Integer i_usuario) {
		this.i_usuario = i_usuario;
	}

	public Date getDt_fecha_actual() {
		return dt_fecha_actual;
	}

	public void setDt_fecha_actual(Date dt_fecha_actual) {
		this.dt_fecha_actual = dt_fecha_actual;
	}

	public String getVc_direccion_ip() {
		return vc_direccion_ip;
	}

	public void setVc_direccion_ip(String vc_direccion_ip) {
		this.vc_direccion_ip = vc_direccion_ip;
	}

	public String getVc_programa_id() {
		return vc_programa_id;
	}

	public void setVc_programa_id(String vc_programa_id) {
		this.vc_programa_id = vc_programa_id;
	}

	public Integer getI_sucursal() {
		return i_sucursal;
	}

	public void setI_sucursal(Integer i_sucursal) {
		this.i_sucursal = i_sucursal;
	}

	public Integer getI_num_transaccion() {
		return i_num_transaccion;
	}

	public void setI_num_transaccion(Integer i_num_transaccion) {
		this.i_num_transaccion = i_num_transaccion;
	}

	@Override
	public String toString() {
		return "EstadosRepub [pk_i_estado_id=" + pk_i_estado_id + ", i_empresa_id=" + i_empresa_id + ", vc_nombre="
				+ vc_nombre + ", vc_eq_buro_cred=" + vc_eq_buro_cred + ", vc_eq_cir_cre=" + vc_eq_cir_cre + ", c_clave="
				+ c_clave + ", i_puntos_pld=" + i_puntos_pld + ", i_usuario=" + i_usuario + ", dt_fecha_actual="
				+ dt_fecha_actual + ", vc_direccion_ip=" + vc_direccion_ip + ", vc_programa_id=" + vc_programa_id
				+ ", i_sucursal=" + i_sucursal + ", i_num_transaccion=" + i_num_transaccion + "]";
	}
	
}
