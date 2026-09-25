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
    name = "TBL_CAT_PRECRM_TIPO_GESTION",
    schema = "SIB"
)
public class precrmTipoGestion {


    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "PK_TIPO_GESTION")
    private Integer pkTipoGestion;


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
        name = "I_ORDEN",
        nullable = false
    )
    private Integer orden;


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

    public Integer getPkTipoGestion() {

        return pkTipoGestion;
    }


    public void setPkTipoGestion(
            Integer pkTipoGestion) {

        this.pkTipoGestion =
            pkTipoGestion;
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


    public Integer getOrden() {

        return orden;
    }


    public void setOrden(
            Integer orden) {

        this.orden =
            orden;
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