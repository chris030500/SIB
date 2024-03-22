package ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NumClienteModel {

	@Id
	@Column(name = "I_ClienteID")
	private int i_clienteid;

	@Column(name = "ClienteInfo")
	private String clienteinfo;

	public int getI_clienteid() {
		return i_clienteid;
	}

	public void setI_clienteid(int i_clienteid) {
		this.i_clienteid = i_clienteid;
	}

	public String getClienteinfo() {
		return clienteinfo;
	}

	public void setClienteinfo(String clienteinfo) {
		this.clienteinfo = clienteinfo;
	}

}
