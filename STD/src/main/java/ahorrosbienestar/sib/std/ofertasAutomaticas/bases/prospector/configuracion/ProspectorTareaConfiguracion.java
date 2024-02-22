package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.prospector.configuracion;

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

import ahorrosbienestar.sib.std.correoSIB.service.EnvioCorreoService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto.ValidarEjecutarCronBase;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.service.InformacionService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.index.service.IndexOfertasAutomaticasService;

/***
 * 
 * @author juan.trejo
 *
 */
@Configuration
@EnableScheduling
public class ProspectorTareaConfiguracion {
	
	 private static final Logger logger = Logger.getLogger(ProspectorTareaConfiguracion.class);
	 
	 @Autowired
	 JdbcTemplate jdbcTemplate;
	 
	 @Autowired
	 private TaskScheduler taskSchedulerProspector;
	 
	 @Autowired
	 private InformacionService informacionService;
	 
	 @Autowired
	 private IndexOfertasAutomaticasService indexOfertasAutomaticasService;
	 
	 @Autowired
	 private EnvioCorreoService envioCorreoService;
	
	 @Autowired
	 public ProspectorTareaConfiguracion(TaskScheduler taskSchedulerProspector) {
	       this.taskSchedulerProspector = taskSchedulerProspector;
	 }
	 
	 private int idBase = 4;
	 @Value("${my.job.cron}")
	 private String expresionCron;
	 
	 @PostConstruct
	    public void init() {
	        try {
	        	actualizarExpresionCronProspector();
			} catch (Exception e) {
				logger.error(""+e.getMessage());
			}
	 }
	 
	 public void actualizarExpresionCronProspector() {
			try {
				
				logger.info("Procesar Campanias");
				List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(idBase);
				
				for(ValidarEjecutarCronBase valBase : validacionCron) {
					if(!valBase.getExpresionCron().equals(expresionCron)) {
						expresionCron = valBase.getExpresionCron();
						// Crea un nuevo trigger con la nueva expresión cron
			            CronTrigger trigger = new CronTrigger(expresionCron);
			            // Reemplaza el trigger existente con el nuevo trigger
			            taskSchedulerProspector.schedule(() -> procesarCron(), trigger);
			        
			            logger.info("Se actualizo cron Campanias");
					}else {
						logger.info("No se actualizo Campanias");
			        }
					
				}
			} catch (Exception e) {
				logger.error("Error:"+e.getMessage());
				
			}
	   }
	 
		public void procesarCron() {
			logger.info("Proceso de Campanias VIP");
			List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(idBase);
			
			try {
				LocalTime horaActual = LocalTime.now();

		        // Crear un formateador para mostrar solo horas y minutos
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		        // Formatear la hora actual y mostrarla
		        String horaYMinutos = horaActual.format(formatter);
				for(ValidarEjecutarCronBase valBase : validacionCron) {
					if(valBase.getI_ejecutar() == 1) {
						procesarProspector();
						logger.info("Se ejecuto proceso Campañas VIP:"+horaYMinutos);
					}else {
						logger.info("No es tiempo de ejecutar proceso Campañas VIP:"+horaYMinutos);
					}
				}			
				
			} catch (Exception e) {
				logger.error("el proceso de companias no se ejecuto"+e.getMessage());
			}
		}

	    public synchronized void procesarProspector() {
	    	try {
	    		logger.info("Ejecuta los SP");
	        	jdbcTemplate.update("CALL SP_PROCESAR_INFORMACION_CAMPANIAS(4)");
	        	indexOfertasAutomaticasService.generarArchivoOA();
	        	int tipoCorreo = 11;
	        	logger.info("REGISTROS DE PROSPECTOR");
				envioCorreoService.prepararCorreo(tipoCorreo,"");
			} catch (Exception e) {
				logger.error("Error:"+e.getMessage());
			}
	    } 
	 
}
