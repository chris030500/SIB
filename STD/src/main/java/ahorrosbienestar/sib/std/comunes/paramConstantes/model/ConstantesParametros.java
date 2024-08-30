package ahorrosbienestar.sib.std.comunes.paramConstantes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_PARAMS_CONSTANTES")
public class ConstantesParametros {
	
	@Id
	@Column(name = "PK_CONSTANTE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_constante;
	
	@Column(name = "VC_CLAVE", columnDefinition = "VARCHAR(100)")
	private String vc_clave;
	
	@Column(name = "VC_VALOR", columnDefinition = "VARCHAR(100)")
	private String vc_valor;
	
	@Column(name = "VC_CONTROLLER", columnDefinition = "VARCHAR(100)")
	private String vc_controller;

	public int getPk_constante() {
		return pk_constante;
	}

	public void setPk_constante(int pk_constante) {
		this.pk_constante = pk_constante;
	}

	public String getVc_clave() {
		return vc_clave;
	}

	public void setVc_clave(String vc_clave) {
		this.vc_clave = vc_clave;
	}

	public String getVc_valor() {
		return vc_valor;
	}

	public void setVc_valor(String vc_valor) {
		this.vc_valor = vc_valor;
	}

	public String getVc_controller() {
		return vc_controller;
	}

	public void setVc_controller(String vc_controller) {
		this.vc_controller = vc_controller;
	}
	
	

}
