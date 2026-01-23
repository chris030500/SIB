package ahorrosbienestar.sib.std.sucursales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_SUCURSALES")
public class SucursalesModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_SUCURSAL")
	private int pk_id_sucursal;

	@Column(name = "VC_NOMBRESUCURSAL")
	private String vc_nombresucursal;

	@Column(name = "VC_TIPOSUCURSAL")
	private String vc_tiposucursal;

	@Column(name = "I_REGIONID")
	private int i_regionid;

	@Column(name = "VC_DIRECCIONCOMPLETA")
	private String vc_direccioncompleta;

	@Column(name = "I_CENTROCOSTOID")
	private int i_centrocostoid;

	@Column(name = "VC_TELEFONOFIJO")
	private String vc_telefonofijo;

	@Column(name = "VC_TELEFONO")
	private String vc_telefono;

	@Column(name = "VC_NUMEMPGERENTE")
	private String vc_numempgerente;

	@Column(name = "VC_NUMEMPSUBGERENTE")
	private String vc_numempsubgerente;

	@Column(name = "VC_ESTATUS")
	private String vc_estatus;

	public int getPk_id_sucursal() {
		return pk_id_sucursal;
	}

	public void setPk_id_sucursal(int pk_id_sucursal) {
		this.pk_id_sucursal = pk_id_sucursal;
	}

	public String getVc_nombresucursal() {
		return vc_nombresucursal;
	}

	public void setVc_nombresucursal(String vc_nombresucursal) {
		this.vc_nombresucursal = vc_nombresucursal;
	}

	public String getVc_tiposucursal() {
		return vc_tiposucursal;
	}

	public void setVc_tiposucursal(String vc_tiposucursal) {
		this.vc_tiposucursal = vc_tiposucursal;
	}

	public int getI_regionid() {
		return i_regionid;
	}

	public void setI_regionid(int i_regionid) {
		this.i_regionid = i_regionid;
	}

	public String getVc_direccioncompleta() {
		return vc_direccioncompleta;
	}

	public void setVc_direccioncompleta(String vc_direccioncompleta) {
		this.vc_direccioncompleta = vc_direccioncompleta;
	}

	public int getI_centrocostoid() {
		return i_centrocostoid;
	}

	public void setI_centrocostoid(int i_centrocostoid) {
		this.i_centrocostoid = i_centrocostoid;
	}

	public String getVc_telefonofijo() {
		return vc_telefonofijo;
	}

	public void setVc_telefonofijo(String vc_telefonofijo) {
		this.vc_telefonofijo = vc_telefonofijo;
	}

	public String getVc_telefono() {
		return vc_telefono;
	}

	public void setVc_telefono(String vc_telefono) {
		this.vc_telefono = vc_telefono;
	}

	public String getVc_numempgerente() {
		return vc_numempgerente;
	}

	public void setVc_numempgerente(String vc_numempgerente) {
		this.vc_numempgerente = vc_numempgerente;
	}

	public String getVc_numempsubgerente() {
		return vc_numempsubgerente;
	}

	public void setVc_numempsubgerente(String vc_numempsubgerente) {
		this.vc_numempsubgerente = vc_numempsubgerente;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}

}

