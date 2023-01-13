package ahorrosbienestar.sib.std.solicitudes.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SolicitudEnvioJDBCService {
	private JdbcTemplate jdbcTemplate;

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	public SolicitudEnvioJDBCService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Declaramos que SP se va a ejecutar (SP_BUSCAR_CAT_PRODUCTO)
	public int spValidacionArchivoSolicitud(int solicitud) {
		logger.info("Ejecutando CALL SIB.SP_VALIDACION_ARCHIVOS_SOLICITUD(" + solicitud + ")");
		return jdbcTemplate.queryForObject("CALL SIB.SP_VALIDACION_ARCHIVOS_SOLICITUD(" + solicitud + ")", Integer.class);
	}
}
