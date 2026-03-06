package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model;
 
import jakarta.persistence.*;
import java.time.LocalDateTime;
 
@Entity
@Table(name = "TBL_PROCESOS_PEPS", schema = "SIB")
public class ProcesosPEPsModel {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID_PEPS")
    private int pk_id_peps;
 
    @Column(name = "I_USER_REG", nullable = false)
    private Integer i_usuario_reg;
 
    @Column(name = "DT_FECHA_REGISTRO")
    private LocalDateTime dt_fecha_registro;
 
    @Column(name = "I_ESTATUS", nullable = false)
    private int i_estatus;
 
    // === Getters y Setters ===
 
    public int getPk_id_peps() {
        return pk_id_peps;
    }
 
    public void setPk_id_peps(int pk_id_peps) {
        this.pk_id_peps = pk_id_peps;
    }
 
    public Integer getI_usuario_reg() {
        return i_usuario_reg;
    }
 
    public void setI_usuario_reg(Integer i_usuario_reg) {
        this.i_usuario_reg = i_usuario_reg;
    }
 
    public LocalDateTime getDt_fecha_registro() {
        return dt_fecha_registro;
    }
 
    public void setDt_fecha_registro(LocalDateTime dt_fecha_registro) {
        this.dt_fecha_registro = dt_fecha_registro;
    }
 
    public int getI_estatus() {
        return i_estatus;
    }
 
    public void setI_estatus(int i_estatus) {
        this.i_estatus = i_estatus;
    }
}