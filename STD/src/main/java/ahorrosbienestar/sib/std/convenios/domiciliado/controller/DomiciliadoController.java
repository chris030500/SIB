package ahorrosbienestar.sib.std.convenios.domiciliado.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("convenios")
public class DomiciliadoController {

	// Método para mostrar la pantalla de Cobro Domiciliado
	@GetMapping("/domiciliado")
	public String FiltradoQEQ() {
		return "convenios/domiciliado";
	}

}
