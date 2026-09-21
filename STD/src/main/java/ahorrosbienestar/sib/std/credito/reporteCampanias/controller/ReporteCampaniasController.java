package ahorrosbienestar.sib.std.credito.reporteCampanias.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.service.TiposCampaniasService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("credito")
public class ReporteCampaniasController {
	
	private static final Logger logger =
	        LoggerFactory.getLogger(ReporteCampaniasController.class);
	
	private static final String controllerReporte = "generarReporte";
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;

	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private TiposCampaniasService tiposCampaniasService;
	
	@Autowired
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	List<Usuario> usuario = null;
	
	
	@GetMapping("/reportesCampanias")
	public String indexReportes(Model model) {
		
		try {
			String rutaDireccion;
			
			if(validarAcceso(Constantes.REPORTE_CAMPANIAS) == 1) {
				model.addAttribute("cat_estatus", iCatalogoRepository.generarOpciones(Constantes.CAT_ESTATUS_CAMPANIAS));	
				model.addAttribute("cat_reportes", iCatalogoRepository.generarOpciones(Constantes.CAT_REPORTES_CAMPANIAS));
				model.addAttribute("cat_campanias",tiposCampaniasService.campaniasReportes());
				rutaDireccion = Constantes.REPORTE_CAMPANIAS;
				
			}else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}	
	}
	
	//llenar el combo opcion multiple de campañas
		@GetMapping("/api/catMulTipoCampania")
		@ResponseBody
	    public ResponseEntity<List<TiposCampaniasModel>> getTiposCampanias() {
	     try {
	    	 List<TiposCampaniasModel> credenciales = tiposCampaniasService.getTiposCampaniasModel();
	    	 
			 if (credenciales == null) {
			        return ResponseEntity.noContent().build(); // Devuelve 204
			    }else {
				    return ResponseEntity.ok(credenciales); // Devuelve JSON 200
			    }
	    			 
	     }catch(Exception e) {
	    	 
	    	 logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
				return null;
	     }
			
	        //return ResponseEntity.ok(tiposCampaniasService.obtenerCampaniasActivos());
	    }
		
		@RequestMapping(value = { "/reporteExclusionesInternas" }, method = RequestMethod.POST)
		public ResponseEntity<?> reporteExclusionesInternas(@RequestParam("dtFechaInicio") String dtFechaInicio, 
														@RequestParam("dtFechaFin") String dtFechaFin) {

			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				
				ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_EXCLUSIONES_INTERNAS);
				
				String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)
						+ datosReporte.getPk_id_reporte() + "&parametros=?FechaInicio=" + dtFechaInicio + "~FechaFin="
						+ dtFechaFin + "&tipoDocumento=" + datosReporte.getVc_extension() + "&identificadorDoc=OA";

				//System.out.println("reporteURL: " + reporteURL);
				
				respuesta.add(reporteURL);
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				logger.error("Error:" + e.getMessage());
				respuesta.add(e.getMessage());
				return ResponseEntity.ok(respuesta);
			}

		}
		
		@RequestMapping(value = { "/reporteExclusionesInternasEstatus" }, method = RequestMethod.POST)
		public ResponseEntity<?> reporteExclusionesInternasEstatus(@RequestParam("dtFechaInicio") String dtFechaInicio, 
														@RequestParam("dtFechaFin") String dtFechaFin,
														@RequestParam("estatus") Integer estatus) {

			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				
				ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_EXCLUSIONES_INTERNAS_ESTATUS);
				
				String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)
						+ datosReporte.getPk_id_reporte() + "&parametros=?FechaInicio=" + dtFechaInicio + "~FechaFin="
						+ dtFechaFin + "~Estatus=" + estatus + "&tipoDocumento=" + datosReporte.getVc_extension() + "&identificadorDoc=OA";

				//System.out.println("reporteURL: " + reporteURL);
				
				respuesta.add(reporteURL);
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				logger.error("Error:" + e.getMessage());
				respuesta.add(e.getMessage());
				return ResponseEntity.ok(respuesta);
			}

		}
		
		@RequestMapping(value = { "/reporteExclusionesInternasSinFecha" }, method = RequestMethod.POST)
		public ResponseEntity<?> reporteExclusionesInternasSinFecha(@RequestParam("dtFechaInicio") String dtFechaInicio, 
														@RequestParam("dtFechaFin") String dtFechaFin) {

			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				
				ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_EXCLUSIONES_INTERNAS_SIN_FECHA);
				
				String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)
						+ datosReporte.getPk_id_reporte() + "&parametros=?FechaInicio=" + dtFechaInicio + "~FechaFin="
						+ dtFechaFin + "&tipoDocumento=" + datosReporte.getVc_extension() + "&identificadorDoc=OA";

				//System.out.println("reporteURL: " + reporteURL);
				
				respuesta.add(reporteURL);
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				logger.error("Error:" + e.getMessage());
				respuesta.add(e.getMessage());
				return ResponseEntity.ok(respuesta);
			}

		}
		
		@RequestMapping(value = { "/reporteBasesCampaniasMc" }, method = RequestMethod.POST)
		public ResponseEntity<?> reporteBasesCampaniasMc(@RequestParam("dtFechaInicio") String dtFechaInicio, 
														@RequestParam("dtFechaFin") String dtFechaFin,
														@RequestParam("idCampanias") String idCampanias) {

			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				
				ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_BASES_CAMPANIAS_MC);
				
				String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)
						+ datosReporte.getPk_id_reporte() + "&parametros=?FechaInicio=" + dtFechaInicio + "~FechaFin="
						+ dtFechaFin + "~idCampanias=" + idCampanias + "&tipoDocumento=" + datosReporte.getVc_extension() + "&identificadorDoc=OA";

				//System.out.println("reporteURL: " + reporteURL);
				
				respuesta.add(reporteURL);
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				logger.error("Error:" + e.getMessage());
				respuesta.add(e.getMessage());
				return ResponseEntity.ok(respuesta);
			}

		}
	
	public int validarAcceso(String rutaController) {
		try {

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (!(principal instanceof UserDetails)) {
				logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
			}
			
			UserDetails userDetails = (UserDetails) principal;
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
	        if (usuario == null || usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return 0;
	        }

			return iRelRolOpcionesRepository.validarAcceso(
					usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return 0;
		}
	}
	
}
