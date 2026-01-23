package ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.model;

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
import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.accion.model.CodigoAccionModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.resultado.model.CodigoResultadoModel;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_RELACION_CODIGOS_OA")
public class RelacionCodigosModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_RELACION_CODIGO")
	private int pk_id_relacion_codigo;

	@OneToOne
	@JoinColumn(name = "FK_ID_CODIGO_ACCION")
	private CodigoAccionModel codigoAccionModel;

	@OneToOne
	@JoinColumn(name = "FK_ID_CODIGO_RESULTADO")
	private CodigoResultadoModel codigoResultadoModel;

	@OneToOne
	@JoinColumn(name = "I_ACCION_REALIZAR")
	private Catalogo accionRealizar;

	@OneToOne
	@JoinColumn(name = "I_ULTIMA_GESTION_VIGENTE")
	private Catalogo ultimaGestion;

	@OneToOne
	@JoinColumn(name = "I_BLOQUEO_LISTAS_NEGRAS")
	private Catalogo bloqueoListas;

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
	@JoinColumn(name = "I_TIPO_GESTION")
	private Catalogo i_tipo_gestion;

	@OneToOne
	@JoinColumn(name = "I_USUARIO_REGISTRO")
	private Usuario i_usuario_registro;

	@OneToOne
	@JoinColumn(name = "I_USUARIO_MODIFICACION")
	private Usuario i_usuario_modificacion;

	public int getPk_id_relacion_codigo() {
		return pk_id_relacion_codigo;
	}

	public void setPk_id_relacion_codigo(int pk_id_relacion_codigo) {
		this.pk_id_relacion_codigo = pk_id_relacion_codigo;
	}

	public CodigoAccionModel getCodigoAccionModel() {
		return codigoAccionModel;
	}

	public void setCodigoAccionModel(CodigoAccionModel codigoAccionModel) {
		this.codigoAccionModel = codigoAccionModel;
	}

	public CodigoResultadoModel getCodigoResultadoModel() {
		return codigoResultadoModel;
	}

	public void setCodigoResultadoModel(CodigoResultadoModel codigoResultadoModel) {
		this.codigoResultadoModel = codigoResultadoModel;
	}

	public Catalogo getAccionRealizar() {
		return accionRealizar;
	}

	public void setAccionRealizar(Catalogo accionRealizar) {
		this.accionRealizar = accionRealizar;
	}

	public Catalogo getUltimaGestion() {
		return ultimaGestion;
	}

	public void setUltimaGestion(Catalogo ultimaGestion) {
		this.ultimaGestion = ultimaGestion;
	}

	public Catalogo getBloqueoListas() {
		return bloqueoListas;
	}

	public void setBloqueoListas(Catalogo bloqueoListas) {
		this.bloqueoListas = bloqueoListas;
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

	public Catalogo getI_tipo_gestion() {
		return i_tipo_gestion;
	}

	public void setI_tipo_gestion(Catalogo i_tipo_gestion) {
		this.i_tipo_gestion = i_tipo_gestion;
	}

}

