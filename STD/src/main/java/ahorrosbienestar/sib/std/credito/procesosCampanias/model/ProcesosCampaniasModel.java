package ahorrosbienestar.sib.std.credito.procesosCampanias.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_PROCESO_CAMPANIA")
public class ProcesosCampaniasModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_PROCESO_CAMPANIA")
	private Integer pk_id_proceso_campania;
	
	@ManyToOne
	@JoinColumn(name = "I_ID_TIPO_CAMPANIA")
	private TiposCampaniasModel tipoCampania;
 
	@Column(name = "DT_FECHA_EXTRACCION")
	private LocalDate dt_fecha_extraccion;
	
	@Column(name = "I_BANDERA_EXTRACCION")
	private Integer i_bandera_extraccion;
	
	@Column(name = "DT_FECHA_INICIO_VIGENCIA")
	private LocalDate dt_fecha_inicio_vigencia;
	
	@Column(name = "DT_FECHA_FIN_VIGENCIA")
	private LocalDate dt_fecha_fin_vigencia;
	
	@Column(name = "DT_FECHA_ENVIO_MC")
	private LocalDate dt_fecha_envio_mc;
	
	@Column(name = "I_ESTATUS_REG")
	private Integer i_estatus_reg;
	
	@ManyToOne
	@JoinColumn(name = "I_ID_ESTATUS_CAMPANIA")
	private EstatusCampaniasModel estatusCampania;
	
	@Column(name = "DT_FECHA_REGISTRO")
	private LocalDate dt_fecha_registro;
	
	@OneToOne
	@JoinColumn(name = "I_USER_REG")
	private Usuario i_user_reg;

	public TiposCampaniasModel getTipoCampania() {
		return tipoCampania;
	}

	public void setTipoCampania(TiposCampaniasModel tipoCampania) {
		this.tipoCampania = tipoCampania;
	}

	public Integer getPk_id_proceso_campania() {
		return pk_id_proceso_campania;
	}

	public void setPk_id_proceso_campania(Integer pk_id_proceso_campania) {
		this.pk_id_proceso_campania = pk_id_proceso_campania;
	}

	/*public Integer getI_id_tipo_campania() {
		return i_id_tipo_campania;
	}

	public void setI_id_tipo_campania(Integer i_id_tipo_campania) {
		this.i_id_tipo_campania = i_id_tipo_campania;
	}*/

	public LocalDate getDt_fecha_extraccion() {
		return dt_fecha_extraccion;
	}

	public void setDt_fecha_extraccion(LocalDate dt_fecha_extraccion) {
		this.dt_fecha_extraccion = dt_fecha_extraccion;
	}

	public Integer getI_bandera_extraccion() {
		return i_bandera_extraccion;
	}

	public void setI_bandera_extraccion(Integer i_bandera_extraccion) {
		this.i_bandera_extraccion = i_bandera_extraccion;
	}

	public LocalDate getDt_fecha_inicio_vigencia() {
		return dt_fecha_inicio_vigencia;
	}

	public void setDt_fecha_inicio_vigencia(LocalDate dt_fecha_inicio_vigencia) {
		this.dt_fecha_inicio_vigencia = dt_fecha_inicio_vigencia;
	}

	public LocalDate getDt_fecha_fin_vigencia() {
		return dt_fecha_fin_vigencia;
	}

	public void setDt_fecha_fin_vigencia(LocalDate dt_fecha_fin_vigencia) {
		this.dt_fecha_fin_vigencia = dt_fecha_fin_vigencia;
	}

	public LocalDate getDt_fecha_envio_mc() {
		return dt_fecha_envio_mc;
	}

	public void setDt_fecha_envio_mc(LocalDate dt_fecha_envio_mc) {
		this.dt_fecha_envio_mc = dt_fecha_envio_mc;
	}

	public Integer getI_estatus_reg() {
		return i_estatus_reg;
	}

	public void setI_estatus_reg(Integer i_estatus_reg) {
		this.i_estatus_reg = i_estatus_reg;
	}

	public EstatusCampaniasModel getEstatusCampania() {
		return estatusCampania;
	}

	public void setEstatusCampania(EstatusCampaniasModel estatusCampania) {
		this.estatusCampania = estatusCampania;
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
