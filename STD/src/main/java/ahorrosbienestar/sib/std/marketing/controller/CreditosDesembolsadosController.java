package ahorrosbienestar.sib.std.marketing.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.marketing.model.ConfiguracionSMS;
import ahorrosbienestar.sib.std.marketing.model.Credenciales;
import ahorrosbienestar.sib.std.marketing.service.ConfiguracionSMSService;
import ahorrosbienestar.sib.std.marketing.service.CredencialesService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("marketing")
public class CreditosDesembolsadosController {
	
	private static final Logger logger = Logger.getLogger(CreditosDesembolsadosController.class);
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private CredencialesService configuracionesService;
	
	@Autowired
	private ConfiguracionSMSService configuracionSMSService;
	
	private String rutaDireccion = "";
	
	@GetMapping("/creditosDesembolsados")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
					userDetails = (UserDetails) principal;
			}
			if (validarAcceso() == 1) {
				int mesActual = LocalDate.now().getMonthValue();
				List<Credenciales> configuraciones = configuracionesService.getCredenciales();
				model.addAttribute("permiso_admin", configuracionesService.searchConfiguracion(Constantes.USUARIO_ADMIN_CREDITOS_DESEMBOLSADOS, userDetails.getUsername()));
				model.addAttribute("permiso_admin_marketing", configuracionesService.searchConfiguracion(Constantes.USUARIO_ADMIN_MARKETING, userDetails.getUsername()));
				model.addAttribute("permiso_editor_marketing", configuracionesService.searchConfiguracion(Constantes.USUARIOS_EDITOR_MARKETING, userDetails.getUsername()));
				model.addAttribute("lista_configuraciones", configuraciones);
				model.addAttribute("mesActual", mesActual);
				model.addAttribute("cat_meses", iCatalogoRepository.generarOpciones(Constantes.MARKETING_CAT_MESES));
				rutaDireccion = Constantes.MARKETING_CREDITOS_DESEMBOLSADOS;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_ERROR + e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/guardarCredenciales")
	@ResponseBody
	public ResponseEntity<Map<String, String>> saveCredencialesCalixta(@RequestBody Map<String, String> credenciales) {
		
		List<Credenciales> credencialesCalixta = configuracionesService.getDatosCalixta();
		List<Credenciales> updatedCredenciales = new ArrayList<>();
		
		for (Map.Entry<String, String> entry : credenciales.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			Credenciales calixta = credencialesCalixta.stream()
					.filter(c -> c.getNombreConfiguracion().equalsIgnoreCase(key))
					.findFirst()
					.orElse(null);
			
			if (calixta != null) {
				calixta.setValorConfiguracion(value);
				updatedCredenciales.add(calixta);
			}
		}
		
		configuracionesService.saveCredencialesCalixta(updatedCredenciales);
		
	    // Crear un objeto JSON con la respuesta
	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Credenciales guardadas con éxito");

	    // Devolver el objeto JSON
	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/obtener-plantilla")
	public ResponseEntity<?> getCurrentSMSConfig(@RequestParam String tipoCredito, @RequestParam Integer isCampania) {
		
		Optional<ConfiguracionSMS> configuracionSMS = configuracionSMSService.getCurrentSMSConfig(tipoCredito, isCampania);
		
		if (configuracionSMS.isPresent()) {
			return ResponseEntity.ok(configuracionSMS.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la configuración SMS");
		}
	}
	
    @PostMapping("/guardar-plantilla")
    @ResponseBody
    public ResponseEntity<?> updateSMSText(@RequestBody Map<String, Object> payload) {
    	
        try {
        	
            String tipoCredito = (String) payload.get("tipoCredito");
            Integer isCampania = (Integer) payload.get("isCampania");
            String texto = (String) payload.get("texto");
            
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			
			if (principal instanceof UserDetails) {
				
				userDetails = (UserDetails) principal;
				
			}
			
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			Integer idUsuario = usuario.get(0).getPk_id_usuario();
            
            ConfiguracionSMS updatedConfig = configuracionSMSService.updateCurrentSMSText(tipoCredito, isCampania, idUsuario, texto);
            
            if (updatedConfig != null) {
                return ResponseEntity.ok(updatedConfig);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una configuración SMS con los parámetros proporcionados.");
            }
        } catch (Exception e) {
        	logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la plantilla de SMS: " + e.getMessage());
        }
    }
    
    /*public ResponseEntity<?> getGestionMensaje(@RequestParam String tipoCredito, @RequestParam Integer isCampania) {
    	
    	Optional<ConfiguracionSMS> configuracionSMS = configuracionSMSService.getCurrentSMSConfig(tipoCredito, isCampania);
    	
    	Integer idConfigSMS = configuracionSMS.get().getIdConfig();
    }*/
    
	public int validarAcceso() {
		
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
					userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
				
			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(Constantes.MARKETING_CREDITOS_DESEMBOLSADOS));
				
				
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			return 0;
		}					
	}

}
