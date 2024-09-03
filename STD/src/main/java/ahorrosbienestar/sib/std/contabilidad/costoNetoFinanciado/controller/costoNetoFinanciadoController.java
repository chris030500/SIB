package ahorrosbienestar.sib.std.contabilidad.costoNetoFinanciado.controller;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.contabilidad.costoNetoFinanciado.repository.IProcesosHistCostoNetoFinanciado;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("/contabilidad")
public class costoNetoFinanciadoController {
	private static final Logger logger = Logger.getLogger(costoNetoFinanciadoController.class);

	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;

	@Autowired
	private IUsuarioRepository iUsuarioRepository;

	@Autowired
	private OpcionesMenuService opcionesMenuService;
	
	@Autowired
	private IProcesosHistCostoNetoFinanciado iProcesosHistCostoNetoFinanciado;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired 
	private IReporteRepository iReporteRepository;
	
    @Autowired
    JdbcTemplate jdbcTemplate;

	List<Usuario> usuario = null;

	private String rutaDireccion = "";

	@GetMapping("/costoNetoFinanciado")
	public String costoNetoFinanciado(Model model) throws IOException {
		rutaDireccion = Constantes.CONTABILIDAD_COSTO_NETO_FINANCIADO;
		LocalDate fechaSistema = LocalDate.now();
		try {
			if (validarAcceso(Constantes.CONTABILIDAD_COSTO_NETO_FINANCIADO) == 1) {
				ObjectMapper objectMapper = new ObjectMapper();
				String listaHistProceso = objectMapper.writeValueAsString(iProcesosHistCostoNetoFinanciado.obtProcesoCNF());
				model.addAttribute("listaHistProceso", listaHistProceso);
				model.addAttribute("fechaSistema", fechaSistema);
				model.addAttribute("fechaCierre",iProcesosHistCostoNetoFinanciado.obtFechaCierre());
			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}
			return rutaDireccion;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return null;
		}

	}

	@PostMapping("/guardar-voboProceso")
	public ResponseEntity<Map<String, Object>> guardarVoBo(@RequestParam Map<String, String> params){
		Map<String, Object> response = new HashMap<>();
		try {
			Connection connection = null;
	        CallableStatement callableStatement = null;
	        String errorMessage = null;
	        int estatusProceso = 0;
            
            try {
	            connection = jdbcTemplate.getDataSource().getConnection();
	            callableStatement = connection.prepareCall(Constantes.PROCESO_VOBO_OK);
	            callableStatement.setInt(1, obtUsuario());
	            callableStatement.registerOutParameter(2, Types.VARCHAR);
	            callableStatement.registerOutParameter(3, Types.INTEGER);
	            callableStatement.execute();
	            errorMessage = callableStatement.getString(2);
	            estatusProceso = callableStatement.getInt(3);
	        } catch (SQLException e) {
	            logger.error("Error al ejecutar el procedimiento almacenado: " + e.getMessage());
	            errorMessage = "Error al ejecutar el procedimiento almacenado: " + e.getMessage();
	        } finally {
	            if (callableStatement != null) {
	                try {
	                    callableStatement.close();
	                } catch (SQLException e) {
	                    logger.error("Error al cerrar el CallableStatement: " + e.getMessage());
	                }
	            }
	            if (connection != null) {
	                try {
	                    connection.close();
	                } catch (SQLException e) {
	                    logger.error("Error al cerrar la conexión: " + e.getMessage());
	                }
	            }
	        }
            
        	ObjectMapper objectMapper = new ObjectMapper();
			String listaHistProceso = objectMapper.writeValueAsString(iProcesosHistCostoNetoFinanciado.obtProcesoCNF());
			
			response.put("message", (errorMessage == null)?Constantes.VOBO_OK:errorMessage);
			response.put("estatusProceso", estatusProceso);
			response.put("listaHistProceso", listaHistProceso);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put("message", Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 } catch (IllegalArgumentException e) {
			 response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 } catch (Exception e) {
			 response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		 }
	}
	
	@PostMapping("/procesar-costoNetoFinanciado")
	public ResponseEntity<Map<String, Object>> procesarCostoNetoFinaciado(@RequestParam Map<String, String> params){
		Map<String, Object> response = new HashMap<>();
		try {
			Connection connection = null;
	        CallableStatement callableStatement = null;
	        String errorMessage = null;
	        int estatusProceso = 0;
            
            try {
	            connection = jdbcTemplate.getDataSource().getConnection();
	            callableStatement = connection.prepareCall(Constantes.PROCESO_COSTO_NETO_FINANCIADO);
	            callableStatement.setInt(1, obtUsuario());
	            callableStatement.registerOutParameter(2, Types.VARCHAR);
	            callableStatement.registerOutParameter(3, Types.INTEGER);
	            callableStatement.execute();
	            errorMessage = callableStatement.getString(2);
	            estatusProceso = callableStatement.getInt(3);
	        } catch (SQLException e) {
	            logger.error("Error al ejecutar el procedimiento almacenado: " + e.getMessage());
	            errorMessage = "Error al ejecutar el procedimiento almacenado: " + e.getMessage();
	        } finally {
	            if (callableStatement != null) {
	                try {
	                    callableStatement.close();
	                } catch (SQLException e) {
	                    logger.error("Error al cerrar el CallableStatement: " + e.getMessage());
	                }
	            }
	            if (connection != null) {
	                try {
	                    connection.close();
	                } catch (SQLException e) {
	                    logger.error("Error al cerrar la conexión: " + e.getMessage());
	                }
	            }
	        }
	        
	        
			response.put("message", (errorMessage == null)?Constantes.PROCESO_CNF_OK:errorMessage);
			response.put("estatusProceso", estatusProceso);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put("message", Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 } catch (IllegalArgumentException e) {
			 response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 } catch (Exception e) {
			 response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		 }
	}
	
	@PostMapping("/reporte-costoNetoFinanciado")
	public ResponseEntity<Map<String, Object>> reporteCostoNetoFinaciado(@RequestParam Map<String, String> params){
		Map<String, Object> response = new HashMap<>();
		try {
			LocalDate fechaActual = LocalDate.now();
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_COSTO_NETO_FINANCIADO);
			
			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)+datosReporte.getPk_id_reporte()+"&parametros="
					+"&tipoDocumento="+datosReporte.getVc_extension()+"&identificadorDoc="+fechaActual;
				
			response.put("urlReporte", reporteURL);
			response.put("message", (reporteURL.length()>0)?Constantes.REPORTE_OK:Constantes.REPORTE_KO);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put("message", Constantes.ERROR_FORMATO_NUMERICO+ e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 } catch (IllegalArgumentException e) {
			 response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 } catch (Exception e) {
			 response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		 }
	}
	
	@PostMapping("/reporte-costoNetoFinanciadoHist")
	public ResponseEntity<Map<String, Object>> costoNetoFinanciadoHist(@RequestBody Map<String, Object> data){
		Map<String, Object> response = new HashMap<>();
		try {
			 // Obtén el valor como Object
	        Object procesoIdObj = data.get("procesoId");
	        Integer tipoProceso = null;

	        // Verifica si procesoIdObj es una instancia de Number
	        if (procesoIdObj instanceof Number) {
	            tipoProceso = ((Number) procesoIdObj).intValue();
	        } else if (procesoIdObj != null) {
	            // Convierte a Integer si no es una instancia de Number
	            tipoProceso = Integer.parseInt(procesoIdObj.toString());
	        }
	        System.out.println("Tipo Proceso: " + tipoProceso);
			LocalDate fechaActual = LocalDate.now();
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(Constantes.REPORTE_COSTO_NETO_FINANCIADO_HIST);
			
			String reporteURL = iConfiguracionRepository.buscarPropiedad(Constantes.CONTROLLER_JASPER_CONF)+datosReporte.getPk_id_reporte()
					+Constantes.REPORTE_HIST+tipoProceso
					+"&tipoDocumento="+datosReporte.getVc_extension()+"&identificadorDoc="+fechaActual;
				
			response.put("urlReporte", reporteURL);
			response.put("message", (reporteURL.length()>0)?Constantes.REPORTE_OK:Constantes.REPORTE_KO);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			response.put("message", Constantes.ERROR_FORMATO_NUMERICO+ e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 } catch (IllegalArgumentException e) {
			 response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 } catch (Exception e) {
			 response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		 }
	}
	
	public int validarAcceso(String rutaController) {

		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return 0;
		}

	}
	
	public int obtUsuario() {

		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			return usuario.get(0).getPk_id_usuario();

		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return 0;
		}

	}
}
