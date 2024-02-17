package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EstatusSeguimientoModel {

	@Id
	@Column(name = "PK_ID")
	private int pk_id;

	@Column(name = "V_VALOR")
	private String v_valor;

	@Column(name = "TOTAL_REGISTROS")
	private int total_registros;

	@Column(name = "VC_NOMBRESUCURSAL")
	private String vc_nombresucursal;

	public int getPk_id() {
		return pk_id;
	}

	public void setPk_id(int pk_id) {
		this.pk_id = pk_id;
	}

	public String getV_valor() {
		return v_valor;
	}

	public void setV_valor(String v_valor) {
		this.v_valor = v_valor;
	}

	public int getTotal_registros() {
		return total_registros;
	}

	public void setTotal_registros(int total_registros) {
		this.total_registros = total_registros;
	}

	public String getVc_nombresucursal() {
		return vc_nombresucursal;
	}

	public void setVc_nombresucursal(String vc_nombresucursal) {
		this.vc_nombresucursal = vc_nombresucursal;
	}

}
