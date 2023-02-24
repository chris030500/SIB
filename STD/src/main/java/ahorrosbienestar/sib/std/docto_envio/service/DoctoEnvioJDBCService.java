package ahorrosbienestar.sib.std.docto_envio.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.core_bancario.dto.ParticipantesDto;
import ahorrosbienestar.sib.std.docto_envio.model.ArchivoSolicitud;
import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;

@Service
public class DoctoEnvioJDBCService {
	
	private JdbcTemplate jdbcTemplate;

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	public DoctoEnvioJDBCService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ArchivoSolicitud> obtenerArchivosSolicitud(int solicitud) {
		String sql = "CALL SIB.SP_ARCHIVOS_SOLICITUD (?);";
		logger.info("Ejecutando CALL SP_ARCHIVOS_SOLICITUD (" + solicitud + ")");
		List<ArchivoSolicitud> ls = jdbcTemplate.query(sql, new Object[] { solicitud },
				new RowMapper<ArchivoSolicitud>() {
					public ArchivoSolicitud mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ArchivoSolicitud docto = new ArchivoSolicitud();
						docto.setIdDoctoEnvio(rs.getInt(1));
						docto.setRutaDoct(rs.getString(2));
						docto.setNombreArchivo(rs.getString(3));
						return docto;
					}
				});

		return ls;
	}

}
