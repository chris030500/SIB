package ahorrosbienestar.sib.std.seguimientoFolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("seguimientoFolio")
public class SeguimientoFolioController {

	@GetMapping("/seguimiento")
	public String inicioFolio(Model model) {
		try {
			
			return "seguimientoFolio/seguimiento";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
		}
		return null;
	}
}
