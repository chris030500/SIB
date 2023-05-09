package ahorrosbienestar.sib.std.planAccion.controller;

import java.util.ArrayList;
import java.util.List;

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

import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.folios.model.Observaciones;
import ahorrosbienestar.sib.std.folios.repository.IFolioRepository;
import ahorrosbienestar.sib.std.folios.repository.IObservacionRepository;
import ahorrosbienestar.sib.std.folios.service.FolioService;
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
	private IFolioRepository iFolioRepository;
	
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
			//model.addAttribute("lista_observaciones",iObservacionRepository.findAllxEmpleado(usuario.get(0).getI_id_empleado()));
			
			return "planAccion/inicio";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
		}
		return null;
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
			
			iObservacionRepository.rechazarObservacion(idObservacion,vc_motivo);
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
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listaObservaciones", method = RequestMethod.GET)
	public List<Observaciones> listaObservaciones(@RequestParam("idFolio") Integer idFolio, Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return iObservacionRepository.findAllxEmpleado(usuario.get(0).getI_id_empleado(),idFolio);
			
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

	
}
