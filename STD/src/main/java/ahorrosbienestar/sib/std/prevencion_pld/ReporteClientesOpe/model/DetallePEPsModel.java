package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name="TBL_DETALLE_PEPS")
public class DetallePEPsModel {
 
    @Column(name = "PK_ID_DETALLE")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pk_id_detalle;
    
    @Column(name = "FK_PROCESO")
    private int fk_proceso;
    
    @Column(name = "I_CLIENTEID")
    private int i_clienteid;
    
    @Column(name = "VC_NOMBRE_CLIENTE")
    private String vc_nombre_cliente;
 
    public int getPk_id_detalle() {
        return pk_id_detalle;
    }
 
    public void setPk_id_detalle(int pk_id_detalle) {
        this.pk_id_detalle = pk_id_detalle;
    }
 
    public int getFk_proceso() {
        return fk_proceso;
    }
 
    public void setFk_proceso(int fk_proceso) {
        this.fk_proceso = fk_proceso;
    }
 
    public int getI_clienteid() {
        return i_clienteid;
    }
 
    public void setI_clienteid(int i_clienteid) {
        this.i_clienteid = i_clienteid;
    }
 
    public String getVc_nombre_cliente() {
        return vc_nombre_cliente;
    }
 
    public void setVc_nombre_cliente(String vc_nombre_cliente) {
        this.vc_nombre_cliente = vc_nombre_cliente;
    }
 
    
    
    
}

