package ahorrosbienestar.sib.std.solicitudCredito.creditosDesembolsados.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.solicitudCredito.creditosDesembolsados.model.ConfiguracionCredenciales;
import ahorrosbienestar.sib.std.solicitudCredito.creditosDesembolsados.service.ConfiguracionCredencialesService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("solicitudCredito")
public class CreditosDesembolsadosController {
	
	private static final Logger logger = Logger.getLogger(CreditosDesembolsadosController.class);
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private ConfiguracionCredencialesService configuracionesService;
	
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
				List<ConfiguracionCredenciales> configuraciones = configuracionesService.getCredenciales();
				model.addAttribute("permiso_admin", configuracionesService.searchConfiguracion(Constantes.USUARIO_ADMIN_SOLICITUD_CREDITO, userDetails.getUsername()));
				model.addAttribute("permiso_admin_marketing", configuracionesService.searchConfiguracion(Constantes.USUARIO_ADMIN_MARKETING, userDetails.getUsername()));
				model.addAttribute("permiso_editor_marketing", configuracionesService.searchConfiguracion(Constantes.USUARIOS_EDITOR_MARKETING, userDetails.getUsername()));
				model.addAttribute("lista_configuraciones", configuraciones);
				rutaDireccion = Constantes.SOLICITUD_CREDITO_DESEMBOLSADOS;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_ERROR + e.getMessage());
			return null;
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
				
			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(Constantes.SOLICITUD_CREDITO_DESEMBOLSADOS));
				
				
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			return 0;
		}					
	}

}
