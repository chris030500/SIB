package ahorrosbienestar.sib.std.planAccion.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.catalogos.dto.ActividadesPA;
import ahorrosbienestar.sib.std.catalogos.dto.ArchivosAdjuntos;
import ahorrosbienestar.sib.std.catalogos.dto.ListaFolioXEmpleado;
import ahorrosbienestar.sib.std.catalogos.dto.ObservacionesxFolio;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.correoCSO.service.CorreoCSOService;
import ahorrosbienestar.sib.std.filesCSO.model.ArchivosFolios;
import ahorrosbienestar.sib.std.filesCSO.service.ArchivosFoliosService;
import ahorrosbienestar.sib.std.folios.repository.IFolioRepository;
import ahorrosbienestar.sib.std.folios.repository.IObservacionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.planAccion.model.PlanAccion;
import ahorrosbienestar.sib.std.planAccion.repository.IPlanAccionRepository;
import ahorrosbienestar.sib.std.planAccion.service.PlanAccionService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("planAccion")
public class PlanAccionController {

	private static final Logger logger = Logger.getLogger(PlanAccionController.class);

	
	@Autowired
	private IObservacionRepository iObservacionRepository;
	
	@Autowired
	private PlanAccionService planAccionService;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private CatalogosCEService catalogosCEService;
	
	@Autowired
	private CorreoCSOService correoCSOService;
	
	@Autowired
	private IFolioRepository iFolioRepository;

	@Autowired
	private IPlanAccionRepository iPlanAccionRepository;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private ArchivosFoliosService archivosFoliosService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	
	private int catEstatus = 6;
	private int catFinActividad = 8;
	private int tipoArchivo = 9;
	
	private static final String nombrePantalla = "Registro de Plan de Accion";
	private static final String rolMonitoreo = "ROLE_MONITOREO";
	private static final String rutaError = "errores/error_403";
	private String rutaDireccion="";
	
	List<Usuario> usuario = null;
	
	@Autowired
	public PlanAccionController(PlanAccionService planAccionService) {
		this.planAccionService = planAccionService;
	}
	/**
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/aceptarAsignacion")
	public String inicioFolio(Model model) {
		try {
			
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			if(validarAcceso() == 1) {
				model.addAttribute("lista_encargados", catalogosCEService.busquedaxAreaEmpleado(usuario.get(0).getI_id_empleado()));
				model.addAttribute("cat_lista_estatus", iCatalogoRepository.generarOpciones(catEstatus));
				model.addAttribute("cat_lista_tipoArchivo", iCatalogoRepository.generarOpciones(tipoArchivo));
				model.addAttribute("cat_opcion_fin", iCatalogoRepository.generarOpciones(catFinActividad));
				model.addAttribute("rolMonitoreo", usuario.get(0).getRol_idRol().getVc_nombre_rol().equalsIgnoreCase(rolMonitoreo));
				rutaDireccion = "planAccion/inicio";
			}else {
				rutaDireccion = rutaError;
			}
		
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			return null;
		}
		
	}
	/**
	 * 
	 * @param idObservacion
	 * @return
	 */
	@PostMapping("/aceptarObservacion")
	public ResponseEntity<?> aceptarObservacion(@RequestParam(name = "idObservacion") Integer idObservacion){
		String mensaje ="Actualizo estatus correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			iObservacionRepository.aceptarObservacion(idObservacion,usuario.get(0).getPk_id_usuario());
			catalogosCEService.actEstatus(idObservacion,2,2);
			respuesta.add(mensaje);
				
			
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			mensaje = e.getMessage();
			respuesta.add(mensaje);
		}
		logger.info(respuesta);
		return ResponseEntity.ok(respuesta);
	}
	/**
	 * 
	 * @param idObservacion
	 * @param vc_motivo
	 * @return
	 */
	@PostMapping("/rechazarObservacion")
	public ResponseEntity<?> rechazarObservacion(@RequestParam(name = "idObservacion") Integer idObservacion, @RequestParam(name = "vc_motivo") String vc_motivo){
		String mensaje ="Actualizo registro correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			if(iObservacionRepository.rechazarObservacion(idObservacion,vc_motivo,usuario.get(0).getPk_id_usuario()) == 1) {
				correoCSOService.registrarCorreo(idObservacion,1);
				respuesta.add(mensaje);
			}
			logger.info(respuesta);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			mensaje = e.getMessage();
			respuesta.add(mensaje);
			logger.info(respuesta);
			return ResponseEntity.ok(respuesta);
		}
		
	}
	
	
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listaObservaciones", method = RequestMethod.GET)
	public List<ObservacionesxFolio> listaObservaciones(@RequestParam("idFolio") Integer idFolio, @RequestParam("estatus") Integer estatus) {
		try {
			
			return (this.usuario.get(0).getRol_idRol().getVc_nombre_rol().equalsIgnoreCase(rolMonitoreo))?catalogosCEService.obtObservaciones(idFolio,estatus):catalogosCEService.obtObservacionesxEmpleado(this.usuario.get(0).getI_id_empleado(), idFolio,estatus);
			
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * 
	 * @param idFolio
	 * @return
	 */
	@GetMapping("/buscarFolio")
	public ResponseEntity<?> buscarFolio(@RequestParam("idFolio") Integer idFolio){
		try {
			return ResponseEntity.ok(iFolioRepository.findById(idFolio));
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			return null;
		}
	
	}
	/**
	 * 
	 * @param idFolio
	 * @param model
	 * @return
	 */
	@GetMapping("/buscarActividadesPlan")
	public ResponseEntity<List<ActividadesPA>> buscarActividadesPlan(@RequestParam("idObservacion") Integer idObservacion,Model model) {
		
		try {
			return ResponseEntity.ok(catalogosCEService.buscarActividadesxObservacion(idObservacion));
					
					
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			return null;
		}
	}

	//Dar de alta un rol utilizando metodo POST
	@PostMapping("/altaActividad")
	public ResponseEntity<?> altaFolio(
			@RequestParam(name = "idObservacion") Integer idObservacion, 
			@RequestParam(name = "nombreActividad") String nombreActividad,
			@RequestParam(name = "idEncargado") Integer idEncargado,
			@RequestParam(name = "dtFechaInicio") String dtFechaInicio,
			@RequestParam(name = "dtFechaVencimiento") String dtFechaVencimiento
			){
		int estatusActivo = 2;
		int confirmacionNo = 2;
		String mensaje ="Registro Correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

				PlanAccion altaActividad = new PlanAccion();
				LocalDate fecha = LocalDate.now();
				altaActividad.setDt_fecha_asignacion(fecha);
				altaActividad.setDt_fecha_inicio(LocalDate.parse(dtFechaInicio));
				altaActividad.setDt_fecha_registro(fecha);
				altaActividad.setDt_fecha_vencimiento(LocalDate.parse(dtFechaVencimiento));
				altaActividad.setI_observacion(idObservacion);
				altaActividad.setVc_desc_actividad(nombreActividad);
				altaActividad.setI_usuario_registro(usuario.get(0).getPk_id_usuario());
				altaActividad.setI_estatus(estatusActivo);
				altaActividad.setI_usuario_mod(0);
				altaActividad.setI_usuario_elimino(0);
				altaActividad.setI_responsable(idEncargado);
				altaActividad.setI_confirmacion_encargado(confirmacionNo);
				altaActividad.setI_confirmacion_responsable(confirmacionNo);
				
				iPlanAccionRepository.save(altaActividad);
				
				catalogosCEService.actEstatus(idObservacion,2,3);
				
				respuesta.add(mensaje);
			
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}
	
	}
	
	@ResponseBody
	@RequestMapping(value = "/editarActividad", method = RequestMethod.GET)
	public Optional<PlanAccion> cargarObservacion(@RequestParam(name = "idActividad") Integer idActividad ) {
		try {
			return iPlanAccionRepository.findById(idActividad);
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			return null;
		}
		
		
	}
	
	//Modifcar una actividad del plan de accion.
	@PostMapping("/modificarPA")
	public ResponseEntity<?> modificarPA(
			@RequestParam(name = "idActividad") Integer idActividad
			,@RequestParam(name = "nombreActividad") String nombreActividad
			,@RequestParam(name = "idEncargado") Integer idEncargado
			,@RequestParam(name = "dtFechaInicio") String dtFechaInicio
			,@RequestParam(name = "dtFechaVencimiento") String dtFechaVencimiento
			,@RequestParam(name = "confirmaResponsable") Integer confirmaResponsable
			){
		
		String mensaje ="Se actualizo registro correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			planAccionService.modificarPA(idActividad,nombreActividad,idEncargado,dtFechaInicio,dtFechaVencimiento,usuario.get(0).getPk_id_usuario(),confirmaResponsable);
			catalogosCEService.actEstatus(idActividad,4,5);
			catalogosCEService.actEstatusCascada(idActividad);
			logger.info(respuesta);
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			respuesta.add(""+e.getMessage());
			logger.info(respuesta);
			return ResponseEntity.ok(respuesta);
		}
	}
	
	/**
	 * observaciones por folio
	 * @param model
	 * @return
	 */
	@GetMapping("/buscarArchivos")
	public ResponseEntity<List<ArchivosAdjuntos>> buscarArchivos(@RequestParam("idFolio") Integer idFolio,Model model) {
		
		try {
			return ResponseEntity.ok(catalogosCEService.datosArchivos(idFolio));
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * observaciones por folio
	 * @param model
	 * @return
	 */
	@GetMapping("/buscarPDF")
	public ResponseEntity<Resource> buscarPDF(@RequestParam("idArchivo") Integer idArchivo,Model model) {
		try {
			Optional<ArchivosFolios> dtos= archivosFoliosService.porID(idArchivo);
			File file = new File(UrlPDFServidor()+dtos.get().getVc_ruta_docto());
			byte[] bytesPDF = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			
			
			ContentDisposition contentDisposition = null;
			HttpHeaders h2 = new HttpHeaders();
			
			contentDisposition = ContentDisposition.builder("attachment").filename(dtos.get().getVc_nombre_docto().toString()).build();
			h2.setContentDisposition(contentDisposition);
			
			return ResponseEntity.ok().contentLength((long) bytesPDF.length)
				    .contentType(MediaType.APPLICATION_PDF)
				    .headers(h2).body(new ByteArrayResource(bytesPDF));
			
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			return null;
		}
	}
	
	@GetMapping
	public String UrlPDFServidor() {
		String url =  configService.obtenerUrlCSO().get(0).getVc_valor_propiedad();
		return url.toString();
	}
	
	@PostMapping("/subirArchivo")
    public ResponseEntity<?> adjuntar(@RequestParam("file") MultipartFile[] files, 
    		@RequestParam("idFolio") String idFolio){
		
		String mensaje ="Se actualizo correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		
		try {
			if(files.length > 0) {
				for(MultipartFile file: files) {
					if(archivosFoliosService.existeNombre(file.getOriginalFilename(),idFolio) == 0) {
					 byte[] bytes = file.getBytes();
					 File directorio = new File(UrlPDFServidor()+idFolio);
					 if(!directorio.exists()) {
						 if ((directorio).mkdirs()) {
							 	Path nuevoPath = Paths.get(UrlPDFServidor()+idFolio+"/"+ file.getOriginalFilename());
		                        Files.write(nuevoPath, bytes);
		                        datosArchivo(idFolio+"/"+ file.getOriginalFilename(),idFolio,file.getOriginalFilename());
		                        mensaje="Se agrego archivo correctamente.";
		                        logger.info(mensaje);
						 }else {
							 logger.error("Error al crear directorio: " + directorio);
						 }
					 }else {
						 Path nuevoPath = Paths.get(UrlPDFServidor() +idFolio+"/"+ file.getOriginalFilename());
	                     Files.write(nuevoPath, bytes);
	                     datosArchivo(idFolio+"/"+ file.getOriginalFilename(),idFolio,file.getOriginalFilename());
	                     mensaje="Se agrego archivo correctamente.";
	                     logger.info(mensaje);
					 }
					}else {
						   mensaje="Ya cuenta con un archivo registrado con este nombre:"+file.getOriginalFilename();
						   logger.info(mensaje);
					}
				}
			}else {
				mensaje="No subio archivo.";
				logger.info(mensaje);
			}
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			mensaje = e.getMessage();
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		}
	}
	public int datosArchivo(String urlArchivo,String idFolio, String nombreArchivo){
		Integer estatus = 1;
		Integer respuesta=0;
		try {
		
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			LocalDate fecha = LocalDate.now();
			ArchivosFolios alta = new ArchivosFolios();
			alta.setI_folio(Integer.parseInt(idFolio));
			alta.setDt_fecha_registro(fecha);
			alta.setI_estatus(estatus);
			alta.setVc_ruta_docto(urlArchivo);
			alta.setVc_nombre_docto(nombreArchivo);
			alta.setI_empleado_registro(usuario.get(0).getI_id_empleado());
			
			ArchivosFolios registro = archivosFoliosService.registrar(alta);
			
			if(registro.getPk_id_archivo() > 0) {
				respuesta = 1;
			}else {
				respuesta = 0;
			}
			return respuesta;
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			return 0;
		}
	}
	
	@GetMapping("/obtFolios")
	public ResponseEntity<?> obtFolios(@RequestParam("estatus") Integer estatus){
		try {
//			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//			UserDetails userDetails = null;
//			if (principal instanceof UserDetails) {
//				userDetails = (UserDetails) principal;
//			}
//			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
//			
			List<ListaFolioXEmpleado>  listaFolio = null;
			
			if(usuario.get(0).getRol_idRol().getVc_nombre_rol().equalsIgnoreCase(rolMonitoreo)) {
				listaFolio = catalogosCEService.listaFolios(estatus);
			}else {
				listaFolio = catalogosCEService.listaFolioXEmpleado(this.usuario.get(0).getI_id_empleado(),estatus);	
			}
			
			return ResponseEntity.ok((listaFolio != null)?listaFolio:0);
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			return null;
		}
	
	}
	
	public int validarAcceso() {
		
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
		
		return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacion(nombrePantalla));
		
		
	}
	
}
