package ahorrosbienestar.sib.std.credito.extraccioncampanias.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.credito.procesosCampanias.model.EstatusCampaniasModel;
import ahorrosbienestar.sib.std.credito.procesosCampanias.service.EstatusCampaniasService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.service.TiposCampaniasService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("credito")

public class ExtraccionCampaniasController {

private static final Logger logger = Logger.getLogger(ExtraccionCampaniasController.class);
	
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private TiposCampaniasService tiposCampaniasService;
	
	@Autowired
	private EstatusCampaniasService estatusCampaniasService;

	
	List<Usuario> usuario = null;
	
	@GetMapping("/extraccionCampanias")
	public String mostrarSolicitudes(Model model){
		
		try {
			String rutaDireccion;
			
			if (validarAcceso(Constantes.CAMPANIAS_EXTRACCION) == 1) {
				
				/////
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				
				UserDetails userDetails = (UserDetails) principal;
				usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
				Integer idUsuario = usuario.get(0).getPk_id_usuario();
				//System.out.print("idUsuario:" + idUsuario);
				model.addAttribute("idUsuario", idUsuario);
				////
				
				rutaDireccion = Constantes.CAMPANIAS_EXTRACCION;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			//logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}
	}
	

	//llenar el combo de campañas
	@GetMapping("/api/catTipoCam")
	@ResponseBody
    public ResponseEntity<List<TiposCampaniasModel>> getTiposCampanias() {
     try {
    	 List<TiposCampaniasModel> credenciales = tiposCampaniasService.getTiposCampaniasModel();
    	 
		 if (credenciales == null) {
		        return ResponseEntity.noContent().build(); // Devuelve 204
		    }else {
			    return ResponseEntity.ok(credenciales); // Devuelve JSON 200
		    }
    			 
     }catch(Exception e) {
    	 
    	 logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
     }
		
        //return ResponseEntity.ok(tiposCampaniasService.obtenerCampaniasActivos());
    }
	
	//llenar el combo de estatus campañas
	@GetMapping("/api/catEstatusCam")
	@ResponseBody
    public ResponseEntity<List<EstatusCampaniasModel>> getEstatusCampanias() {
     try {
    	 List<EstatusCampaniasModel> credenciales = estatusCampaniasService.getEstatusCampaniasModel();
    	 
		 if (credenciales == null) {
		        return ResponseEntity.noContent().build(); // Devuelve 204
		    }else {
			    return ResponseEntity.ok(credenciales); // Devuelve JSON 200
		    }
    			 
     }catch(Exception e) {
    	 
    	 logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
     }
		
        //return ResponseEntity.ok(tiposCampaniasService.obtenerCampaniasActivos());
    }
    
    public int validarAcceso(String rutaController) {
		try {

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (!(principal instanceof UserDetails)) {
				//logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
			}
			
			UserDetails userDetails = (UserDetails) principal;
			usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
	        if (usuario == null || usuario.isEmpty()) {
	            //logger.error(Constantes.USUARIO_NO_ENCONTRADO);
	            return 0;
	        }

			return iRelRolOpcionesRepository.validarAcceso(
					usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			//logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return 0;
		}
	}
	
}
