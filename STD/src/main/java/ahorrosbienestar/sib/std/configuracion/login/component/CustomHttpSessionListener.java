package ahorrosbienestar.sib.std.configuracion.login.component;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Component
public class CustomHttpSessionListener implements HttpSessionListener{
	
	@Autowired
	UsuarioService usuarioService;
	
	private static final Logger logger = Logger.getLogger(CustomHttpSessionListener.class);
	
	@Override
	 public void sessionCreated(HttpSessionEvent se) {
		//logger.info("New session created  : " + se.getSession().getId());
		//logger.info("New session created  value names : " + se.getSession().getValueNames());
	     //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	     //logger.info("Auth: " + auth.getName());
	    // logger.info("Auth nombre: " + auth.getPrincipal().getClass().getName().get);
	 }

	 @Override
	 public void sessionDestroyed(HttpSessionEvent se) {
		 logger.info("Session destroyed.");
			logger.info("DestruirSesionUsuario");
			HttpSession session = se.getSession();
	        String username = (String) session.getAttribute("username");
	        // Aquí puedes realizar la lógica para cambiar el estado del usuario en base de datos
	        // utilizando el nombre de usuario obtenido
	        logger.info("Session destroyed. User: " + username);
	        Usuario usuario = usuarioService.findByNombre(username);
	        logger.info("Id del usuario: " + usuario.getPk_id_usuario());
	        usuarioService.destruirSesion(usuario.getPk_id_usuario());

	 }

}
