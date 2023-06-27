package ahorrosbienestar.sib.std.core_bancario.dto;

public class MenuSistema {
	private int pk_id_menu;
	private String vc_nombre_menu;
	private int pk_id_opcion;
	private String vc_nombre_pantalla;
	public int getPk_id_menu() {
		return pk_id_menu;
	}
	public void setPk_id_menu(int pk_id_menu) {
		this.pk_id_menu = pk_id_menu;
	}
	public String getVc_nombre_menu() {
		return vc_nombre_menu;
	}
	public void setVc_nombre_menu(String vc_nombre_menu) {
		this.vc_nombre_menu = vc_nombre_menu;
	}
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
	
	
}
