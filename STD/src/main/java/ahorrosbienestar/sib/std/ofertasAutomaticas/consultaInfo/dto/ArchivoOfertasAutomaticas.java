package ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto;

public class ArchivoOfertasAutomaticas {
	
	private String nombreArchivo;
	private String pesoArchivo;
	private String fechaCreacion;
	private String fechaModifacion;
	private String rutaArchivo;
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getPesoArchivo() {
		return pesoArchivo;
	}
	public void setPesoArchivo(String pesoArchivo) {
		this.pesoArchivo = pesoArchivo;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getFechaModifacion() {
		return fechaModifacion;
	}
	public void setFechaModifacion(String fechaModifacion) {
		this.fechaModifacion = fechaModifacion;
	}
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	
}
