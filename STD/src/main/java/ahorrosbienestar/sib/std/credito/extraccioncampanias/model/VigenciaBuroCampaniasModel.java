package ahorrosbienestar.sib.std.credito.extraccioncampanias.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_CAM_VIGENCIA_BURO")
public class VigenciaBuroCampaniasModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_CAM_VIGENCIA_BURO")
	private Integer pk_id_cam_vigencia_buro;
 
	@Column(name = "VC_RFC_CLIENTE")
	private String vc_rfc_cliente;
	
	@Column(name = "I_TIPO_BURO")
	private Integer i_tipo_buro;
	
	@Column(name = "DT_FECHA_VIGENCIA")
	private LocalDate dt_fecha_vigencia;
	
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;

	public Integer getPk_id_cam_vigencia_buro() {
		return pk_id_cam_vigencia_buro;
	}

	public void setPk_id_cam_vigencia_buro(Integer pk_id_cam_vigencia_buro) {
		this.pk_id_cam_vigencia_buro = pk_id_cam_vigencia_buro;
	}

	public String getVc_rfc_cliente() {
		return vc_rfc_cliente;
	}

	public void setVc_rfc_cliente(String vc_rfc_cliente) {
		this.vc_rfc_cliente = vc_rfc_cliente;
	}

	public Integer getI_tipo_buro() {
		return i_tipo_buro;
	}

	public void setI_tipo_buro(Integer i_tipo_buro) {
		this.i_tipo_buro = i_tipo_buro;
	}

	public LocalDate getDt_fecha_vigencia() {
		return dt_fecha_vigencia;
	}

	public void setDt_fecha_vigencia(LocalDate dt_fecha_vigencia) {
		this.dt_fecha_vigencia = dt_fecha_vigencia;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

}
