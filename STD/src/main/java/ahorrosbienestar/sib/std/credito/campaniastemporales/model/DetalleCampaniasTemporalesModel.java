package ahorrosbienestar.sib.std.credito.campaniastemporales.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;

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
	private Integer creditoId;

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

	@Column(name = "I_USER_REG")
	private Integer userReg;
	
	
}
