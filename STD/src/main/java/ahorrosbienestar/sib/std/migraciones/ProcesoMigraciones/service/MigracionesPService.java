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

            LocalDateTime ahora = LocalDateTime.now();
            int horaActual = ahora.getHour();

            String cron;
            LocalDateTime fechaEjecucion;

            if (horaActual < 13) {
                throw new IllegalStateException(
                    "La migración solo puede ejecutarse a partir de las 13:00 hrs"
                );
            } 
            else if (horaActual >= 13 && horaActual < 17) {
                cron = "17:00";
                fechaEjecucion = ahora.withHour(17).withMinute(0).withSecond(0);
            } 
            else {
                cron = "13:00";
                fechaEjecucion = ahora.plusDays(1)
                        .withHour(13).withMinute(0).withSecond(0);
            }

            entidad.setEjecuta(1);
            entidad.setEstatus(1);
            entidad.setUsuarioEjecuta(usuario);
            entidad.setCron(cron);
            entidad.setFechaEjecucion(fechaEjecucion);

            migracionesPRepository.save(entidad);


            logger.info("Migración marcada para ejecución correctamente.");
            logger.info("Hora real del clic: " + ahora);

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
