package ahorrosbienestar.sib.std.correoSIB.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.correoSIB.dtos.ContenidoCorreo;

@Service
public class EnvioCorreoContenidoService {
	private JdbcTemplate jdbcTemplate;
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	public EnvioCorreoContenidoService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//Armar contenido de correo.
	@SuppressWarnings("deprecation")
	public List<ContenidoCorreo> contenidoCorreo(String titulo, String errores) {
		try {
			logger.info("titulo"+titulo+" errores:"+errores);;
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
