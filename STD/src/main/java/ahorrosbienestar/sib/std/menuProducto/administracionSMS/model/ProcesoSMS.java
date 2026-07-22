package ahorrosbienestar.sib.std.menuProducto.administracionSMS.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PROCESO_SMS_OA")
public class ProcesoSMS {
	@Id
	@Column(name = "PK_ID_PROCES")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int procesoID;
	
    @Column(name = "I_ESTATUS")
    private int estatus;

	public int getProcesoID() {
		return procesoID;
	}

	public void setProcesoID(int procesoID) {
		this.procesoID = procesoID;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
    
    
}
