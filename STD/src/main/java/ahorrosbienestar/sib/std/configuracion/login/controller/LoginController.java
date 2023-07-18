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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ahorrosbienestar.sib.std.configuracion.login.service.LoginService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Controller
public class LoginController extends HttpServlet {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LoginService loginService;
	
	//protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger = Logger.getLogger(LoginController.class);
	
//	{
//		BasicConfigurator.configure();
//	}
	
    @GetMapping("/login")
    public String login(@RequestParam(value="error", required = false) String error, HttpServletRequest request,
            Model model, Principal principal, RedirectAttributes flash, HttpSession session) {
        logger.info("Principal: " + principal);
        logger.debug("cargando index");
        String sessionStatus = (String) session.getAttribute("sessionStatus");
        System.out.println("sessionStatus: " + sessionStatus);
        //HttpSession misession= request.getSession();
        //System.out.println("missesion: " + misession.getAttribute(principal.toString()));
        //System.out.println("Principal: " + principal);
        if ("activo".equals(sessionStatus)) {
            // La sesión está activa
        	System.out.println("La sesión está activa");
        } else {
            // La sesión está inactiva
        	System.out.println("La sesión está inactiva");
        }
        
        if(principal != null) {
            
            logger.info("Principal != null");
            return "redirect:/";
        }
        //model.addAttribute("error", "Mensaje de error");

        
        if (request.getSession().getAttribute("error") != null) {
            model.addAttribute("error", request.getSession().getAttribute("error"));
            request.getSession().removeAttribute("error"); // Eliminar el mensaje de error anterior
        }
        
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
