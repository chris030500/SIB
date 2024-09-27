package ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class DetalleFacturaMod {
	
	@Id
	@Column(name = "ID")
	private int id;
	
	@Column(name = "FOLIO")
	private int folio;
		
	@Column(name = "CONCEPTO")
	private String concepto;
	
	@Column(name = "CANTIDAD")
	private int cantidad; 
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	@Column(name = "PRECIOUNITARIO")
	private Double preciounitario;
	
	@Column(name = "TOTAL")
	private Double total;
	
	@Column(name = "FECHAEMISION")
    private String fechaemision;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPreciounitario() {
		return preciounitario;
	}

	public void setPreciounitario(Double preciounitario) {
		this.preciounitario = preciounitario;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getFechaemision() {
		return fechaemision;
	}

	public void setFechaemision(String fechaemision) {
		this.fechaemision = fechaemision;
	}



}
