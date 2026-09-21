package ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.configuracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.dto.OriginacionWsResponse;
import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.service.OriginacionConsultaEstatusService;

@Component
public class OriginacionConsultaEstatusScheduler {

	private static final Logger logger =
	        LoggerFactory.getLogger(OriginacionConsultaEstatusScheduler.class);

	@Autowired
	private OriginacionConsultaEstatusService originacionConsultaEstatusService;

	@Value("${ws.originacion.consulta-estatus.cron:0 0 6 * * *}")
	private String cronExpresion;

	@Scheduled(cron = "${ws.originacion.consulta-estatus.cron:0 0 6 * * *}", zone = "America/Mexico_City")
	public void ejecutarConsultaProgramada() {
		logger.info("Ejecutando consulta programada de originacion. Cron: " + cronExpresion);
		OriginacionWsResponse response = originacionConsultaEstatusService.consultarConFechasPorVigencia();
		logger.info("Respuesta consulta programada originacion. Status: " + response.getStatus() + ", url: "
				+ response.getUrl());

		if (response.getStatus() != 200) {
			logger.error("Error en consulta programada de originacion: " + response.getResponseBody());
		}
	}
}
