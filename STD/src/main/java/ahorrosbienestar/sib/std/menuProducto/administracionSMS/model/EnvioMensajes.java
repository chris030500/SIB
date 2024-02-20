package ahorrosbienestar.sib.std.menuProducto.administracionSMS.model;

public class EnvioMensajes {

	private String telefonos;
	private String mensajes;
	
	@Override
	public String toString() {
		return "EnvioMensajes [telefonos=" + telefonos + ", mensajes=" + mensajes + ", getTelefonos()=" + getTelefonos()
				+ ", getMensajes()=" + getMensajes() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	public String getTelefonos() {
		return telefonos;
	}
	public void setTelefonos(String telefonos) {
		this.telefonos = telefonos;
	}
	public String getMensajes() {
		return mensajes;
	}
	public void setMensajes(String mensajes) {
		this.mensajes = mensajes;
	}
	
}
