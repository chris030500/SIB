package ahorrosbienestar.sib.std.reportesJasper.model;

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
@Table(name="TBL_CAT_REPORTES_JASPER")
public class ReporteJasper {

	@Column(name="PK_ID_REPORTE")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="reporteJasper_seq")
	@SequenceGenerator(name = "reporteJasper_seq", sequenceName = "reporteJasper_seq", initialValue = 1, allocationSize=1)
	private int pk_id_reporte;

	@Column(name = "VC_URL_REPORTE", columnDefinition = "VARCHAR(100)")
	private String vc_url_reporte;
	
	@Column(name = "VC_TIPO_CONTENIDO", columnDefinition = "VARCHAR(100)")
	private String vc_tipo_contenido;
	
	@Column(name = "VC_NOMBRE_REPORTE", columnDefinition = "VARCHAR(100)")
	private String vc_nombre_reporte;
	
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_CANCELACION")
	private LocalDate dt_fecha_cancelacion;

	public LocalDate getDt_fecha_cancelacion() {
		return dt_fecha_cancelacion;
	}

	public void setDt_fecha_cancelacion(LocalDate dt_fecha_cancelacion) {
		this.dt_fecha_cancelacion = dt_fecha_cancelacion;
	}

	public int getPk_id_reporte() {
		return pk_id_reporte;
	}

	public void setPk_id_reporte(int pk_id_reporte) {
		this.pk_id_reporte = pk_id_reporte;
	}

	public String getVc_url_reporte() {
		return vc_url_reporte;
	}

	public void setVc_url_reporte(String vc_url_reporte) {
		this.vc_url_reporte = vc_url_reporte;
	}

	public String getVc_tipo_contenido() {
		return vc_tipo_contenido;
	}

	public void setVc_tipo_contenido(String vc_tipo_contenido) {
		this.vc_tipo_contenido = vc_tipo_contenido;
	}

	public String getVc_nombre_reporte() {
		return vc_nombre_reporte;
	}

	public void setVc_nombre_reporte(String vc_nombre_reporte) {
		this.vc_nombre_reporte = vc_nombre_reporte;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	
}
