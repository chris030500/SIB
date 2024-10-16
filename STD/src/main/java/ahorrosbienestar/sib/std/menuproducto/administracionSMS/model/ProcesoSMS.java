package ahorrosbienestar.sib.std.menuproducto.administracionSMS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
