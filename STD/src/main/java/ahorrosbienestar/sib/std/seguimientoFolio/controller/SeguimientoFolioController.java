package ahorrosbienestar.sib.std.seguimientoFolio.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.catalogos.dto.ActividadesPA;
import ahorrosbienestar.sib.std.catalogos.dto.ListaFolioXEmpleado;
import ahorrosbienestar.sib.std.catalogos.dto.SeguimientoActividadPA;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.correoCSO.service.CorreoCSOService;
import ahorrosbienestar.sib.std.filesCSO.model.ArchivosFolios;
import ahorrosbienestar.sib.std.filesCSO.service.ArchivosFoliosService;
import ahorrosbienestar.sib.std.planAccion.controller.PlanAccionController;
import ahorrosbienestar.sib.std.seguimientoFolio.model.SeguimientoFolio;
import ahorrosbienestar.sib.std.seguimientoFolio.repository.ISeguimientoFolioRepository;
import ahorrosbienestar.sib.std.seguimientoFolio.service.SeguimientoFolioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;



@Controller
@RequestMapping("seguimientoFolio")
public class SeguimientoFolioController {

	private static final Logger logger = Logger.getLogger(PlanAccionController.class);

	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private ISeguimientoFolioRepository iSeguimientoFolioRepository;
	
	@Autowired
	private SeguimientoFolioService seguimientoFolioService;
	
	@Autowired
	private CatalogosCEService catalogosCEService;
	
	@Autowired
	private ArchivosFoliosService archivosFoliosService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private CorreoCSOService correoCSOService;
	
	private String rolAdmin = "ROLE_RESPONSABLE_CSO";
	private String rolResponsable = "ROLE_ADMIN_CSO";
	private int catEstatus = 6;
	
	@Autowired
	public SeguimientoFolioController(SeguimientoFolioService seguimientoFolioService) {
		this.seguimientoFolioService = seguimientoFolioService;
	}
	
	@GetMapping("/seguimiento")
	public String inicioFolio(Model model) {
		
		try {
					
			model.addAttribute("cat_lista_estatus", iCatalogoRepository.generarOpciones(catEstatus));
			
			return "seguimientoFolio/seguimiento";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @param idFolio
	 * @param model
	 * @return
	 */
	@GetMapping("/buscarActividadesxEmpleado")
	public ResponseEntity<?> buscarActividadesPlan(@RequestParam("idFolio") Integer idFolio,@RequestParam("estatus") Integer estatus) {
		
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			List<ActividadesPA> listaActividades;
			if(usuario.get(0).getRol_idRol().getVc_nombre_rol().equals(rolAdmin) || usuario.get(0).getRol_idRol().getVc_nombre_rol().equals(rolResponsable)) {
				listaActividades = catalogosCEService.buscarActividadesSupervisor(idFolio, usuario.get(0).getI_id_empleado(),usuario.get(0).getPk_id_usuario(),estatus);
			}else {
				listaActividades = catalogosCEService.buscarActividadesxEmpleado(idFolio, usuario.get(0).getI_id_empleado(),estatus);
			}
			
			return ResponseEntity.ok((listaActividades != null)?listaActividades:0);
		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
			return null;
		}
	}
	/**
	 * 
	 * @param idActividad
	 * @param desc_seguimiento
	 * @param estatus
	 * @return
	 */
	@PostMapping("/altaSeguimiento")
	public ResponseEntity<?> altaSeguimiento(
			@RequestParam(name = "idActividad") Integer idActividad
		   ,@RequestParam(name = "desc_seguimiento") String desc_seguimiento
		   ,@RequestParam(name = "estatus") Integer estatus){
		
		String mensaje ="Registro Correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			SeguimientoFolio altaSeguimiento = new SeguimientoFolio();
			LocalDate fecha = LocalDate.now();
			
			altaSeguimiento.setDt_fecha_registro(fecha);
			altaSeguimiento.setI_planaccion(idActividad);
			altaSeguimiento.setI_usuario_registro(usuario.get(0).getPk_id_usuario());
			altaSeguimiento.setVc_desc_seguimiento(desc_seguimiento);
			altaSeguimiento.setI_confirmacion_responsable(estatus);
			
			iSeguimientoFolioRepository.save(altaSeguimiento);
			catalogosCEService.actEstatus(idActividad,4,3);
			if(estatus == 1) {
				catalogosCEService.actEstatus(idActividad,3,1);
				correoCSOService.registrarCorreo(idActividad,2);
			}
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}		
	}
	
	/**
	 * 
	 * @param idActividad
	 * @param desc_seguimiento
	 * @param estatus
	 * @return
	 */
	@PostMapping("/modSeguimiento")
	public ResponseEntity<?> modSeguimiento(
			@RequestParam(name = "idSeguimiento") Integer idSeguimiento
		   ,@RequestParam(name = "desc_seguimiento") String desc_seguimiento
		   ,@RequestParam(name = "estatus") Integer estatus
		   ,@RequestParam(name = "idActividad") Integer idActividad){
		
		String mensaje ="Se Actualizo Correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			seguimientoFolioService.modificarSeguimiento(idSeguimiento,desc_seguimiento,estatus,usuario.get(0).getPk_id_usuario());
			if(estatus == 1) {
				catalogosCEService.actEstatus(idActividad,3,1);
				correoCSOService.registrarCorreo(idActividad,2);
			}
		
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}		
	}
	
	/**
	 * 
	 * @param idObservacion
	 * @param model
	 * @return
	 */
	@GetMapping("/obtSeguimiento")
	public ResponseEntity<List<SeguimientoActividadPA>> buscarSeguimiento(@RequestParam("idActividad") Integer idActividad,Model model) {
		
		try {
			return ResponseEntity.ok(catalogosCEService.obtSeguimientoxActividad(idActividad));
		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param idFolio
	 * @return
	 */
	@GetMapping("/obtenerID")
	public ResponseEntity<?> obtenerID(@RequestParam("idSeguimiento") Integer idSeguimiento){
		try {
			return ResponseEntity.ok(iSeguimientoFolioRepository.findById(idSeguimiento));
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
	@GetMapping("/buscarArchivos")
	public ResponseEntity<List<ArchivosFolios>> buscarArchivos(@RequestParam("idFolio") Integer idFolio,Model model) {
		
		try {
			return ResponseEntity.ok(archivosFoliosService.obtRegistros(idFolio));
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
					 byte[] bytes = file.getBytes();
					 File directorio = new File(UrlPDFServidor()+idFolio);
					 if(!directorio.exists()) {
						 if ((directorio).mkdirs()) {
							 	Path nuevoPath = Paths.get(UrlPDFServidor()+idFolio+"/"+ file.getOriginalFilename());
		                        Files.write(nuevoPath, bytes);
		                        datosArchivo(idFolio+"/"+ file.getOriginalFilename(),idFolio,file.getOriginalFilename());
		                        mensaje="Se agrego archivo correctamente.";
						 }else {
							 logger.error("Error al crear directorio: " + directorio);
						 }
					 }else {
						 Path nuevoPath = Paths.get(UrlPDFServidor() +idFolio+"/"+ file.getOriginalFilename());
	                     Files.write(nuevoPath, bytes);
	                     datosArchivo(idFolio+"/"+ file.getOriginalFilename(),idFolio,file.getOriginalFilename());
	                     mensaje="Se agrego archivo correctamente.";
					 }
				}
			}else {
				mensaje="No subio archivo.";
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
	public int datosArchivo(String urlArchivo,String idFolio, String nombreArchivo){
		Integer estatus = 1;
		Integer respuesta=0;
		try {
		
			LocalDate fecha = LocalDate.now();
			ArchivosFolios alta = new ArchivosFolios();
			alta.setI_folio(Integer.parseInt(idFolio));
			alta.setDt_fecha_registro(fecha);
			alta.setI_estatus(estatus);
			alta.setVc_ruta_docto(urlArchivo);
			alta.setVc_nombre_docto(nombreArchivo);
			
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
	
	@GetMapping("/obtFolios")
	public ResponseEntity<?> obtFolios(@RequestParam("estatus") Integer estatus){
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			List<ListaFolioXEmpleado> listaFolio;
			
			if(usuario.get(0).getRol_idRol().getVc_nombre_rol().equals(rolAdmin) || usuario.get(0).getRol_idRol().getVc_nombre_rol().equals(rolResponsable)) {
				listaFolio = catalogosCEService.listaActividadPASupervisor(usuario.get(0).getI_id_empleado(),usuario.get(0).getPk_id_usuario(),estatus);
			}else {
				listaFolio = catalogosCEService.listaActividadPAXEmpleado(usuario.get(0).getI_id_empleado(),estatus);
			}

			return ResponseEntity.ok((listaFolio != null)?listaFolio:0);
		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
			return null;
		}
	
	}
}
