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
@Table(name="TBL_REL_ROL_OPCIONES")
public class RelRolOpciones {
	
	@Column(name = "PK_ID_ROL_OPCIONES")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="tbl_rol_opciones_seq")
	@SequenceGenerator(name = "tbl_rol_opciones_seq", sequenceName = "tbl_rol_opciones_seq", initialValue = 1, allocationSize=1)
	private int pk_id_rol_opciones;
	
	@Column(name = "FK_ROL", columnDefinition = "TINYINT(1)")
	private int fk_rol;
	
	@Column(name = "FK_OPCION", columnDefinition = "TINYINT(1)")
	private int fk_opcion;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;
	
	@Column(name = "I_USUARIOID_REGISTRA", columnDefinition = "INT")
	private int i_usuarioid_registra;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@Column(name = "I_USUARIOID_MODIFICO", columnDefinition = "INT")
	private int i_usuarioid_modifico;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_MODIFICO")
	private LocalDate dt_fecha_modifico;
	
	@Column(name = "I_USUARIOID_BAJA", columnDefinition = "INT")
	private int i_usuarioid_baja;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_BAJA")
	private LocalDate dt_fecha_baja;

	public int getPk_id_rol_opciones() {
		return pk_id_rol_opciones;
	}

	public void setPk_id_rol_opciones(int pk_id_rol_opciones) {
		this.pk_id_rol_opciones = pk_id_rol_opciones;
	}

	public int getFk_rol() {
		return fk_rol;
	}

	public void setFk_rol(int fk_rol) {
		this.fk_rol = fk_rol;
	}

	public int getFk_opcion() {
		return fk_opcion;
	}

	public void setFk_opcion(int fk_opcion) {
		this.fk_opcion = fk_opcion;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public int getI_usuarioid_registra() {
		return i_usuarioid_registra;
	}

	public void setI_usuarioid_registra(int i_usuarioid_registra) {
		this.i_usuarioid_registra = i_usuarioid_registra;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public int getI_usuarioid_modifico() {
		return i_usuarioid_modifico;
	}

	public void setI_usuarioid_modifico(int i_usuarioid_modifico) {
		this.i_usuarioid_modifico = i_usuarioid_modifico;
	}

	public LocalDate getDt_fecha_modifico() {
		return dt_fecha_modifico;
	}

	public void setDt_fecha_modifico(LocalDate dt_fecha_modifico) {
		this.dt_fecha_modifico = dt_fecha_modifico;
	}

	public int getI_usuarioid_baja() {
		return i_usuarioid_baja;
	}

	public void setI_usuarioid_baja(int i_usuarioid_baja) {
		this.i_usuarioid_baja = i_usuarioid_baja;
	}

	public LocalDate getDt_fecha_baja() {
		return dt_fecha_baja;
	}

	public void setDt_fecha_baja(LocalDate dt_fecha_baja) {
		this.dt_fecha_baja = dt_fecha_baja;
	}
	
	
}

