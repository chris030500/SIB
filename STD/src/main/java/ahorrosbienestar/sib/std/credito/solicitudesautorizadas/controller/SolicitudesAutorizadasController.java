package ahorrosbienestar.sib.std.credito.solicitudesautorizadas.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Controller
@RequestMapping("credito")
public class SolicitudesAutorizadasController {
	
	private static final Logger logger = Logger.getLogger(SolicitudesAutorizadasController.class);
	
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
	private GestionSMSService gestionService;
	
	@Autowired
	private ConfiguracionSMSMensualService configMensualService;
	
	@Autowired
	private DetalleGestionSMSService detalleService;
	
	@GetMapping("/solicitudesAutorizadas")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
			String rutaDireccion;
			
			if (validarAcceso() == 1) {
				List<CredencialesCalixta> credenciales = credencialesService.getDatosCalixta(2);
				model.addAttribute("credenciales", credenciales);
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
		return configuracionSMSService.getAllSMSConfig("A");
	}
	
	@GetMapping("/obtener-plantilla")
	public ResponseEntity<ConfiguracionSMS> getCurrentSMSConfig(@RequestParam Integer idSMSConfig) {
	    Optional<ConfiguracionSMS> configuracionSMS = configuracionSMSService.getCurrentSMSConfig(idSMSConfig);

	    return configuracionSMS.map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PostMapping("/guardar-plantilla")
	@ResponseBody
	public ResponseEntity<ConfiguracionSMS> updateSMSText(@RequestBody Map<String, Object> payload) {
	    try {
	        Integer idSMSConfig = (Integer) payload.get("idSMSConfig");
	        String texto = (String) payload.get("texto");
	        Integer estatus = (Integer) payload.get("estatus");

	        // Buscar usuario en la base de datos
	        Optional<Usuario> usuario = userService.obtenerUsuarioAutenticado();
	        if (usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }

	        Integer idUsuario = usuario.get().getPk_id_usuario();

	        ConfiguracionSMS updatedConfig = configuracionSMSService.updateCurrentSMSText(idSMSConfig, idUsuario, texto, estatus);

	        return (updatedConfig != null) ? ResponseEntity.ok(updatedConfig) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    } catch (Exception e) {
	        logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@GetMapping("/gestion-SMS")
	public ResponseEntity<Map<String, Object>> getAllGestionesSMS() {
		return gestionService.getAllGestionesSMS(2, "");
	}
	
	@GetMapping("/obtener-gestion")
	public ResponseEntity<Map<String, Object>> getCurrentSMSProceso(@RequestParam Integer idSMSProcess) {
		
		Optional<Map<String, Object>> gestionSMS = gestionService.getCurrentProcessSMS(2, idSMSProcess);

	    return gestionSMS.map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
    @PostMapping("/actualizar-configuracion-mensual")
    @ResponseBody
    public ResponseEntity<ConfiguracionSMSMensual> updateConfigMensual(@RequestBody Map<String, Object> payload) {
    	
        try {
        	
            Integer idSMSProceso = (Integer) payload.get("idSMSConfigMensual");
            Integer limiteSMS  = (Integer) payload.get("limiteSMS");
            Integer maxSMS = (Integer) payload.get("maxSMS");
			
			// Buscar usuario en la base de datos
	        Optional<Usuario> usuario = userService.obtenerUsuarioAutenticado();
	        if (usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	        
			Integer idUsuario = usuario.get().getPk_id_usuario();
            
            ConfiguracionSMSMensual updatedConfigMensual = configMensualService.updateConfigMensual(idSMSProceso, limiteSMS, maxSMS, idUsuario);
            
            if (updatedConfigMensual == null) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
            return ResponseEntity.ok(updatedConfigMensual);
            
        } catch (Exception e) {
        	logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
    @PostMapping("/actualizar-estatus-gestion")
    @ResponseBody
    public ResponseEntity<GestionSMS> updateEstatusProceso(@RequestBody Map<String, Object> payload) {
    	
        try {
        	
            Integer idSMSProceso = (Integer) payload.get("idSMSProceso");
            Integer estatus = (Integer) payload.get("estatus");
			
			// Buscar usuario en la base de datos
	        Optional<Usuario> usuario = userService.obtenerUsuarioAutenticado();
	        if (usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
			
			Integer idUsuario = usuario.get().getPk_id_usuario();
            
            GestionSMS updatedGestionSMS = gestionService.updateProcessStatus(idSMSProceso, estatus, idUsuario);
            
            if (updatedGestionSMS == null) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
        	updateDetalleGestionSMS(idSMSProceso, estatus);
            return ResponseEntity.ok(updatedGestionSMS);
            
            
        } catch (Exception e) {
        	logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
					opcionesMenuService.buscarRelacionController(Constantes.CREDITO_SOLICITUDES_AUTORIZADAS)
			);
				
				
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return 0;
		}					
	}
}
