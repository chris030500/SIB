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
	private Integer pkIProducCreditoId;
	
	@Column(name = "VC_DESCRIPCION")
	private String vcDescripcion;

	public Integer getPkIProducCreditoId() {
		return pkIProducCreditoId;
	}

	public void setPkIProducCreditoId(Integer pkIProducCreditoId) {
		this.pkIProducCreditoId = pkIProducCreditoId;
	}

	public String getVcDescripcion() {
		return vcDescripcion;
	}

	public void setVcDescripcion(String vcDescripcion) {
		this.vcDescripcion = vcDescripcion;
	}

	@Override
	public String toString() {
		return "ProductoColocacion [pkIProducCreditoId=" + pkIProducCreditoId + ", vcDescripcion=" + vcDescripcion
				+ "]";
	}
}
