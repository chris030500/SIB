package ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClientesPorSucursal {
	@Id
	@Column(name = "I_ClienteID")
	private int i_clienteid;

	@Column(name = "VC_NombreCompleto")
	private String vc_nombrecompleto;

	@Column(name = "C_Estatus")
	private String c_estatus;

	public int getI_clienteid() {
		return i_clienteid;
	}

	public void setI_clienteid(int i_clienteid) {
		this.i_clienteid = i_clienteid;
	}

	public String getVc_nombrecompleto() {
		return vc_nombrecompleto;
	}

	public void setVc_nombrecompleto(String vc_nombrecompleto) {
		this.vc_nombrecompleto = vc_nombrecompleto;
	}

	public String getC_estatus() {
		return c_estatus;
	}

	public void setC_estatus(String c_estatus) {
		this.c_estatus = c_estatus;
	}
	
	
}
