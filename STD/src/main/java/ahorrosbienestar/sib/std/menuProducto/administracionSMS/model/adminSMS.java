package ahorrosbienestar.sib.std.menuProducto.administracionSMS.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "VW_ASIGNACION_OFERTAS")
public class adminSMS {
	
	@Id
    @Column(name = "I_CLIENTEID")
    private int clienteID;

    @Column(name = "VC_NOMBRECOMPLETO")
    private String nombreCompleto;

    @Column(name = "VC_NOMBRE_BASE")
    private String nombreBase;
    
    @Column(name = "VC_ABREVIACION_CLASIFICACION")
    private String nombreClasificacion;

    @Column(name = "VC_TELEFONO_CELULAR")
    private String telefonoCelular;
    
    @Column(name = "VC_DESC_MENSAJE")
    private String descMensaje;

    @Column(name = "i_estatus")
    private String estMensaje;

	@Override
	public String toString() {
		return "adminSMS [clienteID=" + clienteID + ", nombreCompleto=" + nombreCompleto + ", nombreBase=" + nombreBase
				+ ", nombreClasificacion=" + nombreClasificacion + ", telefonoCelular=" + telefonoCelular
				+ ", descMensaje=" + descMensaje + ", estMensaje=" + estMensaje + ", getClienteID()=" + getClienteID()
				+ ", getNombreCompleto()=" + getNombreCompleto() + ", getNombreBase()=" + getNombreBase()
				+ ", getNombreClasificacion()=" + getNombreClasificacion() + ", getTelefonoCelular()="
				+ getTelefonoCelular() + ", getDescMensaje()=" + getDescMensaje() + ", getEstMensaje()="
				+ getEstMensaje() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public int getClienteID() {
		return clienteID;
	}

	public void setClienteID(int clienteID) {
		this.clienteID = clienteID;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getNombreBase() {
		return nombreBase;
	}

	public void setNombreBase(String nombreBase) {
		this.nombreBase = nombreBase;
	}

	public String getNombreClasificacion() {
		return nombreClasificacion;
	}

	public void setNombreClasificacion(String nombreClasificacion) {
		this.nombreClasificacion = nombreClasificacion;
	}

	public String getTelefonoCelular() {
		return telefonoCelular;
	}

	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}

	public String getDescMensaje() {
		return descMensaje;
	}

	public void setDescMensaje(String descMensaje) {
		this.descMensaje = descMensaje;
	}

	public String getEstMensaje() {
		return estMensaje;
	}

	public void setEstMensaje(String estMensaje) {
		this.estMensaje = estMensaje;
	}
    
}
