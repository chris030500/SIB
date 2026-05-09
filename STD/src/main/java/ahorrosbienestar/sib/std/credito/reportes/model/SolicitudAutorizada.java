package ahorrosbienestar.sib.std.credito.reportes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_AUTORIZADAS_PRODUCCION")
public class SolicitudAutorizada {
	
	@Id
	@Column(name = "SolicitudCreditoID")
	private Long idSolicitudCredito;
	
	@Column(name = "ClienteID")
	private Integer idCliente;
	
	@Column(name = "Estatus")
	private String estatus;
	
	@Column(name = "TipoCredito")
	private String tipoCredito;
	
	@Column(name = "SoloNombres")
	private String nombres;
	
	@Column(name = "TelefonoCelular")
	private String telCelular;

	public Long getIdSolicitudCredito() {
		return idSolicitudCredito;
	}

	public void setIdSolicitudCredito(Long idSolicitudCredito) {
		this.idSolicitudCredito = idSolicitudCredito;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelCelular() {
		return telCelular;
	}

	public void setTelCelular(String telCelular) {
		this.telCelular = telCelular;
	}

	@Override
	public String toString() {
		return "SolicitudesAutorizadas [idSolicitudCredito=" + idSolicitudCredito + ", idCliente=" + idCliente
				+ ", estatus=" + estatus + ", tipoCredito=" + tipoCredito + ", nombres=" + nombres + ", telCelular="
				+ telCelular + "]";
	}
}
