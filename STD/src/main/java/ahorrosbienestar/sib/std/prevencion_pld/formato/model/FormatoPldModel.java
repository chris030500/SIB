package ahorrosbienestar.sib.std.prevencion_pld.formato.model;

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
@Table(name="TBL_FORMATO_TERCEROS_QEQ")
public class FormatoPldModel {
	
	@Id
	@Column(name = "PK_IDQEQ")
	private int pk_idqeq;

	@Column(name = "VC_NOMBRE")
	private String vc_nombre;
	
	@Column(name = "VC_PATERNO")
	private String vc_paterno;
	
	@Column(name = "VC_MATERNO")
	private String vc_materno;
	
	@Column(name = "VC_CURP")
	private String vc_curp;
	
	@Column(name = "VC_RFC")
	private String vc_rfc;
	
	@Column(name = "VC_FECNAC")
	private String vc_fecnac;
	
	@Column(name = "VC_LISTA")
	private String vc_lista;
	
	@Column(name = "VC_ESTATUS")
	private String vc_estatus;
	
	@Column(name = "VC_DEPENDENCIA")
	private String vc_dependencia;
	
	@Column(name = "VC_PUESTO")
	private String vc_puesto;
	
	@Column(name = "VC_IDDISPO")
	private String vc_iddispo;
	
	@Column(name = "VC_CURPOK")
	private String vc_curpok;
	
	@Column(name = "VC_IDREL")
	private String vc_idrel;
	
	@Column(name = "VC_PARENTESCO")
	private String vc_parentesco;
	
	@Column(name = "VC_RAZONSOC")
	private String vc_razonsoc;
	
	@Column(name = "VC_RFCMORAL")
	private String vc_rfcmoral;
	
	@Column(name = "VC_ISSSTE")
	private String vc_issste;
	
	@Column(name = "VC_IMSS")
	private String vc_imss;
	
	@Column(name = "VC_INGRESOS")
	private String vc_ingresos;
	
	@Column(name = "VC_NOMCOMP")
	private String vc_nomcomp;
	
	@Column(name = "VC_APELLIDOS")
	private String vc_apellidos;
	
	@Column(name = "VC_ENTIDAD")
	private String vc_entidad;
	
	@Column(name = "VC_GENERO")
	private String vc_genero;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA")
	private LocalDate dt_fecha;
	
	@OneToOne
	@JoinColumn(name = "FK_USUARIO_REGISTRO")
	private Usuario fk_usuario_registro;

	public int getPk_idqeq() {
		return pk_idqeq;
	}

	public void setPk_idqeq(int pk_idqeq) {
		this.pk_idqeq = pk_idqeq;
	}

	public String getVc_nombre() {
		return vc_nombre;
	}

	public void setVc_nombre(String vc_nombre) {
		this.vc_nombre = vc_nombre;
	}

	public String getVc_paterno() {
		return vc_paterno;
	}

	public void setVc_paterno(String vc_paterno) {
		this.vc_paterno = vc_paterno;
	}

	public String getVc_materno() {
		return vc_materno;
	}

	public void setVc_materno(String vc_materno) {
		this.vc_materno = vc_materno;
	}

	public String getVc_curp() {
		return vc_curp;
	}

	public void setVc_curp(String vc_curp) {
		this.vc_curp = vc_curp;
	}

	public String getVc_rfc() {
		return vc_rfc;
	}

	public void setVc_rfc(String vc_rfc) {
		this.vc_rfc = vc_rfc;
	}

	public String getVc_fecnac() {
		return vc_fecnac;
	}

	public void setVc_fecnac(String vc_fecnac) {
		this.vc_fecnac = vc_fecnac;
	}

	public String getVc_lista() {
		return vc_lista;
	}

	public void setVc_lista(String vc_lista) {
		this.vc_lista = vc_lista;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}

	public String getVc_dependencia() {
		return vc_dependencia;
	}

	public void setVc_dependencia(String vc_dependencia) {
		this.vc_dependencia = vc_dependencia;
	}

	public String getVc_puesto() {
		return vc_puesto;
	}

	public void setVc_puesto(String vc_puesto) {
		this.vc_puesto = vc_puesto;
	}

	public String getVc_iddispo() {
		return vc_iddispo;
	}

	public void setVc_iddispo(String vc_iddispo) {
		this.vc_iddispo = vc_iddispo;
	}

	public String getVc_curpok() {
		return vc_curpok;
	}

	public void setVc_curpok(String vc_curpok) {
		this.vc_curpok = vc_curpok;
	}

	public String getVc_idrel() {
		return vc_idrel;
	}

	public void setVc_idrel(String vc_idrel) {
		this.vc_idrel = vc_idrel;
	}

	public String getVc_parentesco() {
		return vc_parentesco;
	}

	public void setVc_parentesco(String vc_parentesco) {
		this.vc_parentesco = vc_parentesco;
	}

	public String getVc_razonsoc() {
		return vc_razonsoc;
	}

	public void setVc_razonsoc(String vc_razonsoc) {
		this.vc_razonsoc = vc_razonsoc;
	}

	public String getVc_rfcmoral() {
		return vc_rfcmoral;
	}

	public void setVc_rfcmoral(String vc_rfcmoral) {
		this.vc_rfcmoral = vc_rfcmoral;
	}

	public String getVc_issste() {
		return vc_issste;
	}

	public void setVc_issste(String vc_issste) {
		this.vc_issste = vc_issste;
	}

	public String getVc_imss() {
		return vc_imss;
	}

	public void setVc_imss(String vc_imss) {
		this.vc_imss = vc_imss;
	}

	public String getVc_ingresos() {
		return vc_ingresos;
	}

	public void setVc_ingresos(String vc_ingresos) {
		this.vc_ingresos = vc_ingresos;
	}

	public String getVc_nomcomp() {
		return vc_nomcomp;
	}

	public void setVc_nomcomp(String vc_nomcomp) {
		this.vc_nomcomp = vc_nomcomp;
	}

	public String getVc_apellidos() {
		return vc_apellidos;
	}

	public void setVc_apellidos(String vc_apellidos) {
		this.vc_apellidos = vc_apellidos;
	}

	public String getVc_entidad() {
		return vc_entidad;
	}

	public void setVc_entidad(String vc_entidad) {
		this.vc_entidad = vc_entidad;
	}

	public String getVc_genero() {
		return vc_genero;
	}

	public void setVc_genero(String vc_genero) {
		this.vc_genero = vc_genero;
	}

	public LocalDate getDt_fecha() {
		return dt_fecha;
	}

	public void setDt_fecha(LocalDate dt_fecha) {
		this.dt_fecha = dt_fecha;
	}

	public Usuario getFk_usuario_registro() {
		return fk_usuario_registro;
	}

	public void setFk_usuario_registro(Usuario fk_usuario_registro) {
		this.fk_usuario_registro = fk_usuario_registro;
	}
		
}
