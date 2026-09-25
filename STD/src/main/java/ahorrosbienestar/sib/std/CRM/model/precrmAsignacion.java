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
    name = "TBL_PRECRM_ASIGNACION",
    schema = "SIB"
)
public class precrmAsignacion {


    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "PK_ASIGNACION")
    private Long pkAsignacion;


    @Column(
        name = "FK_SOSPECHOSO",
        nullable = false
    )
    private Long fkSospechoso;


    @Column(
        name = "VC_USUARIO_ASIGNADO",
        nullable = false,
        length = 100
    )
    private String usuarioAsignado;


    @Column(
        name = "I_ACTIVO",
        nullable = false
    )
    private Integer activo;


    @Column(
        name = "DT_FECHA_ASIGNACION",
        nullable = false
    )
    private LocalDateTime fechaAsignacion;


    @Column(
        name = "VC_USUARIO_ASIGNA",
        length = 100
    )
    private String usuarioAsigna;


    @Column(
        name = "DT_FECHA_FIN"
    )
    private LocalDateTime fechaFin;


    @Column(
        name = "VC_USUARIO_MODIFICACION",
        length = 100
    )
    private String usuarioModificacion;


    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getPkAsignacion() {
        return pkAsignacion;
    }

    public void setPkAsignacion(
            Long pkAsignacion) {

        this.pkAsignacion =
            pkAsignacion;
    }


    public Long getFkSospechoso() {
        return fkSospechoso;
    }

    public void setFkSospechoso(
            Long fkSospechoso) {

        this.fkSospechoso =
            fkSospechoso;
    }


    public String getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public void setUsuarioAsignado(
            String usuarioAsignado) {

        this.usuarioAsignado =
            usuarioAsignado;
    }


    public Integer getActivo() {
        return activo;
    }

    public void setActivo(
            Integer activo) {

        this.activo =
            activo;
    }


    public LocalDateTime getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(
            LocalDateTime fechaAsignacion) {

        this.fechaAsignacion =
            fechaAsignacion;
    }


    public String getUsuarioAsigna() {
        return usuarioAsigna;
    }

    public void setUsuarioAsigna(
            String usuarioAsigna) {

        this.usuarioAsigna =
            usuarioAsigna;
    }


    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(
            LocalDateTime fechaFin) {

        this.fechaFin =
            fechaFin;
    }


    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(
            String usuarioModificacion) {

        this.usuarioModificacion =
            usuarioModificacion;
    }

}