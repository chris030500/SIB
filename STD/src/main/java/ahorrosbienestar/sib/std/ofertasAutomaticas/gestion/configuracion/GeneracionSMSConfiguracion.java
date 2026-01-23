package ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.configuracion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
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
	    private JdbcTemplate jdbcTemplate;
	    
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
						// Crea un nuevo trigger con la nueva expresiÃ³n cron
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
			Connection connection = null;
	        CallableStatement callableStatement = null;
	        String errorMessage = null;

	        try {
	            connection = jdbcTemplate.getDataSource().getConnection();
	            callableStatement = connection.prepareCall(Constantes.PROCESO_SMS);
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
	                    logger.error("Error al cerrar la conexiÃ³n: " + e.getMessage());
	                }
	            }
	        }

	        return errorMessage;
	    }


}

