package ahorrosbienestar.sib.std.catalogo_contratos.dto;

import java.sql.Date;

public class CatContratoDTO {
	
	private int pk_id_contrato;
	private String vc_contrato;
	private String vc_descripcion;
	private int i_estatus;
	private Date dt_fecha_publicacion;
	private int usr_registra;
	private Date dt_fecha_modificacion;
	
	public Date getDt_fecha_modificacion() {
		return dt_fecha_modificacion;
	}
	public void setDt_fecha_modificacion(Date dt_fecha_modificacion) {
		this.dt_fecha_modificacion = dt_fecha_modificacion;
	}
	public int getPk_id_contrato() {
		return pk_id_contrato;
	}
	public void setPk_id_contrato(int pk_id_contrato) {
		this.pk_id_contrato = pk_id_contrato;
	}
	public String getVc_contrato() {
		return vc_contrato;
	}
	public void setVc_contrato(String vc_contrato) {
		this.vc_contrato = vc_contrato;
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
	public Date getDt_fecha_publicacion() {
		return dt_fecha_publicacion;
	}
	public void setDt_fecha_publicacion(Date dt_fecha_publicacion) {
		this.dt_fecha_publicacion = dt_fecha_publicacion;
	}
	public int getUsr_registra() {
		return usr_registra;
	}
	public void setUsr_registra(int usr_registra) {
		this.usr_registra = usr_registra;
	}	

}
