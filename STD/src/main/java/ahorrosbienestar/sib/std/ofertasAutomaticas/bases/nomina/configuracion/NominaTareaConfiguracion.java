package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.nomina.configuracion;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRules;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;

import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.model.Bases;
import ahorrosbienestar.sib.std.ofertasAutomaticas.bases.service.BasesService;

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


    private static final Logger logger = Logger.getLogger(NominaTareaConfiguracion.class);

    @Autowired
    private TaskScheduler taskScheduler;

    private ScheduledFuture<?> cronTaskLiquidadoConvenio;
    
    private final Object cronTaskLock = new Object(); // Para sincronización

    @PostConstruct
    public void init() {
        String expresionCronInicial = calcularExpresionCron();
        cronTaskLiquidadoConvenio = taskScheduler.schedule(this::ejecutarNomina, new CronTrigger(expresionCronInicial));
        //cronTask = taskScheduler.schedule(() -> ejecutarNominaLiquidadosConvenio(), new CronTrigger(expresionCronInicial));
    }

    private String calcularExpresionCron() {
    	System.out.println("calcularExpresionCron:");
        Bases datos = basesService.findById(1).get();
        int intervalo = Integer.parseInt(datos.getVc_periodo_ejecucion_proceso());
        int horaInicio = LocalTime.parse(datos.getVc_hora_inicio_proceso(), DateTimeFormatter.ofPattern("HH:mm"))
                .getHour();
        int horaFin = LocalTime.parse(datos.getVc_hora_fin_proceso(), DateTimeFormatter.ofPattern("HH:mm")).getHour();
        int diaInicio = Integer.parseInt(datos.getVc_dia_inicio_proceso());

        return "0 */" + intervalo + " " + horaInicio + "-" + horaFin + " " + diaInicio + " * ?";
    }

    public void ejecutarNomina() {
        // Tu lógica de ejecución aquí
        Bases datos = basesService.findById(1).get();

        String hInicio = datos.getVc_hora_inicio_proceso();
        String hFin = datos.getVc_hora_fin_proceso();
        String dInicio = datos.getVc_dia_inicio_proceso();

        LocalTime horaInicioL = LocalTime.parse(hInicio, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime horaFinL = LocalTime.parse(hFin, DateTimeFormatter.ofPattern("HH:mm"));

        int horaInicio = horaInicioL.getHour();
        int horaFin = horaFinL.getHour();
        int diaInicio = Integer.parseInt(dInicio);
        
        ZoneId zonaHoraria = ZoneId.of("America/Mexico_City");
        // Obtener la hora actual con minutos
        LocalTime horaActualConMinutos = LocalTime.now(zonaHoraria);

        // Formatear la hora actual con minutos en "HH:mm"
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
        
        //String horaFormateada = horaActualConMinutos.format(formato);

     // Obtener la hora actual en la zona horaria especificada
        ZonedDateTime horaActualZonaHoraria = ZonedDateTime.now(zonaHoraria);
     // Verificar si la zona horaria actual está en horario de verano
        ZoneRules zoneRules = zonaHoraria.getRules();
        boolean esHorarioVerano = zoneRules.isDaylightSavings(horaActualZonaHoraria.toInstant());

        // Si es horario de verano, restar una hora a la hora actual
        if (esHorarioVerano) {
            horaActualZonaHoraria = horaActualZonaHoraria.minusHours(1);
        }
        
        //System.out.println("horaActualZonaHoraria: " + horaActualZonaHoraria);
        
        String horaFormateada = horaActualZonaHoraria.format(formato);
        
        System.out.println("----------------------------");
        System.out.println("ejecutarNominaLiquidadosConvenio");
        System.out.println("horaInicio: " + horaInicio);
        System.out.println("horaFin: " + horaFin);
        System.out.println("diaInicio: " + diaInicio);
        System.out.println("horaEjecutada: " + horaFormateada);
        System.out.println("intervalo: " + datos.getVc_periodo_ejecucion_proceso());
        System.out.println("-----------------------------");

        if (basesService.esHorarioValido(horaInicio, horaFin) && basesService.esDiaValido(diaInicio)) {
        	procesarNomina();
            //jdbcTemplate.update("CALL SIB.SP_MIGRAR_BASES_NOMINA_LIQUIDADOS_CONVENIO(30)");
            //jdbcTemplate.update("CALL SIB.SP_MIGRAR_BASES_NOMINA_LIQUIDADOS_SN_CONVENIO(30)");
            //jdbcTemplate.update("CALL SIB.SP_MIGRAR_BASES_NOMINA_NLIQUIDADOS_CONVENIO(30)");
            //jdbcTemplate.update("CALL SIB.SP_MIGRAR_BASES_NOMINA_NLIQUIDADOS_SN_CONVENIO(30)");
//            lastExecutionHadData = true; // Actualizar según los resultados del SP
//            lastExecutionTime = System.currentTimeMillis();
        }

    }
    
    public synchronized void procesarNomina() {
    	try {
    		System.out.println("Ejecuta los SP");
        	jdbcTemplate.update("CALL SIB.SP_PROCESAR_INFORMACION_CAMPANIAS(1)");
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
    }



    // Método para actualizar la tarea programada con una nueva expresión 
    
    //@Scheduled(fixedDelay = 60000) // Ejecutar cada 60 segundos (ajusta el valor según tus necesidades)
    @Scheduled(cron = "0 0 8,20 * * *") // Ejecutar diario a las 8:00 A.M y 8:00 P.M
    public void actualizarExpresionCron() {
    	System.out.println("actualizarExpresionCron:");
    	synchronized (cronTaskLock) {
            if (cronTaskLiquidadoConvenio != null) {
                cronTaskLiquidadoConvenio.cancel(true);
            }

            String nuevaExpresionCron = calcularExpresionCron();
            cronTaskLiquidadoConvenio = taskScheduler.schedule(() -> ejecutarNomina(), new CronTrigger(nuevaExpresionCron));
        }
    }
}
