package ahorrosbienestar.sib.std.contrasena.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_HIST_CONTRASENIA",
		indexes = {@Index(name = "fk_historico_contrasena_Usuario1_idx", columnList = "usr_id_Usr ASC, Usr_historico_contrasena_id ASC")})
public class HistContrasena {
	
	@Column(name = "PK_ID_PARAMETROS")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int pk_id_parametros;
	
	@Column(name = "DT_VIG_CONT_INICIO")
	private Date dt_vig_cont_inicio;
	
	@Column(name = "DT_VIG_CONT_FIN")
	private Date dt_vig_cont_fin;
	
	@Column(name = "VC_CONTRASENIA")
	private String vc_contrasenia;
	
	@Column(name = "VC_INDICADOR_ACTIVO")
	private String vc_indicador_activo;
	
	@ManyToOne
	@JoinColumn(name = "usr_id_Usr")
	private Usuario usr_id_Usr;
	
	@ManyToOne
	@JoinColumn(name = "Usr_historico_contrasena_id")
	private Usuario Usr_historico_contrasena_id;

	public int getPk_id_parametros() {
		return pk_id_parametros;
	}

	public void setPk_id_parametros(int pk_id_parametros) {
		this.pk_id_parametros = pk_id_parametros;
	}

	public Date getDt_vig_cont_inicio() {
		return dt_vig_cont_inicio;
	}

	public void setDt_vig_cont_inicio(Date dt_vig_cont_inicio) {
		this.dt_vig_cont_inicio = dt_vig_cont_inicio;
	}

	public Date getDt_vig_cont_fin() {
		return dt_vig_cont_fin;
	}

	public void setDt_vig_cont_fin(Date dt_vig_cont_fin) {
		this.dt_vig_cont_fin = dt_vig_cont_fin;
	}

	public String getVc_contrasenia() {
		return vc_contrasenia;
	}

	public void setVc_contrasenia(String vc_contrasenia) {
		this.vc_contrasenia = vc_contrasenia;
	}

	public String getVc_indicador_activo() {
		return vc_indicador_activo;
	}

	public void setVc_indicador_activo(String vc_indicador_activo) {
		this.vc_indicador_activo = vc_indicador_activo;
	}

	public Usuario getUsr_id_Usr() {
		return usr_id_Usr;
	}

	public void setUsr_id_Usr(Usuario usr_id_Usr) {
		this.usr_id_Usr = usr_id_Usr;
	}

	public Usuario getUsr_historico_contrasena_id() {
		return Usr_historico_contrasena_id;
	}

	public void setUsr_historico_contrasena_id(Usuario usr_historico_contrasena_id) {
		Usr_historico_contrasena_id = usr_historico_contrasena_id;
	}
	
	

}
