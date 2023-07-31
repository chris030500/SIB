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
@Table(name = "TBL_FOLIOS")
public class Folios {
	
	@Column(name = "PK_ID_FOLIO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="tbl_folio_seq")
	@SequenceGenerator(name = "tbl_folio_seq", sequenceName = "tbl_folio_seq", initialValue = 1, allocationSize=1)
	private int pk_id_folio;
	
	@Column(name = "VC_FOLIO", columnDefinition = "VARCHAR(100)")
	private String vc_folio;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_RECEPCION")
	private LocalDate dt_fecha_recepcion;
	
	@Column(name = "VC_ASUNTO", columnDefinition = "VARCHAR(400)")
	private String vc_asunto;
	
	@Column(name = "I_DIRIGIDO", columnDefinition = "INT")
	private int i_dirigido;
	
	@Column(name = "VC_PUESTO", columnDefinition = "VARCHAR(100)")
	private String vc_puesto;

	@Column(name = "I_TIPO_OFICIO", columnDefinition = "INT")
	private int i_tipo_oficio;
	
	@Column(name = "I_ORGANO_EMITE", columnDefinition = "INT")
	private int i_organo_emite;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@Column(name = "I_PLAZO_ATENCION", columnDefinition = "INT")
	private int i_plazo_atencion;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_VENCIMIENTO")
	private LocalDate dt_fecha_vencimiento;
	
	@Column(name = "I_ESTATUS", columnDefinition = "INT")
	private int i_estatus;
	
	@Column(name = "VC_RUTA_OFICIO", columnDefinition = "VARCHAR(400)")
	private String vc_ruta_oficio;

	public int getPk_id_folio() {
		return pk_id_folio;
	}

	public void setPk_id_folio(int pk_id_folio) {
		this.pk_id_folio = pk_id_folio;
	}


	public String getVc_folio() {
		return vc_folio;
	}

	public void setVc_folio(String vc_folio) {
		this.vc_folio = vc_folio;
	}

	public LocalDate getDt_fecha_recepcion() {
		return dt_fecha_recepcion;
	}

	public void setDt_fecha_recepcion(LocalDate dt_fecha_recepcion) {
		this.dt_fecha_recepcion = dt_fecha_recepcion;
	}

	public String getVc_asunto() {
		return vc_asunto;
	}

	public void setVc_asunto(String vc_asunto) {
		this.vc_asunto = vc_asunto;
	}

	public int getI_dirigido() {
		return i_dirigido;
	}

	public void setI_dirigido(int i_dirigido) {
		this.i_dirigido = i_dirigido;
	}

	public int getI_tipo_oficio() {
		return i_tipo_oficio;
	}

	public void setI_tipo_oficio(int i_tipo_oficio) {
		this.i_tipo_oficio = i_tipo_oficio;
	}

	public int getI_organo_emite() {
		return i_organo_emite;
	}

	public void setI_organo_emite(int i_organo_emite) {
		this.i_organo_emite = i_organo_emite;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public int getI_plazo_atencion() {
		return i_plazo_atencion;
	}

	public void setI_plazo_atencion(int i_plazo_atencion) {
		this.i_plazo_atencion = i_plazo_atencion;
	}

	public LocalDate getDt_fecha_vencimiento() {
		return dt_fecha_vencimiento;
	}

	public void setDt_fecha_vencimiento(LocalDate dt_fecha_vencimiento) {
		this.dt_fecha_vencimiento = dt_fecha_vencimiento;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public String getVc_ruta_oficio() {
		return vc_ruta_oficio;
	}

	public void setVc_ruta_oficio(String vc_ruta_oficio) {
		this.vc_ruta_oficio = vc_ruta_oficio;
	}
	
	public String getVc_puesto() {
		return vc_puesto;
	}

	public void setVc_puesto(String vc_puesto) {
		this.vc_puesto = vc_puesto;
	}
}
