package ahorrosbienestar.sib.std.prevencion_pld.pld.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.cat_catalogos.clientes_activos.service.ClientesActivosService;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.rptProductos.controller.ReportesProductosConstroller;
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

	@Autowired
	private IReporteRepository iReporteRepository;

	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;

	@Autowired
	private ClientesActivosService clientesActivosService;

	private static String nombreReporte = "";
	private static String extensionReporte = "";
	private static final String controllerReporte = "generarReporte";
	private static final int opcBusquedaGrupo = 22;
	private static final int opcBusquedaGrupo2 = 25;
	private static final String nombrePantalla = "Reportes PLD";
	private static final String rutaError = "errores/error_403";
	private String rutaDireccion="";
	
	private static final Logger logger = Logger.getLogger(PldController.class);

	// Método para mostrar la pantalla de Filtrado QEQ
	@GetMapping("/pld")
	public String FiltradoQEQ() {
		return "prevencion/pld";
	}

	@GetMapping("/reportesPLD")
	public String reportes(Model model) {

		try {

			if (validarAcceso() == 1) {
				model.addAttribute("cat_reportes_pld", iCatalogoRepository.generarOpciones(opcBusquedaGrupo));
				model.addAttribute("cat_reportes_pld_ext", iCatalogoRepository.generarOpciones(opcBusquedaGrupo2));
				// rutaDireccion = "reportesProductos/reportesProducto";
				rutaDireccion = "prevencion/reportesPLD";
			} else {
				rutaDireccion = rutaError;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return null;
		}
		// return "prevencion/reportesPLD";
	}
	
public int validarAcceso() {
		
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacion(nombrePantalla));
			
			
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return 0;
		}
		
	}

}
