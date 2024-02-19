package ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CONFIGURACION_PARAMETROS_PRODUCTOS")
public class ConfiguracionSistema {
	
	@Id
    @Column(name = "PK_PARAMETRO")
    private int pkParametro;

    @Column(name = "VC_DESCRIPCION_PARAMETRO", length = 200)
    private String descripcionParametro;

    @Column(name = "VC_CONFIGURACION", length = 200)
    private String configuracion;
    

	@Override
	public String toString() {
		return "ConfiguracionSistema [pkParametro=" + pkParametro + ", descripcionParametro=" + descripcionParametro
				+ ", configuracion=" + configuracion + ", getPkParametro()=" + getPkParametro()
				+ ", getDescripcionParametro()=" + getDescripcionParametro() + ", getConfiguracion()="
				+ getConfiguracion() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public int getPkParametro() {
		return pkParametro;
	}

	public void setPkParametro(int pkParametro) {
		this.pkParametro = pkParametro;
	}

	public String getDescripcionParametro() {
		return descripcionParametro;
	}

	public void setDescripcionParametro(String descripcionParametro) {
		this.descripcionParametro = descripcionParametro;
	}

	public String getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(String configuracion) {
		this.configuracion = configuracion;
	}
    
}
