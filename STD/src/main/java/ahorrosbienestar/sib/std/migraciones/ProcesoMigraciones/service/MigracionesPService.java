package ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.service;

import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.model.MigracionesP;
import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.repository.MigracionesPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class MigracionesPService {

    @Autowired
    private MigracionesPRepository repository;

    public Optional<MigracionesP> obtenerMigracionPorId(Integer id) {
        return repository.findById(id);
    }

    public void activarEjecucion(Integer id, String usuario) {
        Optional<MigracionesP> migracionOpt = repository.findById(id);

        if (migracionOpt.isPresent()) {
            MigracionesP migracion = migracionOpt.get();
            migracion.setEstatus(1);
            migracion.setEjecuta(1);
            migracion.setUsuarioEjecuta(usuario);
            migracion.setUsuarioActualiza(usuario);
            migracion.setFechaEjecucion(calcularProximaEjecucion());
            migracion.setMensajeResultado(null); // Limpiar mensaje anterior
            repository.save(migracion);
        }
    }

    public void actualizarEstatus(Integer id, Integer estatus, String usuario) {
        repository.findById(id).ifPresent(migracion -> {
            migracion.setEstatus(estatus);
            migracion.setUsuarioActualiza(usuario);
            repository.save(migracion);
        });
    }

    public void actualizarMensajeResultado(Integer id, String mensaje) {
        repository.findById(id).ifPresent(migracion -> {
            migracion.setEjecuta(0);
            migracion.setMensajeResultado(mensaje);
            migracion.setEstatus(0);
            repository.save(migracion);
        });
    }

    private LocalDateTime calcularProximaEjecucion() {
        LocalTime ahora = LocalTime.now();
        if (ahora.isBefore(LocalTime.of(13, 0))) {
            return LocalDateTime.now().withHour(15).withMinute(0);
        } else if (ahora.isBefore(LocalTime.of(17, 0))) {
            return LocalDateTime.now().withHour(19).withMinute(0);
        } else {
            return LocalDateTime.now().plusDays(1).withHour(13).withMinute(0);
        }
    }
}
