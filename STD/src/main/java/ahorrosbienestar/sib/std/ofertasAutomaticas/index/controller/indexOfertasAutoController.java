package ahorrosbienestar.sib.std.ofertasAutomaticas.index.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.correoSIB.service.EnvioCorreoService;
import ahorrosbienestar.sib.std.menuProducto.parametrosConfiguracion.service.ConfiguracionSistemaService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.configuracion.FidelidadTareaConfiguracion;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.nomina.configuracion.NominaTareaConfiguracion;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.prospector.configuracion.ProspectorTareaConfiguracion;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.service.BasesService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.campaniasVIP.configuracion.CampaniasVIPTareaConfiguracion;
import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.model.ClasificacionBases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.clasificacionBases.service.ClasificacionBasesService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto.ArchivoOfertasAutomaticas;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.configuracion.GeneracionSMSConfiguracion;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.configuracion.GestionAsignacionConfiguracion;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model.ProcesoGestion;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.service.ProcesoGestionService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.service.RegistrosAsignacionService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.historicoCampanias.model.DetalleCampania;
import ahorrosbienestar.sib.std.ofertasAutomaticas.historicoCampanias.service.DetalleCampaniaService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;


@Controller
@RequestMapping("/ofertasAutomaticas")
public class indexOfertasAutoController {

	private static final Logger logger = Logger.getLogger(indexOfertasAutoController.class);

	// Constante Nomina
	private final static int NOMINA = 1;

	// Constantes Fidelidad
	private final static int FIDELIDAD = 2;

	// Constantes Campañas VIP
	private final static int CAMPANAS_VIP = 3;
	
	// Constante Activo
	private final static int ACTIVO = 1;
	
	private String rutaDireccion="";

	@Autowired
	private IUsuarioRepository iUsuarioRepository;	
	
	@Autowired
	private ConfigService configService;

	@Autowired
	private BasesService basesService;
	
	@Autowired
	private DetalleCampaniaService detalleCampaniaService;

	@Autowired
	private ClasificacionBasesService clasificacionBasesService;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private BasesService baseService;
	
	@Autowired
	private ProcesoGestionService procesoGestionService;
	
	@Autowired
	private FidelidadTareaConfiguracion fidelidadTareaConfiguracion;
	
	@Autowired
	private NominaTareaConfiguracion nominaTareaConfiguracion;
	
	@Autowired
	private CampaniasVIPTareaConfiguracion campaniasVIPTareaConfiguracion;
	
	@Autowired
	private ProspectorTareaConfiguracion prospectorTareaConfiguracion;
		
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private GestionAsignacionConfiguracion gestionAsignacionConfiguracion;
	
	@Autowired
	private GeneracionSMSConfiguracion generacionSMSConfiguracion;
	
	@Autowired
	private ConfiguracionSistemaService configuracionSistemaService;
	
	@Autowired
	private RegistrosAsignacionService registrosAsignacionService;
	
	@Autowired 
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired
	private EnvioCorreoService envioCorreoService;
	
	private int catTipoCampanias = 11;
	List<Usuario> usuario = null;
	private int tipoArchivo = 12;
	
	private String error = "";

	@GetMapping("/index")
	public String indexOfertasAuto(Model model) throws IOException {
	 try {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		
		if(validarAcceso(userDetails.getUsername())== 1){
			Configuracion mensajeCampana = configService.valorPropiedad(Constantes.MENSAJE_CAMPANA_VIP);
			model.addAttribute("cat_tipo_campanias", iCatalogoRepository.generarOpciones(catTipoCampanias));
			model.addAttribute("mensajeCampana", mensajeCampana.getVc_valor_propiedad());
			//Bases Configuracion 
			//model.addAttribute("lista_configuracion", baseService.findAll());
			List<Bases> bases = basesService.findAll();
			model.addAttribute("bases", bases);
			model.addAttribute("cat_lista_tipoArchivo", iCatalogoRepository.generarOpciones(tipoArchivo));
			model.addAttribute("permiso_ejecucion_gestion",configuracionSistemaService.buscarConfiguracion(Constantes.USUARIO_EJECUCION_GESTION,userDetails.getUsername()));
			model.addAttribute("permiso_ejecucion_base",configuracionSistemaService.buscarConfiguracion(Constantes.USUARIO_EJECUCION_BASE,userDetails.getUsername()));
			model.addAttribute("permiso_admin",configuracionSistemaService.buscarConfiguracion(Constantes.USUARIOS_ADMIN_OFERTAS,userDetails.getUsername()));
			rutaDireccion ="ofertasAutomaticas/indexOfertasAutomaticas";
		}else {
			rutaDireccion = Constantes.RUTA_ERROR;
		}
		return rutaDireccion;
	 }catch(Exception e) {
		
		 logger.error(""+e.getMessage());
	 }	
	 return null;
	}

	@PostMapping("/guardarParamFidelidad")
	public String guardarParametrosFidelidad(@RequestParam("dComienzoProceso") String dComienzoProceso,
			@RequestParam("dFinalProceso") String dFinalProceso,
			@RequestParam("hComienzoProceso") String hComienzoProceso,
			@RequestParam("hFinalProceso") String hFinalProceso,
			@RequestParam("mPeriodoEjecucion") String mPeriodoEjecucion,
			@RequestParam("rutaArchivo") String rutaArchivo) {

		try {
			logger.info("Ejecutando guardarParamFidelidad");
			// Buscamos por ID en bases para validar que exista el registro
			Optional<Bases> baseFidelidadOptional = basesService.findById(FIDELIDAD);

			// Si existe algún valor realiza la actualizacion
			if (baseFidelidadOptional.isPresent()) {
				Bases baseFidelidad = baseFidelidadOptional.get();
				baseFidelidad.setVc_dia_inicio_proceso(dComienzoProceso);
				baseFidelidad.setVc_dia_fin_proceso(dFinalProceso);
				baseFidelidad.setVc_hora_inicio_proceso(hComienzoProceso);
				baseFidelidad.setVc_hora_fin_proceso(hFinalProceso);
				baseFidelidad.setVc_periodo_ejecucion_proceso(mPeriodoEjecucion);
				baseFidelidad.setVc_ruta_proceso(rutaArchivo);
				basesService.actualizarUsuario(baseFidelidad);
			}

			return "redirect:/ofertasAutomaticas/index";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@PostMapping("/guardarParamCampanaVIP")
	public String guardarParamCampanaVIP(@RequestParam("dComienzoCampana") String dComienzoCampana,
			@RequestParam("dFinalCampana") String dFinalCampana,
			@RequestParam("hComienzoCampana") String hComienzoCampana,
			@RequestParam("hFinalCampana") String hFinalCampana,
			@RequestParam("mPeriodoCampana") String mPeriodoCampana) {

		try {
			logger.info("Ejecutando guardarParamCampanaVIP");

			// Buscamos por ID en bases para validar que exista el registro
			Optional<Bases> baseCampanaOptional = basesService.findById(CAMPANAS_VIP);

			// Si existe algún valor realiza la actualizacion
			if (baseCampanaOptional.isPresent()) {
				Bases baseCampana = baseCampanaOptional.get();
				baseCampana.setVc_dia_inicio_proceso(dComienzoCampana);
				baseCampana.setVc_dia_fin_proceso(dFinalCampana);
				baseCampana.setVc_hora_inicio_proceso(hComienzoCampana);
				baseCampana.setVc_hora_fin_proceso(hFinalCampana);
				baseCampana.setVc_periodo_ejecucion_proceso(mPeriodoCampana);
				basesService.actualizarUsuario(baseCampana);
			}

			return "redirect:/ofertasAutomaticas/index";
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@PostMapping("/guardarClasificacion")
	public String guardarClasificacion(@RequestParam("base") Integer base,
			@RequestParam("nomClasificacion") String nomClasificacion, @RequestParam("abrevacion") String abrevacion,
			@RequestParam("codigoClasificacion") String codigoClasificacion,
			@RequestParam("vigenciaClasificacion") Integer vigenciaClasificacion,
			@RequestParam("mensajeClasificacion") String mensajeClasificacion,
			@RequestParam("confMensaje") boolean confMensaje, 
			@RequestParam(name = "id", required = false) Integer id) {

		try {
			logger.info("Ejecutando guardarClasificacion");
			logger.info("id: " + id);
			Integer integerValue = confMensaje ? 1 : 0;
			LocalDateTime localDateTime = LocalDateTime.now();

			Optional<Bases> tipoBase = basesService.findById(base);

			// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
			// HH:mm:ss");
			logger.info("base: " + base);
			logger.info("nomClasificacion: " + nomClasificacion.toUpperCase());
			logger.info("abrevacion: " + abrevacion.toUpperCase());
			logger.info("codigoClasificacion: " + codigoClasificacion.toUpperCase());
			logger.info("vigenciaClasificacion: " + vigenciaClasificacion);
			logger.info("mensajeClasificacion: " + mensajeClasificacion);
			logger.info("integerValue: " + integerValue);
			logger.info("localDateTime: " + localDateTime);

			if (id != null) {
				logger.info("Entro en id!=null");
				Optional<ClasificacionBases> clasificacionExistente = clasificacionBasesService.findById(id);
				logger.info("clasificacionExistente: " + clasificacionExistente.get().getPk_id_clasificacion());
				if (clasificacionExistente.isPresent()) {
					ClasificacionBases clasificacionBases = clasificacionExistente.get();
					clasificacionBases.setVc_nombre_clasificacion(nomClasificacion.toUpperCase());
					clasificacionBases.setVc_abreviacion_clasificacion(abrevacion.toUpperCase());
					clasificacionBases.setVc_codigo_clasificacion(codigoClasificacion.toUpperCase());
					clasificacionBases.setI_vigencia_clasificacion(vigenciaClasificacion);
					clasificacionBases.setI_configuracion_mensaje(integerValue);
					clasificacionBases.setVc_mensaje_clasificacion(mensajeClasificacion);
					clasificacionBases.setFk_id_base(tipoBase.get());

					clasificacionBasesService.actualizarClasificacion(clasificacionBases);

					logger.info("Nombre Base: " + tipoBase.get().getVc_nombre_base());
					return "redirect:/ofertasAutomaticas/index";
				} else {
					// Manejar el escenario donde el ID no corresponde a un registro existente
					return "El ID de clasificación no es válido.";
				}
			} else {
				ClasificacionBases clasificacionBases = new ClasificacionBases();
				clasificacionBases.setVc_nombre_clasificacion(nomClasificacion.toUpperCase());
				clasificacionBases.setVc_abreviacion_clasificacion(abrevacion.toUpperCase());
				clasificacionBases.setVc_codigo_clasificacion(codigoClasificacion.toUpperCase());
				clasificacionBases.setI_vigencia_clasificacion(vigenciaClasificacion);
				clasificacionBases.setDt_fecha_registro(localDateTime);
				clasificacionBases.setI_configuracion_mensaje(integerValue);
				clasificacionBases.setI_estatus_clasificacion(1);
				clasificacionBases.setVc_mensaje_clasificacion(mensajeClasificacion);
				clasificacionBases.setFk_id_base(tipoBase.get());

				clasificacionBasesService.save(clasificacionBases);

				logger.info("Nombre Base: " + tipoBase.get().getVc_nombre_base());
				return "redirect:/ofertasAutomaticas/index";
			}

		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}

	}
	
	//Metodos Nuevos paa consulta de Informacion.
	//Optional<Bases>
	
	@GetMapping("/consultaParametros")
	public ResponseEntity<?> consultaParametros(@RequestParam(name = "idBase") Integer idBase){		
		try {
			logger.info("/consultaParametros"+idBase);
			return ResponseEntity.ok(basesService.findById(idBase));
		} catch (Exception e) {
			logger.info("--" + e.getMessage());
			return null;
		}
		
	}
	
	@GetMapping("/consultaTabla")
	public ResponseEntity<?> consultaTabla(@RequestParam(name = "idBase") Integer idBase){		
		try {
			logger.info(idBase);
			logger.info("/consultaTabla"+idBase);
			List<DetalleCampania> detalleCampania = new ArrayList<>();
			switch (idBase) {
            case 3:
            	try {
            		detalleCampania = detalleCampaniaService.findDetalleVIP(idBase);
				} catch (Exception e) {
					// TODO: handle exception
				}
                break;
            case 4:
            	try {
            		detalleCampania = detalleCampaniaService.findDetallePros(idBase);
				} catch (Exception e) {
					// TODO: handle exception
				}
                break;
            case 5:
            	try {
            		detalleCampania = detalleCampaniaService.findDetalleFidNom(idBase);
				} catch (Exception e) {
					// TODO: handle exception
				}
                break;
            case 6:
            	try {
            		detalleCampania = detalleCampaniaService.findDetalleFidRen(idBase);
				} catch (Exception e) {
					// TODO: handle exception
				}
                break;
            default:
            	logger.info("En el metodo consultaTabla al entrar al switch no se encontro ninguna coinsidencia");
                break;
        }
			return ResponseEntity.ok(detalleCampania);
			
		} catch (Exception e) {
			logger.info("--" + e.getMessage());
			return null;
		}
		
	}
	
	@PostMapping("/actualizarParametro")
	public ResponseEntity<?> actualizarParametro(
			@RequestParam(name = "idBase") Integer basesID, 
			@RequestParam(name = "dComienzoProceso") String dComienzoProceso, 
			@RequestParam(name = "dFinalProceso") String dFinalProceso,
			@RequestParam(name = "hComienzoProceso") String hComienzoProceso,
			@RequestParam(name = "hFinalProceso") String hFinalProceso,
			@RequestParam(name = "mPeriodoEjecucion") String mPeriodoEjecucion,
			@RequestParam(name = "rutaArchivo") String rutaArchivo
			){
		
		logger.info("/actualizarParametro");
		String mensaje ="";
		String tipo =Constantes.MENSAJE_OK;
		Map<String, Object> respuesta = new HashMap<>();
		
		try {
			if(basesService.actParams(basesID,dComienzoProceso,dFinalProceso,hComienzoProceso,hFinalProceso,mPeriodoEjecucion,rutaArchivo) == 1) {
				switch(basesID){
					case 1:
						nominaTareaConfiguracion.actualizarExpresionCronNomina();
					case 2:
						fidelidadTareaConfiguracion.actualizarExpresionCronFidelidad();
						break;
					case 3:
						campaniasVIPTareaConfiguracion.actualizarExpresionCronCampanias();
						break;	
					case 4:
						prospectorTareaConfiguracion.actualizarExpresionCronProspector();
						break;
					case 5:
						campaniasVIPTareaConfiguracion.actualizarExpresionCronFidNom();
						break;
					case 6:
						campaniasVIPTareaConfiguracion.actualizarExpresionCronFidRen();
						break;
					default:
		            	logger.info("En el metodo actualizarParametro al entrar al switch no se encontro ninguna coinsidencia");
		                break;
				}
				mensaje =Constantes.ACTUALIZACION_OK;
				
			}else {
				mensaje = Constantes.ACTUALIZACION_ERROR;
				tipo = Constantes.MENSAJE_ERROR;
			}
			respuesta.put(Constantes.TEXTO_MNS,mensaje);
			respuesta.put(Constantes.TIPO_MNS,tipo);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.put(Constantes.TEXTO_MNS,e.getMessage());
			respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
			return ResponseEntity.ok(respuesta);
		}
	}
	/***
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/obtParametros")
	public ResponseEntity<List<Bases>> obtenerFolios(Model model) {
		 logger.info("/obtParametros");
		try {
			return ResponseEntity.ok(baseService.findAll());
		} catch (Exception e) {
			logger.error("--" + e.getMessage());
			return null;
		}
	}
	/***
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/obtClasificacion")
	public ResponseEntity<List<ClasificacionBases>> obtClasificacion(Model model) {
		 logger.info("/obtClasificacion");
		try {
			return ResponseEntity.ok(clasificacionBasesService.findAll());
		} catch (Exception e) {
			logger.error("--" + e.getMessage());
			return null;
		}
	}
	/**
	 * Metodo para dar de alta una clasificacion
	 * @return
	 */
	
	@PostMapping("/altaClasificacion")
	public ResponseEntity<?> altaClasificacion(
			@RequestParam(name = "idBase") Integer idBase, 
			@RequestParam(name = "nomClasificacion") String nomClasificacion, 
			@RequestParam(name = "abrevacion") String abrevacion,
			@RequestParam(name = "codigoClasificacion") String codigoClasificacion,
			@RequestParam(name = "vigenciaClasificacion") Integer vigenciaClasificacion,
			@RequestParam(name = "mensajeClasificacion") String mensajeClasificacion
			){
		logger.info("/altaClasificacion");
		String mensaje ="";
		String tipo =Constantes.MENSAJE_OK;
		Map<String, Object> respuesta = new HashMap<>();
		try {		
			Bases iBase = basesService.obtBase(idBase);
			if(clasificacionBasesService.existeCodigo(codigoClasificacion) == 0) {
				if(clasificacionBasesService.existeClasificacion(idBase,nomClasificacion,abrevacion) == 0) {
					LocalDateTime  fecha = LocalDateTime.now();
					
					ClasificacionBases altaClasificacion = new ClasificacionBases();
					altaClasificacion.setFk_id_base(iBase);
					altaClasificacion.setVc_nombre_clasificacion(nomClasificacion);
					altaClasificacion.setVc_abreviacion_clasificacion(abrevacion);
					altaClasificacion.setVc_codigo_clasificacion(codigoClasificacion);
					altaClasificacion.setI_vigencia_clasificacion(vigenciaClasificacion);
					altaClasificacion.setVc_mensaje_clasificacion(mensajeClasificacion);
					altaClasificacion.setDt_fecha_registro(fecha);
					altaClasificacion.setI_estatus_clasificacion(ACTIVO);
					
					clasificacionBasesService.save(altaClasificacion);
					mensaje = Constantes.ACTUALIZACION_OK;
				}else {
					mensaje = Constantes.CLASIF_REGISTRADO;
					tipo = Constantes.MENSAJE_ERROR;
				}
			}else {
				mensaje = Constantes.CODIGO_CLASIF_REGISTRADO;
				tipo = Constantes.MENSAJE_ERROR;
			}
			respuesta.put(Constantes.TEXTO_MNS,mensaje);
			respuesta.put(Constantes.TIPO_MNS,tipo);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.put(Constantes.TEXTO_MNS,e.getMessage());
			respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
			return ResponseEntity.ok(respuesta);
		}
	}
	
	//Metodos Nuevos para consulta de Informacion.
	//Optional<Bases>
	
	@GetMapping("/consultaClasificacion")
	public ResponseEntity<?> consultaClasificacion(@RequestParam(name = "idClass") Integer idClass){		
		try {
			logger.info("/consultaClasificacion"+idClass);
			return ResponseEntity.ok(clasificacionBasesService.findById(idClass));
		} catch (Exception e) {
			logger.error("--" + e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * Metodo para modificar una clasificacion
	 * @return
	 */
	
	@PostMapping("/modClasificacion")
	public ResponseEntity<?> modClasificacion(
			@RequestParam(name = "idClass") Integer idClass,
			@RequestParam(name = "idBase") Integer idBase, 
			@RequestParam(name = "nomClasificacion") String nomClasificacion, 
			@RequestParam(name = "abrevacion") String abrevacion,
			@RequestParam(name = "codigoClasificacion") String codigoClasificacion,
			@RequestParam(name = "vigenciaClasificacion") Integer vigenciaClasificacion,
			@RequestParam(name = "mensajeClasificacion") String mensajeClasificacion
			){
		logger.info("/modClasificacion");
		String mensaje ="";
		String tipo =Constantes.MENSAJE_OK;
		Map<String, Object> respuesta = new HashMap<>();
		try {
		
			if(clasificacionBasesService.existeCodigoMod(codigoClasificacion,idClass) == 0) {
				if(clasificacionBasesService.updateClasificacion(idClass,idBase,nomClasificacion,abrevacion,codigoClasificacion,vigenciaClasificacion,mensajeClasificacion) == 1) {
					mensaje =Constantes.ACTUALIZACION_OK;
					
				}else {
					mensaje = Constantes.ACTUALIZACION_ERROR;
					tipo = Constantes.MENSAJE_ERROR;
				}
			}else {
				mensaje = Constantes.CODIGO_CLASIF_REGISTRADO;
				tipo = Constantes.MENSAJE_ERROR;
			}
			respuesta.put(Constantes.TEXTO_MNS,mensaje);
			respuesta.put(Constantes.TIPO_MNS,tipo);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.put(Constantes.TEXTO_MNS,e.getMessage());
			respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
			return ResponseEntity.ok(respuesta);
		}
	}
	
	/**
	 * Metodo para activar una clasificacion
	 * @return
	 */
	
	@PostMapping("/activarClasificacion")
	public ResponseEntity<?> activarClasificacion(
			@RequestParam(name = "idClass") Integer idClass
			){
		logger.info("/activarClasificacion");
		
		String mensaje ="";
		String tipo =Constantes.MENSAJE_OK;
		Map<String, Object> respuesta = new HashMap<>();
		
		try {
			
			if(clasificacionBasesService.activarClasificacion(idClass) == 1) {
				mensaje = Constantes.CLASIF_ACTIVO;
			}else {
				mensaje = Constantes.ACTUALIZACION_ERROR;
				tipo = Constantes.MENSAJE_ERROR;
			}
			respuesta.put(Constantes.TEXTO_MNS,mensaje);
			respuesta.put(Constantes.TIPO_MNS,tipo);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.put(Constantes.TEXTO_MNS,e.getMessage());
			respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
			return ResponseEntity.ok(respuesta);
		}
	}
	/**
	 * Metodo para inactivar una clasificacion
	 * @return
	 */
	
	@PostMapping("/bajaClasificacion")
	public ResponseEntity<?> bajaClasificacion(
			@RequestParam(name = "idClass") Integer idClass
			){
		logger.info("/inactivarClasificacion");
		String mensaje ="";
		String tipo =Constantes.MENSAJE_OK;
		Map<String, Object> respuesta = new HashMap<>();

		try {
			
			if(clasificacionBasesService.bajaClasificacion(idClass) == 1) {
				mensaje = Constantes.CLASIF_INACTIVO;
				
			}else {
				mensaje = Constantes.ACTUALIZACION_ERROR;
				tipo = Constantes.MENSAJE_ERROR;
			}
			respuesta.put(Constantes.TEXTO_MNS,mensaje);
			respuesta.put(Constantes.TIPO_MNS,tipo);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.put(Constantes.TEXTO_MNS,e.getMessage());
			respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
			return ResponseEntity.ok(respuesta);
		}
	}
	
	/***
	 * 
	 * @return
	 */
	@PostMapping("/ejecutarProceso")
	public ResponseEntity<?> ejecutarProceso(
			@RequestParam(name = "idBase") Integer idBase
			){
	
		logger.info("/ejecutarProceso");
		String mensaje ="";
		String tipo =Constantes.MENSAJE_OK;
		Map<String, Object> respuesta = new HashMap<>();
		
		try {
			logger.info("proceso a ejecutar:"+idBase);
			
			switch(idBase) {
			case 1:
				try {
					nominaTareaConfiguracion.procesarNomina();
					mensaje =Constantes.MENSAJE_BASE_NOMINA;

				} catch (Exception e) {
					mensaje = Constantes.MENSAJE_DE_ERROR+e.getMessage();
					tipo = Constantes.MENSAJE_ERROR;
					logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
				}
				
				break;
			case 2:
				try {
					fidelidadTareaConfiguracion.ejecutarFidelidad();			
					mensaje =Constantes.MENSAJE_BASE_FIDELIDAD;
				} catch (Exception e) {
					mensaje = Constantes.MENSAJE_DE_ERROR+e.getMessage();
					tipo = Constantes.MENSAJE_ERROR;
					logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
				}
				
				break;
			case 4:
				try {
					prospectorTareaConfiguracion.procesarProspector();					
					mensaje =Constantes.MENSAJE_BASE_PROSPECTOR;
				} catch (Exception e) {
					mensaje = Constantes.MENSAJE_DE_ERROR+e.getMessage();
					tipo = Constantes.MENSAJE_ERROR;
					logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
				}
				
				break;
			case 3:
				try {
					campaniasVIPTareaConfiguracion.procesarCampaniasVIP();					
					mensaje =Constantes.MENSAJE_BASE_CAMPANIA_VIP;
				} catch (Exception e) {
					mensaje = Constantes.MENSAJE_DE_ERROR+e.getMessage();
					tipo = Constantes.MENSAJE_ERROR;
					logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
				}
				
				break;
			case 5:
				try {
					campaniasVIPTareaConfiguracion.procesarCampaniasFidNom();
					mensaje =Constantes.MENSAJE_BASE_FIDELIZACION_NOMINA;
				} catch (Exception e) {
					mensaje = Constantes.MENSAJE_DE_ERROR+e.getMessage();
					tipo = Constantes.MENSAJE_ERROR;
					logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
				}
				
				break;
			case 6:
				try {
					campaniasVIPTareaConfiguracion.procesarCampaniasFidRen();
					mensaje =Constantes.MENSAJE_BASE_FIDELIZACION_RENOVADO;
				} catch (Exception e) {
					mensaje = Constantes.MENSAJE_DE_ERROR+e.getMessage();
					tipo = Constantes.MENSAJE_ERROR;
					logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
				}
				
				break;
			}
			respuesta.put(Constantes.TEXTO_MNS,mensaje);
			respuesta.put(Constantes.TIPO_MNS,tipo);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
			respuesta.put(Constantes.TEXTO_MNS,e.getMessage());
			respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
			return ResponseEntity.ok(respuesta);
		}
	}
	
	@GetMapping("/listaArchivos")
    public ResponseEntity<List<ArchivoOfertasAutomaticas>> listFilesInFolder(@RequestParam(name = "idBase") Integer idBase) throws IOException {
		
		try {
			Optional<Bases> bases = basesService.findById(idBase);
	        String folderPath = bases.get().getVc_ruta_proceso();   
	        Path folder = Paths.get(folderPath);

	        List<ArchivoOfertasAutomaticas> listaArchivos = new ArrayList<>();

	        // Listar los archivos en la carpeta
	        Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
	         		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	         		ArchivoOfertasAutomaticas archivos = new ArchivoOfertasAutomaticas();
	         		archivos.setNombreArchivo(file.getFileName().toString());
	         		archivos.setPesoArchivo((attrs.size()/1024)+" KB");
	         		
	            	logger.info("Nombre:"+archivos.getNombreArchivo());
	            	logger.info("tamaño:"+archivos.getPesoArchivo());
	            	long millis = attrs.creationTime().toMillis();
	            	FileTime fileTime = FileTime.fromMillis(millis);
	            	LocalDate fechaCreacion = fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            	String fechaFormateada = fechaCreacion.toString();
	            	archivos.setFechaCreacion(fechaFormateada);
	            	
	            	long fechaModificacion = 	attrs.lastModifiedTime().toMillis();
	            	FileTime fileTimeMod = FileTime.fromMillis(fechaModificacion);
	            	LocalDate fechaMod = fileTimeMod.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            	String fechaFormateadaMod = fechaMod.toString();
	            	archivos.setFechaModifacion(fechaFormateadaMod);
	            	
	            	logger.info("fecha de creacion:"+fechaFormateada);
	            	logger.info("fecha de Modificacion:"+fechaFormateadaMod);
	            	archivos.setRutaArchivo(bases.get().getVc_ruta_proceso()+file.getFileName().toString());
	            	listaArchivos.add(archivos);
	                return FileVisitResult.CONTINUE;
	            }
	        });
			return ResponseEntity.ok(listaArchivos);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return null;
		}
    }
	
	@GetMapping("/verArchivo")
    public ResponseEntity<Resource> descargarArchivo(@RequestParam String ruta,@RequestParam String  nombre) throws IOException {
				
		try {
        File file = new File(ruta);
        byte[] bytesPDF = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
		
		
		ContentDisposition contentDisposition = null;
		HttpHeaders h2 = new HttpHeaders();
		
		contentDisposition = ContentDisposition.builder("attachment").filename(nombre).build();
		h2.setContentDisposition(contentDisposition);
		
		return ResponseEntity.ok().contentLength((long) bytesPDF.length)
			    .contentType(MediaType.APPLICATION_PDF)
			    .headers(h2).body(new ByteArrayResource(bytesPDF));
		}catch (Exception e) {
			logger.info("Error:"+e.getMessage());
			return null;
		}
    }
	
	@PostMapping("/subirArchivo")
    public ResponseEntity<?> adjuntar(@RequestParam("file") MultipartFile[] files, 
    		@RequestParam("idBase") Integer idBase){
		
		
		String mensaje ="";
		ArrayList<String> respuesta = new ArrayList<String>();
		Optional<Bases> bases = basesService.findById(idBase);
		try {
			if(files.length > 0) {
				for(MultipartFile file: files) {
					 byte[] bytes = file.getBytes();
				        String folderPath = bases.get().getVc_ruta_proceso();   
				        Path folder = Paths.get(folderPath);

						Path nuevoPath = Paths.get(folderPath+"/"+ file.getOriginalFilename());
		                Files.write(nuevoPath, bytes);
		                       
		                mensaje="Se agrego archivo correctamente.";
		                logger.info(mensaje);
				}
			}else {
				mensaje="No subio archivo.";
				logger.info(mensaje);
			}
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("--"+e.getMessage());
			mensaje = e.getMessage();
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		}
	}
		
	public int validarAcceso(String usuarioLogin) {

		usuario = iUsuarioRepository.findByNombreUsuario(usuarioLogin);
		return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacion(Constantes.NOMBRE_PANTALLA_OFERTAS));
		
		
	}
	
	
	/**
	 * Nuevos procesos Fase 2 Ofertas Automaticas.
	 */
	
	
	/***
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/obtProceso")
	public ResponseEntity<List<ProcesoGestion>> obtenerProcesoGestion(Model model) {
		 logger.info("/obtParametros:");
		try {
			return ResponseEntity.ok(procesoGestionService.findAll());
		} catch (Exception e) {
			logger.error("--" + e.getMessage());
			return null;
		}
	}
	
	
	/**
	 * Obtener los valores de programacion de la ejecucion de gestion
	 * @param idBase
	 * @return
	 */
	
	@GetMapping("/obtDatosProceso")
	public ResponseEntity<?> obtDatosProceso(@RequestParam(name = "idBase") Integer idBase){		
		try {
			logger.info("/obtDatosProceso:"+idBase);
			return ResponseEntity.ok(procesoGestionService.findById(idBase));
		} catch (Exception e) {
			logger.info("--" + e.getMessage());
			return null;
		}
		
	}
	/**
	 * 
	 * @param basesID
	 * @param dComienzoProceso
	 * @param dFinalProceso
	 * @param hComienzoProceso
	 * @param hFinalProceso
	 * @param mPeriodoEjecucion
	 * @param rutaArchivo
	 * @return
	 */
	
	@PostMapping("/actualizarParametroGestion")
	public ResponseEntity<?> actualizarParametroGestion(
			@RequestParam(name = "procesoGestionID") Integer procesoGestionID, 
			@RequestParam(name = "dComienzoProceso") String dComienzoProceso, 
			@RequestParam(name = "dFinalProceso") String dFinalProceso,
			@RequestParam(name = "hComienzoProceso") String hComienzoProceso,
			@RequestParam(name = "hFinalProceso") String hFinalProceso,
			@RequestParam(name = "mPeriodoEjecucion") String mPeriodoEjecucion
			){
		logger.info("/actualizarParametro");
		
		String mensaje ="";
		String tipo =Constantes.MENSAJE_OK;
		Map<String, Object> respuesta = new HashMap<>();

		try {
			if(procesoGestionService.actParams(procesoGestionID,dComienzoProceso,dFinalProceso,hComienzoProceso,hFinalProceso,mPeriodoEjecucion) == 1) {
				switch(procesoGestionID){
					case 1:
						
						gestionAsignacionConfiguracion.actualizarExpresionCronProceso(procesoGestionID);
						//nominaTareaConfiguracion.actualizarExpresionCronNomina();
						break;
					case 2:
					
						generacionSMSConfiguracion.actualizarExpresionCronProceso(procesoGestionID);
						//fidelidadTareaConfiguracion.actualizarExpresionCronFidelidad();
						break;
				}
				mensaje = Constantes.ACTUALIZACION_OK;
				
			}else {
				mensaje = Constantes.ACTUALIZACION_ERROR;
				tipo = Constantes.MENSAJE_ERROR;
			}
			respuesta.put(Constantes.TEXTO_MNS,mensaje);
			respuesta.put(Constantes.TIPO_MNS,tipo);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.put(Constantes.TEXTO_MNS,e.getMessage());
			respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
			return ResponseEntity.ok(respuesta);
		}
	}
	
	
	/***
	 * 
	 * @return
	 */
	@PostMapping("/ejecutarDatosProceso")
	public ResponseEntity<?> ejecutarDatosProceso(
			@RequestParam(name = "idProceso") Integer idProceso
			){
	
		logger.info("/ejecutarDatosProceso");
		//ArrayList<String> respuesta = new ArrayList<String>();
		String mensaje ="";
		String tipo =Constantes.MENSAJE_OK;
		String envio = Constantes.NO_ENVIA_CORREO_ASIGNACION;
	
		Map<String, Object> respuesta = new HashMap<>();
		
		try {
			switch(idProceso) {
			case 1:
				try {
					error = gestionAsignacionConfiguracion.procesarAsignacion(idProceso,Constantes.PROCESO_MANUAL);
					logger.debug("Ejecutar proceso Asignacion Gestiones.");
					if(error == null ||Constantes.ENVIA_CORREO_ASIGNACION.equals(error)||Constantes.NO_ENVIA_CORREO_ASIGNACION.equals(error)) {
						mensaje = Constantes.ASIGNACION_OK;
						if(error != null ) {
							envio = Constantes.ENVIA_CORREO_ASIGNACION.equals(error)?Constantes.ENVIA_CORREO_ASIGNACION:Constantes.NO_ENVIA_CORREO_ASIGNACION;
						}	
							
					}else {
						mensaje = Constantes.TEXTO_KO.replace(Constantes.IDENTIFICADOR_ERROR,error);
						tipo =Constantes.MENSAJE_ERROR;
					}
				} catch (Exception e) {
					mensaje ="Error:"+e.getMessage();
					tipo =Constantes.MENSAJE_ERROR;
					logger.error("Error"+e.getMessage());
				}
				
				break;
			case 2:
				try {
					logger.debug("Ejecutar proceso Generacion SMS.");
					error = generacionSMSConfiguracion.procesarSMS(idProceso);
					if(error == null) {
						mensaje = Constantes.SMS_OK;
					}else {
						mensaje = Constantes.TEXTO_KO.replace(Constantes.IDENTIFICADOR_ERROR,error);
						tipo =Constantes.MENSAJE_ERROR;
						
					}
					
				} catch (Exception e) {
					mensaje ="Error:"+e.getMessage();
					tipo =Constantes.MENSAJE_ERROR;
					logger.error("Error"+e.getMessage());
				}
				
				break;
			}
			
			respuesta.put(Constantes.TEXTO_MNS,mensaje);
			respuesta.put(Constantes.TIPO_MNS,tipo);
			respuesta.put(Constantes.ENVIA_CORREOS, envio);
			
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.put(Constantes.TEXTO_MNS,"Error:"+e.getMessage());
			respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
			respuesta.put(Constantes.NO_ENVIA_CORREO_ASIGNACION, envio);
			return ResponseEntity.ok(respuesta);
		}
	}
	
	@PostMapping("/correoAsignacion")
	public void correoAsignacion(){
		try {
			// Metodo envio de correo y adjunto
        	envioCorreoService.preparCorreoAdjunto(Constantes.CORREO_ASIGNACION,Constantes.REPORTE_ASIGNACION,Constantes.PARAMETRO_ADJUNTO_ASIGNACION_REASIGMACIONES,Constantes.NOMBRE_REPORTE_ASIGNACION,Constantes.TIPO_BUSQUEDA_1);

		} catch (Exception e) {
			logger.info(Constantes.MENSAJE_ERROR + e.getMessage());
		}
	}
	
	@PostMapping("/enviaCorreoSucursal")
	public void preparCorreoAdjuntoSuc(@RequestParam(name = "numSucursal") Integer numSucursal){
		try {
			// Metodo envio de correo y adjunto
        	envioCorreoService.preparCorreoAdjuntoSuc(Constantes.CORREO_ASIGNACION,Constantes.REPORTE_ASIGNACION,Constantes.PARAMETRO_ADJUNTO_ASIGNACION_REASIGMACIONES,Constantes.NOMBRE_REPORTE_ASIGNACION,numSucursal);

		} catch (Exception e) {
			logger.info(Constantes.MENSAJE_ERROR + e.getMessage());
		}
	}
	
	
	@GetMapping("/obtGeneralesGestion")
	public ResponseEntity<?> obtGeneralesGestion(){		
		try {
			
			return ResponseEntity.ok(registrosAsignacionService.obtRegistroParametros());
		} catch (Exception e) {
			logger.info("--" + e.getMessage());
			return null;
		}
		
	}
	
	
	/**
	 * 
	 */
	@PostMapping("/reporteGestionesAsignadas")
	public ResponseEntity<?> reporteOA(
			@RequestParam("idSucursal") Integer idSucursal){
		
		Map<String, Object> respuesta = new HashMap<>();
		
		try {
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_ASIGNACION_X_SUCURSAL);
			
			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)+datosReporte.getPk_id_reporte()+"&parametros=?IDSUCURSAL="
					+idSucursal+"&tipoDocumento="+datosReporte.getVc_extension()+"&identificadorDoc="+idSucursal;
			
			respuesta.put(Constantes.TEXTO_MNS,Constantes.REPORTE_OK);
			respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_OK);
			respuesta.put(Constantes.URL_REPORTE,reporteURL);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.put(Constantes.TEXTO_MNS,e.getMessage());
			respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
			respuesta.put(Constantes.URL_REPORTE,null);
			return ResponseEntity.ok(respuesta);
		}
				
		
	}
	

}
