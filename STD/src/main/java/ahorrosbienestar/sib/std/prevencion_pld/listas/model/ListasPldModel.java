package ahorrosbienestar.sib.std.prevencion_pld.listas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_CAT_LISTAS_QEQ")
public class ListasPldModel {

	@Id
	@Column(name = "PK_ID_LISTA")
	private int pk_id_lista;
	
	@Column(name = "VC_LISTA")
	private String vc_lista;
	
	@Column(name = "I_ESTATUS")
	private int i_estatus;

	public int getPk_id_lista() {
		return pk_id_lista;
	}

	public void setPk_id_lista(int pk_id_lista) {
		this.pk_id_lista = pk_id_lista;
	}

	public String getVc_lista() {
		return vc_lista;
	}

	public void setVc_lista(String vc_lista) {
		this.vc_lista = vc_lista;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}
	
}
