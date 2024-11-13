package ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_TIPOS_CUENTAS")
public class ProductoCaptacion {
	
	@Id
	@Column(name = "PK_I_TIPO_CUENTA_ID")
	private Integer pk_i_tipo_cuenta_id;
	
	@Column(name = "VC_DESCRIPCION")
	private String vc_descripcion;

	public Integer getPk_i_tipo_cuenta_id() {
		return pk_i_tipo_cuenta_id;
	}

	public void setPk_i_tipo_cuenta_id(Integer pk_i_tipo_cuenta_id) {
		this.pk_i_tipo_cuenta_id = pk_i_tipo_cuenta_id;
	}

	public String getVc_descripcion() {
		return vc_descripcion;
	}

	public void setVc_descripcion(String vc_descripcion) {
		this.vc_descripcion = vc_descripcion;
	}

	@Override
	public String toString() {
		return "ProductosCaptacion [pk_i_tipo_cuenta_id=" + pk_i_tipo_cuenta_id + ", vc_descripcion=" + vc_descripcion
				+ "]";
	}
}
