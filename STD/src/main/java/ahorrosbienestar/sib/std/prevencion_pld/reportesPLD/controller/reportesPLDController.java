package ahorrosbienestar.sib.std.prevencion_pld.reportesPLD.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.model.ClientesActivosModel;
import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.service.ClientesActivosService;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.rptProductos.controller.ReportesProductosConstroller;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;

//@Controller
//@RequestMapping("reportesPLD")
////////////////----DES-24-0002 Historico de Operaciones----////////////////
////////////////----viviana.zamudio-------------------------////////////////
@RestController
@AllArgsConstructor
public class reportesPLDController {
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired 
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired
	private ClientesActivosService clientesActivosService;
	
	@Autowired
	private static final Logger logger = Logger.getLogger(ReportesProductosConstroller.class);
	
	private static String nombreReporte = "";
	private static String extensionReporte = "";
	private static final String controllerReporte = "generarReporte";
	private static final int opcBusquedaGrupo = 22;
	private static final int opcBusquedaGrupo2 = 25;
	private static final String nombrePantalla = "Reportes PLD";
	private static final String rutaError = "errores/error_403";
	private String rutaDireccion="";
	
	//@GetMapping("/historicoOperaciones")
	public String inicioReportes(Model model) {
		try {
			
			if(validarAcceso() == 1) {
				model.addAttribute("cat_reportes_pld", iCatalogoRepository.generarOpciones(opcBusquedaGrupo));
				model.addAttribute("cat_reportes_pld_ext", iCatalogoRepository.generarOpciones(opcBusquedaGrupo2));
				//rutaDireccion = "reportesProductos/reportesProducto";
				rutaDireccion = "reportesPLD/reportePLD";
			}else {
				rutaDireccion = rutaError;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return null;
		}	
	}
	
	//@GetMapping("/listaClientesActivos")
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
	
	
	//@PostMapping("/reporteHistoricoPLD")
	public ResponseEntity<?> reporteHistoricoPLD(
			@RequestParam("idReporte") Integer idReporte
			,@RequestParam("dtFechaInicio") String dtFechaInicio
			,@RequestParam("dtFechaFin") String dtFechaFin
			,@RequestParam("nombreReporteSelect") Integer nombreReporteSelect
			,@RequestParam("extension") Integer extension){
		
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			LocalDate fecha = LocalDate.now();
			
			if(nombreReporteSelect == 1) {
				
				nombreReporte = "rpt_historico_operaciones_inusuales";
				
			}else if(nombreReporteSelect == 2) {
				
				nombreReporte = "rpt_historico_operaciones_relevantes";
			}
			
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(nombreReporte);
			
			if(extension == 2) {
				
				extensionReporte = ".pdf";
				
			}else if(extension == 1 ) {
				
				extensionReporte = ".xls";
			}
			
			//String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)+datosReporte.getPk_id_reporte()+"&parametros=?FechaInicio="
					//+dtFechaInicio+"~FechaFin="+dtFechaFin+"&tipoDocumento="+datosReporte.getVc_extension()+"&identificadorDoc=OA";
			
			String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)+datosReporte.getPk_id_reporte()+"&parametros=?FechaInicio="
					+dtFechaInicio+"~FechaFin="+dtFechaFin+"&tipoDocumento="+extensionReporte+"&identificadorDoc=OA";
					
			respuesta.add(reporteURL);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}
				
		
	}
	
	/***
	 * 
	 * @return
	 */
	public int validarAcceso() {
		
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacion(nombrePantalla));
			
			
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return 0;
		}
		
	}

}
