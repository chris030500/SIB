package ahorrosbienestar.sib.std.configuracion.login.controller;

import java.security.Principal;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	// protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger = Logger.getLogger(LoginController.class);

//	{
//		BasicConfigurator.configure();
//	}

    @GetMapping("/login")
    public String login(HttpServletRequest request,
            Model model, Principal principal, HttpSession session) {
        logger.info("Principal: " + principal);
        logger.debug("cargando index");
       
        

        if(principal != null) {
            

            logger.info("Principal != null");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            System.out.println("Username login: " + username);
            return "redirect:/STD/inicio";
        }
        
        
     // Obtener el mensaje de error del flash
        String error = (String) request.getSession().getAttribute("error");
        if (error != null) {
            model.addAttribute("error", error);
            logger.info(error);
            
            // Eliminar el mensaje de error del flash
            request.getSession().removeAttribute("error");
        }
        

        
        //loginService.spActivarSesion(principal.getName());
        return "login";
    }

	@GetMapping("/cerrar")
	public String cerrar(HttpServletRequest request, HttpServletResponse response) {
		// System.out.println("Controller logout");
		logger.info("Ejecutando logout");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		loginService.spDesactivarSesion(auth.getName());

		if (auth != null) {
			// System.out.println("auth != null");
			logger.info("auth != null");
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}

}
