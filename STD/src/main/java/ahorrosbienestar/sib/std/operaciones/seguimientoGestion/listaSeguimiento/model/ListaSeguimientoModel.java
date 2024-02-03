package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(indexes = { @Index(name = "idx_nombreCompleto", columnList = "nombreCompleto") })
public class ListaSeguimientoModel {

	@Id
	@Column(name = "I_CLIENTEID")
	private int i_clienteid;

	@Column(name = "PK_ID_ASIGNACION_OA")
	private int pk_id_asignacion_oa;

	@Column(name = "nombreCompleto")
	private String nombreCompleto;

	@Column(name = "VC_CODIGO_OFERTA")
	private String vc_codigo_oferta;

	@Column(name = "VC_COD_REFERENCIA")
	private String vc_cod_referencia;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "FECHAINICIO")
	private LocalDate fechainicio;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "FECHAFIN")
	private LocalDate fechafin;

	@Column(name = "i_estatus")
	private int i_estatus;

	@Column(name = "V_VALOR")
	private String v_valor;

	@Column(name = "FK_ID_SUCURSAL")
	private int fk_id_sucursal;

	@Column(name = "VC_NOMBRESUCURSAL")
	private String vc_nombresucursal;

	public int getI_clienteid() {
		return i_clienteid;
	}

	public void setI_clienteid(int i_clienteid) {
		this.i_clienteid = i_clienteid;
	}

	public int getPk_id_asignacion_oa() {
		return pk_id_asignacion_oa;
	}

	public void setPk_id_asignacion_oa(int pk_id_asignacion_oa) {
		this.pk_id_asignacion_oa = pk_id_asignacion_oa;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getVc_codigo_oferta() {
		return vc_codigo_oferta;
	}

	public void setVc_codigo_oferta(String vc_codigo_oferta) {
		this.vc_codigo_oferta = vc_codigo_oferta;
	}

	public String getVc_cod_referencia() {
		return vc_cod_referencia;
	}

	public void setVc_cod_referencia(String vc_cod_referencia) {
		this.vc_cod_referencia = vc_cod_referencia;
	}

	public LocalDate getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(LocalDate fechainicio) {
		this.fechainicio = fechainicio;
	}

	public LocalDate getFechafin() {
		return fechafin;
	}

	public void setFechafin(LocalDate fechafin) {
		this.fechafin = fechafin;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public String getV_valor() {
		return v_valor;
	}

	public void setV_valor(String v_valor) {
		this.v_valor = v_valor;
	}

	public int getFk_id_sucursal() {
		return fk_id_sucursal;
	}

	public void setFk_id_sucursal(int fk_id_sucursal) {
		this.fk_id_sucursal = fk_id_sucursal;
	}

	public String getVc_nombresucursal() {
		return vc_nombresucursal;
	}

	public void setVc_nombresucursal(String vc_nombresucursal) {
		this.vc_nombresucursal = vc_nombresucursal;
	}

}
