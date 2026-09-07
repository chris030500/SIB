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
@Table(name = "TBL_PRECRM_CARGA_DETALLE")
public class PRECRMCargaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_DETALLE")
    private Long pkDetalle;

    @Column(name = "FK_CARGA", nullable = false)
    private Long fkCarga;

    @Column(name = "FILA_EXCEL")
    private Integer filaExcel;

    @Column(name = "ID_FORM")
    private String idForm;

    @Column(name = "HORA_INICIO")
    private LocalDateTime horaInicio;

    @Column(name = "HORA_FINALIZACION")
    private LocalDateTime horaFinalizacion;

    @Column(name = "CORREO_REGISTRO")
    private String correoRegistro;

    @Column(name = "NOMBRE_REGISTRO")
    private String nombreRegistro;

    @Column(name = "SUCURSAL_REGISTRO")
    private String sucursalRegistro;

    @Column(name = "NOMBRE_PROSPECTO")
    private String nombreProspecto;

    @Column(name = "FECHA_NACIMIENTO")
    private LocalDate fechaNacimiento;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "TELEFONO_CONTACTO")
    private String telefonoContacto;

    @Column(name = "CORREO_CONTACTO")
    private String correoContacto;

    @Column(name = "FUENTE_ORIGEN")
    private String fuenteOrigen;

    @Column(name = "ESTATUS_ORIGEN")
    private String estatusOrigen;

    @Column(name = "PRODUCTO_INTERES")
    private String productoInteres;

    @Column(name = "MONTO_INTERES")
    private BigDecimal montoInteres;

    @Column(name = "OBSERVACIONES")
    private String observaciones;

    @Column(name = "NOMBRE1")
    private String nombre1;

    @Column(name = "RESULTADO_CARGA")
    private String resultadoCarga;

    @Column(name = "MOTIVO_RESULTADO")
    private String motivoResultado;

    @Column(name = "FECHA_REGISTRO")
    private LocalDateTime fechaRegistro;


    public Long getPkDetalle() {
        return pkDetalle;
    }

    public void setPkDetalle(Long pkDetalle) {
        this.pkDetalle = pkDetalle;
    }

    public Long getFkCarga() {
        return fkCarga;
    }

    public void setFkCarga(Long fkCarga) {
        this.fkCarga = fkCarga;
    }

    public Integer getFilaExcel() {
        return filaExcel;
    }

    public void setFilaExcel(Integer filaExcel) {
        this.filaExcel = filaExcel;
    }

    public String getIdForm() {
        return idForm;
    }

    public void setIdForm(String idForm) {
        this.idForm = idForm;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFinalizacion() {
        return horaFinalizacion;
    }

    public void setHoraFinalizacion(LocalDateTime horaFinalizacion) {
        this.horaFinalizacion = horaFinalizacion;
    }

    public String getCorreoRegistro() {
        return correoRegistro;
    }

    public void setCorreoRegistro(String correoRegistro) {
        this.correoRegistro = correoRegistro;
    }

    public String getNombreRegistro() {
        return nombreRegistro;
    }

    public void setNombreRegistro(String nombreRegistro) {
        this.nombreRegistro = nombreRegistro;
    }

    public String getSucursalRegistro() {
        return sucursalRegistro;
    }

    public void setSucursalRegistro(String sucursalRegistro) {
        this.sucursalRegistro = sucursalRegistro;
    }

    public String getNombreProspecto() {
        return nombreProspecto;
    }

    public void setNombreProspecto(String nombreProspecto) {
        this.nombreProspecto = nombreProspecto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public String getFuenteOrigen() {
        return fuenteOrigen;
    }

    public void setFuenteOrigen(String fuenteOrigen) {
        this.fuenteOrigen = fuenteOrigen;
    }

    public String getEstatusOrigen() {
        return estatusOrigen;
    }

    public void setEstatusOrigen(String estatusOrigen) {
        this.estatusOrigen = estatusOrigen;
    }

    public String getProductoInteres() {
        return productoInteres;
    }

    public void setProductoInteres(String productoInteres) {
        this.productoInteres = productoInteres;
    }

    public BigDecimal getMontoInteres() {
        return montoInteres;
    }

    public void setMontoInteres(BigDecimal montoInteres) {
        this.montoInteres = montoInteres;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getResultadoCarga() {
        return resultadoCarga;
    }

    public void setResultadoCarga(String resultadoCarga) {
        this.resultadoCarga = resultadoCarga;
    }

    public String getMotivoResultado() {
        return motivoResultado;
    }

    public void setMotivoResultado(String motivoResultado) {
        this.motivoResultado = motivoResultado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}