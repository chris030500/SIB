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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesActivosModel;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.service.ClientesActivosService;
import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.catalogos.service.CatalogoService;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import groovy.transform.Undefined;
import groovyjarjarantlr4.v4.parse.ANTLRParser.finallyClause_return;
import groovyjarjarantlr4.v4.parse.ANTLRParser.id_return;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ReportesRestController {

	@Autowired
	private CatalogoService catalogoService;
	
	@Autowired
	private ClientesActivosService clientesActivosService;
	
	@Autowired
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;

	@Autowired
	private static final Logger logger = Logger.getLogger(ReportesRestController.class);

	private static final int idGrupoBusqueda = 15;
	
	private static final int idGrupoGestion = 14;
	
	private static final String reporteDeGestion = "reporteDeGestion";
	
	private static final String controllerReporte = "generarReporte";

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
	
	
	@RequestMapping(value = {"/buscar_cliente_activo"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaClientesOfertasAutomaticas(@RequestParam("valor") String valor) {
		Map<String, Object> respuesta =  new HashMap<>();
		String mensajeError = null;
		List<ClientesActivosModel> lista = new ArrayList<ClientesActivosModel>();
		try {
			lista = clientesActivosService.listaClientes(valor);
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
	
	
	@RequestMapping(value = {"/reporte_gestion"}, method = RequestMethod.POST)
	public ResponseEntity<?> reporteGestiones(@RequestParam("gestion") String gestion,
											  @RequestParam("sucursal") String sucursal,
											  @RequestParam("cliente") String cliente,
											  @RequestParam("fechaInicio") String fechaInicio,
											  @RequestParam("fechaFin") String fechaFin) {
		ArrayList<String> respuesta = new ArrayList<String>();
		cliente = cliente.equals("undefined") ? "" : cliente;
		sucursal = sucursal.equals("undefined") ? "" : sucursal;
		try {
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(reporteDeGestion);
			String reporteURL = null;
			reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)
					+ datosReporte.getPk_id_reporte() 
					+ "&parametros=?gestion=" + gestion
					+ "~sucursal=" + sucursal
					+ "~cliente=" + cliente
					+ "~FechaInicio=" + fechaInicio
					+ "~FechaFin=" + fechaFin
					+ "&tipoDocumento=" + datosReporte.getVc_extension()
					+ "&identificadorDoc=OA";
			respuesta.add(reporteURL);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error:" + e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}
	}
}
