package ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PRODUCTOS_CREDITO")
public class ProductoColocacion {
	@Id
	@Column(name = "PK_I_PRODUC_CREDITO_ID")
	private Integer pk_i_produc_credito_id;
	
	@Column(name = "VC_DESCRIPCION")
	private String vc_descripcion;

	public Integer getPk_i_produc_credito_id() {
		return pk_i_produc_credito_id;
	}

	public void setPk_i_produc_credito_id(Integer pk_i_produc_credito_id) {
		this.pk_i_produc_credito_id = pk_i_produc_credito_id;
	}

	public String getVc_descripcion() {
		return vc_descripcion;
	}

	public void setVc_descripcion(String vc_descripcion) {
		this.vc_descripcion = vc_descripcion;
	}

	@Override
	public String toString() {
		return "ProductosCredito [pk_i_produc_credito_id=" + pk_i_produc_credito_id + ", vc_descripcion="
				+ vc_descripcion + "]";
	}
}
