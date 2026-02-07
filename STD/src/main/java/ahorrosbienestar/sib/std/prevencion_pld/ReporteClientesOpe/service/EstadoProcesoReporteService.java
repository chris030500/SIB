package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.EstadoProcesoReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.EstadoProcesoReporteRepository;

@Service
public class EstadoProcesoReporteService {

    @Autowired
    private EstadoProcesoReporteRepository repository;

    /**
     * Obtiene el proceso activo del usuario.
     * NO crea proceso si no se ha ejecutado.
     */
    public EstadoProcesoReporte obtenerProcesoActivo(String usuario) {
        return repository.findByVcUsuarioAndEstatus(usuario, 1)
                .orElse(null);
    }

    /**
     * Se llama SOLO cuando se presiona el botón EJECUTAR
     */
    public EstadoProcesoReporte iniciarProceso(
            String usuario,
            LocalDate fechaCorte) {

        EstadoProcesoReporte p = new EstadoProcesoReporte();
        p.setVcUsuario(usuario);
        p.setFechaCorte(fechaCorte);
        p.setEjecutado(true);
        p.setAuditoriaOk(false);
        p.setClientesDescargado(false);
        p.setClientesVobo(false);
        p.setOperacionesDescargado(false);
        p.setOperacionesVobo(false);
        p.setEstatus(1);

        return repository.save(p);
    }

    /**
     * Actualiza pasos del flujo
     */
    public EstadoProcesoReporte actualizarEstado(
            String usuario,
            String accion) {

        EstadoProcesoReporte p = obtenerProcesoActivo(usuario);

        if (p == null) {
            throw new IllegalStateException("No existe proceso activo");
        }

        switch (accion) {
        case "AUDITORIA_OK":
          p.setAuditoriaOk(true);
          break;

        case "CLIENTES_DESCARGADO":
          p.setClientesDescargado(true);
          break;
        case "CLIENTES_VOBO":
          p.setClientesVobo(true);
          break;

        case "OPERACIONES_DESCARGADO":
          p.setOperacionesDescargado(true);
          break;
        case "OPERACIONES_VOBO":
          p.setOperacionesVobo(true);
          break;
      }
      return repository.save(p);

    }
}
