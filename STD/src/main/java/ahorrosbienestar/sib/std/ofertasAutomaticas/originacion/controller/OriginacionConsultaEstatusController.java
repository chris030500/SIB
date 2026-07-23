package ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.dto.ConsultaEstatusOfertasCampaniasRequest;
import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.dto.OriginacionWsResponse;
import ahorrosbienestar.sib.std.ofertasAutomaticas.originacion.service.OriginacionConsultaEstatusService;

@RestController
@RequestMapping("/ofertasAutomaticas/originacion")
public class OriginacionConsultaEstatusController {

	@Autowired
	private OriginacionConsultaEstatusService originacionConsultaEstatusService;

	@PostMapping("/consulta-estatus")
	public ResponseEntity<OriginacionWsResponse> consultarEstatus(
			@RequestBody(required = false) ConsultaEstatusOfertasCampaniasRequest request) {
		OriginacionWsResponse response = originacionConsultaEstatusService.consultar(request);
		return ResponseEntity.status(HttpStatus.valueOf(response.getStatus())).body(response);
	}
}
