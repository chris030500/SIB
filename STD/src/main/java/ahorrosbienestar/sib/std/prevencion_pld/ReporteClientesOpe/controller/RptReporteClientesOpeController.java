package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesoReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesosPEPsModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.ReporteClientesOpeService;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.DetallePEPsService;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.GeneracionReporteService;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.ProcesosPEPsService;

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
    private final DetallePEPsService detallePEPsService;
    private final ProcesosPEPsService procesosPEPsService;

    @Autowired
    public RptReporteClientesOpeController(
            IUsuarioRepository iUsuarioRepository,
            IRelRolOpcionesRepository iRelRolOpcionesRepository,
            IReporteRepository iReporteRepository,
            IConfiguracionRepository iConfiguracionRepository,
            OpcionesMenuService opcionesMenuService,
            ReporteClientesOpeService reporteClientesOpeService,
            GeneracionReporteService generacionReporteService,
            DetallePEPsService detallePEPsService,
            ProcesosPEPsService procesosPEPsService) {
        this.iUsuarioRepository = iUsuarioRepository;
        this.iRelRolOpcionesRepository = iRelRolOpcionesRepository;
        this.iReporteRepository = iReporteRepository;
        this.iConfiguracionRepository = iConfiguracionRepository;
        this.opcionesMenuService = opcionesMenuService;
        this.reporteClientesOpeService = reporteClientesOpeService;
        this.generacionReporteService = generacionReporteService;
        this.detallePEPsService = detallePEPsService;
        this.procesosPEPsService = procesosPEPsService;
    }
    List<Usuario> usuarios =null;
    @GetMapping("/reporteClientesOpe")
    public String vistaReporte(Model model) {
        return (validarAcceso() == 1)
                ? Constantes.PLD_REPORTE_CLIENTES_OPE
                : Constantes.RUTA_ERROR;
    }

    @PostMapping(value = "/reporteClientesOpe/uploadPEP",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public ResponseEntity<String> uploadPEP(@RequestParam("file") MultipartFile file) {
        try {
           

            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		UserDetails userDetails = null;
    		if (principal instanceof UserDetails) {
    			userDetails = (UserDetails) principal;
    			usuarios = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
    		}
 
            ProcesosPEPsModel proceso = procesosPEPsService.registrarProceso(usuarios.get(0).getPk_id_usuario());

            detallePEPsService.procesarCargaConUsuario(file, proceso.getPk_id_peps());

            return ResponseEntity.ok("Archivo procesado exitosamente. ID del proceso: " + proceso.getPk_id_peps());

        } catch (Exception ex) {
            logger.error("Error procesando archivo PEP", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + ex.getMessage());
        }
    }

    @RequestMapping(value = { "/generarRptReporteClientesOpeController" }, method = RequestMethod.POST)
    public ResponseEntity<?> reporteClientesOpe(@RequestParam("fechaInicio") String fechaInicio,
                                                @RequestParam("fechaFin") String fechaFin) {

        List<String> respuesta = new ArrayList<>();

        try {
        	
        	
        	ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_CLIENTES_OPERACIONES_AUDITOR);
			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)+datosReporte.getPk_id_reporte()+ "&parametros=?FechaInicio="+fechaInicio+"~FechaFin="+fechaFin+"&tipoDocumento="+datosReporte.getVc_extension()+"&identificadorDoc=OP";
					
			respuesta.add(reporteURL);
			return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
            respuesta.add(e.getMessage());
            return ResponseEntity.ok(respuesta);
        }
    }

    @PostMapping("/reporteClientesOpe/guardarFechas")
    @ResponseBody
    public ResponseEntity<?> guardarFechas(@RequestBody ProcesoReporte dto) {
        return generacionReporteService.guardarFechasProceso(dto);
    }

    @PostMapping("/reporteClientesOpe/confirmar")
    @ResponseBody
    public ResponseEntity<?> confirmar(@RequestParam boolean confirmado) {
        return generacionReporteService.confirmarReporte(confirmado);
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
