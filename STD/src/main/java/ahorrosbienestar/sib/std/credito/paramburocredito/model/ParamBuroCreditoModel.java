package ahorrosbienestar.sib.std.credito.paramburocredito.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ahorrosbienestar.sib.std.credito.tiposCampanias.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Entity
@Table(name = "TBL_PARAM_BURO_CREDITO", schema = "SIB")
public class ParamBuroCreditoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_PARAMETRO_BC")
    private Integer idParametro;

    @OneToOne
    @JoinColumn(name = "FK_CAMPANIA_ID")
    private TiposCampaniasModel idCampania;

    @Column(name = "I_TIPO__EVALUACION")
    private Integer tipoEvaluacion;

    @Column(name = "D_USO_MAXIMO")
    private Double usoMaximo;

    @Column(name = "D_MOB_MAXIMO")
    private Double mobMaximo;

    @Column(name = "D_BCS_MINIMO")
    private Double bcsMinimo;

    @Column(name = "D_ICC_MINIMO")
    private Double iccMinimo;

    @Column(name = "VC_RIESGO_MAXIMO")
    private String riesgoMaximo;
    
    @OneToOne
    @JoinColumn(name = "I_USER_REG")
    private Usuario usuarioRegistra;
    
    @OneToOne
    @JoinColumn(name = "I_USER_MOD")
    private Usuario usuarioModifica;

	public Integer getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(Integer idParametro) {
		this.idParametro = idParametro;
	}

	public TiposCampaniasModel getIdCampania() {
		return idCampania;
	}

	public void setIdCampania(TiposCampaniasModel idCampania) {
		this.idCampania = idCampania;
	}

	public Integer getTipoEvaluacion() {
		return tipoEvaluacion;
	}

	public void setTipoEvaluacion(Integer tipoEvaluacion) {
		this.tipoEvaluacion = tipoEvaluacion;
	}

	public Double getUsoMaximo() {
		return usoMaximo;
	}

	public void setUsoMaximo(Double usoMaximo) {
		this.usoMaximo = usoMaximo;
	}

	public Double getMobMaximo() {
		return mobMaximo;
	}

	public void setMobMaximo(Double mobMaximo) {
		this.mobMaximo = mobMaximo;
	}

	public Double getBcsMinimo() {
		return bcsMinimo;
	}

	public void setBcsMinimo(Double bcsMinimo) {
		this.bcsMinimo = bcsMinimo;
	}

	public Double getIccMinimo() {
		return iccMinimo;
	}

	public void setIccMinimo(Double iccMinimo) {
		this.iccMinimo = iccMinimo;
	}

	public String getRiesgoMaximo() {
		return riesgoMaximo;
	}

	public void setRiesgoMaximo(String riesgoMaximo) {
		this.riesgoMaximo = riesgoMaximo;
	}

	public Usuario getUsuarioRegistra() {
		return usuarioRegistra;
	}

	public void setUsuarioRegistra(Usuario usuarioRegistra) {
		this.usuarioRegistra = usuarioRegistra;
	}

	public Usuario getUsuarioModifica() {
		return usuarioModifica;
	}

	public void setUsuarioModifica(Usuario usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}

}
