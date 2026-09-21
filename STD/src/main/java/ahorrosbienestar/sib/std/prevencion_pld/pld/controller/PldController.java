package ahorrosbienestar.sib.std.prevencion_pld.pld.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("prevencion")
public class PldController {

	@Autowired
	private IUsuarioRepository iUsuarioRepository;

	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;

	@Autowired
	private ICatalogoRepository iCatalogoRepository;

	@Autowired
	private OpcionesMenuService opcionesMenuService;

	private static final int opcBusquedaGrupo = 22;
	private static final int opcBusquedaGrupo2 = 25;
	private static final String nombrePantalla = "Reportes PLD";
	private static final String rutaError = "errores/error_403";
	private static String rutaDireccion = "";

	private static final Logger logger =
	        LoggerFactory.getLogger(PldController.class);

	// Método para mostrar la pantalla de Filtrado QEQ
	@GetMapping("/pld")
	public String FiltradoQEQ() {
		return "prevencion/pld";
	}

	// Méotodo para mostrar la pantalla de ReportePLD y traer los catálogos
	@GetMapping("/reportesPLD")
	public String reportes(Model model) {

		try {

			if (validarAcceso() == 1) {

			    List<?> reportes = iCatalogoRepository.generarOpciones(opcBusquedaGrupo);
			    List<?> extensiones = iCatalogoRepository.generarOpciones(opcBusquedaGrupo2);
			    
			    logger.info("REPORTES = " + reportes);
			    logger.info("EXTENSIONES = " + extensiones);

			    logger.info("Reportes PLD encontrados: " + reportes.size());
			    logger.info("Extensiones PLD encontradas: " + extensiones.size());

			    model.addAttribute("cat_reportes_pld", reportes);
			    model.addAttribute("cat_reportes_pld_ext", extensiones);

			    rutaDireccion = "prevencion/reportesPLD";

			} else {
			    rutaDireccion = rutaError;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return null;
		}
	}

	public int validarAcceso() {
	    try {
	        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	        if (!(principal instanceof UserDetails)) {
	            logger.warn("Usuario no autenticado.");
	            return 0;
	        }

	        UserDetails userDetails = (UserDetails) principal;
	        List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

	        if (usuario == null || usuario.isEmpty()) {
	            logger.warn("Usuario no encontrado: " + userDetails.getUsername());
	            return 0;
	        }

	        return iRelRolOpcionesRepository.validarAcceso(
	                usuario.get(0).getRol_idRol().getPk_id_rol(),
	                opcionesMenuService.buscarRelacion(nombrePantalla)
	        );

	    } catch (Exception e) {
	        logger.error("Error al validar acceso en PLD.", e);
	        return 0;
	    }
	}

}
