package ahorrosbienestar.sib.std.convenios.domiciliado.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CUENTASTRANS_MF")
public class CuentasModel {
	
	@Id
	@Column(name = "ClienteID")
	private int clienteId;
	
	@Column(name = "RFCOficial")
	private String rfcOficial;
	
	@Column(name = "CreditoID")
	private String creditoId;
	
	@Column(name = "Clabe")
	private String clabe;

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public String getRfcOficial() {
		return rfcOficial;
	}

	public void setRfcOficial(String rfcOficial) {
		this.rfcOficial = rfcOficial;
	}

	public String getCreditoId() {
		return creditoId;
	}

	public void setCreditoId(String creditoId) {
		this.creditoId = creditoId;
	}

	public String getClabe() {
		return clabe;
	}

	public void setClabe(String clabe) {
		this.clabe = clabe;
	}

}
