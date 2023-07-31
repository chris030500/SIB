package ahorrosbienestar.sib.std.core_bancario.dto;

import java.util.List;

public class MenuxRol {

	private int pk_id_menu;
	private String vc_nombre_menu;
	private List<OpcionesxRol> opcMenu;
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
	public List<OpcionesxRol> getOpcMenu() {
		return opcMenu;
	}
	public void setOpcMenu(List<OpcionesxRol> opcMenu) {
		this.opcMenu = opcMenu;
	}

}
