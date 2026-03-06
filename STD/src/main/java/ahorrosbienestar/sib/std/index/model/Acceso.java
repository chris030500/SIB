package ahorrosbienestar.sib.std.index.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "TBL_BITACORA_ACCESO")
public class Acceso {

	@Column(name = "PK_ID_SESION", columnDefinition = "BIGINT")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_id_sesion;

	@Column(name = "I_ID_USUARIO", columnDefinition = "BIGINT")
	private int i_id_usuario;

	@Column(name = "VC_USUARIO", columnDefinition = "VARCHAR(30)")
	private String vc_usuario;

	@Column(name = "I_COD_ACCION", columnDefinition = "BIGINT")
	private int i_cod_accion;

	@Column(name = "VC_DESCRIPCION", columnDefinition = "VARCHAR(250)")
	private String vc_descripci;

	@Column(name = "VC_IP_REMOTO", columnDefinition = "VARCHAR(50)")
	private String vc_ip_remoto;

	@Column(name = "VC_HOST_REMOTO", columnDefinition = "VARCHAR(50)")
	private String vc_host_remoto;

	@Column(name = "VC_NOM_SERVIDOR", columnDefinition = "VARCHAR(50)")
	private String vc_nom_servidor;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_FECHA_REGISTRO", columnDefinition = "DATE DEFAULT CURRENT_DATE")
	private Date dt_fecha_registro;

	@Column(name = "VC_HORA_REGISTRO", columnDefinition = "VARCHAR(10)")
	private String vc_hora_registro;

	public int getPk_id_sesion() {
		return pk_id_sesion;
	}

	public void setPk_id_sesion(int pk_id_sesion) {
		this.pk_id_sesion = pk_id_sesion;
	}

	public int getI_id_usuario() {
		return i_id_usuario;
	}

	public void setI_id_usuario(int i_id_usuario) {
		this.i_id_usuario = i_id_usuario;
	}

	public String getVc_usuario() {
		return vc_usuario;
	}

	public void setVc_usuario(String vc_usuario) {
		this.vc_usuario = vc_usuario;
	}

	public int getI_cod_accion() {
		return i_cod_accion;
	}

	public void setI_cod_accion(int i_cod_accion) {
		this.i_cod_accion = i_cod_accion;
	}

	public String getVc_descripci() {
		return vc_descripci;
	}

	public void setVc_descripci(String vc_descripci) {
		this.vc_descripci = vc_descripci;
	}

	public String getVc_ip_remoto() {
		return vc_ip_remoto;
	}

	public void setVc_ip_remoto(String vc_ip_remoto) {
		this.vc_ip_remoto = vc_ip_remoto;
	}

	public String getVc_host_remoto() {
		return vc_host_remoto;
	}

	public void setVc_host_remoto(String vc_host_remoto) {
		this.vc_host_remoto = vc_host_remoto;
	}

	public String getVc_nom_servidor() {
		return vc_nom_servidor;
	}

	public void setVc_nom_servidor(String vc_nom_servidor) {
		this.vc_nom_servidor = vc_nom_servidor;
	}

	public Date getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(Date dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public String getVc_hora_registro() {
		return vc_hora_registro;
	}

	public void setVc_hora_registro(String vc_hora_registro) {
		this.vc_hora_registro = vc_hora_registro;
	}
	
	
	
}
