package ahorrosbienestar.sib.std.folios.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="TBL_OBSERVACIONESXFOLIO")
public class Observaciones {
	
	@Column(name = "PK_ID_OBSERVACION")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="tbl_observacion_seq")
	@SequenceGenerator(name = "tbl_observacion_seq", sequenceName = "tbl_observacion_seq", initialValue = 1, allocationSize=1)
	private int pk_id_observacion;
	
	//@ManyToOne
	//@JoinColumn(name = "FK_ID_FOLIO", columnDefinition = "INT")
	//private int fk_id_folio;
	
	@Column(name = "I_AREA", columnDefinition = "INT")
	private int i_area;
	
	@Column(name = "I_RESPONSABLE", columnDefinition = "INT")
	private int i_responsable;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_VENCIMIENTO")
	private LocalDate dt_fecha_vencimiento;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_ASGINACION")
	private LocalDate dt_fecha_asignacion;
	
	@Column(name = "VC_DESCRIPCION", columnDefinition = "VARCHAR(600)")
	private String vc_descripcion;
	
	@Column(name = "I_ESTATUS", columnDefinition = "INT")
	private int i_estatus;
	
	@Column(name = "I_USUARIO_REGISTRO", columnDefinition = "INT")
	private int i_usuario_registro;
	
	@Column(name = "I_USUARIO_MOD", columnDefinition = "INT")
	private int i_usuario_mod;
		
	@Column(name = "I_USUARIO_ELIMINO", columnDefinition = "INT")
	private int i_usuario_elimino;
	
	@Column(name = "I_FOLIO", columnDefinition = "INT")
	private int i_folio;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_CONFIRMACION")
	private LocalDate dt_fecha_confirmacion;
	
	@Column(name = "I_CONFIRMA", columnDefinition = "INT")
	private int i_confirma;
	
	
	@Column(name = "VC_DESCRIPCION_RECHAZO", columnDefinition = "VARCHAR(600)")
	private String vc_descripcion_rechazo;
	
	@Column(name = "VC_OBSERVACION_OFICIO", columnDefinition = "VARCHAR(50)")
	private String vc_observacion_oficio;

	public LocalDate getDt_fecha_confirmacion() {
		return dt_fecha_confirmacion;
	}

	public void setDt_fecha_confirmacion(LocalDate dt_fecha_confirmacion) {
		this.dt_fecha_confirmacion = dt_fecha_confirmacion;
	}

	public int getI_confirma() {
		return i_confirma;
	}

	public void setI_confirma(int i_confirma) {
		this.i_confirma = i_confirma;
	}

	public String getVc_descripcion_rechazo() {
		return vc_descripcion_rechazo;
	}

	public void setVc_descripcion_rechazo(String vc_descripcion_rechazo) {
		this.vc_descripcion_rechazo = vc_descripcion_rechazo;
	}

	public int getI_folio() {
		return i_folio;
	}

	public void setI_folio(int i_folio) {
		this.i_folio = i_folio;
	}

	public int getPk_id_observacion() {
		return pk_id_observacion;
	}

	public void setPk_id_observacion(int pk_id_observacion) {
		this.pk_id_observacion = pk_id_observacion;
	}

	//public int getFk_id_folio() {
		//return fk_id_folio;
	//}

	//public void setFk_id_folio(int fk_id_folio) {
		//this.fk_id_folio = fk_id_folio;
	//}

	public int getI_area() {
		return i_area;
	}

	public void setI_area(int i_area) {
		this.i_area = i_area;
	}

	public int getI_responsable() {
		return i_responsable;
	}

	public void setI_responsable(int i_responsable) {
		this.i_responsable = i_responsable;
	}

	public LocalDate getDt_fecha_vencimiento() {
		return dt_fecha_vencimiento;
	}

	public void setDt_fecha_vencimiento(LocalDate dt_fecha_vencimiento) {
		this.dt_fecha_vencimiento = dt_fecha_vencimiento;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public LocalDate getDt_fecha_asignacion() {
		return dt_fecha_asignacion;
	}

	public void setDt_fecha_asignacion(LocalDate dt_fecha_asignacion) {
		this.dt_fecha_asignacion = dt_fecha_asignacion;
	}

	public String getVc_descripcion() {
		return vc_descripcion;
	}

	public void setVc_descripcion(String vc_descripcion) {
		this.vc_descripcion = vc_descripcion;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public int getI_usuario_registro() {
		return i_usuario_registro;
	}

	public void setI_usuario_registro(int i_usuario_registro) {
		this.i_usuario_registro = i_usuario_registro;
	}

	public int getI_usuario_mod() {
		return i_usuario_mod;
	}

	public void setI_usuario_mod(int i_usuario_mod) {
		this.i_usuario_mod = i_usuario_mod;
	}

	public int getI_usuario_elimino() {
		return i_usuario_elimino;
	}

	public void setI_usuario_elimino(int i_usuario_elimino) {
		this.i_usuario_elimino = i_usuario_elimino;
	}

	public String getVc_observacion_oficio() {
		return vc_observacion_oficio;
	}

	public void setVc_observacion_oficio(String vc_observacion_oficio) {
		this.vc_observacion_oficio = vc_observacion_oficio;
	}

}
