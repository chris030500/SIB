package ahorrosbienestar.sib.std.operaciones.reportes.dto;

import java.util.List;

public class ReporteDTO {

	private List<ValoresDTO> estatusGestion;
	private List<ValoresDTO> valores;
	private String fechaInicio;
	private String fechaFin;
	private int busqueda;

	public List<ValoresDTO> getEstatusGestion() {
		return estatusGestion;
	}

	public void setEstatusGestion(List<ValoresDTO> estatusGestion) {
		this.estatusGestion = estatusGestion;
	}

	public List<ValoresDTO> getValores() {
		return valores;
	}

	public void setValores(List<ValoresDTO> valores) {
		this.valores = valores;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(int busqueda) {
		this.busqueda = busqueda;
	}

}
