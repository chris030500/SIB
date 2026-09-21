package ahorrosbienestar.sib.std.opcionMenu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger =
	        LoggerFactory.getLogger(OpcionesMenuController.class);

	@Autowired
	private CoreBancarioService coreBancarioService;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	private Usuario obtenerUsuarioAutenticado() {

	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    if (!(principal instanceof UserDetails)) {
	        return null;
	    }

	    UserDetails userDetails = (UserDetails) principal;

	    List<Usuario> usuarios = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

	    if (usuarios == null || usuarios.isEmpty()) {
	        return null;
	    }

	    return usuarios.get(0);
	}
	
	@GetMapping("/generarMenu")
	public ResponseEntity<?> opcionesXMenu(Model model) {
	    try {

	        Usuario usuario = obtenerUsuarioAutenticado();

	        if (usuario == null) {
	            logger.warn("Usuario no autenticado o no encontrado");
	            return ResponseEntity.status(401).body("Usuario no autenticado");
	        }

	        logger.info("controller /opciones");

	        return ResponseEntity.ok(
	            coreBancarioService.listaOpcionesxRol(
	                4,
	                usuario.getRol_idRol().getPk_id_rol()
	            )
	        );

	    } catch (Exception e) {
	        logger.error("Error en opcionesXMenu", e);
	        return ResponseEntity.internalServerError().body("Error interno");
	    }
	}
	
}
