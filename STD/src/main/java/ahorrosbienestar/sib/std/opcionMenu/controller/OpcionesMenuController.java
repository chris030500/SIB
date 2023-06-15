package ahorrosbienestar.sib.std.opcionMenu.controller;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.planAccion.controller.PlanAccionController;

@Controller
@RequestMapping("opciones")
public class OpcionesMenuController {
	private static final Logger logger = Logger.getLogger(OpcionesMenuController.class);

//	@GetMapping("/generarMenu")
//	public ResponseEntity<?> buscarActividadesPlan(Model model) {
//		System.out.println("Buscando Opciones.....");
//		return null;
//	}
	
}
