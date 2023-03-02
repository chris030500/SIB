package ahorrosbienestar.sib.std.documentos.model;

import java.time.LocalDate;
import java.util.Date;


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

import ahorrosbienestar.sib.std.roles.model.Rol;


@Entity
@Table(name = "TBL_CAT_DOCUMENTO")
public class Documento {
	
	@Column(name = "PK_ID_DOCUMENTO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="documento_seq")
	@SequenceGenerator(name = "documento_seq", sequenceName = "documento_seq", initialValue = 1, allocationSize=1)
	private int pk_id_documento;
	
	@Column(name = "VC_NOMBRE_DOC", columnDefinition = "VARCHAR(300)")
	private String vc_nombre_doc;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;
	
	@Column(name = "IND_DOC_CLIENTE", columnDefinition = "TINYINT(1)")
	private int ind_doc_cliente;
	
	@Column(name = "USR_REGISTRA", columnDefinition = "INT")
	private int usr_registra;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_ALTA")
	private LocalDate dt_fecha_alta;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_BAJA")
	private LocalDate dt_fecha_baja;

	public int getPk_id_documento() {
		return pk_id_documento;
	}

	public void setPk_id_documento(int pk_id_documento) {
		this.pk_id_documento = pk_id_documento;
	}

	public String getVc_nombre_doc() {
		return vc_nombre_doc;
	}

	public void setVc_nombre_doc(String vc_nombre_doc) {
		this.vc_nombre_doc = vc_nombre_doc;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public int getInd_doc_cliente() {
		return ind_doc_cliente;
	}

	public void setInd_doc_cliente(int ind_doc_cliente) {
		this.ind_doc_cliente = ind_doc_cliente;
	}

	public int getUsr_registra() {
		return usr_registra;
	}

	public void setUsr_registra(int usr_registra) {
		this.usr_registra = usr_registra;
	}

	public LocalDate getDt_fecha_alta() {
		return dt_fecha_alta;
	}

	public void setDt_fecha_alta(LocalDate dt_fecha_alta) {
		this.dt_fecha_alta = dt_fecha_alta;
	}

	public LocalDate getDt_fecha_baja() {
		return dt_fecha_baja;
	}

	public void setDt_fecha_baja(LocalDate dt_fecha_baja) {
		this.dt_fecha_baja = dt_fecha_baja;
	}
	
	


	
	
}
