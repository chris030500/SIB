package ahorrosbienestar.sib.std.sccb.consultaExpedientes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_SCCB_EXPEDIENTE_AFILIACION")
public class ExpedienteAfiliacion {
	
	@Id
	@Column(name = "PK_I_ROW_GUID")
	private Integer pk_i_row_guid;
	
	@Column(name = "I_EXPEDIENTE_ID")
	private Integer i_expediente_id;
	
	@Column(name = "I_TIPO_EXPEDIENTE_ID")
	private Integer i_tipo_expediente_id;
	
	@Column(name = "VC_TIPO_EXPEDIENTE")
	private String vc_tipo_expediente;
	
	@Column(name = "I_NO_CLIENTE")
	private Integer i_no_cliente;
	
	@Column(name = "VC_NOMBRE_CLIENTE")
	private String vc_nombre_cliente;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_CREACION")
	private Date dt_fecha_creacion;

	public Integer getPk_i_row_guid() {
		return pk_i_row_guid;
	}

	public void setPk_i_row_guid(Integer pk_i_row_guid) {
		this.pk_i_row_guid = pk_i_row_guid;
	}

	public Integer getI_expediente_id() {
		return i_expediente_id;
	}

	public void setI_expediente_id(Integer i_expediente_id) {
		this.i_expediente_id = i_expediente_id;
	}

	public Integer getI_tipo_expediente_id() {
		return i_tipo_expediente_id;
	}

	public void setI_tipo_expediente_id(Integer i_tipo_expediente_id) {
		this.i_tipo_expediente_id = i_tipo_expediente_id;
	}

	public String getVc_tipo_expediente() {
		return vc_tipo_expediente;
	}

	public void setVc_tipo_expediente(String vc_tipo_expediente) {
		this.vc_tipo_expediente = vc_tipo_expediente;
	}

	public Integer getI_no_cliente() {
		return i_no_cliente;
	}

	public void setI_no_cliente(Integer i_no_cliente) {
		this.i_no_cliente = i_no_cliente;
	}

	public String getVc_nombre_cliente() {
		return vc_nombre_cliente;
	}

	public void setVc_nombre_cliente(String vc_nombre_cliente) {
		this.vc_nombre_cliente = vc_nombre_cliente;
	}

	public Date getDt_fecha_creacion() {
		return dt_fecha_creacion;
	}

	public void setDt_fecha_creacion(Date dt_fecha_creacion) {
		this.dt_fecha_creacion = dt_fecha_creacion;
	}

	@Override
	public String toString() {
		return "ExpedienteAfiliacion [pk_i_row_guid=" + pk_i_row_guid + ", i_expediente_id=" + i_expediente_id
				+ ", i_tipo_expediente_id=" + i_tipo_expediente_id + ", vc_tipo_expediente=" + vc_tipo_expediente
				+ ", i_no_cliente=" + i_no_cliente + ", vc_nombre_cliente=" + vc_nombre_cliente + ", dt_fecha_creacion="
				+ dt_fecha_creacion + "]";
	}
}
