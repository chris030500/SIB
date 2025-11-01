package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.controller;
 
//import java.io.IOException;
//import java.nio.file.Path;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
//import java.sql.Timestamp;
import java.sql.Types;
//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.ServletOutputStream;
//import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
//import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.model.MigracionesP;
import ahorrosbienestar.sib.std.migraciones.ProcesoMigraciones.service.MigracionesPService;
//import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.MigracionesPRepository;
@Controller
@RequestMapping("/prevencion")
public class RptReporteClientesOpeController {
 
    private static final Logger logger = Logger.getLogger(RptReporteClientesOpeController.class);
 
    @Autowired
    private IUsuarioRepository iUsuarioRepository;
    
    @Autowired
    private MigracionesPService migracionesPService;
    
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
        logger.info("1");

        try {
            // Verificación de usuario autenticado
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!(principal instanceof UserDetails)) {
                mensaje = "Error: Usuario no autenticado.";
                respuesta.add(mensaje);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
            }
            logger.info("2");

            UserDetails userDetails = (UserDetails) principal;
            List<Usuario> usuarios = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
            logger.info("3");

            if (usuarios == null || usuarios.isEmpty()) {
                mensaje = "Error: Usuario no encontrado en base de datos.";
                respuesta.add(mensaje);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta);
            }
            logger.info("4");

            // 3) Validar archivo
            if (files == null || files.length == 0 || files[0].isEmpty()) {
                mensaje = "No se subió ningún archivo.";
                respuesta.add(mensaje);
                logger.warn(mensaje);
                return ResponseEntity.badRequest().body(respuesta);
            }
            logger.info("Paso 4: {} archivo(s) recibido(s)");

            // 4) Registrar proceso
            logger.info("Paso 5: registrando proceso para usuario ID={}");
            ProcesosPEPsModel proceso = procesosPEPsService.registrarProceso(usuarios.get(0).getPk_id_usuario());
            logger.info("Paso 6: proceso registrado = {}");

            if (proceso == null || proceso.getPk_id_peps() == 0) {
                mensaje = "Error interno al registrar el proceso.";
                respuesta.add(mensaje);
                logger.error(mensaje);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            }

            // 5) Procesar archivo(s) recibido(s)
            for (MultipartFile file : files) {
                logger.info("Paso 7: Archivo recibido -> nombre={} | tamaño={} bytes");
                // Aquí podrías meter tu lógica de parseo si aplica
            }

            mensaje = "Archivo(s) procesado(s) exitosamente. ID del proceso: " + proceso.getPk_id_peps();
            respuesta.add(mensaje);
            logger.info("Paso 8: {}");

            logger.info("=== FIN uploadPEP ===");
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            mensaje = "Error procesando archivo(s): " + e.getMessage();
            respuesta.add(mensaje);
            logger.error("❌ Paso X: excepción en uploadPEP", e);
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
    
    
    @PostMapping("/procesarAuditoria")
    public ResponseEntity<Map<String, Object>> procesarAuditoria(
            @RequestParam("fechaInicio") Date fechaInicio,
            @RequestParam("fechaFin") Date fechaFin) {

        logger.info("/procesarAuditoria - fechaInicio: " + fechaInicio + ", fechaFin: " + fechaFin);
        Map<String, Object> respuesta = new HashMap<>();

        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            // Llamada al SP con parámetros (IN fechaInicio, IN fechaFin, OUT error_message)
            callableStatement = connection.prepareCall("{CALL SP_ACTUALIZA_PEPS_CLIENTES_OPERACIONES(?)}");

            // Parámetros IN
            callableStatement.registerOutParameter(1, Types.VARCHAR);

            callableStatement.execute();

            String errorMessage = callableStatement.getString(1);

            if (errorMessage != null && !errorMessage.isEmpty()) {
                respuesta.put(Constantes.TEXTO_MNS, errorMessage);
                respuesta.put(Constantes.TIPO_MNS, Constantes.MENSAJE_ERROR);
            } else {
                respuesta.put(Constantes.TEXTO_MNS, "Proceso de auditoría ejecutado correctamente.");
                respuesta.put(Constantes.TIPO_MNS, Constantes.MENSAJE_OK);
            }

            return ResponseEntity.ok(respuesta);

        } catch (SQLException e) {
            logger.error("Error al ejecutar SP Auditoría: " + e.getMessage(), e);
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

    
    @PostMapping("/ejecutarVistoBueno")
    public ResponseEntity<Map<String, Object>> ejecutarVistoBueno(
            @RequestParam("fechaInicio") Date fechaInicio,
            @RequestParam("fechaFin") Date fechaFin) {
        
        logger.info("/ejecutarVistoBueno - fechaInicio: " + fechaInicio + ", fechaFin: " + fechaFin);
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


            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.setInt(2, idUsuario);
            callableStatement.setDate(3, fechaInicio);
            callableStatement.setDate(4, fechaFin);
            

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

    
    @PostMapping("/activar-ejecucion/{id}")
    public ResponseEntity<?> activarEjecucion(@PathVariable("id") Integer id,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        migracionesPService.activarEjecucion(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
 
 
 
    @PostMapping("/finalizar-migracion/{id}")
    public ResponseEntity<?> finalizarMigracion(@PathVariable("id") Integer id,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        try {
            migracionesPService.finalizarMigracion(id, userDetails.getUsername());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
 
 
    @GetMapping(value = "/obtener-migracion/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> obtenerMigracion(@PathVariable("id") Integer id) {
        Optional<MigracionesP> migracion = migracionesPService.obtenerMigracionPorId(id);
        if (migracion.isPresent()) {
            Map<String, Object> estado = new HashMap<>();
            estado.put("estatus", migracion.get().getEstatus()); 
            estado.put("ejecuta", migracion.get().getEjecuta()); 
            estado.put("mensajeResultado", migracion.get().getMensajeResultado()); 
            estado.put("cron", migracion.get().getCron());
            return ResponseEntity.ok(estado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 
 
    
    @PostMapping("/cambiar-estatus")
    public ResponseEntity<?> cambiarEstatus(@RequestParam("id") Integer id,
                                            @RequestParam("estatus") Integer estatus,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        migracionesPService.actualizarSoloEstatus(id, estatus, userDetails.getUsername());
        return ResponseEntity.ok().build();
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
    
    /**
     * Generar reporte de CLIENTES filtrado por año (2024 / 2025)
     */
    @RequestMapping(value = { "/generarRptClientesPorAnio" }, method = RequestMethod.POST)
    public ResponseEntity<List<String>> generarRptClientesPorAnio(@RequestParam("anio") String anio) {
        List<String> respuesta = new ArrayList<>();
        try {
            // Buscar reporte Jasper configurado para Clientes Anual
            ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_CLIENTES_OPERACIONES_AUDITOR);

            // Construir la URL dinámica hacia Jasper con el parámetro "Anio"
            String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
                    + datosReporte.getPk_id_reporte()
                    + "&parametros=?Anio=" + anio
                    + "&tipoDocumento=" + datosReporte.getVc_extension()
                    + "&identificadorDoc=CLI";

            respuesta.add(reporteURL);
            logger.info(" Generando reporte de clientes filtrado por año: " + anio);
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            logger.error(" Error al generar reporte de clientes por año: " + e.getMessage(), e);
            respuesta.add("Error al generar reporte de clientes: " + e.getMessage());
            return ResponseEntity.ok(respuesta);
        }
    }
    
    @PostMapping("/procesarAuditoriaClientes")
    public ResponseEntity<Map<String, Object>> procesarAuditoriaClientes(
            @RequestParam("anio") String anio) {

        logger.info("/procesarAuditoriaClientes - Año recibido: " + anio);
        Map<String, Object> respuesta = new HashMap<>();

        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = jdbcTemplate.getDataSource().getConnection();

            // Aquí llamas a tu SP específico para clientes
            callableStatement = connection.prepareCall("{CALL SIB.SP_ACTUALIZA_PEPS_CLIENTES_OPERACIONES(?)}");
            callableStatement.setString(1, anio);
            callableStatement.execute();

            respuesta.put("mensaje", "Auditoría de clientes para el año " + anio + " procesada correctamente.");
            respuesta.put("tipo", "success");
            return ResponseEntity.ok(respuesta);

        } catch (SQLException e) {
            logger.error("Error al ejecutar SP procesarAuditoriaClientes: " + e.getMessage(), e);
            respuesta.put("mensaje", "Error al procesar auditoría: " + e.getMessage());
            respuesta.put("tipo", "error");
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


}