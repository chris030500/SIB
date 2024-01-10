package ahorrosbienestar.sib.std.menuProducto.conf.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("parametros")
public class ParametrosConfigController {
	

	private static final Logger logger = Logger.getLogger(ParametrosConfigController.class);

	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	private static final String nombrePantalla = "Parametros de Configuracion";
	private static final String rutaError = "errores/error_403";
	private String rutaDireccion="";
	List<Usuario> usuario = null;
	
	
	@GetMapping("/configuracion")
	public String paramsConfiguracion() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			if(validarAcceso() == 1) {
				
				rutaDireccion = "menuProductos/inicioParametros";
			}else {
				rutaDireccion = rutaError;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("error:"+e.getMessage());
			return null;
		}
	}

	public int validarAcceso() {
		
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
		
		return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacion(nombrePantalla));
		
		
	}
	
}
