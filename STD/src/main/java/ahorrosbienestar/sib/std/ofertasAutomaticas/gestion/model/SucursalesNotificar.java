package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SucursalesNotificar {
	
	@Id
	@Column(name = "FK_ID_SUCURSAL")
	private int fk_id_sucursal;

	public int getFk_id_sucursal() {
		return fk_id_sucursal;
	}

	public void setFk_id_sucursal(int fk_id_sucursal) {
		this.fk_id_sucursal = fk_id_sucursal;
	}
	
}
