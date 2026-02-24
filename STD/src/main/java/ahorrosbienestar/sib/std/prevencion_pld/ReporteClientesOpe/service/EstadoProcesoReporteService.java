package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;
 
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.EstadoProcesoReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.EstadoProcesoReporteRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.HistorialReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.HistorialReporte;

import org.springframework.transaction.annotation.Transactional;
 
@Service
public class EstadoProcesoReporteService {
	
	@Autowired
	private HistorialReporteRepository historialRepository;

	@Autowired
	private IUsuarioRepository usuarioRepository;

 
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
    public void preInsertarHistorial(String usuario) {

        EstadoProcesoReporte p = obtenerProcesoActivoSeguro(usuario);

        List<Usuario> listaUsuarios = usuarioRepository.findByNombreUsuario(usuario);

        if (listaUsuarios.isEmpty()) {
            throw new IllegalStateException("Usuario no encontrado: " + usuario);
        }

        Usuario usuarioEntity = listaUsuarios.get(0);

        // CLIENTES
        HistorialReporte hClientes = new HistorialReporte();
        hClientes.setFechaGeneracion(LocalDateTime.now());
        hClientes.setI_usuario(usuarioEntity);
        hClientes.setEstatus(0); // sin vobo
        hClientes.setFechaGeneracionvobo(LocalDateTime.now());
        hClientes.setFechaInicio(
                p.getFechaInicio() != null ? p.getFechaInicio().atStartOfDay() : null
        );
        hClientes.setFechaFin(p.getFechaCorte().atStartOfDay());
        hClientes.setAnioGeneracion(p.getFechaCorte().getYear());
        hClientes.setTipoReporte("CLIENTES");

        historialRepository.save(hClientes);

        // OPERACIONES
        HistorialReporte hOper = new HistorialReporte();
        hOper.setFechaGeneracion(LocalDateTime.now());
        hOper.setI_usuario(usuarioEntity);
        hOper.setEstatus(0); // sin vobo
        hOper.setFechaGeneracionvobo(LocalDateTime.now());
        hOper.setFechaInicio(
                p.getFechaInicio() != null ? p.getFechaInicio().atStartOfDay() : null
        );
        hOper.setFechaFin(p.getFechaCorte().atStartOfDay());
        hOper.setAnioGeneracion(p.getFechaCorte().getYear());
        hOper.setTipoReporte("OPERACIONES");

        historialRepository.save(hOper);
    }

    @Transactional
    public void registrarVoboProceso(String usuario,
                                     boolean clientes,
                                     boolean operaciones) {

        EstadoProcesoReporte p = obtenerProcesoActivoSeguro(usuario);

        Usuario usuarioEntity = usuarioRepository
                .findByNombreUsuario(usuario)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        Integer anio = p.getFechaCorte().getYear();

        /* ============================
           CLIENTES
        ============================ */
        if (clientes) {

            p.setClientesVobo(true);

            // 🔥 1. Desactivar todos los anteriores del mismo año
            historialRepository.desactivarVobosPorTipoYAnio("CLIENTES", anio);

            // 🔥 2. Buscar el más reciente del año
            HistorialReporte h = historialRepository
                    .findTopByTipoReporteAndAnioGeneracionOrderByIdDesc(
                            "CLIENTES",
                            anio
                    );

            if (h != null) {
                h.setEstatus(1);
                h.setI_usuario_vobo(usuarioEntity);
                h.setFechaGeneracionvobo(LocalDateTime.now());
                historialRepository.save(h);
            }
        }

        /* ============================
           OPERACIONES
        ============================ */
        if (operaciones) {

            p.setOperacionesVobo(true);
            p.setEstatus(0); // 🔒 cierre definitivo

            // 🔥 1. Desactivar anteriores
            historialRepository.desactivarVobosPorTipoYAnio("OPERACIONES", anio);

            // 🔥 2. Buscar el más reciente
            HistorialReporte h = historialRepository
                    .findTopByTipoReporteAndAnioGeneracionOrderByIdDesc(
                            "OPERACIONES",
                            anio
                    );

            if (h != null) {
                h.setEstatus(1);
                h.setI_usuario_vobo(usuarioEntity);
                h.setFechaGeneracionvobo(LocalDateTime.now());
                historialRepository.save(h);
            }
        }

        repository.save(p);
    }

}