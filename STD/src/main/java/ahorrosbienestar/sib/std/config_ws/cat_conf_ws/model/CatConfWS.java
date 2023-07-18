package ahorrosbienestar.sib.std.config_ws.cat_conf_ws.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
 
import org.springframework.format.annotation.DateTimeFormat;
 
import com.fasterxml.jackson.annotation.JsonFormat;
 
@Entity
@Table(name = "TBL_CAT_CONF_WS")
public class CatConfWS {
    
    @Column(name = "PK_ID_CAT_CONF_WS")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_conf_ws_seq")
    @SequenceGenerator(name = "cat_conf_ws_seq", sequenceName = "cat_conf_ws_seq", initialValue = 1, allocationSize = 1)
    private int pk_id_cat_conf_ws_seq;
    
    @Column(name = "VC_LLAVE_PARAMETRO", columnDefinition = "VARCHAR(60)")
    private String vc_llave_parametro;
    
    @Column(name = "VC_DESCRIPCION", columnDefinition = "VARCHAR(255)")
    private String vc_descripcion;
    
    @Column(name = "VC_VALOR_PARAMETRO1", columnDefinition = "VARCHAR(150)")
    private String vc_valor_parametro1;
    
    @Column(name = "VC_VALOR_PARAMETRO2", columnDefinition = "VARCHAR(200)")
    private String vc_valor_parametro2;
    
    @Column(name = "VC_VALOR_PARAMETRO3", columnDefinition = "VARCHAR(200)")
    private String vc_valor_parametro3;
    
    @Column(name = "VC_RUTA", columnDefinition = "VARCHAR(150)")
    private String vc_ruta;
    
    //1 = ACTIVO, 2 = INACTIVO
    @Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
    private int i_estatus;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DT_FECHA_REGISTRO")
    private LocalDate dt_fecha_registro;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "DT_FECHA_CANCELACION")
    private LocalDate dt_fecha_cancelacion;
    
    @Column(name = "VC_METODO_ENVIO", columnDefinition = "VARCHAR(45)")
    private String vc_metodo_envio;
    
    @Column(name = "VC_TIPO_CONTENIDO", columnDefinition = "VARCHAR(150)")
    private String vc_tipo_contenido;
 
    public int getPk_id_cat_conf_ws_seq() {
        return pk_id_cat_conf_ws_seq;
    }
 
    public void setPk_id_cat_conf_ws_seq(int pk_id_cat_conf_ws_seq) {
        this.pk_id_cat_conf_ws_seq = pk_id_cat_conf_ws_seq;
    }
 
    public String getVc_llave_parametro() {
        return vc_llave_parametro;
    }
 
    public void setVc_llave_parametro(String vc_llave_parametro) {
        this.vc_llave_parametro = vc_llave_parametro;
    }
 
    public String getVc_descripcion() {
        return vc_descripcion;
    }
 
    public void setVc_descripcion(String vc_descripcion) {
        this.vc_descripcion = vc_descripcion;
    }
 
    public String getVc_valor_parametro1() {
        return vc_valor_parametro1;
    }
 
    public void setVc_valor_parametro1(String vc_valor_parametro1) {
        this.vc_valor_parametro1 = vc_valor_parametro1;
    }
 
    public String getVc_valor_parametro2() {
        return vc_valor_parametro2;
    }
 
    public void setVc_valor_parametro2(String vc_valor_parametro2) {
        this.vc_valor_parametro2 = vc_valor_parametro2;
    }
 
    public String getVc_ruta() {
        return vc_ruta;
    }
 
    public void setVc_ruta(String vc_ruta) {
        this.vc_ruta = vc_ruta;
    }
 
    public int getI_estatus() {
        return i_estatus;
    }
 
    public void setI_estatus(int i_estatus) {
        this.i_estatus = i_estatus;
    }
 
    public LocalDate getDt_fecha_registro() {
        return dt_fecha_registro;
    }
 
    public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
        this.dt_fecha_registro = dt_fecha_registro;
    }
 
    public LocalDate getDt_fecha_cancelacion() {
        return dt_fecha_cancelacion;
    }
 
    public void setDt_fecha_cancelacion(LocalDate dt_fecha_cancelacion) {
        this.dt_fecha_cancelacion = dt_fecha_cancelacion;
    }
 
    public String getVc_metodo_envio() {
        return vc_metodo_envio;
    }
 
    public void setVc_metodo_envio(String vc_metodo_envio) {
        this.vc_metodo_envio = vc_metodo_envio;
    }
 
    public String getVc_tipo_contenido() {
        return vc_tipo_contenido;
    }
 
    public void setVc_tipo_contenido(String vc_tipo_contenido) {
        this.vc_tipo_contenido = vc_tipo_contenido;
    }
 
    public String getVc_valor_parametro3() {
        return vc_valor_parametro3;
    }
 
    public void setVc_valor_parametro3(String vc_valor_parametro3) {
        this.vc_valor_parametro3 = vc_valor_parametro3;
    }
    
 
}
