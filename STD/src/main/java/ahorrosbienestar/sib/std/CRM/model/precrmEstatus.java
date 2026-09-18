package ahorrosbienestar.sib.std.CRM.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_CAT_PRECRM_ESTATUS")
public class precrmEstatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ESTATUS")
    private Integer pkEstatus;

    @Column(name = "CLAVE")
    private String clave;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "DIAS_OBJETIVO")
    private Integer diasObjetivo;

    @Column(name = "ORDEN")
    private Integer orden;

    @Column(name = "FK_SIGUIENTE_ESTATUS")
    private Integer fkSiguienteEstatus;

    @Column(name = "ES_FINAL")
    private Integer esFinal;

    @Column(name = "ACTIVO")
    private Integer activo;

    @Column(name = "FECHA_REGISTRO")
    private LocalDateTime fechaRegistro;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;


    public Integer getPkEstatus() {
        return pkEstatus;
    }

    public void setPkEstatus(Integer pkEstatus) {
        this.pkEstatus = pkEstatus;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDiasObjetivo() {
        return diasObjetivo;
    }

    public void setDiasObjetivo(Integer diasObjetivo) {
        this.diasObjetivo = diasObjetivo;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getFkSiguienteEstatus() {
        return fkSiguienteEstatus;
    }

    public void setFkSiguienteEstatus(
            Integer fkSiguienteEstatus) {

        this.fkSiguienteEstatus =
                fkSiguienteEstatus;
    }

    public Integer getEsFinal() {
        return esFinal;
    }

    public void setEsFinal(Integer esFinal) {
        this.esFinal = esFinal;
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

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(
            LocalDateTime fechaActualizacion) {

        this.fechaActualizacion =
                fechaActualizacion;
    }
}