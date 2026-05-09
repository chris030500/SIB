package ahorrosbienestar.sib.std.presolicitudes.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PROCESO_ENVIO_CORREO_VOBO")
public class ProcesoEnvioCorreoVobo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID_PROCESO")
    private Integer pkIdProceso;  // Clave primaria

    @Column(name = "VC_EMPRESA")
    private String vcEmpresa;  // Relacionado con la empresa

    @Column(name = "DT_FECHA_ENVIO")
    private LocalDateTime dtFechaEnvio;  // Fecha y hora de envío

    @Column(name = "I_CANTIDAD_SOLICITUDES")
    private Integer iCantidadSolicitudes;  // Cantidad de solicitudes enviadas

    @Column(name = "I_ESTATUS")
    private Integer iEstatus;  // Estatus del proceso (Pendiente, Completado, Fallido)

    // Getters y Setters

    public Integer getPkIdProceso() {
        return pkIdProceso;
    }

    public void setPkIdProceso(Integer pkIdProceso) {
        this.pkIdProceso = pkIdProceso;
    }

    public String getVcEmpresa() {
        return vcEmpresa;
    }

    public void setVcEmpresa(String vcEmpresa) {
        this.vcEmpresa = vcEmpresa;
    }

    public LocalDateTime getDtFechaEnvio() {
        return dtFechaEnvio;
    }

    public void setDtFechaEnvio(LocalDateTime dtFechaEnvio) {
        this.dtFechaEnvio = dtFechaEnvio;
    }

    public Integer getICantidadSolicitudes() {
        return iCantidadSolicitudes;
    }

    public void setICantidadSolicitudes(Integer iCantidadSolicitudes) {
        this.iCantidadSolicitudes = iCantidadSolicitudes;
    }

    public Integer getIEstatus() {
        return iEstatus;
    }

    public void setIEstatus(Integer iEstatus) {
        this.iEstatus = iEstatus;
    }
}
