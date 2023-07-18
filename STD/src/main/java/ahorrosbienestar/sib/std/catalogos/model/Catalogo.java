package ahorrosbienestar.sib.std.catalogos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TBL_OPC_BUSQUEDA")
public class Catalogo {
	@Column(name = "PK_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="busqueda_seq")
	@SequenceGenerator(name = "busqueda_seq", sequenceName = "busqueda_seq", initialValue = 1, allocationSize=1)
	private int pk_id;
	
	@Column(name = "I_ID_GRUPO")
	private int i_id_grupo;
	
	@Column(name="I_OPCION")
	private int i_opcion;
	
	@Column(name="V_VALOR")
	private String v_valor;
	
	@Column(name="I_ESTATUS")
	private int i_estatus;
	
	@Column(name="V_PANTALLA")
	private String v_pantalla;

	public int getPk_id() {
		return pk_id;
	}

	public void setPk_id(int pk_id) {
		this.pk_id = pk_id;
	}

	public int getI_id_grupo() {
		return i_id_grupo;
	}

	public void setI_id_grupo(int i_id_grupo) {
		this.i_id_grupo = i_id_grupo;
	}

	public int getI_opcion() {
		return i_opcion;
	}

	public void setI_opcion(int i_opcion) {
		this.i_opcion = i_opcion;
	}

	public String getV_valor() {
		return v_valor;
	}

	public void setV_valor(String v_valor) {
		this.v_valor = v_valor;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public String getV_pantalla() {
		return v_pantalla;
	}

	public void setV_pantalla(String v_pantalla) {
		this.v_pantalla = v_pantalla;
	}
}
