package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PLD_CAT_PERIODOS")
public class PeriodoEbr {
	
	@Id
	@Column(name = "PK_PERIODO_ID")
	private Integer periodoId;
	
	@Column(name = "VC_NOMBRE_PERIODO")
	private String nombrePeriodo;
	
	@Column(name = "I_MES_INICIO")
	private Integer mesInicio;
	
	@Column(name = "I_DIA_INICIO")
	private Integer diaInicio;
	
	@Column(name = "I_MES_FIN")
	private Integer mesFin;
	
	@Column(name = "I_DIA_FIN")
	private Integer diaFin;

	public Integer getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(Integer periodoId) {
		this.periodoId = periodoId;
	}

	public String getNombrePeriodo() {
		return nombrePeriodo;
	}

	public void setNombrePeriodo(String nombrePeriodo) {
		this.nombrePeriodo = nombrePeriodo;
	}

	public Integer getMesInicio() {
		return mesInicio;
	}

	public void setMesInicio(Integer mesInicio) {
		this.mesInicio = mesInicio;
	}

	public Integer getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(Integer diaInicio) {
		this.diaInicio = diaInicio;
	}

	public Integer getMesFin() {
		return mesFin;
	}

	public void setMesFin(Integer mesFin) {
		this.mesFin = mesFin;
	}

	public Integer getDiaFin() {
		return diaFin;
	}

	public void setDiaFin(Integer diaFin) {
		this.diaFin = diaFin;
	}

	@Override
	public String toString() {
		return "PeriodoEbr [periodoId=" + periodoId + ", nombrePeriodo=" + nombrePeriodo + ", mesInicio=" + mesInicio
				+ ", diaInicio=" + diaInicio + ", mesFin=" + mesFin + ", diaFin=" + diaFin + "]";
	}
}
