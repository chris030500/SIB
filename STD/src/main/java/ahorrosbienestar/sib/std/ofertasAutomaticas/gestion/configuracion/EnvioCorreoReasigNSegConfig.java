package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.configuracion;

import java.sql.Types;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;

import ahorrosbienestar.sib.std.comunes.Constantes;
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
    JdbcTemplate jdbcTemplate;
    
    @Autowired
	private TaskScheduler tareCorreoReasignacion;
    
    @Autowired
	private EnvioCorreoService envioCorreoService;
    
	@Autowired
    public EnvioCorreoReasigNSegConfig(TaskScheduler tareCorreoReasignacion) {
        this.tareCorreoReasignacion = tareCorreoReasignacion;
    }
	
	// proceso asignacion Gestion
	private int idProceso = 3;
	
	@Value("${my.job.enviocorreo}")
	private String expresionCron;
	
	private String error="";
	
	
	@PostConstruct
	public void init() {
	    	
	        try {
	        	// Crea un nuevo trigger con la nueva expresión cron
	            CronTrigger trigger = new CronTrigger(expresionCron);
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
        // Crear un SimpleJdbcCall para invocar al procedimiento almacenado
    	StoredProcedure procedure = new StoredProcedure(jdbcTemplate.getDataSource(), Constantes.PROCESO_REASIGNACION) {
            {
                declareParameter(new SqlOutParameter("error_message", Types.VARCHAR));
                compile();
            }
        };

        Map<String, Object> out = procedure.execute();


        return (String) out.get("error_message");
    }
	
	
	
    
}
