package ahorrosbienestar.sib.std.presolicitudes.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.presolicitudes.dto.CondicionesDTO;
import ahorrosbienestar.sib.std.presolicitudes.model.Empleado;
import ahorrosbienestar.sib.std.presolicitudes.model.EmpresaSIB;
import ahorrosbienestar.sib.std.presolicitudes.model.Puesto;
import ahorrosbienestar.sib.std.presolicitudes.model.SolicitudCredito;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("configuracionSolicitudes")
public class PresolicitudesController {

	private final Logger logger = Logger.getLogger(PresolicitudesController.class); // Corregido para no ser estático

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ClienteActivoService clienteActivoService;

	@Autowired
	private PuestoService puestoService;

	@Autowired
	private CondicionesService condicionesService;

	@Autowired
	private SolicitudCreditoService solicitudCreditoService;

	@Autowired
	private OpcionesMenuService opcionesMenuService;

	@Autowired
	private CatalogosCEService catalogosCEService;

	@Autowired
	private IUsuarioRepository iUsuarioRepository;

	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;

	@Autowired
	private SolicitudCreditoRepository solicitudCreditoRepository;

	@Autowired
	private CorreoSucursalService correoSucursalService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private JasperReportController jasperReportController;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	

	private static final String nombrePantalla = "Presolicitudes";
	private static final String rutaError = "errores/error_403";
	private String rutaDireccion = "";
	private int idEmpleado = 0;
	private int sucur = 0;
	List<Usuario> usuario = null;


	private static final String controllerReporte = "generarReporte";
	
	@GetMapping("/solicitudes")
	public String FiltradoQEQ() throws IOException {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			idEmpleado = usuario.get(0).getI_id_empleado();
			sucur = catalogosCEService.obtenerSucursalEmpleado(idEmpleado);
			if (validarAcceso() == 1) {

				rutaDireccion = "presolicitudes_nomina/presolicitudes";
			} else {
				rutaDireccion = rutaError;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("error:" + e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = { "/lista_empresas" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaGestiones() {
		Map<String, Object> respuesta = new HashMap<>();
		List<EmpresaSIB> listaCatalogos = new ArrayList<>();
		String mensajeError = null;
		try {
			listaCatalogos = empresaService.obtenerTodas();
			logger.info("Se llenó la lista");
			respuesta.put("lista", listaCatalogos);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// Manejo adecuado de excepciones
			logger.error("Error: " + e.getMessage(), e);
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
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
			@RequestParam("empresa") Integer empresa, @RequestParam("tipoemp") Integer tipoEmp) {

		try {
			if(producto.equals("NOMINA PRESTACIONES")) {
				tipoEmp = 0;
			}
			
			System.out.print(tipoEmp);
			
			CondicionesDTO condiciones = condicionesService.obtenerCondiciones(producto, empresa, tipoEmp);

			if (condiciones != null) {
				return ResponseEntity.ok(condiciones); // Devuelve 200 con el DTO
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Devuelve 204 si no hay datos
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Manejo de error 500
		}
	}

	@PostMapping("/crearSolicitud")
	public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudCredito solicitud) {
		try {
			solicitud.setEjecutivo(idEmpleado);
			solicitud.setSucursal(sucur);
			solicitud.setEstatus(1);
			// Intentar guardar la nueva solicitud
			SolicitudCredito nuevaSolicitud = solicitudCreditoService.guardarSolicitud(solicitud);
			return ResponseEntity.ok(nuevaSolicitud);
		} catch (Exception e) {
			// Imprimir el error en la consola
			System.out.println("Error al crear la solicitud de crédito: " + e.getMessage());

			// Enviar la respuesta de error al cliente
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

	public int validarAcceso() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

		return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(),
				opcionesMenuService.buscarRelacion(nombrePantalla));

	}

	@GetMapping("/listarSolicitudes")
	public ResponseEntity<?> listarSolicitudes(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		try {
			Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
			Page<SolicitudCredito> solicitudes = solicitudCreditoService.listarSolicitudes(idEmpleado, pageable);
			return ResponseEntity.ok(solicitudes);
		} catch (Exception e) {
			System.out.println("Error en listarSolicitudes: " + e.getMessage());
			e.printStackTrace(); // Agrega esto para ver más detalles en los logs
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
			System.out.println("[ERROR] El archivo está vacío.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo está vacío.");
		}

		try (InputStream inputStream = file.getInputStream()) {
			System.out.println("[INFO] Iniciando procesamiento del archivo...");
			Workbook workbook;

			if (file.getOriginalFilename().endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(inputStream);
			} else if (file.getOriginalFilename().endsWith(".xls")) {
				workbook = new HSSFWorkbook(inputStream);
			} else {
				System.out.println("[ERROR] Formato de archivo no válido.");
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
			System.out.println("[INFO] Encabezados detectados: " + headers);

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
			System.out.println("[INFO] Datos extraídos: " + solicitudes);
			workbook.close();

			// Llamar al servicio para actualizar estatus
			solicitudCreditoService.actualizarEstatusSolicitudes(solicitudes);
			System.out.println("[INFO] Estatus de solicitudes actualizado.");

			// Obtener IDs de solicitudes
			List<Integer> solicitudIds = solicitudes.stream().map(s -> {
				String idStr = s.get("NoSolicitud");
				return idStr != null ? Integer.parseInt(idStr) : null;
			}).filter(Objects::nonNull).collect(Collectors.toList());
			System.out.println("[INFO] IDs de solicitudes extraídos: " + solicitudIds);

			List<SolicitudCredito> solicitudesActualizadas = solicitudCreditoRepository.findAllById(solicitudIds);
			System.out.println("[INFO] Solicitudes obtenidas de la BD: " + solicitudesActualizadas);

			// Agrupar por sucursal
			Map<Integer, List<SolicitudCredito>> solicitudesPorSucursal = solicitudesActualizadas.stream()
					.collect(Collectors.groupingBy(SolicitudCredito::getSucursal));

			System.out.println("[INFO] Solicitudes agrupadas por sucursal: " + solicitudesPorSucursal);

			// Obtener el valor único de "proceso" por sucursal
			Map<Integer, Integer> procesoPorSucursal = new HashMap<>();

			for (Map.Entry<Integer, List<SolicitudCredito>> entry : solicitudesPorSucursal.entrySet()) {
				Integer sucursal = entry.getKey();
				List<SolicitudCredito> solicitudesSucursal = entry.getValue();

				// Obtener un conjunto de valores de "proceso" para verificar si hay
				// inconsistencias
				Set<Integer> procesos = solicitudesSucursal.stream().map(SolicitudCredito::getProceso)
						.collect(Collectors.toSet());

				if (procesos.size() == 1) {
					// Si solo hay un valor, lo guardamos en el mapa
					procesoPorSucursal.put(sucursal, procesos.iterator().next());
				} else {
					// Si hay más de un valor, indicamos inconsistencia
					System.out.println(
							"[ERROR] Inconsistencia en procesos para la sucursal " + sucursal + ": " + procesos);
					procesoPorSucursal.put(sucursal, 0);
				}
			}

			// Imprimir los procesos asignados a cada sucursal
			System.out.println("[INFO] Procesos por sucursal: " + procesoPorSucursal);

			// Enviar correos a cada sucursal
			// Enviar correos agrupados por proceso y sucursal
			for (Map.Entry<Integer, Integer> entry : procesoPorSucursal.entrySet()) {
			    Integer sucursal = entry.getKey();
			    Integer proceso = entry.getValue(); // Obtener el proceso asociado a la sucursal

			    // Generar el cuerpo del correo con la información del proceso
			    String cuerpoCorreo = generarCuerpoCorreo(sucursal);

			    // Obtener el correo de la sucursal
			    String correoSucursal = Optional.ofNullable(correoSucursalService.obtenerCorreoSucursal(sucursal))
			            .orElse("default@empresa.com");

			    // Obtener datos del reporte
			    LocalDate fechaActual = LocalDate.now();
			    ReporteJasper datosReporte = iReporteRepository.buscarReporte("rpt_estatus_solicitudes_vobo");
			    String fileName = "Estatus de Solicitudes " + fechaActual + datosReporte.getVc_extension();
			    InternetAddress direccion = new InternetAddress(correoSucursal);
			    
			    // Construcción del parámetro de consulta con el proceso y sucursal
			    String parametros = "?IDPROCESO=" + proceso + "&IDSUCUR=" + sucursal;
			    byte[] fileBytes = jasperReportController.generarArchivoCorreo(datosReporte.getPk_id_reporte(), parametros);

			    
			    // Debugging: Verificar datos antes del envío
			    System.out.println("[INFO] Enviando correo a: " + direccion.getAddress());
			    System.out.println("[INFO] Sucursal: " + sucursal + " | Proceso: " + proceso);
			    System.out.println("[INFO] Archivo adjunto: " + fileName);
			    System.out.println("[INFO] Parámetros del reporte: " + parametros);

			    // Enviar el correo con el adjunto
			    emailService.sendEmailWithAttachment(direccion.getAddress(), "Actualización de Estatus de Solicitudes", cuerpoCorreo, fileName, fileBytes);
			}


			return ResponseEntity.ok("Archivo procesado y notificaciones enviadas correctamente.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] Excepción capturada: " + e.getMessage());
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

	        // Construcción de la URL del reporte
	        //jasperReportController.generarArchivoRuta(datosReporte.getPk_id_reporte(), parametros, datosReporte.getVc_extension(), nombreArchivo, urlDirectorio);

	        String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)+datosReporte.getPk_id_reporte()+"&parametros=?solicitudID="
					+id+"&tipoDocumento="+datosReporte.getVc_extension()+"&identificadorDoc=SC";
	        
	        respuesta.add(reporteURL);
	        return ResponseEntity.ok(respuesta);
	    } catch (Exception e) {
	        logger.error("Error: " + e.getMessage());
	        respuesta.add("Error al generar el reporte.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
	    }
	}
	
	@PostMapping("/envioCorreo/{idEmpresa}")
    public ResponseEntity<String> enviarCorreoPorEmpresa(@PathVariable String idEmpresa) {
		
		System.out.print("El correo se enviara a la empresa: " + idEmpresa);
		
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
}
