package ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.service;

import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.model.MigracionesP;
import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.repository.MigracionesPRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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

 // =========================================================
    //  ACTIVAR EJECUCIÓN → 9:00 AM
    // =========================================================
    @Transactional
    public void activarEjecucion(
            Integer id,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            String usuario) {

        MigracionesP entidad = migracionesPRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Migración no encontrada"));

        //  No permitir doble ejecución
        if (entidad.getEjecuta() != null && entidad.getEjecuta() == 1) {
            throw new IllegalStateException("La migración ya está en ejecución");
        }

        // =========================
        //  VALIDAR FECHAS
        // =========================
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha fin no puede ser menor a la fecha inicio");
        }

        // =========================
        //  GUARDAR FECHAS
        // =========================
        entidad.setFechaInicio(fechaInicio);
        entidad.setFechaFin(fechaFin);

        // =========================
        //  CAMBIO REAL 0 → 1
        // =========================
        entidad.setEjecuta(1);
        entidad.setEstatus(1);
        entidad.setUsuarioEjecuta(usuario);
        entidad.setMensajeResultado(null);

        // =========================
        //  PROGRAMAR A LAS 13:00
        // =========================
        LocalDateTime ahora = LocalDateTime.now();

        LocalDateTime ejecucion13;

        if (ahora.getHour() < 14) {
            // 👉 Hoy a las 13:00
            ejecucion13 = ahora
                    .withHour(14)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);
        } else {
            // 👉 Mañana a las 13:00
            ejecucion13 = ahora
                    .plusDays(1)
                    .withHour(14)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0);
        }

        entidad.setFechaEjecucion(ejecucion13);

        // Informativo para UI
        entidad.setCron("14:00");

        migracionesPRepository.save(entidad);
    }


    //  Finalizar migración
    @Transactional
    public void finalizarMigracion(Integer id, String usuario) {

        MigracionesP entidad = migracionesPRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Migración no encontrada"));

        entidad.setEjecuta(0); // ← libera ejecución
        entidad.setUsuarioActualiza(usuario);
        entidad.setMensajeResultado(
                "Migración ejecutada correctamente el " + LocalDateTime.now()
        );

        migracionesPRepository.save(entidad);
    }




    
    @Transactional
    public void marcarError(Integer idMigracion, String error) {

        MigracionesP entidad = migracionesPRepository.findById(idMigracion)
            .orElseThrow(() -> new IllegalArgumentException("Migración no encontrada"));

        entidad.setEjecuta(2); // ← ESTADO ERROR
        entidad.setMensajeResultado("❌ Error: " + error);
        entidad.setFechaEjecucion(LocalDateTime.now());

        migracionesPRepository.save(entidad);

        logger.error("Migración fallida: " + error);
    }
}
