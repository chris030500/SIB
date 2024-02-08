package ahorrosbienestar.sib.std.menuProducto.administracionSMS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_REG_PROCESOS_GESTION_OA")
public class ProcesoAsignacion {

	@Column(name = "PK_ID_PROCESO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int procesoID;

	public int getProcesoID() {
		return procesoID;
	}

	public void setProcesoID(int procesoID) {
		this.procesoID = procesoID;
	}

}
