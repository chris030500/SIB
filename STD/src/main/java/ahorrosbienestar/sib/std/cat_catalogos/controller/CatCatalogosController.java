package ahorrosbienestar.sib.std.cat_catalogos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.cat_catalogos.accion_realizar.model.AccionRealizarModel;
import ahorrosbienestar.sib.std.cat_catalogos.accion_realizar.service.AccionRealizarService;
import ahorrosbienestar.sib.std.cat_catalogos.bloqueo_listas_negras.model.BloqueoListasNegrasModel;
import ahorrosbienestar.sib.std.cat_catalogos.bloqueo_listas_negras.service.BloqueoListasNegrasService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CatCatalogosController {

	@Autowired
	private AccionRealizarService accionRealizarService;
	
	@Autowired
	private BloqueoListasNegrasService bloqueoListasNegrasService;
	
	private static final Logger logger = Logger.getLogger(CatCatalogosController.class);

	
	@RequestMapping(value = {"/lista_accionRealizar"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaAccionRealizar() {
		Map<String, Object> respuesta =  new HashMap<>();
		String mensajeError = null;
		List<AccionRealizarModel> lista = new ArrayList<AccionRealizarModel>();
		try {
			lista = accionRealizarService.listaCatAccionRealizar();
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
	
	@RequestMapping(value = {"/lista_bloqueo"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaBloqueo() {
		Map<String, Object> respuesta =  new HashMap<>();
		String mensajeError = null;
		List<BloqueoListasNegrasModel> lista = new ArrayList<BloqueoListasNegrasModel>();
		try {
			lista = bloqueoListasNegrasService.listaCatBloqueo();
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
}
