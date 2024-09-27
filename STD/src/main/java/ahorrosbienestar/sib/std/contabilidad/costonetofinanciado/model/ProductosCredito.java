package ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductosCredito {
	@Id
	@Column(name = "PRODUCTOCREDITOID")
	private int productocreditoid; 
	
	@Column(name = "DESCRIPCIONPRODUCTO")
	private String descripcionproducto;
	
	@Column(name = "ESTATUS")
	private String estatus;

	public int getProductocreditoid() {
		return productocreditoid;
	}

	public void setProductocreditoid(int productocreditoid) {
		this.productocreditoid = productocreditoid;
	}

	public String getDescripcionproducto() {
		return descripcionproducto;
	}

	public void setDescripcionproducto(String descripcionproducto) {
		this.descripcionproducto = descripcionproducto;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

}
