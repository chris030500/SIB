package ahorrosbienestar.sib.std.menuProducto.administracionSMS.model;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TBL_MENSAJES_SMS_AO")
public class MensajeSMS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID_MENSAJE")
    private int id;

    @Column(name = "DT_FECHA_MOD")
    private Date fechaModificacion;

    @Column(name = "DT_FECHA_REGISTRO")
    private Date fechaRegistro;

    @Column(name = "FK_ID_ASIGNACION")
    private Integer idAsignacion;

    @Column(name = "I_ESTATUS")
    private Integer estatus;

    @Column(name = "VC_DESC_MENSAJE", columnDefinition = "LONGTEXT")
    private String descripcionMensaje;

    @Column(name = "I_USUARIO_MOD")
    private Integer idUsuarioModificacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(Integer idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public Integer getEstatus() {
        return estatus;
    }

    public void setEstatus(Integer estatus) {
        this.estatus = estatus;
    }

    public String getDescripcionMensaje() {
        return descripcionMensaje;
    }

    public void setDescripcionMensaje(String descripcionMensaje) {
        this.descripcionMensaje = descripcionMensaje;
    }

    public Integer getIdUsuarioModificacion() {
        return idUsuarioModificacion;
    }

    public void setIdUsuarioModificacion(Integer idUsuarioModificacion) {
        this.idUsuarioModificacion = idUsuarioModificacion;
    }
}

