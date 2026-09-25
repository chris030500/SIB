package ahorrosbienestar.sib.std.CRM.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(
    name = "TBL_PRECRM_CARGA_DETALLE",
    schema = "SIB"
)
public class precrmCargaDetalle {


    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "PK_DETALLE")
    private Long pkDetalle;


    @Column(
        name = "FK_CARGA",
        nullable = false
    )
    private Long fkCarga;


    @Column(
        name = "I_FILA_EXCEL",
        nullable = false
    )
    private Integer filaExcel;


    @Column(
        name = "I_ID_FORM"
    )
    private Integer idForm;


    @Column(
        name = "DT_HORA_INICIO"
    )
    private LocalDateTime horaInicio;


    @Column(
        name = "DT_HORA_FINALIZACION"
    )
    private LocalDateTime horaFinalizacion;


    @Column(
        name = "VC_CORREO_REGISTRO",
        length = 150
    )
    private String correoRegistro;


    @Column(
        name = "VC_NOMBRE_REGISTRO",
        length = 200
    )
    private String nombreRegistro;


    @Column(
        name = "VC_SUCURSAL_REGISTRO",
        length = 150
    )
    private String sucursalRegistro;


    @Column(
        name = "VC_NOMBRE_PROSPECTO",
        length = 200
    )
    private String nombreProspecto;


    @Column(
        name = "DT_FECHA_NACIMIENTO"
    )
    private LocalDate fechaNacimiento;


    @Column(
        name = "VC_TIPO",
        length = 50
    )
    private String tipo;


    @Column(
        name = "VC_TELEFONO_CONTACTO",
        length = 10
    )
    private String telefonoContacto;


    @Column(
        name = "VC_CORREO_CONTACTO",
        length = 150
    )
    private String correoContacto;


    @Column(
        name = "VC_FUENTE_ORIGEN",
        length = 100
    )
    private String fuenteOrigen;


    @Column(
        name = "VC_ESTATUS_ORIGEN",
        length = 50
    )
    private String estatusOrigen;


    @Column(
        name = "VC_PRODUCTO_INTERES",
        length = 100
    )
    private String productoInteres;


    @Column(
        name = "DC_MONTO_INTERES",
        precision = 15,
        scale = 2
    )
    private BigDecimal montoInteres;


    @Column(
        name = "VC_OBSERVACIONES",
        length = 500
    )
    private String observaciones;


    @Column(
        name = "VC_NOMBRE1",
        length = 200
    )
    private String nombre1;


    @Column(
        name = "VC_RESULTADO_CARGA",
        nullable = false,
        length = 30
    )
    private String resultadoCarga;


    @Column(
        name = "VC_MOTIVO_RESULTADO",
        length = 500
    )
    private String motivoResultado;


    @Column(
        name = "DT_FECHA_REGISTRO",
        nullable = false
    )
    private LocalDateTime fechaRegistro;


    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getPkDetalle() {
        return pkDetalle;
    }

    public void setPkDetalle(
            Long pkDetalle) {

        this.pkDetalle =
            pkDetalle;
    }


    public Long getFkCarga() {
        return fkCarga;
    }

    public void setFkCarga(
            Long fkCarga) {

        this.fkCarga =
            fkCarga;
    }


    public Integer getFilaExcel() {
        return filaExcel;
    }

    public void setFilaExcel(
            Integer filaExcel) {

        this.filaExcel =
            filaExcel;
    }


    public Integer getIdForm() {
        return idForm;
    }

    public void setIdForm(
            Integer idForm) {

        this.idForm =
            idForm;
    }


    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(
            LocalDateTime horaInicio) {

        this.horaInicio =
            horaInicio;
    }


    public LocalDateTime getHoraFinalizacion() {
        return horaFinalizacion;
    }

    public void setHoraFinalizacion(
            LocalDateTime horaFinalizacion) {

        this.horaFinalizacion =
            horaFinalizacion;
    }


    public String getCorreoRegistro() {
        return correoRegistro;
    }

    public void setCorreoRegistro(
            String correoRegistro) {

        this.correoRegistro =
            correoRegistro;
    }


    public String getNombreRegistro() {
        return nombreRegistro;
    }

    public void setNombreRegistro(
            String nombreRegistro) {

        this.nombreRegistro =
            nombreRegistro;
    }


    public String getSucursalRegistro() {
        return sucursalRegistro;
    }

    public void setSucursalRegistro(
            String sucursalRegistro) {

        this.sucursalRegistro =
            sucursalRegistro;
    }


    public String getNombreProspecto() {
        return nombreProspecto;
    }

    public void setNombreProspecto(
            String nombreProspecto) {

        this.nombreProspecto =
            nombreProspecto;
    }


    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(
            LocalDate fechaNacimiento) {

        this.fechaNacimiento =
            fechaNacimiento;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(
            String tipo) {

        this.tipo =
            tipo;
    }


    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(
            String telefonoContacto) {

        this.telefonoContacto =
            telefonoContacto;
    }


    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(
            String correoContacto) {

        this.correoContacto =
            correoContacto;
    }


    public String getFuenteOrigen() {
        return fuenteOrigen;
    }

    public void setFuenteOrigen(
            String fuenteOrigen) {

        this.fuenteOrigen =
            fuenteOrigen;
    }


    public String getEstatusOrigen() {
        return estatusOrigen;
    }

    public void setEstatusOrigen(
            String estatusOrigen) {

        this.estatusOrigen =
            estatusOrigen;
    }


    public String getProductoInteres() {
        return productoInteres;
    }

    public void setProductoInteres(
            String productoInteres) {

        this.productoInteres =
            productoInteres;
    }


    public BigDecimal getMontoInteres() {
        return montoInteres;
    }

    public void setMontoInteres(
            BigDecimal montoInteres) {

        this.montoInteres =
            montoInteres;
    }


    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(
            String observaciones) {

        this.observaciones =
            observaciones;
    }


    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(
            String nombre1) {

        this.nombre1 =
            nombre1;
    }


    public String getResultadoCarga() {
        return resultadoCarga;
    }

    public void setResultadoCarga(
            String resultadoCarga) {

        this.resultadoCarga =
            resultadoCarga;
    }


    public String getMotivoResultado() {
        return motivoResultado;
    }

    public void setMotivoResultado(
            String motivoResultado) {

        this.motivoResultado =
            motivoResultado;
    }


    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(
            LocalDateTime fechaRegistro) {

        this.fechaRegistro =
            fechaRegistro;
    }

}