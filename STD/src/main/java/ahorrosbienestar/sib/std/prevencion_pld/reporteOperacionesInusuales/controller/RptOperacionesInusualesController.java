package ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.service.ProductoCaptacionService;
import ahorrosbienestar.sib.std.prevencion_pld.reporteOperacionesInusuales.service.ProductoColocacionService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("prevencion")
public class RptOperacionesInusualesController {
	
	private static final Logger logger = Logger.getLogger(RptOperacionesInusualesController.class);
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;

	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private ProductoCaptacionService productoCaptacionService;
	
	@Autowired
	private ProductoColocacionService productoColocacionService;
	
	private String rutaDireccion = "";
	
	@GetMapping("/reporteOperacionesInusuales")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
					
			if (validarAcceso() == 1) {
				model.addAttribute("cat_tipo_producto", iCatalogoRepository.generarOpciones(Constantes.PLD_CAT_TIPO_PRODUCTO));
				model.addAttribute("cat_productos_captacion", productoCaptacionService.getProductosCaptacion());
				model.addAttribute("cat_productos_colocacion", productoColocacionService.getProductosColocacion());
				rutaDireccion = Constantes.PLD_REPORTE_OPEACIONES_INUSUALES;
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_ERROR + e.getMessage());
			return null;
		}
	}
	
	@RequestMapping(value = { "/generarRptOperacionesInusuales" }, method = RequestMethod.POST)
	public ResponseEntity<?> reporteOperacionesInusuales(@RequestParam("fechaInicio") String fechaInicio, 
			@RequestParam("fechaFin") String fechaFin,
			@RequestParam("noCliente") Integer noCliente,
			@RequestParam("idProducto") Integer idProducto,
			@RequestParam("isCliente") Boolean isCliente,
			@RequestParam("isProducto") Boolean isProducto) {
		
		String paramProduct;
		String paramCustomer;
		
		if(noCliente == 0) {
			paramCustomer = "~NoCliente=";
		} else {
			paramCustomer = "~NoCliente=" + noCliente;
		}
		if(idProducto == 0) {
			paramProduct = "~ProductoID=";
		} else {
			paramProduct = "~ProductoID=" + idProducto;
		}

		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_OPERACIONES_INUSUALES_X_TIPO_PRODUCTO);
			
			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)
					+ datosReporte.getPk_id_reporte() + "&parametros=?FechaInicio=" + fechaInicio + "~FechaFin="
					+ fechaFin + paramCustomer + paramProduct + "~FiltroXnoCliente=" 
					+ isCliente + "~FiltroXproductoID=" + isProducto + "&tipoDocumento=" + datosReporte.getVc_extension() + "&identificadorDoc=PLD";

			respuesta.add(reporteURL);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}

	}
	
	public int validarAcceso() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(), opcionesMenuService.buscarRelacionController(Constantes.PLD_REPORTE_OPEACIONES_INUSUALES));

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return 0;
		}
	}

}
