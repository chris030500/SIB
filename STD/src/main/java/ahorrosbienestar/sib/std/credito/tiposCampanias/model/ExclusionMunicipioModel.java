package ahorrosbienestar.sib.std.credito.tiposCampanias.model;

import javax.persistence.*;

@Entity
@Table(name = "TBL_EXCLUSION_MUNICIPIOS", schema = "SIB")
public class ExclusionMunicipioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ID")
    private Integer id;

    @Column(name = "I_CODIGO")
    private Integer codigo;

    @Column(name = "VC_ASENTA", length = 150)
    private String asentamiento;

    @Column(name = "VC_TIPO_ASENTA", length = 100)
    private String tipoAsentamiento;

    @Column(name = "VC_MUNICIPIO", length = 150)
    private String municipio;

    @Column(name = "VC_ESTADO", length = 150)
    private String estado;

    @Column(name = "VC_CIUDAD", length = 150)
    private String ciudad;

    @Column(name = "I_CP")
    private Integer codigoPostal;

    @Column(name = "I_ESTADO")
    private Integer codigoEstado;

    @Column(name = "VC_COBETURA", length = 2)
    private String cobertura;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getAsentamiento() {
        return asentamiento;
    }

    public void setAsentamiento(String asentamiento) {
        this.asentamiento = asentamiento;
    }

    public String getTipoAsentamiento() {
        return tipoAsentamiento;
    }

    public void setTipoAsentamiento(String tipoAsentamiento) {
        this.tipoAsentamiento = tipoAsentamiento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Integer getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodigoEstado(Integer codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }
}
