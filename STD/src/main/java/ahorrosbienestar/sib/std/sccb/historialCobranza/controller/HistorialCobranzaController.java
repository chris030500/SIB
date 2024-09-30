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
import ahorrosbienestar.sib.std.menuproducto.conf.controller.ParametrosConfigController;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.sccb.historialCobranza.model.HistorialAsignacion;
import ahorrosbienestar.sib.std.sccb.historialCobranza.model.Participantes;
import ahorrosbienestar.sib.std.sccb.historialCobranza.service.HistorialAsignacionService;
import ahorrosbienestar.sib.std.sccb.historialCobranza.service.HistorialCobranzaService;
import ahorrosbienestar.sib.std.sccb.historialCobranza.service.ParticipantesService;
import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.DatosSolicitudCredito;
import ahorrosbienestar.sib.std.sccb.solicitudesCredito.service.DatosSolicitudCreditoService;
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
	private DatosSolicitudCreditoService datosSolicitudService;
	 
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
			logger.error(Constantes.MENSAJE_ERROR + e.getMessage());
			return null;
		}
	}
	
	@RequestMapping(value = { "/getByIdCreditoDatosGenerales" }, method = RequestMethod.GET)
	public ResponseEntity<?> parametroDatosGenerales(@RequestParam("idCredito") Integer idCredito) {
		
		return ResponseEntity.ok(historialCobranzaService.searchDatosGeneralesHistorialCobranza(idCredito));
	}
	
	@RequestMapping(value = { "/getByIdCreditoHistorialAsignacion" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroHistorialAsignacionByIdCredito(@RequestParam("idCredito") Integer idCredito) {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
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
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getByIdCreditoParticipantes" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroParticipantesByIdCredito(@RequestParam("idCredito") Integer idCredito) {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
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
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getByNameParticipantes" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroParticipantesByName(@RequestParam("nombre") String nombre) {
		
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
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
			
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getByIdCreditoDetalleExpediente" }, method = RequestMethod.GET)
	public ResponseEntity<?> parametroDetalleExpedienteByIdCredito(@RequestParam("idCredito") Integer idCredito) {
		
		Optional<DatosSolicitudCredito> datosSolicitud =  datosSolicitudService.searchDatosSolicitudByIdCredito(idCredito);
		
		int idSolicitud = datosSolicitud.get().getI_solicitud_id();
		
		return ResponseEntity.ok(historialCobranzaService.searchDetalleExpediente(idSolicitud));
	}
	
	public int validarAcceso() {
		
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(Constantes.SCCB_HISTORIAL_COBRANZA));
			
			
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			return 0;
		}
		
		
	}
}
