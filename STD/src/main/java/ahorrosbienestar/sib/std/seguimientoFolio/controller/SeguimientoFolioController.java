package ahorrosbienestar.sib.std.seguimientoFolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.catalogos.dto.ActividadesPA;
import ahorrosbienestar.sib.std.catalogos.service.CatalogosCEService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;



@Controller
@RequestMapping("seguimientoFolio")
public class SeguimientoFolioController {

	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private CatalogosCEService catalogosCEService;
	
	
	@GetMapping("/seguimiento")
	public String inicioFolio(Model model) {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			model.addAttribute("lista_folios", catalogosCEService.listaFolioXEmpleado(usuario.get(0).getI_id_empleado()));
			
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
}
