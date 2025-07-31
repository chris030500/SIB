package ahorrosbienestar.sib.std.credito.procesosCampanias.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_ESTATUS_CAMPANIA")
public class EstatusCampaniasModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_ESTATUS_CAMPANIA")
	private Integer pk_id_estatus_campania;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;
	
	@Column(name = "I_ESTATUS_REG")
	private Integer i_estatus_reg;
	
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@OneToOne
	@JoinColumn(name = "I_USER_REG")
	private Usuario i_user_reg;

	public Integer getPk_id_estatus_campania() {
		return pk_id_estatus_campania;
	}

	public void setPk_id_estatus_campania(Integer pk_id_estatus_campania) {
		this.pk_id_estatus_campania = pk_id_estatus_campania;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
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

	public Usuario getI_user_reg() {
		return i_user_reg;
	}

	public void setI_user_reg(Usuario i_user_reg) {
		this.i_user_reg = i_user_reg;
	}

}
