package ahorrosbienestar.sib.std.menuProducto.plantillasSMS.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.model.ClasificacionBases;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name="TBL_PLANTILLAS_SMS")
public class PlantillasSMS {
	
	@Column(name = "PK_ID_PLANTILLA")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pk_id_plantilla;
	
	@Column(name = "VC_NOMBRE_PLANTILLA", columnDefinition = "VARCHAR(50)")
	private String vc_nombre_plantilla;
	
	@Column(name = "VC_CONTENIDO", columnDefinition = "VARCHAR(200)")
	private String vc_contenido;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_REGISTRO", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_registro;
	
	@OneToOne
	@JoinColumn(name = "I_USUARIO_REGISTRO")
	private Usuario i_usuario_registro;
		
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_MOD", columnDefinition = "DATETIME(6) DEFAULT NULL")
	private LocalDate dt_fecha_mod;
	
	@OneToOne
	@JoinColumn(name = "I_USUARIO_MOD", updatable = true)
	private Usuario i_usuario_mod;
	
	@Column(name = "I_ACTIVO", columnDefinition = "TINYINT(1) DEFAULT 1")
	private int i_activo;
	
	//Tiene relacion con TBL_BASES_OA que es 1:1
	@OneToOne
	@JoinColumn(name = "FK_ID_BASE")
	private Bases FK_ID_BASE;

	//Tiene relacion con TBL_CLASIFICACION_BASES_OA que es 1:1
	@OneToOne
	@JoinColumn(name = "FK_ID_CLASIFICACION")
	private ClasificacionBases FK_ID_CLASIFICACION;

	public int getPk_id_plantilla() {
		return pk_id_plantilla;
	}

	public void setPk_id_plantilla(int pk_id_plantilla) {
		this.pk_id_plantilla = pk_id_plantilla;
	}

	public String getVc_nombre_plantilla() {
		return vc_nombre_plantilla;
	}

	public void setVc_nombre_plantilla(String vc_nombre_plantilla) {
		this.vc_nombre_plantilla = vc_nombre_plantilla;
	}

	public String getVc_contenido() {
		return vc_contenido;
	}

	public void setVc_contenido(String vc_contenido) {
		this.vc_contenido = vc_contenido;
	}

	public LocalDate getDt_fecha_registro() {
		return dt_fecha_registro;
	}

	public void setDt_fecha_registro(LocalDate dt_fecha_registro) {
		this.dt_fecha_registro = dt_fecha_registro;
	}

	public LocalDate getDt_fecha_mod() {
		return dt_fecha_mod;
	}

	public void setDt_fecha_mod(LocalDate dt_fecha_mod) {
		this.dt_fecha_mod = dt_fecha_mod;
	}

	public int getI_activo() {
		return i_activo;
	}

	public void setI_activo(int i_activo) {
		this.i_activo = i_activo;
	}

	public Bases getFK_ID_BASE() {
		return FK_ID_BASE;
	}

	public void setFK_ID_BASE(Bases fK_ID_BASE) {
		FK_ID_BASE = fK_ID_BASE;
	}

	public ClasificacionBases getFK_ID_CLASIFICACION() {
		return FK_ID_CLASIFICACION;
	}

	public void setFK_ID_CLASIFICACION(ClasificacionBases fK_ID_CLASIFICACION) {
		FK_ID_CLASIFICACION = fK_ID_CLASIFICACION;
	}

	public Usuario getI_usuario_registro() {
		return i_usuario_registro;
	}

	public void setI_usuario_registro(Usuario i_usuario_registro) {
		this.i_usuario_registro = i_usuario_registro;
	}

	public Usuario getI_usuario_mod() {
		return i_usuario_mod;
	}

	public void setI_usuario_mod(Usuario i_usuario_mod) {
		this.i_usuario_mod = i_usuario_mod;
	}
	
	

}
