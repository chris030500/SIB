package ahorrosbienestar.sib.std.seguimientoFolio.model;

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
@Table(name="TBL_SEGUIMIENTO")
public class SeguimientoFolio {
	
	@Column(name = "PK_ID_SEGUIMIENTO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="tbl_seguimiento_seq")
	@SequenceGenerator(name = "tbl_seguimiento_seq", sequenceName = "tbl_seguimiento_seq", initialValue = 1, allocationSize=1)
	private int pk_id_seguimiento;
	
	@Column(name = "I_PLANACCION", columnDefinition = "INT")
	private int i_planaccion;
	
	@Column(name = "VC_DESC_SEGUIMIENTO", columnDefinition = "LONGTEXT")
	private String vc_desc_seguimiento;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_MOD")
	private LocalDate dt_fecha_mod;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_ELI")
	private LocalDate dt_fecha_eli;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_TERMINO")
	private LocalDate dt_fecha_termino;
	
	@Column(name = "I_CONFIRMACION_RESPONSABLE", columnDefinition = "INT")
	private int i_confirmacion_responsable;
	
	@Column(name = "I_USUARIO_REGISTRO", columnDefinition = "INT")
	private int i_usuario_registro;
	
	@Column(name = "I_USUARIO_MOD", columnDefinition = "INT")
	private int i_usuario_mod;
		
	@Column(name = "I_USUARIO_ELIMINO", columnDefinition = "INT")
	private int i_usuario_elimino;

	public int getPk_id_seguimiento() {
		return pk_id_seguimiento;
	}

	public void setPk_id_seguimiento(int pk_id_seguimiento) {
		this.pk_id_seguimiento = pk_id_seguimiento;
	}

	public int getI_planaccion() {
		return i_planaccion;
	}

	public void setI_planaccion(int i_planaccion) {
		this.i_planaccion = i_planaccion;
	}

	public String getVc_desc_seguimiento() {
		return vc_desc_seguimiento;
	}

	public void setVc_desc_seguimiento(String vc_desc_seguimiento) {
		this.vc_desc_seguimiento = vc_desc_seguimiento;
	}

	public LocalDate getDt_fecha_mod() {
		return dt_fecha_mod;
	}

	public void setDt_fecha_mod(LocalDate dt_fecha_mod) {
		this.dt_fecha_mod = dt_fecha_mod;
	}

	public LocalDate getDt_fecha_eli() {
		return dt_fecha_eli;
	}

	public void setDt_fecha_eli(LocalDate dt_fecha_eli) {
		this.dt_fecha_eli = dt_fecha_eli;
	}

	public LocalDate getDt_fecha_termino() {
		return dt_fecha_termino;
	}

	public void setDt_fecha_termino(LocalDate dt_fecha_termino) {
		this.dt_fecha_termino = dt_fecha_termino;
	}

	public int getI_confirmacion_responsable() {
		return i_confirmacion_responsable;
	}

	public void setI_confirmacion_responsable(int i_confirmacion_responsable) {
		this.i_confirmacion_responsable = i_confirmacion_responsable;
	}

	public int getI_usuario_registro() {
		return i_usuario_registro;
	}

	public void setI_usuario_registro(int i_usuario_registro) {
		this.i_usuario_registro = i_usuario_registro;
	}

	public int getI_usuario_mod() {
		return i_usuario_mod;
	}

	public void setI_usuario_mod(int i_usuario_mod) {
		this.i_usuario_mod = i_usuario_mod;
	}

	public int getI_usuario_elimino() {
		return i_usuario_elimino;
	}

	public void setI_usuario_elimino(int i_usuario_elimino) {
		this.i_usuario_elimino = i_usuario_elimino;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}
	
	
}
