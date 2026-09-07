package ahorrosbienestar.sib.std.CRM.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PRECRM_CARGA")
public class PRECRMCarga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_CARGA")
    private Long pkCarga;

    @Column(name = "NOMBRE_ARCHIVO", nullable = false)
    private String nombreArchivo;

    @Column(name = "FECHA_ARCHIVO")
    private LocalDateTime fechaArchivo;

    @Column(name = "FECHA_PROCESAMIENTO", nullable = false)
    private LocalDateTime fechaProcesamiento;

    @Column(name = "TIPO_CARGA", nullable = false)
    private String tipoCarga;

    @Column(name = "ESTATUS_PROCESO", nullable = false)
    private String estatusProceso;

    @Column(name = "TOTAL_ENCONTRADOS")
    private Integer totalEncontrados;

    @Column(name = "TOTAL_CARGADOS")
    private Integer totalCargados;

    @Column(name = "TOTAL_DUPLICADOS")
    private Integer totalDuplicados;

    @Column(name = "TOTAL_ERRORES")
    private Integer totalErrores;

    @Column(name = "TOTAL_NO_VIABLES")
    private Integer totalNoViables;

    @Column(name = "USUARIO_PROCESO")
    private String usuarioProceso;

    @Column(name = "FECHA_REGISTRO")
    private LocalDateTime fechaRegistro;


    public Long getPkCarga() {
        return pkCarga;
    }

    public void setPkCarga(Long pkCarga) {
        this.pkCarga = pkCarga;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public LocalDateTime getFechaArchivo() {
        return fechaArchivo;
    }

    public void setFechaArchivo(LocalDateTime fechaArchivo) {
        this.fechaArchivo = fechaArchivo;
    }

    public LocalDateTime getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    public void setFechaProcesamiento(LocalDateTime fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public String getEstatusProceso() {
        return estatusProceso;
    }

    public void setEstatusProceso(String estatusProceso) {
        this.estatusProceso = estatusProceso;
    }

    public Integer getTotalEncontrados() {
        return totalEncontrados;
    }

    public void setTotalEncontrados(Integer totalEncontrados) {
        this.totalEncontrados = totalEncontrados;
    }

    public Integer getTotalCargados() {
        return totalCargados;
    }

    public void setTotalCargados(Integer totalCargados) {
        this.totalCargados = totalCargados;
    }

    public Integer getTotalDuplicados() {
        return totalDuplicados;
    }

    public void setTotalDuplicados(Integer totalDuplicados) {
        this.totalDuplicados = totalDuplicados;
    }

    public Integer getTotalErrores() {
        return totalErrores;
    }

    public void setTotalErrores(Integer totalErrores) {
        this.totalErrores = totalErrores;
    }

    public Integer getTotalNoViables() {
        return totalNoViables;
    }

    public void setTotalNoViables(Integer totalNoViables) {
        this.totalNoViables = totalNoViables;
    }

    public String getUsuarioProceso() {
        return usuarioProceso;
    }

    public void setUsuarioProceso(String usuarioProceso) {
        this.usuarioProceso = usuarioProceso;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}