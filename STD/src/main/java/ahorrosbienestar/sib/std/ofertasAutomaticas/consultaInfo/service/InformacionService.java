package ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto.ProcesosBD;

@Service
public class InformacionService {
	private JdbcTemplate jdbcTemplate;
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	public InformacionService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ProcesosBD> obtConfBD() {
		String sql = "CALL SIB.SP_DATOS_TBLS_CSO (?,?);";
		
		@SuppressWarnings("deprecation")
		List<ProcesosBD> ls = jdbcTemplate.query(sql, new Object[] {1,0},
				new RowMapper<ProcesosBD>() {
					public ProcesosBD mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ProcesosBD dtosBD_OA = new ProcesosBD();
						dtosBD_OA.setPk_id_proceso(rs.getInt(1));
						dtosBD_OA.setNombreBase(rs.getString(2));
						dtosBD_OA.setDiaInicio(rs.getString(3));
						dtosBD_OA.setDiaFin(rs.getString(4));
						dtosBD_OA.setHoraInicio(rs.getString(5));
						dtosBD_OA.setHoraFin(rs.getString(6));
						dtosBD_OA.setRutaArchivo(rs.getString(7));
						
						return dtosBD_OA;
					}
				});
		
		return ls;
	}
	
}
