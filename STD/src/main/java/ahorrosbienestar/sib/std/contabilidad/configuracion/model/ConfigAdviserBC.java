package ahorrosbienestar.sib.std.contabilidad.configuracion.model;

import java.math.BigDecimal;

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
	@Column(name = "PK_ID_CB_ADVISER", nullable = false)
	private int id;

	@Column(name = "I_CONCEPTO", nullable = false)
	private int concepto;

	@Column(name = "VC_DESCRIPCION_CONCEPTO", length = 200)
	private String descripcionConcepto;

	@Column(name = "D_MONTO_INICIAL", precision = 12, scale = 2,columnDefinition = "decimal(12,2) default 0.0")
	private BigDecimal montoInicial;

	@Column(name = "D_MONTO_FINAL", precision = 12, scale = 2,columnDefinition = "decimal(12,2) default 0.0")
	private BigDecimal montoFinal;

	@Column(name = "I_PRODUCTOS", length = 200)
	private String productos;

	@Column(name = "VC_DESCRIPCION_PRODUCTO", length = 200)
	private String descripcionProducto;

	@Column(name = "I_ESTATUS", nullable = false, columnDefinition = "int default 1")
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

	public BigDecimal getMontoInicial() {
		return montoInicial;
	}

	public void setMontoInicial(BigDecimal d) {
		this.montoInicial = d;
	}

	public BigDecimal getMontoFinal() {
		return montoFinal;
	}

	public void setMontoFinal(BigDecimal montoFinal) {
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
