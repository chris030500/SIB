package ahorrosbienestar.sib.std.credito.solicitudesautorizadas.controller;

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
import ahorrosbienestar.sib.std.smsconfig.service.GestionSMSService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("credito")
public class SolicitudesAutorizadasController {
	
	private static final Logger logger = Logger.getLogger(SolicitudesAutorizadasController.class);
	
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
	private GestionSMSService gestionService;
	
	@Autowired
	private ConfiguracionSMSMensualService configMensualService;
	
	List<Usuario> usuario = null;
	
	@GetMapping("/solicitudesAutorizadas")
	public String indexSolicitudesAutorizadas(Model model) throws IOException {
		try {
			String rutaDireccion;
			
			if (validarAcceso(Constantes.CREDITO_SOLICITUDES_AUTORIZADAS) == 1) {
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
		return credencialesService.saveCredencialesCalixta(credenciales, 2);
	}
	
	@GetMapping("/plantillas-SMS")
	public ResponseEntity<Map<String, Object>> getAllSMSConfig() {
		return configuracionSMSService.getAllSMSConfig("A");
	}
	
	@GetMapping("/obtener-plantilla")
	public ResponseEntity<ConfiguracionSMS> getCurrentSMSConfig(@RequestParam Long idSMSConfig) {

	    return configuracionSMSService.getCurrentSMSConfig(idSMSConfig).map(ResponseEntity::ok)
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PostMapping("/guardar-plantilla")
	@ResponseBody
	public ResponseEntity<ConfiguracionSMS> updateSMSText(@RequestBody Map<String, Object> payload) {
		return configuracionSMSService.updateSMSText(payload);
	}

	@GetMapping("/gestion-SMS")
	public ResponseEntity<Map<String, Object>> getAllGestionesSMS() {
		return gestionService.getAllGestionesSMS(2, 2, "");
	}
	
	@GetMapping("/obtener-gestion")
	public ResponseEntity<Map<String, Object>> getCurrentSMSProceso(@RequestParam Long idSMSProcess) {

	    return gestionService.getCurrentProcessSMS(2, idSMSProcess).map(ResponseEntity::ok)
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
