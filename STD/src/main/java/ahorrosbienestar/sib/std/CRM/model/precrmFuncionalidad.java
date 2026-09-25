package ahorrosbienestar.sib.std.CRM.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(
    name = "TBL_PRECRM_FUNCIONALIDAD",
    schema = "SIB"
)
public class precrmFuncionalidad {


    // =========================================================
    // PK FUNCIONALIDAD
    // =========================================================

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "PK_FUNCIONALIDAD")
    private Long pkFuncionalidad;


    // =========================================================
    // CLAVE FUNCIONALIDAD
    // =========================================================

    @Column(
        name = "VC_CLAVE_FUNCIONALIDAD",
        length = 50,
        nullable = false,
        unique = true
    )
    private String claveFuncionalidad;


    // =========================================================
    // NOMBRE FUNCIONALIDAD
    // =========================================================

    @Column(
        name = "VC_NOMBRE_FUNCIONALIDAD",
        length = 150,
        nullable = false
    )
    private String nombreFuncionalidad;


    // =========================================================
    // DESCRIPCION
    // =========================================================

    @Column(
        name = "VC_DESCRIPCION",
        length = 500
    )
    private String descripcion;


    // =========================================================
    // RUTA
    // =========================================================

    @Column(
        name = "VC_RUTA",
        length = 255
    )
    private String ruta;


    // =========================================================
    // ACTIVO
    // =========================================================

    @Column(
        name = "I_ACTIVO",
        nullable = false
    )
    private Integer activo;


    // =========================================================
    // ORDEN
    // =========================================================

    @Column(
        name = "I_ORDEN",
        nullable = false
    )
    private Integer orden;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public precrmFuncionalidad() {

    }


    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getPkFuncionalidad() {
        return pkFuncionalidad;
    }


    public void setPkFuncionalidad(
            Long pkFuncionalidad) {

        this.pkFuncionalidad =
            pkFuncionalidad;
    }


    public String getClaveFuncionalidad() {
        return claveFuncionalidad;
    }


    public void setClaveFuncionalidad(
            String claveFuncionalidad) {

        this.claveFuncionalidad =
            claveFuncionalidad;
    }


    public String getNombreFuncionalidad() {
        return nombreFuncionalidad;
    }


    public void setNombreFuncionalidad(
            String nombreFuncionalidad) {

        this.nombreFuncionalidad =
            nombreFuncionalidad;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(
            String descripcion) {

        this.descripcion =
            descripcion;
    }


    public String getRuta() {
        return ruta;
    }


    public void setRuta(
            String ruta) {

        this.ruta =
            ruta;
    }


    public Integer getActivo() {
        return activo;
    }


    public void setActivo(
            Integer activo) {

        this.activo =
            activo;
    }


    public Integer getOrden() {
        return orden;
    }


    public void setOrden(
            Integer orden) {

        this.orden =
            orden;
    }

}