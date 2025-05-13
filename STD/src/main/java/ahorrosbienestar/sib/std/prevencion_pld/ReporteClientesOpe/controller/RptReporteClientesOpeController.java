package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.ReporteClientesOpeService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("prevencion")
public class RptReporteClientesOpeController {

    private static final Logger logger = Logger.getLogger(RptReporteClientesOpeController.class);

    @Autowired private IUsuarioRepository iUsuarioRepository;
    @Autowired private IRelRolOpcionesRepository iRelRolOpcionesRepository;
    @Autowired private OpcionesMenuService opcionesMenuService;
    @Autowired private ReporteClientesOpeService reporteService;

    @GetMapping("/reporteClientesOpe")
    public String paramsConfiguracion(Model model) throws IOException {
        try {
            if (validarAcceso() == 1) {
                return Constantes.PLD_REPORTE_CLIENTES_OPE;
            } else {
                return Constantes.RUTA_ERROR;
            }
        } catch (Exception e) {
            logger.error(Constantes.MENSAJE_ERROR + e.getMessage());
            return null;
        }
    }

    @PostMapping("/reporteClientesOpe/uploadPEP")
    public ResponseEntity<String> cargarArchivoPEP(@RequestParam("archivo") MultipartFile archivo) {
        try {
            reporteService.procesarArchivoPEP(archivo);
            return ResponseEntity.ok("Archivo cargado correctamente");
        } catch (Exception e) {
            logger.error("Error al cargar archivo PEP: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cargar archivo");
        }
    }

    @GetMapping("/reporteClientesOpe/historialArchivos")
    public ResponseEntity<?> obtenerHistorialArchivos() {
        try {
            return ResponseEntity.ok(reporteService.obtenerHistorialArchivosPEP());
        } catch (Exception e) {
            logger.error("Error en historial: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/reporteClientesOpe/generar")
    public ResponseEntity<?> generarReporte(
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin) {
        try {
            String url = reporteService.generarReporte(fechaInicio, fechaFin);
            return ResponseEntity.ok(Map.of("url", url));
        } catch (Exception e) {
            logger.error("Error al generar reporte: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en generación");
        }
    }

    @GetMapping("/reporteClientesOpe/historialReportes")
    public ResponseEntity<?> historialReportes(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        try {
            return ResponseEntity.ok(reporteService.consultarHistorialReportes(fechaInicio, fechaFin));
        } catch (Exception e) {
            logger.error("Error historial reportes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/reporteClientesOpe/descargar")
    public ResponseEntity<InputStreamResource> descargar(@RequestParam("id") Long id) {
        try {
            var archivo = reporteService.descargarReporte(id);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + archivo.getNombre())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(archivo.getContenido()));
        } catch (Exception e) {
            logger.error("Error en descarga: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/reporteClientesOpe/confirmar")
    public ResponseEntity<?> confirmar(@RequestParam("id") Long id) {
        try {
            reporteService.confirmarReporte(id);
            return ResponseEntity.ok("Reporte confirmado");
        } catch (Exception e) {
            logger.error("Error al confirmar: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en confirmación");
        }
    }

    private int validarAcceso() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = (principal instanceof UserDetails) ? (UserDetails) principal : null;
            List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
            return iRelRolOpcionesRepository.validarAcceso(
                usuario.get(0).getRol_idRol().getPk_id_rol(),
                opcionesMenuService.buscarRelacionController(Constantes.PLD_REPORTE_CLIENTES_OPE));
        } catch (Exception e) {
            logger.error("Error:" + e.getMessage());
            return 0;
        }
    }
}