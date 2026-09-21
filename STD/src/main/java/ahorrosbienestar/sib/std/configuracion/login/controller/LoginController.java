package ahorrosbienestar.sib.std.configuracion.login.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger logger =
	        LoggerFactory.getLogger(LoginController.class);
	
//	{
//		BasicConfigurator.configure();
//	}
	
	

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required = false) String error,
	        HttpServletRequest request,
	        Model model,
	        Principal principal,
	        RedirectAttributes flash,
	        HttpSession session) {

	    logger.info("Principal: " + principal);
	    logger.debug("cargando login");

	    if ("usuarioBloqueado".equals(error)) {
	        model.addAttribute("error", "Usuario bloqueado. Favor de comunicarse con el administrador.");
	    }

	    if (request.getSession().getAttribute("error") != null) {
	        model.addAttribute("error", request.getSession().getAttribute("error"));
	        request.getSession().removeAttribute("error");
	    }

	    return "login";
	}
	
	@GetMapping("/cerrar")
	public String cerrar(HttpServletRequest request, HttpServletResponse response) {

	    logger.info("Ejecutando logout");

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	    if (auth != null && auth.isAuthenticated()) {

	        String username = auth.getName();

	        loginService.spDesactivarSesion(username);

	        Usuario usuario = usuarioService.findByNombre(username);

	        if (usuario != null) {
	            Acceso acceso = new Acceso();
	            acceso.setDt_fecha_registro(new Date());
	            acceso.setI_cod_accion(3);
	            acceso.setI_id_usuario(usuario.getPk_id_usuario());
	            acceso.setVc_descripci("Salida del Sistema");

	            LocalDateTime locaDate = LocalDateTime.now();
	            acceso.setVc_hora_registro(
	                locaDate.getHour() + ":" + locaDate.getMinute() + ":" + locaDate.getSecond()
	            );

	            acceso.setVc_host_remoto(request.getRemoteHost());
	            acceso.setVc_ip_remoto(request.getRemoteAddr());
	            acceso.setVc_nom_servidor(request.getServerName());
	            acceso.setVc_usuario(username);

	            accesoService.save(acceso);
	            logger.info("Acceso registrado en bitácora correctamente");
	        }

	        new SecurityContextLogoutHandler().logout(request, response, auth);

	        if (request.getSession(false) != null) {
	            request.getSession(false).invalidate();
	        }

	        SecurityContextHolder.clearContext();
	    }

	    return "redirect:/login?logout";
	}
	
	
	

}
