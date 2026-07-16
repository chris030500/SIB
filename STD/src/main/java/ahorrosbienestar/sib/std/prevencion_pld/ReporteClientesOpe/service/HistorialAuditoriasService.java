//HistorialAuditoriasService
package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

/*import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.HistorialReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.HistorialReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Service*/
/*public class HistorialAuditoriasService {

    private final HistorialReporteRepository historialRepo;
    private final IUsuarioRepository usuarioRepo;

    public HistorialAuditoriasService(HistorialReporteRepository historialRepo,
                                      IUsuarioRepository usuarioRepo) {
        this.historialRepo = historialRepo;
        this.usuarioRepo = usuarioRepo;
    }*/

    /**
     * Inserta 2 filas (CLIENTES y OPERACIONES) con I_ESTATUS = 0
     * y DT_FECHA_VOBO NO NULA (se llena con now()) como pediste.
     */
 /*   @Transactional
    public void insertarPendientes(Integer idUsuario, LocalDate ini, LocalDate fin) {

        Usuario u = usuarioRepo.findById(idUsuario)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado: " + idUsuario));

        LocalDateTime iniDT = ini.atStartOfDay();
        LocalDateTime finDT = fin.atTime(23, 59, 59);

        // Evitar duplicados si se vuelve a ejecutar el amarillo
        boolean existeClientes = historialRepo.existePendiente(idUsuario, "CLIENTES", iniDT, finDT);
        boolean existeOper     = historialRepo.existePendiente(idUsuario, "OPERACIONES", iniDT, finDT);

        LocalDateTime now = LocalDateTime.now();
        Integer anio = ini.getYear();

        if (!existeClientes) {
            HistorialReporte cli = new HistorialReporte();
            cli.setFechaGeneracion(now);
            cli.setI_usuario(u);
            cli.setI_usuario_vobo(null);
            cli.setEstatus(0);
            cli.setFechaGeneracionvobo(now);     // ✅ NO NULL
            cli.setFechaInicio(iniDT);
            cli.setFechaFin(finDT);
            cli.setTipoReporte("CLIENTES");
            cli.setAnioGeneracion(anio);         // ⚠️ usa setAnioGeneracion (sin ñ)
            historialRepo.save(cli);
        }*/

       /* if (!existeOper) {
            HistorialReporte ope = new HistorialReporte();
            ope.setFechaGeneracion(now);
            ope.setI_usuario(u);
            ope.setI_usuario_vobo(null);
            ope.setEstatus(0);
            ope.setFechaGeneracionvobo(now);     // ✅ NO NULL
            ope.setFechaInicio(iniDT);
            ope.setFechaFin(finDT);
            ope.setTipoReporte("OPERACIONES");
            ope.setAnioGeneracion(anio);
            historialRepo.save(ope);
        }
    }*/

    /**
     * Marca VoBo SOLO del registro solicitado:
     * I_ESTATUS: 0 -> 1
     * I_USUARIO_VOBO y DT_FECHA_VOBO se actualizan.
     */
   /* @Transactional
    public void marcarVoboClientes(Integer idUsuarioVobo) {

        Usuario uVobo = usuarioRepo.findById(idUsuarioVobo)
                .orElseThrow(() -> new IllegalStateException("Usuario VoBo no encontrado"));

        HistorialReporte h = historialRepo.buscarUltimoPendienteClientes()
                .orElseThrow(() -> new IllegalStateException(
                        "No existe registro CLIENTES pendiente de VoBo"
                ));

        h.setEstatus(1);
        h.setI_usuario_vobo(uVobo);
        h.setFechaGeneracionvobo(LocalDateTime.now());

        historialRepo.save(h);
    }
    
    @Transactional
    public void marcarVobo(Integer idHistorial, Integer idUsuario) {

        HistorialReporte h = historialRepo.findById(idHistorial)
            .orElseThrow(() -> new RuntimeException("Registro no encontrado"));

        Usuario uVobo = usuarioRepo.findById(idUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario VoBo no encontrado"));

        h.setEstatus(1);
        h.setI_usuario_vobo(uVobo);
        h.setFechaGeneracionvobo(LocalDateTime.now());

        historialRepo.save(h);
    }



}*/
