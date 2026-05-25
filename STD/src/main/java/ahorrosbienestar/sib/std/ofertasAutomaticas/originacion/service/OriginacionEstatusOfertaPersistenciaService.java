package ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.dto.ConsultaEstatusOfertasCampaniasRequest;
import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.model.OriginacionEstatusOfertaWs;
import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.repository.IHisClientesConCampaniaRepository;
import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.repository.IOriginacionEstatusOfertaWsRepository;

@Service
public class OriginacionEstatusOfertaPersistenciaService {

	private static final Logger logger = Logger.getLogger(OriginacionEstatusOfertaPersistenciaService.class);
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

	@Autowired
	private IOriginacionEstatusOfertaWsRepository repository;

	@Autowired
	private IHisClientesConCampaniaRepository hisClientesConCampaniaRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Transactional
	public void guardarRespuesta(ConsultaEstatusOfertasCampaniasRequest request, String responseBody) {
		try {
			String sanitized = sanitizarJson(responseBody);
			JsonNode root = objectMapper.readTree(sanitized);
			JsonNode detalle = root.path("detalleEstatusOfertaBean");

			if (!detalle.isArray() || detalle.size() == 0) {
				logger.info("La respuesta del WS originacion no contiene registros para guardar.");
				return;
			}

			String codigoRespuesta = texto(root, "codigoRespuesta");
			String mensajeRespuesta = texto(root, "mensajeRespuesta");
			LocalDate fechaConsulta = LocalDate.now();
			String horaConsulta = LocalTime.now().format(TIME_FORMATTER);
			List<OriginacionEstatusOfertaWs> registros = new ArrayList<OriginacionEstatusOfertaWs>();

			Iterator<JsonNode> iterator = detalle.elements();
			while (iterator.hasNext()) {
				JsonNode item = iterator.next();
				OriginacionEstatusOfertaWs registro = new OriginacionEstatusOfertaWs();
				registro.setIdOferta(entero(item, "idOferta"));
				registro.setVcEstatusOferta(texto(item, "estatusOferta"));
				registro.setVcEstatusVenta(texto(item, "estatusVenta"));
				registro.setIdVenta(entero(item, "idVenta"));
				registro.setVcIdExtraccion(texto(item, "idExtraccion"));
				registro.setVcFechaEstatusOferta(texto(item, "dtFechaEstatusOferta"));
				registro.setVcFechaEstatusVenta(texto(item, "dtFechaEstatusVenta"));
				registro.setVcUsuarioIdModifico(texto(item, "usuarioIDModifico"));
				registro.setVcCodigoRespuesta(codigoRespuesta);
				registro.setVcMensajeRespuesta(mensajeRespuesta);
				registro.setVcFechaInicioConsulta(request != null ? request.getFechaInicio() : null);
				registro.setVcFechaFinConsulta(request != null ? request.getFechaFin() : null);
				registro.setDtFechaConsulta(fechaConsulta);
				registro.setVcHoraConsulta(horaConsulta);
				registro.setVcJsonRegistro(item.toString());
				registros.add(registro);
			}

			repository.saveAll(registros);
			logger.info("Se guardaron " + registros.size() + " registros de originacion en BD.");

			int actualizados = 0;
			for (OriginacionEstatusOfertaWs registro : registros) {
				if (registro.getIdOferta() != null) {
					actualizados += hisClientesConCampaniaRepository
							.actualizarEstatusMcPorIdClientesConCampania(registro.getIdOferta());
				}
			}
			logger.info("Se actualizaron " + actualizados
					+ " registros en TBL_HIS_CLIENTES_CON_CAMPANIA con I_ESTATUS_MC = 0.");

			ejecutarSpCancelarOfertaVigente();
		} catch (Exception e) {
			logger.error("Error al guardar respuesta del WS originacion en BD: " + e.getMessage(), e);
		}
	}

	private void ejecutarSpCancelarOfertaVigente() {
		Connection connection = null;
		CallableStatement callableStatement = null;

		try {
			connection = jdbcTemplate.getDataSource().getConnection();
			callableStatement = connection.prepareCall("CALL SIB.SP_CANCELAR_OFERTA_VIGENTE();");
			callableStatement.execute();
			logger.info("Se ejecuto correctamente el SP SIB.SP_CANCELAR_OFERTA_VIGENTE.");
		} catch (SQLException e) {
			logger.error("Error al ejecutar el SP SIB.SP_CANCELAR_OFERTA_VIGENTE: " + e.getMessage(), e);
		} finally {
			if (callableStatement != null) {
				try {
					callableStatement.close();
				} catch (SQLException e) {
					logger.error("Error al cerrar CallableStatement de SP_CANCELAR_OFERTA_VIGENTE: " + e.getMessage());
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					logger.error("Error al cerrar conexion de SP_CANCELAR_OFERTA_VIGENTE: " + e.getMessage());
				}
			}
		}
	}

	private String sanitizarJson(String json) {
		String sanitized = json;
		sanitized = sanitized.replaceAll(":\\s*(,|})", ": null$1");
		sanitized = sanitized.replaceAll(":\\s*([A-Za-z_][A-Za-z0-9_]*)(\\s*[,}])", ": \"$1\"$2");
		return sanitized;
	}

	private String texto(JsonNode node, String field) {
		JsonNode value = node.path(field);
		return value.isMissingNode() || value.isNull() ? null : value.asText();
	}

	private Integer entero(JsonNode node, String field) {
		JsonNode value = node.path(field);
		if (value.isMissingNode() || value.isNull() || value.asText().trim().isEmpty()) {
			return null;
		}
		return value.asInt();
	}
}
