//controller
package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.controller;
 
//import java.io.IOException;
//import java.nio.file.Path;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
//import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.ServletOutputStream;
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
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.EstadoProcesoReporteService;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.GeneracionReporteService;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.ProcesosPEPsService;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.EstadoProcesoReporte;
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
    
    @Autowired
    private EstadoProcesoReporteService estadoProcesoReporteService;
 
    
    
    List<Usuario> usuarios =null;
    @GetMapping("/reporteClientesOpe")
    public String vistaReporte(Model model) {
        return (validarAcceso() == 1)
                ? Constantes.PLD_REPORTE_CLIENTES_OPE
                : Constantes.RUTA_ERROR;
    }
    
    @GetMapping("/estado-proceso")
    @ResponseBody
    public ResponseEntity<EstadoProcesoReporte> obtenerEstadoProceso(
            @AuthenticationPrincipal UserDetails userDetails) {
 
        EstadoProcesoReporte estado =
                estadoProcesoReporteService.obtenerProcesoActivo(
                        userDetails.getUsername()
                );
 
        if (estado == null) {
            return ResponseEntity.noContent().build(); // 
        }
 
        return ResponseEntity.ok(estado);
    }
 
 
 
    
    @PostMapping("/estado-proceso/clientes-descargado")
    @ResponseBody
    public void marcarClientesDescargado(
            @AuthenticationPrincipal UserDetails userDetails) {
 
        estadoProcesoReporteService.marcarClientesDescargado(
                userDetails.getUsername()
        );
    }
 
    
    @PostMapping("/estado-proceso/auditoria-ok")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> marcarAuditoriaOk(
            @AuthenticationPrincipal UserDetails userDetails) {
 
        Map<String, Object> resp = new HashMap<>();
 
        try {
            estadoProcesoReporteService.marcarAuditoriaOk(
                    userDetails.getUsername()
            );
 
            resp.put("tipo", "success");
            resp.put("mensaje", "Auditoría marcada correctamente");
 
            return ResponseEntity.ok(resp);
 
        } catch (Exception e) {
            resp.put("tipo", "error");
            resp.put("mensaje", "Error interno al procesar la solicitud.");
 
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(resp);
        }
    }
 
 
    @PostMapping("/estado-proceso/operaciones-descargado")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> marcarOperacionesDescargado(
            @AuthenticationPrincipal UserDetails userDetails) {
 
        estadoProcesoReporteService.marcarOperacionesDescargado(
            userDetails.getUsername()
        );
 
        Map<String, Object> resp = new HashMap<>();
        resp.put("ok", true);
        return ResponseEntity.ok(resp);
    }
 
    
    @PostMapping("/estado-proceso/vobo")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> marcarVobo(
            @AuthenticationPrincipal UserDetails userDetails) {
 
        estadoProcesoReporteService.marcarOperacionesVobo(
            userDetails.getUsername()
        );
 
        Map<String, Object> resp = new HashMap<>();
        resp.put("ok", true);
        resp.put("mensaje", "Visto bueno registrado correctamente");
 
        return ResponseEntity.ok(resp);
    }
 
 
    @PostMapping("/estado-proceso/ejecutar")
    @ResponseBody
    public void ejecutarProceso(
            @RequestParam(value = "fechaInicio", required = false) String fechaInicio,
            @RequestParam("fechaCorte") String fechaCorte,
            @AuthenticationPrincipal UserDetails userDetails) {
 
        estadoProcesoReporteService.iniciarProceso(
            userDetails.getUsername(),
            fechaInicio != null && !fechaInicio.isEmpty()
                ? LocalDate.parse(fechaInicio)
                : null,
            LocalDate.parse(fechaCorte)
        );
    }
 
 
 
 
 
    @PostMapping("/reporteClientesOpe/uploadPEP")
    @ResponseBody
    public ResponseEntity<List<String>> adjuntar(
            @RequestParam("file") MultipartFile[] files) {
 
        List<String> respuesta = new ArrayList<>();
 
        try {
            // =========================
            // 1) VALIDAR USUARIO
            // =========================
            Object principal = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
 
            if (!(principal instanceof UserDetails)) {
                respuesta.add("Usuario no autenticado.");
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(respuesta);
            }
 
            UserDetails userDetails = (UserDetails) principal;
            List<Usuario> usuarios =
                    iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
 
            if (usuarios == null || usuarios.isEmpty()) {
                respuesta.add("Usuario no encontrado en base de datos.");
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(respuesta);
            }
 
            // =========================
            // 2) VALIDAR ARCHIVO
            // =========================
            if (files == null || files.length == 0 || files[0].isEmpty()) {
                respuesta.add("No se subió ningún archivo.");
                return ResponseEntity
                        .badRequest()
                        .body(respuesta);
            }
 
            // =========================
            // 3) REGISTRAR PROCESO
            // =========================
            ProcesosPEPsModel proceso =
                    procesosPEPsService.registrarProceso(
                            usuarios.get(0).getPk_id_usuario()
                    );
 
            if (proceso == null || proceso.getPk_id_peps() == 0) {
                respuesta.add("Error interno al registrar el proceso.");
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(respuesta);
            }
 
            // =========================
            // 4) PROCESAR ARCHIVOS
            // =========================
            for (MultipartFile file : files) {
                detallePEPsService.procesarCargaConUsuario(
                        file,
                        proceso.getPk_id_peps()
                );
            }
 
            // =========================
            // 5) RESPUESTA OK
            // =========================
            respuesta.add(
                    "Archivo(s) procesado(s) correctamente. ID del proceso: "
                            + proceso.getPk_id_peps()
            );
 
            return ResponseEntity.ok(respuesta);
 
        } catch (Exception e) {
            logger.error("❌ Error en uploadPEP", e);
 
            respuesta.add(
                    e.getMessage() != null
                            ? e.getMessage()
                            : "Error al procesar el archivo Excel PEP"
            );
 
            // IMPORTANTE: error controlado, NO 500 mudo
            return ResponseEntity
                    .badRequest()
                    .body(respuesta);
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
            @RequestParam("fechaCorte") String fechaCorte) {
 
        logger.info("Ejecutando SP_ACTUALIZA_PEPS_CLIENTES_OPERACIONES con fechaCorte = " + fechaCorte);
 
        Map<String, Object> respuesta = new HashMap<>();
        Connection connection = null;
        CallableStatement callableStatement = null;
 
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
 
            // el SP recibirá la fecha
            callableStatement = connection.prepareCall("{CALL SIB.SP_ACTUALIZA_PEPS_CLIENTES_OPERACIONES(?, ?)}");
 
            // PARAMETRO 1 (IN)
            callableStatement.setDate(1, Date.valueOf(fechaCorte));
 
            // PARAMETRO 2 (OUT)
            callableStatement.registerOutParameter(2, Types.VARCHAR);
 
            callableStatement.execute();
 
            String errorMessage = callableStatement.getString(2);
 
            if (errorMessage != null && !errorMessage.isEmpty()) {
                respuesta.put("mensaje", errorMessage);
                respuesta.put("tipo", "error");
            } else {
                respuesta.put("mensaje", "Proceso ejecutado correctamente con fecha " + fechaCorte);
                respuesta.put("tipo", "success");
            }
 
            return ResponseEntity.ok(respuesta);
 
        } catch (SQLException e) {
            logger.error("❌ Error al ejecutar SP: " + e.getMessage(), e);
            respuesta.put("mensaje", "Error SQL: ");
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
 
 
    
    @PostMapping("/ejecutarVistoBueno")
    public ResponseEntity<Map<String, Object>> ejecutarVistoBueno(
            @RequestParam("anio") String anio,
            @RequestParam("fechaInicioOper") String fechaInicioOper,
            @RequestParam("fechaFinOper") String fechaFinOper,
            @RequestParam("fechaCorteClientes") String fechaCorteClientes,
            @RequestParam("tipoReporte") String tipoReporte) {

        logger.info("/ejecutarVistoBueno - Año: " + anio 
                    + ", Operaciones: " + fechaInicioOper + " a " + fechaFinOper
                    + ", CorteClientes: " + fechaCorteClientes);

        Map<String, Object> respuesta = new HashMap<>();
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {

            // ===========================
            // OBTENER USUARIO LOGEADO
            // ===========================
            Object principal = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();

            Integer idUsuario = null;

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                List<Usuario> usuarios =
                        iUsuarioRepository.findByNombreUsuario(username);

                if (!usuarios.isEmpty()) {
                    idUsuario = usuarios.get(0).getPk_id_usuario();
                }
            }

            if (idUsuario == null) {
                respuesta.put("mensaje", "No se pudo obtener el ID del usuario.");
                respuesta.put("tipo", "error");
                return ResponseEntity.ok(respuesta);
            }

            // ===========================
            // PARSEAR FECHAS
            // ===========================
            int year = Integer.parseInt(anio);

            Date fInicioOper     = Date.valueOf(fechaInicioOper);
            Date fFinOper        = Date.valueOf(fechaFinOper);
            Date fCorteClientes  = Date.valueOf(fechaCorteClientes);

            // ===========================
            // LLAMADA AL SP
            // ===========================
            connection = jdbcTemplate.getDataSource().getConnection();

            callableStatement = connection.prepareCall(
                    "{CALL SIB.SP_VISTO_BUENO_AUDITORIA(?, ?, ?, ?, ?, ?, ?)}");

            callableStatement.registerOutParameter(1, Types.VARCHAR); // OUT error_message
            callableStatement.setInt(2, idUsuario);                   // p_iusuario
            callableStatement.setInt(3, year);                        // p_ianio
            callableStatement.setDate(4, fInicioOper);                // p_fechaInicio OPERACIONES
            callableStatement.setDate(5, fFinOper);                   // p_fechaFin OPERACIONES
            callableStatement.setDate(6, fCorteClientes);             // p_fechaFin CLIENTES
            callableStatement.setString(7, tipoReporte);

            callableStatement.execute();

            // ===========================
            // RESULTADO
            // ===========================
            String errorMessage = callableStatement.getString(1);

            if (errorMessage != null && !errorMessage.isEmpty()) {
                respuesta.put("mensaje", errorMessage);
                respuesta.put("tipo", "error");
            } else {
                respuesta.put("mensaje", "Visto bueno registrado correctamente.");
                respuesta.put("tipo", "success");
            }

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {

            logger.error("Error al ejecutar SP visto bueno", e);

            respuesta.put("mensaje", "Error: ");
            respuesta.put("tipo", "error");

            return ResponseEntity.ok(respuesta);

        } finally {

            try {
                if (callableStatement != null) callableStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                logger.error("Error al cerrar conexión", e);
            }
        }
    }
    
  /*  @PostMapping("/activar-ejecucion/{id}")
    public ResponseEntity<?> activarEjecucion(
            @PathVariable Integer id,
            @RequestParam(value = "fechaInicio", required = false) String fechaInicio,
            @RequestParam("fechaFin") String fechaFin,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        migracionesPService.activarEjecucion(
                id,
                fechaInicio,
                fechaFin,
                userDetails.getUsername()
        );
        return ResponseEntity.ok().build();
    }*/
 
 
    @PostMapping("/activar-ejecucion/{id}")
    public ResponseEntity<?> activarEjecucion(
            @PathVariable Integer id,
            @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin,
            @AuthenticationPrincipal UserDetails userDetails) {
 
        migracionesPService.activarEjecucion(
                id,
                LocalDate.parse(fechaInicio),
                LocalDate.parse(fechaFin),
                userDetails.getUsername()
        );
 
        return ResponseEntity.ok().build();
    }
 
 
    @PostMapping("/finalizar-migracion/{id}")
    public ResponseEntity<?> finalizarMigracion(@PathVariable("id") Integer id,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        try {
            migracionesPService.finalizarMigracion(id, userDetails.getUsername());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error interno al procesar la solicitud.");
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
    @GetMapping(value="/obtHistoricoProcesos", produces="application/json")
    @ResponseBody
    public ResponseEntity<List<HistorialReporte>> obtHistoricoProceso() {

        logger.info("/obtHistoricoProcesos");

        try {
            return ResponseEntity.ok(guardarhistorialreporte.procesoVobo());
        } catch (Exception e) {
            logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
            return ResponseEntity.internalServerError().build();
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
    
    @PostMapping("/generarRptClientesPorFechaCorte")
    public ResponseEntity<List<String>> generarRptClientesPorFechaCorte(
            @RequestParam("fechaCorte") String fechaCorte) {
 
        List<String> respuesta = new ArrayList<>();
 
        try {
            // Validar formato
            if (fechaCorte == null || fechaCorte.trim().isEmpty()) {
                respuesta.add("La fecha de corte no puede estar vacía.");
                return ResponseEntity.badRequest().body(respuesta);
            }
 
            // Buscar el reporte asociado en Jasper
            ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_CLIENTES_OPERACIONES_AUDITOR);
 
            // Construir URL para Jasper
            String reporteURL =
                    iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
                            + datosReporte.getPk_id_reporte()
                            + "&parametros=?FechaCorte=" + fechaCorte
                            + "&tipoDocumento=" + datosReporte.getVc_extension()
                            + "&identificadorDoc=CLI";
 
            respuesta.add(reporteURL);
 
            logger.info("✔ Reporte CLIENTES por FECHA CORTE generado: " + fechaCorte);
 
            return ResponseEntity.ok(respuesta);
 
        } catch (Exception e) {
            logger.error("❌ Error al generar reporte clientes FECHA CORTE: " + e.getMessage(), e);
            respuesta.add("Error al generar reporte: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
    
    @PostMapping("/estado-proceso/vobo-por-proceso")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> ejecutarVistoBuenoPorProceso(
            @AuthenticationPrincipal UserDetails userDetails) {

        Map<String, Object> respuesta = new HashMap<>();
        Connection connection = null;
        CallableStatement cs = null;

        try {
            // ============================
            // 1️⃣ OBTENER PROCESO ACTIVO
            // ============================
            EstadoProcesoReporte proceso =
                    estadoProcesoReporteService.obtenerProcesoActivo(
                            userDetails.getUsername()
                    );

            if (proceso == null) {
                throw new IllegalStateException("No existe un proceso activo para registrar visto bueno.");
            }

            // ============================
            // 2️⃣ OBTENER USUARIO
            // ============================
            Integer idUsuario = iUsuarioRepository
                    .findByNombreUsuario(userDetails.getUsername())
                    .get(0)
                    .getPk_id_usuario();

            // ============================
            // 3️⃣ EJECUTAR SP
            // ============================
            connection = jdbcTemplate.getDataSource().getConnection();
            cs = connection.prepareCall(Constantes.VISTO_BUENO_PROCESO);

            // 1️⃣ IN → ID_PROCESO
            cs.setLong(1, proceso.getIdProceso());

            // 2️⃣ OUT → error_message
            cs.registerOutParameter(2, Types.VARCHAR);

            cs.execute();

            // Obtener mensaje de error
            String error = cs.getString(2);

            // ============================
            // 4️⃣ RESPUESTA
            // ============================
            if (error != null && !error.isEmpty()) {
                respuesta.put("tipo", "error");
                respuesta.put("mensaje", error);
            } else {

                // ACTUALIZAR ESTADO PROCESO
                estadoProcesoReporteService.marcarOperacionesVobo(
                    userDetails.getUsername()
                );

                respuesta.put("tipo", "success");
                respuesta.put("mensaje", "Visto bueno registrado correctamente por proceso.");
            }

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            logger.error("❌ Error al ejecutar visto bueno por proceso", e);
            respuesta.put("tipo", "error");
            respuesta.put("mensaje", "Error interno al procesar la solicitud.");
            return ResponseEntity.ok(respuesta);

        } finally {
            try { if (cs != null) cs.close(); } catch (Exception ignored) {}
            try { if (connection != null) connection.close(); } catch (Exception ignored) {}
        }
    }

    @PostMapping("/estado-proceso/vobo-seleccion")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> registrarVoboSeleccion(
            @RequestParam boolean clientes,
            @RequestParam boolean operaciones,
            @AuthenticationPrincipal UserDetails userDetails) {

        Map<String, Object> resp = new HashMap<>();

        try {

            // 🔴 PRIMERO insertar los dos registros base
            estadoProcesoReporteService.preInsertarHistorial(
                    userDetails.getUsername()
            );

            // 🔵 LUEGO ejecutar el flujo normal de visto bueno
            estadoProcesoReporteService.registrarVoboProceso(
                    userDetails.getUsername(),
                    clientes,
                    operaciones
            );

            resp.put("tipo", "success");
            resp.put("mensaje", "Visto bueno registrado correctamente");
            return ResponseEntity.ok(resp);

        } catch (Exception e) {
            logger.error("Error al registrar visto bueno.", e);
            resp.put("tipo", "error");
            resp.put("mensaje", "Error interno al procesar la solicitud.");
            return ResponseEntity.badRequest().body(resp);
        }
    }


    @PostMapping("/estado-proceso/pre-insert-historial")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> preInsertHistorial(
            @AuthenticationPrincipal UserDetails userDetails) {

        Map<String, Object> resp = new HashMap<>();

        try {

            estadoProcesoReporteService.preInsertarHistorial(
                    userDetails.getUsername()
            );

            resp.put("tipo", "success");
            resp.put("mensaje", "Historial pre-registrado");
            return ResponseEntity.ok(resp);

        } catch (Exception e) {
            logger.error("Error al pre-registrar historial.", e);
            resp.put("tipo", "error");
            resp.put("mensaje", "Error interno al procesar la solicitud.");
            return ResponseEntity.badRequest().body(resp);
        }
    }


    
    @PostMapping("/procesarAuditoriaClientes")
    public ResponseEntity<Map<String, Object>> procesarAuditoriaClientes(
            @RequestParam("fechaCorte") String fechaCorte) {
 
        logger.info("/procesarAuditoriaClientes - Año recibido: " + fechaCorte);
        Map<String, Object> respuesta = new HashMap<>();
 
        Connection connection = null;
        CallableStatement callableStatement = null;
 
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            callableStatement = connection.prepareCall("{CALL microfin.SP_AUDITORIAS_ANUAL(?)}");
 
            callableStatement.setDate(1, Date.valueOf(fechaCorte));
 
            callableStatement.execute();
 
            respuesta.put("mensaje", "Auditoría de clientes para el año " + fechaCorte + " procesada correctamente.");
            respuesta.put("tipo", "success");
            return ResponseEntity.ok(respuesta);
 
        } catch (SQLException e) {
            logger.error("Error al ejecutar SP procesarAuditoriaClientes: " + e.getMessage(), e);
            respuesta.put("mensaje", "Error al procesar auditoría: ");
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