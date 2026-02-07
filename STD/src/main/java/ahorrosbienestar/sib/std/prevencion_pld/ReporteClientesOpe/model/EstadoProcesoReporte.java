package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PLD_ESTADO_PROCESO", schema = "SIB")
public class EstadoProcesoReporte {

    // =========================
    // PK
    // =========================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROCESO")
    private Integer idProceso;

    // =========================
    // DATOS DE CONTROL
    // =========================
    @Column(name = "VC_USUARIO", nullable = false, length = 50)
    private String vcUsuario;

    @Column(name = "FECHA_CORTE", nullable = false)
    private LocalDate fechaCorte;

    // =========================
    // ESTADOS DEL FLUJO
    // =========================
    @Column(name = "EJECUTADO")
    private Boolean ejecutado = false;

    @Column(name = "AUDITORIA_OK")
    private Boolean auditoriaOk = false;

    @Column(name = "CLIENTES_DESCARGADO")
    private Boolean clientesDescargado = false;

    @Column(name = "CLIENTES_VOBO")
    private Boolean clientesVobo = false;

    @Column(name = "OPERACIONES_DESCARGADO")
    private Boolean operacionesDescargado = false;

    @Column(name = "OPERACIONES_VOBO")
    private Boolean operacionesVobo = false;

    // =========================
    // ESTATUS GENERAL
    // =========================
    /**
     * 1 = ACTIVO
     * 0 = FINALIZADO
     */
    @Column(name = "ESTATUS")
    private Integer estatus = 1;

    // =========================
    // AUDITORÍA
    // =========================
    @Column(name = "FECHA_CREACION", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    // =========================
    // CALLBACKS JPA
    // =========================
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    // =========================
    // GETTERS & SETTERS
    // =========================

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public String getVcUsuario() {
        return vcUsuario;
    }

    public void setVcUsuario(String vcUsuario) {
        this.vcUsuario = vcUsuario;
    }

    public LocalDate getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(LocalDate fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public Boolean getEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(Boolean ejecutado) {
        this.ejecutado = ejecutado;
    }

    public Boolean getAuditoriaOk() {
        return auditoriaOk;
    }

    public void setAuditoriaOk(Boolean auditoriaOk) {
        this.auditoriaOk = auditoriaOk;
    }

    public Boolean getClientesDescargado() {
        return clientesDescargado;
    }

    public void setClientesDescargado(Boolean clientesDescargado) {
        this.clientesDescargado = clientesDescargado;
    }

    public Boolean getClientesVobo() {
        return clientesVobo;
    }

    public void setClientesVobo(Boolean clientesVobo) {
        this.clientesVobo = clientesVobo;
    }

    public Boolean getOperacionesDescargado() {
        return operacionesDescargado;
    }

    public void setOperacionesDescargado(Boolean operacionesDescargado) {
        this.operacionesDescargado = operacionesDescargado;
    }

    public Boolean getOperacionesVobo() {
        return operacionesVobo;
    }

    public void setOperacionesVobo(Boolean operacionesVobo) {
        this.operacionesVobo = operacionesVobo;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

}

