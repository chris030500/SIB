package ahorrosbienestar.sib.std.autorizacion.descargas.bitacora_zip.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;

@Entity
@Table(name = "TBL_BITACORA_ZIP")
public class BitacoraZipModel {

	@Id
	@Column(name = "PK_ID_BITACORA_ZIP")
	private int pk_id_bitacora_zip;
	
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@Column(name = "VC_RUTA_ZIP")
	private String vc_ruta_zip;
	
	@Column(name = "I_ESTATUS")
	private int i_estatus;
	
	@OneToOne
	@JoinColumn(name = "FK_SOLICITUD_ENVIO")
	private SolicitudEnvio fk_solicitud_envio;

	public int getPk_id_bitacora_zip() {
		return pk_id_bitacora_zip;
	}

	public void setPk_id_bitacora_zip(int pk_id_bitacora_zip) {
		this.pk_id_bitacora_zip = pk_id_bitacora_zip;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public String getVc_ruta_zip() {
		return vc_ruta_zip;
	}

	public void setVc_ruta_zip(String vc_ruta_zip) {
		this.vc_ruta_zip = vc_ruta_zip;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public SolicitudEnvio getFk_solicitud_envio() {
		return fk_solicitud_envio;
	}

	public void setFk_solicitud_envio(SolicitudEnvio fk_solicitud_envio) {
		this.fk_solicitud_envio = fk_solicitud_envio;
	}
	
}

