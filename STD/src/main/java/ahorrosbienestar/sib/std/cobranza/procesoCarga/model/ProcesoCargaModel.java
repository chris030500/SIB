package ahorrosbienestar.sib.std.cobranza.procesoCarga.model;

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

import ahorrosbienestar.sib.std.cobranza.banco.model.BancoModel;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_PROCESO_CARGA_CNV")
public class ProcesoCargaModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_PROCESO")
	private int pk_id_proceso;
	
	@OneToOne
	@JoinColumn(name = "FK_BANCO")
	private BancoModel fk_banco;
	
	@Column(name = "I_CANTIDAD_REGISTRO")
	private int i_cantidad_registro;
	
	@Column(name = "I_TOTAL_MONTO")
	private int i_total_monto;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_CARGA")
	private LocalDate dt_fecha_carga;
	
	@OneToOne
	@JoinColumn(name = "FK_USUARIO_CARGA")
	private Usuario fk_usuario_carga;

	public int getPk_id_proceso() {
		return pk_id_proceso;
	}

	public void setPk_id_proceso(int pk_id_proceso) {
		this.pk_id_proceso = pk_id_proceso;
	}

	public BancoModel getFk_banco() {
		return fk_banco;
	}

	public void setFk_banco(BancoModel fk_banco) {
		this.fk_banco = fk_banco;
	}

	public int getI_cantidad_registro() {
		return i_cantidad_registro;
	}

	public void setI_cantidad_registro(int i_cantidad_registro) {
		this.i_cantidad_registro = i_cantidad_registro;
	}

	public int getI_total_monto() {
		return i_total_monto;
	}

	public void setI_total_monto(int i_total_monto) {
		this.i_total_monto = i_total_monto;
	}

	public LocalDate getDt_fecha_carga() {
		return dt_fecha_carga;
	}

	public void setDt_fecha_carga(LocalDate dt_fecha_carga) {
		this.dt_fecha_carga = dt_fecha_carga;
	}

	public Usuario getFk_usuario_carga() {
		return fk_usuario_carga;
	}

	public void setFk_usuario_carga(Usuario fk_usuario_carga) {
		this.fk_usuario_carga = fk_usuario_carga;
	}

}
