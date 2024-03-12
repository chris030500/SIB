package ahorrosbienestar.sib.std.cat_catalogos.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.catalogos.service.CatalogoService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class CatCatalogosController {

	@Autowired
	private CatalogoService catalogoService;

	private static final int grupoAccion = 23;
	private static final int grupoBloqueo = 24;
	private static final int grupoTipoGes = 26;

	private static final Logger logger = Logger.getLogger(CatCatalogosController.class);

	@RequestMapping(value = { "/lista_accionRealizar" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaAccionRealizar() {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<Catalogo> lista = new ArrayList<Catalogo>();
		try {
			lista = catalogoService.generarOpciones(grupoAccion);
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	@RequestMapping(value = { "/lista_bloqueo" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaBloqueoLista() {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<Catalogo> lista = new ArrayList<Catalogo>();
		try {
			lista = catalogoService.generarOpciones(grupoBloqueo);
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}
	
	@RequestMapping(value = { "/lista_tipoGes" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaTipoGestion() {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<Catalogo> lista = new ArrayList<Catalogo>();
		try {
			lista = catalogoService.generarOpciones(grupoTipoGes);
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}
}
