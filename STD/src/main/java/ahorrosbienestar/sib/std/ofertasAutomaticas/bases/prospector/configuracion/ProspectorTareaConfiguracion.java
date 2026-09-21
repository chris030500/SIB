package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.prospector.configuracion;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
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
	
	private static final Logger logger =
	        LoggerFactory.getLogger(ProspectorTareaConfiguracion.class);
	 
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
	 private ConfigService configService;
	
	 @Autowired
	 public ProspectorTareaConfiguracion(TaskScheduler taskSchedulerProspector) {
	       this.taskSchedulerProspector = taskSchedulerProspector;
	 }
	 
	 private Configuracion expresionBD;
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
				expresionBD = configService.valorPropiedad(Constantes.CRON_INICIAL);
				logger.info("Procesar Campanias");
				List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(Constantes.BASE_PROSPECTOR);
				
				for(ValidarEjecutarCronBase valBase : validacionCron) {
					if(!valBase.getExpresionCron().equals(expresionBD.getVc_valor_propiedad())) {
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
			logger.info("Proceso de Prospector.");
			List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(Constantes.BASE_PROSPECTOR);
			
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
	    		logger.info("Ejecuta SP_PROCESAR_INFORMACION_CAMPANIAS");
	        	jdbcTemplate.update("CALL SP_PROCESAR_INFORMACION_CAMPANIAS(+"+Constantes.BASE_PROSPECTOR+")");
	        	indexOfertasAutomaticasService.generarArchivoOA();
	        	logger.info("REGISTROS DE PROSPECTOR");
				envioCorreoService.prepararCorreo(Constantes.CORREO_BASE_PROSPECTOR,"");
			} catch (Exception e) {
				logger.error("Error:"+e.getMessage());
			}
	    } 
	 
}
