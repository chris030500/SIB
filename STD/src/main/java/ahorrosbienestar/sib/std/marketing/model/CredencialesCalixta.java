package ahorrosbienestar.sib.std.marketing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CONFIGURACION_DESEMBOLSADOS_MARKETING")
public class CredencialesCalixta {
	
	@Id
	@Column(name = "PK_I_CONFIG")
	private Integer pkConfiguracion;
	
	@Column(name = "VC_DESC_CONFIG")
	private String descripcionConfiguracion;
	
	@Column(name = "VC_VALOR_CONFIG", columnDefinition = "LONGTEXT")
	private String valorConfiguracion;
	
	@Column(name = "I_TIPO_CONFIG")
	private Integer tipoConfig;
	
	@Column(name = "VC_DESC_TIPO_CONFIG")
	private String descTipoConfig;
	
	@Column(name = "VC_NOMBRE_CONFIG")
	private String nombreConfiguracion;

	public Integer getPkConfiguracion() {
		return pkConfiguracion;
	}

	public void setPkConfiguracion(Integer pkConfiguracion) {
		this.pkConfiguracion = pkConfiguracion;
	}

	public String getDescripcionConfiguracion() {
		return descripcionConfiguracion;
	}

	public void setDescripcionConfiguracion(String descripcionConfiguracion) {
		this.descripcionConfiguracion = descripcionConfiguracion;
	}

	public String getValorConfiguracion() {
		return valorConfiguracion;
	}

	public void setValorConfiguracion(String valorConfiguracion) {
		this.valorConfiguracion = valorConfiguracion;
	}

	public Integer getTipoConfig() {
		return tipoConfig;
	}

	public void setTipoConfig(Integer tipoConfig) {
		this.tipoConfig = tipoConfig;
	}

	public String getDescTipoConfig() {
		return descTipoConfig;
	}

	public void setDescTipoConfig(String descTipoConfig) {
		this.descTipoConfig = descTipoConfig;
	}

	public String getNombreConfiguracion() {
		return nombreConfiguracion;
	}

	public void setNombreConfiguracion(String nombreConfiguracion) {
		this.nombreConfiguracion = nombreConfiguracion;
	}

	@Override
	public String toString() {
		return "ConfiguracionCredenciales [pkConfiguracion=" + pkConfiguracion + ", descripcionConfiguracion="
				+ descripcionConfiguracion + ", valorConfiguracion=" + valorConfiguracion + ", tipoConfig=" + tipoConfig
				+ ", descTipoConfig=" + descTipoConfig + ", nombreConfiguracion=" + nombreConfiguracion + "]";
	}
}
