package ahorrosbienestar.sib.std.ofertasAutomaticas.procesoBases.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="TBL_PROCESOS_OA")
public class ProcesoBase {
	
	@Column(name = "PK_ID_PROCESO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_id_proceso;
	
	@Column(name = "I_TIPO_BASE", columnDefinition = "INT")
	private int i_tipo_base;
	
	@Column(name = "VC_ESTATUS_PROCESO", columnDefinition = "VARCHAR(1)")
	private String vc_estatus_proceso;
	
	@Column(name = "VC_DET_FALLO", columnDefinition = "TEXT")
	private String vc_det_fallo;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_INACTIVACION")
	private LocalDate dt_fecha_inactivacion;

	public int getPk_id_proceso() {
		return pk_id_proceso;
	}

	public void setPk_id_proceso(int pk_id_proceso) {
		this.pk_id_proceso = pk_id_proceso;
	}

	public int getI_tipo_base() {
		return i_tipo_base;
	}

	public void setI_tipo_base(int i_tipo_base) {
		this.i_tipo_base = i_tipo_base;
	}

	public String getVc_estatus_proceso() {
		return vc_estatus_proceso;
	}

	public void setVc_estatus_proceso(String vc_estatus_proceso) {
		this.vc_estatus_proceso = vc_estatus_proceso;
	}

	public String getVc_det_fallo() {
		return vc_det_fallo;
	}

	public void setVc_det_fallo(String vc_det_fallo) {
		this.vc_det_fallo = vc_det_fallo;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public LocalDate getDt_fecha_inactivacion() {
		return dt_fecha_inactivacion;
	}

	public void setDt_fecha_inactivacion(LocalDate dt_fecha_inactivacion) {
		this.dt_fecha_inactivacion = dt_fecha_inactivacion;
	}
	
}

