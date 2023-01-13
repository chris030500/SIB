package ahorrosbienestar.sib.std.core_bancario.dto;

public class ValidaSolicitudDto {
	
	private int resultado;
	private String mensajeError;
	private int solicitudCreditoID;
	private int productoCreditoID;
	private String producto;
	private String montoSolicitado;
	private String montoAutorizado;
	private String tasaFija;
	private String plazo;
	private String estatus;
	private String productoNomina;
	
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
	
	public String getMontoSolicitado() {
		return montoSolicitado;
	}
	public void setMontoSolicitado(String montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}
	public String getMontoAutorizado() {
		return montoAutorizado;
	}
	public void setMontoAutorizado(String montoAutorizado) {
		this.montoAutorizado = montoAutorizado;
	}
	public String getTasaFija() {
		return tasaFija;
	}
	public void setTasaFija(String tasaFija) {
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
	public String getProductoNomina() {
		return productoNomina;
	}
	public void setProductoNomina(String productoNomina) {
		this.productoNomina = productoNomina;
	}
	
		

}
