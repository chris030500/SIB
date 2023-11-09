package ahorrosbienestar.sib.std.cobranza.domiciliado.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("cobranza")
public class DomiciliadoController {

	// Método para mostrar la pantalla de Cobro Domiciliado
	@GetMapping("/domiciliado")
	public String FiltradoQEQ() {
		return "cobranza/domiciliado";
	}

}
