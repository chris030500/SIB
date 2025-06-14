package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
//import java.util.Map;
//import org.springframework.web.bind.annotation.RequestBody;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service.ReporteClientesOpeService;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;


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

	//@Autowired
	//private ReporteClientesOpeRepository repo;
	
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
	
	@PostMapping("/cargar-excel")
	public ResponseEntity<?> cargarArchivo(@RequestParam("file") MultipartFile file) {
	    try {
	        // 1. Guardar temporalmente el archivo
	        String path = "C:/ruta/temp/" + file.getOriginalFilename();
	        file.transferTo(new File(path));

	        ReporteClientesOpeService reporteClientesOpeService = null;
			// 2. Leer Excel
	        @SuppressWarnings("null")
			List<ReporteClientesOpeModel> lista = reporteClientesOpeService.leerArchivoExcel(path);

	        // 3. Procesar y guardar
	        reporteClientesOpeService.procesarYComparar(lista, "folio123");

	        return ResponseEntity.ok("Archivo procesado correctamente.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
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
