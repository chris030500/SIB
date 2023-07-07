package ahorrosbienestar.sib.std.folios.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.catalogos.dto.ArchivosAdjuntos;
import ahorrosbienestar.sib.std.catalogos.dto.EmpleadosCE;
import ahorrosbienestar.sib.std.catalogos.dto.ListaFolios;
import ahorrosbienestar.sib.std.catalogos.dto.ObservacionesxFolio;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.correoCSO.service.CorreoCSOService;
import ahorrosbienestar.sib.std.filesCSO.model.ArchivosFolios;
import ahorrosbienestar.sib.std.filesCSO.service.ArchivosFoliosService;
import ahorrosbienestar.sib.std.folios.model.Folios;
import ahorrosbienestar.sib.std.folios.model.Observaciones;
import ahorrosbienestar.sib.std.folios.repository.IFolioRepository;
import ahorrosbienestar.sib.std.folios.repository.IObservacionRepository;
import ahorrosbienestar.sib.std.folios.service.FolioService;
import ahorrosbienestar.sib.std.folios.service.ObservacionService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
@Controller
@RequestMapping("folios")
public class FoliosController {
	
	private static final Logger logger = Logger.getLogger(FoliosController.class);

	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private IObservacionRepository iObservacionRepository;
	
	@Autowired
	private IFolioRepository iFolioRepository;
	
	@Autowired
	private CorreoCSOService correoCSOService;
	
	@Autowired
	private FolioService folioService;
	
	@Autowired
	private ObservacionService observacionService;
	
	@Autowired
	private CatalogosCEService catalogosCEService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private ArchivosFoliosService archivosFoliosService;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	private int catTipoOficio = 4;
	private int catReguladores = 5;
	private int catEstatus = 6;
	private int tipoArchivo = 9;
	
	private static final String nombrePantalla = "Alta Oficio";
	private static final String rutaError = "errores/error_403";
	private String rutaDireccion="";

	
	@Autowired
	public FoliosController (FolioService folioService) {
		this.folioService = folioService;
	}
		
	/**
	 * Cargar listas iniciales de la pantalla
	 * @param model
	 * @return
	 */
	@GetMapping("/folio")
	public String inicioFolio(Model model) {		
		try {
			if(validarAcceso() == 1) {
				model.addAttribute("cat_tipo_oficio", iCatalogoRepository.generarOpciones(catTipoOficio));
				model.addAttribute("cat_lista_reguladores", iCatalogoRepository.generarOpciones(catReguladores));
				model.addAttribute("cat_lista_estatus", iCatalogoRepository.generarOpciones(catEstatus));
				model.addAttribute("cat_lista_tipoArchivo", iCatalogoRepository.generarOpciones(tipoArchivo));
				model.addAttribute("cat_lista_empleado", catalogosCEService.obtenerEmpleado());
				model.addAttribute("cat_lista_areas", catalogosCEService.obtenerAreas());
				model.addAttribute("lista_folios",catalogosCEService.obtFolios());
				
				rutaDireccion = "folios/inicio";
			}else {
				rutaDireccion = rutaError;
			}

			return rutaDireccion;
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
		}
		return null;
	}
	
	/**
	 * observaciones por folio
	 * @param model
	 * @return
	 */
	@GetMapping("/obtenerFolios")
	public ResponseEntity<List<ListaFolios>> obtenerFolios(Model model) {
		 logger.info("/obtenerFolios");
		try {
			return ResponseEntity.ok(catalogosCEService.obtFolios());
		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
			return null;
		}
	}
	/**
	 * Obtener el puesto del empleado seleccionado
	 * @param empleadoID
	 * @param model
	 * @return
	 */
	@GetMapping("/buscarPuesto")
	public ResponseEntity<?> buscarPuesto(@RequestParam("empleadoID") Integer empleadoID, Model model) {
		logger.info("/buscarPuesto");
		try {
			return ResponseEntity.ok(catalogosCEService.obtenerPuesto(empleadoID));
		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
			return null;
		}
	}
	/**
	 * Buscar los empleados por area
	 * @param areaID
	 * @return
	 */
	@GetMapping("/busquedaxArea")
	public ResponseEntity<?> busquedaxArea(@RequestParam("areaID") Integer areaID){
		try {
			logger.info("/busquedaxArea"+"dtos:"+areaID);
			List<EmpleadosCE> listaResponsables = catalogosCEService.busquedaxArea(areaID);
			return ResponseEntity.ok((listaResponsables != null)?listaResponsables:0);
		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
			return null;
		}
	
	}
	
	//Dar de alta un rol utilizando metodo POST
			@PostMapping("/altaFolio")
			public ResponseEntity<?> altaFolio(
					@RequestParam(name = "noficio") String noficio, 
					@RequestParam(name = "dt_recepcion") String dt_recepcion,
					@RequestParam(name = "vc_asunto") String vc_asunto,
					@RequestParam(name = "i_dirigido") Integer i_dirigido,
					@RequestParam(name = "vc_puesto") String vc_puesto,
					@RequestParam(name = "i_tipo_oficio") Integer i_tipo_oficio,
					@RequestParam(name = "i_organo_emite") Integer i_organo_emite,
					@RequestParam(name = "dt_registro") String dt_registro,
					@RequestParam(name = "i_Plazo") Integer i_Plazo,
					@RequestParam(name = "dt_Vencimiento") String dt_Vencimiento,
					@RequestParam(name = "i_estatus") Integer i_estatus,
					@RequestParam(name = "vc_ruta") String vc_ruta
					){
				logger.info("/altaFolio"+"dtos:"+noficio);
				String mensaje ="Registro Correctamente.";
				ArrayList<String> respuesta = new ArrayList<String>();
				try {
					
					if(folioService.existeFolio(noficio) == 0) {
						
						
						Folios altaFolio = new Folios();
						
						altaFolio.setDt_fecha_recepcion(LocalDate.parse(dt_recepcion));
						altaFolio.setDt_fecha_registro(LocalDate.parse(dt_registro));
						altaFolio.setDt_fecha_vencimiento(LocalDate.parse(dt_Vencimiento));
						altaFolio.setVc_folio(noficio);
						altaFolio.setVc_asunto(vc_asunto);
						altaFolio.setI_dirigido(i_dirigido);
						altaFolio.setVc_puesto(vc_puesto);
						altaFolio.setI_tipo_oficio(i_tipo_oficio);
						altaFolio.setI_plazo_atencion(i_Plazo);
						altaFolio.setI_estatus(i_estatus);
						altaFolio.setVc_ruta_oficio(vc_ruta);
						altaFolio.setI_organo_emite(i_organo_emite);
						
						folioService.save(altaFolio);
												
						respuesta.add(mensaje);
					}else {
						mensaje = "El Folio ya existe";
						respuesta.add(mensaje);
					}
					
					return ResponseEntity.ok(respuesta);
				} catch (Exception e) {
					System.out.println(""+e.getMessage());
				}
				return null;
			}
			
			//Dar de alta un rol utilizando metodo POST
			@PostMapping("/modificarFolio")
			public ResponseEntity<?> modificarFolio(
					@RequestParam(name = "id") Integer id, 
					@RequestParam(name = "noficio") String noficio, 
					@RequestParam(name = "dt_recepcion") String dt_recepcion,
					@RequestParam(name = "vc_asunto") String vc_asunto,
					@RequestParam(name = "i_dirigido") Integer i_dirigido,
					@RequestParam(name = "vc_puesto") String vc_puesto,
					@RequestParam(name = "i_tipo_oficio") Integer i_tipo_oficio,
					@RequestParam(name = "i_organo_emite") Integer i_organo_emite,
					@RequestParam(name = "dt_registro") String dt_registro,
					@RequestParam(name = "i_Plazo") Integer i_Plazo,
					@RequestParam(name = "dt_Vencimiento") String dt_Vencimiento,
					@RequestParam(name = "i_estatus") Integer i_estatus,
					@RequestParam(name = "vc_ruta") String vc_ruta
					){
				logger.info("/modificarFolio"+"dtos:"+id);
				String mensaje ="Actualizo Correctamente.";
				ArrayList<String> respuesta = new ArrayList<String>();
				try {
					
					if(folioService.modFolio(id,noficio,dt_recepcion,vc_asunto,i_dirigido,vc_puesto,i_tipo_oficio,i_organo_emite,dt_registro,i_Plazo,dt_Vencimiento,i_estatus,vc_ruta) == 1) {
						respuesta.add(mensaje);
					}else {
						respuesta.add("No se actualizo correctamente");
					}
					
					return ResponseEntity.ok(respuesta);
				} catch (Exception e) {
					System.out.println(""+e.getMessage());
					return null;
				}
			}
			
			
			/**
			 * observaciones por folio
			 * @param model
			 * @return
			 */
			@GetMapping("/buscarObservaciones")
			public ResponseEntity<List<ObservacionesxFolio>> buscarObservaciones(@RequestParam("idFolio") Integer idFolio,Model model) {
				
				try {
					logger.info("/buscarObservaciones"+"dtos:"+idFolio);
					return ResponseEntity.ok(catalogosCEService.obtObservaciones(idFolio));
				} catch (Exception e) {
					System.out.print("--" + e.getMessage());
					return null;
				}
			}
						
			//Dar de alta una observacion metodo POST
			@PostMapping("/altaObservacion")
			public ResponseEntity<?> altaObservacion(
					@RequestParam(name = "idAreaAtencion") Integer idAreaAtencion, 
					@RequestParam(name = "idResponsableAtencion") Integer idResponsableAtencion,
					@RequestParam(name = "idFechaVencimiento") String idFechaVencimiento,
					@RequestParam(name = "desObservacion") String desObservacion,
					@RequestParam(name = "folioID") Integer folioID,
					@RequestParam(name = "obsOficio") String obsOficio
					){
				logger.info("/altaObservacion"+"dtos:"+idAreaAtencion);
				String mensaje ="Registro Correctamente.";
				ArrayList<String> respuesta = new ArrayList<String>();
				try {
					
						altaObsFolio(idAreaAtencion,idResponsableAtencion,idFechaVencimiento,desObservacion,folioID,obsOficio);
						catalogosCEService.actEstatus(folioID,1,2);
						respuesta.add(mensaje);
				
					return ResponseEntity.ok(respuesta);
				} catch (Exception e) {
					respuesta.add(e.getMessage());
					return ResponseEntity.ok(respuesta);
				}
			}
			
			public void altaObsFolio(Integer idAreaAtencion, Integer idResponsableAtencion, String idFechaVencimiento, String desObservacion, Integer folioID, String obsOficio) {
				Integer estatus = 1;
				try {
					Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					UserDetails userDetails = null;
					if (principal instanceof UserDetails) {
						userDetails = (UserDetails) principal;
					}
					List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
					
					LocalDate fecha = LocalDate.now();
					Observaciones altaObs = new Observaciones();
					
					altaObs.setDt_fecha_asignacion(fecha);
					altaObs.setDt_fecha_registro(fecha);
					altaObs.setDt_fecha_vencimiento(LocalDate.parse(idFechaVencimiento));
					altaObs.setI_folio(folioID);
					altaObs.setI_area(idAreaAtencion);
					altaObs.setI_responsable(idResponsableAtencion);
					altaObs.setI_estatus(estatus);
					altaObs.setVc_descripcion(desObservacion);
					altaObs.setI_usuario_registro(usuario.get(0).getPk_id_usuario());
					altaObs.setI_usuario_elimino(0);
					altaObs.setI_usuario_mod(0);
					altaObs.setI_confirma(0);
					altaObs.setVc_observacion_oficio(obsOficio);
					
					observacionService.save(altaObs);
					correoCSOService.registrarCorreo(altaObs.getPk_id_observacion(),1);
					logger.info("Registro Correo:"+altaObs.getPk_id_observacion());
				} catch (Exception e) {
					System.out.println("--"+e.getMessage());
				} 
			}
			
			//modificar una observacion metodo POST
			@PostMapping("/modificarObservacion")
			public ResponseEntity<?> modificarObservacion(
					@RequestParam(name = "idObs") Integer idObservacion, 
					@RequestParam(name = "idAreaAtencion") Integer idAreaAtencion, 
					@RequestParam(name = "idResponsableAtencion") Integer idResponsableAtencion,
					@RequestParam(name = "idFechaVencimiento") String idFechaVencimiento,
					@RequestParam(name = "desObservacion") String desObservacion,
					@RequestParam(name = "reasignar") Integer reasignar,
					@RequestParam(name = "obsOficio") String obsOficio){
				
				String mensaje ="Actualizo Correctamente.";
				ArrayList<String> respuesta = new ArrayList<String>();
				try {
					Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					UserDetails userDetails = null;
					if (principal instanceof UserDetails) {
						userDetails = (UserDetails) principal;
					}
					List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

					if(reasignar == 1) {
						if(observacionService.inactivarRegistro(idObservacion,usuario.get(0).getPk_id_usuario()) == 1) {
							Optional<Observaciones> altaObs = iObservacionRepository.findById(idObservacion);
							altaObsFolio(idAreaAtencion,idResponsableAtencion,idFechaVencimiento,desObservacion, altaObs.get().getI_folio(),obsOficio);
							respuesta.add(mensaje);
						}else {
							mensaje ="Error al Reasignar Observacion.";
							respuesta.add(mensaje);
						}
					}else if(reasignar == 0) {
						if(observacionService.modObservacion(obsOficio,idObservacion,idAreaAtencion,idResponsableAtencion,idFechaVencimiento,desObservacion,usuario.get(0).getPk_id_usuario(),(reasignar == 1)?usuario.get(0).getPk_id_usuario():0) == 1) {
							correoCSOService.registrarCorreo(idObservacion,1);
							respuesta.add(mensaje);
						}else {
							respuesta.add("No se actualizo correctamente");
						}
					}		
					logger.info("modificarObservacion:"+idObservacion);
					return ResponseEntity.ok(respuesta);
				} catch (Exception e) {
					respuesta.add(e.getMessage());
					return ResponseEntity.ok(respuesta);
				}
			}
			
			
			@ResponseBody
			@RequestMapping(value = "/cargarFolio/{id}", method = RequestMethod.GET)
			public Optional<Folios> caegarFolio(@PathVariable("id") Integer id) {
		
				return iFolioRepository.findById(id);
			}
			
			@ResponseBody
			@RequestMapping(value = "/cargarObservacion/{id}", method = RequestMethod.GET)
			public Optional<Observaciones> cargarObservacion(@PathVariable("id") Integer id) {
				try {
					return iObservacionRepository.findById(id);
				} catch (Exception e) {
					System.out.println(""+e.getMessage());
					return null;
				}
				
			}
			
			@PostMapping("/subirArchivo")
		    public ResponseEntity<?> adjuntar(@RequestParam("file") MultipartFile[] files, 
		    		@RequestParam("idFolio") String idFolio){
				
				String mensaje ="";
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
					System.out.println("--"+e.getMessage());
					mensaje = e.getMessage();
					respuesta.add(mensaje);
					return ResponseEntity.ok(respuesta);
				}
			}
			@GetMapping
			public String UrlPDFServidor() {
				String url =  configService.obtenerUrlCSO().get(0).getVc_valor_propiedad();
				return url.toString();
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
					System.out.println("--"+e.getMessage());
					return 0;
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
					logger.info("/buscarArchivos:"+idFolio);
					return ResponseEntity.ok(catalogosCEService.datosArchivos(idFolio));
				} catch (Exception e) {
					System.out.print("--" + e.getMessage());
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
					logger.info("/buscarPDF:"+idArchivo);
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
					System.out.print("--" + e.getMessage());
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
