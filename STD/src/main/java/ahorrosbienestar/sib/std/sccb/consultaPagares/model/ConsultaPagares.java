package ahorrosbienestar.sib.std.sccb.consultaPagares.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_SCCB_PAGARES")
public class ConsultaPagares {
	
	@Id
	@Column(name = "PK_I_ROW_GUID")
	private Integer pk_i_row_guid;
	
	@Column(name = "I_CONTROL_ID")
	private Integer i_control_id;
	
	@Column(name = "VC_TIPO_DOCUMENTO")
	private String vc_tipo_documento;
	
	@Column(name = "VC_NOMBRE_CENTRO_UBICACION")
	private String vc_nombre_centro_ubicacion;
	
	@Column(name = "I_NO_CLIENTE")
	private Integer i_no_cliente;
	
	@Column(name = "I_CENTRO_ID")
	private Integer i_centro_id;
	
	@Column(name = "I_CREDITO_ID")
	private Integer i_credito_id;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "FL_IMPORTE_CREDITO")
	private Float fl_importe_credito;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_INICIO_CREDITO")
	private Date dt_fecha_inicio_credito;
	
	@Column(name = "VC_ESTATUS_CREDITO")
	private String vc_estatus_credito;
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "DT_FECHA_EVENTO")
	private Date dt_fecha_evento;
	
	@Column(name = "VC_NOMBRE_SOLICITANTE")
	private String vc_nombre_solicitante;
	
	@Column(name = "VC_NOMBRE_CENTRO_SOLICITANTE")
	private String vc_nombre_centro_solicitante;
	
	@Column(name = "VC_NOMBRE_CENTRO_ENTREGA")
	private String vc_nombre_centro_entrega;
	
	@Column(name = "VC_NOMBRE_CENTRO_DESTINO")
	private String vc_nombre_centro_destino;
	
	@Column(name = "VC_ETAPA_CUSTODIA")
	private String vc_etapa_custodia;
	
	@Column(name = "VC_TIPO_DEVOLUCION")
	private String vc_tipo_devolucion;
	
	@Column(name = "I_NO_CLIENTE_SAFI")
	private Integer i_no_cliente_safi;
	
	@Column(name = "I_CREDITO_ID_SAFI")
	private Long i_credito_id_safi;
	
	@Column(name = "VC_NOMBRE_ASIGNADO")
	private String vc_nombre_asignado;
	
	@Column(name = "VC_NOMBRE_DESTINO")
	private String vc_nombre_destino;

	public Integer getPk_i_row_guid() {
		return pk_i_row_guid;
	}

	public void setPk_i_row_guid(Integer pk_i_row_guid) {
		this.pk_i_row_guid = pk_i_row_guid;
	}

	public Integer getI_control_id() {
		return i_control_id;
	}

	public void setI_control_id(Integer i_control_id) {
		this.i_control_id = i_control_id;
	}

	public String getVc_tipo_documento() {
		return vc_tipo_documento;
	}

	public void setVc_tipo_documento(String vc_tipo_documento) {
		this.vc_tipo_documento = vc_tipo_documento;
	}

	public String getVc_nombre_centro_ubicacion() {
		return vc_nombre_centro_ubicacion;
	}

	public void setVc_nombre_centro_ubicacion(String vc_nombre_centro_ubicacion) {
		this.vc_nombre_centro_ubicacion = vc_nombre_centro_ubicacion;
	}

	public Integer getI_no_cliente() {
		return i_no_cliente;
	}

	public void setI_no_cliente(Integer i_no_cliente) {
		this.i_no_cliente = i_no_cliente;
	}

	public Integer getI_centro_id() {
		return i_centro_id;
	}

	public void setI_centro_id(Integer i_centro_id) {
		this.i_centro_id = i_centro_id;
	}
	

	public Integer getI_credito_id() {
		return i_credito_id;
	}

	public void setI_credito_id(Integer i_credito_id) {
		this.i_credito_id = i_credito_id;
	}

	public Float getFl_importe_credito() {
		return fl_importe_credito;
	}

	public void setFl_importe_credito(Float fl_importe_credito) {
		this.fl_importe_credito = fl_importe_credito;
	}

	public Date getDt_fecha_inicio_credito() {
		return dt_fecha_inicio_credito;
	}

	public void setDt_fecha_inicio_credito(Date dt_fecha_inicio_credito) {
		this.dt_fecha_inicio_credito = dt_fecha_inicio_credito;
	}

	public String getVc_estatus_credito() {
		return vc_estatus_credito;
	}

	public void setVc_estatus_credito(String vc_estatus_credito) {
		this.vc_estatus_credito = vc_estatus_credito;
	}

	public Date getDt_fecha_evento() {
		return dt_fecha_evento;
	}

	public void setDt_fecha_evento(Date dt_fecha_evento) {
		this.dt_fecha_evento = dt_fecha_evento;
	}

	public String getVc_nombre_solicitante() {
		return vc_nombre_solicitante;
	}

	public void setVc_nombre_solicitante(String vc_nombre_solicitante) {
		this.vc_nombre_solicitante = vc_nombre_solicitante;
	}

	public String getVc_nombre_centro_solicitante() {
		return vc_nombre_centro_solicitante;
	}

	public void setVc_nombre_centro_solicitante(String vc_nombre_centro_solicitante) {
		this.vc_nombre_centro_solicitante = vc_nombre_centro_solicitante;
	}

	public String getVc_nombre_centro_entrega() {
		return vc_nombre_centro_entrega;
	}

	public void setVc_nombre_centro_entrega(String vc_nombre_centro_entrega) {
		this.vc_nombre_centro_entrega = vc_nombre_centro_entrega;
	}

	public String getVc_nombre_centro_destino() {
		return vc_nombre_centro_destino;
	}

	public void setVc_nombre_centro_destino(String vc_nombre_centro_destino) {
		this.vc_nombre_centro_destino = vc_nombre_centro_destino;
	}

	public String getVc_etapa_custodia() {
		return vc_etapa_custodia;
	}

	public void setVc_etapa_custodia(String vc_etapa_custodia) {
		this.vc_etapa_custodia = vc_etapa_custodia;
	}

	public String getVc_tipo_devolucion() {
		return vc_tipo_devolucion;
	}

	public void setVc_tipo_devolucion(String vc_tipo_devolucion) {
		this.vc_tipo_devolucion = vc_tipo_devolucion;
	}

	public Integer getI_no_cliente_safi() {
		return i_no_cliente_safi;
	}

	public void setI_no_cliente_safi(Integer i_no_cliente_safi) {
		this.i_no_cliente_safi = i_no_cliente_safi;
	}

	public Long getI_credito_id_safi() {
		return i_credito_id_safi;
	}

	public void setI_credito_id_safi(Long i_credito_id_safi) {
		this.i_credito_id_safi = i_credito_id_safi;
	}

	public String getVc_nombre_asignado() {
		return vc_nombre_asignado;
	}

	public void setVc_nombre_asignado(String vc_nombre_asignado) {
		this.vc_nombre_asignado = vc_nombre_asignado;
	}

	public String getVc_nombre_destino() {
		return vc_nombre_destino;
	}

	public void setVc_nombre_destino(String vc_nombre_destino) {
		this.vc_nombre_destino = vc_nombre_destino;
	}

	@Override
	public String toString() {
		return "ConsultaPagares [pk_i_row_guid=" + pk_i_row_guid + ", i_control_id=" + i_control_id
				+ ", vc_tipo_documento=" + vc_tipo_documento + ", vc_nombre_centro_ubicacion="
				+ vc_nombre_centro_ubicacion + ", i_no_cliente=" + i_no_cliente + ", i_centro_id=" + i_centro_id
				+ ", i_credito_id=" + i_credito_id + ", fl_importe_credito=" + fl_importe_credito
				+ ", dt_fecha_inicio_credito=" + dt_fecha_inicio_credito + ", vc_estatus_credito=" + vc_estatus_credito
				+ ", dt_fecha_evento=" + dt_fecha_evento + ", vc_nombre_solicitante=" + vc_nombre_solicitante
				+ ", vc_nombre_centro_solicitante=" + vc_nombre_centro_solicitante + ", vc_nombre_centro_entrega="
				+ vc_nombre_centro_entrega + ", vc_nombre_centro_destino=" + vc_nombre_centro_destino
				+ ", vc_etapa_custodia=" + vc_etapa_custodia + ", vc_tipo_devolucion=" + vc_tipo_devolucion
				+ ", i_no_cliente_safi=" + i_no_cliente_safi + ", i_credito_id_safi=" + i_credito_id_safi
				+ ", vc_nombre_asignado=" + vc_nombre_asignado + ", vc_nombre_destino=" + vc_nombre_destino + "]";
	}
	
}

