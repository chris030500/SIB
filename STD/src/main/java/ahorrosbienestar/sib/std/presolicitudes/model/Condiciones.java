package ahorrosbienestar.sib.std.presolicitudes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "TBL_CONDICIONES_CONVENIOS")
public class Condiciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID")
    private Integer pkId;

    @Column(name = "I_CONDICION")
    private Integer iCondicion;

    @Column(name = "I_EMPRESA")
    private Integer iEmpresa;

    @Column(name = "VC_PRODUCTO", length = 45)
    private String vcProducto;

    @Column(name = "I_TIPOEMP")
    private Integer iTipoemp;

    @Column(name = "I_MONTOMIN")
    private Integer iMontoMin;

    @Column(name = "I_MONTOMAX")
    private Integer iMontoMax;

    @Column(name = "DC_TASA", precision = 12, scale = 2)
    private BigDecimal dcTasa;

    @Column(name = "VC_FRECUENCIA", length = 45)
    private String vcFrecuencia;

    @Column(name = "VC_PLAZO", length = 45)
    private String vcPlazo;

    // Getters and Setters

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getiCondicion() {
        return iCondicion;
    }

    public void setiCondicion(Integer iCondicion) {
        this.iCondicion = iCondicion;
    }

    public Integer getiEmpresa() {
        return iEmpresa;
    }

    public void setiEmpresa(Integer iEmpresa) {
        this.iEmpresa = iEmpresa;
    }

    public String getVcProducto() {
        return vcProducto;
    }

    public void setVcProducto(String vcProducto) {
        this.vcProducto = vcProducto;
    }

    public Integer getiTipoemp() {
        return iTipoemp;
    }

    public void setiTipoemp(Integer iTipoemp) {
        this.iTipoemp = iTipoemp;
    }

    public Integer getiMontoMin() {
        return iMontoMin;
    }

    public void setiMontoMin(Integer iMontoMin) {
        this.iMontoMin = iMontoMin;
    }

    public Integer getiMontoMax() {
        return iMontoMax;
    }

    public void setiMontoMax(Integer iMontoMax) {
        this.iMontoMax = iMontoMax;
    }

    public BigDecimal getDcTasa() {
        return dcTasa;
    }

    public void setDcTasa(BigDecimal dcTasa) {
        this.dcTasa = dcTasa;
    }

    public String getVcFrecuencia() {
        return vcFrecuencia;
    }

    public void setVcFrecuencia(String vcFrecuencia) {
        this.vcFrecuencia = vcFrecuencia;
    }

    public String getVcPlazo() {
        return vcPlazo;
    }

    public void setVcPlazo(String vcPlazo) {
        this.vcPlazo = vcPlazo;
    }
}


