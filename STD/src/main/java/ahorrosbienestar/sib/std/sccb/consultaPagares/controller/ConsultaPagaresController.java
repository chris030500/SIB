package ahorrosbienestar.sib.std.sccb.consultaPagares.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

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
import ahorrosbienestar.sib.std.sccb.consultaPagares.model.ConsultaPagares;
import ahorrosbienestar.sib.std.sccb.consultaPagares.service.ConsultaPagaresService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("sccb")
public class ConsultaPagaresController {
	private static final Logger logger = Logger.getLogger(ParametrosConfigController.class);

	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private ConsultaPagaresService pagaresService;
	 
	private String rutaDireccion = "";
	
	 
	@GetMapping("/consultaPagares")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
					
			if (validarAcceso() == 1) {
				rutaDireccion = Constantes.SCCB_CONSULTA_PAGARES;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("error:" + e.getMessage());
			return null;
		}
	}
	
	@RequestMapping(value = { "/getPagaresByIdCredito" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroIdCreditoPagares(@RequestParam("idCredito") Integer idCredito) {
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<ConsultaPagares> pagares = pagaresService.searchByIdCreditoPagares(idCredito);
		
		try {
			if (pagares != null && !pagares.isEmpty()) {
				res.put("pagares", pagares);
				return ResponseEntity.ok(res);
			} else {
				res.put("pagares", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			res.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getPagaresByNoCliente" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroNoClientePagares(@RequestParam("noCliente") Integer noCliente) {
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<ConsultaPagares> pagares = pagaresService.searchByNoClientePagares(noCliente);
		
		try {
			if (pagares != null && !pagares.isEmpty()) {
				res.put("pagares", pagares);
				return ResponseEntity.ok(res);
			} else {
				res.put("pagares", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			res.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}
	
	@RequestMapping(value = { "/getPagaresByNoClienteAndIdCredito" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametroNoClienteAndIdCreditoPagares(
			@RequestParam("noCliente") Integer noCliente,
			@RequestParam("idCredito") Integer idCredito) {
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<ConsultaPagares> pagares = pagaresService.searchByNoClienteAndIdCreditoPagares(noCliente, idCredito);
		
		try {
			if (pagares != null && !pagares.isEmpty()) {
				res.put("pagares", pagares);
				return ResponseEntity.ok(res);
			} else {
				res.put("pagares", Collections.emptyList());
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
