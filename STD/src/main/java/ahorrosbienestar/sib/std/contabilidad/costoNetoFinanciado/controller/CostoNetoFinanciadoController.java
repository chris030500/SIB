package ahorrosbienestar.sib.std.contabilidad.costoNetoFinanciado.controller;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

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

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.contabilidad.configuracion.model.ConfigAdviserBC;
import ahorrosbienestar.sib.std.contabilidad.configuracion.repository.IConfigAdviserBC;
import ahorrosbienestar.sib.std.contabilidad.configuracion.repository.IProductosCreditoConf;
import ahorrosbienestar.sib.std.contabilidad.configuracion.service.ProductosCreditoService;
import ahorrosbienestar.sib.std.contabilidad.costoNetoFinanciado.model.ConfiguracionProductosConceptos;
import ahorrosbienestar.sib.std.contabilidad.costoNetoFinanciado.repository.IConfiguracionProductosConceptos;
import ahorrosbienestar.sib.std.contabilidad.costoNetoFinanciado.repository.IDetalleFactura;
import ahorrosbienestar.sib.std.contabilidad.costoNetoFinanciado.repository.IProcesosHistCostoNetoFinanciado;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("/contabilidad")
public class CostoNetoFinanciadoController {
	private static final Logger logger = Logger.getLogger(CostoNetoFinanciadoController.class);

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
	private IConfigAdviserBC iConfigAdviserBC;
	
	@Autowired
	private IProductosCreditoConf iProductosCreditoConf;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private IDetalleFactura iDetalleFactura;
	
	@Autowired 
	private ProductosCreditoService productosCreditoService;
	
	@Autowired
	private IConfiguracionProductosConceptos iConfiguracionProductosConceptos;
	
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
				String listaBCJson = objectMapper.writeValueAsString(iProductosCreditoConf.findAll());
						//objectMapper.writeValueAsString(iConfigAdviserBC.findAll());
				
				model.addAttribute("listaHistProceso", listaHistProceso);
				model.addAttribute("fechaSistema", fechaSistema);
				model.addAttribute("fechaCierre",iProcesosHistCostoNetoFinanciado.obtFechaCierre());
				model.addAttribute("lista_BC", listaBCJson);
				model.addAttribute("cat_productos", productosCreditoService.obtProductos());
				model.addAttribute("cat_conceptos",  iCatalogoRepository.generarOpciones(Constantes.CAT_CONCEPTOS));
				model.addAttribute("cat_costo_unitario",  iCatalogoRepository.generarOpciones(Constantes.CAT_COSTOS));
				model.addAttribute("costoBuro",  iCatalogoRepository.obtDescripcion(Constantes.CAT_COSTOS,1));
				model.addAttribute("costoAdviser",  iCatalogoRepository.obtDescripcion(Constantes.CAT_COSTOS,2));
			
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
	/**
	 * Pantalla Costos
	 * @return
	 */
	
	@PostMapping("/guardar-Costos")
	public ResponseEntity<Map<String, Object>> guardarCostos(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		// Convertir la lista a JSON
        //ObjectMapper objectMapper = new ObjectMapper();
        
		try {
			// Crear un NumberFormat para la localización deseada
	        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
			 //
	        Number costoBC = format.parse(params.get("costo_unitario_BC"));
	        Number costoAdviser = format.parse(params.get("costo_unitario_Adviser")); 
	        String costoBuro = costoBC.toString();
	        String costoAd = costoAdviser.toString();
	        
	        if (iCatalogoRepository.guardarCosto(costoBuro, Constantes.CAT_COSTOS, 1) == 1 &&
	        	    iCatalogoRepository.guardarCosto(costoAd, Constantes.CAT_COSTOS, 2) == 1) {
	        	response.put("costoBC", iCatalogoRepository.obtDescripcion(Constantes.CAT_COSTOS,1));
	        	response.put("costoAdviser", iCatalogoRepository.obtDescripcion(Constantes.CAT_COSTOS,2));
	        	response.put("catcostounitario",  iCatalogoRepository.generarOpciones(Constantes.CAT_COSTOS));
				//response.put("catcostounitario",iCatalogoRepository.generarOpciones(Constantes.CAT_COSTOS));
                response.put("message", "Datos guardados correctamente");
                return ResponseEntity.ok(response);
                
	        }else {
	        	  throw new Exception("Error al guardar los datos");
            }
	        
		} catch (NumberFormatException e) {
			logger.error("Error:"+e.getMessage());
			response.put("message", Constantes.ERROR_FORMATO_NUMERICO+ e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 } catch (IllegalArgumentException e) {
			 logger.error("Error:"+e.getMessage());
			 response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 } catch (Exception e) {
			 logger.error("Error:"+e.getMessage());
			 response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		 }
	}
	
	@PostMapping("/guardar-CatalogoBC")
	public ResponseEntity<Map<String, Object>> guardarCatalogoBC(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		try {
			ConfigAdviserBC configAdviserBC = new ConfigAdviserBC();
			
			// Crear un NumberFormat para la localización deseada
	        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
	        
	        // Parsear el monto inicial y final
	       
	        Number montoInicial = format.parse(params.get("montoInicial"));
	        Number montoFinal = format.parse(params.get("montoFinal")); 
	        
	        int existe = iConfigAdviserBC.existeConfiguracion(montoInicial.doubleValue(),montoFinal.doubleValue(),params.get("Productos"),Integer.parseInt(params.get("Concepto")));
	        if(existe == 0) {
	        
	        
	        //si es igual a 0 es un alta si es mayor a 0 es una actualizacion. 
	        if(Integer.parseInt(params.get("gastoIde")) > 0) {
	        	 Optional<ConfigAdviserBC> obtConfiguracion = iConfigAdviserBC.findById(Integer.parseInt(params.get("gastoIde")));
	        	 obtConfiguracion.get().setConcepto(Integer.parseInt(params.get("Concepto")));
	        	 obtConfiguracion.get().setDescripcionConcepto(iCatalogoRepository.obtDescripcion(Constantes.CAT_CONCEPTOS,Integer.parseInt(params.get("Concepto"))));
	        	 obtConfiguracion.get().setProductos(params.get("Productos"));
	        	 obtConfiguracion.get().setDescripcionProducto(productosCreditoService.descProducto(params.get("Productos")));
	        	 obtConfiguracion.get().setMontoInicial(montoInicial.doubleValue());
	        	 obtConfiguracion.get().setMontoFinal(montoFinal.doubleValue());
	        	 configAdviserBC = iConfigAdviserBC.save(obtConfiguracion.get());
	        	 
	             if (configAdviserBC != null && configAdviserBC.getId() != 0) {
	                 response.put("listaCatalogo", iConfigAdviserBC.findAll());
	                 response.put("message", "Se actualizo correctamente");
	                 return ResponseEntity.ok(response);
	             } else {
	                 throw new Exception("Error al guardar los datos");
	             }
	        	 
	        }else {
	        	configAdviserBC.setConcepto(Integer.parseInt(params.get("Concepto")));
	        	configAdviserBC.setDescripcionConcepto(iCatalogoRepository.obtDescripcion(Constantes.CAT_CONCEPTOS,Integer.parseInt(params.get("Concepto"))));
				configAdviserBC.setProductos(params.get("Productos"));
				configAdviserBC.setDescripcionProducto(productosCreditoService.descProducto(params.get("Productos")));
				configAdviserBC.setMontoInicial(montoInicial.doubleValue());
				configAdviserBC.setMontoFinal(montoFinal.doubleValue());
				configAdviserBC.setEstatus(Constantes.ESTATUS_BC);
				ConfigAdviserBC guardaConfigAdviserBC = iConfigAdviserBC.save(configAdviserBC);
				
				   if (guardaConfigAdviserBC != null && guardaConfigAdviserBC.getId() != 0) {
		                response.put("listaCatalogo", iConfigAdviserBC.findAll());
		                response.put("message", "Se registraron datos correctamente");
		                return ResponseEntity.ok(response);
		            } else {
		                throw new Exception("Error al guardar los datos");
		            }
				
	        }
	        }else {
	        	response.put("listaCatalogo", iConfigAdviserBC.findAll());
                response.put("message", "Los parametros seleccionados ya estan registrados en la BD.");
                return ResponseEntity.ok(response);
	        }
		 } catch (NumberFormatException e) {
				logger.error("Error:"+e.getMessage());
				response.put("message", Constantes.ERROR_FORMATO_NUMERICO+ e.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			 } catch (IllegalArgumentException e) {
				 logger.error("Error:"+e.getMessage());
				 response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			 } catch (Exception e) {
				 logger.error("Error:"+e.getMessage());
				 response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
				 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			 }
	}
	
	@PostMapping("/guardar-ConfiguracionGasto")
	public ResponseEntity<Map<String, Object>> guardarConfiguracion(@RequestParam Map<String, String> params) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			int codProd = Integer.parseInt(params.get("confProd"));
			int estatus=1;


			// guardar los datos que existen
			Set<Integer> conceptosEnFormulario = new HashSet<>();
			 // Iterar sobre las entradas del Map
	        for (Map.Entry<String, String> entry : params.entrySet()) {
	        	
	        	  String key = entry.getKey();
		          String value = entry.getValue();
	        	 
	            if (!key.equals("confProd") && !key.equals("tblConfConceptos_length") && !key.equals("total")) {
	            	 int conceptoFormulario = Integer.parseInt(value);
	                 conceptosEnFormulario.add(conceptoFormulario); // Agregar concepto al Set
	            	
	            	ConfiguracionProductosConceptos registro = iConfiguracionProductosConceptos.existeRegistro(codProd,Integer.parseInt(value));
	            	//si es null es alta
	            	if(registro == null) {
	            		ConfiguracionProductosConceptos configuracion = new ConfiguracionProductosConceptos();
		            	configuracion.setProducto(codProd);
		            	configuracion.setConcepto(Integer.parseInt(value));
		            	//configuracion.setConcepto(0)
		            	iConfiguracionProductosConceptos.save(configuracion);
	            	}else {
	            		// si no es null es modificacion
	            		registro.setEstatus(estatus);
	            		iConfiguracionProductosConceptos.save(registro);
	            	}	            	
	            }
	           
	        }
			List<ConfiguracionProductosConceptos> registros =iConfiguracionProductosConceptos.obtenerXproducto(codProd);
	        // Después de iterar sobre los datos del formulario, iterar sobre la lista de registros existentes
	        for (ConfiguracionProductosConceptos registro : registros) {
	        	ConfiguracionProductosConceptos confActualizar = new ConfiguracionProductosConceptos();
	            if (!conceptosEnFormulario.contains(registro.getConcepto())) {
	                // Si el concepto no está en el formulario, actualizar el estatus a 2
	                registro.setEstatus(2);
	                confActualizar = iConfiguracionProductosConceptos.save(registro);
	            }
	        }
			
			
			ConfigAdviserBC configAdviserBC = new ConfigAdviserBC();
			

			
			// Crear un NumberFormat para la localización deseada
	        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
	        
	        // Parsear el monto inicial y final
	       
	        Number montoInicial = format.parse(params.get("montoInicial"));
	        Number montoFinal = format.parse(params.get("montoFinal")); 
	        
	        int existe = iConfigAdviserBC.existeConfiguracion(montoInicial.doubleValue(),montoFinal.doubleValue(),params.get("Productos"),Integer.parseInt(params.get("Concepto")));
	        if(existe == 0) {
	        
	        
	        //si es igual a 0 es un alta si es mayor a 0 es una actualizacion. 
	        if(Integer.parseInt(params.get("gastoIde")) > 0) {
	        	 Optional<ConfigAdviserBC> obtConfiguracion = iConfigAdviserBC.findById(Integer.parseInt(params.get("gastoIde")));
	        	 obtConfiguracion.get().setConcepto(Integer.parseInt(params.get("Concepto")));
	        	 obtConfiguracion.get().setDescripcionConcepto(iCatalogoRepository.obtDescripcion(Constantes.CAT_CONCEPTOS,Integer.parseInt(params.get("Concepto"))));
	        	 obtConfiguracion.get().setProductos(params.get("Productos"));
	        	 obtConfiguracion.get().setDescripcionProducto(productosCreditoService.descProducto(params.get("Productos")));
	        	 obtConfiguracion.get().setMontoInicial(montoInicial.doubleValue());
	        	 obtConfiguracion.get().setMontoFinal(montoFinal.doubleValue());
	        	 configAdviserBC = iConfigAdviserBC.save(obtConfiguracion.get());
	        	 
	             if (configAdviserBC != null && configAdviserBC.getId() != 0) {
	                 response.put("listaCatalogo", iConfigAdviserBC.findAll());
	                 response.put("message", "Se actualizo correctamente");
	                 return ResponseEntity.ok(response);
	             } else {
	                 throw new Exception("Error al guardar los datos");
	             }
	        	 
	        }else {
	        	configAdviserBC.setConcepto(Integer.parseInt(params.get("Concepto")));
	        	configAdviserBC.setDescripcionConcepto(iCatalogoRepository.obtDescripcion(Constantes.CAT_CONCEPTOS,Integer.parseInt(params.get("Concepto"))));
				configAdviserBC.setProductos(params.get("Productos"));
				configAdviserBC.setDescripcionProducto(productosCreditoService.descProducto(params.get("Productos")));
				configAdviserBC.setMontoInicial(montoInicial.doubleValue());
				configAdviserBC.setMontoFinal(montoFinal.doubleValue());
				configAdviserBC.setEstatus(Constantes.ESTATUS_BC);
				ConfigAdviserBC guardaConfigAdviserBC = iConfigAdviserBC.save(configAdviserBC);
				
				   if (guardaConfigAdviserBC != null && guardaConfigAdviserBC.getId() != 0) {
		                response.put("listaCatalogo", iConfigAdviserBC.findAll());
		                response.put("message", "Se registraron datos correctamente");
		                return ResponseEntity.ok(response);
		            } else {
		                throw new Exception("Error al guardar los datos");
		            }
				
	        }
	        }else {
	        	response.put("listaCatalogo", iConfigAdviserBC.findAll());
                response.put("message", "Los parametros seleccionados ya estan registrados en la BD.");
                return ResponseEntity.ok(response);
	        }
		 } catch (NumberFormatException e) {
				logger.error("Error:"+e.getMessage());
				response.put("message", Constantes.ERROR_FORMATO_NUMERICO+ e.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			 } catch (IllegalArgumentException e) {
				 logger.error("Error:"+e.getMessage());
				 response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			 } catch (Exception e) {
				 logger.error("Error:"+e.getMessage());
				 response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
				 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			 }
	}
	
	
	
	@PostMapping("/obtener-gastos")
	public ResponseEntity<Map<String, Object>> obtGastos(@RequestBody Map<String, Object> data){		
		Map<String, Object> response = new HashMap<>();
		try {
			// Obtén el valor como Object
	        Object procesoIdObj = data.get("identificador");
	        Integer tipoProceso = null;
	        
	        // Verifica si procesoIdObj es una instancia de Number
	        if (procesoIdObj instanceof Number) {
	            tipoProceso = ((Number) procesoIdObj).intValue();
	        } else if (procesoIdObj != null) {
	            // Convierte a Integer si no es una instancia de Number
	            tipoProceso = Integer.parseInt(procesoIdObj.toString());
	        }
	        
	        Optional<ConfigAdviserBC> obtConfiguracion = iConfigAdviserBC.findById(tipoProceso);
	        response.put("gastoDatos", obtConfiguracion);
        	response.put("listaDetalleFactura", iDetalleFactura.obtDetalleFacturaMod());
            return ResponseEntity.ok(response);
	        /*if (!obtConfiguracion.isEmpty()) {
	        	//response.put("gastoDatos", obtConfiguracion);
	        	//response.put("listaDetalleFactura", iDetalleFactura.obtDetalleFactura());
                //return ResponseEntity.ok(response);
	        }else {
	        	  throw new Exception("Error al guardar los datos");
            }*/
			
		 } catch (NumberFormatException e) {
				logger.error("Error:"+e.getMessage());
				response.put("message", Constantes.ERROR_FORMATO_NUMERICO+ e.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			 } catch (IllegalArgumentException e) {
				 logger.error("Error:"+e.getMessage());
				 response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			 } catch (Exception e) {
				 logger.error("Error:"+e.getMessage());
				 response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
				 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			 }
	}
	
	@PostMapping("/procesar-prorrateo")
	public ResponseEntity<Map<String, Object>> procesarProrrateo(@RequestParam Map<String, String> params){
		Map<String, Object> response = new HashMap<>();
		try {
			Connection connection = null;
	        CallableStatement callableStatement = null;
	        String errorMessage = null;
	        int estatusProceso = 0;
            
            try {
	            connection = jdbcTemplate.getDataSource().getConnection();
	            callableStatement = connection.prepareCall(Constantes.PROCESO_PRORRATEO);
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
            response.put("estatusProceso", estatusProceso);
			response.put("message", (errorMessage == null)?Constantes.PRORRATEO:errorMessage);
			return ResponseEntity.ok(response);
		} catch (NumberFormatException e) {
			logger.error("Error:"+e.getMessage());
			response.put("message", Constantes.ERROR_FORMATO_NUMERICO + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 } catch (IllegalArgumentException e) {
			 logger.error("Error:"+e.getMessage());
			 response.put("message", Constantes.ERROR_PARAMETROS + e.getMessage());
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 } catch (Exception e) {
			 logger.error("Error:"+e.getMessage());
			 response.put("message", Constantes.ERROR_GUARDAR + e.getMessage());
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
