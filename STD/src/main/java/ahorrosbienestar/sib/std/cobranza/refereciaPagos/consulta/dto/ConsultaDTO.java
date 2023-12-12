package ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.dto;

import java.math.BigInteger;

public class ConsultaDTO {

	private String parametroBusqueda;
	private String valorParametro;
	private BigInteger numeroCliente;

	public String getParametroBusqueda() {
		return parametroBusqueda;
	}

	public void setParametroBusqueda(String parametroBusqueda) {
		this.parametroBusqueda = parametroBusqueda;
	}

	public String getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
	}

	public BigInteger getNumeroCliente() {
		return numeroCliente;
	}

	public void setNumeroCliente(BigInteger numeroCliente) {
		this.numeroCliente = numeroCliente;
	}

}
