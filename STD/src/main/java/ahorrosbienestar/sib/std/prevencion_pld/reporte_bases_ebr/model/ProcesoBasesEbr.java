package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_PLD_PROCESO_BASES_EBR")
public class ProcesoBasesEbr {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_PROCESO_EBR_ID")	
	private Integer procesoId;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "DT_FECHA_INICIO")
	private LocalDate fechaInicio;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "DT_FECHA_FIN")
	private LocalDate fechaFin;
	
	@Column(name = "I_USUARIO_EJECUTO")
	private Integer usuarioEjecuto;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DT_FECHA_EJECUCION")
	private LocalDateTime fechaEjecucion;
	
	@Column(name = "I_ESTATUS_EJECUCION")
	private Boolean estatusEjecucion;
	
	@Column(name = "VC_CRON")
	private String cron;
	
	@Column(name = "I_ESTATUS_VOBO")
	private Boolean estatusVobo;
	
	@Column(name = "I_USUARIO_VOBO")
	private Integer usuarioVobo;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DT_FECHA_VOBO")
	private LocalDateTime fechaVobo;

	public Integer getProcesoId() {
		return procesoId;
	}

	public void setProcesoId(Integer procesoId) {
		this.procesoId = procesoId;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Integer getUsuarioEjecuto() {
		return usuarioEjecuto;
	}

	public void setUsuarioEjecuto(Integer usuarioEjecuto) {
		this.usuarioEjecuto = usuarioEjecuto;
	}

	public LocalDateTime getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(LocalDateTime fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public Boolean getEstatusEjecucion() {
		return estatusEjecucion;
	}

	public void setEstatusEjecucion(Boolean estatusEjecucion) {
		this.estatusEjecucion = estatusEjecucion;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Boolean getEstatusVobo() {
		return estatusVobo;
	}

	public void setEstatusVobo(Boolean estatusVobo) {
		this.estatusVobo = estatusVobo;
	}

	public Integer getUsuarioVobo() {
		return usuarioVobo;
	}

	public void setUsuarioVobo(Integer usuarioVobo) {
		this.usuarioVobo = usuarioVobo;
	}

	public LocalDateTime getFechaVobo() {
		return fechaVobo;
	}

	public void setFechaVobo(LocalDateTime fechaVobo) {
		this.fechaVobo = fechaVobo;
	}

	@Override
	public String toString() {
		return "ProcesoBasesEbr [procesoId=" + procesoId + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", usuarioEjecuto=" + usuarioEjecuto + ", fechaEjecucion=" + fechaEjecucion + ", estatusEjecucion="
				+ estatusEjecucion + ", cron=" + cron + ", estatusVobo=" + estatusVobo + ", usuarioVobo=" + usuarioVobo
				+ ", fechaVobo=" + fechaVobo + "]";
	}
}
