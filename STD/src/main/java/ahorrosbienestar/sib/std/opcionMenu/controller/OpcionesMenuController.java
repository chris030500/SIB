package ahorrosbienestar.sib.std.opcionMenu.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("opciones")
public class OpcionesMenuController {
	private static final Logger logger = Logger.getLogger(OpcionesMenuController.class);

	@Autowired
	private CoreBancarioService coreBancarioService;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	
	@GetMapping("/generarMenu")
	public ResponseEntity<?> opcionesXMenu(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			logger.info("controller /opciones");
			return ResponseEntity.ok(coreBancarioService.listaOpcionesxRol(4, usuario.get(0).getRol_idRol().getPk_id_rol()));
		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
			return null;
		}
			
	}
	
}
