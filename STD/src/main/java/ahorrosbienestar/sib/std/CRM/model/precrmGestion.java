package ahorrosbienestar.sib.std.CRM.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "TBL_PRECRM_GESTION")
public class precrmGestion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_GESTION")
    private Long pkGestion;


    @Column(name = "FK_SOSPECHOSO", nullable = false)
    private Long fkSospechoso;


    @Column(name = "TIPO_GESTION", nullable = false)
    private String tipoGestion;


    @Column(name = "COMENTARIO")
    private String comentario;


    @Column(name = "FECHA_PROXIMO_SEGUIMIENTO")
    private LocalDateTime fechaProximoSeguimiento;


    @Column(name = "FECHA_CITA")
    private LocalDateTime fechaCita;


    @Column(name = "USUARIO_GESTION", nullable = false)
    private String usuarioGestion;


    @Column(name = "FECHA_GESTION", nullable = false)
    private LocalDateTime fechaGestion;


    public Long getPkGestion() {
        return pkGestion;
    }


    public void setPkGestion(
            Long pkGestion) {

        this.pkGestion = pkGestion;
    }


    public Long getFkSospechoso() {
        return fkSospechoso;
    }


    public void setFkSospechoso(
            Long fkSospechoso) {

        this.fkSospechoso = fkSospechoso;
    }


    public String getTipoGestion() {
        return tipoGestion;
    }


    public void setTipoGestion(
            String tipoGestion) {

        this.tipoGestion = tipoGestion;
    }


    public String getComentario() {
        return comentario;
    }


    public void setComentario(
            String comentario) {

        this.comentario = comentario;
    }


    public LocalDateTime getFechaProximoSeguimiento() {
        return fechaProximoSeguimiento;
    }


    public void setFechaProximoSeguimiento(
            LocalDateTime fechaProximoSeguimiento) {

        this.fechaProximoSeguimiento =
                fechaProximoSeguimiento;
    }


    public LocalDateTime getFechaCita() {
        return fechaCita;
    }


    public void setFechaCita(
            LocalDateTime fechaCita) {

        this.fechaCita = fechaCita;
    }


    public String getUsuarioGestion() {
        return usuarioGestion;
    }


    public void setUsuarioGestion(
            String usuarioGestion) {

        this.usuarioGestion = usuarioGestion;
    }


    public LocalDateTime getFechaGestion() {
        return fechaGestion;
    }


    public void setFechaGestion(
            LocalDateTime fechaGestion) {

        this.fechaGestion = fechaGestion;
    }
}