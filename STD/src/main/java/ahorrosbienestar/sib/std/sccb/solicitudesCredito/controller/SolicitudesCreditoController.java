package ahorrosbienestar.sib.std.sccb.solicitudesCredito.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
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

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.menuProducto.conf.controller.ParametrosConfigController;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.DatosSolicitudCredito;
import ahorrosbienestar.sib.std.sccb.solicitudesCredito.model.SolicitudesCredito;
import ahorrosbienestar.sib.std.sccb.solicitudesCredito.service.DatosSolicitudCreditoService;
import ahorrosbienestar.sib.std.sccb.solicitudesCredito.service.SolicitudesCreditoService;

@Controller
@RequestMapping("sccb")
public class SolicitudesCreditoController {

	private static final Logger logger = Logger.getLogger(ParametrosConfigController.class);

	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
    private ICatalogoRepository iCatalogoRepository;
	
	 @Autowired
	 private OpcionesMenuService opcionesMenuService;
	 
	 @Autowired
	 private SolicitudesCreditoService solicitudesService;
	 
	 @Autowired
	 private DatosSolicitudCreditoService datosSolicitudService;
	    
	 private String rutaDireccion = "";
	
	@GetMapping("/solicitudesCredito")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
			
			if (validarAcceso() == 1) {
				rutaDireccion = Constantes.SCCB_SOLICITUDES_CREDITO;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("error:" + e.getMessage());
			return null;
		}
	}
	

	@RequestMapping(value = { "/getByIdCreditoSolicitudes" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametrosSolicitudesByIdCredito(@RequestParam("idCredito") Integer idCredito) {

		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		//System.out.print(idCredito.getClass().getName());
		System.out.println(idCredito);
		try {
			List<SolicitudesCredito> solicitudes = solicitudesService.searchByIdCreditoSolicitudes(idCredito);
			
			System.out.print(solicitudes.get(0).getI_credito_id());

			// Verificar si la lista de mensajes es nula o vacía
			if (solicitudes != null && !solicitudes.isEmpty()) {
				res.put("solicitudes", solicitudes);
				return ResponseEntity.ok(res);
			} else {
				// Devolver una lista vacía si no hay mensajes encontrados
				res.put("solicitudes", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			res.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getByNoClienteSolicitudes" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametrosSolicitudesByNoCliente(@RequestParam("noCliente") Integer noCliente) {

		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		//System.out.print(idCredito.getClass().getName());
		System.out.println(noCliente);
		try {
			List<SolicitudesCredito> solicitudes = solicitudesService.searchByNoClienteSolicitudes(noCliente);
			
			System.out.print(solicitudes.get(0).getI_credito_id());

			// Verificar si la lista de mensajes es nula o vacía
			if (solicitudes != null && !solicitudes.isEmpty()) {
				res.put("solicitudes", solicitudes);
				return ResponseEntity.ok(res);
			} else {
				// Devolver una lista vacía si no hay mensajes encontrados
				res.put("solicitudes", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			res.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getByNomClienteSolicitudes" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametrosSolicitudesByNomCliente(@RequestParam("nomCliente") String nomCliente) {

		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		//System.out.print(idCredito.getClass().getName());
		System.out.println(nomCliente);
		try {
			List<SolicitudesCredito> solicitudes = solicitudesService.searchByNomClienteSolicitudes(nomCliente);
			
			System.out.print(solicitudes.get(0).getI_credito_id());

			// Verificar si la lista de mensajes es nula o vacía
			if (solicitudes != null && !solicitudes.isEmpty()) {
				res.put("solicitudes", solicitudes);
				return ResponseEntity.ok(res);
			} else {
				// Devolver una lista vacía si no hay mensajes encontrados
				res.put("solicitudes", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			res.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getDatosSolicitud" }, method = RequestMethod.GET)
	public ResponseEntity<?> getDatosSolicitud(@RequestParam("idSolicitud") Integer idSolicitud) {
		
		System.out.println(idSolicitud);	
		return ResponseEntity.ok(datosSolicitudService.searchDatosSolicitud(idSolicitud));
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

