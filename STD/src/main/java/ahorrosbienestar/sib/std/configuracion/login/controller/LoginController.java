package ahorrosbienestar.sib.std.configuracion.login.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
import ahorrosbienestar.sib.std.index.model.Acceso;
import ahorrosbienestar.sib.std.index.service.AccesoService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Controller
public class LoginController extends HttpServlet {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private AccesoService accesoService;
	
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
		
		// Obteniendo  informacion del usuario con una consulta por por username
		Usuario usuario = usuarioService.findByNombre(auth.getName());
		int estatus = usuario.getPk_id_usuario();
		// Setenado valores al modelo Acceso
		Acceso acceso = new Acceso();
		acceso.setDt_fecha_registro(new Date());
		acceso.setI_cod_accion(3);
		acceso.setI_id_usuario(estatus);
		acceso.setVc_descripci("Salida del Sistema");
		LocalDateTime locaDate = LocalDateTime.now();
		acceso.setVc_hora_registro(locaDate.getHour()  + ":"+ locaDate.getMinute() +":"+locaDate.getSecond());
		acceso.setVc_host_remoto(request.getRemoteHost());
		acceso.setVc_ip_remoto(request.getRemoteAddr());
		acceso.setVc_nom_servidor(request.getServerName());
		acceso.setVc_usuario(auth.getName());
		//Guardanto los valores de acceso en la base de datos
		accesoService.save(acceso);
		System.out.println("Acceso registrado en bitacora correctamente");
		
		if(auth != null) {
			//System.out.println("auth != null");
			logger.info("auth != null");
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/";
	}
	
	
	

}
