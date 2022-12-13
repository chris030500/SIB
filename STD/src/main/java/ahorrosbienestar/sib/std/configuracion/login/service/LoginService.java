package ahorrosbienestar.sib.std.configuracion.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public void spActivarSesion(String usuario) {
		jdbcTemplate.update("CALL SIB.SP_ACTIVAR_SESION (?)", usuario);
	}
	
	public void spDesactivarSesion(String usuario) {
		jdbcTemplate.update("CALL SIB.SP_DESACTIVAR_SESION (?)", usuario);
	}

}
