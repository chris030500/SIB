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

import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ParametrosReglas;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ParametrosReglasAsignacion;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ReglasAsignacion;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.service.ParametrosReglasAsignacionService;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.service.ParametrosReglasService;
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
	private ParametrosReglasAsignacionService parametrosReglasAsignacionService;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private ParametrosReglasService  parametrosReglasService;
	
	
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
	
	@GetMapping("/obtRegistrosParams")
	public ResponseEntity<List<ParametrosReglas>> obtRegistrosParams(Model model,@RequestParam(name = "idRegla") Integer idRegla) {
		 logger.info("/obtRegistrosReglas");
		try {
			
			return ResponseEntity.ok(parametrosReglasService.obtRegistroParametros(idRegla));
		} catch (Exception e) {
			logger.error("--" + e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/registroParams")
	public ResponseEntity<?> registroParams(
			@RequestParam(name = "idRegla") Integer idRegla,
			@RequestParam(name = "slAsignar") Integer slAsignar,
			@RequestParam(name = "porcRegistros") Integer porcRegistros,
			@RequestParam(name = "tipoCliente") Integer tipoCliente,
			@RequestParam(name = "slcTipoCartera") Integer slcTipoCartera,
			@RequestParam(name = "tipoPrioridad") Integer tipoPrioridad,
			@RequestParam(name = "slEstatus") Integer slEstatus
			){
		logger.info("/registroParams");
		String mensaje ="Registro Correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());			
			LocalDate fecha = LocalDate.now();
						
			Optional<ReglasAsignacion> reglasAsignacion = reglasAsignacionService.findById(idRegla);
			ParametrosReglasAsignacion parametrosReglasAsignacion = new ParametrosReglasAsignacion();
			
			parametrosReglasAsignacion.setFk_id_regla(reglasAsignacion.get());
			parametrosReglasAsignacion.setI_tipo_asignado(slAsignar);
			parametrosReglasAsignacion.setI_porcentaje(porcRegistros);
			parametrosReglasAsignacion.setI_tipo_cliente(tipoCliente);
			parametrosReglasAsignacion.setI_tipo_cartera(slcTipoCartera);
			parametrosReglasAsignacion.setI_prioridad(tipoPrioridad);
			parametrosReglasAsignacion.setI_estatus(slEstatus);
			parametrosReglasAsignacion.setDt_fecha_registro(fecha);
			parametrosReglasAsignacion.setI_usuario_registro(usuario.get(0));
			
			parametrosReglasAsignacionService.save(parametrosReglasAsignacion);

			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}
	}
	
	@GetMapping("/obtParametro")
	public ResponseEntity<Optional<ParametrosReglasAsignacion>> obtParametro(Model model,@RequestParam(name = "idParametros") Integer idParametros) {
		 logger.info("/obtParametro");
		try {
			return ResponseEntity.ok(parametrosReglasAsignacionService.findById(idParametros));
		} catch (Exception e) {
			logger.error("--" + e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/actualizaParams")
	public ResponseEntity<?> actualizaParams(
			@RequestParam(name = "idParams") Integer idParams,
			@RequestParam(name = "slAsignar") Integer slAsignar,
			@RequestParam(name = "porcRegistros") Integer porcRegistros,
			@RequestParam(name = "tipoCliente") Integer tipoCliente,
			@RequestParam(name = "slcTipoCartera") Integer slcTipoCartera,
			@RequestParam(name = "tipoPrioridad") Integer tipoPrioridad,
			@RequestParam(name = "slEstatus") Integer slEstatus
			){
		logger.info("/actualizaParams");
		String mensaje ="Actualizo Correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());			
			LocalDate fecha = LocalDate.now();

			Optional<ParametrosReglasAsignacion> parametrosReglasAsignacion = parametrosReglasAsignacionService.findById(idParams);
			
			parametrosReglasAsignacion.get().setI_tipo_asignado(slAsignar);
			parametrosReglasAsignacion.get().setI_porcentaje(porcRegistros);
			parametrosReglasAsignacion.get().setI_tipo_cliente(tipoCliente);
			parametrosReglasAsignacion.get().setI_tipo_cartera(slcTipoCartera);
			parametrosReglasAsignacion.get().setI_prioridad(tipoPrioridad);
			parametrosReglasAsignacion.get().setI_estatus(slEstatus);
			parametrosReglasAsignacion.get().setDt_fecha_mod(fecha);
			parametrosReglasAsignacion.get().setI_usuario_mod(usuario.get(0));
			
			parametrosReglasAsignacionService.save(parametrosReglasAsignacion.get());

			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}
	}
	

}
