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
@Table(name = "TBL_ASIGNACION_OFERTAS")
public class AsignacionOfertas {

	@Column(name = "PK_ID_ASIGNACION_OA")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_id_asignacion_oa;

	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1) DEFAULT 1")
	private int i_estatus;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_ASIGNACION", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_asignacion;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_VIGENCIA", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_vigencia;

	@Column(name = "FK_ID_OFERTAS_AUTOMATICAS", columnDefinition = "INT DEFAULT NULL")
	private int fk_id_ofertas_automaticas;

	@Column(name = "FK_ID_SUCURSAL", columnDefinition = "INT DEFAULT NULL")
	private int fk_id_sucursal;

	@Column(name = "FK_ID_PROCESO", columnDefinition = "INT DEFAULT 0")
	private int fk_id_proceso;

	@Column(name = "I_ASIGNADO", columnDefinition = "TINYINT(1) DEFAULT 0")
	private int i_asignado;
	
	@Column(name = "I_ACTIVO", columnDefinition = "TINYINT(1) DEFAULT 1")
	private int i_activo;

	@Column(name = "I_PROCESO_ASIGNACION", columnDefinition = "INT DEFAULT 0")
	private int i_proceso_asignacion;
	
	@Column(name = "I_SMS", columnDefinition = "TINYINT(1) DEFAULT 0")
	private int i_sms;
	
	@Column(name = "VC_COD_REFERENCIA", columnDefinition = "VARCHAR(15)")
	private String vc_cod_referencia;
	
	@Column(name = "I_SUC_CREDITO", columnDefinition = "INT DEFAULT NULL")
	private int i_suc_credito;
	
	@Column(name = "I_REASIGNAR", columnDefinition = "TINYINT(1) DEFAULT 0")
	private int i_reasignar;
	
	@Column(name = "I_ANTERIOR_SUC", columnDefinition = "INT DEFAULT 0")
	private int i_anterior_suc;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REASIGNACION_NO_SEGUIMIENTO", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_reasignacion_n_seg;

	@Column(name = "I_BLOQUEO", columnDefinition = "INT DEFAULT 0")
	private int i_bloqueo;

	public int getPk_id_asignacion_oa() {
		return pk_id_asignacion_oa;
	}

	public void setPk_id_asignacion_oa(int pk_id_asignacion_oa) {
		this.pk_id_asignacion_oa = pk_id_asignacion_oa;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public LocalDate getDt_fecha_asignacion() {
		return dt_fecha_asignacion;
	}

	public void setDt_fecha_asignacion(LocalDate dt_fecha_asignacion) {
		this.dt_fecha_asignacion = dt_fecha_asignacion;
	}

	public LocalDate getDt_fecha_vigencia() {
		return dt_fecha_vigencia;
	}

	public void setDt_fecha_vigencia(LocalDate dt_fecha_vigencia) {
		this.dt_fecha_vigencia = dt_fecha_vigencia;
	}

	public int getFk_id_ofertas_automaticas() {
		return fk_id_ofertas_automaticas;
	}

	public void setFk_id_ofertas_automaticas(int fk_id_ofertas_automaticas) {
		this.fk_id_ofertas_automaticas = fk_id_ofertas_automaticas;
	}

	public int getFk_id_sucursal() {
		return fk_id_sucursal;
	}

	public void setFk_id_sucursal(int fk_id_sucursal) {
		this.fk_id_sucursal = fk_id_sucursal;
	}

	public int getFk_id_proceso() {
		return fk_id_proceso;
	}

	public void setFk_id_proceso(int fk_id_proceso) {
		this.fk_id_proceso = fk_id_proceso;
	}

	public int getI_asignado() {
		return i_asignado;
	}

	public void setI_asignado(int i_asignado) {
		this.i_asignado = i_asignado;
	}

	public int getI_activo() {
		return i_activo;
	}

	public void setI_activo(int i_activo) {
		this.i_activo = i_activo;
	}

	public int getI_proceso_asignacion() {
		return i_proceso_asignacion;
	}

	public void setI_proceso_asignacion(int i_proceso_asignacion) {
		this.i_proceso_asignacion = i_proceso_asignacion;
	}

	public int getI_sms() {
		return i_sms;
	}

	public void setI_sms(int i_sms) {
		this.i_sms = i_sms;
	}

	public String getVc_cod_referencia() {
		return vc_cod_referencia;
	}

	public void setVc_cod_referencia(String vc_cod_referencia) {
		this.vc_cod_referencia = vc_cod_referencia;
	}

	public int getI_suc_credito() {
		return i_suc_credito;
	}

	public void setI_suc_credito(int i_suc_credito) {
		this.i_suc_credito = i_suc_credito;
	}

	public int getI_reasignar() {
		return i_reasignar;
	}

	public void setI_reasignar(int i_reasignar) {
		this.i_reasignar = i_reasignar;
	}

	public int getI_anterior_suc() {
		return i_anterior_suc;
	}

	public void setI_anterior_suc(int i_anterior_suc) {
		this.i_anterior_suc = i_anterior_suc;
	}

	public LocalDate getDt_fecha_reasignacion_n_seg() {
		return dt_fecha_reasignacion_n_seg;
	}

	public void setDt_fecha_reasignacion_n_seg(LocalDate dt_fecha_reasignacion_n_seg) {
		this.dt_fecha_reasignacion_n_seg = dt_fecha_reasignacion_n_seg;
	}

	public int getI_bloqueo() {
		return i_bloqueo;
	}

	public void setI_bloqueo(int i_bloqueo) {
		this.i_bloqueo = i_bloqueo;
	}

	
}

