package ahorrosbienestar.sib.std.cobranza.banco.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_BANCO_CNV")
public class BancoModel {

	@Id
	@Column(name = "PK_ID_BANCO")
	private int pk_id_banco;
	
	@Column(name = "VC_NOMBRE")
	private String vc_nombre;
	
	@Column(name = "VC_RAZON_SOCIAL")
	private String vc_razon_social;
	
	@Column(name = "I_ESTATUS")
	private int i_estatus;

	public int getPk_id_banco() {
		return pk_id_banco;
	}

	public void setPk_id_banco(int pk_id_banco) {
		this.pk_id_banco = pk_id_banco;
	}

	public String getVc_nombre() {
		return vc_nombre;
	}

	public void setVc_nombre(String vc_nombre) {
		this.vc_nombre = vc_nombre;
	}

	public String getVc_razon_social() {
		return vc_razon_social;
	}

	public void setVc_razon_social(String vc_razon_social) {
		this.vc_razon_social = vc_razon_social;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}
	
}
