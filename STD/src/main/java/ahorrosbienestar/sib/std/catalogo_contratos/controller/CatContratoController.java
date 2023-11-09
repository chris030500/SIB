package ahorrosbienestar.sib.std.catalogo_contratos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("catalogo_contratos")
public class CatContratoController {
	
	// Mostrar la pantalla de contratos | documentos
	@GetMapping("/documento")
	public String ContratoDocumento( ) {
		return "catalogo_contratos/documento";
	}

}
