package ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CLIENTES_ACTIVOS", indexes = {@Index(name = "idx_nombrecompleto", columnList = "VC_NombreCompleto")})
public class ClientesActivosModel {
 
	@Id
	@Column(name = "I_ClienteID")
	private int i_clienteid;
 
	@Column(name = "VC_NombreCompleto")
	private String vc_nombrecompleto;
 
	@Column(name = "C_Estatus")
	private String c_estatus;
 
	@Column(name = "i_estadoid")
	private int I_EstadoID;
 
	@Column(name = "vc_direccioncompleta")
	private String VC_DireccionCompleta;
 
	public int getI_clienteid() {
		return i_clienteid;
	}
 
	public void setI_clienteid(int i_clienteid) {
		this.i_clienteid = i_clienteid;
	}
 
	public String getVc_nombrecompleto() {
		return vc_nombrecompleto;
	}
 
	public void setVc_nombrecompleto(String vc_nombrecompleto) {
		this.vc_nombrecompleto = vc_nombrecompleto;
	}
}
