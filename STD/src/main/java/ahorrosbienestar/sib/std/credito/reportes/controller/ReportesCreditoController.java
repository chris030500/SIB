package ahorrosbienestar.sib.std.credito.reportes.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.credito.reportes.service.SolicitudesAutorizadasService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("credito")
public class ReportesCreditoController {
	
	private static final Logger logger = Logger.getLogger(ReportesCreditoController.class);
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private SolicitudesAutorizadasService autorizadasService;
	
	@GetMapping("/reportes")
	public String indexReportes(Model model) {
		
		try {
			String rutaDireccion;
			
			if(validarAcceso() == 1) {
				rutaDireccion = Constantes.CREDITO_REPORTES;
				
			}else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return null;
		}	
	}
	
	@GetMapping("/listaClientes")
	public ResponseEntity<?> listaClientes(@RequestParam("valor") String valor) {
	    Map<String, Object> respuesta = new HashMap<>();
	    String mensajeError = null;
	    
	    try {
	        List<Map<String, Object>> listaClientes = autorizadasService.getClientes(valor);
	        
	        if (listaClientes.isEmpty()) {
	            respuesta.put("mensaje", "No se encontraron clientes");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	        }

	        logger.info("Se llenó la lista con {} clientes: " + listaClientes.size());
	        return ResponseEntity.ok(listaClientes);
	        
	    } catch (Exception e) {
	    	 mensajeError = "Error al obtener clientes: " + e.getMessage() + " " + e;
	        logger.error(mensajeError);
	        respuesta.put("error", mensajeError);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
	    }
	}
	
	@GetMapping("/listaCelClientes")
	public ResponseEntity<?> listaCelClientes(@RequestParam("valor") String valor) {
	    Map<String, Object> respuesta = new HashMap<>();
	    String mensajeError = null;
	    
	    try {
	        List<Map<String, Object>> listaCelulares = autorizadasService.getCustomerCellphones(valor);
	        
	        if (listaCelulares.isEmpty()) {
	            respuesta.put("mensaje", "No se encontraron clientes");
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
	        }

	        logger.info("Se llenó la lista con {} telCelular: " + listaCelulares.size());
	        return ResponseEntity.ok(listaCelulares);
	        
	    } catch (Exception e) {
	    	 mensajeError = "Error al obtener clientes: " + e.getMessage() + " " + e;
	        logger.error(mensajeError);
	        respuesta.put("error", mensajeError);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
	    }
	}
	
	@RequestMapping(value = { "/generarRptConsumoSMS" }, method = RequestMethod.POST)
	public ResponseEntity<?> reporteConsumoSMS(@RequestParam("fechaInicio") String fechaInicio, 
			@RequestParam("fechaFin") String fechaFin, 
			@RequestParam("idCliente") Integer idCliente, 
			@RequestParam("telCelular") String telCelular) {
		
		String paramCustomer;
		String paramCellphone;
		
		paramCustomer = (idCliente == 0) ? "~idCliente=" : "~idCliente=" + idCliente;
		paramCellphone = (telCelular.equals("")) ? "~telCelular=" : "~telCelular=" + telCelular;
		
		List<String> respuesta = new ArrayList<>();
		
		try {
			
			LocalDate currentDate = LocalDate.now();
			
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_SMS_SOLICITUDES_AUTORIZADAS);
			StringBuilder reporteURL = new StringBuilder();
			reporteURL.append(iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF))
			.append(datosReporte.getPk_id_reporte())
			.append("&parametros=?fechaInicio=")
			.append(fechaInicio)
			.append("~fechaFin=")
			.append(fechaFin)
			.append(paramCustomer)
			.append(paramCellphone)
			.append("&tipoDocumento=")
			.append(datosReporte.getVc_extension())
			.append("&identificadorDoc=")
			.append(currentDate);
			
			respuesta.add(reporteURL.toString());
			return ResponseEntity.ok(respuesta);
			
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}
	}
	
	public int validarAcceso() {
		
		try {
			// Obtener el usuario autenticado
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!(principal instanceof UserDetails)) {
	            logger.error("El usuario no está autenticado.");
	            return 0;
			}
			
			UserDetails userDetails = (UserDetails) principal;
			
	        // Buscar usuario en la base de datos con Optional
	        Optional<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername()).stream().findFirst();
	        if (usuario.isEmpty()) {
	            logger.error("No se encontró el usuario en la base de datos.");
	            return 0;
	        }
				
			return iRelRolOpcionesRepository.validarAcceso(
					usuario.get().getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(Constantes.CREDITO_REPORTES)
			);
				
				
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			return 0;
		}					
	}
}
