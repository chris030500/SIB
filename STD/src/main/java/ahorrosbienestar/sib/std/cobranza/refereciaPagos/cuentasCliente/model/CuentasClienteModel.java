package ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.model;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_CUENTAS_CLIENTE_RP")
public class CuentasClienteModel {

	@Id
	@Column(name = "PK_ID_CUENTA_RP")
	private int pk_id_cuenta_rp;

	@Column(name = "I_CLIENTE_ID")
	private BigInteger i_cliente_id;

	@Column(name = "VC_NOMBRE_CLIENTE")
	private String vc_nombre_cliente;

	@Column(name = "I_CREDITO_ID")
	private BigInteger i_credito_id;

	@Column(name = "VC_PRODUCTO_CREDITO")
	private String vc_producto_credito;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_MINISTRADO")
	private LocalDate dt_fecha_ministrado;

	@Column(name = "VC_ESTATUS_CREDITO")
	private String vc_estatus_credito;

	@Column(name = "I_REFERENCIA_CREDITO")
	private String i_referencia_credito;

	@Column(name = "I_CUENTA_AHO_ID")
	private BigInteger i_cuenta_aho_id;

	@Column(name = "I_REFERENCIA_CTA_PRINCIPAL_1")
	private String i_referencia_cta_principal_1;

	@Column(name = "I_REFERENCIA_CTA_PRINCIPAL_2")
	private String i_referencia_cta_principal_2;

	@Column(name = "I_INSTITUCION_ID")
	private int i_institucion_id;

	@Column(name = "VC_NOMBRE_INSTITUCION")
	private String vc_nombre_institucion;

	@Column(name = "VC_TIPO_REFERENCIA")
	private String vc_tipo_referencia;

	public int getPk_id_cuenta_rp() {
		return pk_id_cuenta_rp;
	}

	public void setPk_id_cuenta_rp(int pk_id_cuenta_rp) {
		this.pk_id_cuenta_rp = pk_id_cuenta_rp;
	}

	public BigInteger getI_cliente_id() {
		return i_cliente_id;
	}

	public void setI_cliente_id(BigInteger i_cliente_id) {
		this.i_cliente_id = i_cliente_id;
	}

	public String getVc_nombre_cliente() {
		return vc_nombre_cliente;
	}

	public void setVc_nombre_cliente(String vc_nombre_cliente) {
		this.vc_nombre_cliente = vc_nombre_cliente;
	}

	public BigInteger getI_credito_id() {
		return i_credito_id;
	}

	public void setI_credito_id(BigInteger i_credito_id) {
		this.i_credito_id = i_credito_id;
	}

	public String getVc_producto_credito() {
		return vc_producto_credito;
	}

	public void setVc_producto_credito(String vc_producto_credito) {
		this.vc_producto_credito = vc_producto_credito;
	}

	public LocalDate getDt_fecha_ministrado() {
		return dt_fecha_ministrado;
	}

	public void setDt_fecha_ministrado(LocalDate dt_fecha_ministrado) {
		this.dt_fecha_ministrado = dt_fecha_ministrado;
	}

	public String getVc_estatus_credito() {
		return vc_estatus_credito;
	}

	public void setVc_estatus_credito(String vc_estatus_credito) {
		this.vc_estatus_credito = vc_estatus_credito;
	}

	public String getI_referencia_credito() {
		return i_referencia_credito;
	}

	public void setI_referencia_credito(String i_referencia_credito) {
		this.i_referencia_credito = i_referencia_credito;
	}

	public BigInteger getI_cuenta_aho_id() {
		return i_cuenta_aho_id;
	}

	public void setI_cuenta_aho_id(BigInteger i_cuenta_aho_id) {
		this.i_cuenta_aho_id = i_cuenta_aho_id;
	}

	public String getI_referencia_cta_principal_1() {
		return i_referencia_cta_principal_1;
	}

	public void setI_referencia_cta_principal_1(String i_referencia_cta_principal_1) {
		this.i_referencia_cta_principal_1 = i_referencia_cta_principal_1;
	}

	public String getI_referencia_cta_principal_2() {
		return i_referencia_cta_principal_2;
	}

	public void setI_referencia_cta_principal_2(String i_referencia_cta_principal_2) {
		this.i_referencia_cta_principal_2 = i_referencia_cta_principal_2;
	}

	public int getI_institucion_id() {
		return i_institucion_id;
	}

	public void setI_institucion_id(int i_institucion_id) {
		this.i_institucion_id = i_institucion_id;
	}

	public String getVc_nombre_institucion() {
		return vc_nombre_institucion;
	}

	public void setVc_nombre_institucion(String vc_nombre_institucion) {
		this.vc_nombre_institucion = vc_nombre_institucion;
	}

	public String getVc_tipo_referencia() {
		return vc_tipo_referencia;
	}

	public void setVc_tipo_referencia(String vc_tipo_referencia) {
		this.vc_tipo_referencia = vc_tipo_referencia;
	}

}
