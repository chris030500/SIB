package ahorrosbienestar.sib.std.cobranza.refereciaPagos.hisotrial.model;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
public class HistorialModel {

	@Id
	@Column(name = "PK_ID_CONSULTA_RP")
	private int pk_id_consulta_rp;

	@OneToOne
	@JoinColumn(name = "PK_ID_USUARIO")
	private Usuario pk_id_usuario;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;

	@Column(name = "VC_PARAMETRO_BUSQUEDA")
	private String vc_parametro_busqueda;

	@Column(name = "VC_VALOR_PARAMETRO")
	private String vc_valor_parametro;

	@Column(name = "I_NUMERO_CLIENTE")
	private BigInteger i_numero_cliente;

	@Column(name = "VC_NOMBRE_CLIENTE")
	private String vc_nombre_cliente;

	@Column(name = "I_NUMERO_CUENTA_PRINCIPAL_1")
	private BigInteger i_numero_cuenta_principal_1;

	@Column(name = "I_NUMERO_CUENTA_PRINCIPAL_2")
	private BigInteger i_numero_cuenta_principal_2;

	@Column(name = "I_NUMERO_CREDITO")
	private BigInteger i_numero_credito;

	@Column(name = "VC_PRODUCTO_CREDITO")
	private String vc_producto_credito;

	@Column(name = "I_REFERENCIA_BANCO_CREDITO")
	private BigInteger i_referencia_banco_credito;

	@Column(name = "VC_NOMBRE_BANCO_CREDITO")
	private String vc_nombre_banco_credito;

	public int getPk_id_consulta_rp() {
		return pk_id_consulta_rp;
	}

	public void setPk_id_consulta_rp(int pk_id_consulta_rp) {
		this.pk_id_consulta_rp = pk_id_consulta_rp;
	}

	public Usuario getPk_id_usuario() {
		return pk_id_usuario;
	}

	public void setPk_id_usuario(Usuario pk_id_usuario) {
		this.pk_id_usuario = pk_id_usuario;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
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

	public BigInteger getI_numero_cliente() {
		return i_numero_cliente;
	}

	public void setI_numero_cliente(BigInteger i_numero_cliente) {
		this.i_numero_cliente = i_numero_cliente;
	}

	public String getVc_nombre_cliente() {
		return vc_nombre_cliente;
	}

	public void setVc_nombre_cliente(String vc_nombre_cliente) {
		this.vc_nombre_cliente = vc_nombre_cliente;
	}

	public BigInteger getI_numero_cuenta_principal_1() {
		return i_numero_cuenta_principal_1;
	}

	public void setI_numero_cuenta_principal_1(BigInteger i_numero_cuenta_principal_1) {
		this.i_numero_cuenta_principal_1 = i_numero_cuenta_principal_1;
	}

	public BigInteger getI_numero_cuenta_principal_2() {
		return i_numero_cuenta_principal_2;
	}

	public void setI_numero_cuenta_principal_2(BigInteger i_numero_cuenta_principal_2) {
		this.i_numero_cuenta_principal_2 = i_numero_cuenta_principal_2;
	}

	public BigInteger getI_numero_credito() {
		return i_numero_credito;
	}

	public void setI_numero_credito(BigInteger i_numero_credito) {
		this.i_numero_credito = i_numero_credito;
	}

	public String getVc_producto_credito() {
		return vc_producto_credito;
	}

	public void setVc_producto_credito(String vc_producto_credito) {
		this.vc_producto_credito = vc_producto_credito;
	}

	public BigInteger getI_referencia_banco_credito() {
		return i_referencia_banco_credito;
	}

	public void setI_referencia_banco_credito(BigInteger i_referencia_banco_credito) {
		this.i_referencia_banco_credito = i_referencia_banco_credito;
	}

	public String getVc_nombre_banco_credito() {
		return vc_nombre_banco_credito;
	}

	public void setVc_nombre_banco_credito(String vc_nombre_banco_credito) {
		this.vc_nombre_banco_credito = vc_nombre_banco_credito;
	}

}