package ahorrosbienestar.sib.std.sccb.historialCobranza.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

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
import ahorrosbienestar.sib.std.menuProducto.conf.controller.ParametrosConfigController;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.sccb.historialCobranza.model.HistorialAsignacion;
import ahorrosbienestar.sib.std.sccb.historialCobranza.model.Participantes;
import ahorrosbienestar.sib.std.sccb.historialCobranza.service.HistorialAsignacionService;
import ahorrosbienestar.sib.std.sccb.historialCobranza.service.HistorialCobranzaService;
import ahorrosbienestar.sib.std.sccb.historialCobranza.service.ParticipantesService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("sccb")
public class HistorialCobranzaController {
	
	private static final Logger logger = Logger.getLogger(ParametrosConfigController.class);

	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	 @Autowired
	 private OpcionesMenuService opcionesMenuService;
	 
	 @Autowired
	 private HistorialAsignacionService historialAsigService;
	 
	 @Autowired
	 private ParticipantesService participantesService;
	 
	 @Autowired
	 private HistorialCobranzaService historialCobranzaService;
	 
	 private String rutaDireccion = "";
	 
	@GetMapping("/historialCobranza")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
				
			if (validarAcceso() == 1) {
				rutaDireccion = Constantes.SCCB_HISTORIAL_COBRANZA;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("error:" + e.getMessage());
			return null;
		}
	}
	
	@RequestMapping(value = { "/getByIdCreditoDetalleExpediente" }, method = RequestMethod.GET)
	public ResponseEntity<?> parametroDetalleExpedienteByIdCredito(@RequestParam("idCredito") Integer idCredito) {
		
		Optional<HistorialAsignacion> histAsig = historialAsigService.searchByIdCreditoRowHistAsig(idCredito);
		
		int idExpediente = histAsig.get().getI_expediente_id();
		
		return ResponseEntity.ok(historialCobranzaService.searchDetalleExpediente(idExpediente));
	}
	
	/*@RequestMapping(value = { "/getByIdCreditoDatosGenerales" }, method = RequestMethod.GET)
	public ResponseEntity<?> parametroDatosGenerales(@RequestParam("idCredito") Integer idCredito) {
		
		Optional<HistorialAsignacion> histAsig = historialAsigService.searchByIdCreditoRowHistAsig(idCredito);
		
		int idExpediente = histAsig.get().getI_expediente_id();
		
		System.out.print(idExpediente);
		
		return ResponseEntity.ok(historialCobranzaService.searchDatosGeneralesHistorialCobranza(idExpediente));
	}*/
	
	@RequestMapping(value = { "/getByIdCreditoHistorialAsignacion" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroHistorialAsignacionByIdCredito(@RequestParam("idCredito") Integer idCredito) {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		System.out.println(idCredito);
		
		try {
			List<HistorialAsignacion> historialAsig = historialAsigService.searchByIdCreditoHistorialAsig(idCredito);
			
			if (historialAsig != null && !historialAsig.isEmpty()) {
				res.put("historialAsig", historialAsig);
				return ResponseEntity.ok(res);
			} else {
				res.put("historialAsig", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
			
		} catch (Exception e) {
			
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			res.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getByIdCreditoParticipantes" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroParticipantesByIdCredito(@RequestParam("idCredito") Integer idCredito) {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		System.out.println(idCredito);
		
		try {
			List<Participantes> participantes = participantesService.searchByIdCreditoParticipantes(idCredito);
			
			if (participantes != null && !participantes.isEmpty()) {
				res.put("participantes", participantes);
				return ResponseEntity.ok(res);
			} else {
				res.put("participantes", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
			
		} catch (Exception e) {
			
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			res.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getByNameParticipantes" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroParticipantesByName(@RequestParam("nombre") String nombre) {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		System.out.println(nombre);
		
		try {
			List<Participantes> participantes = participantesService.searchByNameParticipantes(nombre);
			
			if (participantes != null && !participantes.isEmpty()) {
				res.put("participantes", participantes);
				return ResponseEntity.ok(res);
			} else {
				res.put("participantes", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
			
		} catch (Exception e) {
			
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			res.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
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
			
			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(Constantes.SCCB_SOLICITUDES_CREDITO));
			
			
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return 0;
		}
		
		
	}
}
