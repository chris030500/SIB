package ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PRODUCTOS_CONFIGURACION", schema = "SIB")
public class ProductosCreditoConf {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID", nullable = false)
	private int id;
	
	@Column(name = "VC_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "C_ESTATUS")
	private String estatus;
	
	@Column(name = "D_MONTO_INICIAL", columnDefinition = "decimal(12,2)")
	private Double montoInicial;

	@Column(name = "D_MONTO_FINAL", columnDefinition = "decimal(12,2)")
	private Double montoFinal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Double getMontoInicial() {
		return montoInicial;
	}

	public void setMontoInicial(Double montoInicial) {
		this.montoInicial = montoInicial;
	}

	public Double getMontoFinal() {
		return montoFinal;
	}

	public void setMontoFinal(Double montoFinal) {
		this.montoFinal = montoFinal;
	}

}
