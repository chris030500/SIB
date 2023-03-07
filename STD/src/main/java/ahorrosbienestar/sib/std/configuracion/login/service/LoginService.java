package ahorrosbienestar.sib.std.configuracion.login.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	public void spActivarSesion(String usuario) {
		//System.out.println("Ejecutando spActivarSesion");
		logger.info("CALL SIB.SP_ACTIVAR_SESION(" + usuario + ")");
		jdbcTemplate.update("CALL SIB.SP_ACTIVAR_SESION (?)", usuario);
	}
	
	public void spDesactivarSesion(String usuario) {
		//System.out.println("Ejecutando spDesactivarSesion");
		logger.info("CALL SIB.SP_DESACTIVAR_SESION(" + usuario + ")");
		jdbcTemplate.update("CALL SIB.SP_DESACTIVAR_SESION (?)", usuario);
	}

}
