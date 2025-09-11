package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VoboBasesEbrRepository {
	private static final Logger logger = Logger.getLogger(VoboBasesEbrRepository.class);
	
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
