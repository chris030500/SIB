package ahorrosbienestar.sib.std.autorizacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("autorizar")
public class AutorizacionController {

	// Mostrar pantalla de autorizacion videograbacion
	@GetMapping("/index")
	public String AutorizarVideograbacion() {
		return "autorizacion/autorizacion";
	}
	
}
