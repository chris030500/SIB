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
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto.ValidarEjecutarCronBase;

@Service
public class InformacionService {
	private JdbcTemplate jdbcTemplate;
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	public InformacionService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<ValidarEjecutarCronBase> obtInfoCron(Integer idBase) {
		String sql = "CALL SIB.SP_VALIDARCRON(?);";
		
		@SuppressWarnings("deprecation")
		List<ValidarEjecutarCronBase> ls = jdbcTemplate.query(sql, new Object[] {idBase},
				new RowMapper<ValidarEjecutarCronBase>() {
					public ValidarEjecutarCronBase mapRow(ResultSet rs, final int rowNum) throws SQLException {
						ValidarEjecutarCronBase dtosCron = new ValidarEjecutarCronBase();
						
						dtosCron.setI_ejecutar(rs.getInt(1));
						dtosCron.setExpresionCron(rs.getString(2));
						
						return dtosCron;
					}
				});
		
		return ls;
	}
	
}
