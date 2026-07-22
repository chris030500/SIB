package ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_CONTROL_REPORTE_JASPER")
public class ControlReporteJasper {
	
	@Id
	@Column(name = "PK_I_CONTROL_REPORTE_ID")
	private Integer pkIControlReporteId;
	
	@Column(name = "I_ESTATUS_CONTROL_REPORTE")
	private Integer iEstatusControlReporte;
	
	@Column(name = "VC_NOMBRE_CONTROL_REPORTE")
	private String vcNombreControlReporte;
	
	@Column(name = "I_ESTATUS_ANTERIOR_CONTROL_REPORTE")
	private Integer iEstatusAnteriorControlReporte;

	public Integer getPkIControlReporteId() {
		return pkIControlReporteId;
	}

	public void setPkIControlReporteId(Integer pkIControlReporteId) {
		this.pkIControlReporteId = pkIControlReporteId;
	}

	public Integer getiEstatusControlReporte() {
		return iEstatusControlReporte;
	}

	public void setiEstatusControlReporte(Integer iEstatusControlReporte) {
		this.iEstatusControlReporte = iEstatusControlReporte;
	}

	public String getVcNombreControlReporte() {
		return vcNombreControlReporte;
	}

	public void setVcNombreControlReporte(String vcNombreControlReporte) {
		this.vcNombreControlReporte = vcNombreControlReporte;
	}

	public Integer getiEstatusAnteriorControlReporte() {
		return iEstatusAnteriorControlReporte;
	}

	public void setiEstatusAnteriorControlReporte(Integer iEstatusAnteriorControlReporte) {
		this.iEstatusAnteriorControlReporte = iEstatusAnteriorControlReporte;
	}

	@Override
	public String toString() {
		return "ControlReporteJasper [pkIControlReporteId=" + pkIControlReporteId + ", iEstatusControlReporte="
				+ iEstatusControlReporte + ", vcNombreControlReporte=" + vcNombreControlReporte
				+ ", iEstatusAnteriorControlReporte=" + iEstatusAnteriorControlReporte + "]";
	}
}
