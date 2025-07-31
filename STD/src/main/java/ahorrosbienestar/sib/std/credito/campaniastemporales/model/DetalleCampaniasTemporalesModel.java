package ahorrosbienestar.sib.std.credito.campaniastemporales.model;

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
@Table(name = "TBL_DETALLE_CAMPANIAS_TEMPORAL", schema = "SIB")
public class DetalleCampaniasTemporalesModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID_DETALLE")
	private Integer idDetalle;
	
	@OneToOne
	@JoinColumn(name = "FK_CAMPANIAS")
	private TiposCampaniasModel fkCampanias;
	
	@Column(name = "FK_PROCESO")
	private Integer fkProceso;

	@Column(name = "I_CLIENTEID")
	private Integer clienteId;

	@Column(name = "I_SOLICITUD_CREDITOID_ORIGEN")
	private Integer solicitudCreditoIdOrigen;

	@Column(name = "I_CREDITOID")
	private Long creditoId;

	@Column(name = "D_MONTO_CREDITO_ORIGEN")
	private Double montoCreditoOrigen;

	@Column(name = "I_PLAZO_OFERTAID")
	private Integer plazoOfertaId;

	@Column(name = "I_NUMERO_CUOTAS")
	private Integer numeroCuotas;

	@Column(name = "C_FRECUENCIA_OFERTA")
	private String frecuenciaOferta;

	@Column(name = "C_TIPO_CREDITO")
	private String tipoCredito;

	@Column(name = "D_PORCENTAJ_INCREMENTO_OFERTA")
	private Double porcentajeIncrementoOferta;

	@Column(name = "D_MONTO_OFERTA")
	private Double montoOferta;

	@Column(name = "D_TASA_ORDINARIA_OFERTA")
	private Double tasaOrdinariaOferta;

	@Column(name = "D_CUOTA_OFERTA")
	private Double cuotaOferta;

	@Column(name = "I_PRODUCTOID_OFERTA")
	private Integer productoIdOferta;

	@OneToOne
	@JoinColumn(name = "I_USER_REG")
	private Usuario userReg;
	

	public Integer getIdDetalle() {
		return idDetalle;
	}

	public void setIdDetalle(Integer idDetalle) {
		this.idDetalle = idDetalle;
	}

	public TiposCampaniasModel getFkCampanias() {
		return fkCampanias;
	}

	public void setFkCampanias(TiposCampaniasModel fkCampanias) {
		this.fkCampanias = fkCampanias;
	}

	public Integer getFkProceso() {
		return fkProceso;
	}

	public void setFkProceso(Integer fkProceso) {
		this.fkProceso = fkProceso;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Integer getSolicitudCreditoIdOrigen() {
		return solicitudCreditoIdOrigen;
	}

	public void setSolicitudCreditoIdOrigen(Integer solicitudCreditoIdOrigen) {
		this.solicitudCreditoIdOrigen = solicitudCreditoIdOrigen;
	}


	public Long getCreditoId() {
		return creditoId;
	}

	public void setCreditoId(Long creditoId) {
		this.creditoId = creditoId;
	}

	public Double getMontoCreditoOrigen() {
		return montoCreditoOrigen;
	}

	public void setMontoCreditoOrigen(Double montoCreditoOrigen) {
		this.montoCreditoOrigen = montoCreditoOrigen;
	}

	public Integer getPlazoOfertaId() {
		return plazoOfertaId;
	}

	public void setPlazoOfertaId(Integer plazoOfertaId) {
		this.plazoOfertaId = plazoOfertaId;
	}

	public Integer getNumeroCuotas() {
		return numeroCuotas;
	}

	public void setNumeroCuotas(Integer numeroCuotas) {
		this.numeroCuotas = numeroCuotas;
	}

	public String getFrecuenciaOferta() {
		return frecuenciaOferta;
	}

	public void setFrecuenciaOferta(String frecuenciaOferta) {
		this.frecuenciaOferta = frecuenciaOferta;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public Double getPorcentajeIncrementoOferta() {
		return porcentajeIncrementoOferta;
	}

	public void setPorcentajeIncrementoOferta(Double porcentajeIncrementoOferta) {
		this.porcentajeIncrementoOferta = porcentajeIncrementoOferta;
	}

	public Double getMontoOferta() {
		return montoOferta;
	}

	public void setMontoOferta(Double montoOferta) {
		this.montoOferta = montoOferta;
	}

	public Double getTasaOrdinariaOferta() {
		return tasaOrdinariaOferta;
	}

	public void setTasaOrdinariaOferta(Double tasaOrdinariaOferta) {
		this.tasaOrdinariaOferta = tasaOrdinariaOferta;
	}

	public Double getCuotaOferta() {
		return cuotaOferta;
	}

	public void setCuotaOferta(Double cuotaOferta) {
		this.cuotaOferta = cuotaOferta;
	}

	public Integer getProductoIdOferta() {
		return productoIdOferta;
	}

	public void setProductoIdOferta(Integer productoIdOferta) {
		this.productoIdOferta = productoIdOferta;
	}

	public Usuario getUserReg() {
		return userReg;
	}

	public void setUserReg(Usuario userReg) {
		this.userReg = userReg;
	}
	
	
}
