package ahorrosbienestar.sib.std.configuracion.login.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ahorrosbienestar.sib.std.configuracion.login.service.LoginService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Controller
public class LoginController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LoginService loginService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required = false) String error,
			Model model, Principal principal, RedirectAttributes flash) {
		logger.info("Principal: " + principal);
		//System.out.println("Principal: " + principal);
		
		if(principal != null) {
			
			//flash.addFlashAttribute("info", "Ya ha inciado sesión anteriormente");
			//flash.addFlashAttribute("info", "Ya ha inciado sesión anteriormente");
//			System.out.println("Principal != null");
			logger.info("Principal != null");
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "Error en el inicio de sesión: "
					+ "Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo");
			logger.info("Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo");
		}
		
//		if(logout != null) {
//			model.addAttribute("success", "Has cerrado sesión con éxito");
//		}
		
		//loginService.spActivarSesion(principal.getName());
		return "login";
	}
	
	@GetMapping("/cerrar")
	public String cerrar(HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("Controller logout");
		logger.info("Ejecutando logout");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		loginService.spDesactivarSesion(auth.getName());
		
		if(auth != null) {
			//System.out.println("auth != null");
			logger.info("auth != null");
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}

}
