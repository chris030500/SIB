package ahorrosbienestar.sib.std.prevencion_pld.pld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("prevencion")
public class PldController {
	
	
	//Método para mostrar la pantalla de Filtrado QEQ
	@GetMapping("/pld")
	public String FiltradoQEQ() {
		return "prevencion/pld";
	}
	
}
