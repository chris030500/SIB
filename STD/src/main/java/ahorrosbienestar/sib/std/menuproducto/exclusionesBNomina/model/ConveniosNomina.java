package ahorrosbienestar.sib.std.menuproducto.exclusionesBNomina.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table( name = "TBL_CONVENIOS_NOMINA" )
public class ConveniosNomina {
	
	@Id
	@Column( name = "PK_I_CONVENIO_NOMINA_ID" )
	private Integer pk_i_convenio_nomina_id;
	
	@Column( name = "I_INSIST_NOMINA_ID" )
	private Integer i_insist_nomina_id;
	
	@Column( name = "VC_DESCRIPCION" )
	private String vc_descripcion;
	
	@Column( name = "I_INDICADOR_DEPURAR" )
	private Integer i_indicador_depurar;
	
	@Column( name = "I_USUARIO_MODIFICO" )
	private Integer i_usuario_modifico;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column( name = "DT_FECHA_MODIFICO" )
	private LocalDateTime dt_fecha_modifico;
	
	@Column( name = "I_VALOR_ANTERIOR" )
	private Integer i_valor_anterior;

	public Integer getPk_i_convenio_nomina_id() {
		return pk_i_convenio_nomina_id;
	}

	public void setPk_i_convenio_nomina_id(Integer pk_i_convenio_nomina_id) {
		this.pk_i_convenio_nomina_id = pk_i_convenio_nomina_id;
	}

	public Integer getI_insist_nomina_id() {
		return i_insist_nomina_id;
	}

	public void setI_insist_nomina_id(Integer i_insist_nomina_id) {
		this.i_insist_nomina_id = i_insist_nomina_id;
	}

	public String getVc_descripcion() {
		return vc_descripcion;
	}

	public void setVc_descripcion(String vc_descripcion) {
		this.vc_descripcion = vc_descripcion;
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
		return "ConveniosNomina [pk_i_convenio_nomina_id=" + pk_i_convenio_nomina_id + ", i_insist_nomina_id="
				+ i_insist_nomina_id + ", vc_descripcion=" + vc_descripcion + ", i_indicador_depurar="
				+ i_indicador_depurar + ", i_usuario_modifico=" + i_usuario_modifico + ", dt_fecha_modifico="
				+ dt_fecha_modifico + ", i_valor_anterior=" + i_valor_anterior + "]";
	}
	
}
