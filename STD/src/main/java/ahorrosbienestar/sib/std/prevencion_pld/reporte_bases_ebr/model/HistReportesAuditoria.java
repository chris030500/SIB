package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_HISTORIAL_REPORTES_AUDITORIAS")
public class HistReportesAuditoria {
	
	@Id
	@Column(name = "PK_PROCESO_HISTORICO")
	private Integer procesoHistId;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DT_FECHA_GENERACION")
	private LocalDateTime fechaGeneracion;
	
	@Column(name = "I_USUARIO")
	private Integer usuarioId;
	
	@Column(name = "I_USUARIO_VOBO")
	private Integer usuarioVoboId;
	
	@Column(name = "I_ESTATUS")
	private Boolean estatus;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DT_FECHA_VOBO")
	private LocalDateTime fechaVobo;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "DT_FechaInicio")
	private LocalDate fechaInicio;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "DT_FechaFinal")
	private LocalDate fechaFinal;
	
	@Column(name = "I_ANIO_GENERACION")
	private String anioGeneracion;
	
	@Column(name = "VC_TIPO_REPORTE")
	private String tipoReporte;

	public Integer getProcesoHistId() {
		return procesoHistId;
	}

	public void setProcesoHistId(Integer procesoHistId) {
		this.procesoHistId = procesoHistId;
	}

	public LocalDateTime getFechaGeneracion() {
		return fechaGeneracion;
	}

	public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getUsuarioVoboId() {
		return usuarioVoboId;
	}

	public void setUsuarioVoboId(Integer usuarioVoboId) {
		this.usuarioVoboId = usuarioVoboId;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public LocalDateTime getFechaVobo() {
		return fechaVobo;
	}

	public void setFechaVobo(LocalDateTime fechaVobo) {
		this.fechaVobo = fechaVobo;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(LocalDate fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public String getAnioGeneracion() {
		return anioGeneracion;
	}

	public void setAnioGeneracion(String anioGeneracion) {
		this.anioGeneracion = anioGeneracion;
	}

	public String getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	@Override
	public String toString() {
		return "HistReportesAuditoria [procesoHistId=" + procesoHistId + ", fechaGeneracion=" + fechaGeneracion
				+ ", usuarioId=" + usuarioId + ", usuarioVoboId=" + usuarioVoboId + ", estatus=" + estatus
				+ ", fechaVobo=" + fechaVobo + ", fechaInicio=" + fechaInicio + ", fechaFinal=" + fechaFinal
				+ ", anioGeneracion=" + anioGeneracion + ", tipoReporte=" + tipoReporte + "]";
	}
	
}
