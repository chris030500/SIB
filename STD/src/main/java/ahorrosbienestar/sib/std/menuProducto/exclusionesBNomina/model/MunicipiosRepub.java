package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table( name = "TBL_MUNICIPIOS_REPUB" )
public class MunicipiosRepub {
	
	@Id
	@Column( name = "PK_I_MUNICIPIO_COBERTURA" )
	private Integer pk_i_municipio_cobertura;
	
	@Column( name = "I_ESTADO_ID" )
	private Integer i_estado_id;
	
	@Column( name = "I_MUNICIPIO_ID" )
	private Integer i_municipio_id;
	
	@Column( name = "VC_NOMBRE" )
	private String vc_nombre;
	
	@Column( name = "I_INDICADOR_DEPURAR" )
	private Integer i_indicador_depurar;
	
	@Column( name = "I_USUARIO_MODIFICO" )
	private Integer i_usuario_modifico;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column( name = "DT_FECHA_MODIFICO" )
	private LocalDateTime dt_fecha_modifico;
	
	@Column( name = "I_VALOR_ANTERIOR" )
	private Integer i_valor_anterior;

	public Integer getPk_i_municipio_cobertura() {
		return pk_i_municipio_cobertura;
	}

	public void setPk_i_municipio_cobertura(Integer pk_i_municipio_cobertura) {
		this.pk_i_municipio_cobertura = pk_i_municipio_cobertura;
	}

	public Integer getI_estado_id() {
		return i_estado_id;
	}

	public void setI_estado_id(Integer i_estado_id) {
		this.i_estado_id = i_estado_id;
	}

	public Integer getI_municipio_id() {
		return i_municipio_id;
	}

	public void setI_municipio_id(Integer i_municipio_id) {
		this.i_municipio_id = i_municipio_id;
	}

	public String getVc_nombre() {
		return vc_nombre;
	}

	public void setVc_nombre(String vc_nombre) {
		this.vc_nombre = vc_nombre;
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
		return "MunicipiosRepub [pk_i_municipio_cobertura=" + pk_i_municipio_cobertura + ", i_estado_id=" + i_estado_id
				+ ", i_municipio_id=" + i_municipio_id + ", vc_nombre=" + vc_nombre + ", i_indicador_depurar="
				+ i_indicador_depurar + ", i_usuario_modifico=" + i_usuario_modifico + ", dt_fecha_modifico="
				+ dt_fecha_modifico + ", i_valor_anterior=" + i_valor_anterior + "]";
	}
	
}

