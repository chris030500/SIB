package ahorrosbienestar.sib.std.usuarios.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.roles.model.Rol;
import ahorrosbienestar.sib.std.roles.service.RolService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired RolService rolService;

	// Inicio de usuarios
	@GetMapping("/formulario")
	public String InicioUsuario(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		// Mostramos todos los usuarios
		model.addAttribute("usuarios", usuarioService.findAll());
		return "usuario/usuarios";
	}

	@GetMapping("/altaUsuario")
	public String AltaUsuario(Model model) throws IOException {
		List<Rol> roles = rolService.findAll();
		InputStream inputStream = new FileInputStream("src/main/resources/contrasena.properties");
		Properties properties = new Properties();
		properties.load(inputStream);
		model.addAttribute("contrasena", properties.getProperty("contrasena"));
		model.addAttribute("roles", roles);
		return "usuario/altaUsuario";
	}

	@ResponseBody
	@RequestMapping(value = "usuario/update", method = RequestMethod.PUT)
	public void update(@ModelAttribute("usuario") Usuario usuario) {
		usuarioService.save(usuario);
	}

	@ResponseBody
	@RequestMapping(value = "/usuario/loadEntity/{id}", method = RequestMethod.GET)
	public Optional<Usuario> loadEntity(@PathVariable("id") Integer id) {
		return usuarioService.findById(id);
	}
	
	

}
