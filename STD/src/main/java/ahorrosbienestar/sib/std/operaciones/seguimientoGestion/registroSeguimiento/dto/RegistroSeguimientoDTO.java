package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.dto;

import java.time.LocalDate;

public class RegistroSeguimientoDTO {

	private int relacionCodigos;
	private int asignacionOfertas;
	private String referencia;
	private String descripcion;
	private LocalDate fechaCita;
	private LocalDate fechaGestion;

	public int getRelacionCodigos() {
		return relacionCodigos;
	}

	public void setRelacionCodigos(int relacionCodigos) {
		this.relacionCodigos = relacionCodigos;
	}

	public int getAsignacionOfertas() {
		return asignacionOfertas;
	}

	public void setAsignacionOfertas(int asignacionOfertas) {
		this.asignacionOfertas = asignacionOfertas;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(LocalDate fechaCita) {
		this.fechaCita = fechaCita;
	}

	public LocalDate getFechaGestion() {
		return fechaGestion;
	}

	public void setFechaGestion(LocalDate fechaGestion) {
		this.fechaGestion = fechaGestion;
	}

}
