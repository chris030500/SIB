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
@Table(name="TBL_CAT_MENUS")
public class Menus {
	
	@Column(name = "PK_ID_MENU")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="tbl_menu_seq")
	@SequenceGenerator(name = "tbl_menu_seq", sequenceName = "tbl_menu_seq", initialValue = 1, allocationSize=1)
	private int pk_id_menu;
	
	@Column(name = "VC_NOMBRE_MENU", columnDefinition = "VARCHAR(200)")
	private String vc_nombre_usuario;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	public int getPk_id_menu() {
		return pk_id_menu;
	}

	public void setPk_id_menu(int pk_id_menu) {
		this.pk_id_menu = pk_id_menu;
	}

	public String getVc_nombre_usuario() {
		return vc_nombre_usuario;
	}

	public void setVc_nombre_usuario(String vc_nombre_usuario) {
		this.vc_nombre_usuario = vc_nombre_usuario;
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

}
