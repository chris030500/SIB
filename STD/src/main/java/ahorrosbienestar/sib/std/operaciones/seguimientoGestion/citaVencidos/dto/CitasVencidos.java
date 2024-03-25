package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.citaVencidos.dto;

public class CitasVencidos {

	private int i_pk_asignacion;
	private int i_cliente;
	private String vc_nombre_cliente;
	private String vc_campania;
	private String vc_clasificacion;
	private String vc_tipo_alerta;
	private String vc_estatus;
	private String vc_descripcion_cita;
	private int i_estatus;
	private int i_pk_id_seguimiento;
	public int getI_pk_asignacion() {
		return i_pk_asignacion;
	}
	public void setI_pk_asignacion(int i_pk_asignacion) {
		this.i_pk_asignacion = i_pk_asignacion;
	}
	public int getI_cliente() {
		return i_cliente;
	}
	public void setI_cliente(int i_cliente) {
		this.i_cliente = i_cliente;
	}
	public String getVc_nombre_cliente() {
		return vc_nombre_cliente;
	}
	public void setVc_nombre_cliente(String vc_nombre_cliente) {
		this.vc_nombre_cliente = vc_nombre_cliente;
	}
	public String getVc_campania() {
		return vc_campania;
	}
	public void setVc_campania(String vc_campania) {
		this.vc_campania = vc_campania;
	}
	public String getVc_clasificacion() {
		return vc_clasificacion;
	}
	public void setVc_clasificacion(String vc_clasificacion) {
		this.vc_clasificacion = vc_clasificacion;
	}
	public String getVc_tipo_alerta() {
		return vc_tipo_alerta;
	}
	public void setVc_tipo_alerta(String vc_tipo_alerta) {
		this.vc_tipo_alerta = vc_tipo_alerta;
	}
	public String getVc_estatus() {
		return vc_estatus;
	}
	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}
	public String getVc_descripcion_cita() {
		return vc_descripcion_cita;
	}
	public void setVc_descripcion_cita(String vc_descripcion_cita) {
		this.vc_descripcion_cita = vc_descripcion_cita;
	}
	public int getI_estatus() {
		return i_estatus;
	}
	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}
	public int getI_pk_id_seguimiento() {
		return i_pk_id_seguimiento;
	}
	public void setI_pk_id_seguimiento(int i_pk_id_seguimiento) {
		this.i_pk_id_seguimiento = i_pk_id_seguimiento;
	}
}
