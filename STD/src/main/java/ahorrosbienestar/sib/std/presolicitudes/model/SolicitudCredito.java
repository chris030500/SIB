package ahorrosbienestar.sib.std.presolicitudes.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_SOLICITUDES_CREDITO", schema = "SIB")
public class SolicitudCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID_SOLICITUD")
    private Integer id;

    @Column(name = "VC_EMPRESA")
    private String empresa;

    @Column(name = "VC_EMPLEADO")
    private String empleado;

    @Column(name = "VC_PRODUCTO")
    private String producto;

    @Column(name = "VC_NUM_EMPLEADO")
    private String numEmpleado;

    @Column(name = "I_PUESTO")
    private Integer puesto;

    @Column(name = "I_PLAZO")
    private Integer plazo;

    @Column(name = "VC_FRCUENCIA") // Posible typo: ¿"I_FRECUENCIA"?
    private String frecuencia;

    @Column(name = "DC_MONTO_SOLICITADO")
    private Double montoSolicitado;

    @Column(name = "DC_MONTO_DESCONTAR")
    private Double montoDescontar;

    @Column(name = "DT_FECHA_SOLICITUD")
    private LocalDate fechaSolicitud;
    
    @Column(name = "I_EJECUTIVO")
    private Integer ejecutivo;
    
    @Column(name = "I_SUCURSAL")
    private Integer sucursal;
    
    @Column(name = "I_ESTATUS")
    private Integer estatus;
    
    @Column(name = "FK_ID_PROCESO")
    private Integer proceso;
    
    @Column(name = "VC_RENOVADO")
    private String renovacion;
    
    @Column(name = "VC_COMENTARIOS")
    private String comentarios;
    
    @Column(name = "DT_FECHA_APROBACION")
    private LocalDate fechaAprobacion;
    
	@OneToOne
	@JoinColumn(name = "I_USUARIO_REG", updatable = true)
	private Usuario usuarioReg;
    
    @Override
	public String toString() {
		return "SolicitudCredito [id=" + id + ", empresa=" + empresa + ", empleado=" + empleado + ", producto="
				+ producto + ", numEmpleado=" + numEmpleado + ", puesto=" + puesto + ", plazo=" + plazo
				+ ", frecuencia=" + frecuencia + ", montoSolicitado=" + montoSolicitado + ", montoDescontar="
				+ montoDescontar + ", fechaSolicitud=" + fechaSolicitud + ", ejecutivo=" + ejecutivo + ", sucursal="
				+ sucursal + ", estatus=" + estatus + ", proceso=" + proceso +", fechaAprobacion=" + fechaAprobacion +", usuarioReg=" + usuarioReg + "]";
	}

	// Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public Integer getPuesto() {
        return puesto;
    }

    public void setPuesto(Integer puesto) {
        this.puesto = puesto;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Double getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(Double montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public Double getMontoDescontar() {
        return montoDescontar;
    }

    public void setMontoDescontar(Double montoDescontar) {
        this.montoDescontar = montoDescontar;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

	public Integer getEjecutivo() {
		return ejecutivo;
	}

	public void setEjecutivo(Integer ejecutivo) {
		this.ejecutivo = ejecutivo;
	}

	public Integer getSucursal() {
		return sucursal;
	}

	public void setSucursal(Integer sucursal) {
		this.sucursal = sucursal;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Integer getProceso() {
		return proceso;
	}

	public void setProceso(Integer proceso) {
		this.proceso = proceso;
	}

	public String getRenovacion() {
		return renovacion;
	}

	public void setRenovacion(String renovacion) {
		this.renovacion = renovacion;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public LocalDate getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(LocalDate fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public Usuario getUsuarioReg() {
		return usuarioReg;
	}

	public void setUsuarioReg(Usuario usuarioReg) {
		this.usuarioReg = usuarioReg;
	}


	
}

