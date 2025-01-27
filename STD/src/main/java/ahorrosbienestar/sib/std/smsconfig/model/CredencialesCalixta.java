package ahorrosbienestar.sib.std.smsconfig.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CREDENCIALES_CALIXTA")
public class CredencialesCalixta {
	 
	@Id
	@Column(name = "PK_I_CREDENCIAL")
	private Integer pkCredencial;
	
	@Column(name = "VC_DESC_CREDENCIAL")
	private String descripcionCredencial;
	
	@Column(name = "VC_VALOR_CREDENCIAL", columnDefinition = "LONGTEXT")
	private String valorCredencial;
	
	@Column(name = "I_TIPO_CREDENCIAL")
	private Integer tipoCredencial;
	
	@Column(name = "VC_DESC_TIPO_CREDENCIAL")
	private String descTipoCredencial;
	
	@Column(name = "VC_NOMBRE_CREDENCIAL")
	private String nombreCredencial;

	public Integer getPkCredencial() {
		return pkCredencial;
	}

	public void setPkCredencial(Integer pkCredencial) {
		this.pkCredencial = pkCredencial;
	}

	public String getDescripcionCredencial() {
		return descripcionCredencial;
	}

	public void setDescripcionCredencial(String descripcionCredencial) {
		this.descripcionCredencial = descripcionCredencial;
	}

	public String getValorCredencial() {
		return valorCredencial;
	}

	public void setValorCredencial(String valorCredencial) {
		this.valorCredencial = valorCredencial;
	}

	public Integer getTipoCredencial() {
		return tipoCredencial;
	}

	public void setTipoCredencial(Integer tipoCredencial) {
		this.tipoCredencial = tipoCredencial;
	}

	public String getDescTipoCredencial() {
		return descTipoCredencial;
	}

	public void setDescTipoCredencial(String descTipoCredencial) {
		this.descTipoCredencial = descTipoCredencial;
	}

	public String getNombreCredencial() {
		return nombreCredencial;
	}

	public void setNombreCredencial(String nombreCredencial) {
		this.nombreCredencial = nombreCredencial;
	}

	@Override
	public String toString() {
		return "CredencialesCalixta [pkCredencial=" + pkCredencial + ", descripcionCredencial=" + descripcionCredencial
				+ ", valorCredencial=" + valorCredencial + ", tipoCredencial=" + tipoCredencial
				+ ", descTipoCredencial=" + descTipoCredencial + ", nombreCredencial=" + nombreCredencial + "]";
	}
}
