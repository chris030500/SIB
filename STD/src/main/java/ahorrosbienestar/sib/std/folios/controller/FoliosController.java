package ahorrosbienestar.sib.std.folios.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import ahorrosbienestar.sib.std.catalogos.dto.ObservacionesxFolio;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.correoCSO.service.CorreoCSOService;
import ahorrosbienestar.sib.std.folios.model.Folios;
import ahorrosbienestar.sib.std.folios.model.Observaciones;
import ahorrosbienestar.sib.std.folios.repository.IFolioRepository;
import ahorrosbienestar.sib.std.folios.repository.IObservacionRepository;
import ahorrosbienestar.sib.std.folios.service.FolioService;
import ahorrosbienestar.sib.std.folios.service.ObservacionService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;


@Controller
@RequestMapping("folios")
public class FoliosController {
	
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
	
	private int catTipoOficio = 4;
	private int catReguladores = 5;
	private int catEstatus = 6;
	
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
						
			model.addAttribute("cat_tipo_oficio", iCatalogoRepository.generarOpciones(catTipoOficio));
			model.addAttribute("cat_lista_reguladores", iCatalogoRepository.generarOpciones(catReguladores));
			model.addAttribute("cat_lista_estatus", iCatalogoRepository.generarOpciones(catEstatus));
			model.addAttribute("cat_lista_empleado", catalogosCEService.obtenerEmpleado());
			model.addAttribute("cat_lista_areas", catalogosCEService.obtenerAreas());
			model.addAttribute("lista_folios",catalogosCEService.obtFolios());
			return "folios/inicio";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
		}
		return null;
	}
	
	/**
	 * Obtener el puesto del empleado seleccionado
	 * @param empleadoID
	 * @param model
	 * @return
	 */
	@GetMapping("/buscarPuesto")
	public ResponseEntity<?> buscarPuesto(@RequestParam("empleadoID") Integer empleadoID, Model model) {

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
			return ResponseEntity.ok(catalogosCEService.busquedaxArea(areaID));
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
				String mensaje ="Actualizo Correctamente.";
				ArrayList<String> respuesta = new ArrayList<String>();
				try {
					
					if(folioService.modFolio(noficio,dt_recepcion,vc_asunto,i_dirigido,vc_puesto,i_tipo_oficio,i_organo_emite,dt_registro,i_Plazo,dt_Vencimiento,i_estatus,vc_ruta) == 1) {
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
					@RequestParam(name = "folioID") Integer folioID
					){
				
				String mensaje ="Registro Correctamente.";
				ArrayList<String> respuesta = new ArrayList<String>();
				try {
					
						altaObsFolio(idAreaAtencion,idResponsableAtencion,idFechaVencimiento,desObservacion,folioID);
						catalogosCEService.actEstatus(folioID,1,2);
						respuesta.add(mensaje);
				
					return ResponseEntity.ok(respuesta);
				} catch (Exception e) {
					respuesta.add(e.getMessage());
					return ResponseEntity.ok(respuesta);
				}
			}
			
			public void altaObsFolio(Integer idAreaAtencion, Integer idResponsableAtencion, String idFechaVencimiento, String desObservacion, Integer folioID) {
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
					
					observacionService.save(altaObs);
					correoCSOService.registrarCorreo(altaObs.getPk_id_observacion());
					
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
					@RequestParam(name = "reasignar") Integer reasignar){
				
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
							altaObsFolio(idAreaAtencion,idResponsableAtencion,idFechaVencimiento,desObservacion, altaObs.get().getI_folio());
						}else {
							mensaje ="Error al Reasignar Observacion.";
							respuesta.add(mensaje);
						}
					}else if(reasignar == 0) {
						if(observacionService.modObservacion(idObservacion,idAreaAtencion,idResponsableAtencion,idFechaVencimiento,desObservacion,usuario.get(0).getPk_id_usuario(),(reasignar == 1)?usuario.get(0).getPk_id_usuario():0) == 1) {
							correoCSOService.registrarCorreo(idObservacion);
							respuesta.add(mensaje);
						}else {
							respuesta.add("No se actualizo correctamente");
						}
					}			
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
		
				return iObservacionRepository.findById(id);
			}
}
