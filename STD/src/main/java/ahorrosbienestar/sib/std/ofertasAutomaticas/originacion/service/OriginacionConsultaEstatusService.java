package ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.service;

import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ahorrosbienestar.sib.std.config_ws.ws_configuraciones.model.WSConfiguracion;
import ahorrosbienestar.sib.std.config_ws.ws_configuraciones.service.WSConfiguracionService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.dto.ConsultaEstatusOfertasCampaniasRequest;
import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.dto.OriginacionWsResponse;

@Service
public class OriginacionConsultaEstatusService {

	private static final Logger logger = Logger.getLogger(OriginacionConsultaEstatusService.class);
	private static final String DESCRIPCION_WS_ORIGINACION = "Consulta Estatus Ofertas Campanias";

	@Autowired
	private WSConfiguracionService wsConfiguracionService;

	@Autowired
	private OriginacionEstatusOfertaPersistenciaService persistenciaService;

	@Value("${ws.originacion.consulta-estatus.connect-timeout-ms:10000}")
	private int connectTimeout;

	@Value("${ws.originacion.consulta-estatus.read-timeout-ms:30000}")
	private int readTimeout;

	public OriginacionWsResponse consultar(ConsultaEstatusOfertasCampaniasRequest request) {
		Optional<WSConfiguracion> configuracionOpt = wsConfiguracionService
				.buscarConfiguracionActivaPorDescripcion(DESCRIPCION_WS_ORIGINACION);
		if (!configuracionOpt.isPresent()) {
			return new OriginacionWsResponse(400, HttpMethod.POST.name(), null,
					"No se encontro configuracion activa en TBL_WS_CONFIGURACIONES para la descripcion "
							+ DESCRIPCION_WS_ORIGINACION + ".");
		}

		WSConfiguracion configuracion = configuracionOpt.get();
		HttpMethod httpMethod = HttpMethod.POST;
		String url = configuracion.getVcRuta();
		HttpHeaders headers = construirHeaders(configuracion);
		String requestBody = construirBody(request);
		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		try {
			logger.info("Consumir WS originacion: " + httpMethod.name() + " " + url);
			ResponseEntity<String> response = crearRestTemplate().exchange(url, httpMethod, entity, String.class);
			if (response.getStatusCodeValue() == 200 && response.getBody() != null) {
				persistenciaService.guardarRespuesta(request, response.getBody());
			}
			return new OriginacionWsResponse(response.getStatusCodeValue(), httpMethod.name(), url, response.getBody());
		} catch (HttpStatusCodeException e) {
			logger.error("Error HTTP al consumir WS originacion: " + e.getStatusCode().value() + " " + e.getMessage());
			return new OriginacionWsResponse(e.getStatusCode().value(), httpMethod.name(), url, e.getResponseBodyAsString());
		} catch (Exception e) {
			logger.error("Error al consumir WS originacion: " + e.getMessage(), e);
			return new OriginacionWsResponse(500, httpMethod.name(), url, e.getMessage());
		}
	}

	private RestTemplate crearRestTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(connectTimeout);
		factory.setReadTimeout(readTimeout);
		return new RestTemplate(factory);
	}

	private HttpHeaders construirHeaders(WSConfiguracion configuracion) {
		HttpHeaders headers = new HttpHeaders();
		if (configuracion != null && StringUtils.hasText(configuracion.getVcHeader())
				&& StringUtils.hasText(configuracion.getVcPass())) {
			headers.set(configuracion.getVcHeader(), configuracion.getVcPass());
		}
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return headers;
	}

	private String construirBody(ConsultaEstatusOfertasCampaniasRequest request) {
		try {
			LinkedHashMap<String, String> body = new LinkedHashMap<String, String>();
			body.put("fechaInicio", request != null ? request.getFechaInicio() : null);
			body.put("fechaFin", request != null ? request.getFechaFin() : null);
			return new ObjectMapper().writeValueAsString(body);
		} catch (Exception e) {
			logger.error("Error al construir body del WS originacion: " + e.getMessage(), e);
			return "{}";
		}
	}
}
