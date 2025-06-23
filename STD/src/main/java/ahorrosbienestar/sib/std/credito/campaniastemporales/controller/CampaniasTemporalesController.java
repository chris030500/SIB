package ahorrosbienestar.sib.std.credito.campaniastemporales.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.credito.campaniastemporales.dto.CampaniaTemporalDTO;
import ahorrosbienestar.sib.std.credito.paramburocredito.controller.ParamBuroCreditoController;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.service.TiposCampaniasService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("credito")
public class CampaniasTemporalesController {
private static final Logger logger = Logger.getLogger(ParamBuroCreditoController.class);
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private TiposCampaniasService tiposCampaniasService;
	



	List<Usuario> usuario = null;
	/**
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/campaniasTemporales")
	public String indexReportes(Model model) {
		
		try {
			String rutaDireccion;
			
			if(validarAcceso(Constantes.CAMPANIAS_TEMPORALES) == 1) {
				rutaDireccion = Constantes.CAMPANIAS_TEMPORALES;
				
			}else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}	
	}
	/**
	 * 
	 * @param rutaController
	 * @return
	 */
	public int validarAcceso(String rutaController) {
		try {

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (!(principal instanceof UserDetails)) {
				logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
			}
			
			UserDetails userDetails = (UserDetails) principal;
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
	        if (usuario == null || usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return 0;
	        }

			return iRelRolOpcionesRepository.validarAcceso(
					usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return 0;
		}
	}
	
	@PostMapping("/procesarArchivo")
	public ResponseEntity<?> adjuntar(@RequestParam("file") MultipartFile[] files,
	                                  @RequestParam("idCmpTmp") Integer idCmpTmp) {

	    List<String> respuesta = new ArrayList<>();

	    try {
	        for (MultipartFile file : files) {
	            String fileName = file.getOriginalFilename();

	            if (fileName == null || file.isEmpty()) {
	                respuesta.add("Archivo vacío o sin nombre válido.");
	                continue;
	            }

	            Workbook workbook;

	            if (fileName.endsWith(".xls")) {
	                workbook = new HSSFWorkbook(file.getInputStream()); // Excel 97-2003
	            } else if (fileName.endsWith(".xlsx")) {
	                workbook = new XSSFWorkbook(file.getInputStream()); // Excel 2007+
	            } else {
	                respuesta.add("Formato no compatible: " + fileName);
	                continue;
	            }

	            Sheet sheet = workbook.getSheetAt(0); // Primera hoja
	            for (Row row : sheet) {
	                StringBuilder fila = new StringBuilder();
	                for (Cell cell : row) {
	                    switch (cell.getCellType()) {
	                        case STRING:
	                            fila.append(cell.getStringCellValue()).append(" | ");
	                            break;
	                        case NUMERIC:
	                            if (DateUtil.isCellDateFormatted(cell)) {
	                                fila.append(cell.getDateCellValue()).append(" | ");
	                            } else {
	                                fila.append(cell.getNumericCellValue()).append(" | ");
	                            }
	                            break;
	                        case BOOLEAN:
	                            fila.append(cell.getBooleanCellValue()).append(" | ");
	                            break;
	                        case FORMULA:
	                            fila.append(cell.getCellFormula()).append(" | ");
	                            break;
	                        default:
	                            fila.append(" ").append(" | ");
	                            break;
	                    }
	                }
	                respuesta.add(fila.toString());
	            }

	            workbook.close();
	        }

	        return ResponseEntity.ok(respuesta);

	    } catch (Exception e) {
	        respuesta.add("Error al procesar archivo: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
	    }
	}
	
	@PostMapping("/registroCmpTmp")
	public ResponseEntity<String> guardarCampania(@ModelAttribute CampaniaTemporalDTO cmptmpdto) {
		try {
			
			// Obtener la fecha actual sin tiempo
	        LocalDate fechaActual = LocalDate.now();
			
			TiposCampaniasModel tipoCampaniaTmp = new TiposCampaniasModel();
			tipoCampaniaTmp.setVc_tipo_campania(cmptmpdto.getNombreCampania());
			tipoCampaniaTmp.setI_vigencia_campania(cmptmpdto.getVigenciaMeses());
			tipoCampaniaTmp.setI_tipo_campania(Constantes.CAMPANIA_TIPO_TEMPORAL);
			tipoCampaniaTmp.setDt_fecha_registro(fechaActual);
			tipoCampaniaTmp.setI_user_reg(obtUsuario());
			
			
			if(tiposCampaniasService.save(tipoCampaniaTmp) !=null) {
				return ResponseEntity.ok("Campaña guardada correctamente");
			}else {
				return ResponseEntity.ok("Error al guardar campania");
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Error: " + e.getMessage());
		}
	}
	
	public Usuario obtUsuario() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (!(principal instanceof UserDetails)) {
			logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
		}
		
		UserDetails userDetails = (UserDetails) principal;
		usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
		return usuario.get(0);
		
	}
}
