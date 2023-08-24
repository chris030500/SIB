package ahorrosbienestar.sib.std.convenios.domiciliado.dto;

public class ProcesoDTO {
	
	private String entrada;
	private String salida;
	private int banco;
	private int cantidadRegistro;
	private int totalMonto;
	
	public String getEntrada() {
		return entrada;
	}
	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}
	public String getSalida() {
		return salida;
	}
	public void setSalida(String salida) {
		this.salida = salida;
	}
	public int getBanco() {
		return banco;
	}
	public void setBanco(int banco) {
		this.banco = banco;
	}
	public int getCantidadRegistro() {
		return cantidadRegistro;
	}
	public void setCantidadRegistro(int cantidadRegistro) {
		this.cantidadRegistro = cantidadRegistro;
	}
	public int getTotalMonto() {
		return totalMonto;
	}
	public void setTotalMonto(int totalMonto) {
		this.totalMonto = totalMonto;
	}

}
