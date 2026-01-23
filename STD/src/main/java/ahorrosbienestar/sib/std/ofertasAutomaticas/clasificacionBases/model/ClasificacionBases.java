package ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;

@Entity
@Table(name = "TBL_CLASIFICACION_BASES_OA")
public class ClasificacionBases {
	
	@Column(name = "PK_ID_CLASIFICACION")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_id_clasificacion;
	
	@Column(name = "VC_NOMBRE_CLASIFICACION", columnDefinition = "VARCHAR(100) DEFAULT NULL")
	private String vc_nombre_clasificacion;
	
	@Column(name = "VC_ABREVIACION_CLASIFICACION", columnDefinition = "VARCHAR(100) DEFAULT NULL")
	private String vc_abreviacion_clasificacion;
	
	@Column(name = "VC_CODIGO_CLASIFICACION", columnDefinition = "VARCHAR(2) DEFAULT NULL")
	private String vc_codigo_clasificacion;
	
	@Column(name = "I_VIGENCIA_CLASIFICACION", columnDefinition = "INT DEFAULT NULL")
	private int i_vigencia_clasificacion;
	
	@Column(name = "I_CONFIGURACION_MENSAJE", columnDefinition = "INT DEFAULT NULL")
	private int i_configuracion_mensaje;
	
	@Column(name = "VC_MENSAJE_CLASIFICACION", columnDefinition = "VARCHAR (200) DEFAULT NULL")
	private String vc_mensaje_clasificacion;


	@Column(name = "I_ESTATUS_CLASIFICACION", columnDefinition = "INT DEFAULT NULL COMMENT '1 = ACTIVO, 0 = INACTIVO'")
	private int i_estatus_clasificacion;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "DT_FECHA_REGISTRO", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDateTime  dt_fecha_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "DT_FECHA_BAJA", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDateTime  dt_fecha_baja;
	
	@OneToOne
	@JoinColumn(name = "FK_ID_BASE")
	private Bases fk_id_base;

	public int getPk_id_clasificacion() {
		return pk_id_clasificacion;
	}

	public void setPk_id_clasificacion(int pk_id_clasificacion) {
		this.pk_id_clasificacion = pk_id_clasificacion;
	}

	public String getVc_nombre_clasificacion() {
		return vc_nombre_clasificacion;
	}

	public void setVc_nombre_clasificacion(String vc_nombre_clasificacion) {
		this.vc_nombre_clasificacion = vc_nombre_clasificacion;
	}

	public String getVc_abreviacion_clasificacion() {
		return vc_abreviacion_clasificacion;
	}

	public void setVc_abreviacion_clasificacion(String vc_abreviacion_clasificacion) {
		this.vc_abreviacion_clasificacion = vc_abreviacion_clasificacion;
	}

	public String getVc_codigo_clasificacion() {
		return vc_codigo_clasificacion;
	}

	public void setVc_codigo_clasificacion(String vc_codigo_clasificacion) {
		this.vc_codigo_clasificacion = vc_codigo_clasificacion;
	}

	public int getI_vigencia_clasificacion() {
		return i_vigencia_clasificacion;
	}

	public void setI_vigencia_clasificacion(int i_vigencia_clasificacion) {
		this.i_vigencia_clasificacion = i_vigencia_clasificacion;
	}


	public int getI_configuracion_mensaje() {
		return i_configuracion_mensaje;
	}

	public void setI_configuracion_mensaje(int i_configuracion_mensaje) {
		this.i_configuracion_mensaje = i_configuracion_mensaje;
	}

	public String getVc_mensaje_clasificacion() {
		return vc_mensaje_clasificacion;
	}

	public void setVc_mensaje_clasificacion(String vc_mensaje_clasificacion) {
		this.vc_mensaje_clasificacion = vc_mensaje_clasificacion;
	}

	public int getI_estatus_clasificacion() {
		return i_estatus_clasificacion;
	}

	public void setI_estatus_clasificacion(int i_estatus_clasificacion) {
		this.i_estatus_clasificacion = i_estatus_clasificacion;
	}


	public LocalDateTime getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDateTime dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public LocalDateTime getDt_fecha_baja() {
		return dt_fecha_baja;
	}

	public void setDt_fecha_baja(LocalDateTime dt_fecha_baja) {
		this.dt_fecha_baja = dt_fecha_baja;
	}

	public Bases getFk_id_base() {
		return fk_id_base;
	}

	public void setFk_id_base(Bases fk_id_base) {
		this.fk_id_base = fk_id_base;
	}

	

}

