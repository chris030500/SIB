package ahorrosbienestar.sib.std.credito.procesosCampanias.controller.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.credito.procesosCampanias.controller.service.ProcesosCampaniasService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.controller.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.model.ProductoColocacion;

@Controller
@RequestMapping("credito")

public class ProcesosCampaniasController {
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private ProcesosCampaniasService procesosCampaniasService;
	
	List<Usuario> usuario = null;
	
	@GetMapping("/procesosCampanias")
	public String mostrarSolicitudes(Model model){
		
		try {
			String rutaDireccion;
			
			if (validarAcceso(Constantes.CAMPANIAS_PROCESOS) == 1) {
				
				/////
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				
				UserDetails userDetails = (UserDetails) principal;
				usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
				Integer idUsuario = usuario.get(0).getPk_id_usuario();
				//System.out.print("idUsuario:" + idUsuario);
				model.addAttribute("idUsuario", idUsuario);
				////
				
				rutaDireccion = Constantes.CAMPANIAS_PROCESOS;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			//logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}
	}
	
	//llenar el combo opcion multiple de campañas
	@GetMapping("/api/catTiposCampanias")
	@ResponseBody
    public ResponseEntity<List<TiposCampaniasModel>> getTiposCampaniasModel() {
        return ResponseEntity.ok(procesosCampaniasService.getTiposCampaniasModel());
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
