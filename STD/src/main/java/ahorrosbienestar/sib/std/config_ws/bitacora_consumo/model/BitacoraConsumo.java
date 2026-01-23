package ahorrosbienestar.sib.std.config_ws.bitacora_consumo.model;

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
import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.model.WSAutenticacionUser;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
 
@Entity
@Table(name = "TBL_BITACORA_CONSUMO")
public class BitacoraConsumo {
    
    @Column(name = "PK_ID_BITACORA_CONSUMO")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_bitacora_consumo_seq")
    @SequenceGenerator(name = "tbl_bitacora_consumo_seq", sequenceName = "tbl_bitacora_consumo_seq", initialValue = 1, allocationSize = 1)
    private int pk_id_bitacora_consumo;
    
    @OneToOne
    @JoinColumn(name = "FK_ID_AUTENTICACION_USR")
    private WSAutenticacionUser fk_id_autenticacion_usr;
    
    @OneToOne
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
    
    @Column(name = "VC_JSON_ENVIO", columnDefinition = "MEDIUMTEXT")
    private String vc_json_envio;
    
    @Column(name = "VC_JSON_RESPUESTA", columnDefinition = "MEDIUMTEXT")
    private String vc_json_respuesta;
    
    @Column(name = "VC_RESULTADO_CONSUMO", columnDefinition = "MEDIUMTEXT")
    private String vc_resultado_consumo;
 
    public int getPk_id_bitacora_consumo() {
        return pk_id_bitacora_consumo;
    }
 
    public void setPk_id_bitacora_consumo(int pk_id_bitacora_consumo) {
        this.pk_id_bitacora_consumo = pk_id_bitacora_consumo;
    }
 
    public WSAutenticacionUser getFk_id_autenticacion_usr() {
        return fk_id_autenticacion_usr;
    }
 
    public void setFk_id_autenticacion_usr(WSAutenticacionUser fk_id_autenticacion_usr) {
        this.fk_id_autenticacion_usr = fk_id_autenticacion_usr;
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
 
    public String getVc_json_envio() {
        return vc_json_envio;
    }
 
    public void setVc_json_envio(String vc_json_envio) {
        this.vc_json_envio = vc_json_envio;
    }
 
    public String getVc_json_respuesta() {
        return vc_json_respuesta;
    }
 
    public void setVc_json_respuesta(String vc_json_respuesta) {
        this.vc_json_respuesta = vc_json_respuesta;
    }
 
    public String getVc_resultado_consumo() {
        return vc_resultado_consumo;
    }
 
    public void setVc_resultado_consumo(String vc_resultado_consumo) {
        this.vc_resultado_consumo = vc_resultado_consumo;
    }
    
    
}
