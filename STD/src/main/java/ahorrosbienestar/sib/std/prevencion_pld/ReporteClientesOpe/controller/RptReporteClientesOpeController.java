package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DescargaReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesoReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.ReporteClientesOpeService;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.GeneracionReporteService;

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
    private final GeneracionReporteService generacionReporteService;

    @Autowired
    public RptReporteClientesOpeController(
            IUsuarioRepository iUsuarioRepository,
            IRelRolOpcionesRepository iRelRolOpcionesRepository,
            IReporteRepository iReporteRepository,
            IConfiguracionRepository iConfiguracionRepository,
            OpcionesMenuService opcionesMenuService,
            ReporteClientesOpeService reporteClientesOpeService,
            GeneracionReporteService generacionReporteService) {

        this.iUsuarioRepository = iUsuarioRepository;
        this.iRelRolOpcionesRepository = iRelRolOpcionesRepository;
        this.iReporteRepository = iReporteRepository;
        this.iConfiguracionRepository = iConfiguracionRepository;
        this.opcionesMenuService = opcionesMenuService;
        this.reporteClientesOpeService = reporteClientesOpeService;
        this.generacionReporteService = generacionReporteService;
    }

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

    @PostMapping(path = "/reporteClientesOpe/uploadPEP", consumes = "multipart/form-data")
    @ResponseBody
    public ResponseEntity<String> uploadPEP(@RequestPart("file") MultipartFile file) {
        try {
            // Procesar el archivo y almacenar los datos en la base de datos
            reporteClientesOpeService.procesarArchivoPEP(file);
            return ResponseEntity.ok("Archivo procesado exitosamente");
        } catch (Exception e) {
            logger.error("Fallo al procesar archivo", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }


    /* opcional, para que GET no devuelva 404 sino un mensaje aclaratorio */
    @GetMapping("/prevencion/reporteClientesOpe/uploadPEP")
    @ResponseBody
    public String soloPost() {
        return "Endpoint activo: usa POST multipart/form-data.";
    }

    @PostMapping("/reporteClientesOpe/guardarFechas")
    @ResponseBody
    public ResponseEntity<?> guardarFechas(@RequestBody ProcesoReporte dto) {
        return generacionReporteService.guardarFechasProceso(dto);
    }

    @PostMapping("/reporteClientesOpe/procesar")
    @ResponseBody
    public ResponseEntity<?> procesar(@RequestParam Long procesoId) {
        return generacionReporteService.procesarAhora(procesoId);
    }

    @PostMapping("/reporteClientesOpe/confirmar")
    @ResponseBody
    public ResponseEntity<?> confirmar(@RequestParam boolean confirmado) {
        return generacionReporteService.confirmarReporte(confirmado);
    }

    @GetMapping("/reporteClientesOpe/descargas")
    @ResponseBody
    public List<DescargaReporte> descargas() {
        return generacionReporteService.obtenerDescargas();
    }

    @GetMapping("/reporteClientesOpe/descargar/{id}")
    public ResponseEntity<Resource> descargar(@PathVariable Long id) {
        return generacionReporteService.descargarArchivo(id);
    }

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
