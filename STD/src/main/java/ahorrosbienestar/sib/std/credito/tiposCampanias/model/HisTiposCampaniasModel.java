package ahorrosbienestar.sib.std.credito.tiposCampanias.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_HIS_TIPOS_CAMPANIA")

public class HisTiposCampaniasModel {
	
	@Id
	@Column(name = "PK_ID_HIS_TIPO_CAMPANIA")
	private Integer pk_id_his_tipo_campania;
 
	@Column(name = "VC_JSON_ENTRADA")
	private String vc_json_entrada;
	
	@Column(name = "VC_TABLA")
	private String vc_tabla;
	
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
 
	@Column(name = "I_USER_REG")
	private Integer i_user_reg;

	public String getVc_tabla() {
		return vc_tabla;
	}

	public void setVc_tabla(String vc_tabla) {
		this.vc_tabla = vc_tabla;
	}

	public Integer getPk_id_his_tipo_campania() {
		return pk_id_his_tipo_campania;
	}

	public void setPk_id_his_tipo_campania(Integer pk_id_his_tipo_campania) {
		this.pk_id_his_tipo_campania = pk_id_his_tipo_campania;
	}

	public String getVc_json_entrada() {
		return vc_json_entrada;
	}

	public void setVc_json_entrada(String vc_json_entrada) {
		this.vc_json_entrada = vc_json_entrada;
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

}
