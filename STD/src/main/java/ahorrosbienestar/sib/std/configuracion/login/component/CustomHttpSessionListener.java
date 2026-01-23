package ahorrosbienestar.sib.std.configuracion.login.component;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Component
public class CustomHttpSessionListener implements HttpSessionListener{
	
	@Autowired
	UsuarioService usuarioService;
	
	private static final Logger logger = Logger.getLogger(CustomHttpSessionListener.class);
	
	@Override
	 public void sessionCreated(HttpSessionEvent se) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		//logger.info("New session created  : " + se.getSession().getId());
		//logger.info("New session created  value names : " + se.getSession().getValueNames());
	     //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	     //logger.info("Auth: " + auth.getName());
	    // logger.info("Auth nombre: " + auth.getPrincipal().getClass().getName().get);
	 }

	 @Override
	 public void sessionDestroyed(HttpSessionEvent se) {
		 SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		 logger.info("Session destroyed.");
			logger.info("DestruirSesionUsuario");
			HttpSession session = se.getSession();
	        String username = (String) session.getAttribute("username");
	        if (usuarioService == null || username == null) {
	        	logger.warn("Session destroyed without usuarioService or username.");
	        	return;
	        }
	        // AquÃ­ puedes realizar la lÃ³gica para cambiar el estado del usuario en base de datos
	        // utilizando el nombre de usuario obtenido
	        logger.info("Session destroyed. User: " + username);
	        Usuario usuario = usuarioService.findByNombre(username);
	        if (usuario == null) {
	        	logger.warn("Session destroyed. Usuario no encontrado: " + username);
	        	return;
	        }
	        logger.info("Id del usuario: " + usuario.getPk_id_usuario());
	        usuarioService.destruirSesion(usuario.getPk_id_usuario());

	 }

}

