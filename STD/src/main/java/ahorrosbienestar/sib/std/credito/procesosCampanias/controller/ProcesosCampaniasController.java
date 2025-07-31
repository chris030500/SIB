package ahorrosbienestar.sib.std.credito.procesosCampanias.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.credito.procesosCampanias.model.ProcesosCampaniasModel;
import ahorrosbienestar.sib.std.credito.procesosCampanias.service.ProcesosCampaniasService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.service.TiposCampaniasService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;

@Controller
@RequestMapping("credito")

public class ProcesosCampaniasController {
	
	private static final Logger logger = Logger.getLogger(ProcesosCampaniasController.class);
	
	private static final String controllerReporte = "generarReporte";
	
	@Autowired
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private TiposCampaniasService tiposCampaniasService;
	
	@Autowired
	private ProcesosCampaniasService procesosCampaniasService;
	
	List<Usuario> usuario = null;
	
	@GetMapping("/procesosCampanias")
	public String mostrarSolicitudes(Model model){
		
		try {
			String rutaDireccion;
			
			if (validarAcceso(Constantes.CAMPANIAS_PROCESOS) == 1) {
				
				/////
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				
				UserDetails userDetails = (UserDetails) principal;
				usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
				Integer idUsuario = usuario.get(0).getPk_id_usuario();
				//System.out.print("idUsuario:" + idUsuario);
				model.addAttribute("idUsuario", idUsuario);
				////
				
				// Obtener la fecha actual sin tiempo
		        LocalDate fechaActual = LocalDate.now();
		        model.addAttribute("idFecha", fechaActual);
				
				rutaDireccion = Constantes.CAMPANIAS_PROCESOS;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			//logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}
	}
	
	//llenar el combo opcion multiple de campañas
	@GetMapping("/api/catTipoCampania")
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
	
	//llena la tabla de procesos campañas
	@GetMapping("/api/procesosCampanias")
	@ResponseBody
	/*public ResponseEntity<List<ProcesosCampaniasModel>> buscarProcesosJson(
	    @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
	    @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
	    @RequestParam("Campania") List<Integer> tipoCampania 
	) {
	    try {
	        List<ProcesosCampaniasModel> resultado = procesosCampaniasService.buscarCampanias(fechaInicio, fechaFin, tipoCampania);
	        return ResponseEntity.ok(resultado);
	    } catch(Exception e) {
	        logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}*/
	public ResponseEntity<List<ProcesosCampaniasModel>> buscarProcesosJson(
		    @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
		    @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
		    @RequestParam("Campania") String tipoCampania
		) {
		try {
			List<ProcesosCampaniasModel> resultado = procesosCampaniasService.buscarCampanias(fechaInicio, fechaFin, tipoCampania);
			/*for (ProcesosCampaniasModel c : resultado) {
			    System.out.println("Campaña ID: " + c.getTipoCampania().getPk_id_tipo_campania() +
			                       ", Nombre: " + c.getTipoCampania().getVc_tipo_campania());
			}*/
		    return ResponseEntity.ok(resultado);
		}catch(Exception e) {
	    	 
	    	 logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
				return null;
	     }
		    
		}
	
	@RequestMapping(value = { "/reporteProcesosCampania" }, method = RequestMethod.POST)
	public ResponseEntity<?> reporteProcesosCampania(@RequestParam("dtFechaInicio") String dtFechaInicio,
													@RequestParam("dtFechaFin") String dtFechaFin,
													@RequestParam("campania") Integer campania) {

		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_PROCESOS_CAMPANIA);

			String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)
					+ datosReporte.getPk_id_reporte() + "&parametros=?FechaInicio=" + dtFechaInicio + "~FechaFin="
					+ dtFechaFin + "~Campania=" + campania + "&tipoDocumento=" +datosReporte.getVc_extension()+ "&identificadorDoc=OA";

			System.out.println("reporteURL: " + reporteURL);
			
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
				//logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
			}
			
			UserDetails userDetails = (UserDetails) principal;
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
	        if (usuario == null || usuario.isEmpty()) {
	            //logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return 0;
	        }

			return iRelRolOpcionesRepository.validarAcceso(
					usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			//logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return 0;
		}
	}

}
