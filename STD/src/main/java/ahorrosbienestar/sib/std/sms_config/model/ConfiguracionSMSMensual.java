package ahorrosbienestar.sib.std.sms_config.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CONFIG_SMS_MENSUAL")
public class ConfiguracionSMSMensual {
	
	@Id
	@Column(name = "PK_ID_CONFIG_SMS_MENSUAL")
	private Integer pkConfigMensual;
	
	@Column(name = "FK_ID_CONFIG_SMS")
	private Integer fkConfigSMS;
	
	@Column(name = "VC_MENSAJE_ENVIAR")
	private String mensaje;
	
	@Column(name = "I_IND_LIM_ENVIO_SMS")
	private Integer limiteSMS;
	
	@Column(name = "I_NUM_ENVIOS_SMS")
	private Integer numSMS;
	
	@Column(name = "I_MES")
	private Integer mes;
	
	@Column(name = "I_ANIO")
	private Integer anio;
	
	@Column(name = "I_ACTIVO")
	private Integer activo;
	
	@Column(name = "I_USR_MODIFICA")
	private Integer userModifica;

	public Integer getPkConfigMensual() {
		return pkConfigMensual;
	}

	public void setPkConfigMensual(Integer pkConfigMensual) {
		this.pkConfigMensual = pkConfigMensual;
	}

	public Integer getFkConfigSMS() {
		return fkConfigSMS;
	}

	public void setFkConfigSMS(Integer fkConfigSMS) {
		this.fkConfigSMS = fkConfigSMS;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getLimiteSMS() {
		return limiteSMS;
	}

	public void setLimiteSMS(Integer limiteSMS) {
		this.limiteSMS = limiteSMS;
	}

	public Integer getNumSMS() {
		return numSMS;
	}

	public void setNumSMS(Integer numSMS) {
		this.numSMS = numSMS;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public Integer getUserModifica() {
		return userModifica;
	}

	public void setUserModifica(Integer userModifica) {
		this.userModifica = userModifica;
	}

	@Override
	public String toString() {
		return "ConfiguracionSMSMensual [pkConfigMensual=" + pkConfigMensual + ", fkConfigSMS=" + fkConfigSMS
				+ ", mensaje=" + mensaje + ", limiteSMS=" + limiteSMS + ", numSMS=" + numSMS + ", mes=" + mes
				+ ", anio=" + anio + ", activo=" + activo + ", userModifica=" + userModifica + "]";
	}
}
