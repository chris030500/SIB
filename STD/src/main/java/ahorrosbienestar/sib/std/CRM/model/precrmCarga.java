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
    name = "TBL_PRECRM_CARGA",
    schema = "SIB"
)
public class precrmCarga {


    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    @Column(name = "PK_CARGA")
    private Long pkCarga;


    @Column(
        name = "VC_NOMBRE_ARCHIVO",
        nullable = false,
        length = 255
    )
    private String nombreArchivo;


    @Column(
        name = "DT_FECHA_ARCHIVO"
    )
    private LocalDateTime fechaArchivo;


    @Column(
        name = "DT_FECHA_PROCESAMIENTO",
        nullable = false
    )
    private LocalDateTime fechaProcesamiento;


    @Column(
        name = "VC_TIPO_CARGA",
        nullable = false,
        length = 20
    )
    private String tipoCarga;


    @Column(
        name = "VC_ESTATUS_PROCESO",
        nullable = false,
        length = 20
    )
    private String estatusProceso;


    @Column(
        name = "I_TOTAL_ENCONTRADOS",
        nullable = false
    )
    private Integer totalEncontrados;


    @Column(
        name = "I_TOTAL_CARGADOS",
        nullable = false
    )
    private Integer totalCargados;


    @Column(
        name = "I_TOTAL_DUPLICADOS",
        nullable = false
    )
    private Integer totalDuplicados;


    @Column(
        name = "I_TOTAL_REGISTRADOS_PREVIAMENTE",
        nullable = false
    )
    private Integer totalRegistradosPreviamente;


    @Column(
        name = "I_TOTAL_ERRORES",
        nullable = false
    )
    private Integer totalErrores;


    @Column(
        name = "I_TOTAL_NO_VIABLES",
        nullable = false
    )
    private Integer totalNoViables;


    @Column(
        name = "VC_USUARIO_PROCESO",
        length = 100
    )
    private String usuarioProceso;


    @Column(
        name = "DT_FECHA_REGISTRO",
        nullable = false
    )
    private LocalDateTime fechaRegistro;


    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public Long getPkCarga() {
        return pkCarga;
    }

    public void setPkCarga(
            Long pkCarga) {

        this.pkCarga =
            pkCarga;
    }


    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(
            String nombreArchivo) {

        this.nombreArchivo =
            nombreArchivo;
    }


    public LocalDateTime getFechaArchivo() {
        return fechaArchivo;
    }

    public void setFechaArchivo(
            LocalDateTime fechaArchivo) {

        this.fechaArchivo =
            fechaArchivo;
    }


    public LocalDateTime getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    public void setFechaProcesamiento(
            LocalDateTime fechaProcesamiento) {

        this.fechaProcesamiento =
            fechaProcesamiento;
    }


    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(
            String tipoCarga) {

        this.tipoCarga =
            tipoCarga;
    }


    public String getEstatusProceso() {
        return estatusProceso;
    }

    public void setEstatusProceso(
            String estatusProceso) {

        this.estatusProceso =
            estatusProceso;
    }


    public Integer getTotalEncontrados() {
        return totalEncontrados;
    }

    public void setTotalEncontrados(
            Integer totalEncontrados) {

        this.totalEncontrados =
            totalEncontrados;
    }


    public Integer getTotalCargados() {
        return totalCargados;
    }

    public void setTotalCargados(
            Integer totalCargados) {

        this.totalCargados =
            totalCargados;
    }


    public Integer getTotalDuplicados() {
        return totalDuplicados;
    }

    public void setTotalDuplicados(
            Integer totalDuplicados) {

        this.totalDuplicados =
            totalDuplicados;
    }


    public Integer getTotalRegistradosPreviamente() {
        return totalRegistradosPreviamente;
    }

    public void setTotalRegistradosPreviamente(
            Integer totalRegistradosPreviamente) {

        this.totalRegistradosPreviamente =
            totalRegistradosPreviamente;
    }


    public Integer getTotalErrores() {
        return totalErrores;
    }

    public void setTotalErrores(
            Integer totalErrores) {

        this.totalErrores =
            totalErrores;
    }


    public Integer getTotalNoViables() {
        return totalNoViables;
    }

    public void setTotalNoViables(
            Integer totalNoViables) {

        this.totalNoViables =
            totalNoViables;
    }


    public String getUsuarioProceso() {
        return usuarioProceso;
    }

    public void setUsuarioProceso(
            String usuarioProceso) {

        this.usuarioProceso =
            usuarioProceso;
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