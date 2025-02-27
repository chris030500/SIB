package ahorrosbienestar.sib.std.marketing.creditosdesembolsados.controller;

import java.io.IOException;
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
import ahorrosbienestar.sib.std.smsconfig.service.GestionSMSService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("marketing")
public class CreditosDesembolsadosController {
	
	private static final Logger logger = Logger.getLogger(CreditosDesembolsadosController.class);
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
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
	private  ConfigCreditosDesembolsadosService configService;
	
	List<Usuario> usuario = null;
	
	@GetMapping("/creditosDesembolsados")
	public String indexCreditosDesembolsados(Model model) throws IOException {
		try {
			String rutaDireccion;
			// Obtener usuario autenticado
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!(principal instanceof UserDetails)) {
	            logger.error("El usuario no está autenticado.");
	            return "El usuario no está autenticado.";
			}
			
			UserDetails userDetails = (UserDetails) principal;
			
			if (validarAcceso(Constantes.MARKETING_CREDITOS_DESEMBOLSADOS) == 1) {
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
		return credencialesService.saveCredencialesCalixta(credenciales, 1);
	}
	
	@GetMapping("/plantillas-SMS")
	public ResponseEntity<Map<String, Object>> getAllSMSConfig() {
		return configuracionSMSService.getAllSMSConfig("D");
	}
	
	@GetMapping("/obtener-plantilla")
	public ResponseEntity<ConfiguracionSMS> getCurrentSMSConfig(@RequestParam Long idSMSConfig) {
		
		Optional<ConfiguracionSMS> configuracionSMS = configuracionSMSService.getCurrentSMSConfig(idSMSConfig);
		
		if (configuracionSMS.isPresent()) {
			return ResponseEntity.ok(configuracionSMS.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

    @PostMapping("/guardar-plantilla")
    @ResponseBody
    public ResponseEntity<ConfiguracionSMS> updateSMSText(@RequestBody Map<String, Object> payload) {
    	return configuracionSMSService.updateSMSText(payload);
    }
    
	@GetMapping("/gestion-SMS")
	public ResponseEntity<Map<String, Object>> getAllGestionesSMS(@RequestParam Integer isCampania, @RequestParam String tipoCredito) {
		return gestionService.getAllGestionesSMS(1, isCampania, tipoCredito);
	}
	
	@GetMapping("/obtener-gestion")
	public ResponseEntity<Map<String, Object>> getCurrentSMSProceso(@RequestParam Long idSMSProcess) {
		
	    return gestionService.getCurrentProcessSMS(1, idSMSProcess).map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
    @PostMapping("/actualizar-configuracion-mensual")
    @ResponseBody
    public ResponseEntity<ConfiguracionSMSMensual> updateConfigMensual(@RequestBody Map<String, Object> payload) {
    	return configMensualService.updateConfigMensual(payload);
    }
	
    @PostMapping("/actualizar-estatus-gestion")
    @ResponseBody
    public ResponseEntity<GestionSMS> updateEstatusProceso(@RequestBody Map<String, Object> payload) {
    	return gestionService.updateEstatusProceso(payload);
    }
    
	public int validarAcceso(String rutaController) {
		try {

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (!(principal instanceof UserDetails)) {
				logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
			}
			
			UserDetails userDetails = (UserDetails) principal;
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
	        if (usuario == null || usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return 0;
	        }

			return iRelRolOpcionesRepository.validarAcceso(
					usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return 0;
		}
	}	
}
