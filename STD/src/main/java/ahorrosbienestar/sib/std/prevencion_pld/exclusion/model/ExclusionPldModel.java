package ahorrosbienestar.sib.std.prevencion_pld.exclusion.model;

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

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.prevencion_pld.estatus.model.EstatusPldModel;
import ahorrosbienestar.sib.std.prevencion_pld.listas.model.ListasPldModel;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name="TBL_EXCLUSION_LISTA_ESTATUS_QEQ")
public class ExclusionPldModel {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "PK_ID_EXCLUSION")
	private int pk_id_exclusion;
	
	@ManyToOne
	@JoinColumn(name = "FK_LISTA")
	private ListasPldModel fk_lista;
	
	@ManyToOne
	@JoinColumn(name = "FK_ESTATUS")
	private EstatusPldModel fk_estatus;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA")
	private LocalDate dt_fecha;
	
	@OneToOne
	@JoinColumn(name = "FK_USUARIO_REGISTRO")
	private Usuario fk_usuario_registro;

	public int getPk_id_exclusion() {
		return pk_id_exclusion;
	}

	public void setPk_id_exclusion(int pk_id_exclusion) {
		this.pk_id_exclusion = pk_id_exclusion;
	}

	public ListasPldModel getFk_lista() {
		return fk_lista;
	}

	public void setFk_lista(ListasPldModel fk_lista) {
		this.fk_lista = fk_lista;
	}

	public EstatusPldModel getFk_estatus() {
		return fk_estatus;
	}

	public void setFk_estatus(EstatusPldModel fk_estatus) {
		this.fk_estatus = fk_estatus;
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

