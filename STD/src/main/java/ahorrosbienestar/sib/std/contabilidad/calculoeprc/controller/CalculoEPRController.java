package ahorrosbienestar.sib.std.contabilidad.calculoeprc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.fasterxml.jackson.databind.ObjectMapper;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.contabilidad.calculoeprc.repository.IProcesoHistEPRC;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto.ArchivoOfertasAutomaticas;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("/contabilidad")
public class CalculoEPRController {
	private static final String ERROR_MESSAGE = "Error al ejecutar el procedimiento almacenado: ";
	
	private static final String ERROR_MESSAGE_KEY = "errorMessage";
	
	private static final String ESTATUS_PROCESO_KEY = "estatusProceso";
	
	private static final String MESSAGE_KEY = "message";
	
	private static final String SUCCESS_KEY = "success";
	
	private static final String ERROR_FILE_PROCESSING = "Error al procesar el archivo: ";
	
	private static final String ERROR_KEY = "error";
	
	private static final String COLUMN_PREFIX = ", Columna ";
	
	private static final String ROW_PREFIX = "Fila ";
	
	private static final Logger logger = Logger.getLogger(CalculoEPRController.class);

	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;

	@Autowired
	private IUsuarioRepository iUsuarioRepository;

	@Autowired
	private OpcionesMenuService opcionesMenuService;

	@Autowired
	private IReporteRepository iReporteRepository;

	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;

	@Autowired
	private IProcesoHistEPRC iProcesosHistEPRC;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
    private ConfigService configService;

	List<Usuario> usuario = null;
	
	String format = "yyyy-MM-dd";
	
	public static class FileProcessingException extends RuntimeException implements Serializable {
        private static final long serialVersionUID = 1L; // o cualquier número que elijas

        public FileProcessingException(String message) {
            super(message);
        }

        public FileProcessingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
	
	@PostMapping("/subirArchivoEPRC")
    public ResponseEntity<?> adjuntar(@RequestParam("file") MultipartFile[] files,
            @RequestParam("idFolio") String idFolio) {
 
        String mensaje = "";
        ArrayList<String> respuesta = new ArrayList<>();
        List<Configuracion> bases = configService.obtenerUrlLayout();
        try {
            if (files.length > 0) {
                for (MultipartFile file : files) {
                    byte[] bytes = file.getBytes();
                    String folderPath = bases.get(0).getVc_valor_propiedad();
 
                    Path nuevoPath = Paths.get(folderPath + file.getOriginalFilename());
                    Files.write(nuevoPath, bytes);
 
                    mensaje = "Se agrego archivo correctamente.";
                    logger.info(mensaje);
                }
            } else {
                mensaje = "No subio archivo.";
                logger.info(mensaje);
            }
            respuesta.add(mensaje);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            logger.error("--" + e.getMessage());
            mensaje = e.getMessage();
            respuesta.add(mensaje);
            return ResponseEntity.ok(respuesta);
        }
    }
	
	@GetMapping("/listaArchivosEPRC")
    public ResponseEntity<List<ArchivoOfertasAutomaticas>> listFilesInFolder() throws IOException {
 
        try {
            List<Configuracion> bases = configService.obtenerUrlLayout();
            String folderPath = bases.get(0).getVc_valor_propiedad();
            Path folder = Paths.get(folderPath);
 
            List<ArchivoOfertasAutomaticas> listaArchivos = new ArrayList<>();
 
            // Listar los archivos en la carpeta
            Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
            	@Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    ArchivoOfertasAutomaticas archivos = new ArchivoOfertasAutomaticas();
                    archivos.setNombreArchivo(file.getFileName().toString());
                    archivos.setPesoArchivo((attrs.size() / 1024) + " KB");
 
                    logger.info("Nombre:" + archivos.getNombreArchivo());
                    logger.info("tamaño:" + archivos.getPesoArchivo());
                    long millis = attrs.creationTime().toMillis();
                    FileTime fileTime = FileTime.fromMillis(millis);
                    LocalDate fechaCreacion = fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    String fechaFormateada = fechaCreacion.toString();
                    archivos.setFechaCreacion(fechaFormateada);
 
                    long fechaModificacion = attrs.lastModifiedTime().toMillis();
                    FileTime fileTimeMod = FileTime.fromMillis(fechaModificacion);
                    LocalDate fechaMod = fileTimeMod.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    String fechaFormateadaMod = fechaMod.toString();
                    archivos.setFechaModifacion(fechaFormateadaMod);
 
                    logger.info("fecha de creacion:" + fechaFormateada);
                    logger.info("fecha de Modificacion:" + fechaFormateadaMod);
                    archivos.setRutaArchivo(bases.get(0).getVc_valor_propiedad() + file.getFileName().toString());
                    listaArchivos.add(archivos);
                    return FileVisitResult.CONTINUE;
                }
            });
            return ResponseEntity.ok(listaArchivos);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

	@GetMapping("/calculoEPRC")
	public String calculoEPRC(Model model) throws IOException {
		String rutaDireccion = Constantes.CONTABILIDAD_CALCULO_EPRC;
		try {
			if (validarAcceso(Constantes.CONTABILIDAD_CALCULO_EPRC) == 1) {
				ObjectMapper objectMapper = new ObjectMapper();
				String listaHistProcesoEPRC = objectMapper.writeValueAsString(iProcesosHistEPRC.obtProcesoEPRC());
				model.addAttribute("listaHistProcesoEPRC", listaHistProcesoEPRC);
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}
	
	private Map<String, Object> ejecutarProcedimientoEPRC() {
	    Map<String, Object> result = new HashMap<>();
	    String errorMessage = "";
	    int estatusProceso = 0;
	    
	    try (Connection connection = jdbcTemplate.getDataSource().getConnection();
	         CallableStatement callableStatement = connection.prepareCall(Constantes.PROCESO_EPRC)) {
	        
	        callableStatement.setInt(1, obtUsuario());
	        callableStatement.registerOutParameter(2, Types.VARCHAR);
	        callableStatement.registerOutParameter(3, Types.INTEGER);
	        callableStatement.execute();
	        
	        errorMessage = callableStatement.getString(2);
	        estatusProceso = callableStatement.getInt(3);
	        
	    } catch (SQLException e) {
	        logger.error(ERROR_MESSAGE + e.getMessage());
	        errorMessage = ERROR_MESSAGE + e.getMessage();
	    }
	    
	    result.put(ERROR_MESSAGE_KEY, errorMessage);
	    result.put(ESTATUS_PROCESO_KEY, estatusProceso);
	    return result;
	}


	@PostMapping("/procesar-EPRC")
	public ResponseEntity<Map<String, Object>> procesarCostoNetoFinaciado(@RequestParam Map<String, String> params) {
	    Map<String, Object> response = new HashMap<>();
	    
	    try {
	        Map<String, Object> resultado = ejecutarProcedimientoEPRC();
	        String errorMessage = (String) resultado.get(ERROR_MESSAGE_KEY);
	        int estatusProceso = (int) resultado.get(ESTATUS_PROCESO_KEY);
	        
	        response.put(MESSAGE_KEY, (errorMessage.isEmpty()) ? Constantes.PROCESO_CNF_OK : errorMessage);
	        response.put(ESTATUS_PROCESO_KEY, estatusProceso);
	        return ResponseEntity.ok(response);
	        
	    } catch (NumberFormatException e) {
	        response.put(MESSAGE_KEY, Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        
	    } catch (IllegalArgumentException e) {
	        response.put(MESSAGE_KEY, Constantes.ERROR_PARAMETROS + e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        
	    } catch (Exception e) {
	        response.put(MESSAGE_KEY, Constantes.ERROR_GUARDAR + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	public int validarAcceso(String rutaController) {

		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuarios = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return iRelRolOpcionesRepository.validarAcceso(usuarios.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}

	}

	public int obtUsuario() {

		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuarios = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return usuarios.get(0).getPk_id_usuario();

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return 0;
		}

	}

	@PostMapping("/reporte-EPRC")
	public ResponseEntity<Map<String, Object>> reporteCostoNetoFinaciado(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		try {
			LocalDate fechaActual = LocalDate.now();
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_EPRC);

			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
					+ datosReporte.getPk_id_reporte() + "&parametros=" + "&tipoDocumento="
					+ datosReporte.getVc_extension() + "&identificadorDoc=" + fechaActual;

			response.put("urlReporte", reporteURL);
			response.put(MESSAGE_KEY, (reporteURL.length() > 0) ? Constantes.REPORTE_OK : Constantes.REPORTE_KO);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put(MESSAGE_KEY, Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			response.put(MESSAGE_KEY, Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			response.put(MESSAGE_KEY, Constantes.ERROR_GUARDAR + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	private Map<String, Object> ejecutarProcedimientoVoBo() {
	    Map<String, Object> result = new HashMap<>();
	    String errorMessage = null;
	    int estatusProceso = 0;

	    try (Connection connection = jdbcTemplate.getDataSource().getConnection();
	         CallableStatement callableStatement = connection.prepareCall(Constantes.PROCESO_VOBO_OK_EPRC)) {
	        
	        callableStatement.setInt(1, obtUsuario());
	        callableStatement.registerOutParameter(2, Types.VARCHAR);
	        callableStatement.registerOutParameter(3, Types.INTEGER);
	        callableStatement.execute();
	        
	        errorMessage = callableStatement.getString(2);
	        estatusProceso = callableStatement.getInt(3);

	    } catch (SQLException e) {
	        logger.error(ERROR_MESSAGE, e);
	        errorMessage = ERROR_MESSAGE + e.getMessage();
	    }

	    result.put(ERROR_MESSAGE_KEY, errorMessage);
	    result.put(ESTATUS_PROCESO_KEY, estatusProceso);
	    return result;
	}

	@PostMapping("/guardar-voboProcesoEPRC")
	public ResponseEntity<Map<String, Object>> guardarVoBo(@RequestParam Map<String, String> params) {
	    Map<String, Object> response = new HashMap<>();
	    
	    try {
	        // Ejecutar procedimiento almacenado
	        Map<String, Object> resultado = ejecutarProcedimientoVoBo();
	        String errorMessage = (String) resultado.get(ERROR_MESSAGE_KEY);
	        int estatusProceso = (int) resultado.get(ESTATUS_PROCESO_KEY);
	        
	        // Obtener historial de procesos
	        ObjectMapper objectMapper = new ObjectMapper();
	        String listaHistProcesoEPRC = objectMapper.writeValueAsString(iProcesosHistEPRC.obtProcesoEPRC());
	        
	        // Construir respuesta
	        response.put(MESSAGE_KEY, (errorMessage == null) ? Constantes.VOBO_OK : errorMessage);
	        response.put(ESTATUS_PROCESO_KEY, estatusProceso);
	        response.put("listaHistProcesoEPRC", listaHistProcesoEPRC);
	        
	        return ResponseEntity.ok(response);

	    } catch (NumberFormatException e) {
	        response.put(MESSAGE_KEY, Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
	        return ResponseEntity.badRequest().body(response);

	    } catch (IllegalArgumentException e) {
	        response.put(MESSAGE_KEY, Constantes.ERROR_PARAMETROS + e.getMessage());
	        return ResponseEntity.badRequest().body(response);

	    } catch (Exception e) {
	        response.put(MESSAGE_KEY, Constantes.ERROR_GUARDAR + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@PostMapping("/reporte-EPRCHist")
	public ResponseEntity<Map<String, Object>> costoNetoFinanciadoHist(@RequestBody Map<String, Object> data) {
		Map<String, Object> response = new HashMap<>();
		try {
			// Obtén el valor como Object
			Object procesoIdObj = data.get("procesoId");
			Integer tipoProceso = null;

			// Verifica si procesoIdObj es una instancia de Number
			if (procesoIdObj instanceof Number) {
				tipoProceso = ((Number) procesoIdObj).intValue();
			} else if (procesoIdObj != null) {
				// Convierte a Integer si no es una instancia de Number
				tipoProceso = Integer.parseInt(procesoIdObj.toString());
			}
			LocalDate fechaActual = LocalDate.now();
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_EPRC_HIST);

			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
					+ datosReporte.getPk_id_reporte() + Constantes.REPORTE_HIST + tipoProceso + "&tipoDocumento="
					+ datosReporte.getVc_extension() + "&identificadorDoc=" + fechaActual;

			response.put("urlReporte", reporteURL);
			response.put(MESSAGE_KEY, (reporteURL.length() > 0) ? Constantes.REPORTE_OK : Constantes.REPORTE_KO);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put(MESSAGE_KEY, Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			response.put(MESSAGE_KEY, Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			response.put(MESSAGE_KEY, Constantes.ERROR_GUARDAR + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/procesarArchivo")
	public ResponseEntity<Map<String, Object>> procesarArchivo(@RequestBody Map<String, String> request) {
		String nombreArchivo = request.get("nombreArchivo");
		
		if(nombreArchivo.contains("layout_garantias")) {
			
			try {
	            // Aquí agregas la lógica para procesar el archivo
	            procesarYMigrarArchivoGarantias(nombreArchivo);

	            Map<String, Object> response = new HashMap<>();
	            response.put(SUCCESS_KEY, true);
	            return ResponseEntity.ok(response);
	        } catch (IOException e) {
	            logger.error(ERROR_FILE_PROCESSING, e);
	            Map<String, Object> response = new HashMap<>();
	            response.put(SUCCESS_KEY, false);
	            response.put(ERROR_KEY, ERROR_FILE_PROCESSING + e.getMessage());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        } catch (Exception e) {
	            logger.error("Error inesperado: ", e);
	            Map<String, Object> response = new HashMap<>();
	            response.put(SUCCESS_KEY, false);
	            response.put(ERROR_KEY, e.getMessage());
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
			
		} else {
			
			try {
				// Aquí agregas la lógica para procesar el archivo
				procesarYMigrarArchivoEtapas(nombreArchivo);

				Map<String, Object> response = new HashMap<>();
				response.put(SUCCESS_KEY, true);
				return ResponseEntity.ok(response);
			} catch (IOException e) {
				logger.error(ERROR_FILE_PROCESSING, e);
				Map<String, Object> response = new HashMap<>();
				response.put(SUCCESS_KEY, false);
				response.put(ERROR_KEY, ERROR_FILE_PROCESSING + e.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} catch (Exception e) {
				logger.error(ERROR_FILE_PROCESSING, e);
				Map<String, Object> response = new HashMap<>();
				response.put(SUCCESS_KEY, false);
				response.put(ERROR_KEY, e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
		}
	}

	public void procesarYMigrarArchivoGarantias(String nombreArchivo) throws IOException {
	    List<Configuracion> bases = configService.obtenerUrlLayout();
	    String folderPath = bases.get(0).getVc_valor_propiedad();
	    
	    File file = new File(folderPath + nombreArchivo);
	    verificarArchivoExistente(file);
	    
	    List<String> errores = new ArrayList<>(); // Para almacenar errores
	    List<Object[]> dataToInsert = new ArrayList<>();
	    String fechaCorteFin = "";
	    
	    try (FileInputStream fis = new FileInputStream(file); Workbook workbook = obtenerWorkbook(nombreArchivo, fis)) {
	        Sheet sheet = workbook.getSheetAt(0);
	        int idProceso = obtenerSiguienteIdProcesoGarantias();
	        
	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	            Row row = sheet.getRow(i);
	            procesarFila(row, idProceso, i, dataToInsert, errores);
	        }

	        if (!errores.isEmpty()) {
	            throw new FileProcessingException("Errores encontrados en el archivo:\n" + String.join("\n", errores));
	        }

	        if (!dataToInsert.isEmpty()) {
	            fechaCorteFin = (String) dataToInsert.get(0)[4];
	            insertarEnBaseDeDatosGarantias(dataToInsert);
	            insertarDatosDeControlGarantias(idProceso, dataToInsert.size(), fechaCorteFin);
	        }
	    } catch (Exception e) {
	    	logger.error("Se produjo un error: ", e);
	        throw new FileProcessingException("Error procesando la operación", e); // Lanza una excepción dedicada
	    }
	}

	private void verificarArchivoExistente(File file) throws IOException {
	    if (!file.exists()) {
	        throw new IOException("El archivo no existe en la ruta especificada.");
	    }
	}

	private Workbook obtenerWorkbookEta(String nombreArchivo, FileInputStream fis) throws IOException {
	    if (nombreArchivo.endsWith("xlsx")) {
	        return new XSSFWorkbook(fis);
	    } else if (nombreArchivo.endsWith("xls")) {
	        return new HSSFWorkbook(fis);
	    } else {
	        throw new IOException("Formato de archivo no soportado. Solo se aceptan archivos .xlsx y .xls.");
	    }
	}

	private void procesarFila(Row row, int idProceso, int filaIndex, List<Object[]> dataToInsert, List<String> errores) {
	    if (row == null) {
	        errores.add(ROW_PREFIX + (filaIndex + 1) + ": La fila está vacía.");
	        return;
	    }

	    try {
	        BigInteger valor1 = obtenerValorNumerico(row.getCell(0), filaIndex, "A", errores);
	        String valor2 = obtenerValorTexto(row.getCell(1), filaIndex, "B", errores);
	        float valor3 = obtenerValorNumerico(row.getCell(2), filaIndex, "C", errores).floatValue();
	        String valor4 = obtenerValorTexto(row.getCell(3), filaIndex, "D", errores);
	        String valor5 = obtenerValorFecha(row.getCell(4), filaIndex, "E", errores);
	        
	        if (errores.isEmpty()) {
	            dataToInsert.add(new Object[]{valor1, valor2, valor3, valor4, valor5, idProceso});
	        }
	    } catch (Exception e) {
	        errores.add(String.format("Error en la fila %d: %s", filaIndex + 1, e.getMessage()));
	    }
	}

	private BigInteger obtenerValorNumerico(Cell cell, int filaIndex, String columna, List<String> errores) {
	    if (cell != null && cell.getCellType() == CellType.NUMERIC) {
	        return BigInteger.valueOf((long) cell.getNumericCellValue());
	    } else {
	        errores.add(ROW_PREFIX + (filaIndex + 1) + COLUMN_PREFIX + columna + ": Valor no válido (debe ser numérico).");
	        return BigInteger.ZERO; // Valor predeterminado
	    }
	}

	private String obtenerValorTexto(Cell cell, int filaIndex, String columna, List<String> errores) {
	    if (cell != null && cell.getCellType() == CellType.STRING) {
	        return cell.getStringCellValue();
	    } else {
	        errores.add(ROW_PREFIX + (filaIndex + 1) + COLUMN_PREFIX + columna + ": Valor no válido (debe ser texto).");
	        return ""; // Valor predeterminado
	    }
	}

	private String obtenerValorFecha(Cell cell, int filaIndex, String columna, List<String> errores) {
	    if (cell != null && cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
	        Date date = cell.getDateCellValue();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
	        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        return localDate.format(formatter);
	    } else {
	        errores.add(ROW_PREFIX + (filaIndex + 1) + COLUMN_PREFIX + columna + ": Valor no válido (debe ser una fecha).");
	        return ""; // Valor predeterminado
	    }
	}

	private void insertarEnBaseDeDatosGarantias(List<Object[]> dataToInsert) {
	    // Usar la constante SQL para la inserción
	    jdbcTemplate.batchUpdate(Constantes.SQL_INSERTAR_GARANTIAS, dataToInsert);
	}

	private void insertarDatosDeControlGarantias(int idProceso, int totalRecords, String fechaCorteFin) {
	    // Usar las constantes SQL para las consultas
	    List<Map<String, Object>> result = jdbcTemplate.queryForList(Constantes.SQL_SELECCIONAR_CONTROL_GARANTIAS, fechaCorteFin);

	    if (!result.isEmpty()) {
	        Map<String, Object> row = result.get(result.size() - 1);
	        int idProc = (Integer) row.get("PK_IdProcesoComision");

	        // Usar constante SQL para actualizar el estatus
	        jdbcTemplate.update(Constantes.SQL_ACTUALIZAR_ESTATUS_CONTROL_GARANTIAS, idProc);
	    }

	    // Definir los valores de control
	    String estatusProceso = "A";
	    Date fechaProceso = new Date();
	    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
	    String fechaProcesoStr = dateFormat.format(fechaProceso);

	    // Usar constante SQL para la inserción de control
	    jdbcTemplate.update(Constantes.SQL_INSERTAR_CONTROL_GARANTIAS, idProceso, totalRecords, fechaProcesoStr, obtUsuario(), estatusProceso, fechaCorteFin);
	}

	private int obtenerSiguienteIdProcesoGarantias() {
	    // Usar la constante SQL para obtener el siguiente ID
	    return jdbcTemplate.queryForObject(Constantes.SQL_OBTENER_SIGUIENTE_ID_PROCESO_GARANTIAS, Integer.class);
	}

    
    
    
    
	public void procesarYMigrarArchivoEtapas(String nombreArchivo) throws IOException {
	    List<Configuracion> bases = configService.obtenerUrlLayout();
	    String folderPath = bases.get(0).getVc_valor_propiedad();
	    File file = new File(folderPath + nombreArchivo);
	    
	    if (!file.exists()) {
	        throw new IOException("El archivo no existe en la ruta especificada.");
	    }

	    try (FileInputStream fis = new FileInputStream(file);
	         Workbook workbook = obtenerWorkbookEta(nombreArchivo, fis)) {
	        
	        List<Object[]> dataToInsert = new ArrayList<>();
	        List<String> errores = new ArrayList<>();
	        int idProceso = obtenerSiguienteIdProcesoEtapas();

	        for (int i = 1; i <= workbook.getSheetAt(0).getLastRowNum(); i++) {
	            procesarFila(workbook.getSheetAt(0).getRow(i), dataToInsert, errores, idProceso, i);
	        }

	        verificarErrores(errores);
	        insertarDatosSiExistente(dataToInsert, idProceso);

	    } catch (Exception e) {
	        logger.error(ERROR_FILE_PROCESSING + e.getMessage(), e);
	        throw e; // Lanza la excepción para manejarla en otro lugar
	    }
	}

	private Workbook obtenerWorkbook(String nombreArchivo, FileInputStream fis) throws IOException {
	    if (nombreArchivo.endsWith("xlsx")) {
	        return new XSSFWorkbook(fis);
	    } else if (nombreArchivo.endsWith("xls")) {
	        return new HSSFWorkbook(fis);
	    } else {
	        throw new IOException("Formato de archivo no soportado. Solo se aceptan archivos .xlsx y .xls.");
	    }
	}

	private void procesarFila(Row row, List<Object[]> dataToInsert, List<String> errores, int idProceso, int rowIndex) {
	    if (row == null) {
	        errores.add(ROW_PREFIX + (rowIndex + 1) + ": La fila está vacía.");
	        return;
	    }

	    try {
	        BigInteger valor1 = obtenerValorCeldaNumerica(row.getCell(0), "A", errores, rowIndex);
	        int valor2 = obtenerValorCeldaNumerica(row.getCell(1), "B", errores, rowIndex).intValue();
	        String valor3 = obtenerValorCeldaFecha(row.getCell(2), errores, rowIndex);
	        
	        if (valor1 != null && valor3 != null) {
	            dataToInsert.add(new Object[]{valor1, valor2, valor3, idProceso});
	        }
	    } catch (Exception e) {
	        errores.add("Error en la fila " + (rowIndex + 1) + ": " + e.getMessage());
	    }
	}

	private BigInteger obtenerValorCeldaNumerica(Cell cell, String columna, List<String> errores, int rowIndex) {
	    if (cell != null && cell.getCellType() == CellType.NUMERIC) {
	        return BigInteger.valueOf((long) cell.getNumericCellValue());
	    } else {
	        errores.add(ROW_PREFIX + (rowIndex + 1) + COLUMN_PREFIX + columna + ": Valor no válido (debe ser numérico).");
	        return null;
	    }
	}

	private String obtenerValorCeldaFecha(Cell cell, List<String> errores, int rowIndex) {
	    if (cell != null && cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
	        Date date = cell.getDateCellValue();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        return localDate.format(formatter);
	    } else {
	        errores.add(ROW_PREFIX + (rowIndex + 1) + ", Columna C: Valor no válido (debe ser una fecha).");
	        return null;
	    }
	}

	private void verificarErrores(List<String> errores) {
	    if (!errores.isEmpty()) {
	        throw new FileProcessingException("Errores encontrados en el archivo:\n" + String.join("\n", errores));
	    }
	}

	private void insertarDatosSiExistente(List<Object[]> dataToInsert, int idProceso) {
	    if (!dataToInsert.isEmpty()) {
	        Object[] firstRow = dataToInsert.get(0);
	        String fechaCorteFin = (String) firstRow[2];
	        insertarEnBaseDeDatosEtapas(dataToInsert);
	        insertarDatosDeControlEtapas(idProceso, dataToInsert.size(), fechaCorteFin);
	    }
	}

    private void insertarEnBaseDeDatosEtapas(List<Object[]> dataToInsert) {
        // Usar la constante en lugar de la consulta en línea
        jdbcTemplate.batchUpdate(Constantes.INSERT_CAMBIO_ETAPA, dataToInsert);
    }
    
    private void insertarDatosDeControlEtapas(int idProceso, int totalRecords, String fechaCorteFin) {
        // Ejecutar el SELECT y obtener una lista de resultados
        List<Map<String, Object>> result = jdbcTemplate.queryForList(Constantes.SELECT_CONTROL_ETAPA, fechaCorteFin);

        // Validar si se encontró algún resultado
        if (!result.isEmpty()) {
            // Extraer el primer registro
            Map<String, Object> row = result.get(result.size() - 1);

            // Obtener los valores del registro
            int idProc = (Integer) row.get("PK_IdProcesoComision");

            // Ejecutar el UPDATE con la constante
            jdbcTemplate.update(Constantes.UPDATE_CONTROL_ETAPA, idProc);
        }

        // Definir los valores de control
        String estatusProceso = "A";
        Date fechaProceso = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String fechaProcesoStr = dateFormat.format(fechaProceso);

        // Ejecutar la inserción de control con la constante
        jdbcTemplate.update(Constantes.INSERT_CONTROL_ETAPA, idProceso, totalRecords, fechaProcesoStr, obtUsuario(), estatusProceso, fechaCorteFin);
    }


	private int obtenerSiguienteIdProcesoEtapas() {
	    // Usar la constante en lugar de la consulta en línea
	    return jdbcTemplate.queryForObject(Constantes.SELECT_NEXT_ID_PROCESO, Integer.class);
	}

}
