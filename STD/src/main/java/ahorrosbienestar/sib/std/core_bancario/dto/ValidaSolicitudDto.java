package ahorrosbienestar.sib.std.core_bancario.dto;

public class ValidaSolicitudDto {
	
	private int resultado;
	private String mensajeError;
	private int solicitudCreditoID;
	private int productoCreditoID;
	private String producto;
	private double montoSolicitado;
	private double montoAutorizado;
	private double tasaFija;
	private String plazo;
	private String estatus;
	public int getResultado() {
		return resultado;
	}
	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
	public int getSolicitudCreditoID() {
		return solicitudCreditoID;
	}
	public void setSolicitudCreditoID(int solicitudCreditoID) {
		this.solicitudCreditoID = solicitudCreditoID;
	}
	public int getProductoCreditoID() {
		return productoCreditoID;
	}
	public void setProductoCreditoID(int productoCreditoID) {
		this.productoCreditoID = productoCreditoID;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	
	public double getMontoSolicitado() {
		return montoSolicitado;
	}
	public void setMontoSolicitado(double montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}
	public double getMontoAutorizado() {
		return montoAutorizado;
	}
	public void setMontoAutorizado(double montoAutorizado) {
		this.montoAutorizado = montoAutorizado;
	}
	public double getTasaFija() {
		return tasaFija;
	}
	public void setTasaFija(double tasaFija) {
		this.tasaFija = tasaFija;
	}
	public void setTasaFija(float tasaFija) {
		this.tasaFija = tasaFija;
	}
	public String getPlazo() {
		return plazo;
	}
	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
		

}
