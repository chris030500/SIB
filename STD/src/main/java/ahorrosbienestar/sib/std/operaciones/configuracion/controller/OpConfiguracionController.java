package ahorrosbienestar.sib.std.operaciones.configuracion.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.citaVencidos.service.CitasVencidosService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("operaciones")
public class OpConfiguracionController {
	private static final Logger logger = Logger.getLogger(OpConfiguracionController.class);

	@Autowired
	private CitasVencidosService citasVencidosService;
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/configuracion")
	public String ModuloOperacion() {
		return "operaciones/configuracion";
	}
	
	@GetMapping("/seguimientoGestion")
	public String ModuloSeguimientoGestion(Model model) {
		model.addAttribute("sucursales", citasVencidosService.obtenerSucursales(obtenerEmpleadoLogueado()));
		return "operaciones/seguimientoGestion";
	}

	@GetMapping("/reportes")
	public String ModuloReportes() {
		return "operaciones/reportes";
	}
	
	public String obtenerUsuarioLogueado() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			
			return userDetails.getUsername();
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
			return null;
		}
	}
	
	public int obtenerEmpleadoLogueado() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			
			return usuario.getI_id_empleado();
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR+e.getMessage());
			return 0;
		}
	}
	
}
