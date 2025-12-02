package ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.service;

import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.model.MigracionesP;
import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.repository.MigracionesPRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MigracionesPService {
	
	private static final Logger logger = Logger.getLogger(MigracionesPService.class);

	 @Autowired
	    private MigracionesPRepository migracionesPRepository;

	 public void actualizarSoloEstatus(Integer id, Integer estatus, String usuario) {
		    migracionesPRepository.actualizarEstatusSolo(id, estatus, usuario);
		}


    public Optional<MigracionesP> obtenerMigracionPorId(Integer id) {
        return migracionesPRepository.findById(id);
    }

    //  Activar ejecución
    public void activarEjecucion(Integer idMigracion, String usuario) {
        logger.info("==> Inicia activarEjecucion para ID: " + idMigracion + ", usuario: " + usuario);

        Optional<MigracionesP> optional = migracionesPRepository.findById(idMigracion);
        if (optional.isPresent()) {
            MigracionesP entidad = optional.get();

            logger.info("Migración encontrada. Estableciendo valores...");

            entidad.setEjecuta(1);
            entidad.setEstatus(1);
            entidad.setUsuarioEjecuta(usuario);

            LocalDateTime ahora = LocalDateTime.now(); // <-- Fecha y hora del clic

            int horaActual = ahora.getHour();
            String siguienteHora;

            if (horaActual < 13) {
                siguienteHora = "15";
            } else if (horaActual < 17) {
                siguienteHora = "19";
            } else {
                siguienteHora = "13";
                logger.info("Se programa para el día siguiente (solo VC_CRON, no DT_FECHA_EJECUCION)");
            }

            entidad.setCron(siguienteHora + ":00"); // <-- Se mantiene la programación lógica

            entidad.setFechaEjecucion(ahora); // <-- Se guarda la hora REAL del clic, no la programada

            logger.info("CRON programado: " + entidad.getCron());
            logger.info("Fecha real de ejecución guardada: " + ahora);

            migracionesPRepository.save(entidad);
            logger.info("Migración actualizada y guardada exitosamente.");
        } else {
            logger.error("No se encontró la migración con ID: " + idMigracion);
            throw new IllegalArgumentException("Migración no encontrada con ID: " + idMigracion);
        }
    }

    //  Finalizar migración
    public void finalizarMigracion(Integer idMigracion, String usuarioActualiza) {
        logger.info("==> Inicia finalizarMigracion para ID: " + idMigracion + ", usuario: " + usuarioActualiza);

        Optional<MigracionesP> optional = migracionesPRepository.findById(idMigracion);
        if (optional.isPresent()) {
            MigracionesP entidad = optional.get();

            entidad.setEjecuta(0);
            entidad.setEstatus(0);
            entidad.setUsuarioActualiza(usuarioActualiza);

            String mensaje = "Se migró la información a " + LocalDate.now();
            entidad.setMensajeResultado(mensaje);

            logger.info("Finalizando migración. Mensaje: " + mensaje);

            migracionesPRepository.save(entidad);
            logger.info("Migración finalizada y guardada exitosamente.");
        } else {
            logger.error("No se encontró la migración con ID: " + idMigracion);
            throw new IllegalArgumentException("Migración no encontrada con ID: " + idMigracion);
        }
    }


}
