package ahorrosbienestar.sib.std.contabilidad.calculoEPRC.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.contabilidad.calculoEPRC.repository.IProcesoHistEPRC;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("/contabilidad")
public class CalculoEPRController {
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

	List<Usuario> usuario = null;

	private String rutaDireccion = "";

	@GetMapping("/calculoEPRC")
	public String calculoEPRC(Model model) throws IOException {
		rutaDireccion = Constantes.CONTABILIDAD_CALCULO_EPRC;
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
			logger.error("Error:" + e.getMessage());
			return null;
		}

	}

	@PostMapping("/procesar-EPRC")
	public ResponseEntity<Map<String, Object>> procesarCostoNetoFinaciado(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		System.out.print("SI");
		try {
			Connection connection = null;
			CallableStatement callableStatement = null;
			String errorMessage = null;
			int estatusProceso = 0;
			System.out.print("SII");
			try {
				System.out.print("SIII");
				connection = jdbcTemplate.getDataSource().getConnection();
				callableStatement = connection.prepareCall(Constantes.PROCESO_EPRC);
				callableStatement.setInt(1, obtUsuario());
				callableStatement.registerOutParameter(2, Types.VARCHAR);
				callableStatement.registerOutParameter(3, Types.INTEGER);
				callableStatement.execute();
				errorMessage = callableStatement.getString(2);
				estatusProceso = callableStatement.getInt(3);
			} catch (SQLException e) {
				System.out.print("Error al ejecutar el procedimiento almacenado: " + e.getMessage());
				logger.error("Error al ejecutar el procedimiento almacenado: " + e.getMessage());
				errorMessage = "Error al ejecutar el procedimiento almacenado: " + e.getMessage();
			} finally {
				System.out.print("SIIII");
				if (callableStatement != null) {
					try {
						callableStatement.close();
					} catch (SQLException e) {
						System.out.print("Error al cerrar el CallableStatement: " + e.getMessage());
						logger.error("Error al cerrar el CallableStatement: " + e.getMessage());
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						System.out.print("Error al cerrar la conexión: " + e.getMessage());
						logger.error("Error al cerrar la conexión: " + e.getMessage());
					}
				}
			}

			System.out.print("FIN");
			System.out.print(errorMessage);
			System.out.print(estatusProceso);
			response.put("message", (errorMessage.isEmpty()) ? Constantes.PROCESO_CNF_OK : errorMessage);
			response.put("estatusProceso", estatusProceso);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put("message", Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
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
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
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
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return usuario.get(0).getPk_id_usuario();

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
			response.put("message", (reporteURL.length() > 0) ? Constantes.REPORTE_OK : Constantes.REPORTE_KO);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put("message", Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/guardar-voboProcesoEPRC")
	public ResponseEntity<Map<String, Object>> guardarVoBo(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		try {
			Connection connection = null;
			CallableStatement callableStatement = null;
			String errorMessage = null;
			int estatusProceso = 0;

			try {
				connection = jdbcTemplate.getDataSource().getConnection();
				callableStatement = connection.prepareCall(Constantes.PROCESO_VOBO_OK_EPRC);
				callableStatement.setInt(1, obtUsuario());
				callableStatement.registerOutParameter(2, Types.VARCHAR);
				callableStatement.registerOutParameter(3, Types.INTEGER);
				callableStatement.execute();
				errorMessage = callableStatement.getString(2);
				estatusProceso = callableStatement.getInt(3);
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

			ObjectMapper objectMapper = new ObjectMapper();
			String listaHistProcesoEPRC = objectMapper.writeValueAsString(iProcesosHistEPRC.obtProcesoEPRC());

			System.out.print(listaHistProcesoEPRC);

			response.put("message", (errorMessage == null) ? Constantes.VOBO_OK : errorMessage);
			response.put("estatusProceso", estatusProceso);
			response.put("listaHistProcesoEPRC", listaHistProcesoEPRC);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put("message", Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
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
			System.out.println("Tipo Proceso: " + tipoProceso);
			LocalDate fechaActual = LocalDate.now();
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_EPRC_HIST);

			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
					+ datosReporte.getPk_id_reporte() + Constantes.REPORTE_HIST + tipoProceso + "&tipoDocumento="
					+ datosReporte.getVc_extension() + "&identificadorDoc=" + fechaActual;

			System.out.println(reporteURL);

			response.put("urlReporte", reporteURL);
			response.put("message", (reporteURL.length() > 0) ? Constantes.REPORTE_OK : Constantes.REPORTE_KO);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put("message", Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/procesarArchivo")
	public ResponseEntity<Map<String, Object>> procesarArchivo(@RequestBody Map<String, String> request) {
		String nombreArchivo = request.get("nombreArchivo");

		try {
			// Aquí agregas la lógica para procesar el archivo
			procesarYMigrarArchivo(nombreArchivo);

			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			return ResponseEntity.ok(response);
		} catch (IOException e) {
			e.printStackTrace();
			Map<String, Object> response = new HashMap<>();
			response.put("success", false);
			response.put("error", "Error al procesar el archivo: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> response = new HashMap<>();
			response.put("success", false);
			response.put("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	public void procesarYMigrarArchivo(String nombreArchivo) throws IOException {
		File file = new File("\\\\10.150.102.63\\std\\Layouts\\" + nombreArchivo);

		if (!file.exists()) {
			throw new IOException("El archivo no existe en la ruta especificada.");
		}

		try (FileInputStream fis = new FileInputStream(file)) {
			Workbook workbook = null;
			try {
				if (nombreArchivo.endsWith("xlsx")) {
					workbook = new XSSFWorkbook(fis);
				} else if (nombreArchivo.endsWith("xls")) {
					workbook = new HSSFWorkbook(fis);
				} else {
					throw new IOException("Formato de archivo no soportado. Solo se aceptan archivos .xlsx y .xls.");
				}

				Sheet sheet = workbook.getSheetAt(0);

				// Acumulación de datos para batch insert
				List<Object[]> dataToInsert = new ArrayList<>();
				int totalRecords = 0;
				int idProceso = obtenerSiguienteIdProceso();

				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}

					// Obtener el valor de la primera celda (BigInteger)
					Cell cell0 = row.getCell(0);
					BigInteger valor1 = BigInteger.ZERO;
					if (cell0 != null && cell0.getCellType() == CellType.NUMERIC) {
						valor1 = BigInteger.valueOf((long) cell0.getNumericCellValue());
					}

					// Obtener el valor de la segunda celda (String)
					Cell cell1 = row.getCell(1);
					String valor2 = "";
					if (cell1 != null && cell1.getCellType() == CellType.STRING) {
						valor2 = cell1.getStringCellValue();
					}

					// Obtener el valor de la tercera celda (float)
					Cell cell2 = row.getCell(2);
					float valor3 = 0;
					if (cell2 != null && cell2.getCellType() == CellType.NUMERIC) {
						valor3 = (float) cell2.getNumericCellValue();
					}

					// Obtener el valor de la cuarta celda (String)
					Cell cell3 = row.getCell(3);
					String valor4 = "";
					if (cell3 != null && cell3.getCellType() == CellType.STRING) {
						valor4 = cell3.getStringCellValue();
					}

					// Obtener el valor de la quinta celda (Date)
					Cell cell4 = row.getCell(4);
					String valor5 = "";
					if (cell4 != null && cell4.getCellType() == CellType.NUMERIC
							&& DateUtil.isCellDateFormatted(cell4)) {
						Date date = cell4.getDateCellValue();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
						valor5 = localDate.format(formatter);
					}
					
					int valor6 = idProceso;

					// Acumulamos los datos para el batch
					dataToInsert.add(new Object[] { valor1, valor2, valor3, valor4, valor5, valor6});
					totalRecords++;
					Object[] firstRow = dataToInsert.get(0); 
					Date fechaCorte = (Date) firstRow[5];
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate localDate = fechaCorte.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
					String fechaCorteFin = localDate.format(formatter);
				}
				
				

				// Insertar los datos en la base de datos
				insertarEnBaseDeDatos(dataToInsert);

				// Insertar datos de control en la tabla de control
				insertarDatosDeControl(idProceso, totalRecords);

			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		}
	}

	// Método para insertar los datos en la base de datos usando batch insert con
	// JdbcTemplate
	private void insertarEnBaseDeDatos(List<Object[]> dataToInsert) {
		String sql = "INSERT INTO TBL_GARANTIA_EPRC (I_NumCredito, VC_TipoGarantia, DC_MontoGarantia, VC_CalidadGarantia, DT_FechaCorte, I_IdProceso) VALUES (?, ?, ?, ?, ?, ?)";

		// Ejecutar el batch insert usando JdbcTemplate
		jdbcTemplate.batchUpdate(sql, dataToInsert);
	}

	// Método para insertar datos de control en la tabla de control
	private void insertarDatosDeControl(int idProceso, int totalRecords) {
		String sql = "INSERT INTO TBL_CONTROL_GARANTIAS (PK_IdProcesoComision, I_NumReg, DT_FechaProceso, I_Usuario, VC_Estatus) VALUES (?, ?, ?, ?, ?)";

		// Generar un ID de proceso único, por ejemplo, usando un UUID o similar
		

		// Definir los valores de control
		String estatusProceso = "A";
		Date fechaProceso = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaProcesoStr = dateFormat.format(fechaProceso);

		// Ejecutar la inserción de control
		jdbcTemplate.update(sql, idProceso, totalRecords, fechaProcesoStr, obtUsuario(), estatusProceso);
	}

	// Método para obtener el siguiente ID de proceso
    private int obtenerSiguienteIdProceso() {
        String sql = "SELECT COALESCE(MAX(PK_IdProcesoComision), 0) + 1 FROM TBL_CONTROL_GARANTIAS";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

}
