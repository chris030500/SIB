package ahorrosbienestar.sib.std.config_ws.ws_configuraciones.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_WS_CONFIGURACIONES")
public class WSConfiguracion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID")
	private Integer pkId;

	@Column(name = "VC_DESCRIPCION")
	private String vcDescripcion;

	@Column(name = "VC_RUTA")
	private String vcRuta;

	@Column(name = "I_ESTATUS")
	private Integer iEstatus;

	@Column(name = "VC_PASS")
	private String vcPass;

	@Column(name = "VC_HEADER")
	private String vcHeader;

	public Integer getPkId() {
		return pkId;
	}

	public void setPkId(Integer pkId) {
		this.pkId = pkId;
	}

	public String getVcDescripcion() {
		return vcDescripcion;
	}

	public void setVcDescripcion(String vcDescripcion) {
		this.vcDescripcion = vcDescripcion;
	}

	public String getVcRuta() {
		return vcRuta;
	}

	public void setVcRuta(String vcRuta) {
		this.vcRuta = vcRuta;
	}

	public Integer getIEstatus() {
		return iEstatus;
	}

	public void setIEstatus(Integer iEstatus) {
		this.iEstatus = iEstatus;
	}

	public String getVcPass() {
		return vcPass;
	}

	public void setVcPass(String vcPass) {
		this.vcPass = vcPass;
	}

	public String getVcHeader() {
		return vcHeader;
	}

	public void setVcHeader(String vcHeader) {
		this.vcHeader = vcHeader;
	}
}
