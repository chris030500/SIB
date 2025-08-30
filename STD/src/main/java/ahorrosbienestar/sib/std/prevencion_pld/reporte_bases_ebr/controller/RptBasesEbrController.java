package ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.controller;

import java.io.IOException;
import java.time.Year;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.model.ProcesoBasesEbr;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service.ProcesoBasesEbrService;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_bases_ebr.service.VoboBasesEbrService;
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
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private ProcesoBasesEbrService procesoBasesEbrService;
	
	@Autowired
	private VoboBasesEbrService voboBasesEbrService;
	
	@GetMapping("/reporteBasesEBR")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
			List<Catalogo> periodos = iCatalogoRepository.generarOpciones(Constantes.PLD_CAT_PERIODO_EJECUCION_EBR);
			String rutaDireccion;
			int anioActual = Year.now().getValue();
			
			periodos.forEach(p -> {
				String valor = p.getV_valor();
				
				if (valor.contains("Semestre")) {
					valor = valor.replace(")", " " + anioActual + ")");
					p.setV_valor(valor);
				} else if (valor.equalsIgnoreCase("Año actual")) {
					p.setV_valor("Año " + anioActual);
				}
			});
					
			if (validarAcceso() == 1) {
				model.addAttribute("cat_periodo_ejecucion_ebr", periodos);
				
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
	
	@PostMapping("/ejecutarProcesoBasesEbr")
	public ResponseEntity<?> ejecutarProcesoBasesEbr(@RequestParam int periodo) {
		Integer idUsuario = obtenerIdUsuario();
		
	    try {
	        ProcesoBasesEbr proceso = procesoBasesEbrService.registrarProcesoBasesEbr(periodo, idUsuario);
	        return ResponseEntity.ok(proceso);
	    } catch (IllegalArgumentException e) {
	        // error de validación
	        return ResponseEntity.badRequest().body(e.getMessage());
	    } catch (RuntimeException e) {
	        // error inesperado en la BD
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("Ocurrió un error al registrar el proceso: " + e.getMessage());
	    }
	}
	
    @PostMapping("/voBoBasesEbr")
    public ResponseEntity<String> ejecutarVistoBueno() {
    	Integer idUsuario = obtenerIdUsuario();
    	
        voboBasesEbrService.ejecutarVoBo(idUsuario);
        return ResponseEntity.ok("Visto bueno ejecutado correctamente!");
    }
	
	@RequestMapping(value = { "/generarBasesEBR" }, method = RequestMethod.POST)
	public ResponseEntity<?> reporteBasesEBR(@RequestParam("tipoReporte") Integer tipoReporte) {

		List<String> respuesta = new ArrayList<>();
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
		}
		
		try {
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(reporte);
			
			StringBuilder reporteURL = new StringBuilder();
			reporteURL.append(iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF))
			.append(datosReporte.getPk_id_reporte())
			.append("&parametros=")
			.append("&tipoDocumento=")
			.append(datosReporte.getVc_extension())
			.append("&identificadorDoc=");

			respuesta.add(reporteURL.toString());
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error al generar reporte Bases EBR: " + e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
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
		
		Integer idUsuario = usuario.get(0).getPk_id_usuario();
		
		return idUsuario;
	}
	
	public int validarAcceso() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(Constantes.PLD_REPORTE_BASES_EBR));

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return 0;
		}
	}

}
