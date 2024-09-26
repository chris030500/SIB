package ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductosCredito {
	@Id
	@Column(name = "I_PRODUCTO_CREDITOID")
	private int i_producto_creditoid; 
	
	@Column(name = "VC_DESCRIPCION_PRODUCTO")
	private String vc_descripcion_producto;
	
	@Column(name = "C_ESTATUS")
	private String c_estatus;

	public int getI_producto_creditoid() {
		return i_producto_creditoid;
	}

	public void setI_producto_creditoid(int i_producto_creditoid) {
		this.i_producto_creditoid = i_producto_creditoid;
	}

	public String getVc_descripcion_producto() {
		return vc_descripcion_producto;
	}

	public void setVc_descripcion_producto(String vc_descripcion_producto) {
		this.vc_descripcion_producto = vc_descripcion_producto;
	}

	public String getC_estatus() {
		return c_estatus;
	}

	public void setC_estatus(String c_estatus) {
		this.c_estatus = c_estatus;
	}
	
}
