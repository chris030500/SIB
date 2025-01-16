package ahorrosbienestar.sib.std.marketing.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_CONFIG_SMS")
public class ConfiguracionSMS {
	
	@Id
	@Column(name = "PK_ID_CONFIG_SMS")
	private Integer idConfig;
	
	@Column(name = "FK_VC_TP_CREDITO")
	private String tipoCredito;
	
	@Column(name = "FK_VC_ESTATUS_SOLCRE")
	private String estatusSolCred;
	
	@Column(name = "I_TP_PROCESO")
	private Integer tipoProceso;
	
	@Column(name = "I_IND_ALL_TP_CREDITO")
	private Integer allTipoCredito;
	
	@Column(name = "I_IND_SOLCRE_CAMPANIA")
	private Integer solCredCampania;
	
	@Column(name = "VC_MENSAJE_ENVIAR")
	private String mensaje;
	
	@Column(name = "I_IND_LIM_ENVIO_SMS")
	private Integer limiteEnvio;
	
	@Column(name = "FK_FRECUENCIA_ENVIO_SMS")
	private Integer frecuenciaEnvio;
	
	@Column(name = "I_NUM_ENVIOS_SMS")
	private Integer numEnvios;
	
	@Column(name = "I_USR_REGISTRA")
	private Integer userRegistra;
	
	@Column(name = "I_IND_ACTIVO")
	private Integer activo;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDateTime fechaRegistro;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "DT_FECHA_MODIFICACION")
	private LocalDateTime fechaModificacion;
	
	@Column(name = "I_USR_MODIFICA")
	private Integer userModifica;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "DT_FECHA_BAJA")
	private LocalDateTime fechaBaja;

	public Integer getIdConfig() {
		return idConfig;
	}

	public void setIdConfig(Integer idConfig) {
		this.idConfig = idConfig;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public String getEstatusSolCred() {
		return estatusSolCred;
	}

	public void setEstatusSolCred(String estatusSolCred) {
		this.estatusSolCred = estatusSolCred;
	}

	public Integer getTipoProceso() {
		return tipoProceso;
	}

	public void setTipoProceso(Integer tipoProceso) {
		this.tipoProceso = tipoProceso;
	}

	public Integer getAllTipoCredito() {
		return allTipoCredito;
	}

	public void setAllTipoCredito(Integer allTipoCredito) {
		this.allTipoCredito = allTipoCredito;
	}

	public Integer getSolCredCampania() {
		return solCredCampania;
	}

	public void setSolCredCampania(Integer solCredCampania) {
		this.solCredCampania = solCredCampania;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getLimiteEnvio() {
		return limiteEnvio;
	}

	public void setLimiteEnvio(Integer limiteEnvio) {
		this.limiteEnvio = limiteEnvio;
	}

	public Integer getFrecuenciaEnvio() {
		return frecuenciaEnvio;
	}

	public void setFrecuenciaEnvio(Integer frecuenciaEnvio) {
		this.frecuenciaEnvio = frecuenciaEnvio;
	}

	public Integer getNumEnvios() {
		return numEnvios;
	}

	public void setNumEnvios(Integer numEnvios) {
		this.numEnvios = numEnvios;
	}

	public Integer getUserRegistra() {
		return userRegistra;
	}

	public void setUserRegistra(Integer userRegistra) {
		this.userRegistra = userRegistra;
	}

	public Integer getActivo() {
		return activo;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public Integer getUserModifica() {
		return userModifica;
	}

	public void setUserModifica(Integer userModifica) {
		this.userModifica = userModifica;
	}

	public LocalDateTime getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(LocalDateTime fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	@Override
	public String toString() {
		return "ConfiguracionSMS [idConfig=" + idConfig + ", tipoCredito=" + tipoCredito + ", estatusSolCred="
				+ estatusSolCred + ", tipoProceso=" + tipoProceso + ", allTipoCredito=" + allTipoCredito
				+ ", solCredCampania=" + solCredCampania + ", mensaje=" + mensaje + ", limiteEnvio=" + limiteEnvio
				+ ", frecuenciaEnvio=" + frecuenciaEnvio + ", numEnvios=" + numEnvios + ", userRegistra=" + userRegistra
				+ ", activo=" + activo + ", fechaRegistro=" + fechaRegistro + ", fechaModificacion=" + fechaModificacion
				+ ", userModifica=" + userModifica + ", fechaBaja=" + fechaBaja + "]";
	}
}
