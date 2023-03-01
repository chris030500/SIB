
package ahorrosbienestar.sib.std.core_bancario.service;

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
import ahorrosbienestar.sib.std.core_bancario.dto.ProductosDto;
import ahorrosbienestar.sib.std.core_bancario.dto.ValidaSolicitudDto;
import ahorrosbienestar.sib.std.core_bancario.dto.ListaProductos;

@Service
public class CoreBancarioService {

	private JdbcTemplate jdbcTemplate;

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	public CoreBancarioService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Declaramos que SP se va a ejecutar (validar la solicitud)
	public List<ValidaSolicitudDto> validaSolicitud(int solicitud) {

		// String sql = "call microfin.SIB_SP_VALIDA_SOLICITUD (?);";
		String sql = "SELECT * FROM SIB.TBL_SIB_VALIDA_SOLICITUD WHERE SolicitudCreditoID = ?;";
		logger.info("Ejecutando call SIB_VALIDA_SOLICITUD (" + solicitud + ")");
		// System.out.println("Querry: " + sql + ", 1) " + solicitud );
		List<ValidaSolicitudDto> ls = jdbcTemplate.query(sql, new Object[] { solicitud },
				new RowMapper<ValidaSolicitudDto>() {
					public ValidaSolicitudDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ValidaSolicitudDto cb = new ValidaSolicitudDto();
//                        cb.setResultado(rs.getInt(1));
//                        cb.setMensajeError(rs.getString(2));
						cb.setSolicitudCreditoID(rs.getInt(1));
						cb.setProductoCreditoID(rs.getInt(2));
						cb.setProducto(rs.getString(3));
						cb.setMontoSolicitado(rs.getString(4));
						cb.setMontoAutorizado(rs.getString(5));
						cb.setTasaFija(rs.getString(6));
						cb.setPlazo(rs.getString(7));
						cb.setEstatus(rs.getString(8));
						cb.setProductoNomina(rs.getString(9));
						cb.setRegistroReca(rs.getString(10));
						cb.setTasaMoratoria(rs.getString(11));
						cb.setClienteId(rs.getString(12));
						return cb;
					}
				});
		logger.info("ls  SIB.SIB_VALIDA_SOLICITUD: " + ls.get(0).getSolicitudCreditoID());
		return ls;
	}

	// Declaramos que SP se va a ejecutar (traer participantes)
	public List<ParticipantesDto> traeParticipantes(int solicitud) {

		// String sql = "CALL microfin.SIB_SP_TRAE_PARTICIPANTES (?);";
		String sql = "SELECT * FROM SIB.TBL_SIB_TRAE_PARTICIPANTES WHERE SolicitudCreditoID = ?;";
		logger.info("Ejecutando SIB_TRAE_PARTICIPANTES (" + solicitud + ")");
		List<ParticipantesDto> ls = jdbcTemplate.query(sql, new Object[] { solicitud },
				new RowMapper<ParticipantesDto>() {
					public ParticipantesDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ParticipantesDto p = new ParticipantesDto();
						p.setSolicitudCreditoID(rs.getInt(1));
						p.setNombreCompleto(rs.getString(2));
						p.setRolId(rs.getInt(3));
						p.setRol(rs.getString(4));
						p.setTelefonoCelular(rs.getString(5));
						p.setCorreoElectronico(rs.getString(6));
						p.setDireccionCompleta(rs.getString(7));
						return p;
					}
				});
		logger.info("ls SIB.SIB_TRAE_PARTICIPANTES: " + ls);
		return ls;
	}

	public List<ProductosDto> listaProductos(int solicitud) {
		// String sql = "CALL SIB.SIB_SP_VALIDA_PRODUCTO (?);";
		String sql = "SELECT * FROM SIB.TBL_SIB_PRODUCTOSCREDITO WHERE ProducCreditoID = ?;";
		// String sql = "CALL microfin.SIB_SP_VALIDA_PRODUCTO (?);";
		logger.info("Ejecutando microfin.SIB_SP_VALIDA_PRODUCTO (" + solicitud + ")");
		List<ProductosDto> lsp = jdbcTemplate.query(sql, new Object[] { solicitud }, new RowMapper<ProductosDto>() {
			public ProductosDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
				ProductosDto ps = new ProductosDto();
				ps.setP_no_producto(rs.getInt(1));
				ps.setP_id_clasif(rs.getInt(2));
				ps.setNombre_producto(rs.getString(3));

				return ps;
			}
		});
		logger.info("lsp microfin.SIB_SP_VALIDA_PRODUCTO: " + lsp);
		return lsp;
	}

	public List<ProductosDto> listaProductos(int idProducto, int clasificacion) {
		String sql = "CALL SIB.SIB_SP_VALIDA_PRODUCTO (?,?);";
		logger.info("Ejecutando SIB.SIB_SP_VALIDA_PRODUCTO (" + idProducto + ")");
		List<ProductosDto> lsp = jdbcTemplate.query(sql, new Object[] { idProducto, clasificacion },
				new RowMapper<ProductosDto>() {
					public ProductosDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ProductosDto ps = new ProductosDto();
						ps.setP_no_producto(rs.getInt(1));
						ps.setP_id_clasif(rs.getInt(2));
						ps.setNombre_producto(rs.getString(3));

						return ps;
					}
				});
		return lsp;
	}

	// Declaramos que SP se va a ejecutar (SP_BUSCAR_CAT_PRODUCTO)
	public int spBuscarCatProducto(int producto) {
		logger.info("Ejecutando CALL SIB.SP_BUSCAR_CAT_PRODUCTO(" + producto + ")");
		return jdbcTemplate.queryForObject("CALL SIB.SP_BUSCAR_CAT_PRODUCTO(" + producto + ")", Integer.class);
	}

	public List<ListaProductos> obtProductos() {
		String sql = "select ProducCreditoID, Descripcion from SIB.TBL_SIB_PRODUCTOSCREDITO WHERE ESTATUS = ?;";
		String estatus = "A";
		List<ListaProductos> lsp = jdbcTemplate.query(sql, new Object[] { estatus }, new RowMapper<ListaProductos>() {
			public ListaProductos mapRow(ResultSet rs, final int rowNum) throws SQLException {
				ListaProductos ps = new ListaProductos();
				ps.setIdproducto(rs.getInt(1));
				ps.setNombreProducto(rs.getString(2));
				return ps;
			}
		});
		logger.info("consulta productos colocacion: " + lsp);
		return lsp;
	}

	public List<ListaProductos> obtProductosCaptacion() {
		String sql = "select tipoCuentaID, Descripcion from SIB.TBL_SIB_TIPOSCUENTAS WHERE ESTATUS = ?;";
		String estatus = "A";
		List<ListaProductos> lsp = jdbcTemplate.query(sql, new Object[] { estatus }, new RowMapper<ListaProductos>() {
			public ListaProductos mapRow(ResultSet rs, final int rowNum) throws SQLException {
				ListaProductos ps = new ListaProductos();
				ps.setIdproducto(rs.getInt(1));
				ps.setNombreProducto(rs.getString(2));

				return ps;
			}
		});
		logger.info("consulta productos captacion: " + lsp);
		return lsp;
	}

}
