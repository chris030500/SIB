package ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ParametrosReglasAsignacion;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ReglasAsignacion;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.service.ParametrosReglasAsignacionService;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.service.ReglasAsignacionService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("reglas")
public class ReglasAsignacionController {

	private static final Logger logger = Logger.getLogger(ReglasAsignacionController.class);

	@Autowired
	private ReglasAsignacionService reglasAsignacionService;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired ParametrosReglasAsignacionService parametrosReglasAsignacionService;
	
	@GetMapping("/obtDatosReglasAsignacion")
	public ResponseEntity<List<ReglasAsignacion>> obtDatosReglasAsignacion(Model model) {
		 logger.info("/obtDatosReglasAsignacion");
		try {
			return ResponseEntity.ok(reglasAsignacionService.obtReglasAsignacion());
		} catch (Exception e) {
			logger.error("--" + e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/cambiarEstatusRegla")
	public ResponseEntity<?> cambiarEstatusRegla(
			@RequestParam(name = "idRegla") Integer idRegla,
			@RequestParam(name = "estatus") Integer estatus
			){
		logger.info("/cambiarEstatusRegla");
		String mensaje ="Activo Correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());			
			LocalDate fecha = LocalDate.now();
			
			Optional<ReglasAsignacion> editarRegla = reglasAsignacionService.findById(idRegla);
			
			editarRegla.get().setDt_fecha_mod(fecha);
			editarRegla.get().setI_usuario_mod(usuario.get(0));
			editarRegla.get().setI_estatus(estatus);
			
			reglasAsignacionService.save(editarRegla.get());
		
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}
	}
	
	@GetMapping("/obtRegistrosReglas")
	public ResponseEntity<List<ParametrosReglasAsignacion>> obtPlantillas(Model model,@RequestParam(name = "idRegla") Integer idRegla) {
		 logger.info("/obtRegistrosReglas");
		try {
			return ResponseEntity.ok(parametrosReglasAsignacionService.obtRegParams(idRegla));
		} catch (Exception e) {
			logger.error("--" + e.getMessage());
			return null;
		}
	}

}
