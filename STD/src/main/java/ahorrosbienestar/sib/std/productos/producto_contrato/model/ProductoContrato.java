package ahorrosbienestar.sib.std.productos.producto_contrato.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class ProductoContrato {

	@Column(name = "PK_ID_PRODUCTO")
	@Id
	private int pk_id_producto;

	@Column(name = "I_NO_PRODUCTO", columnDefinition = "INT")
	private int i_no_producto;

	@Column(name = "VC_NOMBRE_PRODUCTO", columnDefinition = "VARCHAR(45)")
	private String vc_nombre_producto;

	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;

	@Column(name = "I_CLASIFICACION", columnDefinition = "TINYINT(1)")
	private int i_clasificacion;

	@Column(name = "I_TIPO_PRODUCTO", columnDefinition = "TINYINT(1)")
	private int i_tipo_producto;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_OPERACION")
	private LocalDate dt_fecha_operacion;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_CANCELACION")
	private LocalDate dt_fecha_cancelacion;

	@Column(name = "I_USUARIOID_REGISTRA", columnDefinition = "INT")
	private int i_usuarioid_registra;

	@Column(name = "VC_CONTRATO")
	private String vc_contrato;
	
	@Column(name = "VC_IDENTIFICADOR_KYC", columnDefinition = "varchar(40)")
	private String vc_identificador_kyc;
	
	@Column(name = "VC_DESCRIPCION_PRODUCTO", columnDefinition = "varchar(30)")
	private String vc_descripcion_producto;

	public int getPk_id_producto() {
		return pk_id_producto;
	}

	public void setPk_id_producto(int pk_id_producto) {
		this.pk_id_producto = pk_id_producto;
	}

	public int getI_no_producto() {
		return i_no_producto;
	}

	public void setI_no_producto(int i_no_producto) {
		this.i_no_producto = i_no_producto;
	}

	public String getVc_nombre_producto() {
		return vc_nombre_producto;
	}

	public void setVc_nombre_producto(String vc_nombre_producto) {
		this.vc_nombre_producto = vc_nombre_producto;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public int getI_clasificacion() {
		return i_clasificacion;
	}

	public void setI_clasificacion(int i_clasificacion) {
		this.i_clasificacion = i_clasificacion;
	}

	public int getI_tipo_producto() {
		return i_tipo_producto;
	}

	public void setI_tipo_producto(int i_tipo_producto) {
		this.i_tipo_producto = i_tipo_producto;
	}

	public LocalDate getDt_fecha_operacion() {
		return dt_fecha_operacion;
	}

	public void setDt_fecha_operacion(LocalDate dt_fecha_operacion) {
		this.dt_fecha_operacion = dt_fecha_operacion;
	}

	public LocalDate getDt_fecha_cancelacion() {
		return dt_fecha_cancelacion;
	}

	public void setDt_fecha_cancelacion(LocalDate dt_fecha_cancelacion) {
		this.dt_fecha_cancelacion = dt_fecha_cancelacion;
	}

	public int getI_usuarioid_registra() {
		return i_usuarioid_registra;
	}

	public void setI_usuarioid_registra(int i_usuarioid_registra) {
		this.i_usuarioid_registra = i_usuarioid_registra;
	}

	public String getVc_contrato() {
		return vc_contrato;
	}

	public void setVc_contrato(String vc_contrato) {
		this.vc_contrato = vc_contrato;
	}

	public String getVc_identificador_kyc() {
		return vc_identificador_kyc;
	}

	public void setVc_identificador_kyc(String vc_identificador_kyc) {
		this.vc_identificador_kyc = vc_identificador_kyc;
	}

	public String getVc_descripcion_producto() {
		return vc_descripcion_producto;
	}

	public void setVc_descripcion_producto(String vc_descripcion_producto) {
		this.vc_descripcion_producto = vc_descripcion_producto;
	}
	
	


}
