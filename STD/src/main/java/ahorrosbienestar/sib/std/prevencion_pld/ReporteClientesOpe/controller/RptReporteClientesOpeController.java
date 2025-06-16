package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.controller;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
//import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;
import java.util.HashMap;
//import org.springframework.web.bind.annotation.RequestBody;

//import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.ReporteClientesOpeService;
import org.springframework.web.multipart.MultipartFile;
//import java.io.File;


@Controller
@RequestMapping("/prevencion")
public class RptReporteClientesOpeController {

    private static final Logger logger = Logger.getLogger(RptReporteClientesOpeController.class);

    private final IUsuarioRepository iUsuarioRepository;
    private final IRelRolOpcionesRepository iRelRolOpcionesRepository;
    private final IReporteRepository iReporteRepository;
    private final IConfiguracionRepository iConfiguracionRepository;
    private final OpcionesMenuService opcionesMenuService;
    private final ReporteClientesOpeService reporteClientesOpeService;  

    @Autowired
    public RptReporteClientesOpeController(
            IUsuarioRepository iUsuarioRepository,
            IRelRolOpcionesRepository iRelRolOpcionesRepository,
            IReporteRepository iReporteRepository,
            IConfiguracionRepository iConfiguracionRepository,
            OpcionesMenuService opcionesMenuService,
            ReporteClientesOpeService reporteClientesOpeService) {

        this.iUsuarioRepository        = iUsuarioRepository;
        this.iRelRolOpcionesRepository = iRelRolOpcionesRepository;
        this.iReporteRepository        = iReporteRepository;
        this.iConfiguracionRepository  = iConfiguracionRepository;
        this.opcionesMenuService       = opcionesMenuService;
        this.reporteClientesOpeService = reporteClientesOpeService;
    }

    /* ---------- Vista principal ---------- */
    @GetMapping("/reporteClientesOpe")
    public String vistaReporte(Model model) {
        try {
            return (validarAcceso() == 1)
                   ? Constantes.PLD_REPORTE_CLIENTES_OPE
                   : Constantes.RUTA_ERROR;
        } catch (Exception e) {
            logger.error(Constantes.MENSAJE_ERROR + e.getMessage());
            return Constantes.RUTA_ERROR;
        }
    }

    /* ---------- Generar enlace Jasper ---------- */
    @PostMapping("/generarRptReporteClientesOpe")
    public ResponseEntity<List<String>> generarReporte(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {

        List<String> respuesta = new ArrayList<>();

        try {
            ReporteJasper r = iReporteRepository
                    .buscarReporte(Constantes.RPT_CLIENTES_OPERACIONES_AUDITOR);

            String url = iConfiguracionRepository
                    .buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
                    + r.getPk_id_reporte()
                    + "&parametros=?FechaInicio=" + fechaInicio
                    + "~FechaFin=" + fechaFin;

            respuesta.add(url);
            return ResponseEntity.ok(respuesta);

        } catch (Exception ex) {
            logger.error("Error: " + ex.getMessage());
            respuesta.add(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    /* ---------- Subida de Excel ---------- */
    @PostMapping("/uploadPEP")
    public ResponseEntity<Map<String, String>> uploadPEP(
            @RequestParam("fileUploadPEP") MultipartFile file) {

        Map<String, String> body = new HashMap<>();

        if (file.isEmpty()) {
            body.put("mensaje", "El archivo está vacío.");
            return ResponseEntity.badRequest().body(body);
        }
        try {
            reporteClientesOpeService.procesarArchivoPEP(file);
            body.put("mensaje", "Archivo procesado correctamente.");
            return ResponseEntity.ok(body);
        } catch (Exception e) {
            logger.error("Error al procesar archivo", e);
            body.put("mensaje", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }

    /* ---------- Seguridad ---------- */
    private int validarAcceso() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!(principal instanceof UserDetails)) return 0;
            String username = ((UserDetails) principal).getUsername();
            Usuario u = iUsuarioRepository.findByNombreUsuario(username).get(0);

            return iRelRolOpcionesRepository.validarAcceso(
                    u.getRol_idRol().getPk_id_rol(),
                    opcionesMenuService.buscarRelacionController(Constantes.PLD_REPORTE_CLIENTES_OPE));

        } catch (Exception e) {
            logger.error("Error:" + e.getMessage());
            return 0;
        }
    }
}
