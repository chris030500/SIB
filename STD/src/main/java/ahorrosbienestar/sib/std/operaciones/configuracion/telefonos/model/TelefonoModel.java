package ahorrosbienestar.sib.std.operaciones.configuracion.telefonos.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import ahorrosbienestar.sib.std.sucursales.model.SucursalesModel;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_TELEFONOS_SUCURSAL_OA")
public class TelefonoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_TELEFONO")
	private int pk_id_telefono;

	@ManyToOne
	@JoinColumn(name = "FK_ID_SUCURSAL")
	private SucursalesModel fk_id_sucursal;

	@Column(name = "VC_TELEFONO")
	private String vc_telefono;

	@Column(name = "VC_EXTENSION")
	private String vc_extension;

	@Column(name = "VC_COMENTARIOS")
	private String vc_comentarios;

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

	public int getPk_id_telefono() {
		return pk_id_telefono;
	}

	public void setPk_id_telefono(int pk_id_telefono) {
		this.pk_id_telefono = pk_id_telefono;
	}

	public SucursalesModel getFk_id_sucursal() {
		return fk_id_sucursal;
	}

	public void setFk_id_sucursal(SucursalesModel fk_id_sucursal) {
		this.fk_id_sucursal = fk_id_sucursal;
	}

	public String getVc_telefono() {
		return vc_telefono;
	}

	public void setVc_telefono(String vc_telefono) {
		this.vc_telefono = vc_telefono;
	}

	public String getVc_extension() {
		return vc_extension;
	}

	public void setVc_extension(String vc_extension) {
		this.vc_extension = vc_extension;
	}

	public String getVc_comentarios() {
		return vc_comentarios;
	}

	public void setVc_comentarios(String vc_comentarios) {
		this.vc_comentarios = vc_comentarios;
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
