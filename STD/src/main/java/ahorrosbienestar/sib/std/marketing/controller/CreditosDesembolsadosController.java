package ahorrosbienestar.sib.std.marketing.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.marketing.model.ConfiguracionSMS;
import ahorrosbienestar.sib.std.marketing.model.CredencialesCalixta;
import ahorrosbienestar.sib.std.marketing.model.GestionSMS;
import ahorrosbienestar.sib.std.marketing.service.ConfiguracionSMSService;
import ahorrosbienestar.sib.std.marketing.service.CredencialesCalixtaService;
import ahorrosbienestar.sib.std.marketing.service.GestionSMSService;
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
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private CredencialesCalixtaService configuracionesService;
	
	@Autowired
	private ConfiguracionSMSService configuracionSMSService;
	
	@Autowired
	private GestionSMSService gestionService;
	
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
				List<CredencialesCalixta> configuraciones = configuracionesService.getCredenciales();
				model.addAttribute("permiso_admin", configuracionesService.searchConfiguracion(Constantes.USUARIO_ADMIN_CREDITOS_DESEMBOLSADOS, userDetails.getUsername()));
				model.addAttribute("permiso_admin_marketing", configuracionesService.searchConfiguracion(Constantes.USUARIO_ADMIN_MARKETING, userDetails.getUsername()));
				model.addAttribute("permiso_editor_marketing", configuracionesService.searchConfiguracion(Constantes.USUARIOS_EDITOR_MARKETING, userDetails.getUsername()));
				model.addAttribute("lista_configuraciones", configuraciones);
				model.addAttribute("mesActual", mesActual);
				model.addAttribute("cat_meses", iCatalogoRepository.generarOpciones(Constantes.MARKETING_CAT_MESES));
				model.addAttribute("hora_inicio_semanal", iConfiguracionRepository.buscarPropiedad(Constantes.HORA_INICIO_SEMANAL));
				model.addAttribute("hora_fin_semanal", iConfiguracionRepository.buscarPropiedad(Constantes.HORA_FIN_SEMANAL));
				model.addAttribute("hora_inicio_fin_semana", iConfiguracionRepository.buscarPropiedad(Constantes.HORA_INICIO_FIN_SEMANA));
				model.addAttribute("hora_fin_fin_semana", iConfiguracionRepository.buscarPropiedad(Constantes.HORA_FIN_FIN_SEMANA));
				model.addAttribute("recurrencia_envio_sms", iConfiguracionRepository.buscarPropiedad(Constantes.RECURRENCIA_SMS));
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
		
		List<CredencialesCalixta> credencialesCalixta = configuracionesService.getDatosCalixta();
		List<CredencialesCalixta> updatedCredenciales = new ArrayList<>();
		
		for (Map.Entry<String, String> entry : credenciales.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			CredencialesCalixta calixta = credencialesCalixta.stream()
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
	
	@GetMapping("/plantillas-SMS")
	public ResponseEntity<Map<String, Object>> getAllSMSConfig() {
		
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		
		try {
			List<Map<String, Object>> plantillasSMS = configuracionSMSService.getAllSMSConfig();
			
			if (plantillasSMS != null && !plantillasSMS.isEmpty()) {
				respuesta.put("plantillas", plantillasSMS);
				return ResponseEntity.ok(respuesta);
			} else  {
				respuesta.put("plantillas", Collections.emptyList());
				return ResponseEntity.ok(respuesta);
			}
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
		
	}
	
	@GetMapping("/obtener-plantilla")
	public ResponseEntity<?> getCurrentSMSConfig(@RequestParam Integer idSMSConfig) {
		
		Optional<ConfiguracionSMS> configuracionSMS = configuracionSMSService.getCurrentSMSConfig(idSMSConfig);
		
		if (configuracionSMS.isPresent()) {
			return ResponseEntity.ok(configuracionSMS.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la configuración SMS");
		}
	}
	
	/*@GetMapping("/obtener-plantilla")
	public ResponseEntity<?> getCurrentSMSConfig(@RequestParam String tipoCredito, @RequestParam Integer isCampania) {
		
		Optional<ConfiguracionSMS> configuracionSMS = configuracionSMSService.getCurrentSMSConfig(tipoCredito, isCampania);
		
		if (configuracionSMS.isPresent()) {
			return ResponseEntity.ok(configuracionSMS.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la configuración SMS");
		}
	}*/
	
    @PostMapping("/guardar-plantilla")
    @ResponseBody
    public ResponseEntity<?> updateSMSText(@RequestBody Map<String, Object> payload) {
    	
        try {
        	
            Integer idSMSConfig = (Integer) payload.get("idSMSConfig");
            String texto = (String) payload.get("texto");
            Integer estatus = (Integer) payload.get("estatus");
            
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			
			if (principal instanceof UserDetails) {
				
				userDetails = (UserDetails) principal;
				
			}
			
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			Integer idUsuario = usuario.get(0).getPk_id_usuario();
            
            ConfiguracionSMS updatedConfig = configuracionSMSService.updateCurrentSMSText(idSMSConfig, idUsuario, texto, estatus);
            
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
    
	@GetMapping("/gestion-SMS")
	public ResponseEntity<Map<String, Object>> getAllGestionesSMS(@RequestParam String tipoCredito) {
		
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		
		try {
			List<Map<String, Object>> gestionesSMS = gestionService.getGestionesSMSByTipoCredito(tipoCredito);
			
			if (gestionesSMS != null && !gestionesSMS.isEmpty()) {
				respuesta.put("gestiones", gestionesSMS);
				return ResponseEntity.ok(respuesta);
			} else  {
				respuesta.put("gestiones", Collections.emptyList());
				return ResponseEntity.ok(respuesta);
			}
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
		
	}
	
	@GetMapping("/obtener-gestion")
	public ResponseEntity<?> getCurrentSMSProceso(@RequestParam Integer idSMSProcess) {
		
		Optional<Map<String, Object>> gestionSMS = gestionService.getCurrentProcessSMS(idSMSProcess);
		
		if (gestionSMS.isPresent()) {
			return ResponseEntity.ok(gestionSMS.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la configuración SMS");
		}
	}
	
    @PostMapping("/actualizar-gestion-sms")
    @ResponseBody
    public ResponseEntity<?> updateEstatusProceso(@RequestBody Map<String, Object> payload) {
    	
        try {
        	
            Integer idSMSProceso = (Integer) payload.get("idSMSProceso");
            Integer estatus = (Integer) payload.get("estatus");
            
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			
			if (principal instanceof UserDetails) {
				
				userDetails = (UserDetails) principal;
				
			}
			
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			Integer idUsuario = usuario.get(0).getPk_id_usuario();
            
            GestionSMS updatedGestionSMS = gestionService.updateProcessStatus(idSMSProceso, estatus, idUsuario);
            
            if (updatedGestionSMS != null) {
                return ResponseEntity.ok(updatedGestionSMS);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró un proceso SMS con los parámetros proporcionados.");
            }
        } catch (Exception e) {
        	logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el proceso de SMS: " + e.getMessage());
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
				
			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(Constantes.MARKETING_CREDITOS_DESEMBOLSADOS));
				
				
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			return 0;
		}					
	}
	
}
