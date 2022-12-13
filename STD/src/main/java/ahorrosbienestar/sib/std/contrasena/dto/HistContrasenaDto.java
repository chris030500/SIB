package ahorrosbienestar.sib.std.contrasena.dto;

public class HistContrasenaDto {
	public int pk_id_usuario;
	public String vc_nombre_usuario;
	public String vc_contrasenia;
	public int valorFecha;
	public int getPk_id_usuario() {
		return pk_id_usuario;
	}
	public void setPk_id_usuario(int pk_id_usuario) {
		this.pk_id_usuario = pk_id_usuario;
	}
	public String getVc_nombre_usuario() {
		return vc_nombre_usuario;
	}
	public void setVc_nombre_usuario(String vc_nombre_usuario) {
		this.vc_nombre_usuario = vc_nombre_usuario;
	}
	public String getVc_contrasenia() {
		return vc_contrasenia;
	}
	public void setVc_contrasenia(String vc_contrasenia) {
		this.vc_contrasenia = vc_contrasenia;
	}
	public int getValorFecha() {
		return valorFecha;
	}
	public void setValorFecha(int valorFecha) {
		this.valorFecha = valorFecha;
	}
	
	

}
