package ahorrosbienestar.sib.std.usuarios.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.usuarios.dto.MicrofinUsuarioDTO;
import ahorrosbienestar.sib.std.usuarios.dto.PuestosMicrofinDTO;
@Service
public class MicrofinUsuarioService {

	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = Logger.getLogger(MicrofinUsuarioService.class);

	@Autowired
	public MicrofinUsuarioService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Obtener el usuario
	public List<MicrofinUsuarioDTO> obtenerUsuario(int empleadoId) {
		logger.info("Ejecutando obtenerUsuario: " + empleadoId);
		try {
				
			String sql = "CALL SIB.SP_INFORMACION_USUARIO(?);";
			@SuppressWarnings("deprecation")
			List<MicrofinUsuarioDTO> ls = jdbcTemplate.query(sql, new Object[] { empleadoId }, new RowMapper<MicrofinUsuarioDTO>() {
						public MicrofinUsuarioDTO mapRow(ResultSet rs, final int rowNum) throws SQLException {
							MicrofinUsuarioDTO e = new MicrofinUsuarioDTO();
							e.setClavePuestoID(rs.getString(1));
							e.setDescPuesto(rs.getString(2));
							e.setSucursalUsuario(rs.getInt(3));
							e.setPermisoCC(rs.getInt(4));
							e.setPermisoSuc(rs.getInt(5));
							e.setPermisoAdmin(rs.getInt(6));
							e.setPermisoConsulta(rs.getInt(7));
							return e;
						}
					});
			//logger.info(ls.get(0).getNombreCompleto());
			return ls;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			return Collections.emptyList(); // Devuelve una lista vacía en caso de error
		}
	}
	
	// Obtener el usuario
	public List<PuestosMicrofinDTO> obtenerPuestos() {
			
			try {
				String sql = "select ClavePuestoID,Descripcion from CE.TBL_MICROFIN_PUESTOS where Estatus = 'V'";
				@SuppressWarnings("deprecation")
				List<PuestosMicrofinDTO> ls = jdbcTemplate.query(sql, new Object[] {}, new RowMapper<PuestosMicrofinDTO>() {
							public PuestosMicrofinDTO mapRow(ResultSet rs, final int rowNum) throws SQLException {
								PuestosMicrofinDTO e = new PuestosMicrofinDTO();
								e.setVc_clavePuestoID(rs.getString(1));
								e.setVc_descripcionPuesto(rs.getString(2));
								return e;
							}
						});
				//logger.info(ls.get(0).getNombreCompleto());
				return ls;
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage());
				
			}
			return Collections.emptyList(); // Devuelve una lista vacía en caso de error
		}

}
