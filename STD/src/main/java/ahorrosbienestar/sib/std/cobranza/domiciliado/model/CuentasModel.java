package ahorrosbienestar.sib.std.cobranza.domiciliado.model;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_CUENTASTRANS_MF")
public class CuentasModel {

	@Id
	@Column(name = "PK_ID_CUENTAS")
	private int pk_id_cuentas;
	
	@Column(name = "CLIENTE_ID")
	private int cliente_id;
	
	@Column(name = "RFC_OFICIAL")
	private String rfc_oficial;
	
	@Column(name = "CREDITO_ID")
	private BigInteger credito_id;
	
	@Column(name = "CLABE")
	private String clabe;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "FECHA_MINISTRADO")
	private LocalDate fechaMinistrado;

	public int getPk_id_cuentas() {
		return pk_id_cuentas;
	}

	public void setPk_id_cuentas(int pk_id_cuentas) {
		this.pk_id_cuentas = pk_id_cuentas;
	}

	public int getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(int cliente_id) {
		this.cliente_id = cliente_id;
	}

	public String getRfc_oficial() {
		return rfc_oficial;
	}

	public void setRfc_oficial(String rfc_oficial) {
		this.rfc_oficial = rfc_oficial;
	}

	public BigInteger getCredito_id() {
		return credito_id;
	}

	public void setCredito_id(BigInteger credito_id) {
		this.credito_id = credito_id;
	}

	public String getClabe() {
		return clabe;
	}

	public void setClabe(String clabe) {
		this.clabe = clabe;
	}

	public LocalDate getFechaMinistrado() {
		return fechaMinistrado;
	}

	public void setFechaMinistrado(LocalDate fechaMinistrado) {
		this.fechaMinistrado = fechaMinistrado;
	}

}
