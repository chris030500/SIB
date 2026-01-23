package ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
 
import org.springframework.format.annotation.DateTimeFormat;
 
import com.fasterxml.jackson.annotation.JsonFormat;
 
import ahorrosbienestar.sib.std.config_ws.cat_conf_ws.model.CatConfWS;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
 
@Entity
@Table(name = "TBL_WS_AUTENTICACION_USER")
public class WSAutenticacionUser {
    
    @Column(name = "PK_ID_WS_AUTENTICACION")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_ws_autenticacion_user_seq")
    @SequenceGenerator(name = "tbl_ws_autenticacion_user_seq", sequenceName = "tbl_ws_autenticacion_user_seq", initialValue = 1, allocationSize = 1)
    private int pk_id_ws_autenticacion;
    
    @ManyToOne
    @JoinColumn(name = "FK_ID_CONF_WS")
    private CatConfWS fk_id_conf_ws;
    
    @Column(name = "VC_TP_PROCESO_ORIGEN")
    private String vc_tp_proceso_origen;
    
    @ManyToOne
    @JoinColumn(name = "FK_ID_SOLICITUD_ENVIO")
    private SolicitudEnvio fk_id_solicitud_envio;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DT_FECHA_SOLICITUD")
    private LocalDate dt_fecha_solicitud;
    
    @Column(name = "VC_HORA_SOLICITUD", columnDefinition = "VARCHAR(45)")
    private String vc_hora_solicitud;
    
    @ManyToOne
    @JoinColumn(name = "FK_USR_SOLICITA")
    private Usuario fk_usr_solicita;
    
    @Column(name = "B_RESULTADO_LOGIN")
    private boolean b_resultado_login;
    
    @Column(name = "VC_JSON_ENVIO", columnDefinition = "LONGTEXT")
    private String vc_json_envio;
 
    
 
    public int getPk_id_ws_autenticacion() {
        return pk_id_ws_autenticacion;
    }
 
    public void setPk_id_ws_autenticacion(int pk_id_ws_autenticacion) {
        this.pk_id_ws_autenticacion = pk_id_ws_autenticacion;
    }
 
    public CatConfWS getFk_id_conf_ws() {
        return fk_id_conf_ws;
    }
 
    public void setFk_id_conf_ws(CatConfWS fk_id_conf_ws) {
        this.fk_id_conf_ws = fk_id_conf_ws;
    }
 
    public String getVc_tp_proceso_origen() {
        return vc_tp_proceso_origen;
    }
 
    public void setVc_tp_proceso_origen(String vc_tp_proceso_origen) {
        this.vc_tp_proceso_origen = vc_tp_proceso_origen;
    }
 
    public SolicitudEnvio getFk_id_solicitud_envio() {
        return fk_id_solicitud_envio;
    }
 
    public void setFk_id_solicitud_envio(SolicitudEnvio fk_id_solicitud_envio) {
        this.fk_id_solicitud_envio = fk_id_solicitud_envio;
    }
 
    public LocalDate getDt_fecha_solicitud() {
        return dt_fecha_solicitud;
    }
 
    public void setDt_fecha_solicitud(LocalDate dt_fecha_solicitud) {
        this.dt_fecha_solicitud = dt_fecha_solicitud;
    }
 
    public String getVc_hora_solicitud() {
        return vc_hora_solicitud;
    }
 
    public void setVc_hora_solicitud(String vc_hora_solicitud) {
        this.vc_hora_solicitud = vc_hora_solicitud;
    }
 
    public Usuario getFk_usr_solicita() {
        return fk_usr_solicita;
    }
 
    public void setFk_usr_solicita(Usuario fk_usr_solicita) {
        this.fk_usr_solicita = fk_usr_solicita;
    }
 
    public boolean isB_resultado_login() {
        return b_resultado_login;
    }
 
    public void setB_resultado_login(boolean b_resultado_login) {
        this.b_resultado_login = b_resultado_login;
    }
 
    public String getVc_json_envio() {
        return vc_json_envio;
    }
 
    public void setVc_json_envio(String vc_json_envio) {
        this.vc_json_envio = vc_json_envio;
    }
    
    
}

