package ahorrosbienestar.sib.std.catalogos.dto;

public class ActividadesPA {
	
	private int id_planaccion;
	private String nombreActividad;
	private String nombreResponsable;
	private String dtFechaInicio;
	private String dtFechaVencimiento; 
	private String Estatus;
	public int getId_planaccion() {
		return id_planaccion;
	}
	public void setId_planaccion(int id_planaccion) {
		this.id_planaccion = id_planaccion;
	}
	public String getNombreActividad() {
		return nombreActividad;
	}
	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}
	public String getNombreResponsable() {
		return nombreResponsable;
	}
	public void setNombreResponsable(String nombreResponsable) {
		this.nombreResponsable = nombreResponsable;
	}
	public String getDtFechaInicio() {
		return dtFechaInicio;
	}
	public void setDtFechaInicio(String dtFechaInicio) {
		this.dtFechaInicio = dtFechaInicio;
	}
	public String getDtFechaVencimiento() {
		return dtFechaVencimiento;
	}
	public void setDtFechaVencimiento(String dtFechaVencimiento) {
		this.dtFechaVencimiento = dtFechaVencimiento;
	}
	public String getEstatus() {
		return Estatus;
	}
	public void setEstatus(String estatus) {
		Estatus = estatus;
	}

}
