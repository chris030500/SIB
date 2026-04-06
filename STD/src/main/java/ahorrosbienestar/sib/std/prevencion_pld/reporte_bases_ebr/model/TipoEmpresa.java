package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PLD_EBR_TIPO_EMPRESAS")
public class TipoEmpresa {
	
	@Id
	@Column(name = "I_EMPRESA_ID")	
	private Integer empresaId;
	
	@Column(name = "VC_NOMBRE_EMPRESA")	
	private String nombreEmpresa;
	
	@Column(name = "VC_ESTATUS")	
	private String estatus;
	
	@Column(name = "VC_UBICACION")	
	private String ubicacion;
	
	@Column(name = "VC_SECTOR")	
	private String sector;

	public Integer getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Integer empresaId) {
		this.empresaId = empresaId;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	@Override
	public String toString() {
		return "TipoEmpresa [empresaId=" + empresaId + ", nombreEmpresa=" + nombreEmpresa + ", estatus=" + estatus
				+ ", ubicacion=" + ubicacion + ", sector=" + sector + "]";
	}
}
