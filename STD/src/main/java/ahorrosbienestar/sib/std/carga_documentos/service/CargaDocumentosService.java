package ahorrosbienestar.sib.std.carga_documentos.service;

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
import ahorrosbienestar.sib.std.core_bancario.dto.ObtenerDoctoAsociadoProdDto;
import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;

@Service
public class CargaDocumentosService {

	private JdbcTemplate jdbcTemplate;

	//protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger =
	        LoggerFactory.getLogger(CargaDocumentosService.class);

	@Autowired
	public CargaDocumentosService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Declaramos que SP se va a ejecutar (SP_BUSCAR_DOCT_PROD) busca los documentos
	// relacionados con el producto
	public List<ObtenerDoctoAsociadoProdDto> obtenerDoctAsociadoProd(int producto) {
		String sql = "CALL SIB.SP_BUSCAR_DOCT_PROD (?);";

		logger.info("Ejecutando: " + sql + "(" + producto + ")");
		List<ObtenerDoctoAsociadoProdDto> ls = jdbcTemplate.query(sql, new Object[] { producto },
				new RowMapper<ObtenerDoctoAsociadoProdDto>() {
					public ObtenerDoctoAsociadoProdDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ObtenerDoctoAsociadoProdDto docto = new ObtenerDoctoAsociadoProdDto();
						docto.setIdRelProdDoct(rs.getInt(1));
						docto.setIdDocumento(rs.getInt(2));
						docto.setNombreDocumento(rs.getString(3));
						return docto;
					}
				});

		return ls;
	}

	// Declaramos que SP se va a ejecutar (SP_BUSCAR_DOCT_PROD) busca los documentos
	// relacionados con el producto
	public List<ObtenerDoctoAsociadoProdDto> obtenerDoctAsociadoProdClienteNuevo(int producto) {
		
		String sql = "CALL SIB.SP_BUSCAR_DOCT_PROD_NUEVO_USUARIO (?);";
		logger.info("Ejecutando: " + sql + "(" + producto + ")");
		List<ObtenerDoctoAsociadoProdDto> ls = jdbcTemplate.query(sql, new Object[] { producto },
				new RowMapper<ObtenerDoctoAsociadoProdDto>() {
					public ObtenerDoctoAsociadoProdDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ObtenerDoctoAsociadoProdDto docto = new ObtenerDoctoAsociadoProdDto();
						docto.setIdRelProdDoct(rs.getInt(1));
						docto.setIdDocumento(rs.getInt(2));
						docto.setNombreDocumento(rs.getString(3));
						return docto;
					}
				});
		logger.info("ls SIB.SP_BUSCAR_DOCT_PROD_NUEVO_USUARIO:" + + ls.get(0).getIdDocumento());
		//System.out.println("ls SIB.SP_BUSCAR_DOCT_PROD_NUEVO_USUARIO: " + ls.get(0).getIdDocumento());
		return ls;
	}

	// Declaramos que SP se va a ejecutar (SP_BUSCAR_DOCT_PROD) busca los documentos
	// relacionados con el producto
	public List<ObtenerDoctoAsociadoProdDto> obtenerIdDocumentoDoctAsociado(int documento) {
		String sql = "CALL SIB.SP_BUSCAR_DOCUMENTO (?);";

		logger.info("Ejecutando: " + sql + "(" + documento + ")");
		List<ObtenerDoctoAsociadoProdDto> ls = jdbcTemplate.query(sql, new Object[] { documento },
				new RowMapper<ObtenerDoctoAsociadoProdDto>() {
					public ObtenerDoctoAsociadoProdDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ObtenerDoctoAsociadoProdDto docto = new ObtenerDoctoAsociadoProdDto();
						docto.setIdDocumento(rs.getInt(1));
						return docto;
					}
				});

		return ls;
	}

	
}
