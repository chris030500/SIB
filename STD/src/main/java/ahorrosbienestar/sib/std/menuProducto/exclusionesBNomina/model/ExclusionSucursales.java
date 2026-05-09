package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table( name = "TBL_EXCLUSION_SUCURSALES" )
public class ExclusionSucursales {
	
	@Id
	@Column( name = "PK_I_SUCURSAL_ID" )
	private Integer pk_i_sucursal_id;
	
	@Column( name = "VC_NOMBRE" )
	private String vc_nombre;
	
	@Column( name = "C_ESTATUS" )
	private char c_estatus;
	
	@Column( name = "I_INDICADOR_DEPURAR" )
	private Integer i_indicador_depurar;
	
	@Column( name = "I_USUARIO_MODIFICO" )
	private Integer i_usuario_modifico;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column( name = "DT_FECHA_MODIFICO" )
	private LocalDateTime dt_fecha_modifico;
	
	@Column( name = "I_VALOR_ANTERIOR" )
	private Integer i_valor_anterior;

	public Integer getPk_i_sucursal_id() {
		return pk_i_sucursal_id;
	}

	public void setPk_i_sucursal_id(Integer pk_i_sucursal_id) {
		this.pk_i_sucursal_id = pk_i_sucursal_id;
	}

	public String getVc_nombre() {
		return vc_nombre;
	}

	public void setVc_nombre(String vc_nombre) {
		this.vc_nombre = vc_nombre;
	}

	public char getC_estatus() {
		return c_estatus;
	}

	public void setC_estatus(char c_estatus) {
		this.c_estatus = c_estatus;
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

	public LocalDateTime getDt_fecha_modifico() {
		return dt_fecha_modifico;
	}

	public void setDt_fecha_modifico(LocalDateTime dt_fecha_modifico) {
		this.dt_fecha_modifico = dt_fecha_modifico;
	}

	public Integer getI_valor_anterior() {
		return i_valor_anterior;
	}

	public void setI_valor_anterior(Integer i_valor_anterior) {
		this.i_valor_anterior = i_valor_anterior;
	}

	@Override
	public String toString() {
		return "ExclusionSucursales [pk_i_sucursal_id=" + pk_i_sucursal_id + ", vc_nombre=" + vc_nombre + ", c_estatus="
				+ c_estatus + ", i_indicador_depurar=" + i_indicador_depurar + ", i_usuario_modifico="
				+ i_usuario_modifico + ", dt_fecha_modifico=" + dt_fecha_modifico + ", i_valor_anterior="
				+ i_valor_anterior + "]";
	}
	
}
