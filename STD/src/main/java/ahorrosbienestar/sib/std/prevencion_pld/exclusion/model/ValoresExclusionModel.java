package ahorrosbienestar.sib.std.prevencion_pld.exclusion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ValoresExclusionModel {
	
	@Id
	@Column(name = "PK_ID_EXCLUSION")
	private int pk_id_exclusion;
	
	@Column(name = "VC_LISTA")
	private String vc_lista;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;
	

	public int getPk_id_exclusion() {
		return pk_id_exclusion;
	}

	public void setPk_id_exclusion(int pk_id_exclusion) {
		this.pk_id_exclusion = pk_id_exclusion;
	}

	public String getVc_lista() {
		return vc_lista;
	}

	public void setVc_lista(String vc_lista) {
		this.vc_lista = vc_lista;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}
	
}
