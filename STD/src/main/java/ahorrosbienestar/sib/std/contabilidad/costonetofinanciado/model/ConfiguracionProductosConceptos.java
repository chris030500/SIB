package ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CAT_CONF_CONCEPTOS_PRORRATEOS", schema = "SIB")
public class ConfiguracionProductosConceptos {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID", nullable = false)
	private int id;
	
	@Column(name = "FK_ID_PRODUCTO", nullable = false)
	private int producto;
	
	@Column(name = "FK_ID_CONCEPTO", nullable = false)
	private int concepto;
	
	@Column(name = "I_ESTATUS", nullable = false)
	private int estatus=1;
	
	@Column(name = "I_USUARIO_REGISTRA")
	private Integer i_usuario_registra;

	@Column(name = "DT_FECHA_REGISTRO")
    private Date dt_fecha_registro;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProducto() {
		return producto;
	}

	public void setProducto(int producto) {
		this.producto = producto;
	}

	public int getConcepto() {
		return concepto;
	}

	public void setConcepto(int concepto) {
		this.concepto = concepto;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public Integer getI_usuario_registra() {
		return i_usuario_registra;
	}

	public void setI_usuario_registra(Integer i_usuario_registra) {
		this.i_usuario_registra = i_usuario_registra;
	}

	public Date getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(Date dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}
	
}
