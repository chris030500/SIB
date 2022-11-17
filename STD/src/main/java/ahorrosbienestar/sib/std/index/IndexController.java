package ahorrosbienestar.sib.std.index;


import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ahorrosbienestar.sib.std.contrasena.dto.HistContrasenaDto;
import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaDtoService;
import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;


@Controller
public class IndexController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private HistContrasenaDtoService histContrasenaDtoService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@RequestMapping(value={"/","/inicio"}, method = RequestMethod.GET)
	public String index(Authentication authentication) {
		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
			
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		
		if(auth != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(auth.getName()));
		}
		String nombreUsuario = auth.getName();
		System.out.println("Nombre del usuario: " + nombreUsuario);
		int estatusFecha = histContrasenaDtoService.obtenerUsuarioAndFecha(nombreUsuario).get(0).valorFecha;
		System.out.println("C dto: " + estatusFecha);
		if(estatusFecha == 0) {
			System.out.println("Debe de cambiar la contraseña");
			return ("Cambio de contraseña");
		}
		System.out.println("Puede acceder al sistema");
		//System.out.println("DTO: "+histContrasenaDtoService.obtenerUsuarioAndFecha(nombreUsuario).get(0).valorFecha);
		//Usuario u = usuarioService.findByNombre(auth.getName());
		//System.out.println("Usuario u : " +u.getRol_idRol().);
		return "index";
	}

}
