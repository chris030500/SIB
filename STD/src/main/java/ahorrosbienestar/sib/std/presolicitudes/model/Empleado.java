package ahorrosbienestar.sib.std.presolicitudes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TBL_CLIENTES_ACTIVOS")
public class Empleado {

    @Id
    @Column(name = "I_ClienteID", nullable = false)
    private Integer clienteId;

    @Column(name = "VC_NombreCompleto", length = 200)
    private String nombreCompleto;

    @Column(name = "C_Estatus", length = 1)
    private String estatus;  // 'A' (Activo) o 'I' (Inactivo)

    @Column(name = "I_EstadoID")
    private Integer estadoId;

    @Column(name = "VC_DireccionCompleta", length = 250)
    private String direccionCompleta;

    @Column(name = "VC_NumeroEmpleado", length = 45)
    private String numeroEmpleado;

    @Column(name = "I_TipoEmpleado")
    private Integer tipoEmpleado;

    @Column(name = "I_EmpresaEmpleado")
    private Integer empresaEmpleado;

    // Getters y Setters

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Integer getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Integer estadoId) {
        this.estadoId = estadoId;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
    }

    public void setDireccionCompleta(String direccionCompleta) {
        this.direccionCompleta = direccionCompleta;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public Integer getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(Integer tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public Integer getEmpresaEmpleado() {
        return empresaEmpleado;
    }

    public void setEmpresaEmpleado(Integer empresaEmpleado) {
        this.empresaEmpleado = empresaEmpleado;
    }
}

