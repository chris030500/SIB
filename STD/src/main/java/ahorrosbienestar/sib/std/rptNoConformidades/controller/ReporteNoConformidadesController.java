package ahorrosbienestar.sib.std.rptNoConformidades.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("reporteFolio")
public class ReporteNoConformidadesController {

	@GetMapping("/noConformidades")
	public String inicioFolio(Model model) {
		try {
			
			return "reporteFolio/noConformidades";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
		}
		return null;
	}
}
