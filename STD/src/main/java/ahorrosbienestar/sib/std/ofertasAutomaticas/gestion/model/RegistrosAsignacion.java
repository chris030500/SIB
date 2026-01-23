package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class RegistrosAsignacion {
	

	@Id
	@Column(name = "FK_ID_SUCURSAL")
	private int fk_id_sucursal;
	
	@Column(name = "VC_TOTAL")
	private int vc_total;
	
	@Column(name = "VC_NOMBRESUCURSAL")
	private String vc_nombresucursal;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHAINICIO")
	private LocalDate dt_fechainicio;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "VC_FECHAFIN")
	private LocalDate dt_fechafin;

	public int getVc_total() {
		return vc_total;
	}

	public void setVc_total(int vc_total) {
		this.vc_total = vc_total;
	}

	public int getFk_id_sucursal() {
		return fk_id_sucursal;
	}

	public void setFk_id_sucursal(int fk_id_sucursal) {
		this.fk_id_sucursal = fk_id_sucursal;
	}

	public String getVc_nombresucursal() {
		return vc_nombresucursal;
	}

	public void setVc_nombresucursal(String vc_nombresucursal) {
		this.vc_nombresucursal = vc_nombresucursal;
	}

	public LocalDate getDt_fechainicio() {
		return dt_fechainicio;
	}

	public void setDt_fechainicio(LocalDate dt_fechainicio) {
		this.dt_fechainicio = dt_fechainicio;
	}

	public LocalDate getDt_fechafin() {
		return dt_fechafin;
	}

	public void setDt_fechafin(LocalDate dt_fechafin) {
		this.dt_fechafin = dt_fechafin;
	}

}

