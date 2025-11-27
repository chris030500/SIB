package ahorrosbienestar.sib.std.credito.extraccioncampanias.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.configuracion.service.ConfiguracionService;
import ahorrosbienestar.sib.std.credito.extraccioncampanias.model.ValBuroCreditoCampaniaModel;
import ahorrosbienestar.sib.std.credito.extraccioncampanias.service.ExtraccionCampaniasService;
import ahorrosbienestar.sib.std.credito.extraccioncampanias.service.ValBuroCreditoCampaniaService;
import ahorrosbienestar.sib.std.credito.procesosCampanias.dto.ProcesosCampaniasDTO;
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
	private static final String nomPropiedadExc = "cronCampaniasExc";
	private static final String nomPropiedadBanderaExc = "extraccionCampaniasExc";
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
	private ValBuroCreditoCampaniaService serviceValBuroCredito;
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	private static final int opcBusquedaGrupo = 38;

	
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
		        
		        Configuracion configuracionExc = configService.valorPropiedad(nomPropiedadExc);
		        model.addAttribute("configIdCronExc", configuracionExc.getPk_id_configuracion());
		        
		        Configuracion configuracionBanderaExc = configService.valorPropiedad(nomPropiedadBanderaExc);
		        model.addAttribute("configIdBanderaExc", configuracionBanderaExc.getPk_id_configuracion());
				
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
	
	//alta datos Tipo Buro de Credito 1: PROSPECTOR, 2: ADVISER
	/*@PostMapping("/api/crearValBuroCredito")
	public ResponseEntity<String> crear(@RequestBody ValBuroCreditoCampaniaModel buroCredito) {
	    try {
	        
	    	if(serviceValBuroCredito.save(buroCredito) !=null) {
				return ResponseEntity.ok("Datos Tipo Buro de Credito guardada correctamente");
			}else {
				return ResponseEntity.ok("Error al guardar datos Tipo Buro de Credito");
			}
	    	
	    } catch (Exception e) {
	    	
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Error al crear datos Tipo Buro de Credito: " + e.getMessage());
	    }
	}*/
	//inserta todo el array
	@PostMapping("/api/crearValBuroCredito")
	public ResponseEntity<String> crear(@RequestBody List<ValBuroCreditoCampaniaModel> buroCreditos) {
	    try {
	        serviceValBuroCredito.saveAll(buroCreditos);
	        return ResponseEntity.ok("Se guardaron " + buroCreditos.size() + " registros de Buro de Credito correctamente");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Error al crear datos Tipo Buro de Credito: " + e.getMessage());
	    }
	}
	
	@GetMapping("/api/countExtraccion")
    @ResponseBody
    public int contarProcesosExtraccion() {
        return procesosCampaniasRepository.contarProcesosExtraccion();
    }
	
	@GetMapping("/api/countDepuracionBc")
    @ResponseBody
    public int contarProcesosDepuracionBc() {
        return procesosCampaniasRepository.contarProcesosDepuracionBc();
    }
	
	@GetMapping("/api/countExtraccionTerm")
	@ResponseBody
    public int contarProcesosExtraccionTerm(@RequestParam("tipoCampania") int tipoCampania,
    										@RequestParam("estatus") int estatus) {
		
        return procesosCampaniasService.contarProcesosExtraccionFin(tipoCampania, estatus);
    }
	
	@GetMapping("/api/reporteBuroCredito")
	@ResponseBody
    public int reporteBuroCredito(@RequestParam("tipoCampania") int tipoCampania) {
		
        return procesosCampaniasService.reporteBuroCredito(tipoCampania);
    }
	
	@GetMapping("/api/consultaCodigoCam")
	/*@ResponseBody
    public String consultaCodigoCam(@RequestParam("procesoCampania") int procesoCampania) {
		
        return procesosCampaniasService.consultaCodigoCam(procesoCampania);
    }*/
	@ResponseBody
	public Map<String, String> consultaCodigoCam(@RequestParam("procesoCampania") int procesoCampania) {
	    String codigo = procesosCampaniasService.consultaCodigoCam(procesoCampania);
	    return Map.of("codigo", codigo);
	}
	
	@GetMapping("/api/consultaFechas")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> consultaFechas(
		    @RequestParam("tipoCampania") Integer tipoCampania) {

		    Map<String, Object> respuesta = new HashMap<>();

		    try {
		        List<ProcesosCampaniasDTO> lista = procesosCampaniasService.buscarFechaCampania(tipoCampania);
		        respuesta.put("lista", lista);
		        return ResponseEntity.ok(respuesta);
		    } catch (Exception e) {
		        respuesta.put("error", e.getMessage());
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		    }
		}
	
	
	
	@GetMapping("/api/procesoCamEstatus")
	@ResponseBody
    public int procesoCamEstatus(@RequestParam("tipoCampania") int tipoCampania, 
    							@RequestParam("estatusCampania") int estatusCampania,
    							@RequestParam("banderaCampania") int banderaCampania) {
		
        return procesosCampaniasService.procesoCamEstatus(tipoCampania, estatusCampania, banderaCampania);
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
	
	@PostMapping("/api/actualizarProcesoCampania")
	public ResponseEntity<String> actualizar(@RequestBody ProcesosCampaniasModel procesos) {
	    try {
	        boolean actualizado = procesosCampaniasService.actualizarProcesoCampania(procesos);
	        if (actualizado) {
	            return ResponseEntity.ok("Actualizado correctamente");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proceso campania no encontrada o sin cambios");
	        }
	    } catch (Exception e) {
	        // Log the error here to get more information about what went wrong
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al mostrar el proceso campania: " + e.getMessage());
	    }
	}
	
	@PostMapping("/api/crearExtCargaBuro")
    public ResponseEntity<String> crearExtCargaBc(@RequestBody ProcesosCampaniasModel procesos) {
		
        try {
            if (procesosCampaniasService.save(procesos) != null) {
                return ResponseEntity.ok("Proceso guardado correctamente");
            } else {
            	logger.error("Error al guardar");
                return ResponseEntity.ok("Error al guardar proceso de extraccion de carga buro");
                
            }
        } catch (Exception e) {
        	logger.error("Error al crear" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al crear proceso de extraccion de carga buro: " + e.getMessage());
        }
    }
	
	//llenar el combo de tipos buro de credito (Adviser/Prospector)
	/*@GetMapping("/api/catTipoBuro")
	@ResponseBody
	public List<Catalogo> getCatalogoTipoBuro(@RequestParam("idGrupo") Integer idGrupo,@RequestParam("tipoCampania") int tipoCampania) {
	    return iCatalogoRepository.listaTipoBuro(idGrupo, tipoCampania);
	}*/
	@GetMapping("/api/catTipoBuro")
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> getCatalogoTipoBuro(
	    @RequestParam("idGrupo") Integer idGrupo,
	    @RequestParam("tipoCampania") Integer tipoCampania
	) {
	    try {
	        List<Object[]> resultados = iCatalogoRepository.listaTipoBuro(idGrupo, tipoCampania);
	        List<Map<String, Object>> lista = new ArrayList<>();

	        for (Object[] row : resultados) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("i_opcion", row[0]);
	            map.put("v_valor", row[1]);
	            lista.add(map);
	        }

	        return ResponseEntity.ok(lista);
	    } catch (Exception e) {
	        e.printStackTrace(); // 
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	
	@GetMapping("/api/ejecutarDepuracionBc")
    public ResponseEntity<String> ejecutarDepuracionBc(
            @RequestParam("tipoCampania") int tipoCampania,
            @RequestParam("procesoCampania") int procesoCampania) {
        try {
        	procesosCampaniasService.ejecutarDepuracionBC(tipoCampania, procesoCampania);
            return ResponseEntity.ok("Procedimiento ejecutado correctamente depuracion buro");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al ejecutar el procedimiento depuracion buro: " + e.getMessage());
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
    
    //SP_DEP_CAM_EXTRACCIONCAMPCRED
    @GetMapping("/api/ejecutarArchivoMc")
    public ResponseEntity<String> ejecutarArchivoMc(
            @RequestParam("tipoCampania") int tipoCampania) {
        try {
        	procesosCampaniasService.ejecutarArchivoMc(tipoCampania);
            return ResponseEntity.ok("Procedimiento ejecutado correctamente archivo MC SP_DEP_CAM_EXTRACCIONCAMPCRED");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al ejecutar el procedimiento archivo MC SP_DEP_CAM_EXTRACCIONCAMPCRED: " + e.getMessage());
        }
    }
	
}
