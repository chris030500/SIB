package ahorrosbienestar.sib.std.prevencion_pld.estatus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="TBL_CAT_ESTATUS_QEQ")
public class EstatusPldModel {
	
	@Id
	@Column(name = "PK_ID_ESTATUS")
	private int pk_id_estatus;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;
	
	@Column(name = "I_ESTATUS")
	private int i_estatus;

	public int getPk_id_estatus() {
		return pk_id_estatus;
	}

	public void setPk_id_estatus(int pk_id_estatus) {
		this.pk_id_estatus = pk_id_estatus;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}
	
}
