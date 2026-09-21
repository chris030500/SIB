package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.nomina.configuracion;

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
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.service.BasesService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto.ValidarEjecutarCronBase;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.service.InformacionService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.index.service.IndexOfertasAutomaticasService;

/**
 * @author osvaldo.escamilla
 *
 */
@Configuration
@EnableScheduling
public class NominaTareaConfiguracion {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BasesService basesService;


    private static final Logger logger =
            LoggerFactory.getLogger(NominaTareaConfiguracion.class);

	@Autowired
	private TaskScheduler taskSchedulerNomina;
	@Autowired
	private InformacionService informacionService;
	@Autowired
	private IndexOfertasAutomaticasService indexOfertasAutomaticasService;
	
	@Autowired
	private EnvioCorreoService envioCorreoService;
	
	 @Autowired
	 private ConfigService configService;
	
	@Autowired
    public NominaTareaConfiguracion(TaskScheduler taskSchedulerNomina) {
        this.taskSchedulerNomina = taskSchedulerNomina;
    }
    
	
	private Configuracion expresionBD;
	private String expresionCron;

    @PostConstruct
    public void init() {
    	
        try {
        	actualizarExpresionCronNomina();	
		} catch (Exception e) {
			logger.error(""+e.getMessage());
		}
    }
    
	public void actualizarExpresionCronNomina() {
		try {
			expresionBD = configService.valorPropiedad(Constantes.CRON_INICIAL);
			logger.info("Proceso de Nomina.");
			List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(Constantes.BASE_NOMINA);
			
			for(ValidarEjecutarCronBase valBase : validacionCron) {
				if(!valBase.getExpresionCron().equals(expresionBD.getVc_valor_propiedad())) {
					expresionCron = valBase.getExpresionCron();
					// Crea un nuevo trigger con la nueva expresión cron
		            CronTrigger trigger = new CronTrigger(expresionCron);
		            // Reemplaza el trigger existente con el nuevo trigger
		            taskSchedulerNomina.schedule(() -> procesarCron(), trigger);
		            logger.info("Se actualizo cron Nomina");
		        	
				}else {
					logger.info("No se actualizo Nomina");
		        	
		        }
				
			}
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
		}
	}
	
	public void procesarCron() {
		logger.info("Proceso de Nomina");
		List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(Constantes.BASE_NOMINA);
		
		try {
			LocalTime horaActual = LocalTime.now();

	        // Crear un formateador para mostrar solo horas y minutos
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

	        // Formatear la hora actual y mostrarla
	        String horaYMinutos = horaActual.format(formatter);
			for(ValidarEjecutarCronBase valBase : validacionCron) {
				if(valBase.getI_ejecutar() == 1) {
					procesarNomina();
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

    
    public synchronized void procesarNomina() {
    	try {
    		logger.info("Ejecuta los SP nomina");
        	jdbcTemplate.update("CALL SIB.SP_PROCESAR_INFORMACION_CAMPANIAS("+Constantes.BASE_NOMINA+")");
        	indexOfertasAutomaticasService.generarArchivoOA();

        	// "REGISTROS DE NOMINA";
			envioCorreoService.prepararCorreo(Constantes.CORREO_BASE_NOMINA,"");
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
		}
    }
}
