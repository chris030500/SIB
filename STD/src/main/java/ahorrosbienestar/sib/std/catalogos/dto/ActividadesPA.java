package ahorrosbienestar.sib.std.catalogos.dto;

public class ActividadesPA {
	
	private int id_planaccion;
	private String nombreActividad;
	private String nombreResponsable;
	private String dtFechaInicio;
	private String dtFechaVencimiento; 
	private String Estatus;
	private int i_confirmacion_encargado;
	private String vc_estatus_encargado;
	private int i_confirmacion_responsable;
	private String vc_estatus_responsable;
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
	public int getI_confirmacion_encargado() {
		return i_confirmacion_encargado;
	}
	public void setI_confirmacion_encargado(int i_confirmacion_encargado) {
		this.i_confirmacion_encargado = i_confirmacion_encargado;
	}
	public int getI_confirmacion_responsable() {
		return i_confirmacion_responsable;
	}
	public void setI_confirmacion_responsable(int i_confirmacion_responsable) {
		this.i_confirmacion_responsable = i_confirmacion_responsable;
	}
	public String getVc_estatus_encargado() {
		return vc_estatus_encargado;
	}
	public void setVc_estatus_encargado(String vc_estatus_encargado) {
		this.vc_estatus_encargado = vc_estatus_encargado;
	}
	public String getVc_estatus_responsable() {
		return vc_estatus_responsable;
	}
	public void setVc_estatus_responsable(String vc_estatus_responsable) {
		this.vc_estatus_responsable = vc_estatus_responsable;
	}

}
