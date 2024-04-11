package ahorrosbienestar.sib.std.menuProducto.clienteBloqueados.model;

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
@Table(name="TBL_CLIENTES_BLOQUEADOS_AO")
public class ClientesBloqueados {
	
	@Column(name = "PK_ID_BLOQUEO")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_id_bloqueo;
	
	@Column(name = "I_CLIENTEID", columnDefinition = "INT")
	private int i_clienteid;
	
	@Column(name = "VC_NOMBRE_CLIENTE", columnDefinition = "VARCHAR(300)")
	private String vc_nombre_cliente;
	
	@OneToOne
	@JoinColumn(name = "I_USUARIO_BLOQUEO", updatable = true)
	private Usuario i_usuario_bloqueo;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_BLOQUEO", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_bloqueo;
	
	@Column(name = "VC_MOTIVO_BLOQUEO", columnDefinition = "LONGTEXT")
	private String vc_motivo_bloqueo;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_VENCIMIENTO", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_vencimiento;
	
	@Column(name = "VC_MOTIVO_DESBLOQUEO", columnDefinition = "VARCHAR(300)")
	private String vc_motivo_desbloqueo;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_DESBLOQUEO", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_desbloqueo;
	
	@OneToOne
	@JoinColumn(name = "I_USUARIO_DESBLOQUEO", updatable = true)
	private Usuario i_usuario_desbloqueo;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1) DEFAULT 2")
	private int i_estatus;
	
	@Column(name = "I_TIPO_BLOQUEO", columnDefinition = "TINYINT(1) DEFAULT 1")
	private int i_tipo_bloqueo;

	public int getPk_id_bloqueo() {
		return pk_id_bloqueo;
	}

	public void setPk_id_bloqueo(int pk_id_bloqueo) {
		this.pk_id_bloqueo = pk_id_bloqueo;
	}

	public int getI_clienteid() {
		return i_clienteid;
	}

	public void setI_clienteid(int i_clienteid) {
		this.i_clienteid = i_clienteid;
	}

	public String getVc_nombre_cliente() {
		return vc_nombre_cliente;
	}

	public void setVc_nombre_cliente(String vc_nombre_cliente) {
		this.vc_nombre_cliente = vc_nombre_cliente;
	}

	public Usuario getI_usuario_bloqueo() {
		return i_usuario_bloqueo;
	}

	public void setI_usuario_bloqueo(Usuario i_usuario_bloqueo) {
		this.i_usuario_bloqueo = i_usuario_bloqueo;
	}

	public LocalDate getDt_fecha_bloqueo() {
		return dt_fecha_bloqueo;
	}

	public void setDt_fecha_bloqueo(LocalDate dt_fecha_bloqueo) {
		this.dt_fecha_bloqueo = dt_fecha_bloqueo;
	}

	public String getVc_motivo_bloqueo() {
		return vc_motivo_bloqueo;
	}

	public void setVc_motivo_bloqueo(String vc_motivo_bloqueo) {
		this.vc_motivo_bloqueo = vc_motivo_bloqueo;
	}

	public LocalDate getDt_fecha_vencimiento() {
		return dt_fecha_vencimiento;
	}

	public void setDt_fecha_vencimiento(LocalDate dt_fecha_vencimiento) {
		this.dt_fecha_vencimiento = dt_fecha_vencimiento;
	}

	public String getVc_motivo_desbloqueo() {
		return vc_motivo_desbloqueo;
	}

	public void setVc_motivo_desbloqueo(String vc_motivo_desbloqueo) {
		this.vc_motivo_desbloqueo = vc_motivo_desbloqueo;
	}

	public LocalDate getDt_fecha_desbloqueo() {
		return dt_fecha_desbloqueo;
	}

	public void setDt_fecha_desbloqueo(LocalDate dt_fecha_desbloqueo) {
		this.dt_fecha_desbloqueo = dt_fecha_desbloqueo;
	}

	public Usuario getI_usuario_desbloqueo() {
		return i_usuario_desbloqueo;
	}

	public void setI_usuario_desbloqueo(Usuario i_usuario_desbloqueo) {
		this.i_usuario_desbloqueo = i_usuario_desbloqueo;
	}

	public int getI_estatus() {
		return i_estatus;
	}

	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}

	public int getI_tipo_bloqueo() {
		return i_tipo_bloqueo;
	}

	public void setI_tipo_bloqueo(int i_tipo_bloqueo) {
		this.i_tipo_bloqueo = i_tipo_bloqueo;
	}

}
