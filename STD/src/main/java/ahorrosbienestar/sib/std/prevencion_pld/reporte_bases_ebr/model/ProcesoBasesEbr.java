package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model;

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
	
	@Column(name = "I_ANIO_AUDITORIA")	
	private Integer anioAuditoria;
	
	@Column(name = "I_USUARIO_EJECUTO")
	private Integer usuarioEjecuto;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DT_FECHA_EJECUCION")
	private LocalDateTime fechaEjecucion;
	
	@Column(name = "I_ESTATUS_EJECUCION")
	private Boolean estatusEjecucion;
	
	@Column(name = "I_ESTATUS_VOBO")
	private Boolean estatusVobo;
	
	@Column(name = "I_USUARIO_VOBO")
	private Integer usuarioVobo;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DT_FECHA_VOBO")
	private LocalDateTime fechaVobo;
	
	@Column(name = "I_ESTATUS_PROCESO")
	private Integer estatusProceso;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Column(name = "DT_FECHA_TERMINO")
	private LocalDateTime fechaTermino;

	public Integer getProcesoId() {
		return procesoId;
	}

	public Integer getAnioAuditoria() {
		return anioAuditoria;
	}

	public void setAnioAuditoria(Integer anioAuditoria) {
		this.anioAuditoria = anioAuditoria;
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

	public Integer getEstatusProceso() {
		return estatusProceso;
	}

	public void setEstatusProceso(Integer estatusProceso) {
		this.estatusProceso = estatusProceso;
	}

	public LocalDateTime getFechaTermino() {
		return fechaTermino;
	}

	public void setFechaTermino(LocalDateTime fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public void setProcesoId(Integer procesoId) {
		this.procesoId = procesoId;
	}

	@Override
	public String toString() {
		return "ProcesoBasesEbr [procesoId=" + procesoId + ", anioAuditoria=" + anioAuditoria + ", usuarioEjecuto="
				+ usuarioEjecuto + ", fechaEjecucion=" + fechaEjecucion + ", estatusEjecucion=" + estatusEjecucion
				+ ", estatusVobo=" + estatusVobo + ", usuarioVobo=" + usuarioVobo + ", fechaVobo=" + fechaVobo
				+ ", estatusProceso=" + estatusProceso + ", fechaTermino=" + fechaTermino + "]";
	}
	
}
