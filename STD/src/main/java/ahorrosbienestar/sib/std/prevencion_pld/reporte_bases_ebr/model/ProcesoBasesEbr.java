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
	private Integer pk_proceso_id;
	
	@Column(name = "I_PERIODO_EJECUCION")
	private Integer i_periodo_ejecucion;
	
	@Column(name = "I_USUARIO_EJECUTO")
	private Integer i_usuario_ejecuto;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "DT_FECHA_EJECUCION")
	private LocalDateTime dt_fecha_ejecucion;
	
	@Column(name = "I_ESTATUS_VOBO")
	private Boolean i_estatus_vobo;
	
	@Column(name = "I_USUARIO_VOBO")
	private Integer i_usuario_vobo;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "DT_FECHA_VOBO")
	private LocalDateTime dt_fecha_vobo;

	public Integer getPk_proceso_id() {
		return pk_proceso_id;
	}

	public void setPk_proceso_id(Integer pk_proceso_id) {
		this.pk_proceso_id = pk_proceso_id;
	}

	public Integer getI_periodo_ejecucion() {
		return i_periodo_ejecucion;
	}

	public void setI_periodo_ejecucion(Integer i_periodo_ejecucion) {
		this.i_periodo_ejecucion = i_periodo_ejecucion;
	}

	public Integer getI_usuario_ejecuto() {
		return i_usuario_ejecuto;
	}

	public void setI_usuario_ejecuto(Integer i_usuario_ejecuto) {
		this.i_usuario_ejecuto = i_usuario_ejecuto;
	}

	public LocalDateTime getDt_fecha_ejecucion() {
		return dt_fecha_ejecucion;
	}

	public void setDt_fecha_ejecucion(LocalDateTime dt_fecha_ejecucion) {
		this.dt_fecha_ejecucion = dt_fecha_ejecucion;
	}

	public Boolean getI_estatus_vobo() {
		return i_estatus_vobo;
	}

	public void setI_estatus_vobo(Boolean i_estatus_vobo) {
		this.i_estatus_vobo = i_estatus_vobo;
	}

	public Integer getI_usuario_vobo() {
		return i_usuario_vobo;
	}

	public void setI_usuario_vobo(Integer i_usuario_vobo) {
		this.i_usuario_vobo = i_usuario_vobo;
	}

	public LocalDateTime getDt_fecha_vobo() {
		return dt_fecha_vobo;
	}

	public void setDt_fecha_vobo(LocalDateTime dt_fecha_vobo) {
		this.dt_fecha_vobo = dt_fecha_vobo;
	}

	@Override
	public String toString() {
		return "ProcesoBasesEbr [pk_proceso_id=" + pk_proceso_id + ", i_periodo_ejecucion=" + i_periodo_ejecucion
				+ ", i_usuario_ejecuto=" + i_usuario_ejecuto + ", dt_fecha_ejecucion=" + dt_fecha_ejecucion
				+ ", i_estatus_vobo=" + i_estatus_vobo + ", i_usuario_vobo=" + i_usuario_vobo + ", dt_fecha_vobo="
				+ dt_fecha_vobo + "]";
	}
}
