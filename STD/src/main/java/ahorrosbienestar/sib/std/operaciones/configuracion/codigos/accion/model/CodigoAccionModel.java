package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.accion.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_CODIGOS_ACCION_OA")
public class CodigoAccionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_CODIGO_ACCION")
	private int pk_id_codigo_accion;

	@Column(name = "VC_CODIGO")
	private String vc_codigo;

	@Column(name = "VC_DESCRIPCION")
	private String vc_descripcion;

	@Column(name = "I_ESTATUS")
	private int i_estatus;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_MODIFICACION")
	private LocalDate dt_fecha_modificacion;

	@OneToOne
	@JoinColumn(name = "I_USUARIO_REGISTRO")
	private Usuario i_usuario_registro;

	@OneToOne
	@JoinColumn(name = "I_USUARIO_MODIFICACION")
	private Usuario i_usuario_modificacion;

	public int getPk_id_codigo_accion() {
		return pk_id_codigo_accion;
	}

	public void setPk_id_codigo_accion(int pk_id_codigo_accion) {
		this.pk_id_codigo_accion = pk_id_codigo_accion;
	}

	public String getVc_codigo() {
		return vc_codigo;
	}

	public void setVc_codigo(String vc_codigo) {
		this.vc_codigo = vc_codigo;
	}

	public String getVc_descripcion() {
		return vc_descripcion;
	}

	public void setVc_descripcion(String vc_descripcion) {
		this.vc_descripcion = vc_descripcion;
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

	public LocalDate getDt_fecha_modificacion() {
		return dt_fecha_modificacion;
	}

	public void setDt_fecha_modificacion(LocalDate dt_fecha_modificacion) {
		this.dt_fecha_modificacion = dt_fecha_modificacion;
	}

	public Usuario getI_usuario_registro() {
		return i_usuario_registro;
	}

	public void setI_usuario_registro(Usuario i_usuario_registro) {
		this.i_usuario_registro = i_usuario_registro;
	}

	public Usuario getI_usuario_modificacion() {
		return i_usuario_modificacion;
	}

	public void setI_usuario_modificacion(Usuario i_usuario_modificacion) {
		this.i_usuario_modificacion = i_usuario_modificacion;
	}

}
