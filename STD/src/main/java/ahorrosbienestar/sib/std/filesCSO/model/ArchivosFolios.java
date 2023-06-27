package ahorrosbienestar.sib.std.filesCSO.model;

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
@Table(name = "TBL_ARCHIVOS_FOLIOS")
public class ArchivosFolios {
	@Column(name = "PK_ID_ARCHIVO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="tbl_archivos_seq")
	@SequenceGenerator(name = "tbl_archivos_seq", sequenceName = "tbl_archivos_seq", initialValue = 1, allocationSize=1)
	private int pk_id_archivo;
	
	@Column(name = "I_FOLIO", columnDefinition = "INT")
	private int i_folio;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@Column(name = "VC_RUTA_DOCTO", columnDefinition = "VARCHAR(600)")
	private String vc_ruta_docto;
	
	@Column(name = "I_ESTATUS", columnDefinition = "INT")
	private int i_estatus;

	@Column(name = "VC_NOMBRE_DOCTO", columnDefinition = "VARCHAR(600)")
	private String vc_nombre_docto;
	
	public int getPk_id_archivo() {
		return pk_id_archivo;
	}

	public void setPk_id_archivo(int pk_id_archivo) {
		this.pk_id_archivo = pk_id_archivo;
	}

	public int getI_folio() {
		return i_folio;
	}

	public void setI_folio(int i_folio) {
		this.i_folio = i_folio;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public String getVc_ruta_docto() {
		return vc_ruta_docto;
	}

	public void setVc_ruta_docto(String vc_ruta_docto) {
		this.vc_ruta_docto = vc_ruta_docto;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public String getVc_nombre_docto() {
		return vc_nombre_docto;
	}

	public void setVc_nombre_docto(String vc_nombre_docto) {
		this.vc_nombre_docto = vc_nombre_docto;
	}
	
}
