package ahorrosbienestar.sib.std.convenios.detalleCarga.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import ahorrosbienestar.sib.std.convenios.procesoCarga.model.ProcesoCargaModel;

@Entity
@Table(name = "TBL_DETALLE_CARGA_CNV")
public class DetalleCargaModel {
	
	@Id
	@Column(name = "PK_ID_DETALLE")
	private int pk_id_detalle;
	
	@OneToOne
	@JoinColumn(name = "FK_PROCESO")
	private ProcesoCargaModel fk_proceso;
	
	@Column(name = "TX_ARCHIVO_ENTRADA")
	private String tx_archivo_entrada;
	
	@Column(name = "TX_ARCHIVO_SALIDA")
	private String tx_archivo_salida;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_ENTRADA")
	private LocalDate dt_fecha_entrada;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_SALIDA")
	private LocalDate dt_fecha_salida;

	public int getPk_id_detalle() {
		return pk_id_detalle;
	}

	public void setPk_id_detalle(int pk_id_detalle) {
		this.pk_id_detalle = pk_id_detalle;
	}

	public ProcesoCargaModel getFk_proceso() {
		return fk_proceso;
	}

	public void setFk_proceso(ProcesoCargaModel fk_proceso) {
		this.fk_proceso = fk_proceso;
	}

	public String getTx_archivo_entrada() {
		return tx_archivo_entrada;
	}

	public void setTx_archivo_entrada(String tx_archivo_entrada) {
		this.tx_archivo_entrada = tx_archivo_entrada;
	}

	public String getTx_archivo_salida() {
		return tx_archivo_salida;
	}

	public void setTx_archivo_salida(String tx_archivo_salida) {
		this.tx_archivo_salida = tx_archivo_salida;
	}

	public LocalDate getDt_fecha_entrada() {
		return dt_fecha_entrada;
	}

	public void setDt_fecha_entrada(LocalDate dt_fecha_entrada) {
		this.dt_fecha_entrada = dt_fecha_entrada;
	}

	public LocalDate getDt_fecha_salida() {
		return dt_fecha_salida;
	}

	public void setDt_fecha_salida(LocalDate dt_fecha_salida) {
		this.dt_fecha_salida = dt_fecha_salida;
	}
	
}
