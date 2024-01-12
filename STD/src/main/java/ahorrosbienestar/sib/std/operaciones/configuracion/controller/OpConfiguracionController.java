package ahorrosbienestar.sib.std.operaciones.configuracion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("operaciones")
public class OpConfiguracionController {

	@GetMapping("/configuracion")
	public String ModuloOperacion() {
		return "operaciones/configuracion";
	}
	
	@GetMapping("/seguimientoGestion")
	public String ModuloSeguimientoGestion() {
		return "operaciones/seguimientoGestion";
	}

	@GetMapping("/reportes")
	public String ModuloReportes() {
		return "operaciones/reportes";
	}
}
