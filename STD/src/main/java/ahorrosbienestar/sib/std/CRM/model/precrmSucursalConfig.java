package ahorrosbienestar.sib.std.CRM.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


// =========================================================
// CONFIGURACION DE SUCURSALES PRE CRM
// =========================================================

@Entity
@Table(name = "TBL_PRECRM_SUCURSAL_CONFIG")
public class precrmSucursalConfig {


    // =====================================================
    // PK CONFIGURACION
    // =====================================================

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "PK_SUCURSAL_CONFIG")
    private Integer pkSucursalConfig;


    // =====================================================
    // FK SUCURSAL
    // TBL_SUCURSALES.PK_ID_SUCURSAL
    // =====================================================

    @Column(
        name = "FK_SUCURSAL",
        nullable = false
    )
    private Integer fkSucursal;


    // =====================================================
    // INDICA SI LA SUCURSAL PARTICIPA EN PRE CRM
    //
    // 1 = ACTIVA
    // 0 = INACTIVA
    // =====================================================

    @Column(
        name = "ACTIVO_PRECRM",
        nullable = false
    )
    private Integer activoPrecrm;


    // =====================================================
    // FECHA DE ALTA
    // =====================================================

    @Column(
        name = "FECHA_ALTA",
        nullable = false
    )
    private LocalDateTime fechaAlta;


    // =====================================================
    // USUARIO QUE DIO DE ALTA
    // =====================================================

    @Column(
        name = "USUARIO_ALTA",
        length = 100,
        nullable = false
    )
    private String usuarioAlta;


    // =====================================================
    // FECHA DE ULTIMA MODIFICACION
    // =====================================================

    @Column(
        name = "FECHA_MODIFICACION"
    )
    private LocalDateTime fechaModificacion;


    // =====================================================
    // USUARIO QUE REALIZO ULTIMA MODIFICACION
    // =====================================================

    @Column(
        name = "USUARIO_MODIFICACION",
        length = 100
    )
    private String usuarioModificacion;



    // =====================================================
    // CONSTRUCTOR VACIO
    // =====================================================

    public precrmSucursalConfig() {

    }



    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public precrmSucursalConfig(
            Integer fkSucursal,
            Integer activoPrecrm,
            String usuarioAlta) {

        this.fkSucursal =
            fkSucursal;

        this.activoPrecrm =
            activoPrecrm;

        this.usuarioAlta =
            usuarioAlta;
    }



    // =====================================================
    // PRE PERSIST
    // =====================================================

    @PrePersist
    public void prePersist() {


        if (this.fechaAlta == null) {

            this.fechaAlta =
                LocalDateTime.now();
        }


        if (this.activoPrecrm == null) {

            this.activoPrecrm =
                1;
        }
    }



    // =====================================================
    // GET PK SUCURSAL CONFIG
    // =====================================================

    public Integer getPkSucursalConfig() {

        return pkSucursalConfig;
    }


    // =====================================================
    // SET PK SUCURSAL CONFIG
    // =====================================================

    public void setPkSucursalConfig(
            Integer pkSucursalConfig) {

        this.pkSucursalConfig =
            pkSucursalConfig;
    }



    // =====================================================
    // GET FK SUCURSAL
    // =====================================================

    public Integer getFkSucursal() {

        return fkSucursal;
    }


    // =====================================================
    // SET FK SUCURSAL
    // =====================================================

    public void setFkSucursal(
            Integer fkSucursal) {

        this.fkSucursal =
            fkSucursal;
    }



    // =====================================================
    // GET ACTIVO PRE CRM
    // =====================================================

    public Integer getActivoPrecrm() {

        return activoPrecrm;
    }


    // =====================================================
    // SET ACTIVO PRE CRM
    // =====================================================

    public void setActivoPrecrm(
            Integer activoPrecrm) {

        this.activoPrecrm =
            activoPrecrm;
    }



    // =====================================================
    // GET FECHA ALTA
    // =====================================================

    public LocalDateTime getFechaAlta() {

        return fechaAlta;
    }


    // =====================================================
    // SET FECHA ALTA
    // =====================================================

    public void setFechaAlta(
            LocalDateTime fechaAlta) {

        this.fechaAlta =
            fechaAlta;
    }



    // =====================================================
    // GET USUARIO ALTA
    // =====================================================

    public String getUsuarioAlta() {

        return usuarioAlta;
    }


    // =====================================================
    // SET USUARIO ALTA
    // =====================================================

    public void setUsuarioAlta(
            String usuarioAlta) {

        this.usuarioAlta =
            usuarioAlta;
    }



    // =====================================================
    // GET FECHA MODIFICACION
    // =====================================================

    public LocalDateTime getFechaModificacion() {

        return fechaModificacion;
    }


    // =====================================================
    // SET FECHA MODIFICACION
    // =====================================================

    public void setFechaModificacion(
            LocalDateTime fechaModificacion) {

        this.fechaModificacion =
            fechaModificacion;
    }



    // =====================================================
    // GET USUARIO MODIFICACION
    // =====================================================

    public String getUsuarioModificacion() {

        return usuarioModificacion;
    }


    // =====================================================
    // SET USUARIO MODIFICACION
    // =====================================================

    public void setUsuarioModificacion(
            String usuarioModificacion) {

        this.usuarioModificacion =
            usuarioModificacion;
    }

}