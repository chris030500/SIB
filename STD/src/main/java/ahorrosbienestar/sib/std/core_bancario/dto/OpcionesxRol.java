package ahorrosbienestar.sib.std.core_bancario.dto;

public class OpcionesxRol {

	private int pk_id_opcion;
	private String vc_nombre_pantalla;
	private String vc_controller;
	private int i_nivel;
	public int getPk_id_opcion() {
		return pk_id_opcion;
	}
	public void setPk_id_opcion(int pk_id_opcion) {
		this.pk_id_opcion = pk_id_opcion;
	}
	public String getVc_nombre_pantalla() {
		return vc_nombre_pantalla;
	}
	public void setVc_nombre_pantalla(String vc_nombre_pantalla) {
		this.vc_nombre_pantalla = vc_nombre_pantalla;
	}
	public String getVc_controller() {
		return vc_controller;
	}
	public void setVc_controller(String vc_controller) {
		this.vc_controller = vc_controller;
	}
	public int getI_nivel() {
		return i_nivel;
	}
	public void setI_nivel(int i_nivel) {
		this.i_nivel = i_nivel;
	}
	
}
