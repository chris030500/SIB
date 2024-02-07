package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.configuracion;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;

import ahorrosbienestar.sib.std.correoSIB.service.EnvioCorreoService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model.SucursalesNotificar;
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
    		logger.info("Ejecuta los SP Asignacion");
        	jdbcTemplate.update("CALL SIB.SP_REASIG_X_FAL_SEGUIMIENTO()");
        	
        	List<SucursalesNotificar> listaSucursales = sucursalesNotificarService.consultaSucursal();
        	for(SucursalesNotificar suc : listaSucursales) {
        		int tipoCorreo = 10;
        	
            	// registrso de asignacion
            	envioCorreoService.prepararCorreo(tipoCorreo,String.valueOf(suc.getFk_id_sucursal()));
        	}
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
		}
		
	}
	
    
}
