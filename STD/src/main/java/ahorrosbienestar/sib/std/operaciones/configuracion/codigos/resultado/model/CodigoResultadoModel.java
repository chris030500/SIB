package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.resultado.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_CODIGOS_RESULTADO_OA")
public class CodigoResultadoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_CODIGO_RESULTADO")
	private int pk_id_codigo_resultado;

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
	private Usuario usuario;

	public int getPk_id_codigo_resultado() {
		return pk_id_codigo_resultado;
	}

	public void setPk_id_codigo_resultado(int pk_id_codigo_resultado) {
		this.pk_id_codigo_resultado = pk_id_codigo_resultado;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
