package ahorrosbienestar.sib.std.productodocumento.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_CAT_PROD_CAT_DOCT")
public class RelacionDocumentos {
	
	@Column(name = "ID_REL_PROD_DOCT")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="relDocumento_seq")
	@SequenceGenerator(name = "relDocumento_seq", sequenceName = "relDocumento_seq", initialValue = 1, allocationSize=1)
	private int pk_id_rel_prod_doct;
	
	@Column(name = "FK_PRODUCTO", columnDefinition = "int")
	private int fk_producto;
	
	@Column(name = "FK_DOCUMENTO", columnDefinition = "int")
	private int fk_documento;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_OPERACION")
	private LocalDate dt_fecha_operacion;
	
	@Column(name = "I_ESTATUS", columnDefinition = "TINYINT(1)")
	private int i_estatus;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_BAJA")
	private LocalDate dt_fecha_baja;
	
	
	public int getPk_id_rel_prod_doct() {
		return pk_id_rel_prod_doct;
	}


	public void setPk_id_rel_prod_doct(int pk_id_rel_prod_doct) {
		this.pk_id_rel_prod_doct = pk_id_rel_prod_doct;
	}


	public int getFk_producto() {
		return fk_producto;
	}


	public void setFk_producto(int fk_producto) {
		this.fk_producto = fk_producto;
	}


	public int getFk_documento() {
		return fk_documento;
	}


	public void setFk_documento(int fk_documento) {
		this.fk_documento = fk_documento;
	}


	public LocalDate getDt_fecha_operacion() {
		return dt_fecha_operacion;
	}


	public void setDt_fecha_operacion(LocalDate dt_fecha_operacion) {
		this.dt_fecha_operacion = dt_fecha_operacion;
	}


	public int getI_estatus() {
		return i_estatus;
	}


	public void setI_estatus(int i_estatus) {
		this.i_estatus = i_estatus;
	}


	public LocalDate getDt_fecha_baja() {
		return dt_fecha_baja;
	}


	public void setDt_fecha_baja(LocalDate dt_fecha_baja) {
		this.dt_fecha_baja = dt_fecha_baja;
	}



	
}
