package ahorrosbienestar.sib.std.credito.procesosCampanias.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProcesosCampaniasDTO {
	
	private int pk_id_proceso_campania;
    private LocalDateTime dt_fecha_extraccion;
    private String vc_nombre_usuario;
    private String vc_tipo_campania;
    private LocalDateTime dt_fecha_inicio_vigencia;
    private LocalDateTime dt_fecha_fin_vigencia;
    private String vc_estatus;
    
    public ProcesosCampaniasDTO(int pk_id_proceso_campania, LocalDateTime dt_fecha_extraccion, String vc_nombre_usuario, String vc_tipo_campania, LocalDateTime dt_fecha_inicio_vigencia, LocalDateTime dt_fecha_fin_vigencia,String vc_estatus) {
        this.pk_id_proceso_campania = pk_id_proceso_campania;
        this.dt_fecha_extraccion = dt_fecha_extraccion;
        this.vc_nombre_usuario = vc_nombre_usuario;
        this.vc_tipo_campania = vc_tipo_campania;
        this.dt_fecha_inicio_vigencia = dt_fecha_inicio_vigencia;
        this.dt_fecha_fin_vigencia = dt_fecha_fin_vigencia;
        this.vc_estatus = vc_estatus;
    }
    
	public int getPk_id_proceso_campania() {
		return pk_id_proceso_campania;
	}
	public void setPk_id_proceso_campania(int pk_id_proceso_campania) {
		this.pk_id_proceso_campania = pk_id_proceso_campania;
	}
	public LocalDateTime getDt_fecha_extraccion() {
		return dt_fecha_extraccion;
	}
	public void setDt_fecha_extraccion(LocalDateTime dt_fecha_extraccion) {
		this.dt_fecha_extraccion = dt_fecha_extraccion;
	}
	public String getVc_nombre_usuario() {
		return vc_nombre_usuario;
	}
	public void setVc_nombre_usuario(String vc_nombre_usuario) {
		this.vc_nombre_usuario = vc_nombre_usuario;
	}
	public String getVc_tipo_campania() {
		return vc_tipo_campania;
	}
	public void setVc_tipo_campania(String vc_tipo_campania) {
		this.vc_tipo_campania = vc_tipo_campania;
	}
	public LocalDateTime getDt_fecha_inicio_vigencia() {
		return dt_fecha_inicio_vigencia;
	}
	public void setDt_fecha_inicio_vigencia(LocalDateTime dt_fecha_inicio_vigencia) {
		this.dt_fecha_inicio_vigencia = dt_fecha_inicio_vigencia;
	}
	public LocalDateTime getDt_fecha_fin_vigencia() {
		return dt_fecha_fin_vigencia;
	}
	public void setDt_fecha_fin_vigencia(LocalDateTime dt_fecha_fin_vigencia) {
		this.dt_fecha_fin_vigencia = dt_fecha_fin_vigencia;
	}
	public String getVc_estatus() {
		return vc_estatus;
	}
	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}

}
