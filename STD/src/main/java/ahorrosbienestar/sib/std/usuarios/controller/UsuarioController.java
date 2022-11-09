package ahorrosbienestar.sib.std.usuarios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@GetMapping("/formulario")
	public String InicioUsuario() {
		return "usuario/usuarios";
	}
	
	@GetMapping("/altaUsuario")
	public String AltaUsuario() {
		return "usuario/altaUsuario";
	}
	

}
