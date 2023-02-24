package ahorrosbienestar.sib.std.usuarios.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.configuracion.service.ConfiguracionService;
import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;
import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaService;
import ahorrosbienestar.sib.std.roles.model.Rol;
import ahorrosbienestar.sib.std.roles.service.RolService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired 
	private RolService rolService;
	
	@Autowired 
	private ConfigService configService;
	
	@Autowired 
	private HistContrasenaService histContrasenaService;
	
	@Autowired
	private ConfiguracionService configuracionService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	// Inicio de usuarios
	@GetMapping("/formulario")
	public String InicioUsuario(Model model) {
		logger.info("Ejecutando formulario");
		Usuario usuario = new Usuario();
		HistContrasena hc = new HistContrasena();
		//System.out.println("hc: " + hc.getUsr_historico_contrasena_id().getPk_id_usuario());
		model.addAttribute("historico", hc);
		model.addAttribute("usuario", usuario);
		
		// Mostramos todos los usuarios
		model.addAttribute("usuarios", usuarioService.findAll());
		return "usuario/usuarios";
	}

	@GetMapping("/altaUsuario")
	public String AltaUsuario(Model model) throws IOException {
		logger.info("Ejecutando alta usuario");
		List<Rol> roles = rolService.findAll();
		String contrasena = configService.findAll().get(5).getVc_valor_propiedad().toString();
//		InputStream inputStream = new FileInputStream("src/main/resources/contrasena.properties");
//		Properties properties = new Properties();
//		properties.load(inputStream);
		
		model.addAttribute("contrasena", contrasena);
		model.addAttribute("roles", roles);
		return "usuario/altaUsuario";
	}

	@ResponseBody
	@RequestMapping(value = "usuario/update", method = RequestMethod.PUT)
	public void update(@ModelAttribute("usuario") Usuario usuario) {
		logger.info("Ejecutando editar usuario");
		usuarioService.save(usuario);
	}

	@ResponseBody
	@RequestMapping(value = "/usuario/loadEntity/{id}", method = RequestMethod.GET)
	public Optional<Usuario> loadEntity(@PathVariable("id") Integer id) {
		logger.info("Ejecutando loadEntity en usuarioController");
		System.out.println("Entro en loadEntity en usuarioController");
		return usuarioService.findById(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/usuario/cambioContra/{id}", method = RequestMethod.GET)
	public Optional<HistContrasena> loadCambioContra(@PathVariable("id") Integer id) {
		logger.info("Ejecutando loadCambioContra en usuarioController");
		System.out.println("Entro en loadCambioContra en usuarioController");
		return histContrasenaService.findByIdHisto(id);
	}
	
	@GetMapping("/mensajeContrasena")
	public ResponseEntity<?> mensajeContrasena(@RequestParam("contrasena") String contrasena) throws IOException {
//		InputStream inputStream = new FileInputStream("src/main/resources/confContrasena.properties");
//
//		Properties properties = new Properties();
//		properties.load(inputStream);
		logger.info("Ejecutando mensajeContrasena");
		String longitud = configService.findAll().get(0).getVc_valor_propiedad();
		String mayusculas = configService.findAll().get(4).getVc_valor_propiedad();
		String minusculas = configService.findAll().get(3).getVc_valor_propiedad();
		String numeros = configService.findAll().get(2).getVc_valor_propiedad();
		String caracteres = configService.findAll().get(1).getVc_valor_propiedad();

		return ResponseEntity.ok(configuracionService.mensajeParametros(contrasena, Integer.parseInt(longitud),
				Integer.parseInt(mayusculas), Integer.parseInt(minusculas), Integer.parseInt(numeros),
				Integer.parseInt(caracteres)));
	}
	
	

}

