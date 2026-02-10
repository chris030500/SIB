package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;
 
import java.time.LocalDate;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.EstadoProcesoReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.EstadoProcesoReporteRepository;
import org.springframework.transaction.annotation.Transactional;
 
@Service
public class EstadoProcesoReporteService {
 
    @Autowired
    private EstadoProcesoReporteRepository repository;
 
    /* ============================
       OBTENER PROCESO ACTIVO
    ============================ */
    public EstadoProcesoReporte obtenerProcesoActivo(String usuario) {
 
        return repository
            .findFirstByVcUsuarioAndEstatusOrderByFechaCreacionDesc(usuario, 1)
            .orElse(null);
    }
 
    private EstadoProcesoReporte obtenerProcesoActivoSeguro(String usuario) {
        EstadoProcesoReporte p = obtenerProcesoActivo(usuario);
        if (p == null) {
            throw new IllegalStateException(
                "No existe un proceso activo para el usuario " + usuario
            );
        }
        return p;
    }
 
    /* ============================
       INICIAR PROCESO
    ============================ */
    public void iniciarProceso(String usuario, LocalDate fechaInicio, LocalDate fechaCorte) {
 
        EstadoProcesoReporte p = obtenerProcesoActivo(usuario);
 
        if (p == null) {
            p = new EstadoProcesoReporte();
            p.setVcUsuario(usuario);
            p.setEstatus(1);
        }
 
        p.setFechaInicio(fechaInicio);
        p.setFechaCorte(fechaCorte);
        p.setEjecutado(true);
 
        repository.save(p);
    }
 
 
    /* ============================
       MARCAR AUDITORÍA OK
    ============================ */
    public void marcarAuditoriaOk(String usuario) {
 
        EstadoProcesoReporte p = obtenerProcesoActivoSeguro(usuario);
        p.setAuditoriaOk(true);
        repository.save(p);
    }
 
    /* ============================
       CLIENTES DESCARGADO
    ============================ */
    public void marcarClientesDescargado(String usuario) {
 
        EstadoProcesoReporte p = obtenerProcesoActivoSeguro(usuario);
        p.setClientesDescargado(true);
        repository.save(p);
    }
 
    /* ============================
       OPERACIONES DESCARGADO
    ============================ */
    public void marcarOperacionesDescargado(String usuario) {
 
        EstadoProcesoReporte p = obtenerProcesoActivoSeguro(usuario);
        p.setOperacionesDescargado(true);
        repository.save(p);
    }
 
    /* ============================
       VISTO BUENO CLIENTES
    ============================ */
    public void marcarClientesVobo(String usuario) {
 
        EstadoProcesoReporte p = obtenerProcesoActivoSeguro(usuario);
        p.setClientesVobo(true);
        repository.save(p);
    }
 
    /* ============================
       VISTO BUENO OPERACIONES (CIERRA PROCESO)
    ============================ */
    public void marcarOperacionesVobo(String usuario) {
 
        EstadoProcesoReporte p = obtenerProcesoActivoSeguro(usuario);
 
        p.setOperacionesVobo(true);
        p.setEstatus(0); // 🔒 CIERRE DEFINITIVO
 
        repository.save(p);
    }
 
    /* ============================
       GUARDAR GENÉRICO
    ============================ */
    public void guardar(EstadoProcesoReporte p) {
        repository.save(p);
    }
    
    @Transactional
    public void registrarVoboProceso(String usuario,
                                     boolean clientes,
                                     boolean operaciones) {

        EstadoProcesoReporte p = obtenerProcesoActivoSeguro(usuario);

        if (!clientes && !operaciones) {
            throw new IllegalArgumentException(
                "Debe seleccionar al menos un reporte para visto bueno"
            );
        }

        if (clientes) {
            p.setClientesVobo(true);
        }

        if (operaciones) {
            p.setOperacionesVobo(true);
            p.setEstatus(0); // 🔒 solo aquí se cierra
        }

        repository.save(p);
    }
}