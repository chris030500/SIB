package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.configuracion;

import java.sql.Types;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto.ValidarEjecutarCronBase;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.service.InformacionService;

/***
 * 
 * @author juan.trejo
 *
 */
@Configuration
@EnableScheduling
public class GeneracionSMSConfiguracion {
	  private static final Logger logger = Logger.getLogger(GeneracionSMSConfiguracion.class);
	  
	  @Autowired
		private InformacionService informacionService;
	    
	    @Autowired
	    JdbcTemplate jdbcTemplate;
	    
		@Autowired
		private TaskScheduler tareaProcesoSMS;

		
		@Autowired
	    public GeneracionSMSConfiguracion(TaskScheduler tareaProcesoSMS) {
	        this.tareaProcesoSMS = tareaProcesoSMS;
	    }
		// proceso asignacion Gestion
		private int idProceso = 2;
		
		@Value("${my.job.cron}")
		private String expresionCron;
		
		private String error="";
		
		@PostConstruct
		public void init() {
		    	
		        try {
		        	actualizarExpresionCronProceso(idProceso);	
				} catch (Exception e) {
					logger.error(""+e.getMessage());
				}
		}
		
		public void actualizarExpresionCronProceso(Integer procesoGestionID) {
			try {
				
				logger.info("Procesos SMS ");
				List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCronGestion(idProceso);
				
				for(ValidarEjecutarCronBase valBase : validacionCron) {
					if(!valBase.getExpresionCron().equals(expresionCron)) {
						expresionCron = valBase.getExpresionCron();
						// Crea un nuevo trigger con la nueva expresión cron
			            CronTrigger trigger = new CronTrigger(expresionCron);
			            // Reemplaza el trigger existente con el nuevo trigger
			            tareaProcesoSMS.schedule(() -> procesarCron(procesoGestionID), trigger);
			            logger.info("Se actualizo cron SMS");
			        	
					}else {
						logger.info("No se actualizo Nomina");
			        	
			        }
					
				}
			} catch (Exception e) {
				logger.error("Error:"+e.getMessage());
			}
		}
		
		public void procesarCron(Integer procesoGestionID) {
			logger.info("Proceso de SMS");
			List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCronGestion(idProceso);
			
			try {
				LocalTime horaActual = LocalTime.now();

		        // Crear un formateador para mostrar solo horas y minutos
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		        // Formatear la hora actual y mostrarla
		        String horaYMinutos = horaActual.format(formatter);
				for(ValidarEjecutarCronBase valBase : validacionCron) {
					if(valBase.getI_ejecutar() == 1) {
						procesarSMS(procesoGestionID);
						logger.info("Se ejecuto proceso SMS:"+horaYMinutos);
					}else {
						logger.info("No es tiempo de ejecutar proceso SMS:"+horaYMinutos);
					}
				}			
				
			} catch (Exception e) {
				logger.error("Error:"+e.getMessage());
				logger.info("el proceso de SMS no se ejecuto");
			}
		}

	    
		public synchronized String procesarSMS(Integer idProceso){
	    	try {
	    		logger.info("Generar SMS");
	    		error = procesoGeneracionSMS();
	    		if(error == null) {
	    			return null;
	    		}else {
	    			return error;
	    		}
			} catch (Exception e) {
				logger.error("Error:"+e.getMessage());
				return "Error:"+e.getMessage();
			}
	    }
		
		public String procesoGeneracionSMS() {
		        // Crear un SimpleJdbcCall para invocar al procedimiento almacenado
		    	StoredProcedure procedure = new StoredProcedure(jdbcTemplate.getDataSource(), Constantes.PROCESO_SMS) {
		            {
		                declareParameter(new SqlOutParameter("error_message", Types.VARCHAR));
		                compile();
		            }
		        };

		        Map<String, Object> out = procedure.execute();


		        return (String) out.get("error_message");
		    }


}
