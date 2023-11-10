package ahorrosbienestar.sib.std.cobranza.refereciaPagos.cuentasCliente.dto;

import java.time.LocalDate;

public class CuentasClienteDTO {

	private String pk_id_cuenta_rp;
	private String i_cliente_id;
	private String vc_nombre_cliente;
	private String i_credito_id;
	private String vc_producto_credito;
	private LocalDate dt_fecha_ministrado;
	private String vc_estatus_credito;
	private String i_referencia_credito;
	private String i_cuenta_aho_id;
	private String i_referencia_cta_principal_1;
	private String i_referencia_cta_principal_2;
	private String i_institucion_id;
	private String vc_nombre_institucion;
	private String vc_tipo_referencia;

	public String getPk_id_cuenta_rp() {
		return pk_id_cuenta_rp;
	}

	public void setPk_id_cuenta_rp(String pk_id_cuenta_rp) {
		this.pk_id_cuenta_rp = pk_id_cuenta_rp;
	}

	public String getI_cliente_id() {
		return i_cliente_id;
	}

	public void setI_cliente_id(String i_cliente_id) {
		this.i_cliente_id = i_cliente_id;
	}

	public String getVc_nombre_cliente() {
		return vc_nombre_cliente;
	}

	public void setVc_nombre_cliente(String vc_nombre_cliente) {
		this.vc_nombre_cliente = vc_nombre_cliente;
	}

	public String getI_credito_id() {
		return i_credito_id;
	}

	public void setI_credito_id(String i_credito_id) {
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

	public String getI_cuenta_aho_id() {
		return i_cuenta_aho_id;
	}

	public void setI_cuenta_aho_id(String i_cuenta_aho_id) {
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

	public String getI_institucion_id() {
		return i_institucion_id;
	}

	public void setI_institucion_id(String i_institucion_id) {
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
