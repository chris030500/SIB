package ahorrosbienestar.sib.std.ofertasAutomaticas.campaniasVIP.configuracion;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
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

/***
 * 
 * @author juan.trejo
 *
 */
@Configuration
@EnableScheduling
public class CampaniasVIPTareaConfiguracion {

	private static final Logger logger = Logger.getLogger(CampaniasVIPTareaConfiguracion.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	BasesService basesService;

	@Autowired
	private TaskScheduler taskSchedulerCampanias;

	@Autowired
	private InformacionService informacionService;

	@Autowired
	private IndexOfertasAutomaticasService indexOfertasAutomaticasService;
	@Autowired
	private EnvioCorreoService envioCorreoService;
	
	 @Autowired
	 private ConfigService configService;

	@Autowired
	public CampaniasVIPTareaConfiguracion(TaskScheduler taskSchedulerCampanias) {
		this.taskSchedulerCampanias = taskSchedulerCampanias;
	}


	private String expresionCronFidRen = "0 */5 8-20 1,30 * ?";
	private String expresionCronFidNom = "0 */5 8-20 1,30 * ?";
	private static final String ERROR_MESSAGE_PREFIX = "Error:";
	private static final String HORA_MINUTOS_FORMATO = "HH:mm";
	private static final String EJECUTA_SP_MENSAJE = "Ejecuta los SP";
	
	private Configuracion expresionBD;
	private String expresionCron;

	@PostConstruct
	public void init() {
		try {
			actualizarExpresionCronCampanias();
			actualizarExpresionCronFidNom();
			actualizarExpresionCronFidRen();
		} catch (Exception e) {
			logger.error("" + e.getMessage());
		}
	}

	public void actualizarExpresionCronCampanias() {
		try {
			expresionBD = configService.valorPropiedad(Constantes.CRON_INICIAL);
			logger.info("Procesar Campanias");
			List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(Constantes.BASE_CAMPANIAS_VIP);

			for (ValidarEjecutarCronBase valBase : validacionCron) {
				if (!valBase.getExpresionCron().equals(expresionBD.getVc_valor_propiedad())) {
					expresionCron = valBase.getExpresionCron();
					// Crea un nuevo trigger con la nueva expresión cron
					CronTrigger trigger = new CronTrigger(expresionCron);
					// Reemplaza el trigger existente con el nuevo trigger
					taskSchedulerCampanias.schedule(() -> procesarCron(), trigger);

					logger.info("Se actualizo cron Campanias");
				} else {
					logger.info("No se actualizo Campanias");
				}

			}
		} catch (Exception e) {
			logger.error(ERROR_MESSAGE_PREFIX + e.getMessage());

		}
	}

	public void procesarCron() {
		logger.info("Proceso de Campanias VIP");
		List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(Constantes.BASE_CAMPANIAS_VIP);

		try {
			LocalTime horaActual = LocalTime.now();

			// Crear un formateador para mostrar solo horas y minutos
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(HORA_MINUTOS_FORMATO);

			// Formatear la hora actual y mostrarla
			String horaYMinutos = horaActual.format(formatter);
			for (ValidarEjecutarCronBase valBase : validacionCron) {
				if (valBase.getI_ejecutar() == 1) {
					procesarCampaniasVIP();
					logger.info("Se ejecuto proceso Campañas VIP:" + horaYMinutos);
				} else {
					logger.info("No es tiempo de ejecutar proceso Campañas VIP:" + horaYMinutos);
				}
			}

		} catch (Exception e) {
			logger.error("el proceso de companias no se ejecuto" + e.getMessage());
		}
	}

	public void actualizarExpresionCronFidNom() {
		try {
			expresionBD = configService.valorPropiedad(Constantes.CRON_INICIAL);
			logger.info("Procesar Fidelizacion Nomina");
			List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(Constantes.BASE_FID_NOM);

			for (ValidarEjecutarCronBase valBase : validacionCron) {
				if (!valBase.getExpresionCron().equals(expresionBD.getVc_valor_propiedad())) {
					expresionCronFidNom = valBase.getExpresionCron();
					// Crea un nuevo trigger con la nueva expresión cron
					CronTrigger trigger = new CronTrigger(expresionCronFidNom);
					// Reemplaza el trigger existente con el nuevo trigger
					taskSchedulerCampanias.schedule(() -> procesarCronFidNom(), trigger);

					logger.info("Se actualizo cron Fidelizacion Nomina");
				} else {
					logger.info("No se actualizo Fidelizacion Nomina");
				}

			}
		} catch (Exception e) {
			logger.error(ERROR_MESSAGE_PREFIX + e.getMessage());

		}
	}

	public void procesarCronFidNom() {
		logger.info("Proceso de Fidelizacion Nomina");
		List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(Constantes.BASE_FID_NOM);

		try {
			LocalTime horaActual = LocalTime.now();

			// Crear un formateador para mostrar solo horas y minutos
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(HORA_MINUTOS_FORMATO);

			// Formatear la hora actual y mostrarla
			String horaYMinutos = horaActual.format(formatter);
			for (ValidarEjecutarCronBase valBase : validacionCron) {
				if (valBase.getI_ejecutar() == 1) {
					procesarCampaniasFidNom();
					logger.info("Se ejecuto proceso Fidelizacion Nomina:" + horaYMinutos);
				} else {
					logger.info("No es tiempo de ejecutar proceso Fidelizacion Nomina:" + horaYMinutos);
				}
			}

		} catch (Exception e) {
			logger.error("el proceso de Fidelizacion Nomina no se ejecuto" + e.getMessage());
		}
	}

	public void actualizarExpresionCronFidRen() {
		try {
			expresionBD = configService.valorPropiedad(Constantes.CRON_INICIAL);
			logger.info("Procesar Fidelizacion Renovado");
			List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(Constantes.BASE_FID_REN);

			for (ValidarEjecutarCronBase valBase : validacionCron) {
				if (!valBase.getExpresionCron().equals(expresionBD.getVc_valor_propiedad())) {
					expresionCronFidRen = valBase.getExpresionCron();
					// Crea un nuevo trigger con la nueva expresión cron
					CronTrigger trigger = new CronTrigger(expresionCronFidRen);
					// Reemplaza el trigger existente con el nuevo trigger
					taskSchedulerCampanias.schedule(() -> procesarCronFidRen(), trigger);

					logger.info("Se actualizo cron Fidelizacion Renovado");
				} else {
					logger.info("No se actualizo Fidelizacion Renvado");
				}

			}
		} catch (Exception e) {
			logger.error(ERROR_MESSAGE_PREFIX + e.getMessage());

		}
	}

	public void procesarCronFidRen() {
		logger.info("Se ejecuta proceso automatico de fidelizacion Renovado");
		logger.info("Proceso de Fidelizacion Renovado");
		List<ValidarEjecutarCronBase> validacionCron = informacionService.obtInfoCron(Constantes.BASE_FID_REN);

		try {
			
			LocalTime horaActual = LocalTime.now();

			// Crear un formateador para mostrar solo horas y minutos
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(HORA_MINUTOS_FORMATO);

			// Formatear la hora actual y mostrarla
			String horaYMinutos = horaActual.format(formatter);
			for (ValidarEjecutarCronBase valBase : validacionCron) {
				if (valBase.getI_ejecutar() == 1) {
					procesarCampaniasFidRen();
					logger.info("Se ejecuto proceso Fidelizacion Renovado:" + horaYMinutos);
				} else {
					logger.info("No es tiempo de ejecutar proceso Fidelizacion Renovado:" + horaYMinutos);
				}
			}

		} catch (Exception e) {
			logger.error("el proceso de Fidelizacion Renovado no se ejecuto" + e.getMessage());
		}
	}

	public synchronized void procesarCampaniasVIP() {
		try {
			logger.info(EJECUTA_SP_MENSAJE);
			jdbcTemplate.update("CALL SP_PROCESAR_INFORMACION_CAMPANIAS("+Constantes.BASE_CAMPANIAS_VIP+")");
			indexOfertasAutomaticasService.generarArchivoOA();
			logger.info("REGISTROS DE CAMPANIAS VIP");
			envioCorreoService.prepararCorreo(Constantes.CORREO_BASE_CAMPANIAS_VIP, "");
		} catch (Exception e) {
			logger.error(ERROR_MESSAGE_PREFIX + e.getMessage());
		}
	}

	public synchronized void procesarCampaniasFidNom() {
		try {
			logger.info(EJECUTA_SP_MENSAJE);
			jdbcTemplate.update("CALL SP_PROCESAR_INFORMACION_CAMPANIAS("+Constantes.BASE_FID_NOM+")");
			indexOfertasAutomaticasService.generarArchivoOA();
			logger.info("REGISTROS DE CAMPANIAS FIDELIZACION NOMINA");
			envioCorreoService.prepararCorreo(Constantes.CORREO_BASE_FID_NOM, "");
		} catch (Exception e) {
			logger.error(ERROR_MESSAGE_PREFIX + e.getMessage());
		}
	}

	public synchronized void procesarCampaniasFidRen() {
		try {
			logger.info(EJECUTA_SP_MENSAJE);
			jdbcTemplate.update("CALL SP_PROCESAR_INFORMACION_CAMPANIAS("+Constantes.BASE_FID_REN+")");
			indexOfertasAutomaticasService.generarArchivoOA();
			logger.info("REGISTROS DE CAMPANIAS FIDELIZACION RENOVADO");
			envioCorreoService.prepararCorreo(Constantes.CORREO_BASE_FID_RENO, "");
		} catch (Exception e) {
			logger.error(ERROR_MESSAGE_PREFIX + e.getMessage());
		}
	}

}
