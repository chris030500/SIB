package ahorrosbienestar.sib.std.videoGrabacion.model;

public class VideoGrabacionDatos {

	private int pk_id_solicitud_envio;
	private int clienteID;
	private int solicitudID;
	private String Estatus;
	private String Producto;
	private String fechaRegistro;
	private String fechaAutorizacion;
	private String fechaCancelacion;
	private String fechaEnvio;
	
	public int getPk_id_solicitud_envio() {
		return pk_id_solicitud_envio;
	}
	public void setPk_id_solicitud_envio(int pk_id_solicitud_envio) {
		this.pk_id_solicitud_envio = pk_id_solicitud_envio;
	}
	public int getClienteID() {
		return clienteID;
	}
	public void setClienteID(int clienteID) {
		this.clienteID = clienteID;
	}
	public int getSolicitudID() {
		return solicitudID;
	}
	public void setSolicitudID(int solicitudID) {
		this.solicitudID = solicitudID;
	}
	public String getEstatus() {
		return Estatus;
	}
	public void setEstatus(String estatus) {
		Estatus = estatus;
	}
	public String getProducto() {
		return Producto;
	}
	public void setProducto(String producto) {
		Producto = producto;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getFechaAutorizacion() {
		return fechaAutorizacion;
	}
	public void setFechaAutorizacion(String fechaAutorizacion) {
		this.fechaAutorizacion = fechaAutorizacion;
	}
	public String getFechaCancelacion() {
		return fechaCancelacion;
	}
	public void setFechaCancelacion(String fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}
	public String getFechaEnvio() {
		return fechaEnvio;
	}
	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

}
