package ahorrosbienestar.sib.std.menuProducto.administracionSMS.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProcesoAsignacion {

	@Column(name = "Proceso")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int procesoID;
	
	@Column(name = "Base")
	private String tipoBase;

	public int getProcesoID() {
		return procesoID;
	}

	public void setProcesoID(int procesoID) {
		this.procesoID = procesoID;
	}

	public String getTipoBase() {
		return tipoBase;
	}

	public void setTipoBase(String tipoBase) {
		this.tipoBase = tipoBase;
	}

}
