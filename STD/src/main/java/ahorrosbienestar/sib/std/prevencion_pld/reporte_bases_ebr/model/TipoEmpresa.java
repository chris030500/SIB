package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PLD_EBR_TIPO_EMPRESAS")
public class TipoEmpresa {
	
	@Id
	@Column(name = "I_EMPRESA_ID")	
	private Integer i_empresa_id;
	
	@Column(name = "VC_NOMBRE_EMPRESA")	
	private String vc_nombre_empresa;
	
	@Column(name = "VC_ESTATUS")	
	private String vc_estatus;
	
	@Column(name = "VC_UBICACION")	
	private String vc_ubicacion;
	
	@Column(name = "VC_SECTOR")	
	private String vc_sector;

	public Integer getI_empresa_id() {
		return i_empresa_id;
	}

	public void setI_empresa_id(Integer i_empresa_id) {
		this.i_empresa_id = i_empresa_id;
	}

	public String getVc_nombre_empresa() {
		return vc_nombre_empresa;
	}

	public void setVc_nombre_empresa(String vc_nombre_empresa) {
		this.vc_nombre_empresa = vc_nombre_empresa;
	}

	public String getVc_estatus() {
		return vc_estatus;
	}

	public void setVc_estatus(String vc_estatus) {
		this.vc_estatus = vc_estatus;
	}

	public String getVc_ubicacion() {
		return vc_ubicacion;
	}

	public void setVc_ubicacion(String vc_ubicacion) {
		this.vc_ubicacion = vc_ubicacion;
	}

	public String getVc_sector() {
		return vc_sector;
	}

	public void setVc_sector(String vc_sector) {
		this.vc_sector = vc_sector;
	}

	@Override
	public String toString() {
		return "TipoEmpresa [i_empresa_id=" + i_empresa_id + ", vc_nombre_empresa=" + vc_nombre_empresa
				+ ", vc_estatus=" + vc_estatus + ", vc_ubicacion=" + vc_ubicacion + ", vc_sector=" + vc_sector + "]";
	}
}
