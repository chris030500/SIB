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
@Table(
    name = "TBL_PRECRM_USUARIO_ROL",
    schema = "SIB"
)
public class precrmUsuarioRol {


    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "PK_USUARIO_ROL")
    private Long pkUsuarioRol;


    @Column(
        name = "VC_USUARIO",
        nullable = false,
        length = 100
    )
    private String usuario;


    @Column(
        name = "FK_ROL",
        nullable = false
    )
    private Integer fkRol;


    @Column(
        name = "I_ACTIVO",
        nullable = false
    )
    private Integer activo;


    @Column(
        name = "DT_FECHA_ALTA",
        nullable = false
    )
    private LocalDateTime fechaAlta;


    @Column(
        name = "VC_USUARIO_ALTA",
        length = 100
    )
    private String usuarioAlta;


    @Column(
        name = "DT_FECHA_MODIFICACION"
    )
    private LocalDateTime fechaModificacion;


    @Column(
        name = "VC_USUARIO_MODIFICACION",
        length = 100
    )
    private String usuarioModificacion;


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

    }


    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getPkUsuarioRol() {

        return pkUsuarioRol;
    }


    public void setPkUsuarioRol(
            Long pkUsuarioRol) {

        this.pkUsuarioRol =
            pkUsuarioRol;
    }


    public String getUsuario() {

        return usuario;
    }


    public void setUsuario(
            String usuario) {

        this.usuario =
            usuario;
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