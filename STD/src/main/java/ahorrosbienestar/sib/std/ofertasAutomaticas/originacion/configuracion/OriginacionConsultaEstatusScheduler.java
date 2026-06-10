package ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.configuracion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ahorrosbienestar.sib.std.credito.tiposCampanias.service.TiposCampaniasService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.dto.ConsultaEstatusOfertasCampaniasRequest;
import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.dto.OriginacionWsResponse;
import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.service.OriginacionConsultaEstatusService;

@Component
public class OriginacionConsultaEstatusScheduler {

	private static final Logger logger = Logger.getLogger(OriginacionConsultaEstatusScheduler.class);
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Autowired
	private OriginacionConsultaEstatusService originacionConsultaEstatusService;

	@Autowired
	private TiposCampaniasService tiposCampaniasService;

	@Value("${ws.originacion.consulta-estatus.cron:0 0 6 * * *}")
	private String cronExpresion;

	@Scheduled(cron = "${ws.originacion.consulta-estatus.cron:0 0 6 * * *}", zone = "America/Mexico_City")
	public void ejecutarConsultaProgramada() {
		LocalDate diaActual = LocalDate.now();
		Integer vigenciaCampaniaMeses = tiposCampaniasService.obtenerMaximaVigenciaCampaniaActiva();
		int mesesRestar = vigenciaCampaniaMeses != null ? vigenciaCampaniaMeses.intValue() : 0;
		LocalDate fechaInicioProceso = diaActual.minusMonths(mesesRestar);
		String fechaInicio = fechaInicioProceso.format(FORMATTER);
		String fechaFin = diaActual.format(FORMATTER);

		ConsultaEstatusOfertasCampaniasRequest request = new ConsultaEstatusOfertasCampaniasRequest();
		request.setFechaInicio(fechaInicio);
		request.setFechaFin(fechaFin);

		logger.info("Ejecutando consulta programada de originacion. Cron: " + cronExpresion
				+ ", vigenciaMeses: " + mesesRestar + ", fechaInicio: " + fechaInicio + ", fechaFin: " + fechaFin);

		OriginacionWsResponse response = originacionConsultaEstatusService.consultar(request);
		logger.info("Respuesta consulta programada originacion. Status: " + response.getStatus() + ", url: "
				+ response.getUrl());

		if (response.getStatus() != 200) {
			logger.error("Error en consulta programada de originacion: " + response.getResponseBody());
		}
	}
}
