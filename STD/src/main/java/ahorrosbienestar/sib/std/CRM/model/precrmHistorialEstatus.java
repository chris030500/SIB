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
    name = "TBL_PRECRM_HISTORIAL_ESTATUS",
    schema = "SIB"
)
public class precrmHistorialEstatus {


    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "PK_HISTORIAL")
    private Long pkHistorial;


    @Column(
        name = "FK_SOSPECHOSO",
        nullable = false
    )
    private Long fkSospechoso;


    @Column(
        name = "VC_ESTATUS_ANTERIOR",
        length = 50
    )
    private String estatusAnterior;


    @Column(
        name = "VC_ESTATUS_NUEVO",
        nullable = false,
        length = 50
    )
    private String estatusNuevo;


    @Column(
        name = "DT_FECHA_INICIO_ETAPA"
    )
    private LocalDateTime fechaInicioEtapa;


    @Column(
        name = "DT_FECHA_CAMBIO",
        nullable = false
    )
    private LocalDateTime fechaCambio;


    @Column(
        name = "DT_FECHA_LIMITE"
    )
    private LocalDateTime fechaLimite;


    @Column(
        name = "I_DIAS_OBJETIVO"
    )
    private Integer diasObjetivo;


    @Column(
        name = "VC_USUARIO_CAMBIO",
        nullable = false,
        length = 100
    )
    private String usuarioCambio;


    @Column(
        name = "VC_ORIGEN_CAMBIO",
        length = 30
    )
    private String origenCambio;


    @Column(
        name = "VC_COMENTARIO",
        length = 500
    )
    private String comentario;


    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getPkHistorial() {
        return pkHistorial;
    }

    public void setPkHistorial(
            Long pkHistorial) {

        this.pkHistorial =
            pkHistorial;
    }


    public Long getFkSospechoso() {
        return fkSospechoso;
    }

    public void setFkSospechoso(
            Long fkSospechoso) {

        this.fkSospechoso =
            fkSospechoso;
    }


    public String getEstatusAnterior() {
        return estatusAnterior;
    }

    public void setEstatusAnterior(
            String estatusAnterior) {

        this.estatusAnterior =
            estatusAnterior;
    }


    public String getEstatusNuevo() {
        return estatusNuevo;
    }

    public void setEstatusNuevo(
            String estatusNuevo) {

        this.estatusNuevo =
            estatusNuevo;
    }


    public LocalDateTime getFechaInicioEtapa() {
        return fechaInicioEtapa;
    }

    public void setFechaInicioEtapa(
            LocalDateTime fechaInicioEtapa) {

        this.fechaInicioEtapa =
            fechaInicioEtapa;
    }


    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(
            LocalDateTime fechaCambio) {

        this.fechaCambio =
            fechaCambio;
    }


    public LocalDateTime getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(
            LocalDateTime fechaLimite) {

        this.fechaLimite =
            fechaLimite;
    }


    public Integer getDiasObjetivo() {
        return diasObjetivo;
    }

    public void setDiasObjetivo(
            Integer diasObjetivo) {

        this.diasObjetivo =
            diasObjetivo;
    }


    public String getUsuarioCambio() {
        return usuarioCambio;
    }

    public void setUsuarioCambio(
            String usuarioCambio) {

        this.usuarioCambio =
            usuarioCambio;
    }


    public String getOrigenCambio() {
        return origenCambio;
    }

    public void setOrigenCambio(
            String origenCambio) {

        this.origenCambio =
            origenCambio;
    }


    public String getComentario() {
        return comentario;
    }

    public void setComentario(
            String comentario) {

        this.comentario =
            comentario;
    }

}