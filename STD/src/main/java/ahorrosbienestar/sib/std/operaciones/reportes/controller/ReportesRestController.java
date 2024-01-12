package ahorrosbienestar.sib.std.operaciones.reportes.controller;

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
import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.catalogos.service.CatalogoService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ReportesRestController {

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private static final Logger logger = Logger.getLogger(ReportesRestController.class);

	private static final int idGrupoBusqueda = 15;
	
	private static final int idGrupoGestion = 14;

	@RequestMapping(value = { "/lista_busqueda" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaBusqueda() {
		Map<String, Object> respuesta = new HashMap<>();
		List<Catalogo> listaCatalogos = new ArrayList<Catalogo>();
		String mensajeError = null;
		try {
			listaCatalogos = catalogoService.generarOpciones(idGrupoBusqueda);
			logger.info("Se llenó la lista");
			respuesta.put("lista", listaCatalogos);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@RequestMapping(value = { "/lista_gestiones" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaGestiones() {
		Map<String, Object> respuesta = new HashMap<>();
		List<Catalogo> listaCatalogos = new ArrayList<Catalogo>();
		String mensajeError = null;
		try {
			listaCatalogos = catalogoService.generarOpciones(idGrupoGestion);
			logger.info("Se llenó la lista");
			respuesta.put("lista", listaCatalogos);
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
