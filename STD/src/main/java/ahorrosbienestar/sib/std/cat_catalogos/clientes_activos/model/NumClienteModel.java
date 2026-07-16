package ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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
