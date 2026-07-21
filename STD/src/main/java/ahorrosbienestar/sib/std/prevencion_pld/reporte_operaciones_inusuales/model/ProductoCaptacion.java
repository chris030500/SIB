package ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_TIPOS_CUENTAS")
public class ProductoCaptacion {
	@Id
	@Column(name = "PK_I_TIPO_CUENTA_ID")
	private Integer pkITipoCuentaId;
	
	@Column(name = "VC_DESCRIPCION")
	private String vcDescripcion;

	public Integer getPkITipoCuentaId() {
		return pkITipoCuentaId;
	}

	public void setPkITipoCuentaId(Integer pkITipoCuentaId) {
		this.pkITipoCuentaId = pkITipoCuentaId;
	}

	public String getVcDescripcion() {
		return vcDescripcion;
	}

	public void setVcDescripcion(String vcDescripcion) {
		this.vcDescripcion = vcDescripcion;
	}

	@Override
	public String toString() {
		return "ProductoCaptacion [pkITipoCuentaId=" + pkITipoCuentaId + ", vcDescripcion=" + vcDescripcion + "]";
	}
	
}
