package ahorrosbienestar.sib.std.productos.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.catalogo_contratos.model.CatContrato;


@Entity
@Table(name = "TBL_CAT_PRODUCTO")
public class Producto {
	

	@Column(name = "PK_ID_PRODUCTO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="tbl_producto_seq")
	@SequenceGenerator(name = "tbl_producto_seq", sequenceName = "tbl_producto_seq", initialValue = 1, allocationSize=1)
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
	
	@Column(name = "VC_IDENTIFICADOR_KYC" , columnDefinition = "VARCHAR(40)")
	private String vc_identificador_kyc;
	
	@Column(name = "VC_DESCRIPCION_PRODUCTO" , columnDefinition = "VARCHAR(30)")
	private String vc_descripcion_producto;
	
	@OneToOne
	@JoinColumn(name = "FK_ID_CONTRATO")
	private CatContrato fk_id_contrato;

	public CatContrato getFk_id_contrato() {
		return fk_id_contrato;
	}

	public void setFk_id_contrato(CatContrato fk_id_contrato) {
		this.fk_id_contrato = fk_id_contrato;
	}

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
