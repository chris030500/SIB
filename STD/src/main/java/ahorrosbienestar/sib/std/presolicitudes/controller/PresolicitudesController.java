package ahorrosbienestar.sib.std.presolicitudes.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.mail.internet.InternetAddress;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.presolicitudes.dto.CondicionesDTO;
import ahorrosbienestar.sib.std.presolicitudes.dto.SimuladorRequest;
import ahorrosbienestar.sib.std.presolicitudes.model.Empleado;
import ahorrosbienestar.sib.std.presolicitudes.model.EmpresaSIB;
import ahorrosbienestar.sib.std.presolicitudes.model.Puesto;
import ahorrosbienestar.sib.std.presolicitudes.model.SolicitudCredito;
import ahorrosbienestar.sib.std.presolicitudes.repository.ParametrosimuladorRepository;
import ahorrosbienestar.sib.std.presolicitudes.repository.SolicitudCreditoRepository;
import ahorrosbienestar.sib.std.presolicitudes.service.ClienteActivoService;
import ahorrosbienestar.sib.std.presolicitudes.service.CondicionesService;
import ahorrosbienestar.sib.std.presolicitudes.service.CorreoSucursalService;
import ahorrosbienestar.sib.std.presolicitudes.service.EmailService;
import ahorrosbienestar.sib.std.presolicitudes.service.EmpresaService;
import ahorrosbienestar.sib.std.presolicitudes.service.PuestoService;
import ahorrosbienestar.sib.std.presolicitudes.service.SolicitudCreditoService;
import ahorrosbienestar.sib.std.reportesJasper.controller.JasperReportController;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("configuracionSolicitudes")
// @RequestScope // <- opcional si quisieras garantizar aislamiento por request
public class PresolicitudesController {

    private static final Logger logger = Logger.getLogger(PresolicitudesController.class);

    @Autowired private EmpresaService empresaService;
    @Autowired private ClienteActivoService clienteActivoService;
    @Autowired private PuestoService puestoService;
    @Autowired private CondicionesService condicionesService;
    @Autowired private SolicitudCreditoService solicitudCreditoService;
    @Autowired private OpcionesMenuService opcionesMenuService;
    @Autowired private CatalogosCEService catalogosCEService;
    @Autowired private IUsuarioRepository iUsuarioRepository;
    @Autowired private IRelRolOpcionesRepository iRelRolOpcionesRepository;
    @Autowired private SolicitudCreditoRepository solicitudCreditoRepository;
    @Autowired private CorreoSucursalService correoSucursalService;
    @Autowired private EmailService emailService;
    @Autowired private IReporteRepository iReporteRepository;
    @Autowired private JasperReportController jasperReportController;
    @Autowired private IConfiguracionRepository iConfiguracionRepository;
    @Autowired private ParametrosimuladorRepository parametrosimuladorRepository;
    @Autowired private JdbcTemplate jdbcTemplate;

    private static final String nombrePantalla = "Presolicitudes";
    private static final String rutaError = "errores/error_403";
    private static final String controllerReporte = "generarReporte";

    // ---------- Helpers por petición (stateless) ----------
    private Usuario currentUsuario() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) {
            logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
            throw new IllegalStateException("Usuario no autenticado");
        }
        String username = ((UserDetails) principal).getUsername();
        List<Usuario> usuarios = iUsuarioRepository.findByNombreUsuario(username);
        if (usuarios == null || usuarios.isEmpty()) {
            throw new IllegalStateException("Usuario no encontrado: " + username);
        }
        return usuarios.get(0);
    }

    private int currentIdEmpleado() {
        return currentUsuario().getI_id_empleado();
    }

    private int currentSucursal() {
        return catalogosCEService.obtenerSucursalEmpleado(currentIdEmpleado());
    }

    private int currentIdRol() {
        return currentUsuario().getRol_idRol().getPk_id_rol();
    }

    private int validarAcceso(String usuarioLogin) {
        List<Usuario> usuarios = iUsuarioRepository.findByNombreUsuario(usuarioLogin);
        if (usuarios == null || usuarios.isEmpty()) return 0;
        int rol = usuarios.get(0).getRol_idRol().getPk_id_rol();
        return iRelRolOpcionesRepository.validarAcceso(rol, opcionesMenuService.buscarRelacion(nombrePantalla));
    }

    // ---------- Endpoints ----------
    @GetMapping("/solicitudes")
    public String FiltradoQEQ(Model model) throws IOException {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = (principal instanceof UserDetails) ? (UserDetails) principal : null;

            if (userDetails != null && validarAcceso(userDetails.getUsername()) == 1) {
                model.addAttribute("cat_tipo_frecuencia", parametrosimuladorRepository.findAll());
                return "presolicitudes_nomina/presolicitudes";
            } else {
                return rutaError;
            }
        } catch (Exception e) {
            logger.error("error:" + e.getMessage(), e);
            return rutaError;
        }
    }

    @RequestMapping(value = { "/lista_empresas" }, method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> listaGestiones() {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            List<EmpresaSIB> listaCatalogos = empresaService.obtenerTodas();
            logger.info("Se llenó la lista");
            respuesta.put("lista", listaCatalogos);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage(), e);
            respuesta.put("error", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @GetMapping("empresa/{nombre}")
    public ResponseEntity<EmpresaSIB> obtenerEmpresaId(@PathVariable String nombre) {
        try {
            EmpresaSIB id = empresaService.obtenerIdEmpresa(nombre);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/lista_empleados")
    public ResponseEntity<List<Empleado>> obtenerClientesPorEmpresaEmpleado(@RequestParam Integer empresaEmpleado) {
        List<Empleado> clientes = clienteActivoService.obtenerClientesPorEmpresaEmpleado(empresaEmpleado);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/lista_productos")
    public ResponseEntity<List<String>> obtenerProductos(@RequestParam Integer empresa,
                                                         @RequestParam(required = false) Integer puesto) {
        List<String> productos;
        if (puesto != 1111) {
            productos = condicionesService.obtenerProductosUnicosPorPuesto(empresa, puesto);
        } else {
            productos = condicionesService.obtenerProductosUnicosPorEmpresa(empresa);
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/lista_plazos")
    public ResponseEntity<List<String>> obtenerPlazosPorProducto(@RequestParam String producto) {
        List<String> plazos = condicionesService.obtenerPlazosPorProducto(producto);
        return ResponseEntity.ok(plazos);
    }

    @GetMapping("/obtener_puestos")
    public ResponseEntity<List<Puesto>> obtenerPuestos(@RequestParam("empresa") Integer empresa) {
        List<Puesto> puestos = puestoService.obtenerTodosLosPuestos(empresa);
        return ResponseEntity.ok(puestos);
    }

    @GetMapping("/condiciones")
    public ResponseEntity<CondicionesDTO> obtenerCondiciones(@RequestParam("producto") String producto,
                                                             @RequestParam("empresa") Integer empresa,
                                                             @RequestParam("tipoemp") Integer tipoEmp) {
        try {
            if ("NOMINA PRESTACIONES".equals(producto)) {
                tipoEmp = 0;
            }
            CondicionesDTO condiciones = condicionesService.obtenerCondiciones(producto, empresa, tipoEmp);
            if (condiciones != null) {
                return ResponseEntity.ok(condiciones);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/crearSolicitud")
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudCredito solicitud) {
        try {
            int idEmp = currentIdEmpleado();
            int suc = currentSucursal();

            solicitud.setEjecutivo(idEmp);
            solicitud.setSucursal(suc);
            solicitud.setEstatus(1);
            solicitud.setUsuarioReg(currentUsuario());

            SolicitudCredito nuevaSolicitud = solicitudCreditoService.guardarSolicitud(solicitud);
            return ResponseEntity.ok(nuevaSolicitud);
        } catch (Exception e) {
            logger.error("Error al crear la solicitud de crédito: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la solicitud de crédito: " + e.getMessage());
        }
    }

    @PutMapping("/editarSolicitud/{id}")
    public ResponseEntity<?> editarSolicitud(@PathVariable Integer id, @RequestBody SolicitudCredito solicitud) {
        try {
            Optional<SolicitudCredito> solicitudExistente = solicitudCreditoService.obtSolicitudPorId(id);
            if (solicitudExistente.isPresent()) {
                SolicitudCredito solicitudActualizada = solicitudExistente.get();

                solicitudActualizada.setEmpresa(solicitud.getEmpresa());
                solicitudActualizada.setEmpleado(solicitud.getEmpleado());
                solicitudActualizada.setProducto(solicitud.getProducto());
                solicitudActualizada.setNumEmpleado(solicitud.getNumEmpleado());
                solicitudActualizada.setPuesto(solicitud.getPuesto());
                solicitudActualizada.setPlazo(solicitud.getPlazo());
                solicitudActualizada.setFrecuencia(solicitud.getFrecuencia());
                solicitudActualizada.setMontoSolicitado(solicitud.getMontoSolicitado());
                solicitudActualizada.setMontoDescontar(solicitud.getMontoDescontar());
                solicitudActualizada.setComentarios(solicitud.getComentarios());
                solicitudActualizada.setRenovacion(solicitud.getRenovacion());

                SolicitudCredito solicitudGuardada = solicitudCreditoService.guardarSolicitud(solicitudActualizada);
                return ResponseEntity.ok(solicitudGuardada);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitud no encontrada");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la solicitud: " + e.getMessage());
        }
    }

    @GetMapping("/listarSolicitudes")
    public ResponseEntity<?> listarSolicitudes() {
        try {
            int idEmp = currentIdEmpleado();
            int rol = currentIdRol();

            logger.info("idEmpleado: " + idEmp);
            logger.info("idRol: " + rol);

            String jefe = catalogosCEService.obtenerJefe(idEmp);
            logger.info("Jefe: " + jefe);

            List<SolicitudCredito> solicitudes;
            Map<String, Object> response = new HashMap<>();

            String rolesVobo = iConfiguracionRepository.ObtenerRolesVobo();
            List<Integer> rolesPermitidos = Arrays.stream(rolesVobo.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            if (rolesPermitidos.contains(rol)) {
                solicitudes = solicitudCreditoService.listarTodasSolicitudes();
                response.put("permiso", true);
            } else {
                if ("S".equals(jefe)) {
                    List<Integer> sucursalesJefe = catalogosCEService.obtenerSucursalesPorJefe(idEmp);
                    logger.info("Sucursales: " + sucursalesJefe);
                    solicitudes = solicitudCreditoService.listarSolicitudesPorSucursales(sucursalesJefe);
                } else {
                    solicitudes = solicitudCreditoService.listarSolicitudes(idEmp);
                }
                response.put("permiso", false);
            }

            response.put("contenido", solicitudes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error en listarSolicitudes: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar solicitudes: " + e.getMessage());
        }
    }

    @GetMapping("solicitud/{id}")
    public ResponseEntity<SolicitudCredito> obtenerSolicitudPorId(@PathVariable Integer id) {
        try {
            SolicitudCredito solicitud = solicitudCreditoService.obtenerSolicitudPorId(id);
            return ResponseEntity.ok(solicitud);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel2(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.info("[ERROR] El archivo está vacío.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo está vacío.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            logger.info("[INFO] Iniciando procesamiento del archivo...");
            Workbook workbook;

            if (file.getOriginalFilename().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (file.getOriginalFilename().endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                logger.error("[ERROR] Formato de archivo no válido.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo no es un formato Excel válido.");
            }

            Sheet sheet = workbook.getSheetAt(0);
            List<Map<String, String>> solicitudes = new ArrayList<>();
            Iterator<Row> rowIterator = sheet.iterator();

            // Leer encabezados
            Row headerRow = rowIterator.next();
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue().trim());
            }
            logger.info("[INFO] Encabezados detectados: " + headers);

            // Leer datos
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Map<String, String> solicitud = new HashMap<>();

                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = row.getCell(i);
                    String cellValue = "";

                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                cellValue = cell.getStringCellValue().trim();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    cellValue = cell.getDateCellValue().toString();
                                } else {
                                    cellValue = String.valueOf((int) cell.getNumericCellValue());
                                }
                                break;
                            case BOOLEAN:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            default:
                                cellValue = "";
                        }
                    }
                    solicitud.put(headers.get(i), cellValue);
                }
                solicitudes.add(solicitud);
            }
            logger.info("[INFO] Datos extraídos: " + solicitudes);
            workbook.close();

            // Actualiza estatus con el usuario actual (por request)
            solicitudCreditoService.actualizarEstatusSolicitudes(solicitudes, currentUsuario());
            logger.info("[INFO] Estatus de solicitudes actualizado.");

            // Obtener IDs de solicitudes para reconsultar
            List<Integer> solicitudIds = solicitudes.stream()
                    .map(s -> {
                        String idStr = s.get("NoSolicitud");
                        return idStr != null ? Integer.parseInt(idStr) : null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            logger.info("[INFO] IDs de solicitudes extraídos: " + solicitudIds);

            List<SolicitudCredito> solicitudesActualizadas = solicitudCreditoRepository.findAllById(solicitudIds);
            logger.info("[INFO] Solicitudes obtenidas de la BD: " + solicitudesActualizadas);

            // Agrupar por sucursal
            Map<Integer, List<SolicitudCredito>> solicitudesPorSucursal =
                    solicitudesActualizadas.stream().collect(Collectors.groupingBy(SolicitudCredito::getSucursal));

            logger.info("[INFO] Solicitudes agrupadas por sucursal: " + solicitudesPorSucursal);

            // Obtener valor único de proceso por sucursal
            Map<Integer, Integer> procesoPorSucursal = new HashMap<>();
            for (Map.Entry<Integer, List<SolicitudCredito>> entry : solicitudesPorSucursal.entrySet()) {
                Integer sucursal = entry.getKey();
                List<SolicitudCredito> solicitudesSucursal = entry.getValue();

                Set<Integer> procesos = solicitudesSucursal.stream()
                        .map(SolicitudCredito::getProceso)
                        .collect(Collectors.toSet());

                if (procesos.size() == 1) {
                    procesoPorSucursal.put(sucursal, procesos.iterator().next());
                } else {
                    logger.info("[ERROR] Inconsistencia en procesos para la sucursal " + sucursal + ": " + procesos);
                    procesoPorSucursal.put(sucursal, 0);
                }
            }
            logger.info("[INFO] Procesos por sucursal: " + procesoPorSucursal);

            // Envío de correos por sucursal
            for (Map.Entry<Integer, Integer> entry : procesoPorSucursal.entrySet()) {
                Integer sucursal = entry.getKey();
                Integer proceso = entry.getValue();

                String cuerpoCorreo = generarCuerpoCorreo(sucursal);
                String correoSucursal = Optional.ofNullable(correoSucursalService.obtenerCorreoSucursal(sucursal))
                        .orElse("default@empresa.com");

                LocalDate fechaActual = LocalDate.now();
                ReporteJasper datosReporte = iReporteRepository.buscarReporte("rpt_estatus_solicitudes_vobo");
                String fileName = "Estatus de Solicitudes " + fechaActual + datosReporte.getVc_extension();
                InternetAddress direccion = new InternetAddress(correoSucursal);

                String parametros = "?IDPROCESO=" + proceso + "&IDSUCUR=" + sucursal;
                byte[] fileBytes = jasperReportController.generarArchivoCorreo(
                        datosReporte.getPk_id_reporte(), parametros);

                logger.info("[INFO] Enviando correo a: " + direccion.getAddress());
                logger.info("[INFO] Sucursal: " + sucursal + " | Proceso: " + proceso);
                logger.info("[INFO] Archivo adjunto: " + fileName);
                logger.info("[INFO] Parámetros del reporte: " + parametros);

                emailService.sendEmailWithAttachment(
                        direccion.getAddress(),
                        "Actualización de Estatus de Solicitudes",
                        cuerpoCorreo,
                        fileName,
                        fileBytes
                );
            }

            return ResponseEntity.ok("Archivo procesado y notificaciones enviadas correctamente.");
        } catch (Exception e) {
            logger.error("[ERROR] Excepción capturada: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el archivo: " + e.getMessage());
        }
    }

    private String generarCuerpoCorreo(Integer sucursal) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<head><meta charset='UTF-8'></head>");
        sb.append("<body style='font-family: Arial, sans-serif; color: #2c3e50; background-color: #f4f6f7; padding: 20px;'>");
        sb.append("<div style='max-width: 600px; background: white; padding: 20px; border-radius: 8px; box-shadow: 0px 4px 6px rgba(0,0,0,0.1);'>");
        sb.append("<h2 style='color:#2980b9; border-bottom: 2px solid #2980b9; padding-bottom: 10px;'>Notificación: Actualización de Estatus</h2>");
        sb.append("<p>Estimado equipo de la sucursal <strong>").append(sucursal).append("</strong>,</p>");
        sb.append("<p>Les informamos que se ha actualizado el estatus de las solicitudes de credito de nomina registradas en su sucursal.</p>");
        sb.append("<p>Adjunto a este correo encontraran un archivo de Excel con los nuevos estatus correspondientes.</p>");
        sb.append("<p style='font-weight: bold; color: #e74c3c;'>No es necesario responder a este correo.</p>");
        sb.append("<p>Saludos.</p>");
        sb.append("</div>");
        sb.append("</body></html>");
        return sb.toString();
    }

    @RequestMapping(value = "/reporte_gestion/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> reporteGestiones(@PathVariable Integer id) {
        List<String> respuesta = new ArrayList<>();
        try {
            ReporteJasper datosReporte = iReporteRepository.buscarReporte("rpt_solicitud_aprobada");
            String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)
                    + datosReporte.getPk_id_reporte()
                    + "&parametros=?solicitudID=" + id
                    + "&tipoDocumento=" + datosReporte.getVc_extension()
                    + "&identificadorDoc=SC";
            respuesta.add(reporteURL);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage(), e);
            respuesta.add("Error al generar el reporte.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @PostMapping("/envioCorreo/{idEmpresa}")
    public ResponseEntity<String> enviarCorreoPorEmpresa(@PathVariable String idEmpresa) {
        logger.info("El correo se enviara a la empresa: " + idEmpresa);
        try {
            empresaService.prepararCorreoPorEmpresa(idEmpresa);
            return ResponseEntity.ok("Correo enviado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar correo: " + e.getMessage());
        }
    }

    @PostMapping("/aprobarSolicitud/{id}")
    public ResponseEntity<String> aprobarSolicitud(@PathVariable Integer id) {
        try {
            solicitudCreditoService.aprobarSolicitud(id);
            return ResponseEntity.ok("Solicitud aprobada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al aprobar la solicitud");
        }
    }

    @PostMapping("/cancelarSolicitud/{id}")
    public ResponseEntity<String> cancelarSolicitud(@PathVariable Integer id) {
        try {
            solicitudCreditoService.cancelarSolicitud(id);
            return ResponseEntity.ok("Solicitud cancelada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cancelar la solicitud");
        }
    }

    @PostMapping("/cambiarEstatus/{id}")
    public ResponseEntity<String> cambiarEstatus(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
        try {
            int rol = currentIdRol();
            String rolesVobo = iConfiguracionRepository.ObtenerRolesVobo();
            List<Integer> rolesPermitidos = Arrays.stream(rolesVobo.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            if (!rolesPermitidos.contains(rol)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sin permisos para cambiar estatus");
            }

            Object estatusRaw = body.get("estatus");
            if (estatusRaw == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Estatus requerido");
            }
            Integer estatus = Integer.valueOf(estatusRaw.toString());
            solicitudCreditoService.actualizarEstatusManual(id, estatus, currentUsuario());
            return ResponseEntity.ok("Estatus actualizado");
        } catch (Exception e) {
            logger.error("Error al cambiar estatus: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cambiar estatus");
        }
    }

    // Mantengo este método por compatibilidad si lo usas en otros servicios.
    public Usuario obtUsuario() {
        return currentUsuario();
    }

    @PostMapping(value = "/simulador_credito_convenios", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, Object>> procesoSimuladorConvenios(@RequestBody SimuladorRequest req) {
        Connection connection = null;
        CallableStatement cs = null;

        String errorMessage = null;
        BigDecimal cuotaDescontar = null;

        try {
            DataSource ds = jdbcTemplate.getDataSource();
            if (ds == null) throw new SQLException("DataSource no disponible");
            connection = ds.getConnection();

            cs = connection.prepareCall("{ call PROCESO_SIMULADOR_CREDITO_CONVENIOS(?, ?, ?, ?, ?, ?) }");

            // OUT
            cs.registerOutParameter(1, Types.VARCHAR); // error_message
            cs.registerOutParameter(2, Types.DECIMAL); // cuotaDescontar

            // IN
            cs.setBigDecimal(3, req.getMonto());       // D_MONTO
            cs.setBigDecimal(4, req.getTasaAnual());   // D_TASA_ANUAL
            cs.setInt(5, req.getPlazo());              // PLAZO
            cs.setString(6, req.getFrecuencia());      // FRECUENCIA

            cs.execute();

            errorMessage = cs.getString(1);
            Object cuotaObj = cs.getObject(2);
            if (cuotaObj != null) {
                cuotaDescontar = (cuotaObj instanceof BigDecimal)
                        ? (BigDecimal) cuotaObj
                        : new BigDecimal(cuotaObj.toString());
            }
        } catch (SQLException e) {
            errorMessage = "Error al ejecutar el procedimiento: " + e.getMessage();
        } finally {
            if (cs != null) {
                try { cs.close(); } catch (SQLException ignored) {}
            }
            if (connection != null) {
                try { connection.close(); } catch (SQLException ignored) {}
            }
        }

        Map<String, Object> body = new HashMap<>();
        body.put("errorMessage", errorMessage);
        body.put("cuotaDescontar", cuotaDescontar);

        return ResponseEntity.ok(body);
    }
}
