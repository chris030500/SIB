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

    @Column(name = "VC_CONFIGURACION", columnDefinition = "LONGTEXT")
    private String configuracion;
    
	@Column(name = "I_TIPO", columnDefinition = "TINYINT(1)")
	private int i_tipo;
	
	@Column(name = "VC_DES_TIPO", length = 50)
	private String vc_des_tipo;
	
	@Column(name = "VC_NOMBRE_CAMPO", length = 50)
	private String vc_nombre_campo;
    
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

	public int getI_tipo() {
		return i_tipo;
	}

	public void setI_tipo(int i_tipo) {
		this.i_tipo = i_tipo;
	}

	public String getVc_des_tipo() {
		return vc_des_tipo;
	}

	public void setVc_des_tipo(String vc_des_tipo) {
		this.vc_des_tipo = vc_des_tipo;
	}

	public String getVc_nombre_campo() {
		return vc_nombre_campo;
	}

	public void setVc_nombre_campo(String vc_nombre_campo) {
		this.vc_nombre_campo = vc_nombre_campo;
	}
}

