package ahorrosbienestar.sib.std.cobranza.refereciaPagos.consulta.model;

import java.math.BigInteger;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_CONSULTA_RP")
public class ConsultaModel {

	@Id
	@Column(name = "PK_ID_CONSULTA_RP")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_id_consulta_rp;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;

	@OneToOne
	@JoinColumn(name = "FK_USUARIO_REGISTRO")
	private Usuario fk_usuario_registro;

	@Column(name = "VC_PARAMETRO_BUSQUEDA")
	private String vc_parametro_busqueda;

	@Column(name = "VC_VALOR_PARAMETRO")
	private String vc_valor_parametro;

	@Column(name = "I_NUMERO_CLIENTE")
	private BigInteger i_numero_cliente;

	@Column(name = "VC_NOMBRE_CLIENTE")
	private String vc_nombre_cliente;

	@Column(name = "I_NUMERO_CUENTA_PRINCIPAL_1")
	private String i_numero_cuenta_principal_1;

	@Column(name = "I_NUMERO_CUENTA_PRINCIPAL_2")
	private String i_numero_cuenta_principal_2;

	public int getPk_id_consulta_rp() {
		return pk_id_consulta_rp;
	}

	public void setPk_id_consulta_rp(int pk_id_consulta_rp) {
		this.pk_id_consulta_rp = pk_id_consulta_rp;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public Usuario getFk_usuario_registro() {
		return fk_usuario_registro;
	}

	public void setFk_usuario_registro(Usuario fk_usuario_registro) {
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

