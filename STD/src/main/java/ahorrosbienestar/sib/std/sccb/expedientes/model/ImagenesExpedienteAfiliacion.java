package ahorrosbienestar.sib.std.sccb.expedientes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_SCCB_IMAGENES_EXP_AFILIACION")
public class ImagenesExpedienteAfiliacion {
	
	@Id
	@Column(name = "PK_I_ROWGUID")
	private Integer pk_i_rowguid;
	
	@Column(name = "I_ROW_GUID")
	private Integer i_row_guid;
	
	@Column(name = "I_EXPEDIENTE_ID")
	private Integer i_expediente_id;
	
	@Column(name = "VC_TIPO_REQUISITO")
	private String vc_tipo_requisito;
	
	@Column(name = "VC_TIPO_COMPROBANTE")
	private String vc_tipo_comprobante;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_DIGITALIZACION")
	private Date dt_fecha_digitalizacion;
	
	@Column(name = "VC_IMAGEN")
	private String vc_imagen;
	
	@Column(name = "VC_ESTATUS_ENTREGADO")
	private String vc_estatus_entregado;
	
	@Column(name = "VC_ESTATUS_DIGITALIZACION")
	private String vc_estatus_digitalizacion;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_VALIDO_DESDE")
	private Date dt_valido_desde;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_VALIDO_HASTA")
	private Date dt_valido_hasta;

	public Integer getPk_i_rowguid() {
		return pk_i_rowguid;
	}

	public void setPk_i_rowguid(Integer pk_i_rowguid) {
		this.pk_i_rowguid = pk_i_rowguid;
	}

	public Integer getI_row_guid() {
		return i_row_guid;
	}

	public void setI_row_guid(Integer i_row_guid) {
		this.i_row_guid = i_row_guid;
	}

	public Integer getI_expediente_id() {
		return i_expediente_id;
	}

	public void setI_expediente_id(Integer i_expediente_id) {
		this.i_expediente_id = i_expediente_id;
	}

	public String getVc_tipo_requisito() {
		return vc_tipo_requisito;
	}

	public void setVc_tipo_requisito(String vc_tipo_requisito) {
		this.vc_tipo_requisito = vc_tipo_requisito;
	}

	public String getVc_tipo_comprobante() {
		return vc_tipo_comprobante;
	}

	public void setVc_tipo_comprobante(String vc_tipo_comprobante) {
		this.vc_tipo_comprobante = vc_tipo_comprobante;
	}

	public Date getDt_fecha_digitalizacion() {
		return dt_fecha_digitalizacion;
	}

	public void setDt_fecha_digitalizacion(Date dt_fecha_digitalizacion) {
		this.dt_fecha_digitalizacion = dt_fecha_digitalizacion;
	}

	public String getVc_imagen() {
		return vc_imagen;
	}

	public void setVc_imagen(String vc_imagen) {
		this.vc_imagen = vc_imagen;
	}

	public String getVc_estatus_entregado() {
		return vc_estatus_entregado;
	}

	public void setVc_estatus_entregado(String vc_estatus_entregado) {
		this.vc_estatus_entregado = vc_estatus_entregado;
	}

	public String getVc_estatus_digitalizacion() {
		return vc_estatus_digitalizacion;
	}

	public void setVc_estatus_digitalizacion(String vc_estatus_digitalizacion) {
		this.vc_estatus_digitalizacion = vc_estatus_digitalizacion;
	}

	public Date getDt_valido_desde() {
		return dt_valido_desde;
	}

	public void setDt_valido_desde(Date dt_valido_desde) {
		this.dt_valido_desde = dt_valido_desde;
	}

	public Date getDt_valido_hasta() {
		return dt_valido_hasta;
	}

	public void setDt_valido_hasta(Date dt_valido_hasta) {
		this.dt_valido_hasta = dt_valido_hasta;
	}

	@Override
	public String toString() {
		return "ImagenesExpedienteAfiliacion [pk_i_rowguid=" + pk_i_rowguid + ", i_row_guid=" + i_row_guid
				+ ", i_expediente_id=" + i_expediente_id + ", vc_tipo_requisito=" + vc_tipo_requisito
				+ ", vc_tipo_comprobante=" + vc_tipo_comprobante + ", dt_fecha_digitalizacion="
				+ dt_fecha_digitalizacion + ", vc_imagen=" + vc_imagen + ", vc_estatus_entregado="
				+ vc_estatus_entregado + ", vc_estatus_digitalizacion=" + vc_estatus_digitalizacion
				+ ", dt_valido_desde=" + dt_valido_desde + ", dt_valido_hasta=" + dt_valido_hasta + "]";
	}
	
	

}
