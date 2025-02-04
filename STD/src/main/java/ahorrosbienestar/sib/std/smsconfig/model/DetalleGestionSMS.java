package ahorrosbienestar.sib.std.smsconfig.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_DET_PROCESO_ENVIO_SMS")
public class DetalleGestionSMS {
	
	@Id
	@Column(name = "PK_DETALLEID")
	private Integer idDetalle;
	
	@Column(name = "FK_PROCESOID")
	private Integer idProceso;
	
	@Column(name = "I_SOLICITUDCREDITOID")
	private Integer idSolicitudCredito;
	
	@Column(name = "VC_NOMBRECLIENTE")
	private String nombreCliente;
	
	@Column(name = "I_TELCELULAR")
	private Integer telCelular;
	
	@Column(name = "VC_ESTATUS_SOLCRE")
	private String estatusSolicitud;
	
	@Column(name = "VC_TIPOCREDITO")
	private String tipoCredito;
	
	@Column(name = "VC_ESCAMPANIA")
	private String isCampania;
	
	@Column(name = "VC_SMS_ENVIAR")
	private String smsEnviar;
	
	@Column(name = "VC_ENVIAR_MSN")
	private String enviarSMS;
	
	@Column(name = "I_ESTATUS_ENVIO")
	private Integer estatusEnvio;
	
	@Column(name = "DT_FECHA_ENVIO")
	private LocalDateTime fechaEnvio;
	
	@Column(name = "DT_FECHA_RESPUESTA")
	private LocalDateTime fechaRespuesta;

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	public Integer getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Integer idProceso) {
		this.idProceso = idProceso;
	}

	public Integer getIdSolicitudCredito() {
		return idSolicitudCredito;
	}

	public void setIdSolicitudCredito(Integer idSolicitudCredito) {
		this.idSolicitudCredito = idSolicitudCredito;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public Integer getTelCelular() {
		return telCelular;
	}

	public void setTelCelular(Integer telCelular) {
		this.telCelular = telCelular;
	}

	public String getEstatusSolicitud() {
		return estatusSolicitud;
	}

	public void setEstatusSolicitud(String estatusSolicitud) {
		this.estatusSolicitud = estatusSolicitud;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public String getIsCampania() {
		return isCampania;
	}

	public void setIsCampania(String isCampania) {
		this.isCampania = isCampania;
	}

	public String getSmsEnviar() {
		return smsEnviar;
	}

	public void setSmsEnviar(String smsEnviar) {
		this.smsEnviar = smsEnviar;
	}

	public String getEnviarSMS() {
		return enviarSMS;
	}

	public void setEnviarSMS(String enviarSMS) {
		this.enviarSMS = enviarSMS;
	}

	public Integer getEstatusEnvio() {
		return estatusEnvio;
	}

	public void setEstatusEnvio(Integer estatusEnvio) {
		this.estatusEnvio = estatusEnvio;
	}

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public LocalDateTime getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(LocalDateTime fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	@Override
	public String toString() {
		return "DetalleGestionSMS [idDetalle=" + idDetalle + ", idProceso=" + idProceso + ", idSolicitudCredito="
				+ idSolicitudCredito + ", nombreCliente=" + nombreCliente + ", telCelular=" + telCelular
				+ ", estatusSolicitud=" + estatusSolicitud + ", tipoCredito=" + tipoCredito + ", isCampania="
				+ isCampania + ", smsEnviar=" + smsEnviar + ", enviarSMS=" + enviarSMS + ", estatusEnvio="
				+ estatusEnvio + ", fechaEnvio=" + fechaEnvio + ", fechaRespuesta=" + fechaRespuesta + "]";
	}
}
