package ahorrosbienestar.sib.std.contratos.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "TBL_DTOS_CONTRATO")
public class Contrato {
	

	@Column(name = "PK_ID_CONTRATO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="tbl_contrato_seq")
	@SequenceGenerator(name = "tbl_contrato_seq", sequenceName = "tbl_contrato_seq", initialValue = 1, allocationSize=1)
	private int pk_id_contrato;
	
	@Column(name = "I_NO_SOLICITUD", columnDefinition = "INT")
	private int i_no_solicitud;
	
	@Column(name = "VC_NOMBRE_CLIENTE", columnDefinition = "VARCHAR(80)")
	private String vc_nombre_cliente;
	
	@Column(name = "VC_DIRECCION", columnDefinition = "VARCHAR(250)")
	private String vc_direccion;
	
	@Column(name = "D_CAPITAL_OTORGADO", columnDefinition = "FLOAT")
	private float d_capital_otorgado;
	
	@Column(name = "D_TASA_INTERESES", columnDefinition = "FLOAT")
	private float i_tasa_intereses;
	
	@Column(name = "D_TASA_MORATORIA", columnDefinition = "float")
	private float i_tasa_moratoria;
		
	@Column(name = "D_CAT", columnDefinition = "float")
	private float d_cat;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@Column(name = "I_USUARIOID_REGISTRA", columnDefinition = "INT")
	private int i_usuarioid_registra;
	
	@Column(name = "V_RECA", columnDefinition = "VARCHAR(50)")
	private String v_reca;
	

	public String getV_reca() {
		return v_reca;
	}

	public void setV_reca(String v_reca) {
		this.v_reca = v_reca;
	}

	public int getPk_id_contrato() {
		return pk_id_contrato;
	}

	public void setPk_id_contrato(int pk_id_contrato) {
		this.pk_id_contrato = pk_id_contrato;
	}

	public int getI_no_solicitud() {
		return i_no_solicitud;
	}

	public void setI_no_solicitud(int i_no_solicitud) {
		this.i_no_solicitud = i_no_solicitud;
	}

	public String getVc_nombre_cliente() {
		return vc_nombre_cliente;
	}

	public void setVc_nombre_cliente(String vc_nombre_cliente) {
		this.vc_nombre_cliente = vc_nombre_cliente;
	}


	public float getD_capital_otorgado() {
		return d_capital_otorgado;
	}

	public void setD_capital_otorgado(float d_capital_otorgado) {
		this.d_capital_otorgado = d_capital_otorgado;
	}

	public float getI_tasa_intereses() {
		return i_tasa_intereses;
	}

	public void setI_tasa_intereses(float i_tasa_intereses) {
		this.i_tasa_intereses = i_tasa_intereses;
	}

	public float getI_tasa_moratoria() {
		return i_tasa_moratoria;
	}

	public void setI_tasa_moratoria(float i_tasa_moratoria) {
		this.i_tasa_moratoria = i_tasa_moratoria;
	}

	public float getD_cat() {
		return d_cat;
	}

	public void setD_cat(float d_cat) {
		this.d_cat = d_cat;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public int getI_usuarioid_registra() {
		return i_usuarioid_registra;
	}

	public void setI_usuarioid_registra(int i_usuarioid_registra) {
		this.i_usuarioid_registra = i_usuarioid_registra;
	}
	public String getVc_direccion() {
		return vc_direccion;
	}

	public void setVc_direccion(String vc_direccion) {
		this.vc_direccion = vc_direccion;
	}

	
}

