package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.consultarRelaciones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ConsultarRelacionesModel {

	@Id
	@Column(name = "PK_ID_RELACION_CODIGO")
	private int pk_id_relacion_codigo;

	@Column(name = "RELACION_CODIGOS")
	private String relacion_codigos;

	@Column(name = "I_ESTATUS")
	private String i_estatus;

	@Column(name = "V_VALOR")
	private String v_valor;

	public int getPk_id_relacion_codigo() {
		return pk_id_relacion_codigo;
	}

	public void setPk_id_relacion_codigo(int pk_id_relacion_codigo) {
		this.pk_id_relacion_codigo = pk_id_relacion_codigo;
	}

	public String getRelacion_codigos() {
		return relacion_codigos;
	}

	public void setRelacion_codigos(String relacion_codigos) {
		this.relacion_codigos = relacion_codigos;
	}

	public String getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(String i_estatus) {
		this.i_estatus = i_estatus;
	}

	public String getV_valor() {
		return v_valor;
	}

	public void setV_valor(String v_valor) {
		this.v_valor = v_valor;
	}

}
