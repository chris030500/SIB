package ahorrosbienestar.sib.std.credito.reportes.service;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Service
public class ReportesCreditoService {
	private static final Logger logger = Logger.getLogger(ReportesCreditoService.class);
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	public Optional<Usuario> obtenerUsuarioAutenticado() {
	    try {
	        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        if (!(principal instanceof UserDetails)) {
	            logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
	            return Optional.empty();
	        }

	        UserDetails userDetails = (UserDetails) principal;
	        return iUsuarioRepository.findByNombreUsuario(userDetails.getUsername()).stream().findFirst();

	    } catch (Exception e) {
	        logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
	        return Optional.empty();
	    }
	}
	
	public int validarAcceso(OpcionesMenuService opcionesMenuService, String pantalla) {
		
		try {
	        Optional<Usuario> usuario = obtenerUsuarioAutenticado();
	        if (usuario.isEmpty()) {
	            logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return 0;
	        }
				
			return iRelRolOpcionesRepository.validarAcceso(usuario.get().getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(pantalla)
			);
				
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return 0;
		}					
	}
}
