package ahorrosbienestar.sib.std.contabilidad.configuracion.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.contabilidad.configuracion.service.ProductosCreditoService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import java.util.HashMap;


@Controller
@RequestMapping("/contabilidad")
public class ConfiguracionContabilidadController {

private static final Logger logger = Logger.getLogger(ConfiguracionContabilidadController.class);
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired 
	private ProductosCreditoService productosCreditoService;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	
	List<Usuario> usuario = null;
	
	private String rutaDireccion="";
	
	@GetMapping("/parametrosConfiguracion")
	public String parametrosConfiguracion(Model model) throws IOException {
		rutaDireccion = Constantes.CONTABILIDAD_CONFIGURACION;
		try {
			if(validarAcceso(Constantes.CONTABILIDAD_CONFIGURACION) == 1) {
				model.addAttribute("cat_productos", productosCreditoService.obtProductos());
				model.addAttribute("cat_conceptos",  iCatalogoRepository.generarOpciones(Constantes.CAT_CONCEPTOS));
				
			}else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return null;
		}
		
	}
	
	@PostMapping("/guardar-CatalogoBC")
	public ResponseEntity<Map<String, String>> guardarCatalogoBC(@RequestParam Map<String, String> params) {
		Map<String, String> response = new HashMap<>();
		try {
			String campo = params.get("slcConcepto");
			response.put("message", "Datos guardados correctamente");
	        return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("error", "Error al guardar los datos: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		
	}
	
	public int validarAcceso(String rutaController) {

		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(rutaController));
			
			
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return 0;
		}
		
		
	}
}
