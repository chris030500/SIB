package ahorrosbienestar.sib.std.rptNoConformidades.model;

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
@Table(name = "TBL_PARAMS_BUSQUEDA")
public class BusquedaCSO {
	@Column(name = "PK_ID_BUSQUEDA")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="busqueda_seq")
	@SequenceGenerator(name = "busqueda_seq", sequenceName = "busqueda_seq", initialValue = 1, allocationSize=1)
	private int pk_id_busqueda;
	
	@Column(name = "I_USUARIO_REGISTRO", columnDefinition = "INT")
	private int i_usuario_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_INICIO")
	private LocalDate dt_fecha_inicio;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_FIN")
	private LocalDate dt_fecha_fin;
	
	@Column(name = "I_TIPO_OFICIO", columnDefinition = "INT")
	private int i_tipo_oficio;
	
	@Column(name = "I_ORGANO_EMITE", columnDefinition = "INT")
	private int i_organo_emite;
	
	@Column(name = "I_ESTATUS", columnDefinition = "INT")
	private int i_estatus;
	
	@Column(name = "I_TODOS", columnDefinition = "INT")
	private int i_todos;
	
	@Column(name = "I_FOLIO", columnDefinition = "INT")
	private int i_folio;

	public int getPk_id_busqueda() {
		return pk_id_busqueda;
	}

	public void setPk_id_busqueda(int pk_id_busqueda) {
		this.pk_id_busqueda = pk_id_busqueda;
	}

	public int getI_usuario_registro() {
		return i_usuario_registro;
	}

	public void setI_usuario_registro(int i_usuario_registro) {
		this.i_usuario_registro = i_usuario_registro;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public LocalDate getDt_fecha_inicio() {
		return dt_fecha_inicio;
	}

	public void setDt_fecha_inicio(LocalDate dt_fecha_inicio) {
		this.dt_fecha_inicio = dt_fecha_inicio;
	}

	public LocalDate getDt_fecha_fin() {
		return dt_fecha_fin;
	}

	public void setDt_fecha_fin(LocalDate dt_fecha_fin) {
		this.dt_fecha_fin = dt_fecha_fin;
	}

	public int getI_tipo_oficio() {
		return i_tipo_oficio;
	}

	public void setI_tipo_oficio(int i_tipo_oficio) {
		this.i_tipo_oficio = i_tipo_oficio;
	}

	public int getI_organo_emite() {
		return i_organo_emite;
	}

	public void setI_organo_emite(int i_organo_emite) {
		this.i_organo_emite = i_organo_emite;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public int getI_todos() {
		return i_todos;
	}

	public void setI_todos(int i_todos) {
		this.i_todos = i_todos;
	}

	public int getI_folio() {
		return i_folio;
	}

	public void setI_folio(int i_folio) {
		this.i_folio = i_folio;
	}
	
}
