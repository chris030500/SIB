package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;

import javax.persistence.*;

@Entity
@Table(name = "TBL_DETALLE_PEPS")
public class DetallePEPsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID_DETALLE")
    private Integer pkIdDetalle;

    @Column(name = "FK_PROCESO", nullable = true)
    private Integer fkProceso;

    @Column(name = "I_CLIENTEID", nullable = true)
    private Integer iClienteId;

    @Column(name = "VC_NOMBRE_CLIENTE", length = 500, nullable = true)
    private String vcNombreCliente;

    // ==========================
    // GETTERS Y SETTERS
    // ==========================

    public Integer getPkIdDetalle() {
        return pkIdDetalle;
    }

    public void setPkIdDetalle(Integer pkIdDetalle) {
        this.pkIdDetalle = pkIdDetalle;
    }

    public Integer getFkProceso() {
        return fkProceso;
    }

    public void setFkProceso(Integer fkProceso) {
        this.fkProceso = fkProceso;
    }

    public Integer getIClienteId() {
        return iClienteId;
    }

    public void setIClienteId(Integer iClienteId) {
        this.iClienteId = iClienteId;
    }

    public String getVcNombreCliente() {
        return vcNombreCliente;
    }

    public void setVcNombreCliente(String vcNombreCliente) {
        this.vcNombreCliente = vcNombreCliente;
    }
}
