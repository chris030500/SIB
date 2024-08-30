package ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.dto;

import java.time.LocalDate;

public class DatosConsultaDTO {

	private String pk_id_consulta_rp;
	private LocalDate dt_fecha_registro;
	private String fk_usuario_registro;
	private String vc_parametro_busqueda;
	private String vc_valor_parametro;
	private String i_numero_cliente;
	private String vc_nombre_cliente;
	private String i_numero_cuenta_principal_1;
	private String i_numero_cuenta_principal_2;

	public String getPk_id_consulta_rp() {
		return pk_id_consulta_rp;
	}

	public void setPk_id_consulta_rp(String pk_id_consulta_rp) {
		this.pk_id_consulta_rp = pk_id_consulta_rp;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public String getFk_usuario_registro() {
		return fk_usuario_registro;
	}

	public void setFk_usuario_registro(String fk_usuario_registro) {
		this.fk_usuario_registro = fk_usuario_registro;
	}

	public String getVc_parametro_busqueda() {
		return vc_parametro_busqueda;
	}

	public void setVc_parametro_busqueda(String vc_parametro_busqueda) {
		this.vc_parametro_busqueda = vc_parametro_busqueda;
	}

	public String getVc_valor_parametro() {
		return vc_valor_parametro;
	}

	public void setVc_valor_parametro(String vc_valor_parametro) {
		this.vc_valor_parametro = vc_valor_parametro;
	}

	public String getI_numero_cliente() {
		return i_numero_cliente;
	}

	public void setI_numero_cliente(String i_numero_cliente) {
		this.i_numero_cliente = i_numero_cliente;
	}

	public String getVc_nombre_cliente() {
		return vc_nombre_cliente;
	}

	public void setVc_nombre_cliente(String vc_nombre_cliente) {
		this.vc_nombre_cliente = vc_nombre_cliente;
	}

	public String getI_numero_cuenta_principal_1() {
		return i_numero_cuenta_principal_1;
	}

	public void setI_numero_cuenta_principal_1(String i_numero_cuenta_principal_1) {
		this.i_numero_cuenta_principal_1 = i_numero_cuenta_principal_1;
	}

	public String getI_numero_cuenta_principal_2() {
		return i_numero_cuenta_principal_2;
	}

	public void setI_numero_cuenta_principal_2(String i_numero_cuenta_principal_2) {
		this.i_numero_cuenta_principal_2 = i_numero_cuenta_principal_2;
	}

}
