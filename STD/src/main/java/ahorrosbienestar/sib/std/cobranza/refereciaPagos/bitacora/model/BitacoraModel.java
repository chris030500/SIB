package ahorrosbienestar.sib.std.cobranza.refereciaPagos.bitacora.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.model.ConsultaModel;

@Entity
@Table(name = "TBL_BITACORA_RP")
public class BitacoraModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_BITACORA_RP")
	private int pk_id_bitacora_rp;

	@ManyToOne
	@JoinColumn(name = "FK_ID_CONSULTA_RP")
	private ConsultaModel fk_id_consulta_rp;

	@Column(name = "I_NUMERO_CREDITO")
	private BigInteger i_numero_credito;

	@Column(name = "VC_PRODUCTO_CREDITO")
	private String vc_producto_credito;

	@Column(name = "I_REFERENCIA_BANCO_CREDITO")
	private String i_referencia_banco_credito;

	@Column(name = "VC_NOMBRE_BANCO_CREDITO")
	private String vc_nombre_banco_credito;

	public int getPk_id_bitacora_rp() {
		return pk_id_bitacora_rp;
	}

	public void setPk_id_bitacora_rp(int pk_id_bitacora_rp) {
		this.pk_id_bitacora_rp = pk_id_bitacora_rp;
	}

	public ConsultaModel getFk_id_consulta_rp() {
		return fk_id_consulta_rp;
	}

	public void setFk_id_consulta_rp(ConsultaModel fk_id_consulta_rp) {
		this.fk_id_consulta_rp = fk_id_consulta_rp;
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

	public String getI_referencia_banco_credito() {
		return i_referencia_banco_credito;
	}

	public void setI_referencia_banco_credito(String i_referencia_banco_credito) {
		this.i_referencia_banco_credito = i_referencia_banco_credito;
	}

	public String getVc_nombre_banco_credito() {
		return vc_nombre_banco_credito;
	}

	public void setVc_nombre_banco_credito(String vc_nombre_banco_credito) {
		this.vc_nombre_banco_credito = vc_nombre_banco_credito;
	}

}

