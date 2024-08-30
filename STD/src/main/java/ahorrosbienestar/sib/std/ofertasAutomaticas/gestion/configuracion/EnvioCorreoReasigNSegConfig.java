package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.configuracion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.correoSIB.service.EnvioCorreoService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.service.SucursalesNotificarService;

/***
 * 
 * @author juan.trejo
 *
 */
@Configuration
@EnableScheduling
public class EnvioCorreoReasigNSegConfig {

    private static final Logger logger = Logger.getLogger(EnvioCorreoReasigNSegConfig.class);

    @Autowired SucursalesNotificarService sucursalesNotificarService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
	private TaskScheduler tareCorreoReasignacion;
    
    @Autowired
	private EnvioCorreoService envioCorreoService;
    
    @Autowired
	 private ConfigService configService;
    
	@Autowired
    public EnvioCorreoReasigNSegConfig(TaskScheduler tareCorreoReasignacion) {
        this.tareCorreoReasignacion = tareCorreoReasignacion;
    }
	
	// proceso asignacion Gestion
	private int idProceso = 3;
	
	private Configuracion expresionBD;
	
	private String error="";
	
	
	@PostConstruct
	public void init() {
	    	
	        try {
	        	expresionBD = configService.valorPropiedad(Constantes.CRON_ENVIO_CORREO_REASIGNACION);
	        	// Crea un nuevo trigger con la nueva expresión cron
	            CronTrigger trigger = new CronTrigger(expresionBD.getVc_valor_propiedad());
	            // Reemplaza el trigger existente con el nuevo trigger
	            tareCorreoReasignacion.schedule(() -> procesarCron(idProceso), trigger);
	            logger.info("Se actualizo Envio Correo Reasignacion");	
			} catch (Exception e) {
				logger.error(""+e.getMessage());
			}
	}
	
	public void procesarCron(Integer idProceso) {

		try {
			LocalTime horaActual = LocalTime.now();

	        // Crear un formateador para mostrar solo horas y minutos
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

	        // Formatear la hora actual y mostrarla
	        String horaYMinutos = horaActual.format(formatter);
	        
	        procesarReasignacion(idProceso);
	        logger.info("Se ejecuto proceso Envio Correo Reasignacion:"+horaYMinutos);
	        		
			
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			logger.info("el proceso de Envio Correo Reasignacion no se ejecuto");
		}
	}
	
	public synchronized void procesarReasignacion(Integer idProceso) throws DataAccessException {
		try {
			
			error = procesoReasignacion();
			
			if(error == null) {
				// Metodo envio de correo y adjunto
	    		envioCorreoService.preparCorreoAdjunto(Constantes.CORREO_REASIGNACION,Constantes.REPORTE_REASIGNACION,Constantes.PARAMETRO_ADJUNTO_ASIGNACION_REASIGMACIONES,Constantes.NOMBRE_REPORTE_REASIGNACION,Constantes.TIPO_BUSQUEDA_2);
	        	
			}else {
				logger.error("Error:"+error);
			}
    		
    	
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
		}
		
	}
	

    public String procesoReasignacion() {
              
    	Connection connection = null;
        CallableStatement callableStatement = null;
        String errorMessage = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            callableStatement = connection.prepareCall(Constantes.PROCESO_REASIGNACION);
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.execute();
            errorMessage = callableStatement.getString(1);
        } catch (SQLException e) {
            logger.error("Error al ejecutar el procedimiento almacenado: " + e.getMessage());
            errorMessage = "Error al ejecutar el procedimiento almacenado: " + e.getMessage();
        } finally {
            if (callableStatement != null) {
                try {
                    callableStatement.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar el CallableStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }

        return errorMessage;
        
        
    }
	
}
