package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="TBL_BASES_OA")
public class Bases {
	
	@Column(name = "PK_ID_BASE")
	@Id
	private int pk_id_base;
	
	@Column(name = "VC_NOMBRE_BASE", columnDefinition = "VARCHAR(100)")
	private String vc_nombre_base;
	
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
	
	@Column(name = "VC_RUTA_PROCESO", columnDefinition = "VARCHAR(200) DEFAULT NULL")
	private String vc_ruta_proceso;
	
	@Column(name = "I_MENSAJE_PROCESO", 
			columnDefinition = "TINYINT(1) DEFAULT NULL COMMENT '1 = Mensaje Automatico, 2 = Mensaje Manual, 0 = No necesita mensaje'")
	private int i_mensaje_proceso;
	

	@Column(name = "I_ESTATUS_BASE", 
			columnDefinition = "TINYINT(1) DEFAULT NULL")
	private int i_estatus_base;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_BAJA", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_baja;
	
	@Column(name = "I_SUBIR_ARCHIVO", 
	columnDefinition = "INT DEFAULT 0")
	private int i_subir_archivo;

	public int getI_subir_archivo() {
		return i_subir_archivo;
	}

	public void setI_subir_archivo(int i_subir_archivo) {
		this.i_subir_archivo = i_subir_archivo;
	}

	public int getPk_id_base() {
		return pk_id_base;
	}

	public void setPk_id_base(int pk_id_base) {
		this.pk_id_base = pk_id_base;
	}

	public String getVc_nombre_base() {
		return vc_nombre_base;
	}

	public void setVc_nombre_base(String vc_nombre_base) {
		this.vc_nombre_base = vc_nombre_base;
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

	public String getVc_ruta_proceso() {
		return vc_ruta_proceso;
	}

	public void setVc_ruta_proceso(String vc_ruta_proceso) {
		this.vc_ruta_proceso = vc_ruta_proceso;
	}

	public int getI_mensaje_proceso() {
		return i_mensaje_proceso;
	}

	public void setI_mensaje_proceso(int i_mensaje_proceso) {
		this.i_mensaje_proceso = i_mensaje_proceso;
	}

	public int getI_estatus_base() {
		return i_estatus_base;
	}

	public void setI_estatus_base(int i_estatus_base) {
		this.i_estatus_base = i_estatus_base;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public LocalDate getDt_fecha_baja() {
		return dt_fecha_baja;
	}

	public void setDt_fecha_baja(LocalDate dt_fecha_baja) {
		this.dt_fecha_baja = dt_fecha_baja;
	}

	


}
