package ahorrosbienestar.sib.std.seguimientoFolio.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

import ahorrosbienestar.sib.std.catalogos.dto.ActividadesPA;
import ahorrosbienestar.sib.std.catalogos.dto.SeguimientoActividadPA;
import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.seguimientoFolio.model.SeguimientoFolio;
import ahorrosbienestar.sib.std.seguimientoFolio.repository.ISeguimientoFolioRepository;
import ahorrosbienestar.sib.std.seguimientoFolio.service.SeguimientoFolioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;



@Controller
@RequestMapping("seguimientoFolio")
public class SeguimientoFolioController {

	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private ISeguimientoFolioRepository iSeguimientoFolioRepository;
	
	@Autowired
	private SeguimientoFolioService seguimientoFolioService;
	
	@Autowired
	private CatalogosCEService catalogosCEService;
	
	@Autowired
	public SeguimientoFolioController(SeguimientoFolioService seguimientoFolioService) {
		this.seguimientoFolioService = seguimientoFolioService;
	}
	
	@GetMapping("/seguimiento")
	public String inicioFolio(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			model.addAttribute("lista_folios", catalogosCEService.listaActividadPAXEmpleado(usuario.get(0).getI_id_empleado()));
			
			return "seguimientoFolio/seguimiento";
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @param idFolio
	 * @param model
	 * @return
	 */
	@GetMapping("/buscarActividadesxEmpleado")
	public ResponseEntity<List<ActividadesPA>> buscarActividadesPlan(@RequestParam("idFolio") Integer idFolio,Model model) {
		
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			return ResponseEntity.ok(catalogosCEService.buscarActividadesxEmpleado(idFolio, usuario.get(0).getI_id_empleado()));
		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
			return null;
		}
	}
	/**
	 * 
	 * @param idActividad
	 * @param desc_seguimiento
	 * @param estatus
	 * @return
	 */
	@PostMapping("/altaSeguimiento")
	public ResponseEntity<?> altaSeguimiento(
			@RequestParam(name = "idActividad") Integer idActividad
		   ,@RequestParam(name = "desc_seguimiento") String desc_seguimiento
		   ,@RequestParam(name = "estatus") Integer estatus){
		
		String mensaje ="Registro Correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			SeguimientoFolio altaSeguimiento = new SeguimientoFolio();
			LocalDate fecha = LocalDate.now();
			
			altaSeguimiento.setDt_fecha_registro(fecha);
			altaSeguimiento.setI_planaccion(idActividad);
			altaSeguimiento.setI_usuario_registro(usuario.get(0).getPk_id_usuario());
			altaSeguimiento.setVc_desc_seguimiento(desc_seguimiento);
			altaSeguimiento.setI_confirmacion_responsable(estatus);
			
			iSeguimientoFolioRepository.save(altaSeguimiento);
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}		
	}
	
	/**
	 * 
	 * @param idActividad
	 * @param desc_seguimiento
	 * @param estatus
	 * @return
	 */
	@PostMapping("/modSeguimiento")
	public ResponseEntity<?> modSeguimiento(
			@RequestParam(name = "idSeguimiento") Integer idSeguimiento
		   ,@RequestParam(name = "desc_seguimiento") String desc_seguimiento
		   ,@RequestParam(name = "estatus") Integer estatus){
		
		String mensaje ="Se Actualizo Correctamente.";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			seguimientoFolioService.modificarSeguimiento(idSeguimiento,desc_seguimiento,estatus,usuario.get(0).getPk_id_usuario());
		
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}		
	}
	
	/**
	 * 
	 * @param idObservacion
	 * @param model
	 * @return
	 */
	@GetMapping("/obtSeguimiento")
	public ResponseEntity<List<SeguimientoActividadPA>> buscarSeguimiento(@RequestParam("idActividad") Integer idActividad,Model model) {
		
		try {
			return ResponseEntity.ok(catalogosCEService.obtSeguimientoxActividad(idActividad));
		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param idFolio
	 * @return
	 */
	@GetMapping("/obtenerID")
	public ResponseEntity<?> obtenerID(@RequestParam("idSeguimiento") Integer idSeguimiento){
		try {
			return ResponseEntity.ok(iSeguimientoFolioRepository.findById(idSeguimiento));
		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
			return null;
		}
	
	}
	
}
