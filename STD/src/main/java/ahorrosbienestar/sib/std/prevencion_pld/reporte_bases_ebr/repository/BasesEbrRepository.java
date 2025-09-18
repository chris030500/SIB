package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.repository;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, Integer> obtenerRangoFechas() {
        return jdbcTemplate.execute((ConnectionCallback<Map<String, Integer>>) con -> {
            try (CallableStatement cs = con.prepareCall("{CALL SIB.SP_PLD_RANGO_FECHAS_EBR(?, ?)}")) {
                cs.registerOutParameter(1, java.sql.Types.INTEGER);
                cs.registerOutParameter(2, java.sql.Types.INTEGER);

                cs.execute();

                Map<String, Integer> result = new HashMap<>();
                
                int min = cs.getInt(1);
                result.put("minYear", cs.wasNull() ? 0 : min);
                
                int max = cs.getInt(2);
                result.put("maxYear", cs.wasNull() ? 0 : max);
                
                // Fallback si no hay filas
                if (result.get("minYear") == 0 && result.get("maxYear") == 0) {
                    int currentYear = java.time.Year.now().getValue();
                    result.put("minYear", currentYear);
                    result.put("maxYear", currentYear);
                }

                logger.info("SP obtenerRangoFechas ejecutado correctamente: " + result);
                return result;
            } catch (SQLException e) {
                logger.error("Error al ejecutar SP obtenerRangoFechas: " + e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });
    }
    
    public int ejecutarExtraccionAuditoriaEbr(int idPeriodo, LocalDate fechaInicio, LocalDate fechaFin) {
        return jdbcTemplate.execute((ConnectionCallback<Integer>) con -> {
            try (CallableStatement cs = con.prepareCall("{CALL SIB.SP_PLD_EXTRACCION_AUDITORIA_EBR(?, ?, ?, ?)}")) {
                cs.setInt(1, idPeriodo);
                cs.setDate(2, Date.valueOf(fechaInicio));
                cs.setDate(3, Date.valueOf(fechaFin));
                cs.registerOutParameter(4, java.sql.Types.INTEGER);

                cs.execute();

                int flag = cs.getInt(4);
                logger.info("SP ejecutado correctamente, flag:" + flag);
                return flag;
            } catch (SQLException e) {
                logger.error("Error al ejecutar SP ejecutarExtraccionAuditoriaEbr: " + e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });
    }
}
