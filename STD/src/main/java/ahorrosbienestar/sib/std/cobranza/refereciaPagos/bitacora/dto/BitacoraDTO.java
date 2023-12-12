package ahorrosbienestar.sib.std.cobranza.refereciaPagos.bitacora.dto;

public class BitacoraDTO {

	private int consultaId;
	private int numeroCredito;
	private String productoCredito;
	private int referenciaCredito;
	private String bancoCredito;

	public int getConsultaId() {
		return consultaId;
	}

	public void setConsultaId(int consultaId) {
		this.consultaId = consultaId;
	}

	public int getNumeroCredito() {
		return numeroCredito;
	}

	public void setNumeroCredito(int numeroCredito) {
		this.numeroCredito = numeroCredito;
	}

	public String getProductoCredito() {
		return productoCredito;
	}

	public void setProductoCredito(String productoCredito) {
		this.productoCredito = productoCredito;
	}

	public int getReferenciaCredito() {
		return referenciaCredito;
	}

	public void setReferenciaCredito(int referenciaCredito) {
		this.referenciaCredito = referenciaCredito;
	}

	public String getBancoCredito() {
		return bancoCredito;
	}

	public void setBancoCredito(String bancoCredito) {
		this.bancoCredito = bancoCredito;
	}

}
