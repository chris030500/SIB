package ahorrosbienestar.sib.std.CRM.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Entity
@Table(name = "TBL_PRECRM_CATALOGO_REPORTE")
public class precrmCatalogoReporte {


    // =========================================================
    // PK REPORTE
    // =========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_REPORTE")
    private Long pkReporte;


    // =========================================================
    // CLAVE REPORTE
    // =========================================================

    @Column(
        name = "CLAVE_REPORTE",
        length = 50,
        nullable = false,
        unique = true
    )
    private String claveReporte;


    // =========================================================
    // NOMBRE REPORTE
    // =========================================================

    @Column(
        name = "NOMBRE_REPORTE",
        length = 150,
        nullable = false
    )
    private String nombreReporte;


    // =========================================================
    // DESCRIPCION
    // =========================================================

    @Column(
        name = "DESCRIPCION",
        length = 500
    )
    private String descripcion;


    // =========================================================
    // TIPO REPORTE
    // =========================================================

    @Column(
        name = "TIPO_REPORTE",
        length = 50
    )
    private String tipoReporte;


    // =========================================================
    // ACTIVO
    // =========================================================

    @Column(
        name = "ACTIVO",
        nullable = false
    )
    private Integer activo;


    // =========================================================
    // ORDEN
    // =========================================================

    @Column(
        name = "ORDEN",
        nullable = false
    )
    private Integer orden;


    // =========================================================
    // FECHA ALTA
    // =========================================================

    @Column(
        name = "FECHA_ALTA",
        nullable = false
    )
    private LocalDateTime fechaAlta;


    // =========================================================
    // USUARIO ALTA
    // =========================================================

    @Column(
        name = "USUARIO_ALTA",
        length = 100
    )
    private String usuarioAlta;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public precrmCatalogoReporte() {

    }


    // =========================================================
    // VALORES POR DEFECTO
    // =========================================================

    @PrePersist
    public void prePersist() {

        if (fechaAlta == null) {

            fechaAlta =
                LocalDateTime.now();

        }


        if (activo == null) {

            activo = 1;

        }


        if (orden == null) {

            orden = 0;

        }

    }


    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getPkReporte() {

        return pkReporte;

    }


    public void setPkReporte(
            Long pkReporte) {

        this.pkReporte =
            pkReporte;

    }


    public String getClaveReporte() {

        return claveReporte;

    }


    public void setClaveReporte(
            String claveReporte) {

        this.claveReporte =
            claveReporte;

    }


    public String getNombreReporte() {

        return nombreReporte;

    }


    public void setNombreReporte(
            String nombreReporte) {

        this.nombreReporte =
            nombreReporte;

    }


    public String getDescripcion() {

        return descripcion;

    }


    public void setDescripcion(
            String descripcion) {

        this.descripcion =
            descripcion;

    }


    public String getTipoReporte() {

        return tipoReporte;

    }


    public void setTipoReporte(
            String tipoReporte) {

        this.tipoReporte =
            tipoReporte;

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


    public LocalDateTime getFechaAlta() {

        return fechaAlta;

    }


    public void setFechaAlta(
            LocalDateTime fechaAlta) {

        this.fechaAlta =
            fechaAlta;

    }


    public String getUsuarioAlta() {

        return usuarioAlta;

    }


    public void setUsuarioAlta(
            String usuarioAlta) {

        this.usuarioAlta =
            usuarioAlta;

    }

}