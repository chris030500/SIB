package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository;

import java.sql.CallableStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BasesEbrRepository {
	private static final Logger logger = Logger.getLogger(BasesEbrRepository.class);
	
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
    
    public int ejecutarExtraccionAuditoriaEbr(int anioAuditoria) {
        return jdbcTemplate.execute((ConnectionCallback<Integer>) con -> {
            try (CallableStatement cs = con.prepareCall("{CALL SIB.SP_PLD_EXTRACCION_AUDITORIA_EBR(?, ?)}")) {
                cs.setInt(1, anioAuditoria);
                cs.registerOutParameter(2, java.sql.Types.INTEGER);

                cs.execute();

                int flag = cs.getInt(2);
                logger.info("SP ejecutado correctamente, flag:" + flag);
                return flag;
            } catch (SQLException e) {
                logger.error("Error al ejecutar SP ejecutarExtraccionAuditoriaEbr: " + e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });
    }
}
