package ahorrosbienestar.sib.std.credito.paramburocredito.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PARAM_BURO_CREDITO", schema = "SIB")
public class ParamBuroCredito {

    @Id
    @Column(name = "PK_PARAMETRO_BC", nullable = false)
    private int pkParametroBc;

    @Column(name = "VC_DESCRIPCION_PARAMETRO", length = 200)
    private String descripcionParametro;

    @Column(name = "VC_CONFIGURACION", columnDefinition = "LONGTEXT")
    private String configuracion;

    @Column(name = "I_TIPO")
    private Integer tipo;

    @Column(name = "VC_DES_TIPO", length = 50)
    private String descripcionTipo;

    @Column(name = "VC_NOMBRE_CAMPO", length = 50)
    private String nombreCampo;

    @Override
	public String toString() {
		return "ConfiguracionSistema [pkParametroBc=" + pkParametroBc + ", descripcionParametro=" + descripcionParametro
				+ ", configuracion=" + configuracion + ", getPkParametroBc()=" + getPkParametroBc()
				+ ", getDescripcionParametro()=" + getDescripcionParametro() + ", getConfiguracion()="
				+ getConfiguracion() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
    
    // Getters y Setters

    public int getPkParametroBc() {
        return pkParametroBc;
    }

    public void setPkParametroBc(int pkParametroBc) {
        this.pkParametroBc = pkParametroBc;
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

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
    }

    public String getNombreCampo() {
        return nombreCampo;
    }

    public void setNombreCampo(String nombreCampo) {
        this.nombreCampo = nombreCampo;
    }
}
