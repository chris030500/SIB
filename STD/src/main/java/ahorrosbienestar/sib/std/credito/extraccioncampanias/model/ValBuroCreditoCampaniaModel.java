package ahorrosbienestar.sib.std.credito.extraccioncampanias.model;

import java.time.LocalDate;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_VAL_BURO_CRED_CAMPANIA")
public class ValBuroCreditoCampaniaModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_VAL_BURO_CRED_CAMPANIA")
	private Integer pk_id_val_buro_cred_campania;
 
	@Column(name = "VC_RFC_CLIENTE")
	private String vc_rfc_cliente;
 
	@Column(name = "I_TIPO_BURO_CREDITO")
	private Integer i_tipo_buro_credito;
	
	@Column(name = "D_PORC_USO_MAXIMO_ADVISER")
	private Double d_porc_uso_maximo_adviser;
	
	@Column(name = "D_MOP_MAXIMO_ADVISER")
	private Double d_mop_maximo_adviser;
	
	@Column(name = "D_BCS_MINIMO_ADVISER")
	private Double d_bcs_minimo_adviser;
	
	@Column(name = "D_ICC_MINIMO_ADVISER")
	private Double d_icc_minimo_adviser;
	
	@Column(name = "VC_RIESGO_MAXIMO_ADVISER")
	private String vc_riesgo_maximo_adviser;
	
	@Column(name = "D_BCS_MINIMO_PROSPECTOR")
	private Double d_bcs_minnimo_prospector;
	
	@Column(name = "D_ICC_MINIMO_PROSPECTOR")
	private Double d_icc_minnimo_prospector;
	
	@Column(name = "VC_RIESGO_MAXIMO_PROSPECTOR")
	private String vc_riesgo_maximo_prospector;
	
	@Column(name = "I_ESTATUS_REG")
	private Integer i_estatus_reg;
	
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	/*@OneToOne
	@JoinColumn(name = "I_USER_REG")
	private Usuario i_user_reg;*/
	
	@Column(name = "I_USER_REG")
	private Integer i_user_reg;
	
	@Column(name = "I_TIPO_CAMPANIA")
	private Integer i_tipo_campania;
	
	@Column(name = "I_PROCESO_CAMPANIA")
	private Integer i_proceso_campania;

	public Integer getPk_id_val_buro_cred_campania() {
		return pk_id_val_buro_cred_campania;
	}

	public void setPk_id_val_buro_cred_campania(Integer pk_id_val_buro_cred_campania) {
		this.pk_id_val_buro_cred_campania = pk_id_val_buro_cred_campania;
	}

	public String getVc_rfc_cliente() {
		return vc_rfc_cliente;
	}

	public void setVc_rfc_cliente(String vc_rfc_cliente) {
		this.vc_rfc_cliente = vc_rfc_cliente;
	}

	public Integer getI_tipo_buro_credito() {
		return i_tipo_buro_credito;
	}

	public void setI_tipo_buro_credito(Integer i_tipo_buro_credito) {
		this.i_tipo_buro_credito = i_tipo_buro_credito;
	}

	public Double getD_porc_uso_maximo_adviser() {
		return d_porc_uso_maximo_adviser;
	}

	public void setD_porc_uso_maximo_adviser(Double d_porc_uso_maximo_adviser) {
		this.d_porc_uso_maximo_adviser = d_porc_uso_maximo_adviser;
	}

	public Double getD_mop_maximo_adviser() {
		return d_mop_maximo_adviser;
	}

	public void setD_mop_maximo_adviser(Double d_mop_maximo_adviser) {
		this.d_mop_maximo_adviser = d_mop_maximo_adviser;
	}

	public Double getD_bcs_minimo_adviser() {
		return d_bcs_minimo_adviser;
	}

	public void setD_bcs_minimo_adviser(Double d_bcs_minimo_adviser) {
		this.d_bcs_minimo_adviser = d_bcs_minimo_adviser;
	}

	public Double getD_icc_minimo_adviser() {
		return d_icc_minimo_adviser;
	}

	public void setD_icc_minimo_adviser(Double d_icc_minimo_adviser) {
		this.d_icc_minimo_adviser = d_icc_minimo_adviser;
	}

	public Double getD_bcs_minnimo_prospector() {
		return d_bcs_minnimo_prospector;
	}

	public void setD_bcs_minnimo_prospector(Double d_bcs_minnimo_prospector) {
		this.d_bcs_minnimo_prospector = d_bcs_minnimo_prospector;
	}

	public Double getD_icc_minnimo_prospector() {
		return d_icc_minnimo_prospector;
	}

	public void setD_icc_minnimo_prospector(Double d_icc_minnimo_prospector) {
		this.d_icc_minnimo_prospector = d_icc_minnimo_prospector;
	}

	public String getVc_riesgo_maximo_adviser() {
		return vc_riesgo_maximo_adviser;
	}

	public void setVc_riesgo_maximo_adviser(String vc_riesgo_maximo_adviser) {
		this.vc_riesgo_maximo_adviser = vc_riesgo_maximo_adviser;
	}

	public String getVc_riesgo_maximo_prospector() {
		return vc_riesgo_maximo_prospector;
	}

	public void setVc_riesgo_maximo_prospector(String vc_riesgo_maximo_prospector) {
		this.vc_riesgo_maximo_prospector = vc_riesgo_maximo_prospector;
	}

	public Integer getI_estatus_reg() {
		return i_estatus_reg;
	}

	public void setI_estatus_reg(Integer i_estatus_reg) {
		this.i_estatus_reg = i_estatus_reg;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public Integer getI_user_reg() {
		return i_user_reg;
	}

	public void setI_user_reg(Integer i_user_reg) {
		this.i_user_reg = i_user_reg;
	}

	public Integer getI_tipo_campania() {
		return i_tipo_campania;
	}

	public void setI_tipo_campania(Integer i_tipo_campania) {
		this.i_tipo_campania = i_tipo_campania;
	}

	public Integer getI_proceso_campania() {
		return i_proceso_campania;
	}

	public void setI_proceso_campania(Integer i_proceso_campania) {
		this.i_proceso_campania = i_proceso_campania;
	}

}
