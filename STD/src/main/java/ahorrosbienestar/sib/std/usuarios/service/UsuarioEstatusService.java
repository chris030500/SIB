package ahorrosbienestar.sib.std.usuarios.service;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class UsuarioEstatusService {
//	@Scheduled(fixedDelayString = "${server.servlet.session.timeout}")
//    public void updateUserStatusToInactive(Principal principal) {
//        // Aquí puedes implementar la lógica para cambiar el estado del usuario a "inactivo"
//		logger.info("Principal UsuarioEstatusService:" + principal.getName());
//		
//		
//    }
	
	private JdbcTemplate jdbcTemplate;
	//protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger = Logger.getLogger(UsuarioEstatusService.class);

	@Autowired
	public UsuarioEstatusService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void cambiarEstatus() {
		logger.info("Ejecutando cambiarEstatus");
        this.jdbcTemplate.update(
                "UPDATE SIB.TBL_CAT_USUARIOS SET I_ESTATUS_SESION = 0 WHERE PK_ID_USUARIO  > 0");
    }
}
