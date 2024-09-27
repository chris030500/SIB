package ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model.ConfigAdviserBC;
import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.model.ConfiguracionProductosConceptos;
import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.repository.IConfigAdviserBC;
import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.repository.IConfiguracionProductosConceptos;
import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.repository.IDetalleFactura;
import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.repository.IProcesosHistCostoNetoFinanciado;
import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.repository.IProductosCreditoConf;
import ahorrosbienestar.sib.std.contabilidad.costonetofinanciado.service.ProductosCreditoService;
import ahorrosbienestar.sib.std.ofertasAutomaticas.consultaInfo.dto.ArchivoOfertasAutomaticas;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("/contabilidad")
public class CostoNetoFinanciadoController {
	
	private static final String ERROR_PROCESAR_ARCHIVO = "Error al procesar el archivo: ";
	
	private static final Logger logger = Logger.getLogger(CostoNetoFinanciadoController.class);

	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;

	@Autowired
	private IUsuarioRepository iUsuarioRepository;

	@Autowired
	private OpcionesMenuService opcionesMenuService;

	@Autowired
	private IProcesosHistCostoNetoFinanciado iProcesosHistCostoNetoFinanciado;

	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;

	@Autowired
	private IReporteRepository iReporteRepository;

	@Autowired
	private IConfigAdviserBC iConfigAdviserBC;

	@Autowired
	private IProductosCreditoConf iProductosCreditoConf;

	@Autowired
	private ICatalogoRepository iCatalogoRepository;

	@Autowired
	private IDetalleFactura iDetalleFactura;

	@Autowired
	private ProductosCreditoService productosCreditoService;

	@Autowired
	private ConfigService configService;
	@Autowired
	private IConfiguracionProductosConceptos iConfiguracionProductosConceptos;

	@Autowired
	JdbcTemplate jdbcTemplate;

	List<Usuario> usuario = null;


	@GetMapping("/costoNetoFinanciado")
	public String costoNetoFinanciado(Model model) throws IOException {
		String rutaDireccion = "";
		rutaDireccion = Constantes.CONTABILIDAD_COSTO_NETO_FINANCIADO;
		LocalDate fechaSistema = LocalDate.now();
		try {
			if (validarAcceso(Constantes.CONTABILIDAD_COSTO_NETO_FINANCIADO) == 1) {
				ObjectMapper objectMapper = new ObjectMapper();
				String listaHistProceso = objectMapper
						.writeValueAsString(iProcesosHistCostoNetoFinanciado.obtProcesoCNF());
				String listaBCJson = objectMapper.writeValueAsString(iProductosCreditoConf.findAll());
				

				model.addAttribute(Constantes.PRM_LISTA_PROCESOS, listaHistProceso);
				model.addAttribute("fechaSistema", fechaSistema);
				model.addAttribute("fechaCierre", iProcesosHistCostoNetoFinanciado.obtFechaCierre());
				model.addAttribute("lista_BC", listaBCJson);
				model.addAttribute("cat_productos", productosCreditoService.obtProductos());
				model.addAttribute("cat_conceptos", iCatalogoRepository.generarOpciones(Constantes.CAT_CONCEPTOS));
				model.addAttribute("cat_costo_unitario", iCatalogoRepository.generarOpciones(Constantes.CAT_COSTOS));
				model.addAttribute("costoBuro", iCatalogoRepository.obtDescripcion(Constantes.CAT_COSTOS, 1));
				model.addAttribute("costoAdviser", iCatalogoRepository.obtDescripcion(Constantes.CAT_COSTOS, 2));

			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			return null;
		}

	}

	@GetMapping("/listaArchivosFactura")
	public ResponseEntity<List<ArchivoOfertasAutomaticas>> listFilesInFolder() throws IOException {

		try {
			List<Configuracion> bases = configService.obtenerUrlFactura();
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
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			return null;
		}
	}

	@GetMapping("/listaArchivosComisiones")
	public ResponseEntity<List<ArchivoOfertasAutomaticas>> listFilesInFolderComisiones() throws IOException {

		try {
			List<Configuracion> bases = configService.obtenerUrlComisiones();
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
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			return null;
		}
	}

	@PostMapping("/subirArchivoFactura")
	public ResponseEntity<?> adjuntar(@RequestParam("file") MultipartFile[] files,
			@RequestParam("idFolio") String idFolio) {

		String mensaje = "";
		ArrayList<String> respuesta = new ArrayList<>();
		List<Configuracion> bases = configService.obtenerUrlFactura();
		try {
			if (files.length > 0) {
				for (MultipartFile file : files) {
					byte[] bytes = file.getBytes();
					String folderPath = bases.get(0).getVc_valor_propiedad();

					Path nuevoPath = Paths.get(folderPath + file.getOriginalFilename());
					Files.write(nuevoPath, bytes);

					mensaje = Constantes.MSN_OK_LAYOUT;
					logger.info(mensaje);
				}
			} else {
				mensaje = Constantes.MSN_KO_LAYOUT;
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

	@PostMapping("/subirArchivoComisiones")
	public ResponseEntity<?> adjuntarComisiones(@RequestParam("file") MultipartFile[] files,
			@RequestParam("idFolio") String idFolio) {

		String mensaje = "";
		ArrayList<String> respuesta = new ArrayList<>();
		List<Configuracion> bases = configService.obtenerUrlComisiones();
		try {
			if (files.length > 0) {
				for (MultipartFile file : files) {
					byte[] bytes = file.getBytes();
					String folderPath = bases.get(0).getVc_valor_propiedad();

					Path nuevoPath = Paths.get(folderPath + file.getOriginalFilename());
					Files.write(nuevoPath, bytes);

					mensaje = Constantes.MSN_OK_LAYOUT;
					logger.info(mensaje);
				}
			} else {
				mensaje = Constantes.MSN_KO_LAYOUT;
				logger.info(mensaje);
			}
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			mensaje = e.getMessage();
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		}
	}
	private Map<String, Object> ejecutarProcesoVoBo() {
	    Map<String, Object> result = new HashMap<>();
	    Connection connection = null;
	    CallableStatement callableStatement = null;
	    String errorMessage = null;
	    int estatusProceso = 0;

	    try {
	        connection = jdbcTemplate.getDataSource().getConnection();
	        callableStatement = connection.prepareCall(Constantes.PROCESO_VOBO_OK);
	        callableStatement.setInt(1, obtUsuario());
	        callableStatement.registerOutParameter(2, Types.VARCHAR);
	        callableStatement.registerOutParameter(3, Types.INTEGER);
	        callableStatement.execute();
	        errorMessage = callableStatement.getString(2);
	        estatusProceso = callableStatement.getInt(3);
	    } catch (SQLException e) {
	        logger.error(Constantes.ERROR_LOG_PROCESO + e.getMessage());
	        errorMessage = Constantes.ERROR_LOG_PROCESO + e.getMessage();
	    } finally {
	    	 closeResources(callableStatement, connection);
	    }

	    result.put(Constantes.PRM_ERROR_MENSAJE, errorMessage);
	    result.put(Constantes.PRM_ESTATUSPROCESO, estatusProceso);
	    return result;
	}

	@PostMapping("/guardar-voboProceso")
	public ResponseEntity<Map<String, Object>> guardarVoBo(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		try {
			 
	        Map<String, Object> procesoResult = ejecutarProcesoVoBo();
	        String errorMessage = (String) procesoResult.get(Constantes.PRM_ERROR_MENSAJE);
	        int estatusProceso = (int) procesoResult.get(Constantes.PRM_ESTATUSPROCESO);

	        // Convertir lista de procesos a JSON
	        ObjectMapper objectMapper = new ObjectMapper();
	        String listaHistProceso = objectMapper.writeValueAsString(iProcesosHistCostoNetoFinanciado.obtProcesoCNF());

	        // Poner datos en la respuesta
	        response.put(Constantes.PRM_MESSAGE, (errorMessage == null) ? Constantes.VOBO_OK : errorMessage);
	        response.put(Constantes.PRM_ESTATUSPROCESO, estatusProceso);
	        response.put(Constantes.PRM_LISTA_PROCESOS, listaHistProceso);
	        return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_GUARDAR + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	/**
	 * 
	 * @return
	 */
	private Map<String, Object> executeCostoNetoFinanciadoProcedure() {
	    Map<String, Object> response = new HashMap<>();
	    Connection connection = null;
	    CallableStatement callableStatement = null;
	    String errorMessage = null;
	    int estatusProceso = 0;
	    
	    try {
	        connection = jdbcTemplate.getDataSource().getConnection();
	        callableStatement = connection.prepareCall(Constantes.PROCESO_COSTO_NETO_FINANCIADO);
	        callableStatement.setInt(1, obtUsuario());
	        callableStatement.registerOutParameter(2, Types.VARCHAR);
	        callableStatement.registerOutParameter(3, Types.INTEGER);
	        callableStatement.execute();
	        errorMessage = callableStatement.getString(2);
	        estatusProceso = callableStatement.getInt(3);
	    } catch (SQLException e) {
	        logger.error(Constantes.ERROR_LOG_PROCESO + e.getMessage());
	        errorMessage = Constantes.ERROR_LOG_PROCESO + e.getMessage();
	    } finally {
	        closeResources(callableStatement, connection);
	    }

	    response.put(Constantes.PRM_MESSAGE, (errorMessage == null) ? Constantes.PROCESO_CNF_OK : errorMessage);
	    response.put(Constantes.PRM_ESTATUSPROCESO, estatusProceso);
	    return response;
	}
/**
 * 
 * @param callableStatement
 * @param connection
 */
	private void closeResources(CallableStatement callableStatement, Connection connection) {
	    if (callableStatement != null) {
	        try {
	            callableStatement.close();
	        } catch (SQLException e) {
	            logger.error(Constantes.ERROR_BD_CERRAR + e.getMessage());
	        }
	    }
	    if (connection != null) {
	        try {
	            connection.close();
	        } catch (SQLException e) {
	            logger.error(Constantes.ERROR_BD_CON + e.getMessage());
	        }
	    }
	}
	

	@PostMapping("/procesar-costoNetoFinanciado")
	public ResponseEntity<Map<String, Object>> procesarCostoNetoFinaciado(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		try {
			Map<String, Object> dbResponse = executeCostoNetoFinanciadoProcedure();
	        response.putAll(dbResponse);
	        return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_GUARDAR + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/reporte-costoNetoFinanciado")
	public ResponseEntity<Map<String, Object>> reporteCostoNetoFinaciado(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		try {
			LocalDate fechaActual = LocalDate.now();
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_COSTO_NETO_FINANCIADO);

			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
					+ datosReporte.getPk_id_reporte() + "&parametros=" + "&tipoDocumento="
					+ datosReporte.getVc_extension() + "&identificadorDoc=" + fechaActual;

			response.put(Constantes.PRM_URL_REPORTE, reporteURL);
			response.put(Constantes.PRM_MESSAGE, (reporteURL.length() > 0) ? Constantes.REPORTE_OK : Constantes.REPORTE_KO);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_GUARDAR + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/reporte-costoNetoFinanciadoHist")
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
			ReporteJasper datosReporte = iReporteRepository
					.buscarReporte(Constantes.REPORTE_COSTO_NETO_FINANCIADO_HIST);

			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
					+ datosReporte.getPk_id_reporte() + Constantes.REPORTE_HIST + tipoProceso + "&tipoDocumento="
					+ datosReporte.getVc_extension() + "&identificadorDoc=" + fechaActual;

			response.put(Constantes.PRM_URL_REPORTE, reporteURL);
			response.put(Constantes.PRM_MESSAGE, (reporteURL.length() > 0) ? Constantes.REPORTE_OK : Constantes.REPORTE_KO);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_GUARDAR + e.getMessage());
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
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			return 0;
		}

	}

	/**
	 * Pantalla Costos
	 * 
	 * @return
	 */

	@PostMapping("/guardar-Costos")
	public ResponseEntity<Map<String, Object>> guardarCostos(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();

		try {
			// Crear un NumberFormat para la localización deseada
			NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
			//
			Number costoBC = format.parse(params.get("costo_unitario_BC"));
			Number costoAdviser = format.parse(params.get("costo_unitario_Adviser"));
			String costoBuro = costoBC.toString();
			String costoAd = costoAdviser.toString();

			if (iCatalogoRepository.guardarCosto(costoBuro, Constantes.CAT_COSTOS, 1) == 1
					&& iCatalogoRepository.guardarCosto(costoAd, Constantes.CAT_COSTOS, 2) == 1) {
				response.put("costoBC", iCatalogoRepository.obtDescripcion(Constantes.CAT_COSTOS, 1));
				response.put("costoAdviser", iCatalogoRepository.obtDescripcion(Constantes.CAT_COSTOS, 2));
				response.put("catcostounitario", iCatalogoRepository.generarOpciones(Constantes.CAT_COSTOS));

				response.put(Constantes.PRM_MESSAGE, Constantes.MSN_GUARDAR_OK);
				

			} 
			return ResponseEntity.ok(response);

		} catch (NumberFormatException e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_GUARDAR + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/guardar-CatalogoBC")
	public ResponseEntity<Map<String, Object>> guardarCatalogoBC(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		try {
			ConfigAdviserBC configAdviserBC = new ConfigAdviserBC();

			// Crear un NumberFormat para la localización deseada
			NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

			// Parsear el monto inicial y final

			Number montoInicial = format.parse(params.get(Constantes.PRM_MONTOINICIAL));
			Number montoFinal = format.parse(params.get(Constantes.PRM_MONTOFINAL));

			int existe = iConfigAdviserBC.existeConfiguracion(montoInicial.doubleValue(), montoFinal.doubleValue(),
					params.get(Constantes.PRM_PRODUCTOS), Integer.parseInt(params.get(Constantes.PRM_CONCEPTOS)));
			if (existe == 0) {

				// si es igual a 0 es un alta si es mayor a 0 es una actualizacion.
				if (Integer.parseInt(params.get(Constantes.PRM_GASTOID)) > 0) {
					Optional<ConfigAdviserBC> obtConfiguracion = iConfigAdviserBC.findById(Integer.parseInt(params.get(Constantes.PRM_GASTOID)));
					obtConfiguracion.get().setConcepto(Integer.parseInt(params.get(Constantes.PRM_CONCEPTOS)));
					obtConfiguracion.get().setDescripcionConcepto(iCatalogoRepository
							.obtDescripcion(Constantes.CAT_CONCEPTOS, Integer.parseInt(params.get(Constantes.PRM_CONCEPTOS))));
					obtConfiguracion.get().setProductos(params.get(Constantes.PRM_PRODUCTOS));
					obtConfiguracion.get()
							.setDescripcionProducto(productosCreditoService.descProducto(params.get(Constantes.PRM_PRODUCTOS)));
					obtConfiguracion.get().setMontoInicial(montoInicial.doubleValue());
					obtConfiguracion.get().setMontoFinal(montoFinal.doubleValue());
					configAdviserBC = iConfigAdviserBC.save(obtConfiguracion.get());

					if (configAdviserBC != null && configAdviserBC.getId() != 0) {
						response.put(Constantes.PRM_LISTACATALOGO, iConfigAdviserBC.findAll());
						response.put(Constantes.PRM_MESSAGE, Constantes.MSN_ACTUALIZAR_INFO);
						
					}

				} else {
					configAdviserBC.setConcepto(Integer.parseInt(params.get(Constantes.PRM_CONCEPTOS)));
					configAdviserBC.setDescripcionConcepto(iCatalogoRepository.obtDescripcion(Constantes.CAT_CONCEPTOS,
							Integer.parseInt(params.get(Constantes.PRM_CONCEPTOS))));
					configAdviserBC.setProductos(params.get(Constantes.PRM_PRODUCTOS));
					configAdviserBC
							.setDescripcionProducto(productosCreditoService.descProducto(params.get(Constantes.PRM_PRODUCTOS)));
					configAdviserBC.setMontoInicial(montoInicial.doubleValue());
					configAdviserBC.setMontoFinal(montoFinal.doubleValue());
					configAdviserBC.setEstatus(Constantes.ESTATUS_BC);
					ConfigAdviserBC guardaConfigAdviserBC = iConfigAdviserBC.save(configAdviserBC);

					if (guardaConfigAdviserBC != null && guardaConfigAdviserBC.getId() != 0) {
						response.put( Constantes.PRM_LISTACATALOGO, iConfigAdviserBC.findAll());
						response.put(Constantes.PRM_MESSAGE, Constantes.MSN_ACTUALIZAR_INFO);
						
					} 

				}
				return ResponseEntity.ok(response);
			} else {
				response.put(Constantes.PRM_LISTACATALOGO, iConfigAdviserBC.findAll());
				response.put(Constantes.PRM_MESSAGE, Constantes.MSN_PARAMS_INFO);
				return ResponseEntity.ok(response);
			}
		} catch (NumberFormatException e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_GUARDAR + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/guardar-ConfiguracionGasto")
	public ResponseEntity<Map<String, Object>> guardarConfiguracion(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();

		try {
			int codProd = Integer.parseInt(params.get(Constantes.PRM_CONF_PROD));
			int estatus = 1;
			
			Date fechaProceso = new Date();
		

			// guardar los datos que existen
			Set<Integer> conceptosEnFormulario = new HashSet<>();
			 // Iterar sobre las entradas del Map
	        for (Map.Entry<String, String> entry : params.entrySet()) {
	        	
	        	  String key = entry.getKey();
		          String value = entry.getValue();
	        	 
	            if (!key.equals(Constantes.PRM_CONF_PROD) && !key.equals(Constantes.PRM_TBL_CONF) && !key.equals(Constantes.PRM_TBL_TOTAL)) {
	            	 int conceptoFormulario = Integer.parseInt(value);
	                 conceptosEnFormulario.add(conceptoFormulario); // Agregar concepto al Set
	            	
	            	ConfiguracionProductosConceptos registro = iConfiguracionProductosConceptos.existeRegistro(codProd,Integer.parseInt(value));
	            	//si es null es alta
	            	if(registro == null) {
	            		ConfiguracionProductosConceptos configuracion = new ConfiguracionProductosConceptos();
		            	configuracion.setProducto(codProd);
		            	configuracion.setConcepto(Integer.parseInt(value));
		            	configuracion.setUsuarioregistra(obtUsuario());
		            	configuracion.setFecharegistro(fechaProceso);
		            	iConfiguracionProductosConceptos.save(configuracion);
	            	}else {
	            		// si no es null es modificacion
	            		registro.setEstatus(estatus);
	            		registro.setUsuarioregistra(obtUsuario());
	            		registro.setFecharegistro(fechaProceso);
	            		iConfiguracionProductosConceptos.save(registro);
	            	}	            	
	            }
	           
	        }
			List<ConfiguracionProductosConceptos> registros =iConfiguracionProductosConceptos.obtenerXproducto(codProd);
	        // Después de iterar sobre los datos del formulario, iterar sobre la lista de registros existentes
	        for (ConfiguracionProductosConceptos registro : registros) {
	        	if (!conceptosEnFormulario.contains(registro.getConcepto())) {
	                // Si el concepto no está en el formulario, actualizar el estatus a 2
	                registro.setEstatus(2);
	                registro.setUsuarioregistra(obtUsuario());
            		registro.setFecharegistro(fechaProceso);
	                iConfiguracionProductosConceptos.save(registro);
	            }
	        }
			
	        List<ConfiguracionProductosConceptos> listaActualizada =iConfiguracionProductosConceptos.obtenerXproducto(codProd);
	        
	        
	        response.put(Constantes.PRM_LISTA_CONFIGURACION_CONCEPTOS, listaActualizada);
            response.put(Constantes.PRM_MESSAGE, Constantes.MSN_ACTUALIZAR_INFO);
            return ResponseEntity.ok(response);
		 } catch (NumberFormatException e) {
				logger.error(Constantes.ERROR_LOG+e.getMessage());
				response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_FORMATO_NUMERICO+ e.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			 } catch (IllegalArgumentException e) {
				 logger.error(Constantes.ERROR_LOG+e.getMessage());
				 response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_PARAMETROS + e.getMessage());
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			 } catch (Exception e) {
				 logger.error(Constantes.ERROR_LOG+e.getMessage());
				 response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_GUARDAR + e.getMessage());
				 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			 }
	}

	@PostMapping("/obtener-gastos")
	public ResponseEntity<Map<String, Object>> obtGastos(@RequestBody Map<String, Object> data) {
		Map<String, Object> response = new HashMap<>();
		try {
			
			// Obtén el valor como Object
	        Object procesoIdObj = data.get(Constantes.PRM_IDENTIFICADOR);
	        Integer tipoProceso = null;
	        
	        // Verifica si procesoIdObj es una instancia de Number
	        if (procesoIdObj instanceof Number) {
	            tipoProceso = ((Number) procesoIdObj).intValue();
	        } else if (procesoIdObj != null) {
	            // Convierte a Integer si no es una instancia de Number
	            tipoProceso = Integer.parseInt(procesoIdObj.toString());
	        }
			
			
			List<ConfiguracionProductosConceptos> listaConfiguracionProducto =iConfiguracionProductosConceptos.obtenerXproducto(tipoProceso);

	        response.put(Constantes.PRM_LISTA_PROD, listaConfiguracionProducto);
	        response.put(Constantes.PRM_LISTA_DETFACTURA, iDetalleFactura.obtDetalleFacturaMod());
            return ResponseEntity.ok(response);
	
		 } catch (NumberFormatException e) {
				logger.error(Constantes.ERROR_LOG+e.getMessage());
				response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_FORMATO_NUMERICO+ e.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			 } catch (IllegalArgumentException e) {
				 logger.error(Constantes.ERROR_LOG+e.getMessage());
				 response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_PARAMETROS + e.getMessage());
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			 } catch (Exception e) {
				 logger.error(Constantes.ERROR_LOG+e.getMessage());
				 response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_GUARDAR + e.getMessage());
				 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			 }
	}

	@PostMapping("/procesar-prorrateo")
	public ResponseEntity<Map<String, Object>> procesarProrrateo(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		try {
			Map<String, Object> resultadoProrrateo = ejecutarProrrateo();
	        response.put(Constantes.PRM_ESTATUSPROCESO, resultadoProrrateo.get(Constantes.PRM_ESTATUSPROCESO));
	        response.put(Constantes.PRM_MESSAGE, resultadoProrrateo.get(Constantes.PRM_MESSAGE));
	        return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (IllegalArgumentException e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_PARAMETROS + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			response.put(Constantes.PRM_MESSAGE, Constantes.ERROR_GUARDAR + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	private Map<String, Object> ejecutarProrrateo() {
	    Map<String, Object> resultado = new HashMap<>();
	    Connection connection = null;
	    CallableStatement callableStatement = null;
	    String errorMessage = null;
	    int estatusProceso = 0;

	    try {
	        connection = jdbcTemplate.getDataSource().getConnection();
	        callableStatement = connection.prepareCall(Constantes.PROCESO_PRORRATEO);
	        callableStatement.setInt(1, obtUsuario());
	        callableStatement.registerOutParameter(2, Types.VARCHAR);
	        callableStatement.registerOutParameter(3, Types.INTEGER);
	        callableStatement.execute();
	        errorMessage = callableStatement.getString(2);
	        estatusProceso = callableStatement.getInt(3);
	    } catch (SQLException e) {
	        logger.error(Constantes.ERROR_LOG_PROCESO + e.getMessage());
	        errorMessage = Constantes.ERROR_LOG_PROCESO + e.getMessage();
	    } finally {
	    	 closeResources(callableStatement, connection);
	    }

	    resultado.put(Constantes.PRM_ESTATUSPROCESO, estatusProceso);
	    resultado.put(Constantes.PRM_MESSAGE, (errorMessage == null) ? Constantes.PRORRATEO : errorMessage);
	    return resultado;
	}

	public int obtUsuario() {

		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return usuario.get(0).getPk_id_usuario();

		} catch (Exception e) {
			logger.error(Constantes.ERROR_LOG + e.getMessage());
			return 0;
		}

	}

	@PostMapping("/procesarArchivoFactura")
	public ResponseEntity<Map<String, Object>> procesarArchivo(@RequestBody Map<String, String> request) {
		String nombreArchivo = request.get(Constantes.PRM_NOMBRE_ARCHIVO);

		try {
			// Aquí agregas la lógica para procesar el archivo
			procesarYMigrarArchivoFacturas(nombreArchivo);

			Map<String, Object> response = new HashMap<>();
			response.put(Constantes.MENSAJE_OK, true);
			return ResponseEntity.ok(response);
		} catch (IOException e) {
			logger.error(ERROR_PROCESAR_ARCHIVO, e);
			Map<String, Object> response = new HashMap<>();
			response.put(Constantes.MENSAJE_OK, false);
			response.put(Constantes.MENSAJE_ERROR, Constantes.ERROR_PROCESAR_ARCHIVO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			logger.error(ERROR_PROCESAR_ARCHIVO, e);
			Map<String, Object> response = new HashMap<>();
			response.put(Constantes.MENSAJE_OK, false);
			response.put(Constantes.MENSAJE_ERROR, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@PostMapping("/procesarArchivoComisiones")
	public ResponseEntity<Map<String, Object>> procesarArchivoComisiones(@RequestBody Map<String, String> request) {
		String nombreArchivo = request.get(Constantes.PRM_NOMBRE_ARCHIVO);
		Map<String, Object> response = new HashMap<>();

		if (nombreArchivo.contains("layout_comisiones")) {
			try {
				// Procesar archivo comisiones
				procesarYMigrarArchivoComisiones(nombreArchivo);
				response.put(Constantes.MENSAJE_OK, true);
				return ResponseEntity.ok(response);
			} catch (IOException e) {
				logger.error(ERROR_PROCESAR_ARCHIVO, e);
				response.put(Constantes.MENSAJE_OK, false);
				response.put(Constantes.MENSAJE_ERROR, e.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} catch (Exception e) {
				logger.error(ERROR_PROCESAR_ARCHIVO, e);
				response.put(Constantes.MENSAJE_OK, false);
				response.put(Constantes.MENSAJE_ERROR, e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
		} else {
			try {
				// Procesar archivo etapas
				procesarYMigrarArchivoEtapas(nombreArchivo);
				response.put(Constantes.MENSAJE_OK, true);
				return ResponseEntity.ok(response);
			} catch (IOException e) {
				logger.error(ERROR_PROCESAR_ARCHIVO, e);
				response.put(Constantes.MENSAJE_OK, false);
				response.put(Constantes.MENSAJE_ERROR,  e.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} catch (Exception e) {
				logger.error(ERROR_PROCESAR_ARCHIVO, e);
				response.put(Constantes.MENSAJE_OK, false);
				response.put(Constantes.MENSAJE_ERROR, e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
		}
	}

	public void procesarYMigrarArchivoComisiones(String nombreArchivo) throws IOException {
	    List<Configuracion> bases = configService.obtenerUrlComisiones();
	    String folderPath = bases.get(0).getVc_valor_propiedad();
	    
	    File file = new File(folderPath + nombreArchivo);
	    if (!file.exists()) {
	        throw new IOException("El archivo no existe en la ruta especificada.");
	    }

	    List<String> errores = new ArrayList<>();
	    List<Object[]> dataToInsert = new ArrayList<>();
	    int idProceso = obtenerSiguienteIdProcesoComisiones();
	    
	    try (FileInputStream fis = new FileInputStream(file);
	         Workbook workbook = crearWorkbook(fis, nombreArchivo)) {
	        
	        Sheet sheet = workbook.getSheetAt(0);
	        procesarFilas(sheet, dataToInsert, errores, idProceso);
	        
	        if (!errores.isEmpty()) {
	            throw new RuntimeException("Errores encontrados en el archivo:\n" + String.join("\n", errores));
	        }
	        
	        if (!dataToInsert.isEmpty()) {
	            String fechaCorteFin = (String) dataToInsert.get(0)[3];
	            insertarEnBaseDeDatosComisiones(dataToInsert);
	            insertarDatosDeControlComisiones(idProceso, dataToInsert.size(), fechaCorteFin);
	        }
	    } catch (Exception e) {
	        throw e; // Lanza la excepción para manejarla en otro lugar
	    }
	}

	private Workbook crearWorkbook(FileInputStream fis, String nombreArchivo) throws IOException {
	    if (nombreArchivo.endsWith("xlsx")) {
	        return new XSSFWorkbook(fis);
	    } else if (nombreArchivo.endsWith("xls")) {
	        return new HSSFWorkbook(fis);
	    } else {
	        throw new IOException("Formato de archivo no soportado. Solo se aceptan archivos .xlsx y .xls.");
	    }
	}

	private void procesarFilas(Sheet sheet, List<Object[]> dataToInsert, List<String> errores, int idProceso) {
	    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	        Row row = sheet.getRow(i);
	        if (row == null) {
	            errores.add("Fila " + (i + 1) + ": La fila está vacía.");
	            continue;
	        }

	        try {
	            Object[] valores = leerValoresFila(row, i, errores);
	            if (valores != null) {
	                dataToInsert.add(new Object[] { valores[0], valores[1], valores[2], valores[3], idProceso });
	            }
	        } catch (Exception e) {
	            errores.add(String.format("Error en la fila %d: %s", i + 1, e.getMessage()));
	        }
	    }
	}

	private Object[] leerValoresFila(Row row, int filaIndex, List<String> errores) {
	    BigInteger valor1 = leerCeldaNumerica(row.getCell(0), "A", filaIndex, errores);
	    float valor2 = leerCeldaNumericaFloat(row.getCell(1), "B", filaIndex, errores);
	    float valor3 = leerCeldaNumericaFloat(row.getCell(2), "C", filaIndex, errores);
	    String valor4 = leerCeldaFecha(row.getCell(3), filaIndex, errores);
	    
	    if (valor1 == null || valor2 == -1 || valor3 == -1 || valor4 == null) {
	        return null; // Hay errores
	    }
	    return new Object[] { valor1, valor2, valor3, valor4 };
	}

	private BigInteger leerCeldaNumerica(Cell cell, String columna, int filaIndex, List<String> errores) {
	    if (cell != null && cell.getCellType() == CellType.NUMERIC) {
	        return BigInteger.valueOf((long) cell.getNumericCellValue());
	    } else {
	        errores.add("Fila " + (filaIndex + 1) + ", Columna " + columna + ": Valor no válido (debe ser numérico).");
	        return null;
	    }
	}

	private float leerCeldaNumericaFloat(Cell cell, String columna, int filaIndex, List<String> errores) {
	    if (cell != null && cell.getCellType() == CellType.NUMERIC) {
	        return (float) cell.getNumericCellValue();
	    } else {
	        errores.add("Fila " + (filaIndex + 1) + ", Columna " + columna + ": Valor no válido (debe ser numérico).");
	        return -1; // Indica error
	    }
	}

	private String leerCeldaFecha(Cell cell, int filaIndex, List<String> errores) {
	    if (cell != null && cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
	        Date date = cell.getDateCellValue();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        return localDate.format(formatter);
	    } else {
	        errores.add("Fila " + (filaIndex + 1) + ", Columna D: Valor no válido (debe ser una fecha).");
	        return null; // Indica error
	    }
	}

	// Método para insertar los datos en la base de datos usando batch insert con
	// JdbcTemplate
	private void insertarEnBaseDeDatosComisiones(List<Object[]> dataToInsert) {
	    // Usar la constante para la consulta
	    jdbcTemplate.batchUpdate(Constantes.INSERTAR_COMISIONES, dataToInsert);
	}

	// Método para insertar datos de control en la tabla de control
	private void insertarDatosDeControlComisiones(int idProceso, int totalRecords, String fechaCorteFin) {

	    // Usar la constante para la consulta
	    List<Map<String, Object>> result = jdbcTemplate.queryForList(Constantes.SELECT_CONTROL_COMISIONES, fechaCorteFin);

	    // Validar si se encontró algún resultado
	    if (!result.isEmpty()) {
	        Map<String, Object> row = result.get(result.size() - 1);
	        int idProc = (Integer) row.get("PK_IdProcesoComision");

	        // Usar la constante para la consulta de actualización
	        jdbcTemplate.update(Constantes.UPDATE_CONTROL_COMISIONES, idProc);

	        // Definir los valores de control
	        String estatusProceso = "A";
	        Date fechaProceso = new Date();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        String fechaProcesoStr = dateFormat.format(fechaProceso);

	        // Usar la constante para la inserción de control
	        jdbcTemplate.update(Constantes.INSERTAR_CONTROL_COMISIONES, idProceso, totalRecords, fechaProcesoStr, obtUsuario(), estatusProceso, fechaCorteFin);

	    } else {
	        String estatusProceso = "A";
	        Date fechaProceso = new Date();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        String fechaProcesoStr = dateFormat.format(fechaProceso);

	        // Usar la constante para la inserción de control
	        jdbcTemplate.update(Constantes.INSERTAR_CONTROL_COMISIONES, idProceso, totalRecords, fechaProcesoStr, obtUsuario(), estatusProceso, fechaCorteFin);
	    }
	}

	// Método para obtener el siguiente ID de proceso
	private int obtenerSiguienteIdProcesoComisiones() {
	    return jdbcTemplate.queryForObject(Constantes.OBTENER_SIGUIENTE_ID_PROCESO_COMISIONES, Integer.class);
	}


	public void procesarYMigrarArchivoEtapas(String nombreArchivo) throws IOException {
		
		List<Configuracion> bases = configService.obtenerUrlComisiones();
		String folderPath = bases.get(0).getVc_valor_propiedad();
		
		File file = new File(folderPath + nombreArchivo);
		String fechaCorteFin = "";
		List<String> errores = new ArrayList<>(); // Para almacenar errores

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
				List<Object[]> dataToInsert = new ArrayList<>();
				int totalRecords = 0;
				int idProceso = obtenerSiguienteIdProcesoEtapas();

				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						errores.add("Fila " + (i + 1) + ": La fila está vacía.");
						continue;
					}

					try {
						// Obtener el valor de la primera celda (BigInteger)
						Cell cell0 = row.getCell(0);
						BigInteger valor1 = BigInteger.ZERO;
						if (cell0 != null && cell0.getCellType() == CellType.NUMERIC) {
							valor1 = BigInteger.valueOf((long) cell0.getNumericCellValue());
						} else {
							errores.add("Fila " + (i + 1) + ", Columna A: Valor no válido (debe ser numérico).");
							continue;
						}

						// Obtener el valor de la segunda celda (int)
						Cell cell1 = row.getCell(1);
						int valor2 = 0;
						if (cell1 != null && cell1.getCellType() == CellType.NUMERIC) {
							valor2 = (int) cell1.getNumericCellValue();
						} else {
							errores.add("Fila " + (i + 1) + ", Columna B: Valor no válido (debe ser numérico).");
							continue;
						}

						// Obtener el valor de la tercera celda (fecha)
						Cell cell2 = row.getCell(2);
						String valor3 = "";
						if (cell2 != null && cell2.getCellType() == CellType.NUMERIC
								&& DateUtil.isCellDateFormatted(cell2)) {
							Date date = cell2.getDateCellValue();
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
							LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							valor3 = localDate.format(formatter);
						} else {
							errores.add("Fila " + (i + 1) + ", Columna C: Valor no válido (debe ser una fecha).");
							continue;
						}

						int valor4 = idProceso;

						// Acumulamos los datos para el batch
						dataToInsert.add(new Object[] { valor1, valor2, valor3, valor4 });
						totalRecords++;
					} catch (Exception e) {
						errores.add("Error en la fila " + (i + 1) + ": " + e.getMessage());
					}
				}

				// Verifica si hay errores
				if (!errores.isEmpty()) {
					throw new RuntimeException("Errores encontrados en el archivo:\n" + String.join("\n", errores));
				}

				if (!dataToInsert.isEmpty()) {
					Object[] firstRow = dataToInsert.get(0);
					fechaCorteFin = (String) firstRow[2];

					// Insertar los datos en la base de datos
					insertarEnBaseDeDatosEtapas(dataToInsert);

					// Insertar datos de control en la tabla de control
					insertarDatosDeControlEtapas(idProceso, totalRecords, fechaCorteFin);
				}
			} finally {
				if (workbook != null) {
					workbook.close();
				}
			}
		} catch (Exception e) {
			throw e; // Lanza la excepción para manejarla en otro lugar
		}
	}

	// Método para insertar los datos en la base de datos usando batch insert con
	// JdbcTemplate
	private void insertarEnBaseDeDatosEtapas(List<Object[]> dataToInsert) {
        // Usar la constante en lugar de la consulta en línea
        jdbcTemplate.batchUpdate(Constantes.INSERT_CAMBIO_ETAPA, dataToInsert);
    }

	// Método para insertar datos de control en la tabla de control
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaProcesoStr = dateFormat.format(fechaProceso);

        // Ejecutar la inserción de control con la constante
        jdbcTemplate.update(Constantes.INSERT_CONTROL_ETAPA, idProceso, totalRecords, fechaProcesoStr, obtUsuario(), estatusProceso, fechaCorteFin);
    }


	private int obtenerSiguienteIdProcesoEtapas() {
	    // Usar la constante en lugar de la consulta en línea
	    return jdbcTemplate.queryForObject(Constantes.SELECT_NEXT_ID_PROCESO, Integer.class);
	}

	public void procesarYMigrarArchivoFacturas(String nombreArchivo) throws IOException {

	    try {
	        List<Object[]> dataToInsert = new ArrayList<>();

	        // Inicializa el DocumentBuilder
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();

	        // Carga el archivo XML
	        List<Configuracion> bases = configService.obtenerUrlFactura();
	        String folderPath = bases.get(0).getVc_valor_propiedad();

	        File file = new File(folderPath + nombreArchivo);
	        Document document = builder.parse(file);

	        // Normaliza el documento (remueve nodos vacíos y espacios)
	        document.getDocumentElement().normalize();

	        // Obtiene el elemento raíz
	        Element root = document.getDocumentElement();

	        // Obtiene los nodos del "Comprobante"
	        NodeList comprobanteList = document.getElementsByTagName("cfdi:Comprobante");

	        // Itera sobre los nodos de "Comprobante"
	        for (int i = 0; i < comprobanteList.getLength(); i++) {
	            Node comprobanteNode = comprobanteList.item(i);

	            if (comprobanteNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element comprobanteElement = (Element) comprobanteNode;

	                String folio = comprobanteElement.getAttribute("Folio");
	                String fecha = comprobanteElement.getAttribute("Fecha");
	                String descuento = comprobanteElement.getAttribute("Descuento");
	                String total = comprobanteElement.getAttribute("Total");

	                // Emisor
	                Element emisorElement = (Element) comprobanteElement.getElementsByTagName("cfdi:Emisor").item(0);
	                String emisorRFC = emisorElement.getAttribute("Rfc");
	                String emisorNombre = emisorElement.getAttribute("Nombre");

	                // Receptor
	                Element receptorElement = (Element) comprobanteElement.getElementsByTagName("cfdi:Receptor").item(0);
	                String receptorRFC = receptorElement.getAttribute("Rfc");
	                String receptorNombre = receptorElement.getAttribute("Nombre");

	                // Insertar en TBL_DETALLE_FACTURA usando la constante de consulta
	                jdbcTemplate.update(Constantes.INSERT_DETALLE_FACTURA, folio, fecha, descuento, total, emisorRFC, emisorNombre, receptorRFC, receptorNombre);

	                // Lee los conceptos
	                NodeList conceptosList = comprobanteElement.getElementsByTagName("cfdi:Concepto");
	                for (int j = 0; j < conceptosList.getLength(); j++) {
	                    Element conceptoElement = (Element) conceptosList.item(j);
	                    String concepto = conceptoElement.getAttribute("NoIdentificacion");
	                    String cantidad = conceptoElement.getAttribute("Cantidad");
	                    String descripcion = conceptoElement.getAttribute("Descripcion");
	                    String oi = conceptoElement.getAttribute("ObjetoImp");
	                    String um = conceptoElement.getAttribute("ClaveUnidad");
	                    String punit = conceptoElement.getAttribute("ValorUnitario");
	                    String subtotalStr = conceptoElement.getAttribute("Importe");
	                    String descStr = conceptoElement.getAttribute("Descuento");

	                    // Convertir las cadenas a números
	                    double subtotal = Double.parseDouble(subtotalStr.isEmpty() ? "0.00" : subtotalStr);
	                    double desc = descStr.isEmpty() ? 0.00 : Double.parseDouble(descStr);

	                    // Realizar la resta
	                    double totalConcepto = subtotal - desc;

	                    // Agregar a la lista para TBL_FACTURA
	                    dataToInsert.add(new Object[] { cantidad, concepto, descripcion, oi, um, punit, subtotal, desc, totalConcepto, folio });
	                }
	            }
	        }

	        // Insertar en TBL_FACTURA usando batchUpdate con la constante de consulta
	        jdbcTemplate.batchUpdate(Constantes.INSERT_FACTURA, dataToInsert);

	    } catch (Exception e) {
	    	logger.error(ERROR_PROCESAR_ARCHIVO, e);
	    }
	}


	@PostMapping("/cargarPdf")
	public ResponseEntity<Map<String, String>> uploadPdf(@RequestParam("pdf") MultipartFile file,
			@RequestParam("nombreArchivo") String nombreArchivo) {
		
		List<Configuracion> bases = configService.obtenerUrlPDF();
		String folderPath = bases.get(0).getVc_valor_propiedad();

		String UPLOAD_DIR = folderPath;

		// Si el nombre del archivo termina en .xml, lo cambiamos a .pdf
		if (nombreArchivo.toLowerCase().endsWith(".xml")) {
			nombreArchivo = nombreArchivo.substring(0, nombreArchivo.lastIndexOf(".")) + ".pdf";
		}

		if (file.isEmpty() || !file.getContentType().equals(Constantes.PRM_TIPOCONTENT_PDF)) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put(Constantes.MENSAJE_ERROR,Constantes.MSN_ERROR_ARCHIVO_INCORRECTO_PDF);
			return ResponseEntity.badRequest().body(errorResponse);
		}

		try {
			// Guardar el archivo directamente en la ruta especificada
			Path filePath = Paths.get(UPLOAD_DIR).resolve(nombreArchivo);
			file.transferTo(filePath);

			Map<String, String> successResponse = new HashMap<>();
			successResponse.put(Constantes.PRM_MESSAGE, Constantes.MSN_OK_ARCHIVO);
			return ResponseEntity.ok().body(successResponse);

		} catch (IOException e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put(Constantes.MENSAJE_ERROR, Constantes.MSN_ERROR_CARGA_ARCHIVO);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@GetMapping("/verPdf")
	public ResponseEntity<Resource> viewPdf(@RequestParam("nombreArchivo") String nombreArchivo) {

		List<Configuracion> bases = configService.obtenerUrlPDF();
		String folderPath = bases.get(0).getVc_valor_propiedad();

		String UPLOAD_DIR = folderPath;

		// Si el nombre del archivo termina en .xml, lo cambiamos a .pdf
		if (nombreArchivo.toLowerCase().endsWith(".xml")) {
			nombreArchivo = nombreArchivo.substring(0, nombreArchivo.lastIndexOf(".")) + ".pdf";
		}

		try {
			// Crear la ruta completa al archivo PDF
			Path filePath = Paths.get(UPLOAD_DIR).resolve(nombreArchivo).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists() && resource.isReadable()) {
				// Establecer el encabezado de respuesta para mostrar el archivo en el navegador
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
						.header(HttpHeaders.CONTENT_TYPE, Constantes.PRM_TIPOCONTENT_PDF).body(resource);
			} else {
				// Archivo no encontrado
				return ResponseEntity.notFound().build();
			}
		} catch (IOException e) {
			// Error al manejar el archivo
			return ResponseEntity.internalServerError().build();
		}
	}
}
