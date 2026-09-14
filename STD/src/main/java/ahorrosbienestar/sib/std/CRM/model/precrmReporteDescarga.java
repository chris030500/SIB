package ahorrosbienestar.sib.std.CRM.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Entity
@Table(name = "TBL_PRECRM_REPORTE_DESCARGA")
public class precrmReporteDescarga {


    // =========================================================
    // PK DESCARGA
    // =========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_DESCARGA")
    private Long pkDescarga;


    // =========================================================
    // FK REPORTE
    // =========================================================

    @Column(
        name = "FK_REPORTE",
        nullable = false
    )
    private Long fkReporte;


    // =========================================================
    // RELACION CON CATALOGO DE REPORTES
    // =========================================================

    @ManyToOne(
        fetch = FetchType.EAGER
    )
    @JoinColumn(
        name = "FK_REPORTE",
        referencedColumnName = "PK_REPORTE",
        insertable = false,
        updatable = false
    )
    private precrmCatalogoReporte reporte;


    // =========================================================
    // USUARIO
    // =========================================================

    @Column(
        name = "USUARIO",
        length = 100,
        nullable = false
    )
    private String usuario;


    // =========================================================
    // FECHA DESCARGA
    // =========================================================

    @Column(
        name = "FECHA_DESCARGA",
        nullable = false
    )
    private LocalDateTime fechaDescarga;


    // =========================================================
    // NOMBRE ARCHIVO
    // =========================================================

    @Column(
        name = "NOMBRE_ARCHIVO",
        length = 255
    )
    private String nombreArchivo;


    // =========================================================
    // ESTATUS DESCARGA
    // =========================================================

    @Column(
        name = "ESTATUS_DESCARGA",
        length = 20,
        nullable = false
    )
    private String estatusDescarga;


    // =========================================================
    // TOTAL REGISTROS
    // =========================================================

    @Column(
        name = "TOTAL_REGISTROS"
    )
    private Integer totalRegistros;


    // =========================================================
    // MENSAJE ERROR
    // =========================================================

    @Column(
        name = "MENSAJE_ERROR",
        length = 1000
    )
    private String mensajeError;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public precrmReporteDescarga() {

    }


    // =========================================================
    // VALORES POR DEFECTO
    // =========================================================

    @PrePersist
    public void prePersist() {

        if (fechaDescarga == null) {

            fechaDescarga =
                LocalDateTime.now();

        }

    }


    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getPkDescarga() {

        return pkDescarga;

    }


    public void setPkDescarga(
            Long pkDescarga) {

        this.pkDescarga =
            pkDescarga;

    }


    public Long getFkReporte() {

        return fkReporte;

    }


    public void setFkReporte(
            Long fkReporte) {

        this.fkReporte =
            fkReporte;

    }


    public precrmCatalogoReporte getReporte() {

        return reporte;

    }


    public void setReporte(
            precrmCatalogoReporte reporte) {

        this.reporte =
            reporte;

    }


    public String getUsuario() {

        return usuario;

    }


    public void setUsuario(
            String usuario) {

        this.usuario =
            usuario;

    }


    public LocalDateTime getFechaDescarga() {

        return fechaDescarga;

    }


    public void setFechaDescarga(
            LocalDateTime fechaDescarga) {

        this.fechaDescarga =
            fechaDescarga;

    }


    public String getNombreArchivo() {

        return nombreArchivo;

    }


    public void setNombreArchivo(
            String nombreArchivo) {

        this.nombreArchivo =
            nombreArchivo;

    }


    public String getEstatusDescarga() {

        return estatusDescarga;

    }


    public void setEstatusDescarga(
            String estatusDescarga) {

        this.estatusDescarga =
            estatusDescarga;

    }


    public Integer getTotalRegistros() {

        return totalRegistros;

    }


    public void setTotalRegistros(
            Integer totalRegistros) {

        this.totalRegistros =
            totalRegistros;

    }


    public String getMensajeError() {

        return mensajeError;

    }


    public void setMensajeError(
            String mensajeError) {

        this.mensajeError =
            mensajeError;

    }

}