package ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.model.ControlReporteJasper;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.service.ControlReporteJasperService;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.service.ProductoCaptacionService;
import ahorrosbienestar.sib.std.prevencion_pld.reporte_operaciones_inusuales.service.ProductoColocacionService;
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
	
	@Autowired
	private ControlReporteJasperService reporteJasperService;
	
	
	@GetMapping("/reporteOperacionesInusuales")
	public String paramsConfiguracion(Model model) throws IOException {
		try {
			
			String rutaDireccion;
					
			if (validarAcceso() == 1) {
				model.addAttribute("cat_tipo_producto", iCatalogoRepository.generarOpciones(Constantes.PLD_CAT_TIPO_PRODUCTO));
				model.addAttribute("cat_busqueda_cliente", iCatalogoRepository.generarOpciones(Constantes.PLD_CAT_BUSQUEDA_CLIENTE));
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
			@RequestParam("idProducto") String idProducto,
			@RequestParam("isCliente") Boolean isCliente,
			@RequestParam("isProducto") Boolean isProducto) {
		
		String paramProduct;
		String paramCustomer;
		
		paramCustomer = (noCliente == 0) ? "~NoCliente=" : "~NoCliente=" + noCliente;
		paramProduct = idProducto.isEmpty() ? "~ProductoID=" : "~ProductoID=" + idProducto;

		List<String> respuesta = new ArrayList<>();
		
		try {
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_OPERACIONES_INUSUALES_X_TIPO_PRODUCTO);
			
			StringBuilder reporteURL = new StringBuilder();
			reporteURL.append(iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF))
			.append(datosReporte.getPk_id_reporte())
			.append("&parametros=?FechaInicio=")
			.append(fechaInicio)
			.append("~FechaFin=")
			.append(fechaFin)
			.append(paramCustomer)
			.append(paramProduct)
			.append("~isCustomer=")
			.append(isCliente)
			.append("~isProduct=")
			.append(isProducto)
			.append("&tipoDocumento=")
			.append(datosReporte.getVc_extension())
			.append("&identificadorDoc=PLD");

			respuesta.add(reporteURL.toString());
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}

	}
	
	@RequestMapping(value = "/inactivarReporteJasper", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> inactivar(@RequestParam("estatus") int estatus) {

		ControlReporteJasper reporteJasperActualizado = reporteJasperService.inactivarReporteJasper(Constantes.PLD_CONTROL_REPORTE_JASPER, estatus);
		if (reporteJasperActualizado != null) {
			Map<String, Object> response = new HashMap<>();
			response.put("reporteJasper", reporteJasperActualizado);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/getReporteJasper", method = RequestMethod.GET)
	public ResponseEntity<?> getReporteJasper() {

		ControlReporteJasper reporteJasper = reporteJasperService.getReporteJasper(Constantes.PLD_CONTROL_REPORTE_JASPER);
		if (reporteJasper != null) {
			return new ResponseEntity<>(reporteJasper, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
