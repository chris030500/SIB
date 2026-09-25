package ahorrosbienestar.sib.std.CRM.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(
    name = "TBL_PRECRM_PERMISO",
    schema = "SIB"
)
public class precrmPermiso {


    // =========================================================
    // PK PERMISO
    // =========================================================

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "PK_PERMISO")
    private Long pkPermiso;


    // =========================================================
    // CLAVE PERMISO
    // =========================================================

    @Column(
        name = "VC_CLAVE_PERMISO",
        length = 50,
        nullable = false,
        unique = true
    )
    private String clavePermiso;


    // =========================================================
    // NOMBRE PERMISO
    // =========================================================

    @Column(
        name = "VC_NOMBRE_PERMISO",
        length = 100,
        nullable = false
    )
    private String nombrePermiso;


    // =========================================================
    // ACTIVO
    // =========================================================

    @Column(
        name = "I_ACTIVO",
        nullable = false
    )
    private Integer activo;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public precrmPermiso() {

    }


    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getPkPermiso() {

        return pkPermiso;

    }


    public void setPkPermiso(
            Long pkPermiso) {

        this.pkPermiso =
            pkPermiso;

    }


    public String getClavePermiso() {

        return clavePermiso;

    }


    public void setClavePermiso(
            String clavePermiso) {

        this.clavePermiso =
            clavePermiso;

    }


    public String getNombrePermiso() {

        return nombrePermiso;

    }


    public void setNombrePermiso(
            String nombrePermiso) {

        this.nombrePermiso =
            nombrePermiso;

    }


    public Integer getActivo() {

        return activo;

    }


    public void setActivo(
            Integer activo) {

        this.activo =
            activo;

    }

}