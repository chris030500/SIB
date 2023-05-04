package ahorrosbienestar.sib.std.planAccion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public PlanAccionController(PlanAccionService planAccionService) {
		this.planAccionService = planAccionService;
	}
	
	@GetMapping("/aceptarAsignacion")
	public String inicioFolio(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			model.addAttribute("lista_observaciones",iObservacionRepository.findAllxEmpleado(usuario.get(0).getI_id_empleado()));
			
			return "planAccion/inicio";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
		}
		return null;
	}
}
