package ahorrosbienestar.sib.std.docto_unificado.obtener_coordenadas.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.configuracion.login.controller.LoginController;
import ahorrosbienestar.sib.std.docto_envio.model.ArchivoSolicitud;
import ahorrosbienestar.sib.std.docto_unificado.obtener_coordenadas.model.ObtenerCoordenadas;

@Service
public class ObtenerCoordenadasJDBCService {
	
	private JdbcTemplate jdbcTemplate;

	//protected final Log logger = LogFactory.getLog(this.getClass());
	
	private static final Logger logger =
	        LoggerFactory.getLogger(ObtenerCoordenadasJDBCService.class);
	
	@Autowired
	public ObtenerCoordenadasJDBCService (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ObtenerCoordenadas> obtenerInfoCoordenadas(int solicitudEnvio, int operacion) {
		String sql = "CALL SIB.SP_OBTENER_INFO_SOLIC_COORD_DOCTO (?,?);";
		//System.out.println("Ejecutando CALL SIB.SP_OBTENER_INFO_SOLIC_COORD_DOCTO (" + solicitudEnvio + "," + operacion +")");
		logger.info("Ejecutando CALL SIB.SP_OBTENER_INFO_SOLIC_COORD_DOCTO (" + solicitudEnvio + "," + operacion +")");
		List<ObtenerCoordenadas> ls = jdbcTemplate.query(sql, new Object[] { solicitudEnvio, operacion },
				new RowMapper<ObtenerCoordenadas>() {
					public ObtenerCoordenadas mapRow(ResultSet rs, final int rowNum) throws SQLException {
						
						if(operacion == 0 ) {
							ObtenerCoordenadas oc = new ObtenerCoordenadas();
							oc.setPk_id_solicitud(rs.getInt(1));
							oc.setPk_id_docto_envio(rs.getInt(2));
							oc.setVc_ruta_docto(rs.getString(3));
							oc.setNo_hoja(rs.getInt(4));
							return oc;
						} else if (operacion == 1) {
							ObtenerCoordenadas oc = new ObtenerCoordenadas();
							oc.setPk_id_solicitud(rs.getInt(1));
							oc.setPk_id_docto_envio(rs.getInt(2));
							oc.setVc_ruta_docto(rs.getString(3));
							oc.setTotal_hojas(rs.getInt(4));
							oc.setId_firma_docto(rs.getInt(5));
							oc.setCoordenada_x1(rs.getFloat(6));
							oc.setCoordenada_x2(rs.getFloat(7));
							oc.setCoordenada_y1(rs.getFloat(8));
							oc.setCoordenada_y2(rs.getFloat(9));
							oc.setNo_hoja(rs.getInt(10));
							oc.setCoordenada_xCentral(rs.getFloat(11));
							oc.setCoordenada_yCentral(rs.getFloat(12));
							System.out.println("Pk_id_solicitud: " + oc.getPk_id_solicitud());
							return oc;
						}
						return null;
					}
				});

		return ls;
	}
	
	public List<ObtenerCoordenadas> obtenerDocumentosSolicitud(int solicitudEnvio) {
		String sql = "CALL SIB.SP_OBTENER_DOCUMENTOS_SOLICITUD (?);";
		//System.out.println("Ejecutando CALL SIB.SP_OBTENER_INFO_SOLIC_COORD_DOCTO (" + solicitudEnvio + "," + operacion +")");
		logger.info("Ejecutando CALL SIB.SP_OBTENER_DOCUMENTOS_SOLICITUD (" + solicitudEnvio + ")");
		List<ObtenerCoordenadas> ls = jdbcTemplate.query(sql, new Object[] { solicitudEnvio},
				new RowMapper<ObtenerCoordenadas>() {
					public ObtenerCoordenadas mapRow(ResultSet rs, final int rowNum) throws SQLException {
						
							ObtenerCoordenadas oc = new ObtenerCoordenadas();
							oc.setPk_id_solicitud(rs.getInt(1));
							oc.setPk_id_docto_envio(rs.getInt(2));
							oc.setVc_ruta_docto(rs.getString(3));
							oc.setNo_hoja(rs.getInt(4));
							return oc;
					}
				});

		return ls;
	}

}
