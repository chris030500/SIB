package ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
public class DetalleFacturaMod {
	
	@Id
	@Column(name = "PK_ID")
	private int i_id;
	
	@Column(name = "FK_Folio")
	private int i_folio;
		
	@Column(name = "VC_Concepto")
	private String vc_concepto;
	
	@Column(name = "DC_Cantidad")
	private int i_cantidad; 
	
	@Column(name = "VC_Descripcion")
	private String vc_descripcion;
	
	@Column(name = "DC_Precio_Unit")
	private Double d_precio_unitario;
	
	@Column(name = "DC_Total")
	private Double d_total;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_EMISION", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_emision;
	

	public LocalDate getDt_fecha_emision() {
		return dt_fecha_emision;
	}

	public void setDt_fecha_emision(LocalDate dt_fecha_emision) {
		this.dt_fecha_emision = dt_fecha_emision;
	}

	public int getI_folio() {
		return i_folio;
	}

	public void setI_folio(int i_folio) {
		this.i_folio = i_folio;
	}

	public String getVc_concepto() {
		return vc_concepto;
	}

	public void setVc_concepto(String vc_concepto) {
		this.vc_concepto = vc_concepto;
	}

	public int getI_cantidad() {
		return i_cantidad;
	}

	public void setI_cantidad(int i_cantidad) {
		this.i_cantidad = i_cantidad;
	}

	public String getVc_descripcion() {
		return vc_descripcion;
	}

	public void setVc_descripcion(String vc_descripcion) {
		this.vc_descripcion = vc_descripcion;
	}

	public Double getD_precio_unitario() {
		return d_precio_unitario;
	}

	public void setD_precio_unitario(Double d_precio_unitario) {
		this.d_precio_unitario = d_precio_unitario;
	}

	public Double getD_total() {
		return d_total;
	}

	public void setD_total(Double d_total) {
		this.d_total = d_total;
	}

	public int getI_id() {
		return i_id;
	}

	public void setI_id(int i_id) {
		this.i_id = i_id;
	}

}
