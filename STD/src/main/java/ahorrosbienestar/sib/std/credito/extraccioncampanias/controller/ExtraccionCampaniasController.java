package ahorrosbienestar.sib.std.credito.extraccioncampanias.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.configuracion.service.ConfiguracionService;
import ahorrosbienestar.sib.std.credito.extraccioncampanias.service.ExtraccionCampaniasService;
import ahorrosbienestar.sib.std.credito.procesosCampanias.model.EstatusCampaniasModel;
import ahorrosbienestar.sib.std.credito.procesosCampanias.model.ProcesosCampaniasModel;
import ahorrosbienestar.sib.std.credito.procesosCampanias.repository.ProcesosCampaniasRepository;
import ahorrosbienestar.sib.std.credito.procesosCampanias.service.EstatusCampaniasService;
import ahorrosbienestar.sib.std.credito.procesosCampanias.service.ProcesosCampaniasService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.HisTiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.service.HisTiposCampaniasService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.service.TiposCampaniasService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("credito")

public class ExtraccionCampaniasController {

private static final Logger logger = Logger.getLogger(ExtraccionCampaniasController.class);
	
	private static final String nomPropiedad = "cronCampanias";
	private static final String nomPropiedadBandera = "extraccionCampanias";
	private static final String controllerReporte = "generarReporte";

	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private TiposCampaniasService tiposCampaniasService;
	
	@Autowired
	private EstatusCampaniasService estatusCampaniasService;
	
	@Autowired
    private ProcesosCampaniasRepository procesosCampaniasRepository;
	
	@Autowired
	private ProcesosCampaniasService procesosCampaniasService;
	
	@Autowired
	private ExtraccionCampaniasService extraccionCampaniasService;
	
	@Autowired
	private ConfiguracionService configiracionService;
	
	@Autowired
	private HisTiposCampaniasService serviceHis;
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	private IReporteRepository iReporteRepository;

	
	List<Usuario> usuario = null;
	
	@GetMapping("/extraccionCampanias")
	public String mostrarSolicitudes(Model model){
		
		try {
			String rutaDireccion;
			
			if (validarAcceso(Constantes.CAMPANIAS_EXTRACCION) == 1) {
				
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
		        
		        Configuracion configuracion = configService.valorPropiedad(nomPropiedad);
		        model.addAttribute("configIdCron", configuracion.getPk_id_configuracion());
		        
		        Configuracion configuracionBandera = configService.valorPropiedad(nomPropiedadBandera);
		        model.addAttribute("configIdBandera", configuracionBandera.getPk_id_configuracion());
				
				rutaDireccion = Constantes.CAMPANIAS_EXTRACCION;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			//logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}
	}
	

	//llenar el combo de campañas
	@GetMapping("/api/catTipoCam")
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
	
	//llenar el combo de estatus campañas
	@GetMapping("/api/catEstatusCam")
	@ResponseBody
    public ResponseEntity<List<EstatusCampaniasModel>> getEstatusCampanias() {
     try {
    	 List<EstatusCampaniasModel> credenciales = estatusCampaniasService.getEstatusCampaniasModel();
    	 
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
	
	//alta de proceso para extracción
	/*@PostMapping("/api/crearExtraccion")
	public ResponseEntity<String> crear(@RequestBody ProcesosCampaniasModel procesos) {
	    try {
	        
	    	if(procesosCampaniasService.save(procesos) !=null) {
				return ResponseEntity.ok("Proceso guardadoa correctamente");
			}else {
				return ResponseEntity.ok("Error al guardar proceso");
			}
	    	
	        
	    } catch (Exception e) {

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Error al crear proceso: " + e.getMessage());
	    }
	}*/
	@PostMapping("/api/crearExt")
    public ResponseEntity<String> crear(@RequestBody ProcesosCampaniasModel procesos) {
		
        try {
            if (procesosCampaniasService.save(procesos) != null) {
                return ResponseEntity.ok("Proceso guardado correctamente");
            } else {
            	logger.error("Error al guardar");
                return ResponseEntity.ok("Error al guardar proceso");
                
            }
        } catch (Exception e) {
        	logger.error("Error al crear" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al crear proceso: " + e.getMessage());
        }
    }
	
	@PostMapping("/api/actualizarParamExt")
	
	public ResponseEntity<String> actualizar(@RequestBody Configuracion paramtero) {
	    try {
	        boolean actualizado = configiracionService.actualizarConfiguracion(paramtero);
	        if (actualizado) {
	            return ResponseEntity.ok("Actualizado correctamente");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parametro cron no encontrada o sin cambios");
	        }
	    } catch (Exception e) {
	        // Log the error here to get more information about what went wrong
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al mostrar el parametro: " + e.getMessage());
	    }
	}
	
	@PostMapping("/api/crearHisExt")
	public ResponseEntity<String> crearHis(@RequestBody HisTiposCampaniasModel campania) {
	    try {
	        boolean creada = serviceHis.crearHisCampania(campania);
	        if (creada) {
	            return ResponseEntity.ok("Campaña creada correctamente");
	        } else {
	        	
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el historico del proceso");
	        }
	    } catch (Exception e) {
	    	
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Error al crear el historico del proceso: " + e.getMessage());
	    }
	}
	
	@GetMapping("/api/countExtraccion")
    @ResponseBody
    public int contarProcesosExtraccion() {
        return procesosCampaniasRepository.contarProcesosExtraccion();
    }
	
	@GetMapping("/api/countExtraccionTerm")
	@ResponseBody
    public int contarProcesosExtraccionTerm(@RequestParam("tipoCampania") int tipoCampania) {
		
        return procesosCampaniasService.contarProcesosExtraccionFin(tipoCampania);
    }
	
	@GetMapping("/api/reporteBuroCredito")
	@ResponseBody
    public int reporteBuroCredito(@RequestParam("tipoCampania") int tipoCampania) {
		
        return procesosCampaniasService.reporteBuroCredito(tipoCampania);
    }
	
	@RequestMapping(value = { "/rpt_archivo_buro" }, method = RequestMethod.POST)
	public ResponseEntity<?> generaReporteBuroCredito(@RequestParam("ProcesoCampania") Integer ProcesoCampania,
													@RequestParam("TipoCampania") Integer TipoCampania) {

		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_BURO_CREDITO);

			String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)
					+ datosReporte.getPk_id_reporte() + "&parametros=?ProcesoCampania=" + ProcesoCampania + "~TipoCampania="
					+ TipoCampania + "&tipoDocumento=" +datosReporte.getVc_extension()+ "&identificadorDoc=OA";

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
