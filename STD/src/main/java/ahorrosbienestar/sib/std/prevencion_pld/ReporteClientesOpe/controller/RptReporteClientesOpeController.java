package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.ReporteClientesOpeService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
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
	private ReporteClientesOpeService archivoPEPService;

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
	@GetMapping("/historialArchivosPEP")
	@ResponseBody
	public ResponseEntity<List<Map<String, Object>>> obtenerHistorialArchivosPEP() {
	    try {
	        List<Map<String, Object>> historial = archivoPEPService.obtenerHistorialArchivos();
	        return ResponseEntity.ok(historial);
	    } catch (Exception e) {
	        logger.error("Error al obtener historial de archivos PEP: " + e.getMessage());
	        return ResponseEntity.internalServerError().body(Collections.emptyList());
	    }
	}
	@GetMapping("/descargarArchivoPEP")
	public ResponseEntity<byte[]> descargarArchivoPEP(@RequestParam("nombre") String nombreArchivo) {
	    try {
	        Path path = Paths.get("uploads/peps", nombreArchivo);
	        if (!Files.exists(path)) {
	            return ResponseEntity.notFound().build();
	        }

	        byte[] contenido = Files.readAllBytes(path);
	        return ResponseEntity.ok()
	                .header("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"")
	                .body(contenido);

	    } catch (IOException e) {
	        logger.error("Error al descargar archivo: " + e.getMessage());
	        return ResponseEntity.internalServerError().build();
	    }
	}

	@PostMapping("/subirArchivoPEP")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> subirArchivoPEP(@RequestParam("archivo") MultipartFile archivo) {
		Map<String, Object> respuesta = new HashMap<>();

		try {
			if (archivo.isEmpty()) {
				respuesta.put("success", false);
				respuesta.put("message", "Archivo vacío.");
				return ResponseEntity.badRequest().body(respuesta);
			}

			String nombre = archivo.getOriginalFilename();
			if (!nombre.matches("^peps_2025_.*\\.xlsx$")) {
				respuesta.put("success", false);
				respuesta.put("message", "Nombre de archivo inválido.");
				return ResponseEntity.badRequest().body(respuesta);
			}

			// Guardar archivo en disco
			Path ruta = Paths.get("uploads/peps", nombre);
			Files.createDirectories(ruta.getParent());
			Files.write(ruta, archivo.getBytes());

			// Registrar en la tabla SIB.HIST_archivos_pep
			archivoPEPService.guardarRegistro(nombre, LocalDateTime.now(), getUsuario());

			respuesta.put("success", true);
			return ResponseEntity.ok(respuesta);

		} catch (IOException e) {
			respuesta.put("success", false);
			respuesta.put("message", "Error en el servidor: " + e.getMessage());
			return ResponseEntity.internalServerError().body(respuesta);
		}
	}

	@RequestMapping(value = { "/generarRptReporteClientesOpe" }, method = RequestMethod.POST)
	public ResponseEntity<?> ReporteClientesOpe(@RequestParam("FechaInicio") String fechaInicio,
	                                            @RequestParam("FechaFin") String fechaFin) {

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

	private int validarAcceso() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuarios = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			return iRelRolOpcionesRepository.validarAcceso(
					usuarios.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(Constantes.PLD_REPORTE_CLIENTES_OPE)
			);
		} catch (Exception e) {
			logger.error("Error en validación de acceso: " + e.getMessage());
			return 0;
		}
	}

	private String getUsuario() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUsername();
			}
		} catch (Exception e) {
			logger.error("No se pudo obtener el usuario autenticado: " + e.getMessage());
		}
		return "desconocido";
	}
}
