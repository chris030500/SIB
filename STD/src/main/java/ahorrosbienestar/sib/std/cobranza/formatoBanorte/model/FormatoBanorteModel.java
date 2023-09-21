package ahorrosbienestar.sib.std.cobranza.formatoBanorte.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_FORMATO_BANORTE_CNV")
public class FormatoBanorteModel {
	
	@Id
	@Column(name = "PK_ID_FORMATO_BANORTE")
	private int pk_id_formato_banorte;
	
	@Column(name = "VC_NOMBRE")
	private String vc_nombre;
	
	@Column(name = "VC_VALOR")
	private String vc_valor;
	
	@Column(name = "I_ESTATUS")
	private int i_estatus;

	public int getPk_id_formato_banorte() {
		return pk_id_formato_banorte;
	}

	public void setPk_id_formato_banorte(int pk_id_formato_banorte) {
		this.pk_id_formato_banorte = pk_id_formato_banorte;
	}

	public String getVc_nombre() {
		return vc_nombre;
	}

	public void setVc_nombre(String vc_nombre) {
		this.vc_nombre = vc_nombre;
	}

	public String getVc_valor() {
		return vc_valor;
	}

	public void setVc_valor(String vc_valor) {
		this.vc_valor = vc_valor;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

}
