package ahorrosbienestar.sib.std.configuracion.login.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaService;

@Controller
public class LoginController {
	
	
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required = false) String error,
			@RequestParam(value="logout", required = false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
		System.out.println("Principal: " + principal);
		
		if(principal != null) {
			
			//flash.addFlashAttribute("info", "Ya ha inciado sesión anteriormente");
			flash.addFlashAttribute("info", "Ya ha inciado sesión anteriormente");
			System.out.println("Principal: " + principal);
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "Error en el inicio de sesión: "
					+ "Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo");
		}
		
		if(logout != null) {
			model.addAttribute("success", "Has cerrado sesión con éxito");
		}
		
		return "login";
	}

}
