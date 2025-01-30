package ahorrosbienestar.sib.std.credito.reportes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ahorrosbienestar.sib.std.credito.reportes.service.SolicitudesAutorizadasService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("credito")
public class ReportesCreditoController {
	
	private static final Logger logger = Logger.getLogger(ReportesCreditoController.class);
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private SolicitudesAutorizadasService autorizadasService;
	
	private String rutaDireccion = "";
	
	@GetMapping("/reportes")
	public String indexReportes(Model model) {
		try {
			
			if(validarAcceso() == 1) {
				model.addAttribute("reportes_credito", iCatalogoRepository.generarOpciones(Constantes.CREDITO_CAT_REPORTES));
				rutaDireccion = Constantes.CREDITO_REPORTES;
				
			}else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return null;
		}	
	}
	
	@RequestMapping(value = { "/listaClientes" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaClientes(@RequestParam("valor") String valor) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
		try {
			lista = autorizadasService.getClientes(valor);
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
	
	@RequestMapping(value = { "/listaCelClientes" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaCelClientes(@RequestParam("valor") String valor) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<Map<String, Object>> lista = new ArrayList<Map<String, Object>>();
		try {
			lista = autorizadasService.getCustomerCellphones(valor);
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
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
				
			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(Constantes.CREDITO_REPORTES));
				
				
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			return 0;
		}					
	}
}
