package ahorrosbienestar.sib.std.marketing.creditosdesembolsados.controller;

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
import ahorrosbienestar.sib.std.marketing.creditosdesembolsados.service.ConfigCreditosDesembolsadosService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.smsconfig.model.ConfiguracionSMS;
import ahorrosbienestar.sib.std.smsconfig.model.ConfiguracionSMSMensual;
import ahorrosbienestar.sib.std.smsconfig.model.CredencialesCalixta;
import ahorrosbienestar.sib.std.smsconfig.model.GestionSMS;
import ahorrosbienestar.sib.std.smsconfig.service.ConfiguracionSMSMensualService;
import ahorrosbienestar.sib.std.smsconfig.service.ConfiguracionSMSService;
import ahorrosbienestar.sib.std.smsconfig.service.CredencialesCalixtaService;
import ahorrosbienestar.sib.std.smsconfig.service.DetalleGestionSMSService;
import ahorrosbienestar.sib.std.smsconfig.service.GestionSMSService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Controller
@RequestMapping("marketing")
public class CreditosDesembolsadosController {
	
	private static final Logger logger = Logger.getLogger(CreditosDesembolsadosController.class);
	
	@Autowired
	private UsuarioService userService;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private CredencialesCalixtaService credencialesService;
	
	@Autowired
	private ConfiguracionSMSService configuracionSMSService;
	
	@Autowired
	private ConfiguracionSMSMensualService configMensualService;
	
	@Autowired
	private GestionSMSService gestionService;
	
	@Autowired
	private ConfigCreditosDesembolsadosService configService;
	
	@Autowired
	private DetalleGestionSMSService detalleService;
	
	@GetMapping("/creditosDesembolsados")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
			String rutaDireccion;
			// Obtener usuario autenticado
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!(principal instanceof UserDetails)) {
	            logger.error("El usuario no está autenticado.");
	            return "El usuario no está autenticado.";
			}
			
			UserDetails userDetails = (UserDetails) principal;
			
			if (validarAcceso() == 1) {
				List<CredencialesCalixta> credenciales = credencialesService.getDatosCalixta(1);
				
				model.addAttribute("permiso_admin", configService.searchConfiguracion(Constantes.USUARIO_ADMIN_MARKETING, userDetails.getUsername()));
				model.addAttribute("permiso_editor", configService.searchConfiguracion(Constantes.USUARIOS_EDITOR_MARKETING, userDetails.getUsername()));
				model.addAttribute("credenciales", credenciales);
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
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/guardarCredenciales")
	@ResponseBody
	public ResponseEntity<Map<String, String>> saveCredencialesCalixta(@RequestBody Map<String, String> credenciales) {
		return credencialesService.saveCredencialesCalixta(credenciales);
	}
	
	@GetMapping("/plantillas-SMS")
	public ResponseEntity<Map<String, Object>> getAllSMSConfig() {
		return configuracionSMSService.getAllSMSConfig("D");
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

    @PostMapping("/guardar-plantilla")
    @ResponseBody
    public ResponseEntity<?> updateSMSText(@RequestBody Map<String, Object> payload) {
    	
        try {
        	
            Integer idSMSConfig = (Integer) payload.get("idSMSConfig");
            String texto = (String) payload.get("texto");
            Integer estatus = (Integer) payload.get("estatus");
            
	        Optional<Usuario> usuario = userService.obtenerUsuarioAutenticado();
	        if (usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constantes.USUARIO_NO_ENCONTRADO);
	        }
			
			Integer idUsuario = usuario.get().getPk_id_usuario();
            
            ConfiguracionSMS updatedConfig = configuracionSMSService.updateCurrentSMSText(idSMSConfig, idUsuario, texto, estatus);
            
            if (updatedConfig == null) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una configuración SMS con los parámetros proporcionados.");
            }
            
            return ResponseEntity.ok(updatedConfig);
            
        } catch (Exception e) {
        	logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la plantilla de SMS: " + e.getMessage());
        }
    }
    
	@GetMapping("/gestion-SMS")
	public ResponseEntity<Map<String, Object>> getAllGestionesSMS(@RequestParam String tipoCredito) {
		
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		
		try {
			List<Map<String, Object>> gestionesSMS = gestionService.getGestionesSMSByTipoCredito(1, tipoCredito);
			
			if (gestionesSMS != null && !gestionesSMS.isEmpty()) {
				respuesta.put("gestiones", gestionesSMS);
				return ResponseEntity.ok(respuesta);
			} else  {
				respuesta.put("gestiones", Collections.emptyList());
				return ResponseEntity.ok(respuesta);
			}
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_LOGGER_ERROR + e.getMessage();
			respuesta.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
		
	}
	
	@GetMapping("/obtener-gestion")
	public ResponseEntity<?> getCurrentSMSProceso(@RequestParam Integer idSMSProcess) {
		
		Optional<Map<String, Object>> gestionSMS = gestionService.getCurrentProcessSMS(1, idSMSProcess);
		
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
            
	        Optional<Usuario> usuario = userService.obtenerUsuarioAutenticado();
	        if (usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constantes.USUARIO_NO_ENCONTRADO);
	        }
			
			Integer idUsuario = usuario.get().getPk_id_usuario();
            
            ConfiguracionSMSMensual updatedConfigMensual = configMensualService.updateConfigMensual(idSMSProceso, limiteSMS, maxSMS, idUsuario);
            
            if (updatedConfigMensual == null) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró una configuración mensual de SMS con los parámetros proporcionados.");
            }
            
            return ResponseEntity.ok(updatedConfigMensual);
            
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
            
	        Optional<Usuario> usuario = userService.obtenerUsuarioAutenticado();
	        if (usuario.isEmpty()) {
	            logger.error("No se encontró el usuario en la base de datos.");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
	        }
			
			Integer idUsuario = usuario.get().getPk_id_usuario();
            
            GestionSMS updatedGestionSMS = gestionService.updateProcessStatus(idSMSProceso, estatus, idUsuario);
            
            if (updatedGestionSMS == null) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró un proceso SMS con los parámetros proporcionados.");
            }
            
        	updateDetalleGestionSMS(idSMSProceso, estatus);
            return ResponseEntity.ok(updatedGestionSMS);
            
        } catch (Exception e) {
        	logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el proceso de SMS: " + e.getMessage());
        }
    }
    
    private void updateDetalleGestionSMS(Integer idProceeso, Integer estatus) {
    	if (estatus == 4) {
    		detalleService.updateEnviarMensaje(idProceeso, "N");
    	} else {
    		detalleService.updateEnviarMensaje(idProceeso, "S");
    	}
    }
    
	public int validarAcceso() {
		
		try {
	        Optional<Usuario> usuario = userService.obtenerUsuarioAutenticado();
	        if (usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return 0;
	        }
				
			return iRelRolOpcionesRepository.validarAcceso(usuario.get().getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(Constantes.MARKETING_CREDITOS_DESEMBOLSADOS)
			);
				
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return 0;
		}					
	}
	
}
