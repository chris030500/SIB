package ahorrosbienestar.sib.std.correosib.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_MNS_CORREO")
public class EnvioCorreo {
	
	@Column(name="PK_ID_MNS_CORREO")
	@Id
	private int pk_id__msn_correo;
	
	@Column(name = "VC_NOM_PROPIEDAD", columnDefinition = "VARCHAR(100)")
	private String vc_nom_propiedad;
	
	@Column(name = "VC_MSN_CORREO", columnDefinition = "VARCHAR(700)")
	private String vc_msn_correo;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;
	
	@Column(name = "VC_HTML", columnDefinition = "LONGTEXT")
	private String vc_html;
	
	@Column(name = "VC_NOM_PROCESO", columnDefinition = "VARCHAR(100)")
	private String vc_nom_proceso;

	public int getPk_id__msn_correo() {
		return pk_id__msn_correo;
	}

	public void setPk_id__msn_correo(int pk_id__msn_correo) {
		this.pk_id__msn_correo = pk_id__msn_correo;
	}

	public String getVc_nom_propiedad() {
		return vc_nom_propiedad;
	}

	public void setVc_nom_propiedad(String vc_nom_propiedad) {
		this.vc_nom_propiedad = vc_nom_propiedad;
	}

	public String getVc_msn_correo() {
		return vc_msn_correo;
	}

	public void setVc_msn_correo(String vc_msn_correo) {
		this.vc_msn_correo = vc_msn_correo;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public String getVc_html() {
		return vc_html;
	}

	public void setVc_html(String vc_html) {
		this.vc_html = vc_html;
	}

	public String getVc_nom_proceso() {
		return vc_nom_proceso;
	}

	public void setVc_nom_proceso(String vc_nom_proceso) {
		this.vc_nom_proceso = vc_nom_proceso;
	}
	
	
}
