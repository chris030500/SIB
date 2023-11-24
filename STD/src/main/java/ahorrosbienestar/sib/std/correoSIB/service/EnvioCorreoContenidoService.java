package ahorrosbienestar.sib.std.correoSIB.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.correoSIB.dtos.ContenidoCorreo;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.configuracion.FidelidadTareaConfiguracion;

@Service
public class EnvioCorreoContenidoService {
	private JdbcTemplate jdbcTemplate;
	
	
	private static final Logger logger = Logger.getLogger(EnvioCorreoContenidoService.class);
	
	@Autowired
	public EnvioCorreoContenidoService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//Armar contenido de correo.
	@SuppressWarnings("deprecation")
	public List<ContenidoCorreo> contenidoCorreo(int titulo, String errores) {
		try {
			logger.info("titulo"+titulo+" errores:"+errores);
			String sql = "CALL SIB.SP_MNS_CORREO(?,?);";
			List<ContenidoCorreo> ls = jdbcTemplate.query(sql, new Object[] {titulo,errores},
					new RowMapper<ContenidoCorreo>() {
						public ContenidoCorreo mapRow(ResultSet rs, final int rowNum) throws SQLException {
							ContenidoCorreo contenido = new ContenidoCorreo();
							contenido.setVc_asunto(rs.getString(1));
							contenido.setVc_html(rs.getString(2));
							contenido.setVc_destinatarios(rs.getString(3));
				
							return contenido;
						}
					});
			return ls;
		} catch (Exception e) {
			logger.error("error"+e.getMessage());
			return null;
		}
	}
}
