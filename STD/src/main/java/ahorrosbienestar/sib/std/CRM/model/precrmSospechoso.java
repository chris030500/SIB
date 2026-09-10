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
@Table(name = "TBL_PRECRM_SOSPECHOSO")
public class precrmSospechoso {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_SOSPECHOSO")
    private Long pkSospechoso;


    @Column(name = "FK_CARGA_DETALLE", nullable = false)
    private Long fkCargaDetalle;


    @Column(name = "FOLIO", nullable = false)
    private String folio;


    @Column(name = "NOMBRE", nullable = false)
    private String nombre;


    @Column(name = "FECHA_NACIMIENTO")
    private LocalDate fechaNacimiento;


    @Column(name = "TELEFONO_PRINCIPAL", nullable = false)
    private String telefonoPrincipal;
    
    @Column(name = "VC_TELEFONO_ALTERNO")
    private String telefonoAlterno;

    @Column(name = "CORREO")
    private String correo;


    @Column(name = "CLASIFICACION")
    private String clasificacion;


    @Column(name = "FUENTE_ORIGEN")
    private String fuenteOrigen;


    @Column(name = "PRODUCTO_INTERES")
    private String productoInteres;


    @Column(name = "MONTO_INTERES")
    private BigDecimal montoInteres;


    @Column(name = "OBSERVACIONES")
    private String observaciones;


    @Column(name = "SUCURSAL_ORIGEN")
    private String sucursalOrigen;


    @Column(name = "ESTATUS", nullable = false)
    private String estatus;
    
    @Column(name = "FECHA_INICIO_ETAPA")
    private LocalDateTime fechaInicioEtapa;
    
    @Column(name = "FECHA_PROXIMA_GESTION")
    private LocalDateTime fechaProximaGestion;

    @Column(name = "DIAS_RESTANTES")
    private Integer diasRestantes;

    @Column(name = "FECHA_ULTIMA_ACTUALIZACION")
    private LocalDateTime fechaUltimaActualizacion;


    @Column(name = "FECHA_REGISTRO", nullable = false)
    private LocalDateTime fechaRegistro;


    @Column(name = "ACTIVO", nullable = false)
    private Integer activo;


    public Long getPkSospechoso() {
        return pkSospechoso;
    }


    public void setPkSospechoso(
            Long pkSospechoso) {

        this.pkSospechoso =
                pkSospechoso;
    }


    public Long getFkCargaDetalle() {
        return fkCargaDetalle;
    }


    public void setFkCargaDetalle(
            Long fkCargaDetalle) {

        this.fkCargaDetalle =
                fkCargaDetalle;
    }


    public String getFolio() {
        return folio;
    }


    public void setFolio(
            String folio) {

        this.folio =
                folio;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(
            String nombre) {

        this.nombre =
                nombre;
    }


    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }


    public void setFechaNacimiento(
            LocalDate fechaNacimiento) {

        this.fechaNacimiento =
                fechaNacimiento;
    }


    public String getTelefonoPrincipal() {
        return telefonoPrincipal;
    }


    public void setTelefonoPrincipal(
            String telefonoPrincipal) {

        this.telefonoPrincipal =
                telefonoPrincipal;
    }
    
    public String getTelefonoAlterno() {
        return telefonoAlterno;
    }


    public void setTelefonoAlterno(
            String telefonoAlterno) {

        this.telefonoAlterno =
        		telefonoAlterno;
    }


    public String getCorreo() {
        return correo;
    }


    public void setCorreo(
            String correo) {

        this.correo =
                correo;
    }


    public String getClasificacion() {
        return clasificacion;
    }


    public void setClasificacion(
            String clasificacion) {

        this.clasificacion =
                clasificacion;
    }


    public String getFuenteOrigen() {
        return fuenteOrigen;
    }


    public void setFuenteOrigen(
            String fuenteOrigen) {

        this.fuenteOrigen =
                fuenteOrigen;
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


    public String getSucursalOrigen() {
        return sucursalOrigen;
    }


    public void setSucursalOrigen(
            String sucursalOrigen) {

        this.sucursalOrigen =
                sucursalOrigen;
    }


    public String getEstatus() {
        return estatus;
    }


    public void setEstatus(
            String estatus) {

        this.estatus =
                estatus;
    }
    
    public LocalDateTime getFechaInicioEtapa() {

        return fechaInicioEtapa;

    }


    public void setFechaInicioEtapa(
            LocalDateTime fechaInicioEtapa) {

        this.fechaInicioEtapa =
                fechaInicioEtapa;

    }
    
    public LocalDateTime getFechaProximaGestion() {

        return fechaProximaGestion;

    }


    public void setFechaProximaGestion(
            LocalDateTime fechaProximaGestion) {

        this.fechaProximaGestion =
                fechaProximaGestion;

    }
    
    public Integer getDiasRestantes() {

        return diasRestantes;

    }


    public void setDiasRestantes(
            Integer diasRestantes) {

        this.diasRestantes =
                diasRestantes;

    }


    public LocalDateTime getFechaUltimaActualizacion() {

        return fechaUltimaActualizacion;

    }


    public void setFechaUltimaActualizacion(
            LocalDateTime fechaUltimaActualizacion) {

        this.fechaUltimaActualizacion =
                fechaUltimaActualizacion;

    }


    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }


    public void setFechaRegistro(
            LocalDateTime fechaRegistro) {

        this.fechaRegistro =
                fechaRegistro;
    }


    public Integer getActivo() {
        return activo;
    }


    public void setActivo(
            Integer activo) {

        this.activo =
                activo;
    }
}