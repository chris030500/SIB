package ahorrosbienestar.sib.std.smsconfig.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "TBL_PROCESO_ENVIO_SMS")
public class GestionSMS {
	
	@Id
	@Column(name = "PK_PROCESOID")
	private Integer pkIdProceso;
	
	@Column(name = "FK_ID_CONFIG_SMS")
	private Integer idConfigSMS;
	
	@Column(name = "I_TP_PROCESO")
	private Integer tipoProceso;
	
	@Column(name = "DT_FECHA_INICIAL")
	private Date fechaInicial;
	
	@Column(name = "DT_FECHA_FINAL")
	private Date fechaFinal;
	
	@Column(name = "I_SMS_ENVIAR")
	private Integer numSmsEnviar;
	
	@Column(name = "I_SMS_ENVIOS")
	private Integer numSmsEnvios;
	
	@Column(name = "I_SMS_PEND_ENVIAR")
	private Integer numSmsPendientes;
	
	@Column(name = "VC_VALIDAR_ENVIO_PREVIO")
	private String esEnvioPrevio;
	
	@Column(name = "I_ESTATUS_PROCESO")
	private Integer estatusProceso;
	
	@Column(name = "I_USR_REGISTRA")
	private Integer userRegistra;
	
	@Column(name = "I_USR_CANCELA")
	private Integer userCancela;
	
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDateTime fechaRegistro;
	
	@Column(name = "DT_FECHA_CANCELACION")
	private LocalDateTime fechaCancelacion;

	public Integer getPkIdProceso() {
		return pkIdProceso;
	}

	public void setPkIdProceso(Integer pkIdProceso) {
		this.pkIdProceso = pkIdProceso;
	}

	public Integer getIdConfigSMS() {
		return idConfigSMS;
	}

	public void setIdConfigSMS(Integer idConfigSMS) {
		this.idConfigSMS = idConfigSMS;
	}

	public Integer getTipoProceso() {
		return tipoProceso;
	}

	public void setTipoProceso(Integer tipoProceso) {
		this.tipoProceso = tipoProceso;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Integer getNumSmsEnviar() {
		return numSmsEnviar;
	}

	public void setNumSmsEnviar(Integer numSmsEnviar) {
		this.numSmsEnviar = numSmsEnviar;
	}

	public Integer getNumSmsEnvios() {
		return numSmsEnvios;
	}

	public void setNumSmsEnvios(Integer numSmsEnvios) {
		this.numSmsEnvios = numSmsEnvios;
	}

	public Integer getNumSmsPendientes() {
		return numSmsPendientes;
	}

	public void setNumSmsPendientes(Integer numSmsPendientes) {
		this.numSmsPendientes = numSmsPendientes;
	}

	public String getEsEnvioPrevio() {
		return esEnvioPrevio;
	}

	public void setEsEnvioPrevio(String esEnvioPrevio) {
		this.esEnvioPrevio = esEnvioPrevio;
	}

	public Integer getEstatusProceso() {
		return estatusProceso;
	}

	public void setEstatusProceso(Integer estatusProceso) {
		this.estatusProceso = estatusProceso;
	}

	public Integer getUserRegistra() {
		return userRegistra;
	}

	public void setUserRegistra(Integer userRegistra) {
		this.userRegistra = userRegistra;
	}

	public Integer getUserCancela() {
		return userCancela;
	}

	public void setUserCancela(Integer userCancela) {
		this.userCancela = userCancela;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public LocalDateTime getFechaCancelacion() {
		return fechaCancelacion;
	}

	public void setFechaCancelacion(LocalDateTime fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	@Override
	public String toString() {
		return "GestionSMS [pkIdProceso=" + pkIdProceso + ", idConfigSMS=" + idConfigSMS + ", tipoProceso="
				+ tipoProceso + ", fechaInicial=" + fechaInicial + ", fechaFinal=" + fechaFinal + ", numSmsEnviar="
				+ numSmsEnviar + ", numSmsEnvios=" + numSmsEnvios + ", numSmsPendientes=" + numSmsPendientes
				+ ", esEnvioPrevio=" + esEnvioPrevio + ", estatusProceso=" + estatusProceso + ", userRegistra="
				+ userRegistra + ", userCancela=" + userCancela + ", fechaRegistro=" + fechaRegistro
				+ ", fechaCancelacion=" + fechaCancelacion + "]";
	}
}
