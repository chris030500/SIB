package ahorrosbienestar.sib.std.convenios.domiciliado.dto;


public class ClabeDTO {
	
	private int clienteId;
	private String clabe;
	private String rfcOficial;
	private String creditoId;

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
