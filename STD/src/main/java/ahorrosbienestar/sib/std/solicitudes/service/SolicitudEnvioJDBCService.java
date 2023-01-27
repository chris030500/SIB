package ahorrosbienestar.sib.std.solicitudes.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.core_bancario.dto.ValidaSolicitudDto;
import ahorrosbienestar.sib.std.solicitudes.dto.ValidarSolicitudCoreDto;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;

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
		return jdbcTemplate.queryForObject("CALL SIB.SP_VALIDACION_ARCHIVOS_SOLICITUD(" + solicitud + ")",
				Integer.class);
	}

	// Obtenemos una lista para determinar si la solicitud
	public List<ValidarSolicitudCoreDto> spValidarSolicitudCore(int idSolicitudCore) {
		String sql = "call SIB.SP_VALIDAR_SOLICITUD_CORE (?);";
		logger.info("Ejecutando call SIB.SP_VALIDAR_SOLICITUD_CORE (" + idSolicitudCore + ")");
		List<ValidarSolicitudCoreDto> ls = jdbcTemplate.query(sql, new Object[] { idSolicitudCore },
				new RowMapper<ValidarSolicitudCoreDto>() {
					public ValidarSolicitudCoreDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ValidarSolicitudCoreDto se = new ValidarSolicitudCoreDto();
						se.setEstatus(rs.getInt(1));
						return se;
					}
				});

		return ls;
	}

	// Declaramos que SP se va a ejecutar (SP_BUSCAR_CAT_PRODUCTO)
	public int spObtenerIdentificadorRuta(int solicitudCore) {
		logger.info("Ejecutando CALL SIB.SP_OBTENER_IDENTIFICADOR_RUTA(" + solicitudCore + ")");
		return jdbcTemplate.queryForObject("CALL SIB.SP_OBTENER_IDENTIFICADOR_RUTA(" + solicitudCore + ")",
				Integer.class);
	}
}
