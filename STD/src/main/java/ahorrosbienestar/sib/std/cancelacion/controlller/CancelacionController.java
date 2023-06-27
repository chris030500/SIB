package ahorrosbienestar.sib.std.cancelacion.controlller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("cancelacion")
public class CancelacionController {
	
	// Mostrar pantalla de cancelar videograbacion
	@GetMapping("/index")
	public String CancelarVideograbacion() {
		return "cancelacion/index";
	}

}
