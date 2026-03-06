package ahorrosbienestar.sib.std.config_ws.ws_respuesta_autenticacion.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
 
import com.fasterxml.jackson.databind.JsonNode;
 
import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.model.WSAutenticacionUser;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
 
@Entity
@Table(name = "TBL_WS_RESPUESTA_AUTENTICACION")
public class WSRespuestaAutenticacion {
    
 
    @Column(name = "PK_ID_RESPUESTA_AUTENTICACION")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_ws_respuesta_autenticacion_seq")
    @SequenceGenerator(name = "tbl_ws_respuesta_autenticacion_seq", sequenceName = "tbl_ws_respuesta_autenticacion_seq", initialValue = 1, allocationSize = 1)
    private int pk_id_respuesta_autenticacion_seq;
    
    @OneToOne
    @JoinColumn(name = "FK_ID_AUTENTICACION_USER")
    private WSAutenticacionUser fk_id_autenticacion_user;
    
    @Column(name = "VC_TOKEN", columnDefinition = "LONGTEXT")
    private String vc_token;
    
    @Column(name = "VC_JTI", columnDefinition = "VARCHAR(255)")
    private String vc_jti;
    
    @Column(name = "VC_SCOPE", columnDefinition = "VARCHAR(100)")
    private String vc_scope;
    
    @Column(name = "VC_REQUISITION_ID", columnDefinition = "MEDIUMTEXT")
    private String vc_requisition_id;
    
    @Column(name = "VC_JSON_RESPUESTA", columnDefinition = "LONGTEXT")
    private String  vc_json_respuesta;
 
    public int getPk_id_respuesta_autenticacion_seq() {
        return pk_id_respuesta_autenticacion_seq;
    }
 
    public void setPk_id_respuesta_autenticacion_seq(int pk_id_respuesta_autenticacion_seq) {
        this.pk_id_respuesta_autenticacion_seq = pk_id_respuesta_autenticacion_seq;
    }
 
    public WSAutenticacionUser getFk_id_autenticacion_user() {
        return fk_id_autenticacion_user;
    }
 
    public void setFk_id_autenticacion_user(WSAutenticacionUser fk_id_autenticacion_user) {
        this.fk_id_autenticacion_user = fk_id_autenticacion_user;
    }
 
    public String getVc_token() {
        return vc_token;
    }
 
    public void setVc_token(String vc_token) {
        this.vc_token = vc_token;
    }
 
    public String getVc_jti() {
        return vc_jti;
    }
 
    public void setVc_jti(String vc_jti) {
        this.vc_jti = vc_jti;
    }
 
    public String getVc_scope() {
        return vc_scope;
    }
 
    public void setVc_scope(String vc_scope) {
        this.vc_scope = vc_scope;
    }
 
    public String getVc_json_respuesta() {
        return vc_json_respuesta;
    }
 
    public void setVc_json_respuesta(String vc_json_respuesta) {
        this.vc_json_respuesta = vc_json_respuesta;
    }
 
    public String getVc_requisition_id() {
        return vc_requisition_id;
    }
 
    public void setVc_requisition_id(String vc_requisition_id) {
        this.vc_requisition_id = vc_requisition_id;
    }
    
    
 
}
