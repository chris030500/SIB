package ahorrosbienestar.sib.std.catalogos.dto;

public class SeguimientoActividadPA {
	
	private int id_seguimiento;
	private String nombreActividad;
	private String desc_actividad;
	private String dtFechaRegistro;
	private String estatus;
	
	public int getId_seguimiento() {
		return id_seguimiento;
	}
	public void setId_seguimiento(int id_seguimiento) {
		this.id_seguimiento = id_seguimiento;
	}
	public String getNombreActividad() {
		return nombreActividad;
	}
	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}
	public String getDesc_actividad() {
		return desc_actividad;
	}
	public void setDesc_actividad(String desc_actividad) {
		this.desc_actividad = desc_actividad;
	}
	public String getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(String dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	
}
