package ahorrosbienestar.sib.std.operaciones.reportes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesActivosModel;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesPorSucursal;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.service.ClientesActivosService;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.service.ClientesPorActivosService;
import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.catalogos.service.CatalogoService;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.operaciones.reportes.dto.ReporteDTO;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ReportesRestController {

	@Autowired
	private CatalogoService catalogoService;
	
	@Autowired
	private ClientesActivosService clientesActivosService;
	
	@Autowired
	private ClientesPorActivosService clientesPorActivosService;
	
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
	
	@RequestMapping(value = {"/buscar_cliente_activo_sucursal"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaClientesOfertasAutomaticasxSucursal(@RequestParam("idSucursal") int idSucursal) {
		Map<String, Object> respuesta =  new HashMap<>();
		String mensajeError = null;
		List<ClientesPorSucursal> lista = new ArrayList<ClientesPorSucursal>();
		try {
			lista = clientesPorActivosService.listaClientesFiltro(idSucursal);
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
	public ResponseEntity<?> reporteGestiones(@RequestBody ReporteDTO dto) {
		
		ArrayList<String> respuesta = new ArrayList<String>();
		StringBuilder sbGestion = new StringBuilder();
		StringBuilder sbGestion2 = new StringBuilder();
		StringBuilder sbClientes = new StringBuilder();
		StringBuilder sbSucursales = new StringBuilder();
		int tipo = dto.getBusqueda();
				
		switch(tipo) {
			case 1:
				// gestion
	    		dto.getEstatusGestion().forEach(gestion -> {
	    			sbGestion.append(gestion.getId()).append("~gestiones=");
	    		});
	    		
	            if (sbGestion.length() > 0) {
	            	sbGestion.setLength(sbGestion.length() - 11);
	            }
	        	
	            // sucursal
	    		dto.getValores().forEach(valor -> {
	    			sbSucursales.append(valor.getId()).append("~sucursales=");
	    		});
	            
	            if (sbSucursales.length() > 0) {
	            	sbSucursales.setLength(sbSucursales.length() - 12);
	            }
				break;
			case 2:
				// gestion
	    		dto.getEstatusGestion().forEach(gestion -> {
	    			sbGestion2.append(gestion.getId()).append("~gestiones2=");
	    		});
	    		
	            if (sbGestion2.length() > 0) {
	            	sbGestion2.setLength(sbGestion2.length() - 12);
	            }
	        	
	            // clientes
	    		dto.getValores().forEach(valor -> {
	    			sbClientes.append(valor.getId()).append("~clientesActivos=");
	    		});
	            
	            if (sbClientes.length() > 0) {
	            	sbClientes.setLength(sbClientes.length() - 17);
	            }
				break;
			default:
				break;
		}
       
		
		try {
			
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(reporteDeGestion);
			String reporteURL = null;
			reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)
					+ datosReporte.getPk_id_reporte() 
					+ "&parametros=?gestiones=" + sbGestion
					+ "~gestiones2=" + sbGestion2
					+ "~sucursales=" + sbSucursales
					+ "~clientesActivos=" + sbClientes
					+ "~FechaInicio=" + dto.getFechaInicio()
					+ "~FechaFin=" + dto.getFechaFin()
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
