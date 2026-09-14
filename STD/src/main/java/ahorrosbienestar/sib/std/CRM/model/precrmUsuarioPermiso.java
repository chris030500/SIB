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
@Table(name = "TBL_PRECRM_USUARIO_PERMISO")
public class precrmUsuarioPermiso {


    // =========================================================
    // PK USUARIO PERMISO
    // =========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_USUARIO_PERMISO")
    private Long pkUsuarioPermiso;


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
    // FK FUNCIONALIDAD
    // =========================================================

    @Column(
        name = "FK_FUNCIONALIDAD",
        nullable = false
    )
    private Long fkFuncionalidad;


    // =========================================================
    // RELACION FUNCIONALIDAD
    // =========================================================

    @ManyToOne(
        fetch = FetchType.EAGER
    )
    @JoinColumn(
        name = "FK_FUNCIONALIDAD",
        referencedColumnName = "PK_FUNCIONALIDAD",
        insertable = false,
        updatable = false
    )
    private precrmFuncionalidad funcionalidad;


    // =========================================================
    // FK PERMISO
    // =========================================================

    @Column(
        name = "FK_PERMISO",
        nullable = false
    )
    private Long fkPermiso;


    // =========================================================
    // RELACION PERMISO
    // =========================================================

    @ManyToOne(
        fetch = FetchType.EAGER
    )
    @JoinColumn(
        name = "FK_PERMISO",
        referencedColumnName = "PK_PERMISO",
        insertable = false,
        updatable = false
    )
    private precrmPermiso permiso;


    // =========================================================
    // ACTIVO
    // =========================================================

    @Column(
        name = "ACTIVO",
        nullable = false
    )
    private Integer activo;


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
    // FECHA MODIFICACION
    // =========================================================

    @Column(
        name = "FECHA_MODIFICACION"
    )
    private LocalDateTime fechaModificacion;


    // =========================================================
    // USUARIO MODIFICACION
    // =========================================================

    @Column(
        name = "USUARIO_MODIFICACION",
        length = 100
    )
    private String usuarioModificacion;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public precrmUsuarioPermiso() {

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

    }


    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getPkUsuarioPermiso() {

        return pkUsuarioPermiso;

    }


    public void setPkUsuarioPermiso(
            Long pkUsuarioPermiso) {

        this.pkUsuarioPermiso =
            pkUsuarioPermiso;

    }


    public String getUsuario() {

        return usuario;

    }


    public void setUsuario(
            String usuario) {

        this.usuario =
            usuario;

    }


    public Long getFkFuncionalidad() {

        return fkFuncionalidad;

    }


    public void setFkFuncionalidad(
            Long fkFuncionalidad) {

        this.fkFuncionalidad =
            fkFuncionalidad;

    }


    public precrmFuncionalidad getFuncionalidad() {

        return funcionalidad;

    }


    public void setFuncionalidad(
            precrmFuncionalidad funcionalidad) {

        this.funcionalidad =
            funcionalidad;

    }


    public Long getFkPermiso() {

        return fkPermiso;

    }


    public void setFkPermiso(
            Long fkPermiso) {

        this.fkPermiso =
            fkPermiso;

    }


    public precrmPermiso getPermiso() {

        return permiso;

    }


    public void setPermiso(
            precrmPermiso permiso) {

        this.permiso =
            permiso;

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