//DetallePEPsModel
package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_DETALLE_PEPS")
public class DetallePEPsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID_DETALLE")
    private Integer pkIdDetalle;

    @Column(name = "FK_PROCESO")
    private Integer fkProceso;

    @Column(name = "I_CLIENTEID")
    private Integer iClienteId;

    @Column(name = "VC_NOMBRE_CLIENTE")
    private String vcNombreCliente;

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