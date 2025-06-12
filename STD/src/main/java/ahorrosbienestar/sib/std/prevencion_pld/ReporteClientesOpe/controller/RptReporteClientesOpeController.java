package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.IReporteClientesOpeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Map;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("prevencion")
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
	private IReporteClientesOpeService reporteClientesOpeService;

	
	
	@GetMapping("/reporteClientesOpe")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
			
			String rutaDireccion;
					
			if (validarAcceso() == 1) {
				
				rutaDireccion = Constantes.PLD_REPORTE_CLIENTES_OPE;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_ERROR + e.getMessage());
			return null;
		}
	}
	
	@RequestMapping(value = { "/generarRptReporteClientesOpe" }, method = RequestMethod.POST)
	public ResponseEntity<?> ReporteClientesOpe(@RequestParam("fechaInicio") String fechaInicio, 
			@RequestParam("fechaFin") String fechaFin) {

		List<String> respuesta = new ArrayList<>();
		
		try {
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_CLIENTES_OPERACIONES_AUDITOR);
			
			StringBuilder reporteURL = new StringBuilder();
			reporteURL.append(iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF))
			.append(datosReporte.getPk_id_reporte())
			.append("&parametros=?FechaInicio=")
			.append(fechaInicio)
			.append("~FechaFin=")
			.append(fechaFin);

			respuesta.add(reporteURL.toString());
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}

	}
	@PostMapping("/cargarArchivoPeps")
	public ResponseEntity<?> cargarArchivoPeps(@RequestPart("archivoPeps") MultipartFile archivo) {
	    List<ReporteClientesOpeModel> registros = new ArrayList<>();

	    try (Workbook workbook = new XSSFWorkbook(archivo.getInputStream())) {
	        Sheet hoja = workbook.getSheetAt(0);
	        boolean primeraFila = true;

	        for (Row fila : hoja) {
	            if (primeraFila) {
	                primeraFila = false; // Saltar encabezados
	                continue;
	            }

	            ReporteClientesOpeModel registro = new ReporteClientesOpeModel();
	            // Asumiendo que tu Excel tiene columnas simples como ID, nombre, tipo, etc.

	            // Ejemplo de asignación, ajusta los índices y tipos:
	            Cell celda1 = fila.getCell(0); // ejemplo: ID
	            Cell celda2 = fila.getCell(1); // ejemplo: nombre
	            Cell celda3 = fila.getCell(2); // ejemplo: tipo

	            // Asegúrate de tener setters adecuados en ReporteClientesOpeModel
	            registro.setCampo1(celda1.getStringCellValue());
	            registro.setCampo2(celda2.getStringCellValue());
	            registro.setCampo3(celda3.getStringCellValue());

	            registros.add(registro);
	        }

	        iReporteClientesOpeRepository.saveAll(registros);

	        return ResponseEntity.ok().body(Map.of("message", "Archivo cargado correctamente."));

	    } catch (Exception e) {
	        logger.error("Error al procesar el archivo PEPs: " + e.getMessage());
	        return ResponseEntity.badRequest().body(Map.of("message", "Error al cargar archivo: " + e.getMessage()));
	    }
	}
	
	public int validarAcceso() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(Constantes.PLD_REPORTE_CLIENTES_OPE));

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return 0;
		}
	}
}
