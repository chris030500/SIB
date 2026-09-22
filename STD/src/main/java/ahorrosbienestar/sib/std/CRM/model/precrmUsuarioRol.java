package ahorrosbienestar.sib.std.CRM.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "TBL_PRECRM_USUARIO_ROL")
public class precrmUsuarioRol {


    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "PK_USUARIO_ROL")
    private Long pkUsuarioRol;


    @Column(name = "FK_USUARIO")
    private Integer fkUsuario;


    @Column(name = "FK_ROL")
    private Integer fkRol;


    @Column(name = "ACTIVO")
    private Integer activo;


    @Column(name = "FECHA_ALTA")
    private LocalDateTime fechaAlta;


    @Column(name = "USUARIO_ALTA")
    private String usuarioAlta;


    @Column(name = "FECHA_MODIFICACION")
    private LocalDateTime fechaModificacion;


    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;


    public Long getPkUsuarioRol() {
        return pkUsuarioRol;
    }


    public void setPkUsuarioRol(
            Long pkUsuarioRol) {

        this.pkUsuarioRol =
                pkUsuarioRol;
    }


    public Integer getFkUsuario() {
        return fkUsuario;
    }


    public void setFkUsuario(
            Integer fkUsuario) {

        this.fkUsuario =
                fkUsuario;
    }


    public Integer getFkRol() {
        return fkRol;
    }


    public void setFkRol(
            Integer fkRol) {

        this.fkRol =
                fkRol;
    }


    public Integer getActivo() {
        return activo;
    }


    public void setActivo(
            Integer activo) {

        this.activo =
                activo;
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


    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }


    public void setFechaModificacion(
            LocalDateTime fechaModificacion) {

        this.fechaModificacion =
                fechaModificacion;
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