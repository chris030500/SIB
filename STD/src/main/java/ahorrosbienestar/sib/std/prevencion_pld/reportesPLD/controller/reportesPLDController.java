package ahorrosbienestar.sib.std.prevencion_pld.reportesPLD.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.rptProductos.controller.ReportesProductosConstroller;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("reportesPLD")
////////////////----DES-24-0002 Historico de Operaciones----////////////////
////////////////----viviana.zamudio-------------------------////////////////
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
	private static final Logger logger = Logger.getLogger(ReportesProductosConstroller.class);
	
	private static String nombreReporte = "";
	private static final String controllerReporte = "generarReporte";
	private static final int opcBusquedaGrupo = 22;
	private static final String nombrePantalla = "Reportes PLD";
	private static final String rutaError = "errores/error_403";
	private String rutaDireccion="";
	
	@GetMapping("/historicoOperaciones")
	public String inicioReportes(Model model) {
		try {
			
			if(validarAcceso() == 1) {
				model.addAttribute("cat_reportes_pld", iCatalogoRepository.generarOpciones(opcBusquedaGrupo));
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
	
	@PostMapping("/reporteHistoricoPLD")
	public ResponseEntity<?> reporteHistoricoPLD(
			@RequestParam("idReporte") Integer idReporte
			,@RequestParam("dtFechaInicio") String dtFechaInicio
			,@RequestParam("dtFechaFin") String dtFechaFin
			,@RequestParam("nombreReporteSelect") Integer nombreReporteSelect){
		
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			LocalDate fecha = LocalDate.now();
			
			if(nombreReporteSelect == 1) {
				
				nombreReporte = "rpt_historico_operaciones_inusuales";
				
			}else if(nombreReporteSelect == 2) {
				
				nombreReporte = "rpt_historico_operaciones_relevantes";
			}
			
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(nombreReporte);
			
			String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)+datosReporte.getPk_id_reporte()+"&parametros=?FechaInicio="
					+dtFechaInicio+"~FechaFin="+dtFechaFin+"&tipoDocumento="+datosReporte.getVc_extension()+"&identificadorDoc=OA";
					
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
