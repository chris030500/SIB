package ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.model;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CONFIG_MIGRACIONES_AOP", schema = "SIB")
public class MigracionesP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_MIGRACION")
    private Integer idMigracion;

    @Column(name = "VC_NOMBRE_MIGRACION")
    private String nombreMigracion;

    @Column(name = "VC_RUTA_MIGRACION")
    private String rutaMigracion;

    @Column(name = "I_ESTATUS")
    private Integer estatus; // 1 = activo, 0 = inactivo

    @Column(name = "VC_CRON")
    private String cron; // Ejemplo: "13" o "17"

    @Column(name = "I_EJECUTA")
    private Integer ejecuta; // 1 = ejecutar, 0 = no ejecutar

    @Column(name = "VC_USUARIO_EJECUTA")
    private String usuarioEjecuta;

    @Column(name = "VC_USUARIO_ACTUALIZA")
    private String usuarioActualiza;

    @Column(name = "DT_FECHA_EJECUCION")
    private LocalDateTime fechaEjecucion;

    @Column(name = "VC_MENSAJE_RESULTADO")
    private String mensajeResultado;
    
    @Column(name = "DT_FECHA_INICIO")
    private LocalDate fechaInicio;

    @Column(name = "DT_FECHA_FIN")
    private LocalDate fechaFin;

    // Getters y Setters

    public Integer getIdMigracion() {
        return idMigracion;
    }

    public void setIdMigracion(Integer idMigracion) {
        this.idMigracion = idMigracion;
    }

    public String getNombreMigracion() {
        return nombreMigracion;
    }

    public void setNombreMigracion(String nombreMigracion) {
        this.nombreMigracion = nombreMigracion;
    }

    public String getRutaMigracion() {
        return rutaMigracion;
    }

    public void setRutaMigracion(String rutaMigracion) {
        this.rutaMigracion = rutaMigracion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getEjecuta() {
        return ejecuta;
    }

    public void setEjecuta(Integer ejecuta) {
        this.ejecuta = ejecuta;
    }

    public String getUsuarioEjecuta() {
        return usuarioEjecuta;
    }

    public void setUsuarioEjecuta(String usuarioEjecuta) {
        this.usuarioEjecuta = usuarioEjecuta;
    }

    public String getUsuarioActualiza() {
        return usuarioActualiza;
    }

    public void setUsuarioActualiza(String usuarioActualiza) {
        this.usuarioActualiza = usuarioActualiza;
    }

    public LocalDateTime getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(LocalDateTime fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getMensajeResultado() {
        return mensajeResultado;
    }

    public void setMensajeResultado(String mensajeResultado) {
        this.mensajeResultado = mensajeResultado;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}