package ahorrosbienestar.sib.std.CRM.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(
    name = "TBL_CAT_PRECRM_ESTATUS",
    schema = "SIB"
)
public class precrmEstatus {


    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "PK_ESTATUS")
    private Integer pkEstatus;


    @Column(
        name = "VC_CLAVE",
        nullable = false,
        length = 50
    )
    private String clave;


    @Column(
        name = "VC_NOMBRE",
        nullable = false,
        length = 100
    )
    private String nombre;


    @Column(
        name = "VC_DESCRIPCION",
        length = 255
    )
    private String descripcion;


    @Column(
        name = "I_DIAS_OBJETIVO"
    )
    private Integer diasObjetivo;


    @Column(
        name = "I_ORDEN",
        nullable = false
    )
    private Integer orden;


    @Column(
        name = "FK_SIGUIENTE_ESTATUS"
    )
    private Integer fkSiguienteEstatus;


    @Column(
        name = "I_ES_FINAL",
        nullable = false
    )
    private Integer esFinal;


    @Column(
        name = "I_ACTIVO",
        nullable = false
    )
    private Integer activo;


    @Column(
        name = "DT_FECHA_REGISTRO",
        nullable = false
    )
    private LocalDateTime fechaRegistro;


    @Column(
        name = "DT_FECHA_ACTUALIZACION"
    )
    private LocalDateTime fechaActualizacion;


    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Integer getPkEstatus() {
        return pkEstatus;
    }

    public void setPkEstatus(
            Integer pkEstatus) {

        this.pkEstatus =
            pkEstatus;
    }


    public String getClave() {
        return clave;
    }

    public void setClave(
            String clave) {

        this.clave =
            clave;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(
            String nombre) {

        this.nombre =
            nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(
            String descripcion) {

        this.descripcion =
            descripcion;
    }


    public Integer getDiasObjetivo() {
        return diasObjetivo;
    }

    public void setDiasObjetivo(
            Integer diasObjetivo) {

        this.diasObjetivo =
            diasObjetivo;
    }


    public Integer getOrden() {
        return orden;
    }

    public void setOrden(
            Integer orden) {

        this.orden =
            orden;
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

    public void setEsFinal(
            Integer esFinal) {

        this.esFinal =
            esFinal;
    }


    public Integer getActivo() {
        return activo;
    }

    public void setActivo(
            Integer activo) {

        this.activo =
            activo;
    }


    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(
            LocalDateTime fechaRegistro) {

        this.fechaRegistro =
            fechaRegistro;
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