package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.citaVencidos.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.citaVencidos.dto.CitasVencidos;
@Service
public class CitasVencidosService {
	private static final Logger logger = Logger.getLogger(CitasVencidosService.class);
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public CitasVencidosService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<CitasVencidos> obtenerCitasVencidos(int tipoConsulta, String usuario){
		logger.info("Consultar Citas y Vencidos");
		try {
			String sql = "CALL SIB.SP_DATOS_CITAS_VIGENTES (?,?);";
			List<CitasVencidos> ls = jdbcTemplate.query(sql, new Object[] {tipoConsulta,usuario},
					new RowMapper<CitasVencidos>() {
						public CitasVencidos mapRow(ResultSet rs, final int rowNum) throws SQLException {
							CitasVencidos regCitasVendos = new CitasVencidos();
							regCitasVendos.setI_pk_asignacion(rs.getInt(1));
							regCitasVendos.setI_cliente(rs.getInt(2));
							regCitasVendos.setVc_nombre_cliente(rs.getString(3));
							regCitasVendos.setVc_campania(rs.getString(4));
							regCitasVendos.setVc_clasificacion(rs.getString(5));
							regCitasVendos.setVc_estatus(rs.getString(6));
							regCitasVendos.setVc_tipo_alerta(rs.getString(7));
							regCitasVendos.setVc_descripcion_cita(rs.getString(8));
						
							return regCitasVendos;
						}
					});
			
			return ls;
			
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
			return null;
		}
		
	}
	
}
