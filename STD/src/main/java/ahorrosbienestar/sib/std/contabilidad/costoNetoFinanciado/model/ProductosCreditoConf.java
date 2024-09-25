package ahorrosbienestar.sib.std.contabilidad.costoNetoFinanciado.model;

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
	private String vc_descripcion;
	
	@Column(name = "C_ESTATUS")
	private String c_estatus;
	
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

	public String getVc_descripcion() {
		return vc_descripcion;
	}

	public void setVc_descripcion(String vc_descripcion) {
		this.vc_descripcion = vc_descripcion;
	}

	public String getC_estatus() {
		return c_estatus;
	}

	public void setC_estatus(String c_estatus) {
		this.c_estatus = c_estatus;
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
