package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.model.MigracionesP;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.MigracionesPRepository;
//import lombok.RequiredArgsConstructor;

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

	 public void actualizarEstatus(Integer id, Integer estatus, String usuario) {
		    Optional<MigracionesP> opt = migracionesPRepository.findById(id);
		    if (opt.isPresent()) {
		        MigracionesP migracion = opt.get();
		        migracion.setEstatus(estatus);
		        migracion.setUsuarioActualiza(usuario);
		        migracionesPRepository.save(migracion);
		    }
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

            LocalDateTime ahora = LocalDateTime.now();
            int horaActual = ahora.getHour();
            logger.info("Hora actual: " + horaActual);

            String siguienteHora;
            if (horaActual < 13) {
                siguienteHora = "15";
            } else if (horaActual < 17) {
                siguienteHora = "19";
            } else {
                siguienteHora = "13";
                ahora = ahora.plusDays(1);
                logger.info("Se programa para el día siguiente");
            }

            entidad.setCron(siguienteHora + ":00");
            entidad.setFechaEjecucion(
                ahora.withHour(Integer.parseInt(siguienteHora))
                     .withMinute(0)
                     .withSecond(0)
                     .withNano(0)
            );

            logger.info("Valores establecidos - CRON: " + entidad.getCron() + ", FECHA_EJECUCION: " + entidad.getFechaEjecucion());

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
