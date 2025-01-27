package ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.controller;

import java.io.IOException;
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

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.model.ConfiguracionCalixta;
import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.model.ConfiguracionSMSCredito;
import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.model.ProcesoEnvioSMS;
import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.service.ConfiguracionCalixtaService;
import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.service.ConfiguracionSMSCreditoService;
import ahorrosbienestar.sib.std.credito.solicitudesAutorizadas.service.ProcesoEnvioSMSService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.smsconfig.model.ConfiguracionSMSMensual;
import ahorrosbienestar.sib.std.smsconfig.service.ConfiguracionSMSMensualService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("credito")
public class SolicitudesAutorizadasController {
	
	private static final Logger logger = Logger.getLogger(SolicitudesAutorizadasController.class);
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private ConfiguracionCalixtaService calixtaService;
	
	@Autowired
	private ConfiguracionSMSCreditoService configuracionSMSService;
	
	@Autowired
	private ProcesoEnvioSMSService gestionService;
	
	@Autowired
	private ConfiguracionSMSMensualService configMensualService;
	
	private String rutaDireccion = "";
	
	@GetMapping("/solicitudesAutorizadas")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
			
			if (validarAcceso() == 1) {
				List<ConfiguracionCalixta> credencialesCalixta = calixtaService.getCredenciales();
				model.addAttribute("credenciales_calixta", credencialesCalixta);
				model.addAttribute("hora_inicio_semanal", iConfiguracionRepository.buscarPropiedad(Constantes.HORA_INICIO_SEMANAL));
				model.addAttribute("hora_fin_semanal", iConfiguracionRepository.buscarPropiedad(Constantes.HORA_FIN_SEMANAL));
				model.addAttribute("hora_inicio_fin_semana", iConfiguracionRepository.buscarPropiedad(Constantes.HORA_INICIO_FIN_SEMANA));
				model.addAttribute("hora_fin_fin_semana", iConfiguracionRepository.buscarPropiedad(Constantes.HORA_FIN_FIN_SEMANA));
				model.addAttribute("recurrencia_envio_sms", iConfiguracionRepository.buscarPropiedad(Constantes.RECURRENCIA_SMS));
				
				rutaDireccion = Constantes.CREDITO_SOLICITUDES_AUTORIZADAS;
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
		
		List<ConfiguracionCalixta> credencialesCalixta = calixtaService.getDatosCalixta();
		List<ConfiguracionCalixta> updatedCredenciales = new ArrayList<>();
		
		for (Map.Entry<String, String> entry : credenciales.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			ConfiguracionCalixta calixta = credencialesCalixta.stream()
					.filter(c -> c.getNombreConfiguracion().equalsIgnoreCase(key))
					.findFirst()
					.orElse(null);
			
			if (calixta != null) {
				calixta.setValorConfiguracion(value);
				updatedCredenciales.add(calixta);
			}
		}
		
		calixtaService.saveCredencialesCalixta(updatedCredenciales);
		
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
		
		Optional<ConfiguracionSMSCredito> configuracionSMS = configuracionSMSService.getCurrentSMSConfig(idSMSConfig);
		
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
            
            ConfiguracionSMSCredito updatedConfig = configuracionSMSService.updateCurrentSMSText(idSMSConfig, idUsuario, texto, estatus);
            
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
	public ResponseEntity<Map<String, Object>> getAllGestionesSMS() {
		
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		
		try {
			List<Map<String, Object>> gestionesSMS = gestionService.getAllGestionesSMS();
			
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
	
    @PostMapping("/actualizar-configuracion-mensual")
    @ResponseBody
    public ResponseEntity<?> updateConfigMensual(@RequestBody Map<String, Object> payload) {
    	
        try {
        	
            Integer idSMSProceso = (Integer) payload.get("idSMSConfigMensual");
            Integer limiteSMS  = (Integer) payload.get("limiteSMS");
            Integer maxSMS = (Integer) payload.get("maxSMS");
            
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			
			if (principal instanceof UserDetails) {
				
				userDetails = (UserDetails) principal;
				
			}
			
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			Integer idUsuario = usuario.get(0).getPk_id_usuario();
            
            ConfiguracionSMSMensual updatedConfigMensual = configMensualService.updateConfigMensual(idSMSProceso, limiteSMS, maxSMS, idUsuario);
            
            if (updatedConfigMensual != null) {
                return ResponseEntity.ok(updatedConfigMensual);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una configuración mensual de SMS con los parámetros proporcionados.");
            }
        } catch (Exception e) {
        	logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la configuración mensual de SMS: " + e.getMessage());
        }
    }
	
    @PostMapping("/actualizar-estatus-gestion")
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
            
            ProcesoEnvioSMS updatedGestionSMS = gestionService.updateProcessStatus(idSMSProceso, estatus, idUsuario);
            
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
				
			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(Constantes.CREDITO_SOLICITUDES_AUTORIZADAS));
				
				
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			return 0;
		}					
	}

}
