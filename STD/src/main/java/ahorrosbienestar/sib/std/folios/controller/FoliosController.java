package ahorrosbienestar.sib.std.folios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("folios")
public class FoliosController {
	
	@GetMapping("/folio")
	public String inicioFolio(Model model) {
		try {
			
			return "folios/inicio";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
		}
		return null;
	}

}
