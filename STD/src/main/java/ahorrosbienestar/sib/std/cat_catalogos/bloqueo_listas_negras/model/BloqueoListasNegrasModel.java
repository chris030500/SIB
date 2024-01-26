package ahorrosbienestar.sib.std.cat_catalogos.bloqueo_listas_negras.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_CAT_BLOQUEO_LISTAS_NEGRAS_OA")
public class BloqueoListasNegrasModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_BLOQUEO_LISTAS_NEGRAS")
	private int pk_id_bloqueo_listas_negras;

	@Column(name = "VC_NOMBRE")
	private String vc_nombre;

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

	public int getPk_id_bloqueo_listas_negras() {
		return pk_id_bloqueo_listas_negras;
	}

	public void setPk_id_bloqueo_listas_negras(int pk_id_bloqueo_listas_negras) {
		this.pk_id_bloqueo_listas_negras = pk_id_bloqueo_listas_negras;
	}

	public String getVc_nombre() {
		return vc_nombre;
	}

	public void setVc_nombre(String vc_nombre) {
		this.vc_nombre = vc_nombre;
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

}
