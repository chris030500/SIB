package ahorrosbienestar.sib.std.marketing.creditosdesembolsados.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_CONFIG_CREDITOS_DESEMBOLSADOS")
public class ConfigCreditosDesembolsados {
	
	@Id
	@Column(name = "PK_I_CONFIG")
	private Integer pkConfig;
	
	@Column(name = "VC_DESC_CONFIG")
	private String descConfig;
	
	@Column(name = "VC_VALOR_CONFIG")
	private String valorConfig;
	
	@Column(name = "I_TIPO_CONFIG")
	private Integer tipoConfig;
	
	@Column(name = "VC_DESC_TIPO_CONFIG")
	private String descTipoConfig;
	
	@Column(name = "VC_NOMBRE_CONFIG")
	private String nombreConfig;

	public Integer getPkConfig() {
		return pkConfig;
	}

	public void setPkConfig(Integer pkConfig) {
		this.pkConfig = pkConfig;
	}

	public String getDescConfig() {
		return descConfig;
	}

	public void setDescConfig(String descConfig) {
		this.descConfig = descConfig;
	}

	public String getValorConfig() {
		return valorConfig;
	}

	public void setValorConfig(String valorConfig) {
		this.valorConfig = valorConfig;
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

	public String getNombreConfig() {
		return nombreConfig;
	}

	public void setNombreConfig(String nombreConfig) {
		this.nombreConfig = nombreConfig;
	}

	@Override
	public String toString() {
		return "ConfigCreditosDesembolsados [pkConfig=" + pkConfig + ", descConfig=" + descConfig + ", valorConfig="
				+ valorConfig + ", tipoConfig=" + tipoConfig + ", descTipoConfig=" + descTipoConfig + ", nombreConfig="
				+ nombreConfig + "]";
	}
}
