package ahorrosbienestar.sib.std.cobranza.procesoCarga.bitacora.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class ProcesoCargaBitacoraModel {
	
	@Id
	@Column(name = "PK_ID_PROCESO")
	private int pk_id_proceso;
	
	@Column(name = "VC_NOMBRE")
	private String vc_nombre;
	
	@Column(name = "I_CANTIDAD_REGISTRO")
	private int i_cantidad_registro;
	
	@Column(name = "I_TOTAL_MONTO")
	private int i_total_monto;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_CARGA")
	private LocalDate dt_fecha_carga;
	
	@Column(name = "VC_NOMBRE_USUARIO")
	private String vc_nombre_usuario;

	public int getPk_id_proceso() {
		return pk_id_proceso;
	}

	public void setPk_id_proceso(int pk_id_proceso) {
		this.pk_id_proceso = pk_id_proceso;
	}

	public String getVc_nombre() {
		return vc_nombre;
	}

	public void setVc_nombre(String vc_nombre) {
		this.vc_nombre = vc_nombre;
	}

	public int getI_cantidad_registro() {
		return i_cantidad_registro;
	}

	public void setI_cantidad_registro(int i_cantidad_registro) {
		this.i_cantidad_registro = i_cantidad_registro;
	}

	public int getI_total_monto() {
		return i_total_monto;
	}

	public void setI_total_monto(int i_total_monto) {
		this.i_total_monto = i_total_monto;
	}

	public LocalDate getDt_fecha_carga() {
		return dt_fecha_carga;
	}

	public void setDt_fecha_carga(LocalDate dt_fecha_carga) {
		this.dt_fecha_carga = dt_fecha_carga;
	}

	public String getVc_nombre_usuario() {
		return vc_nombre_usuario;
	}

	public void setVc_nombre_usuario(String vc_nombre_usuario) {
		this.vc_nombre_usuario = vc_nombre_usuario;
	}

}
