package ahorrosbienestar.sib.std.contabilidad.configuracion.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.contabilidad.configuracion.model.ConfigAdviserBC;
import ahorrosbienestar.sib.std.contabilidad.configuracion.repository.IConfigAdviserBC;
import ahorrosbienestar.sib.std.contabilidad.configuracion.service.ProductosCreditoService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

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
	
	@Autowired
	private IConfigAdviserBC iConfigAdviserBC;
	
	List<Usuario> usuario = null;
	
	private String rutaDireccion="";
	
	@GetMapping("/parametrosConfiguracion")
	public String parametrosConfiguracion(Model model) throws IOException {
		rutaDireccion = Constantes.CONTABILIDAD_CONFIGURACION;
		try {
			if(validarAcceso(Constantes.CONTABILIDAD_CONFIGURACION) == 1) {
				// Convertir la lista a JSON
		        ObjectMapper objectMapper = new ObjectMapper();
		        String listaBCJson = objectMapper.writeValueAsString(iConfigAdviserBC.findAll());
				model.addAttribute("lista_BC", listaBCJson);
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
	public ResponseEntity<Map<String, Object>> guardarCatalogoBC(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		try {
			ConfigAdviserBC configAdviserBC = new ConfigAdviserBC();
			
			// Crear un NumberFormat para la localización deseada
	        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
	        
	        // Parsear el monto inicial y final
	        Number montoInicial = format.parse(params.get("montoInicial"));
	        Number montoFinal = format.parse(params.get("montoFinal")); 

			
			configAdviserBC.setConcepto(Integer.parseInt(params.get("Concepto")));
			configAdviserBC.setDescripcionConcepto(iCatalogoRepository.obtDescripcion(Constantes.CAT_CONCEPTOS,Integer.parseInt(params.get("Concepto"))));
			configAdviserBC.setProductos(params.get("Productos"));
			configAdviserBC.setDescripcionProducto(productosCreditoService.descProducto(params.get("Productos")));
			configAdviserBC.setMontoInicial(montoInicial.doubleValue());
			configAdviserBC.setMontoFinal(montoFinal.doubleValue());
			configAdviserBC.setEstatus(Constantes.ESTATUS_BC);
			iConfigAdviserBC.save(configAdviserBC);
		
			  // Guardar el objeto y verificar el resultado
            ConfigAdviserBC guardaConfigAdviserBC = iConfigAdviserBC.save(configAdviserBC);
            if (guardaConfigAdviserBC != null && guardaConfigAdviserBC.getId() != 0) {
                response.put("listaCatalogo", iConfigAdviserBC.findAll());
                response.put("message", "Datos guardados correctamente");
                return ResponseEntity.ok(response);
            } else {
                throw new Exception("Error al guardar los datos");
            }
			
		 } catch (NumberFormatException e) {
	            response.put("message", "Formato de número incorrecto: " + e.getMessage());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        } catch (IllegalArgumentException e) {
	            response.put("message", "Error en los parámetros: " + e.getMessage());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        } catch (Exception e) {
	            response.put("message", "Error al guardar los datos: " + e.getMessage());
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
