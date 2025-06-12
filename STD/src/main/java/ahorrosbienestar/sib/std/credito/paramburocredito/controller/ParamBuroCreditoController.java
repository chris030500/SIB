package ahorrosbienestar.sib.std.credito.paramburocredito.controller;

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
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;




@Controller
@RequestMapping("credito")
public class ParamBuroCreditoController {
		
	private static final Logger logger = Logger.getLogger(ParamBuroCreditoController.class);
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;


	List<Usuario> usuario = null;
	
	@GetMapping("/paramBuroCredito")
	public String indexReportes(Model model) {
		
		try {
			String rutaDireccion;
			
			if(validarAcceso(Constantes.PARAMS_BURO_CREDITO) == 1) {
				rutaDireccion = Constantes.PARAMS_BURO_CREDITO;
				
			}else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}	
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
