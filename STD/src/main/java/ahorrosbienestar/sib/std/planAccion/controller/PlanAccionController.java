package ahorrosbienestar.sib.std.planAccion.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.catalogos.dto.ActividadesPA;
import ahorrosbienestar.sib.std.catalogos.dto.ObservacionesxFolio;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.correoCSO.service.CorreoCSOService;
import ahorrosbienestar.sib.std.folios.model.Observaciones;
import ahorrosbienestar.sib.std.folios.repository.IFolioRepository;
import ahorrosbienestar.sib.std.folios.repository.IObservacionRepository;
import ahorrosbienestar.sib.std.planAccion.model.PlanAccion;
import ahorrosbienestar.sib.std.planAccion.repository.IPlanAccionRepository;
import ahorrosbienestar.sib.std.planAccion.service.PlanAccionService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("planAccion")
public class PlanAccionController {

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
	
	private int catEstatus = 6;
	
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
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			model.addAttribute("lista_folios", catalogosCEService.listaFolioXEmpleado(usuario.get(0).getI_id_empleado()));
			model.addAttribute("lista_encargados", catalogosCEService.busquedaxAreaEmpleado(usuario.get(0).getI_id_empleado()));
			model.addAttribute("cat_lista_estatus", iCatalogoRepository.generarOpciones(catEstatus));
			
			return "planAccion/inicio";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
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
			
			iObservacionRepository.aceptarObservacion(idObservacion);
			respuesta.add(mensaje);
				
			
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
			mensaje = e.getMessage();
			respuesta.add(mensaje);
		}
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
			
			if(iObservacionRepository.rechazarObservacion(idObservacion,vc_motivo) == 1) {
				correoCSOService.registrarCorreo(idObservacion);
				respuesta.add(mensaje);
			};
				
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
			mensaje = e.getMessage();
			respuesta.add(mensaje);
		}
		return ResponseEntity.ok(respuesta);
	}
	
	
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listaObservaciones", method = RequestMethod.GET)
	public List<ObservacionesxFolio> listaObservaciones(@RequestParam("idFolio") Integer idFolio, Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			return catalogosCEService.obtObservacionesxEmpleado(usuario.get(0).getI_id_empleado(), idFolio);
			
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
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
			System.out.print("--" + e.getMessage());
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
			System.out.print("--" + e.getMessage());
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
		int estatusActivo = 1;
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
				
				respuesta.add(mensaje);
			
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
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
			System.out.print(""+e.getMessage());
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

			planAccionService.modificarPA(idActividad,nombreActividad,idEncargado,dtFechaInicio,dtFechaVencimiento,usuario.get(0).getPk_id_usuario());
			
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			respuesta.add(""+e.getMessage());
			return ResponseEntity.ok(respuesta);
		}
	}
	
}
