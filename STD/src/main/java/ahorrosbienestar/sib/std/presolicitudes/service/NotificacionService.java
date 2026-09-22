package ahorrosbienestar.sib.std.presolicitudes.service;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;


import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.presolicitudes.model.EmpresaSIB;
import ahorrosbienestar.sib.std.presolicitudes.repository.EmpresaSIBRepository;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Service
public class NotificacionService {
    
    @Autowired
    private EmpresaSIBRepository empresaSIBRepository;

    @Autowired
	private ConfigService configService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TaskScheduler taskScheduler; // Scheduler de Spring

    private ScheduledFuture<?> scheduledFuture;

    @PostConstruct
    public void iniciarScheduler() {
        programarTarea(); // Llamar la programación de la tarea al inicio
    }

    public void programarTarea() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false); // Detener tarea previa si existe
        }

        // Obtener la expresión CRON desde la base de datos
        String cronExpresion = obtenerCronDesdeBD();


        Runnable tarea = this::enviarNotificaciones;

        // Programar la tarea con la nueva expresión CRON
        scheduledFuture = taskScheduler.schedule(tarea, new CronTrigger(cronExpresion));
    }

    private String obtenerCronDesdeBD() {
        Configuracion config = configService.valorPropiedad("CRON_CORREOS_VOBO");
        return config != null ? config.getVc_valor_propiedad() : "0 0 9 * * ?"; // Valor por defecto (9 AM)
    }

    public void enviarNotificaciones() {
        LocalDateTime ahora = LocalDateTime.now();
        List<EmpresaSIB> empresas = empresaSIBRepository.findAll();
        
        for (EmpresaSIB empresa : empresas) {
            if (debeEnviarNotificacion(empresa, ahora)) {
                emailService.enviarCorreo(empresa.getCorreoContacto(), 
                                          "Recordatorio", 
                                          "Este es tu recordatorio según tu frecuencia seleccionada.");
                empresa.setFecha(ahora);
                empresaSIBRepository.save(empresa);
            }
        }
    }

    private boolean debeEnviarNotificacion(EmpresaSIB empresa, LocalDateTime ahora) {
        if (empresa.getFecha() == null) {
            return true;
        }

        long diasDesdeUltima = ahora.toLocalDate().toEpochDay() - empresa.getFecha().toLocalDate().toEpochDay();

        switch (empresa.getFrecuencia()) {
            case 1:
                return diasDesdeUltima >= 7;
            case 2:
                return diasDesdeUltima >= 15;
            case 3:
                return diasDesdeUltima >= 30;
            default:
                return false;
        }
    }
}
