package ahorrosbienestar.sib.std.prevencion_pld.relacion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import ahorrosbienestar.sib.std.prevencion_pld.estatus.model.EstatusPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.listas.model.ListasPldModel;

@Entity
@Table(name="TBL_RELACION_LISTA_ESTATUS_QEQ")
public class RelacionPldModel {
	
	@Id
	@Column(name = "PK_ID_RELACION")
	private int pk_id_relacion;
	
	@ManyToOne
	@JoinColumn(name = "FK_LISTA")
	private ListasPldModel fk_lista;
	
	@ManyToOne
	@JoinColumn(name = "FK_ESTATUS")
	private EstatusPldModel fk_estatus;
	
	@Column(name = "I_ESTATUS")
	private int i_estatus;

	public int getPk_id_relacion() {
		return pk_id_relacion;
	}

	public void setPk_id_relacion(int pk_id_relacion) {
		this.pk_id_relacion = pk_id_relacion;
	}

	public ListasPldModel getFk_lista() {
		return fk_lista;
	}

	public void setFk_lista(ListasPldModel fk_lista) {
		this.fk_lista = fk_lista;
	}

	public EstatusPldModel getFk_estatus() {
		return fk_estatus;
	}

	public void setFk_estatus(EstatusPldModel fk_estatus) {
		this.fk_estatus = fk_estatus;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}
	
}
