package ahorrosbienestar.sib.std.prevencion_pld.bitacora.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;


@Entity
@Table(name="TBL_BITACORA_QEQ")
public class BitacoraPldModel {
	
	@Id
	@Column(name = "PK_ID_BITACORA")
	private int pk_id_bitacora;
	
	@Column(name = "VC_NOMBRE_ARCHIVO")
	private String vc_nombre_archivo;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA")
	private LocalDate dt_fecha;
	
	@Column(name = "I_NUMERO_REGISTRO")
	private int i_numero_registro;
	
	@Column(name = "I_REGISTROS_EXCLUIDOS")
	private int i_registros_excluidos;
	
	@Column(name = "I_REGISTRO_FINAL")
	private int i_registro_final;
	
	@Column(name = "VC_DURACION")
	private String vc_duracion;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;
	
	@Column(name = "VC_COMENTARIOS")
	private String vc_comentarios;
	
	@OneToOne
	@JoinColumn(name = "FK_USUARIO_REGISTRO")
	private Usuario fk_usuario_registro;

	public int getPk_id_bitacora() {
		return pk_id_bitacora;
	}

	public void setPk_id_bitacora(int pk_id_bitacora) {
		this.pk_id_bitacora = pk_id_bitacora;
	}

	public String getVc_nombre_archivo() {
		return vc_nombre_archivo;
	}

	public void setVc_nombre_archivo(String vc_nombre_archivo) {
		this.vc_nombre_archivo = vc_nombre_archivo;
	}

	public LocalDate getDt_fecha() {
		return dt_fecha;
	}

	public void setDt_fecha(LocalDate dt_fecha) {
		this.dt_fecha = dt_fecha;
	}

	public int getI_numero_registro() {
		return i_numero_registro;
	}

	public void setI_numero_registro(int i_numero_registro) {
		this.i_numero_registro = i_numero_registro;
	}

	public int getI_registros_excluidos() {
		return i_registros_excluidos;
	}

	public void setI_registros_excluidos(int i_registros_excluidos) {
		this.i_registros_excluidos = i_registros_excluidos;
	}

	public int getI_registro_final() {
		return i_registro_final;
	}

	public void setI_registro_final(int i_registro_final) {
		this.i_registro_final = i_registro_final;
	}

	public String getVc_duracion() {
		return vc_duracion;
	}

	public void setVc_duracion(String vc_duracion) {
		this.vc_duracion = vc_duracion;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}

	public String getVc_comentarios() {
		return vc_comentarios;
	}

	public void setVc_comentarios(String vc_comentarios) {
		this.vc_comentarios = vc_comentarios;
	}

	public Usuario getFk_usuario_registro() {
		return fk_usuario_registro;
	}

	public void setFk_usuario_registro(Usuario fk_usuario_registro) {
		this.fk_usuario_registro = fk_usuario_registro;
	}
		
}
