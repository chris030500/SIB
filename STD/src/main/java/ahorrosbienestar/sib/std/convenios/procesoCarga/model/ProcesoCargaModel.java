package ahorrosbienestar.sib.std.convenios.procesoCarga.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.convenios.banco.model.BancoModel;

@Entity
@Table(name = "TBL_PROCESO_CARGA_CNV")
public class ProcesoCargaModel {
	
	@Id
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
	
	@Column(name = "VC_USUARIO_CARGA")
	private String vc_usuario_carga;

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

	public String getVc_usuario_carga() {
		return vc_usuario_carga;
	}

	public void setVc_usuario_carga(String vc_usuario_carga) {
		this.vc_usuario_carga = vc_usuario_carga;
	}

}
