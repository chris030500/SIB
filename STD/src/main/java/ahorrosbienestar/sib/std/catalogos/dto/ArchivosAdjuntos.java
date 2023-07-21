package ahorrosbienestar.sib.std.catalogos.dto;

public class ArchivosAdjuntos {

	private int pk_id_archivo;
	private String vc_nombre_docto;
	private String vc_nombre_adjunto;
	public int getPk_id_archivo() {
		return pk_id_archivo;
	}
	public void setPk_id_archivo(int pk_id_archivo) {
		this.pk_id_archivo = pk_id_archivo;
	}
	public String getVc_nombre_docto() {
		return vc_nombre_docto;
	}
	public void setVc_nombre_docto(String vc_nombre_docto) {
		this.vc_nombre_docto = vc_nombre_docto;
	}
	public String getVc_nombre_adjunto() {
		return vc_nombre_adjunto;
	}
	public void setVc_nombre_adjunto(String vc_nombre_adjunto) {
		this.vc_nombre_adjunto = vc_nombre_adjunto;
	}
	
	
}
