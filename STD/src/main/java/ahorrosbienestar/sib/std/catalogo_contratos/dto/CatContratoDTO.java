package ahorrosbienestar.sib.std.catalogo_contratos.dto;

import java.sql.Date;

public class CatContratoDTO {
	
	private int pk_id_contrato;
	private String vc_contrato;
	private String vc_descripcion;
	private Date dt_fecha_publicacion;
	private Date dt_fecha_modificaion;
	private String vc_tipo_formato;
	private String vc_ruta;
	private String vc_version;
	private String vc_nombre_archivo;
	private int i_id_estatus;
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
	public Date getDt_fecha_publicacion() {
		return dt_fecha_publicacion;
	}
	public void setDt_fecha_publicacion(Date dt_fecha_publicacion) {
		this.dt_fecha_publicacion = dt_fecha_publicacion;
	}
	public Date getDt_fecha_modificaion() {
		return dt_fecha_modificaion;
	}
	public void setDt_fecha_modificaion(Date dt_fecha_modificaion) {
		this.dt_fecha_modificaion = dt_fecha_modificaion;
	}
	public String getVc_tipo_formato() {
		return vc_tipo_formato;
	}
	public void setVc_tipo_formato(String vc_tipo_formato) {
		this.vc_tipo_formato = vc_tipo_formato;
	}
	public String getVc_ruta() {
		return vc_ruta;
	}
	public void setVc_ruta(String vc_ruta) {
		this.vc_ruta = vc_ruta;
	}
	public String getVc_version() {
		return vc_version;
	}
	public void setVc_version(String vc_version) {
		this.vc_version = vc_version;
	}
	public String getVc_nombre_archivo() {
		return vc_nombre_archivo;
	}
	public void setVc_nombre_archivo(String vc_nombre_archivo) {
		this.vc_nombre_archivo = vc_nombre_archivo;
	}
	public int getI_id_estatus() {
		return i_id_estatus;
	}
	public void setI_id_estatus(int i_id_estatus) {
		this.i_id_estatus = i_id_estatus;
	}
	
	

}
