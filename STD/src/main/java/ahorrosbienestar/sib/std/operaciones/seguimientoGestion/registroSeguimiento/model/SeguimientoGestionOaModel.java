package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.model;

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
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model.AsignacionOfertas;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.model.RelacionCodigosModel;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_SEGUIMIENTO_GESTION_OA")
public class SeguimientoGestionOaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_SEGUIMIENTO_GESTION")
	private int pk_id_seguimiento_gestion;

	@OneToOne
	@JoinColumn(name = "FK_ID_ASIGNACION_OFERTAS")
	private AsignacionOfertas asignacionOfertas;

	@OneToOne
	@JoinColumn(name = "FK_ID_RELACION_CODIGOS")
	private RelacionCodigosModel relacionCodigosModel;

	@Column(name = "VC_NO_REFERENCIA")
	private String vc_no_referencia;

	@Column(name = "VC_DESCRIPCION")
	private String vc_descripcion;

	@Column(name = "I_ESTATUS")
	private int i_estatus;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_CITA")
	private LocalDate dt_fecha_cita;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_GESTION_REALIZADA")
	private LocalDate dt_fecha_gestion_realizada;

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
	
	@Column(name = "I_TELEFONO")
	private String i_telefono;

	public int getPk_id_seguimiento_gestion() {
		return pk_id_seguimiento_gestion;
	}

	public void setPk_id_seguimiento_gestion(int pk_id_seguimiento_gestion) {
		this.pk_id_seguimiento_gestion = pk_id_seguimiento_gestion;
	}

	public AsignacionOfertas getAsignacionOfertas() {
		return asignacionOfertas;
	}

	public void setAsignacionOfertas(AsignacionOfertas asignacionOfertas) {
		this.asignacionOfertas = asignacionOfertas;
	}

	public RelacionCodigosModel getRelacionCodigosModel() {
		return relacionCodigosModel;
	}

	public void setRelacionCodigosModel(RelacionCodigosModel relacionCodigosModel) {
		this.relacionCodigosModel = relacionCodigosModel;
	}

	public String getVc_no_referencia() {
		return vc_no_referencia;
	}

	public void setVc_no_referencia(String vc_no_referencia) {
		this.vc_no_referencia = vc_no_referencia;
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

	public LocalDate getDt_fecha_cita() {
		return dt_fecha_cita;
	}

	public void setDt_fecha_cita(LocalDate dt_fecha_cita) {
		this.dt_fecha_cita = dt_fecha_cita;
	}

	public LocalDate getDt_fecha_gestion_realizada() {
		return dt_fecha_gestion_realizada;
	}

	public void setDt_fecha_gestion_realizada(LocalDate dt_fecha_gestion_realizada) {
		this.dt_fecha_gestion_realizada = dt_fecha_gestion_realizada;
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

	public String getI_telefono() {
		return i_telefono;
	}

	public void setI_telefono(String i_telefono) {
		this.i_telefono = i_telefono;
	}
}
