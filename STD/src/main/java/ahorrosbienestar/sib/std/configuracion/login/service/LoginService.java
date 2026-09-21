package ahorrosbienestar.sib.std.configuracion.login.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.configuracion.login.controller.LoginController;

@Service
public class LoginService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger =
	        LoggerFactory.getLogger(LoginService.class);

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
	
	public Integer obtenerSesionActiva(String usuario) {
	    try {
	        return jdbcTemplate.queryForObject(
	            "SELECT IFNULL(I_ESTATUS_SESION, 0) " +
	            "FROM SIB.TBL_CAT_USUARIOS " +
	            "WHERE UPPER(TRIM(VC_NOMBRE_USUARIO)) = UPPER(TRIM(?)) " +
	            "LIMIT 1",
	            Integer.class,
	            usuario
	        );
	    } catch (Exception e) {
	        logger.error("Error al obtener sesión activa del usuario: " + usuario, e);
	        return 0;
	    }
	}

}
