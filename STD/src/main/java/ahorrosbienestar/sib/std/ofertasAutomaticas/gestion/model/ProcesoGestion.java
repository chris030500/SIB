package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model;

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
@Table(name="TBL_GESTION_OA")
public class ProcesoGestion {
	@Column(name = "PK_ID_GESTION")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_id_gestion;
	
	@Column(name = "VC_NOMBRE_PROCESO", columnDefinition = "VARCHAR(100)")
	private String vc_nombre_proceso;
	
	@Column(name = "VC_DIA_INICIO_PROCESO", columnDefinition = "VARCHAR(5) DEFAULT NULL")
	private String vc_dia_inicio_proceso;
	
	@Column(name = "VC_DIA_FIN_PROCESO", columnDefinition = "VARCHAR(5) DEFAULT NULL")
	private String vc_dia_fin_proceso;
	
	@Column(name = "VC_HORA_INICIO_PROCESO", columnDefinition = "VARCHAR(5) DEFAULT NULL")
	private String vc_hora_inicio_proceso;
	
	@Column(name = "VC_HORA_FIN_PROCESO", columnDefinition = "VARCHAR(5) DEFAULT NULL")
	private String vc_hora_fin_proceso;
	
	@Column(name = "VC_PERIODO_EJECUCION_PROCESO", 
			columnDefinition = "VARCHAR(5) DEFAULT NULL COMMENT 'El periodo de ejecucion se lleva a cabo en dias'")
	private String vc_periodo_ejecucion_proceso;	

	@Column(name = "I_ESTATUS_PROCESO", 
			columnDefinition = "TINYINT(1) DEFAULT NULL")
	private int i_estatus_proceso;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_EJECUCION", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_ejecucion;
	
	@Column(name = "I_VISIBLE", 
			columnDefinition = "TINYINT(1) DEFAULT 0")
	private int i_visible;
	
	@Column(name = "I_REPORTE", 
			columnDefinition = "TINYINT(1) DEFAULT 0")
	private int i_reporte;

	public int getPk_id_gestion() {
		return pk_id_gestion;
	}

	public void setPk_id_gestion(int pk_id_gestion) {
		this.pk_id_gestion = pk_id_gestion;
	}

	public String getVc_nombre_proceso() {
		return vc_nombre_proceso;
	}

	public void setVc_nombre_proceso(String vc_nombre_proceso) {
		this.vc_nombre_proceso = vc_nombre_proceso;
	}

	public String getVc_dia_inicio_proceso() {
		return vc_dia_inicio_proceso;
	}

	public void setVc_dia_inicio_proceso(String vc_dia_inicio_proceso) {
		this.vc_dia_inicio_proceso = vc_dia_inicio_proceso;
	}

	public String getVc_dia_fin_proceso() {
		return vc_dia_fin_proceso;
	}

	public void setVc_dia_fin_proceso(String vc_dia_fin_proceso) {
		this.vc_dia_fin_proceso = vc_dia_fin_proceso;
	}

	public String getVc_hora_inicio_proceso() {
		return vc_hora_inicio_proceso;
	}

	public void setVc_hora_inicio_proceso(String vc_hora_inicio_proceso) {
		this.vc_hora_inicio_proceso = vc_hora_inicio_proceso;
	}

	public String getVc_hora_fin_proceso() {
		return vc_hora_fin_proceso;
	}

	public void setVc_hora_fin_proceso(String vc_hora_fin_proceso) {
		this.vc_hora_fin_proceso = vc_hora_fin_proceso;
	}

	public String getVc_periodo_ejecucion_proceso() {
		return vc_periodo_ejecucion_proceso;
	}

	public void setVc_periodo_ejecucion_proceso(String vc_periodo_ejecucion_proceso) {
		this.vc_periodo_ejecucion_proceso = vc_periodo_ejecucion_proceso;
	}

	public int getI_estatus_proceso() {
		return i_estatus_proceso;
	}

	public void setI_estatus_proceso(int i_estatus_proceso) {
		this.i_estatus_proceso = i_estatus_proceso;
	}

	public LocalDate getDt_fecha_ejecucion() {
		return dt_fecha_ejecucion;
	}

	public void setDt_fecha_ejecucion(LocalDate dt_fecha_ejecucion) {
		this.dt_fecha_ejecucion = dt_fecha_ejecucion;
	}

	public int getI_visible() {
		return i_visible;
	}

	public void setI_visible(int i_visible) {
		this.i_visible = i_visible;
	}

	public int getI_reporte() {
		return i_reporte;
	}

	public void setI_reporte(int i_reporte) {
		this.i_reporte = i_reporte;
	}
}
