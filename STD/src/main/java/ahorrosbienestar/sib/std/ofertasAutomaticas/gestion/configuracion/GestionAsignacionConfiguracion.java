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
import ahorrosbienestar.sib.std.correosib.service.EnvioCorreoService;
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
	
	private String error="";
	
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
			List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCronGestion(idProceso);
			
			for(ValidarEjecutarCronBase valBase : validacionCron) {
				if(!valBase.getExpresionCron().equals(expresionCron)) {
					expresionCron = valBase.getExpresionCron();
					// Crea un nuevo trigger con la nueva expresión cron
		            CronTrigger trigger = new CronTrigger(expresionCron);
		            // Reemplaza el trigger existente con el nuevo trigger
		            tareProcesoAsignacion.schedule(() -> procesarCron(idProceso), trigger);
		            logger.info("Se actualizo cron Asignacion");
				}else {
					logger.info("No se actualizo Asignacion");
		        	
		        }
				
			}
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
		}
	}
	
	public void correoAsignacion(){
		try {
			// Metodo envio de correo y adjunto
        	envioCorreoService.preparCorreoAdjunto(Constantes.CORREO_ASIGNACION,Constantes.REPORTE_ASIGNACION,Constantes.PARAMETRO_ADJUNTO_ASIGNACION_REASIGMACIONES,Constantes.NOMBRE_REPORTE_ASIGNACION,Constantes.TIPO_BUSQUEDA_1);

		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_ERROR+e.getMessage());
		}
	}
	
	public void procesarCron(Integer idProceso) {
		logger.info("Proceso de Asignacion");
		List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCronGestion(idProceso);
		
		try {
			LocalTime horaActual = LocalTime.now();

	        // Crear un formateador para mostrar solo horas y minutos
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

	        // Formatear la hora actual y mostrarla
	        String horaYMinutos = horaActual.format(formatter);
			for(ValidarEjecutarCronBase valBase : validacionCron) {
				if(valBase.getI_ejecutar() == 1) {
					procesarAsignacion(idProceso, Constantes.PROCESO_AUTOMATICO);
					logger.info("Se ejecuto proceso Asignacion:"+horaYMinutos);
			            
				}else {
					logger.info("No es tiempo de ejecutar proceso Asignacion:"+horaYMinutos);
				}
			}			
			
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			logger.info("el proceso de Nomina no se ejecuto");
		}
	}


	public synchronized String procesarAsignacion(Integer idProceso, int tipoProceso) {
		try {
			
			logger.info("Ejecuta los SP Asignacion");
			error = procesoGestionAsignado();
			
			if(error == null || error.equals(Constantes.ENVIA_CORREO_ASIGNACION) || error.equals(Constantes.NO_ENVIA_CORREO_ASIGNACION)) {
				if(tipoProceso == Constantes.PROCESO_AUTOMATICO && error.equals(Constantes.ENVIA_CORREO_ASIGNACION) ) {
					envioCorreoService.preparCorreoAdjunto(Constantes.CORREO_ASIGNACION,Constantes.REPORTE_ASIGNACION,Constantes.PARAMETRO_ADJUNTO_ASIGNACION_REASIGMACIONES,Constantes.NOMBRE_REPORTE_ASIGNACION,Constantes.TIPO_BUSQUEDA_1);
				}
	        }else{
				logger.error("Error:"+error);
			}
			return error;
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return "Error:"+error+"-"+e.getMessage();
		}
		
		
	}
	
	 public String procesoGestionAsignado() {
		 Connection connection = null;
	        CallableStatement callableStatement = null;
	        String errorMessage = null;

	        try {
	            connection = jdbcTemplate.getDataSource().getConnection();
	            callableStatement = connection.prepareCall(Constantes.PROCESO_ASIGNACION);
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
