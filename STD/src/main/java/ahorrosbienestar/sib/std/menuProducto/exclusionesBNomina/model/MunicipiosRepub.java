package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table( name = "TBL_MUNICIPIOS_REPUB" )
public class MunicipiosRepub {
	
	@Column( name = "PK_I_ESTADO_ID" )
	private Integer pk_i_estado_id;
	
	@Id()
	@Column( name = "PK_I_MUNICIPIO_ID" )
	private Integer pk_i_municipio_id;
	
	@Column( name = "I_EMPRESA_ID" )
	private Integer i_empresa_id;
	
	@Column( name = "VC_NOMBRE" )
	private String vc_nombre;
	
	@Column( name = "VC_LOCALIDAD" )
	private String vc_localidad;
	
	@Column( name = "VC_EQCNBV" )
	private String vc_eqcnbv;
	
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
	
	@Column( name = "I_INDICADOR_DEPURAR" )
	private Integer i_indicador_depurar;
	
	@Column( name = "I_USUARIO_MODIFICO" )
	private Integer i_usuario_modifico;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column( name = "DT_FECHA_MODIFICO" )
	private Date dt_fecha_modifico;
	
	@Column( name = "I_INDICADOR_DEPURAR_ANTERIOR" )
	private Integer i_indicador_depurar_anterior;

	public Integer getPk_i_estado_id() {
		return pk_i_estado_id;
	}

	public void setPk_i_estado_id(Integer pk_i_estado_id) {
		this.pk_i_estado_id = pk_i_estado_id;
	}

	public Integer getPk_i_municipio_id() {
		return pk_i_municipio_id;
	}

	public void setPk_i_municipio_id(Integer pk_i_municipio_id) {
		this.pk_i_municipio_id = pk_i_municipio_id;
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

	public String getVc_localidad() {
		return vc_localidad;
	}

	public void setVc_localidad(String vc_localidad) {
		this.vc_localidad = vc_localidad;
	}

	public String getVc_eqcnbv() {
		return vc_eqcnbv;
	}

	public void setVc_eqcnbv(String vc_eqcnbv) {
		this.vc_eqcnbv = vc_eqcnbv;
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

	public Integer getI_indicador_depurar() {
		return i_indicador_depurar;
	}

	public void setI_indicador_depurar(Integer i_indicador_depurar) {
		this.i_indicador_depurar = i_indicador_depurar;
	}

	public Integer getI_usuario_modifico() {
		return i_usuario_modifico;
	}

	public void setI_usuario_modifico(Integer i_usuario_modifico) {
		this.i_usuario_modifico = i_usuario_modifico;
	}

	public Date getDt_fecha_modifico() {
		return dt_fecha_modifico;
	}

	public void setDt_fecha_modifico(Date dt_fecha_modifico) {
		this.dt_fecha_modifico = dt_fecha_modifico;
	}

	public Integer getI_indicador_depurar_anterior() {
		return i_indicador_depurar_anterior;
	}

	public void setI_indicador_depurar_anterior(Integer i_indicador_depurar_anterior) {
		this.i_indicador_depurar_anterior = i_indicador_depurar_anterior;
	}

	@Override
	public String toString() {
		return "MunicipiosRepub [pk_i_estado_id=" + pk_i_estado_id + ", pk_i_municipio_id=" + pk_i_municipio_id
				+ ", i_empresa_id=" + i_empresa_id + ", vc_nombre=" + vc_nombre + ", vc_localidad=" + vc_localidad
				+ ", vc_eqcnbv=" + vc_eqcnbv + ", i_usuario=" + i_usuario + ", dt_fecha_actual=" + dt_fecha_actual
				+ ", vc_direccion_ip=" + vc_direccion_ip + ", vc_programa_id=" + vc_programa_id + ", i_sucursal="
				+ i_sucursal + ", i_num_transaccion=" + i_num_transaccion + ", i_indicador_depurar="
				+ i_indicador_depurar + ", i_usuario_modifico=" + i_usuario_modifico + ", dt_fecha_modifico="
				+ dt_fecha_modifico + ", i_indicador_depurar_anterior=" + i_indicador_depurar_anterior + "]";
	}
	
	
}
