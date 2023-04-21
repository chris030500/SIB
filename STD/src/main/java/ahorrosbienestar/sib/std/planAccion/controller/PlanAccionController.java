package ahorrosbienestar.sib.std.planAccion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("planAccion")
public class PlanAccionController {

	@GetMapping("/aceptarAsignacion")
	public String inicioFolio(Model model) {
		try {
			
			return "planAccion/inicio";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
		}
		return null;
	}
}
