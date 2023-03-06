package ahorrosbienestar.sib.std.catalogo_contratos.conf_contratos.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CONF_CONTRATO")
public class ConfContrato {

	@Column(name = "PK_ID_CONF_CONTRATO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cat_conf_contrato_seq")
	@SequenceGenerator(name = "cat_conf_contrato_seq", sequenceName = "cat_conf_contrato_seq", initialValue = 1, allocationSize = 1)
	private int pk_id_contrato;

	@Column(name = "VC_DESCRIPCION")
	private String vc_descripcion;

	@Column(name = "DT_FECHA_PUBLICACION")
	private Date dt_fecha_publicacion;

	@Column(name = "DT_FECHA_MODIFICAION")
	private Date dt_fecha_modificaion;

	@Column(name = "VC_TIPO_FORMATO")
	private String vc_tipo_formato;

	@Column(name = "VC_RUTA")
	private String vc_ruta;

	@Column(name = "VC_VERSION")
	private String vc_version;

	@Column(name = "VC_NOMBRE_ARCHIVO")
	private String vc_nombre_archivo;

	@Column(name = "I_ESTATUS")
	private int i_id_estatus;

	public int getPk_id_contrato() {
		return pk_id_contrato;
	}

	public void setPk_id_contrato(int pk_id_contrato) {
		this.pk_id_contrato = pk_id_contrato;
	}

	public String getVc_descripcion() {
		return vc_descripcion;
	}

	public void setVc_descripcion(String vc_descripcion) {
		this.vc_descripcion = vc_descripcion;
	}

	public Date getDt_fecha_publicacion() {
		return dt_fecha_publicacion;
	}

	public void setDt_fecha_publicacion(Date dt_fecha_publicacion) {
		this.dt_fecha_publicacion = dt_fecha_publicacion;
	}

	public Date getDt_fecha_modificaion() {
		return dt_fecha_modificaion;
	}

	public void setDt_fecha_modificaion(Date dt_fecha_modificaion) {
		this.dt_fecha_modificaion = dt_fecha_modificaion;
	}

	public String getVc_tipo_formato() {
		return vc_tipo_formato;
	}

	public void setVc_tipo_formato(String vc_tipo_formato) {
		this.vc_tipo_formato = vc_tipo_formato;
	}

	public String getVc_ruta() {
		return vc_ruta;
	}

	public void setVc_ruta(String vc_ruta) {
		this.vc_ruta = vc_ruta;
	}

	public String getVc_version() {
		return vc_version;
	}

	public void setVc_version(String vc_version) {
		this.vc_version = vc_version;
	}

	public String getVc_nombre_archivo() {
		return vc_nombre_archivo;
	}

	public void setVc_nombre_archivo(String vc_nombre_archivo) {
		this.vc_nombre_archivo = vc_nombre_archivo;
	}

	public int getI_id_estatus() {
		return i_id_estatus;
	}

	public void setI_id_estatus(int i_id_estatus) {
		this.i_id_estatus = i_id_estatus;
	}

}
