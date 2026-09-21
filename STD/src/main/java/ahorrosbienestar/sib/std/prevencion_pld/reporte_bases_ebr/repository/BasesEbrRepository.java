package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BasesEbrRepository {
	private static final Logger logger =
	        LoggerFactory.getLogger(BasesEbrRepository.class);
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void ejecutarVoBo(int idUsuario) {
        String sql = "CALL SIB.SP_PLD_VISTO_BUENO_BASES_EBR(?)";
        try {
            jdbcTemplate.update(sql, idUsuario);
            logger.info("SP ejecutado correctamente para usuario: " + idUsuario);
        } catch (Exception e) {
        	logger.error("Error al ejecutar SP: " + e.getMessage());
            throw e;
        }
    }
}
