package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.controller;
 
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
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
import org.springframework.jdbc.core.JdbcTemplate;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesoReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesosPEPsModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.GuardarHistorialReporte;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.DetallePEPsService;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.GeneracionReporteService;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.ProcesosPEPsService;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.HistorialReporte;
@Controller
@RequestMapping("/prevencion")
public class RptReporteClientesOpeController {
 
    private static final Logger logger = Logger.getLogger(RptReporteClientesOpeController.class);
 
    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    
    @Autowired
    private IRelRolOpcionesRepository iRelRolOpcionesRepository;
    
    @Autowired
    private IReporteRepository iReporteRepository;
    
    @Autowired
    private IConfiguracionRepository iConfiguracionRepository;
    
    @Autowired
    private OpcionesMenuService opcionesMenuService;
    
    @Autowired
    private GeneracionReporteService generacionReporteService;
    
    @Autowired
    private DetallePEPsService detallePEPsService;
    
    @Autowired
    private ProcesosPEPsService procesosPEPsService;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    private GuardarHistorialReporte guardarhistorialreporte;
 
    
    List<Usuario> usuarios =null;
    @GetMapping("/reporteClientesOpe")
    public String vistaReporte(Model model) {
        return (validarAcceso() == 1)
                ? Constantes.PLD_REPORTE_CLIENTES_OPE
                : Constantes.RUTA_ERROR;
    }
    
 
    @PostMapping("/reporteClientesOpe/uploadPEP")
    public ResponseEntity<?> adjuntar(@RequestParam("file") MultipartFile[] files) {

        String mensaje = "";
        ArrayList<String> respuesta = new ArrayList<>();

        try {
            // Verificación de usuario autenticado
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!(principal instanceof UserDetails)) {
                mensaje = "Error: Usuario no autenticado.";
                respuesta.add(mensaje);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
            }

            UserDetails userDetails = (UserDetails) principal;
            List<Usuario> usuarios = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

            if (usuarios == null || usuarios.isEmpty()) {
                mensaje = "Error: Usuario no encontrado en base de datos.";
                respuesta.add(mensaje);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
            }

            if (files == null || files.length == 0) {
                mensaje = "No se subió ningún archivo.";
                respuesta.add(mensaje);
                return ResponseEntity.badRequest().body(respuesta);
            }

            // Registrar proceso
            ProcesosPEPsModel proceso = procesosPEPsService.registrarProceso(usuarios.get(0).getPk_id_usuario());
            if (proceso == null || proceso.getPk_id_peps() == 0) {
                mensaje = "Error interno al registrar el proceso.";
                respuesta.add(mensaje);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            }


            // Procesar cada archivo (sin guardarlo físicamente)
            for (MultipartFile file : files) {
                detallePEPsService.procesarCargaConUsuario(file, proceso.getPk_id_peps());
            }

            mensaje = "Archivo(s) procesado(s) exitosamente. ID del proceso: " + proceso.getPk_id_peps();
            respuesta.add(mensaje);
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            mensaje = "Error procesando archivo(s): " + e.getMessage();
            logger.error(mensaje, e);
            respuesta.add(mensaje);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

 
 
 
    @RequestMapping(value = { "/generarRptReporteClientesOpeController" }, method = RequestMethod.POST)
    public ResponseEntity<List<String>> reporteClientesOpe(@RequestParam("fechaInicio") String fechaInicio,
                                                @RequestParam("fechaFin") String fechaFin) {
 
        List<String> respuesta = new ArrayList<>();
 
        try {
            
            
            ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_CLIENTES_OPERACIONES_AUDITOR);
            String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)+datosReporte.getPk_id_reporte()+ "&parametros=?FechaInicio="+fechaInicio+"~FechaFin="+fechaFin+"&tipoDocumento="+datosReporte.getVc_extension()+"&identificadorDoc=OP";
                    
            respuesta.add(reporteURL);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
            respuesta.add(e.getMessage());
            return ResponseEntity.ok(respuesta);
        }
    }
    
    @RequestMapping(value = { "/generarRptOperacionesAuditoria" }, method = RequestMethod.POST)
    public ResponseEntity<List<String>> reporteOperacionesAuditoria(@RequestParam("fechaInicio") String fechaInicio,
                                                         @RequestParam("fechaFin") String fechaFin) {
 
        List<String> respuesta = new ArrayList<>();
 
        try {
            // Buscar el reporte en base a la constante
            ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_OPERACIONES_AUDITORIA);
            
            // Armar la URL
            String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)+datosReporte.getPk_id_reporte()+ "&parametros=?FechaInicio="+fechaInicio+"~FechaFin="+fechaFin+"&tipoDocumento="+datosReporte.getVc_extension()+"&identificadorDoc=OP";
            
            respuesta.add(reporteURL);
            return ResponseEntity.ok(respuesta);
            
        } catch (Exception e) {
            logger.error("Error al generar reporte operaciones auditoría: " + e.getMessage(), e);
            respuesta.add(e.getMessage());
            return ResponseEntity.ok(respuesta);
        }
    }
    
    @RequestMapping(value = { "/generarRptHistoricoClientes" }, method = RequestMethod.POST)
    public ResponseEntity<List<String>> generarRptHistoricoClientes(@RequestParam("id") Long id) {
        List<String> respuesta = new ArrayList<>();
        try {
            ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_CLIENTES_HISTORICO);
            String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF) 
                    + datosReporte.getPk_id_reporte()
                    + "&parametros=?idHistorico=" + id
                    + "&tipoDocumento=" + datosReporte.getVc_extension()
                    + "&identificadorDoc=HC";
            respuesta.add(reporteURL);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            logger.error("Error al generar histórico clientes: " + e.getMessage(), e);
            respuesta.add(e.getMessage());
            return ResponseEntity.ok(respuesta);
        }
    }
 
    @RequestMapping(value = { "/generarRptHistoricoOperaciones" }, method = RequestMethod.POST)
    public ResponseEntity<List<String>> generarRptHistoricoOperaciones(@RequestParam("id") Long id) {
        List<String> respuesta = new ArrayList<>();
        try {
            ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_OPERACIONES_HISTORICO);
            String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
                    + datosReporte.getPk_id_reporte()
                    + "&parametros=?idHistorico=" + id
                    + "&tipoDocumento=" + datosReporte.getVc_extension()
                    + "&identificadorDoc=HO";
            respuesta.add(reporteURL);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            logger.error("Error al generar histórico operaciones: " + e.getMessage(), e);
            respuesta.add(e.getMessage());
            return ResponseEntity.ok(respuesta);
        }
    }
 
 
    
 
    @PostMapping("/reporteClientesOpe/guardarFechas")
    @ResponseBody
    public ResponseEntity<String> guardarFechas(@RequestBody ProcesoReporte dto) {
        return generacionReporteService.guardarFechasProceso(dto);
    }
 
    @PostMapping("/reporteClientesOpe/confirmar")
    @ResponseBody
    public ResponseEntity<String> confirmar(@RequestParam boolean confirmado) {
        return generacionReporteService.confirmarReporte(confirmado);
    }
    
    @PostMapping("/ejecutarDatosProceso")
    public ResponseEntity<Map<String, Object>> ejecutarDatosProceso(){
    
        logger.info("/ejecutarDatosProceso");
        String envio = Constantes.NO_ENVIA_CORREO_ASIGNACION;
        String procesoPeps ="";
        Map<String, Object> respuesta = new HashMap<>();
        
        try {
            procesoPeps = procesoGestionAsignado();
            
            if(procesoPeps != null) {
                respuesta.put(Constantes.TEXTO_MNS,procesoPeps);
                respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
                
            }else {
                respuesta.put(Constantes.TEXTO_MNS,"PROCESO OK");
                respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_OK);
            }
            
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
            respuesta.put(Constantes.TEXTO_MNS,"Error:"+e.getMessage());
            respuesta.put(Constantes.TIPO_MNS,Constantes.MENSAJE_ERROR);
            respuesta.put(Constantes.NO_ENVIA_CORREO_ASIGNACION, envio);
            return ResponseEntity.ok(respuesta);
        }
    }
 
    public String procesoGestionAsignado() {
         Connection connection = null;
            CallableStatement callableStatement = null;
            String errorMessage = null;
 
            try {
                connection = jdbcTemplate.getDataSource().getConnection();
                callableStatement = connection.prepareCall(Constantes.PEPS_CLIENTES);
                callableStatement.registerOutParameter(1, Types.VARCHAR);
                callableStatement.execute();
                errorMessage = callableStatement.getString(1);
            } catch (SQLException e) {
                logger.error("Error al ejecutar el procedimiento almacenado: " + e.getMessage());
                errorMessage = "Error al ejecutar el procedimiento almacenado: " + e.getMessage();
            } finally {
                if (callableStatement != null) {
                    try {
                        callableStatement.close();
                    } catch (SQLException e) {
                        logger.error("Error al cerrar el CallableStatement: " + e.getMessage());
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        logger.error("Error al cerrar la conexión: " + e.getMessage());
                    }
                }
            }
 
            return errorMessage;
     }
    
    @PostMapping("/ejecutarVistoBueno")
    public ResponseEntity<Map<String, Object>> ejecutarVistoBueno() {
        logger.info("/ejecutarVistoBueno");
        Map<String, Object> respuesta = new HashMap<>();
 
        String errorMessage = null;
        Connection connection = null;
        CallableStatement callableStatement = null;
 
        try {
            // Obtener usuario logueado
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = null;
            Integer idUsuario = null;
 
            if (principal instanceof UserDetails) {
                userDetails = (UserDetails) principal;
                usuarios = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
                if (!usuarios.isEmpty()) {
                    idUsuario = usuarios.get(0).getPk_id_usuario(); 
                }
            }
 
            if (idUsuario == null) {
                respuesta.put(Constantes.TEXTO_MNS, "No se pudo obtener el ID del usuario.");
                respuesta.put(Constantes.TIPO_MNS, Constantes.MENSAJE_ERROR);
                return ResponseEntity.ok(respuesta);
            }
 
            connection = jdbcTemplate.getDataSource().getConnection();
            callableStatement = connection.prepareCall(Constantes.VISTO_BUENO);
 
            callableStatement.registerOutParameter(1, Types.VARCHAR); // OUT param
            callableStatement.setInt(2, idUsuario);                  // IN param
 
            callableStatement.execute();
            errorMessage = callableStatement.getString(1);
 
            if (errorMessage != null && !errorMessage.isEmpty()) {
                respuesta.put(Constantes.TEXTO_MNS, errorMessage);
                respuesta.put(Constantes.TIPO_MNS, Constantes.MENSAJE_ERROR);
            } else {
                respuesta.put(Constantes.TEXTO_MNS, "VISTO BUENO OK");
                respuesta.put(Constantes.TIPO_MNS, Constantes.MENSAJE_OK);
            }
 
            return ResponseEntity.ok(respuesta);
        } catch (SQLException e) {
            logger.error("Error al ejecutar SP Visto Bueno: " + e.getMessage());
            respuesta.put(Constantes.TEXTO_MNS, "Error al ejecutar SP: " + e.getMessage());
            respuesta.put(Constantes.TIPO_MNS, Constantes.MENSAJE_ERROR);
            return ResponseEntity.ok(respuesta);
        } finally {
            try {
                if (callableStatement != null) callableStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                logger.error("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
 
 
    /***
     * 
     * @param model
     * @return
     */
    @GetMapping("/obtHistoricoProcesos")
    public ResponseEntity<List<HistorialReporte>> obtHistoricoProceso(Model model) {
         logger.info("/obtHistoricoProcesos");
        try {
            
            return ResponseEntity.ok(guardarhistorialreporte.procesoVobo());
            
        } catch (Exception e) {
            logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
            return null;
        }
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
            logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
            return 0;
        }
    }
}