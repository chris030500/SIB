package ahorrosbienestar.sib.std.opcionMenu.model;

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
@Table(name="TBL_OPCIONES_ROL")
public class OpcionesMenu {
	
	@Column(name = "PK_ID_OPCION")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="tbl_opciones_seq")
	@SequenceGenerator(name = "tbl_opciones_seq", sequenceName = "tbl_opciones_seq", initialValue = 1, allocationSize=1)
	private int pk_id_opcion;
	
	@Column(name = "I_MENU", columnDefinition = "TINYINT(1)")
	private int i_menu;
	
	@Column(name = "VC_NOMBRE_PANTALLA", columnDefinition = "VARCHAR(200)")
	private String vc_nombre_pantalla;
	
	@Column(name = "VC_CONTROLLER", columnDefinition = "VARCHAR(200)")
	private String vc_controller;
	
	@Column(name = "I_NIVEL", columnDefinition = "TINYINT(1)")
	private int i_nivel;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_INGRESO")
	private LocalDate dt_fecha_ingreso;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_BAJA")
	private LocalDate dt_fecha_baja;
	
	public int getPk_id_opcion() {
		return pk_id_opcion;
	}

	public void setPk_id_opcion(int pk_id_opcion) {
		this.pk_id_opcion = pk_id_opcion;
	}

	public String getVc_nombre_pantalla() {
		return vc_nombre_pantalla;
	}

	public void setVc_nombre_pantalla(String vc_nombre_pantalla) {
		this.vc_nombre_pantalla = vc_nombre_pantalla;
	}

	public int getI_nivel() {
		return i_nivel;
	}

	public void setI_nivel(int i_nivel) {
		this.i_nivel = i_nivel;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public LocalDate getDt_fecha_ingreso() {
		return dt_fecha_ingreso;
	}

	public void setDt_fecha_ingreso(LocalDate dt_fecha_ingreso) {
		this.dt_fecha_ingreso = dt_fecha_ingreso;
	}

	public LocalDate getDt_fecha_baja() {
		return dt_fecha_baja;
	}

	public void setDt_fecha_baja(LocalDate dt_fecha_baja) {
		this.dt_fecha_baja = dt_fecha_baja;
	}

	public String getVc_controller() {
		return vc_controller;
	}

	public void setVc_controller(String vc_controller) {
		this.vc_controller = vc_controller;
	}
	
}
