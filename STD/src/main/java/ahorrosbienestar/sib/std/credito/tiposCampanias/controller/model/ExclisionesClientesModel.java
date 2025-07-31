package ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_EXCLUSION_CLIENTES", schema = "SIB")
public class ExclisionesClientesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID_EXCLUSION")
    private Integer idExclusion;

    @Column(name = "I_ID_CLIENTE")
    private Integer idCliente;

    @Column(name = "VC_NOMBRE_CLIENTE", length = 200)
    private String nombreCliente;

    @Column(name = "VC_MOTIVO_EXCLUSION", length = 200)
    private String motivoExclusion;

    @Column(name = "VC_OBSERVACIONES_EXCLUSION", length = 200)
    private String observacionesExclusion;

    @Column(name = "I_ESTATUS_CLIENTE")
    private Integer estatusCliente;

    // Getters y Setters
    public Integer getIdExclusion() {
        return idExclusion;
    }

    public void setIdExclusion(Integer idExclusion) {
        this.idExclusion = idExclusion;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getMotivoExclusion() {
        return motivoExclusion;
    }

    public void setMotivoExclusion(String motivoExclusion) {
        this.motivoExclusion = motivoExclusion;
    }

    public String getObservacionesExclusion() {
        return observacionesExclusion;
    }

    public void setObservacionesExclusion(String observacionesExclusion) {
        this.observacionesExclusion = observacionesExclusion;
    }

    public Integer getEstatusCliente() {
        return estatusCliente;
    }

    public void setEstatusCliente(Integer estatusCliente) {
        this.estatusCliente = estatusCliente;
    }
}
