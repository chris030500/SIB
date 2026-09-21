package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.controller;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.dto.BasesEbrResponse;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.ProcesoBasesEbr;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.TipoEmpresa;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service.ProcesoBasesEbrService;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service.TipoEmpresaService;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service.BasesEbrService;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service.HistReportesAuditoriaService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("prevencion")
public class RptBasesEbrController {
	
	private static final Logger logger = Logger.getLogger(RptBasesEbrController.class);
	
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
	private ProcesoBasesEbrService procesoBasesEbrService;
	
	@Autowired
	private BasesEbrService basesEbrService;
	
	@Autowired
	private TipoEmpresaService tipoEmpresaService;
	
	@Autowired
	private HistReportesAuditoriaService auditoriaService;
	
	
	@GetMapping("/reporteBasesEBR")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
			
			List<Integer> auditorias = auditoriaService.listaAnioAuditorias();
			String rutaDireccion;
					
			if (validarAcceso() == 1) {
				model.addAttribute("lista_anio_auditorias", auditorias);
				
				rutaDireccion = Constantes.PLD_REPORTE_BASES_EBR;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_ERROR + e.getMessage());
			return null;
		}
	}

    @PatchMapping("actualizarVobo/{idProceso}")
    public ResponseEntity<String> actualizarEstatus(
            @PathVariable int idProceso,
            @RequestBody Map<String, String> proceso) {
    	
    	Boolean estatusVobo = Boolean.parseBoolean(proceso.get("estatusVobo"));
    	
        procesoBasesEbrService.actualizarVobo(idProceso, estatusVobo);
        return ResponseEntity.ok("Estatus actualizado correctamente");
    }   
    
	@PostMapping("/ejecutarProcesoBasesEbr")
	public ResponseEntity<BasesEbrResponse<ProcesoBasesEbr>> ejecutarProcesoBasesEbr(@RequestBody Map<String, String> payload) {
		
		Integer anioAuditoria = Integer.parseInt(payload.get("anioAuditoria"));
	    
		Integer idUsuario = obtenerIdUsuario();
		
	    try {
	        ProcesoBasesEbr proceso = procesoBasesEbrService.registrarProcesoBasesEbr(anioAuditoria, idUsuario);
	        return ResponseEntity.ok(new BasesEbrResponse<>("proceso", proceso));
	    } catch (RuntimeException e) {
	    	logger.error("Ocurrió un error al registrar el proceso: " + e.getMessage());
	        // error inesperado en la BD
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(new BasesEbrResponse<>("Ocurrió un error al registrar el proceso.", null));
	    }
	}
	
    @PostMapping("/voBoBasesEbr")
    public ResponseEntity<String> ejecutarVistoBueno() {
    	Integer idUsuario = obtenerIdUsuario();
    	
        basesEbrService.ejecutarVoBo(idUsuario);
        return ResponseEntity.ok("Vobo ejecutado correctamente, ya se agregaron las bases en el histórico.");
    }
    
    @GetMapping("/obtenerProcesoBasesEbr")
    public ResponseEntity<ProcesoBasesEbr> obtenerProcesoBasesEbr(@RequestParam int idProceso) {
    	ProcesoBasesEbr proceso = procesoBasesEbrService.obtenerProcesoBasesEbr(idProceso);
    	
    	return ResponseEntity.ok(proceso);
    }
    
    @GetMapping("/obtenerLastProcesoBasesEbr")
    public ResponseEntity<ProcesoBasesEbr> obtenerLastProcesoBasesEbr() {
    	ProcesoBasesEbr proceso = procesoBasesEbrService.obtenerLasProcesoBasesEbr();
    	
    	return ResponseEntity.ok(proceso);
    }
    
    @GetMapping("/historicoBasesEbr")
    public ResponseEntity<BasesEbrResponse<List<Map<String, Object>>>> listaHistoricoBasesEbr() {
		List<Map<String, Object>> bases = procesoBasesEbrService.listaHistoricoBases();
		
		try {
			if (bases != null && !bases.isEmpty()) {
				return ResponseEntity.ok(new BasesEbrResponse<>("bases", bases));
			} else {
				return ResponseEntity.ok(new BasesEbrResponse<>("bases", Collections.emptyList()));
			}
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BasesEbrResponse<>(Constantes.MENSAJE_DE_ERROR + "Error interno al procesar la solicitud.", null));
		}
    }
    
	@GetMapping("/generarBasesEBR")
	public ResponseEntity<BasesEbrResponse<String>> reporteBasesEBR(@RequestParam("tipoReporte") Integer tipoReporte) {
		String reporte = "";
		
		switch (tipoReporte) {
			case 1: 
				reporte = Constantes.RPT_EBR_BASE_CLIENTES;
				break;
			case 2:
				reporte = Constantes.RPT_EBR_BASE_OPERACIONES;
				break;
			case 3:
				reporte = Constantes.RPT_EBR_BASE_REMESAS;
				break;
			case 4:
				reporte = Constantes.RPT_EBR_BASE_DESEMBOLSOS;
				break;
			default:
	            return ResponseEntity.badRequest()
	            		.body(new BasesEbrResponse<>("Tipo de reporte no válido", null));
		}
		
		try {
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(reporte);
			
			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
			        + datosReporte.getPk_id_reporte()
			        + "&parametros="
			        + "&tipoDocumento=" + datosReporte.getVc_extension()
			        + "&identificadorDoc=" + LocalDate.now();

			return ResponseEntity.ok(new BasesEbrResponse<>("Reporte generado", reporteURL));
		} catch (Exception e) {
			logger.error("Error al generar reporte Bases EBR: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BasesEbrResponse<>("Error interno al procesar la solicitud.", null));
		}
	}
	
	@GetMapping("/generarHistoricoBasesEBR" )
	public ResponseEntity<BasesEbrResponse<String>> reporteHistoricoBasesEBR(@RequestParam("idProceso") Integer idProceso, @RequestParam("tipoReporte") Integer tipoReporte) {
		String reporte = "";
		
		switch (tipoReporte) {
			case 1: 
				reporte = Constantes.RPT_HISTORICO_EBR_BASE_CLIENTES;
				break;
			case 2:
				reporte = Constantes.RPT_HISTORICO_EBR_BASE_OPERACIONES;
				break;
			case 3:
				reporte = Constantes.RPT_HISTORICO_EBR_BASE_REMESAS;
				break;
			case 4:
				reporte = Constantes.RPT_HISTORICO_EBR_BASE_DESEMBOLSOS;
				break;
			default:
	            return ResponseEntity.badRequest()
	            		.body(new BasesEbrResponse<>("Tipo de reporte no válido", null));
		}
		
		try {
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(reporte);
			
			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
			        + datosReporte.getPk_id_reporte()
			        + "&parametros=?idProceso=" + idProceso
			        + "&tipoDocumento=" + datosReporte.getVc_extension()
			        + "&identificadorDoc=" + LocalDate.now();

			return ResponseEntity.ok(new BasesEbrResponse<>("Reporte generado", reporteURL));
		} catch (Exception e) {
			logger.error("Error al generar reporte Histórico Bases EBR: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BasesEbrResponse<>("Error interno al procesar la solicitud.", null));
		}
	}

	@PostMapping("/uploadTipoEmpresa")
	public ResponseEntity<String> uploadExcelTipoEmpresa(@RequestParam("file") MultipartFile file) {
	    if (file.isEmpty()) {
	        logger.error("El archivo está vacío.");
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo está vacío.");
	    }

	    try (InputStream inputStream = file.getInputStream();
	    		Workbook workbook = WorkbookFactory.create(inputStream)) {

	        logger.info("Iniciando procesamiento del archivo...");

	        Sheet sheet = workbook.getSheetAt(0);
	        Iterator<Row> rowIterator = sheet.iterator();

	        if (!rowIterator.hasNext()) {
	            logger.error("La hoja está vacía.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo Excel no tiene datos.");
	        }

	        // Leer encabezados
	        Row headerRow = rowIterator.next();
	        List<String> headers = new ArrayList<>();
	        for (Cell cell : headerRow) {
	            headers.add(getCellStringValue(cell));
	        }
	        logger.info("Encabezados detectados: " + headers);

	        // Leer datos
	        List<TipoEmpresa> empresas = new ArrayList<>();
	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	            TipoEmpresa tipoEmpresa = new TipoEmpresa();

	            tipoEmpresa.setEmpresaId((int) getCellNumericValue(row.getCell(0)));
	            tipoEmpresa.setNombreEmpresa(getCellStringValue(row.getCell(1)));
	            tipoEmpresa.setEstatus(getCellStringValue(row.getCell(2)));
	            tipoEmpresa.setUbicacion(getCellStringValue(row.getCell(3)));
	            tipoEmpresa.setSector(getCellStringValue(row.getCell(4)));

	            empresas.add(tipoEmpresa);
	        }
	        tipoEmpresaService.eliminarTodos();
	        tipoEmpresaService.registrarTipoEmpresas(empresas);
            logger.info("Archivo procesado correctamente con " +  empresas.size() + " empresas.");
            return ResponseEntity.ok("Archivo procesado con " + empresas.size() + " empresas.");

	    } catch (Exception e) {
	        logger.error("Excepción capturada: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Error al procesar el archivo.");
	    }
	}

	// Métodos auxiliares para leer celdas
	private String getCellStringValue(Cell cell) {
	    if (cell == null) return "";
	    switch (cell.getCellType()) {
	        case STRING: return cell.getStringCellValue().trim();
	        case NUMERIC: return String.valueOf((int) cell.getNumericCellValue());
	        case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
	        case FORMULA: return cell.getCellFormula();
	        default: return "";
	    }
	}

	private double getCellNumericValue(Cell cell) {
	    if (cell == null) return 0;
	    switch (cell.getCellType()) {
	        case NUMERIC: return cell.getNumericCellValue();
	        case STRING:
	            try {
	                return Double.parseDouble(cell.getStringCellValue());
	            } catch (NumberFormatException e) {
	                return 0;
	            }
	        default: return 0;
	    }
	}

	
	public int obtenerIdUsuario() {
		// Obtener usuario autenticado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (!(principal instanceof UserDetails)) {
            logger.error("El usuario no está autenticado.");
		}
		
		UserDetails userDetails = (UserDetails) principal;
		
		List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
		
		return usuario.get(0).getPk_id_usuario();
	}
	
	public int validarAcceso() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
	        if (!(principal instanceof UserDetails)) {
	            logger.error("El usuario no está autenticado o el principal no es válido.");
	            return 0; // o lanza excepción si quieres forzar validación
	        }
	        
	        UserDetails userDetails = (UserDetails) principal;
	        
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
	        if (usuario == null || usuario.isEmpty()) {
	            logger.error("No se encontró información del usuario: " + userDetails.getUsername());
	            return 0;
	        }

			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(Constantes.PLD_REPORTE_BASES_EBR));

		} catch (Exception e) {
			logger.error("Error en validar acceso: " + e.getMessage());
			return 0;
		}
	}

}
