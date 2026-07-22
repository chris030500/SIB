package ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "TBL_WS_ORIGINACION_ESTATUS_OFERTA")
public class OriginacionEstatusOfertaWs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_ID")
	private Integer pkId;

	@Column(name = "ID_OFERTA")
	private Integer idOferta;

	@Column(name = "VC_ESTATUS_OFERTA", columnDefinition = "VARCHAR(20)")
	private String vcEstatusOferta;

	@Column(name = "VC_ESTATUS_VENTA", columnDefinition = "VARCHAR(100)")
	private String vcEstatusVenta;

	@Column(name = "ID_VENTA")
	private Integer idVenta;

	@Column(name = "VC_ID_EXTRACCION", columnDefinition = "VARCHAR(30)")
	private String vcIdExtraccion;

	@Column(name = "VC_FECHA_ESTATUS_OFERTA", columnDefinition = "VARCHAR(50)")
	private String vcFechaEstatusOferta;

	@Column(name = "VC_FECHA_ESTATUS_VENTA", columnDefinition = "VARCHAR(50)")
	private String vcFechaEstatusVenta;

	@Column(name = "VC_USUARIO_ID_MODIFICO", columnDefinition = "VARCHAR(50)")
	private String vcUsuarioIdModifico;

	@Column(name = "VC_CODIGO_RESPUESTA", columnDefinition = "VARCHAR(20)")
	private String vcCodigoRespuesta;

	@Column(name = "VC_MENSAJE_RESPUESTA", columnDefinition = "VARCHAR(255)")
	private String vcMensajeRespuesta;

	@Column(name = "VC_FECHA_INICIO_CONSULTA", columnDefinition = "VARCHAR(10)")
	private String vcFechaInicioConsulta;

	@Column(name = "VC_FECHA_FIN_CONSULTA", columnDefinition = "VARCHAR(10)")
	private String vcFechaFinConsulta;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DT_FECHA_CONSULTA")
	private LocalDate dtFechaConsulta;

	@Column(name = "VC_HORA_CONSULTA", columnDefinition = "VARCHAR(8)")
	private String vcHoraConsulta;

	@Column(name = "VC_JSON_REGISTRO", columnDefinition = "MEDIUMTEXT")
	private String vcJsonRegistro;

	public Integer getPkId() {
		return pkId;
	}

	public void setPkId(Integer pkId) {
		this.pkId = pkId;
	}

	public Integer getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(Integer idOferta) {
		this.idOferta = idOferta;
	}

	public String getVcEstatusOferta() {
		return vcEstatusOferta;
	}

	public void setVcEstatusOferta(String vcEstatusOferta) {
		this.vcEstatusOferta = vcEstatusOferta;
	}

	public String getVcEstatusVenta() {
		return vcEstatusVenta;
	}

	public void setVcEstatusVenta(String vcEstatusVenta) {
		this.vcEstatusVenta = vcEstatusVenta;
	}

	public Integer getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}

	public String getVcIdExtraccion() {
		return vcIdExtraccion;
	}

	public void setVcIdExtraccion(String vcIdExtraccion) {
		this.vcIdExtraccion = vcIdExtraccion;
	}

	public String getVcFechaEstatusOferta() {
		return vcFechaEstatusOferta;
	}

	public void setVcFechaEstatusOferta(String vcFechaEstatusOferta) {
		this.vcFechaEstatusOferta = vcFechaEstatusOferta;
	}

	public String getVcFechaEstatusVenta() {
		return vcFechaEstatusVenta;
	}

	public void setVcFechaEstatusVenta(String vcFechaEstatusVenta) {
		this.vcFechaEstatusVenta = vcFechaEstatusVenta;
	}

	public String getVcUsuarioIdModifico() {
		return vcUsuarioIdModifico;
	}

	public void setVcUsuarioIdModifico(String vcUsuarioIdModifico) {
		this.vcUsuarioIdModifico = vcUsuarioIdModifico;
	}

	public String getVcCodigoRespuesta() {
		return vcCodigoRespuesta;
	}

	public void setVcCodigoRespuesta(String vcCodigoRespuesta) {
		this.vcCodigoRespuesta = vcCodigoRespuesta;
	}

	public String getVcMensajeRespuesta() {
		return vcMensajeRespuesta;
	}

	public void setVcMensajeRespuesta(String vcMensajeRespuesta) {
		this.vcMensajeRespuesta = vcMensajeRespuesta;
	}

	public String getVcFechaInicioConsulta() {
		return vcFechaInicioConsulta;
	}

	public void setVcFechaInicioConsulta(String vcFechaInicioConsulta) {
		this.vcFechaInicioConsulta = vcFechaInicioConsulta;
	}

	public String getVcFechaFinConsulta() {
		return vcFechaFinConsulta;
	}

	public void setVcFechaFinConsulta(String vcFechaFinConsulta) {
		this.vcFechaFinConsulta = vcFechaFinConsulta;
	}

	public LocalDate getDtFechaConsulta() {
		return dtFechaConsulta;
	}

	public void setDtFechaConsulta(LocalDate dtFechaConsulta) {
		this.dtFechaConsulta = dtFechaConsulta;
	}

	public String getVcHoraConsulta() {
		return vcHoraConsulta;
	}

	public void setVcHoraConsulta(String vcHoraConsulta) {
		this.vcHoraConsulta = vcHoraConsulta;
	}

	public String getVcJsonRegistro() {
		return vcJsonRegistro;
	}

	public void setVcJsonRegistro(String vcJsonRegistro) {
		this.vcJsonRegistro = vcJsonRegistro;
	}
}
