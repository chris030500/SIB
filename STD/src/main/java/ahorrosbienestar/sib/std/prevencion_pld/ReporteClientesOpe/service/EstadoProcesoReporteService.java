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

    /* ============================
       OBTENER / CREAR PROCESO
    ============================ */
    public EstadoProcesoReporte obtenerProcesoActivo(String usuario, LocalDate fechaCorte) {

        return repository
            .findByVcUsuarioAndFechaCorteAndEstatus(usuario, fechaCorte, 1)
            .orElseGet(() -> {
                EstadoProcesoReporte p = new EstadoProcesoReporte();
                p.setVcUsuario(usuario);
                p.setFechaCorte(fechaCorte);
                p.setEstatus(1);
                p.setEjecutado(false);
                p.setAuditoriaOk(false);
                p.setOperacionesDescargado(false);
                p.setOperacionesVobo(false);
                return repository.save(p);
            });
    }




    /* ============================
       MARCAR AUDITORÍA OK
    ============================ */
    public void marcarAuditoriaOk(String usuario, LocalDate fechaCorte) {
        EstadoProcesoReporte p = obtenerProcesoActivo(usuario, fechaCorte);
        p.setAuditoriaOk(true);
        repository.save(p);
    }

    /* ============================
       CLIENTES DESCARGADO
    ============================ */
    public void marcarClientesDescargado(String usuario, LocalDate fechaCorte) {
        EstadoProcesoReporte p = obtenerProcesoActivo(usuario, fechaCorte);
        p.setClientesDescargado(true);
        repository.save(p);
    }

    /* ============================
       OPERACIONES DESCARGADO
    ============================ */
    public void marcarOperacionesDescargado(String usuario, LocalDate fechaCorte) {
        EstadoProcesoReporte p = obtenerProcesoActivo(usuario, fechaCorte);
        p.setOperacionesDescargado(true);
        repository.save(p);
    }



    /* ============================
       VISTO BUENO OPERACIONES
    ============================ */
    public void marcarOperacionesVobo(String usuario, LocalDate fechaCorte) {
        EstadoProcesoReporte p = obtenerProcesoActivo(usuario, fechaCorte);

        p.setOperacionesVobo(true);
        p.setEstatus(0); // 🔒 CIERRA EL PROCESO

        repository.save(p);
    }

    /* ============================
       VISTO BUENO CLIENTES (si aplica)
    ============================ */
    public void marcarClientesVobo(String usuario, LocalDate fechaCorte) {
        EstadoProcesoReporte p = obtenerProcesoActivo(usuario, fechaCorte);

        p.setClientesVobo(true);
        repository.save(p);
    }
    
    public void iniciarProceso(String usuario, LocalDate fechaCorte) {

        EstadoProcesoReporte p =
            repository.findByVcUsuarioAndFechaCorteAndEstatus(usuario, fechaCorte, 1)
            .orElseGet(() -> {
                EstadoProcesoReporte nuevo = new EstadoProcesoReporte();
                nuevo.setVcUsuario(usuario);
                nuevo.setFechaCorte(fechaCorte);
                nuevo.setEstatus(1);
                return nuevo;
            });

        p.setEjecutado(true);
        repository.save(p);
    }

    
    public void guardar(EstadoProcesoReporte p) {
        repository.save(p);
    }
    
    

}


