package ahorrosbienestar.sib.std.sccb.consultaExpedientes.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.menuProducto.conf.controller.ParametrosConfigController;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.sccb.consultaExpedientes.model.ExpedienteAfiliacion;
import ahorrosbienestar.sib.std.sccb.consultaExpedientes.model.ExpedienteCredito;
import ahorrosbienestar.sib.std.sccb.consultaExpedientes.model.ImagenesExpedienteAfiliacion;
import ahorrosbienestar.sib.std.sccb.consultaExpedientes.model.ImagenesExpedienteCredito;
import ahorrosbienestar.sib.std.sccb.consultaExpedientes.service.ExpedienteAfiliacionService;
import ahorrosbienestar.sib.std.sccb.consultaExpedientes.service.ExpedienteCreditoService;
import ahorrosbienestar.sib.std.sccb.consultaExpedientes.service.ImagenesExpedienteAfiliacionService;
import ahorrosbienestar.sib.std.sccb.consultaExpedientes.service.ImagenesExpedienteCreditoService;
import ahorrosbienestar.sib.std.sccb.consultaPagares.model.ConsultaPagares;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("sccb")
public class ConsultaExpedientesController {
	
	private static final Logger logger = Logger.getLogger(ParametrosConfigController.class);

	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private ExpedienteCreditoService expedienteCreditoService; 
	
	@Autowired
	private ExpedienteAfiliacionService expedienteAfiliacionService;
	
	@Autowired
	private ImagenesExpedienteCreditoService imagenesExpCreditoService;
	
	@Autowired
	private ImagenesExpedienteAfiliacionService imagenesExpAfiliacionService;
	
	
	
	private String rutaDireccion = "";
	
	@GetMapping("/consultaExpedientes")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
					
			if (validarAcceso() == 1) {
				rutaDireccion = Constantes.SCCB_CONSULTA_EXPEDIENTES;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("error:" + e.getMessage());
			return null;
		}
	}
	
	@RequestMapping(value = { "/getExpedienteCreditoByIdCredito" }, method = RequestMethod.GET)
	public ResponseEntity<?> getExpedienteCreditoById(@RequestParam("idCredito") Integer idCredito) {
		
		System.out.println(idCredito);	
		
		return ResponseEntity.ok(expedienteCreditoService.searchExpedienteCreditoByIdCredito(idCredito));
	}
	
	@RequestMapping(value = { "/getExpedienteAfiliacionByName" }, method = RequestMethod.GET)
	public ResponseEntity<?> getExpedienteAfiliacionByName(@RequestParam("nomCliente") String nomCliente) {
		
		System.out.println(nomCliente);	
		
		return ResponseEntity.ok(expedienteAfiliacionService.searchExpedienteAfiliacionByNomCliente(nomCliente));
	}
	
	
	@RequestMapping(value = { "/getDetalleExpedienteCredito" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroIdExpedienteCredito(@RequestParam("idCredito") Integer idCredito) {
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		Optional<ExpedienteCredito> expedienteCredito = expedienteCreditoService.searchExpedienteCreditoByIdCredito(idCredito);
		int idExpediente = expedienteCredito.get().getI_expediente_id();
		
		System.out.println(idExpediente);
		
		List<ImagenesExpedienteCredito> imagenes = imagenesExpCreditoService.searchDetalleCredito(idExpediente);
		
		try {
			if (imagenes != null && !imagenes.isEmpty()) {
				res.put("imagenes", imagenes);
				return ResponseEntity.ok(res);
			} else {
				res.put("imagenes", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			res.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getDetalleExpedienteAfiliacion" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroIdExpedienteAfiliacion(@RequestParam("nomCliente") String nomCliente) {
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		Optional<ExpedienteAfiliacion> expedienteAfiliacion = expedienteAfiliacionService.searchExpedienteAfiliacionByNomCliente(nomCliente);
		int idExpediente = expedienteAfiliacion.get().getI_expediente_id();
		
		System.out.println(idExpediente);
		
		List<ImagenesExpedienteAfiliacion> imagenes = imagenesExpAfiliacionService.searchDetalleExpdienteAfiliacion(idExpediente);
		
		try {
			if (imagenes != null && !imagenes.isEmpty()) {
				res.put("imagenes", imagenes);
				return ResponseEntity.ok(res);
			} else {
				res.put("imagenes", Collections.emptyList());
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
