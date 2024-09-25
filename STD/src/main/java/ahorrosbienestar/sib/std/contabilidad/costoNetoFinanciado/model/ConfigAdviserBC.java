package ahorrosbienestar.sib.std.contabilidad.costoNetoFinanciado.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CAT_CONF_CB_ADVISER", schema = "SIB")
public class ConfigAdviserBC {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID", nullable = false)
	private int id;

	@Column(name = "I_CONCEPTO", nullable = false)
	private int concepto;

	@Column(name = "VC_DESCRIPCION_CONCEPTO", length = 200)
	private String descripcionConcepto;

	@Column(name = "D_MONTO_INICIAL", columnDefinition = "decimal(12,2)")
	private Double montoInicial;

	@Column(name = "D_MONTO_FINAL", columnDefinition = "decimal(12,2)")
	private Double montoFinal;

	@Column(name = "I_PRODUCTOS", length = 200)
	private String productos;

	@Column(name = "VC_DESCRIPCION_PRODUCTO", length = 200)
	private String descripcionProducto;

	@Column(name = "I_ESTATUS", columnDefinition = "INT DEFAULT 0")
	private int estatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getConcepto() {
		return concepto;
	}

	public void setConcepto(int concepto) {
		this.concepto = concepto;
	}

	public String getDescripcionConcepto() {
		return descripcionConcepto;
	}

	public void setDescripcionConcepto(String descripcionConcepto) {
		this.descripcionConcepto = descripcionConcepto;
	}

	public Double getMontoInicial() {
		return montoInicial;
	}

	public void setMontoInicial(Double d) {
		this.montoInicial = d;
	}

	public Double getMontoFinal() {
		return montoFinal;
	}

	public void setMontoFinal(Double montoFinal) {
		this.montoFinal = montoFinal;
	}

	public String getProductos() {
		return productos;
	}

	public void setProductos(String productos) {
		this.productos = productos;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

}
