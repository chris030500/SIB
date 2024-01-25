package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.configuracion;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;

import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.correoSIB.service.EnvioCorreoService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto.ValidarEjecutarCronBase;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.service.InformacionService;

/***
 * 
 * @author juan.trejo
 *
 */
@Configuration
@EnableScheduling
public class GestionAsignacionConfiguracion {
	
    private static final Logger logger = Logger.getLogger(GestionAsignacionConfiguracion.class);

    @Autowired
	private InformacionService informacionService;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
	@Autowired
	private TaskScheduler tareProcesoAsignacion;

	@Autowired
	private EnvioCorreoService envioCorreoService;
	@Autowired
    public GestionAsignacionConfiguracion(TaskScheduler tareProcesoAsignacion) {
        this.tareProcesoAsignacion = tareProcesoAsignacion;
    }
	// proceso asignacion Gestion
	private int idProceso = 1;
	@Value("${my.job.cron}")
	private String expresionCron;
	
	@PostConstruct
	public void init() {
	    	
	        try {
	        	actualizarExpresionCronProceso(idProceso);	
			} catch (Exception e) {
				logger.error(""+e.getMessage());
			}
	}
	
	public void actualizarExpresionCronProceso(Integer idProceso) {
		try {
			
			logger.info("Procesos.");
			List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCronAsignacion(idProceso);
			
			for(ValidarEjecutarCronBase valBase : validacionCron) {
				if(!valBase.getExpresionCron().equals(expresionCron)) {
					expresionCron = valBase.getExpresionCron();
					// Crea un nuevo trigger con la nueva expresión cron
		            CronTrigger trigger = new CronTrigger(expresionCron);
		            // Reemplaza el trigger existente con el nuevo trigger
		            tareProcesoAsignacion.schedule(() -> procesarCron(idProceso), trigger);
		            logger.info("Se actualizo cron Nomina");
		        	
				}else {
					logger.info("No se actualizo Nomina");
		        	
		        }
				
			}
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
		}
	}
	
	public void procesarCron(Integer idProceso) {
		logger.info("Proceso de Nomina");
		List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCronAsignacion(idProceso);
		
		try {
			LocalTime horaActual = LocalTime.now();

	        // Crear un formateador para mostrar solo horas y minutos
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

	        // Formatear la hora actual y mostrarla
	        String horaYMinutos = horaActual.format(formatter);
			for(ValidarEjecutarCronBase valBase : validacionCron) {
				if(valBase.getI_ejecutar() == 1) {
					procesarAsignacion(idProceso);
					logger.info("Se ejecuto proceso Nomina:"+horaYMinutos);
				}else {
					logger.info("No es tiempo de ejecutar proceso Nomina:"+horaYMinutos);
				}
			}			
			
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			logger.info("el proceso de Nomina no se ejecuto");
		}
	}


	public synchronized void procesarAsignacion(Integer idProceso) {
		try {
    		logger.info("Ejecuta los SP nomina");
        	jdbcTemplate.update("CALL SIB.SP_ASIGNACIONXBASE_1_1()");
        	
        	int tipoCorreo = 9;
        	// registrso de asignacion
        	envioCorreoService.preparCorreoAdjunto(tipoCorreo);
        	
        	// int tipoCorreo = 5;
        	// "REGISTROS DE NOMINA";
			//envioCorreoService.prepararCorreo(tipoCorreo,"");
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
		}
		
	}

}
