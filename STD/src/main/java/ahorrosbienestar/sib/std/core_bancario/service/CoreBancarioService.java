package ahorrosbienestar.sib.std.core_bancario.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.core_bancario.dto.ObtenerDoctoAsociadoProdDto;
import ahorrosbienestar.sib.std.core_bancario.dto.ParticipantesDto;
import ahorrosbienestar.sib.std.core_bancario.dto.ProductosDto;
import ahorrosbienestar.sib.std.core_bancario.dto.ValidaSolicitudDto;
import ahorrosbienestar.sib.std.productos.model.Producto;

@Service
public class CoreBancarioService {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public CoreBancarioService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Declaramos que SP se va a ejecutar (validar la solicitud)
	public List<ValidaSolicitudDto> validaSolicitud(int solicitud) {
		String sql = "call microfin.SIB_SP_VALIDA_SOLICITUD (?);";
		List<ValidaSolicitudDto> ls = jdbcTemplate.query(sql, new Object[] { solicitud },
				new RowMapper<ValidaSolicitudDto>() {
					public ValidaSolicitudDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ValidaSolicitudDto cb = new ValidaSolicitudDto();
						cb.setResultado(rs.getInt(1));
						cb.setMensajeError(rs.getString(2));
						cb.setSolicitudCreditoID(rs.getInt(3));
						cb.setProductoCreditoID(rs.getInt(4));
						cb.setProducto(rs.getString(5));
						cb.setMontoSolicitado(rs.getString(6));
						cb.setMontoAutorizado(rs.getString(7));
						cb.setTasaFija(rs.getString(8));
						cb.setPlazo(rs.getString(9));
						cb.setEstatus(rs.getString(10));

						return cb;
					}
				});

		return ls;
	}

	// Declaramos que SP se va a ejecutar (traer participantes)
	public List<ParticipantesDto> traeParticipantes(int solicitud) {
	      String sql = "CALL microfin.SIB_SP_TRAE_PARTICIPANTES (?);";
	      List<ParticipantesDto> ls = jdbcTemplate.query(sql, new Object[] {solicitud},
	              new RowMapper<ParticipantesDto>() {
	                  public ParticipantesDto mapRow(ResultSet rs, final int rowNum) throws SQLException {
	                	  ParticipantesDto p = new ParticipantesDto();
	                	  p.setSolicitudCreditoID(rs.getInt(1));
	                	  p.setNombreCompleto(rs.getString(2));
	                	  p.setRolId(rs.getInt(3));
	                	  p.setRol(rs.getString(4));
	                	  p.setTelefonoCelular(rs.getString(5));
	                	  p.setCorreoElectronico(rs.getString(6));
	                      return p;
	                  }
	              });
	      
	      return ls;
	  }


	public List<ProductosDto> listaProductos(int solicitud) {
		String sql = "CALL microfin.SIB_SP_VALIDA_PRODUCTO (?);";
		 List <ProductosDto> lsp = jdbcTemplate.query(sql, new Object[] {solicitud},
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
	
	
	//Declaramos que SP se va a ejecutar (SP_BUSCAR_CAT_PRODUCTO)
	public int spBuscarCatProducto(int producto) {
		return jdbcTemplate.queryForObject("CALL SIB.SP_BUSCAR_CAT_PRODUCTO(" + producto + ")", Integer.class);
	}
	
	//Declaramos que SP se va a ejecutar (SP_BUSCAR_DOCT_PROD) busca los documentos relacionados con el producto
	public List<ObtenerDoctoAsociadoProdDto> obtenerDoctAsociadoProd(int producto) {
		String sql = "CALL SIB.SP_BUSCAR_DOCT_PROD (?);";
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

}
