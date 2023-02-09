package ahorrosbienestar.sib.std.docto_unificado.obtener_coordenadas.model;


public class ObtenerCoordenadas {

	private int pk_id_solicitud;
	private int pk_id_docto_envio;
	private String vc_ruta_docto;
	private int total_hojas;
	private int id_firma_docto;
	private float coordenada_x1;
	private float coordenada_x2;
	private float coordenada_y1;
	private float coordenada_y2;
	private int no_hoja;
	private float coordenada_xCentral;
	private float coordenada_yCentral;
	public int getPk_id_solicitud() {
		return pk_id_solicitud;
	}
	public void setPk_id_solicitud(int pk_id_solicitud) {
		this.pk_id_solicitud = pk_id_solicitud;
	}
	public int getPk_id_docto_envio() {
		return pk_id_docto_envio;
	}
	public void setPk_id_docto_envio(int pk_id_docto_envio) {
		this.pk_id_docto_envio = pk_id_docto_envio;
	}
	public String getVc_ruta_docto() {
		return vc_ruta_docto;
	}
	public void setVc_ruta_docto(String vc_ruta_docto) {
		this.vc_ruta_docto = vc_ruta_docto;
	}
	public int getTotal_hojas() {
		return total_hojas;
	}
	public void setTotal_hojas(int total_hojas) {
		this.total_hojas = total_hojas;
	}
	public int getId_firma_docto() {
		return id_firma_docto;
	}
	public void setId_firma_docto(int id_firma_docto) {
		this.id_firma_docto = id_firma_docto;
	}
	public float getCoordenada_x1() {
		return coordenada_x1;
	}
	public void setCoordenada_x1(float coordenada_x1) {
		this.coordenada_x1 = coordenada_x1;
	}
	public float getCoordenada_x2() {
		return coordenada_x2;
	}
	public void setCoordenada_x2(float coordenada_x2) {
		this.coordenada_x2 = coordenada_x2;
	}
	public float getCoordenada_y1() {
		return coordenada_y1;
	}
	public void setCoordenada_y1(float coordenada_y1) {
		this.coordenada_y1 = coordenada_y1;
	}
	public float getCoordenada_y2() {
		return coordenada_y2;
	}
	public void setCoordenada_y2(float coordenada_y2) {
		this.coordenada_y2 = coordenada_y2;
	}
	public int getNo_hoja() {
		return no_hoja;
	}
	public void setNo_hoja(int no_hoja) {
		this.no_hoja = no_hoja;
	}
	public float getCoordenada_xCentral() {
		return coordenada_xCentral;
	}
	public void setCoordenada_xCentral(float coordenada_xCentral) {
		this.coordenada_xCentral = coordenada_xCentral;
	}
	public float getCoordenada_yCentral() {
		return coordenada_yCentral;
	}
	public void setCoordenada_yCentral(float coordenada_yCentral) {
		this.coordenada_yCentral = coordenada_yCentral;
	}
	
	
}
