package ahorrosbienestar.sib.std.catalogos.dto;

public class ObservacionesxFolio {
	private int pk_id_observacion;
	private String vc_area;
	private String vc_responsable;
	private String dt_fecha_asignacion;
	private String dt_fecha_vencimiento;
	private String vc_descripcion;
	private String vc_estatus;
	private String vc_confirma;
	private int i_confirma;
	private String vc_desc_rechazo;
	private int i_observacion_oficio;
	public int getPk_id_observacion() {
		return pk_id_observacion;
	}
	public void setPk_id_observacion(int pk_id_observacion) {
		this.pk_id_observacion = pk_id_observacion;
	}
	public String getVc_responsable() {
		return vc_responsable;
	}
	public void setVc_responsable(String vc_responsable) {
		this.vc_responsable = vc_responsable;
	}
	public String getDt_fecha_asignacion() {
		return dt_fecha_asignacion;
	}
	public void setDt_fecha_asignacion(String dt_fecha_asignacion) {
		this.dt_fecha_asignacion = dt_fecha_asignacion;
	}
	public String getDt_fecha_vencimiento() {
		return dt_fecha_vencimiento;
	}
	public void setDt_fecha_vencimiento(String dt_fecha_vencimiento) {
		this.dt_fecha_vencimiento = dt_fecha_vencimiento;
	}
	public String getVc_descripcion() {
		return vc_descripcion;
	}
	public void setVc_descripcion(String vc_descripcion) {
		this.vc_descripcion = vc_descripcion;
	}
	public String getVc_estatus() {
		return vc_estatus;
	}
	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}
	public String getVc_area() {
		return vc_area;
	}
	public void setVc_area(String vc_area) {
		this.vc_area = vc_area;
	}
	public String getVc_confirma() {
		return vc_confirma;
	}
	public void setVc_confirma(String vc_confirma) {
		this.vc_confirma = vc_confirma;
	}
	public int getI_confirma() {
		return i_confirma;
	}
	public void setI_confirma(int i_confirma) {
		this.i_confirma = i_confirma;
	}
	public String getVc_desc_rechazo() {
		return vc_desc_rechazo;
	}
	public void setVc_desc_rechazo(String vc_desc_rechazo) {
		this.vc_desc_rechazo = vc_desc_rechazo;
	}
	public int getI_observacion_oficio() {
		return i_observacion_oficio;
	}
	public void setI_observacion_oficio(int i_observacion_oficio) {
		this.i_observacion_oficio = i_observacion_oficio;
	}

}
