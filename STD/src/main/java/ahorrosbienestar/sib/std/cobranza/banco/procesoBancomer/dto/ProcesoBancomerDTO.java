package ahorrosbienestar.sib.std.cobranza.banco.procesoBancomer.dto;

public class ProcesoBancomerDTO {
	
	private String fechaAplicacion;
	private String importe;
	private String numeroBanco;
	private String numeroCuenta;
	private String importeIVA;
	private String motivoDevolucion;
	private String referenciaNumerica;
	
	public String getFechaAplicacion() {
		return fechaAplicacion;
	}
	public void setFechaAplicacion(String fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getNumeroBanco() {
		return numeroBanco;
	}
	public void setNumeroBanco(String numeroBanco) {
		this.numeroBanco = numeroBanco;
	}
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public String getImporteIVA() {
		return importeIVA;
	}
	public void setImporteIVA(String importeIVA) {
		this.importeIVA = importeIVA;
	}
	public String getMotivoDevolucion() {
		return motivoDevolucion;
	}
	public void setMotivoDevolucion(String motivoDevolucion) {
		this.motivoDevolucion = motivoDevolucion;
	}
	public String getReferenciaNumerica() {
		return referenciaNumerica;
	}
	public void setReferenciaNumerica(String referenciaNumerica) {
		this.referenciaNumerica = referenciaNumerica;
	}

}
