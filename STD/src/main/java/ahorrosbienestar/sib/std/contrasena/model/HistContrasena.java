package ahorrosbienestar.sib.std.contrasena.model;


import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_HIST_CONTRASENIA",
		indexes = {@Index(name = "fk_historico_contrasena_Usuario1_idx", columnList = "usr_id_Usr ASC, Usr_historico_contrasena_id ASC")})
public class HistContrasena {
	
	@Column(name = "PK_ID_PARAMETROS")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tbl_hist_contrasenia_seq")
	@SequenceGenerator(name = "tbl_hist_contrasenia_seq", sequenceName = "tbl_hist_contrasenia_seq", initialValue = 2, allocationSize=1)
	private int pk_id_parametros;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_VIG_CONT_INICIO")
	private LocalDate dt_vig_cont_inicio;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_VIG_CONT_FIN")
	private LocalDate dt_vig_cont_fin;
	
	@Column(name = "VC_CONTRASENIA", columnDefinition = "VARCHAR(60)")
	private String vc_contrasenia;
	
	@Column(name = "VC_INDICADOR_ACTIVO", columnDefinition = "TINYINT(1)")
	private int vc_indicador_activo;
	
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

	

	public LocalDate getDt_vig_cont_inicio() {
		return dt_vig_cont_inicio;
	}

	public void setDt_vig_cont_inicio(LocalDate dt_vig_cont_inicio) {
		this.dt_vig_cont_inicio = dt_vig_cont_inicio;
	}

	public LocalDate getDt_vig_cont_fin() {
		return dt_vig_cont_fin;
	}

	public void setDt_vig_cont_fin(LocalDate dt_vig_cont_fin) {
		this.dt_vig_cont_fin = dt_vig_cont_fin;
	}

	public String getVc_contrasenia() {
		return vc_contrasenia;
	}

	public void setVc_contrasenia(String vc_contrasenia) {
		this.vc_contrasenia = vc_contrasenia;
	}

	

	public int getVc_indicador_activo() {
		return vc_indicador_activo;
	}

	public void setVc_indicador_activo(int vc_indicador_activo) {
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
