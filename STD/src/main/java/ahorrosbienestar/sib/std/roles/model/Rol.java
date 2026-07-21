package ahorrosbienestar.sib.std.roles.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "TBL_CAT_ROL")
public class Rol {
	
	@Column(name = "PK_ID_ROL")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tbl_rol_seq")
	@SequenceGenerator(name = "tbl_rol_seq", sequenceName = "tbl_rol_seq", initialValue = 2, allocationSize=1)
	private int pk_id_rol;
	
	@Column(name = "VC_NOMBRE_ROL", columnDefinition = "VARCHAR(100)")
	private String vc_nombre_rol;
	
	@Column(name = "VC_DESCRIPCION_ROL", columnDefinition = "VARCHAR(300)")
	private String vc_descripcion_rol;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "VC_FECHA_BAJA")
	private LocalDate vc_fecha_baja;

	public int getPk_id_rol() {
		return pk_id_rol;
	}

	public void setPk_id_rol(int pk_id_roll) {
		this.pk_id_rol = pk_id_roll;
	}

	public String getVc_nombre_rol() {
		return vc_nombre_rol;
	}

	public void setVc_nombre_rol(String vc_nombre_rol) {
		this.vc_nombre_rol = vc_nombre_rol;
	}

	public String getVc_descripcion_rol() {
		return vc_descripcion_rol;
	}

	public void setVc_descripcion_rol(String vc_descripcion_rol) {
		this.vc_descripcion_rol = vc_descripcion_rol;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}

	public LocalDate getVc_fecha_baja() {
		return vc_fecha_baja;
	}

	public void setVc_fecha_baja(LocalDate vc_fecha_baja) {
		this.vc_fecha_baja = vc_fecha_baja;
	}
	
	
	
}
