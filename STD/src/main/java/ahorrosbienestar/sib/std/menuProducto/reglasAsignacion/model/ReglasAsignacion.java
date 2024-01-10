package ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;

@Entity
@Table(name="TBL_REGLAS_ASIGNACION")
public class ReglasAsignacion {
	
	@Column(name = "PK_ID_REGLA")
	@Id
	private int pk_id_regla;
	
	@Column(name = "VC_NOMBRE_REGLA", columnDefinition = "VARCHAR(100)")
	private String vc_nombre_regla;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1) DEFAULT 1")
	private int i_estatus;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_MOD", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_mod;
	
	@Column(name = "I_USUARIO_MOD", columnDefinition = "TINYINT(1) DEFAULT 0")
	private int i_usuario_mod;

	public int getPk_id_regla() {
		return pk_id_regla;
	}

	public void setPk_id_regla(int pk_id_regla) {
		this.pk_id_regla = pk_id_regla;
	}

	public String getVc_nombre_regla() {
		return vc_nombre_regla;
	}

	public void setVc_nombre_regla(String vc_nombre_regla) {
		this.vc_nombre_regla = vc_nombre_regla;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public LocalDate getDt_fecha_mod() {
		return dt_fecha_mod;
	}

	public void setDt_fecha_mod(LocalDate dt_fecha_mod) {
		this.dt_fecha_mod = dt_fecha_mod;
	}

	public int getI_usuario_mod() {
		return i_usuario_mod;
	}

	public void setI_usuario_mod(int i_usuario_mod) {
		this.i_usuario_mod = i_usuario_mod;
	}
	
	
}
