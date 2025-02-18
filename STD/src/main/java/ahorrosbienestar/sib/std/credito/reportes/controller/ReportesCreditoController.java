package ahorrosbienestar.sib.std.credito.reportes.controller;

import java.time.LocalDate;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.credito.reportes.service.SolicitudAutorizadaService;
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
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private SolicitudAutorizadaService autorizadasService;
	
	List<Usuario> usuario = null;
	
	@GetMapping("/reportes")
	public String indexReportes(Model model) {
		
		try {
			String rutaDireccion;
			
			if(validarAcceso(Constantes.CREDITO_REPORTES) == 1) {
				rutaDireccion = Constantes.CREDITO_REPORTES;
				
			}else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			
			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}	
	}
	
	@GetMapping("/listaClientes")
	public ResponseEntity<List<Map<String, Object>>> listaClientes(@RequestParam("valor") String valor) {
	    String mensajeError = null;
	    
	    try {
	        List<Map<String, Object>> listaClientes = autorizadasService.getClientes(valor);
	        
	        if (listaClientes.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listaClientes);
	        }

	        logger.info("Se llenó la lista con {} clientes: " + listaClientes.size());
	        return ResponseEntity.ok(listaClientes);
	        
	    } catch (Exception e) {
	    	 mensajeError = "Error al obtener clientes: " + e.getMessage() +  " " + e;
	        logger.error(mensajeError);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	@GetMapping("/listaCelClientes")
	public ResponseEntity<List<Map<String, Object>>> listaCelClientes(@RequestParam("valor") String valor) {
	    String mensajeError = null;
	    
	    try {
	        List<Map<String, Object>> listaCelulares = autorizadasService.getCustomerCellphones(valor);
	        
	        if (listaCelulares.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listaCelulares);
	        }

	        logger.info("Se llenó la lista con {} telCelular: " + listaCelulares.size());
	        return ResponseEntity.ok(listaCelulares);
	        
	    } catch (Exception e) {
	    	 mensajeError = "Error al obtener telCelulares: " + e.getMessage() + " " + e;
	        logger.error(mensajeError);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	@PostMapping("/generarRptConsumoSMS")
	public ResponseEntity<List<String>> reporteConsumoSMS(@RequestParam("fechaInicio") String fechaInicio, 
			@RequestParam("fechaFin") String fechaFin, 
			@RequestParam("idCliente") Integer idCliente, 
			@RequestParam("telCelular") String telCelular) {
		
		try {
			
			LocalDate currentDate = LocalDate.now();
			
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.RPT_SMS_SOLICITUDES_AUTORIZADAS);
			String configURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF);
			
			String paramCustomer = (idCliente == 0) ? "~idCliente=" : "~idCliente=" + idCliente;
			String paramCellphone = (telCelular.equals("")) ? "~telCelular=" : "~telCelular=" + telCelular;
			
			String reporteURL = configURL + datosReporte.getPk_id_reporte() + 
					"&parametros=?fechaInicio=" + fechaInicio +
					"~fechaFin=" + fechaFin +
					paramCustomer +
					paramCellphone +
					"&tipoDocumento=" + datosReporte.getVc_extension() + 
					"&identificadorDoc=" + currentDate;
			
			 return ResponseEntity.ok(Collections.singletonList(reporteURL));
			
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Collections.singletonList("Error al generar el reporte: " + e.getMessage()));
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
