package ahorrosbienestar.sib.std.credito.campaniastemporales.controller;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.credito.campaniastemporales.dto.CampaniaTemporalDTO;
import ahorrosbienestar.sib.std.credito.campaniastemporales.dto.ResultadoCargaExcel;
import ahorrosbienestar.sib.std.credito.campaniastemporales.service.DetalleCampaniasTemporalesService;
import ahorrosbienestar.sib.std.credito.tiposCampanias.dto.TiposCampaniasDTO;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.credito.tiposCampanias.service.TiposCampaniasService;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("credito")
public class CampaniasTemporalesController {
	private static final Logger logger = Logger.getLogger(CampaniasTemporalesController.class);

	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;

	@Autowired
	private IUsuarioRepository iUsuarioRepository;

	@Autowired
	private OpcionesMenuService opcionesMenuService;

	@Autowired
	private TiposCampaniasService tiposCampaniasService;

	@Autowired
	private DetalleCampaniasTemporalesService detalleCampaniasTemporalesService;

	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	List<Usuario> usuario = null;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/campaniasTemporales")
	public String indexReportes(Model model) {

		try {
			String rutaDireccion;

			if (validarAcceso(Constantes.CAMPANIAS_TEMPORALES) == 1) {
				model.addAttribute("cat_estatus",
						iCatalogoRepository.generarOpciones(Constantes.CAT_ESTATUS_CAMPANIAS));
				rutaDireccion = Constantes.CAMPANIAS_TEMPORALES;

			} else {
				rutaDireccion = Constantes.RUTA_ERROR;
			}

			return rutaDireccion;
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return null;
		}
	}

	/**
	 * 
	 * @param rutaController
	 * @return
	 */
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

			return iRelRolOpcionesRepository.validarAcceso(usuario.get(0).getRol_idRol().getPk_id_rol(),
					opcionesMenuService.buscarRelacionController(rutaController));

		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
			return 0;
		}
	}

	@PostMapping("/procesarArchivo")
	public ResponseEntity<?> adjuntar(@RequestParam("file") MultipartFile[] files,
			@RequestParam("idCmpTmp") Integer idCmpTmp, @RequestParam("codCampania") String codCampania) {

		String mensaje = Constantes.REGISTRO_OK;
		String tipo = Constantes.MENSAJE_OK;
		Map<String, Object> respuesta = new HashMap<>();
		ResultadoCargaExcel resultado = new ResultadoCargaExcel();

		try {
			Optional<TiposCampaniasModel> campaniaConsulta = tiposCampaniasService
					.obtenerCampaniaxID(idCmpTmp);
			resultado = detalleCampaniasTemporalesService.detCampaniasTmp(files[0],campaniaConsulta, usuario.get(0));

			if (resultado.isTieneIncidencias()) {
				String contenido = String.join(System.lineSeparator(), resultado.getIncidencias());

				byte[] bytes = contenido.getBytes(StandardCharsets.UTF_8);

				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=incidencias.txt")
						.contentType(MediaType.TEXT_PLAIN).contentLength(bytes.length).body(bytes);
			} 

			if(guardarDatosTemporales(idCmpTmp
					,null
					,null
					,null
					,null
					,null
					,obtUsuario().getPk_id_usuario()
					,Constantes.TIPO_RELACIONAR_REGISTROS) == null) {
				//obtener la lista de proceso.
			}
			
			respuesta.put(Constantes.TEXTO_MNS, mensaje);
			respuesta.put(Constantes.TIPO_MNS, tipo);

		} catch (Exception e) {
			mensaje = Constantes.ACTUALIZACION_ERROR;
			tipo = Constantes.MENSAJE_ERROR;
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());
		}
		return ResponseEntity.ok(respuesta);
	}
	
	
	@PostMapping("/registroCmpTmp")
	public ResponseEntity<?> guardarCampania(@ModelAttribute CampaniaTemporalDTO cmptmpdto, 
			@RequestParam("codCampania") String codCampania) {
		try {

			String mensaje = "";
			String tipo = "";
			int existenombre = 0;
			Map<String, Object> respuesta = new HashMap<>();

			if (cmptmpdto.getIdCampaniaSeleccionada() != null) {
				Optional<TiposCampaniasModel> campaniaConsulta = tiposCampaniasService
						.obtenerCampaniaxID(cmptmpdto.getIdCampaniaSeleccionada());

				existenombre = tiposCampaniasService.existeNombre(cmptmpdto.getNombreCampania());
				if (campaniaConsulta.isPresent()) {

					// if(existenombre == 0) {
					// Ya existe → ACTUALIZACIÓN
					TiposCampaniasModel existente = campaniaConsulta.get();
					existente.setVc_tipo_campania(cmptmpdto.getNombreCampania().toUpperCase());
					existente.setI_vigencia_campania(cmptmpdto.getVigenciaMeses());
					existente.setDt_fecha_registro(LocalDate.now());
					existente.setI_estatus(cmptmpdto.getEstatusCampania());
					existente.setI_user_reg(obtUsuario());
					existente.setI_tipo_campania(cmptmpdto.getRecurrente() == true ? Constantes.CAMPANIA_TIPO_RECURRENTE
							: Constantes.CAMPANIA_TIPO_TEMPORAL);
					// existente.setI_tipo_campania(cmptmpdto.get);

					if (tiposCampaniasService.save(existente) != null) {
						
					
						mensaje = Constantes.ACTUALIZACION_OK;
						tipo = Constantes.MENSAJE_OK;
					} else {
						mensaje = Constantes.ACTUALIZACION_ERROR;
						tipo = Constantes.MENSAJE_ERROR;
					}
					// }else {
					// mensaje = Constantes.NOMBRE_CAMPANIA_EXISTE;
					// tipo = Constantes.MENSAJE_ERROR;
					// }

				}
			} else {

				if (existenombre == 0) {
					// NO EXISTE → NUEVO REGISTRO
					TiposCampaniasModel nuevo = new TiposCampaniasModel();
					nuevo.setVc_tipo_campania(cmptmpdto.getNombreCampania());
					nuevo.setI_vigencia_campania(cmptmpdto.getVigenciaMeses());
					nuevo.setI_tipo_campania(Constantes.CAMPANIA_TIPO_TEMPORAL);
					nuevo.setDt_fecha_registro(LocalDate.now());
					nuevo.setI_estatus(cmptmpdto.getEstatusCampania());
					nuevo.setI_user_reg(obtUsuario());

					if (tiposCampaniasService.save(nuevo) != null) {
						
						if(guardarDatosTemporales(nuevo.getPk_id_tipo_campania()
								,nuevo.getPk_id_tipo_campania() + codCampania.substring(codCampania.indexOf("-"))
								,cmptmpdto.getFechaRegistro()
								,cmptmpdto.getFechaInicio()
								,cmptmpdto.getFechaFin()
								,cmptmpdto.getFechaEnvioMC()
								,obtUsuario().getPk_id_usuario()
								,Constantes.TIPO_CREAR_PROCESO) == null) {
							//obtener la lista de proceso.
						}
						
						mensaje = Constantes.REGISTRO_OK;
						tipo = Constantes.MENSAJE_OK;
					} else {
						mensaje = Constantes.ACTUALIZACION_ERROR;
						tipo = Constantes.MENSAJE_ERROR;
					}
				} else {
					mensaje = Constantes.NOMBRE_CAMPANIA_EXISTE;
					tipo = Constantes.MENSAJE_ERROR;
				}

			}

			respuesta.put(Constantes.TEXTO_MNS, mensaje);
			respuesta.put(Constantes.TIPO_MNS, tipo);
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_LOGGER_ERROR + e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

	@GetMapping("/obtParamCampaniasTmp")
	public ResponseEntity<TiposCampaniasModel> obtCampanias(@RequestParam int valorCampaniaID) {
		TiposCampaniasModel parametros = tiposCampaniasService.obtenerCampaniaPorId(valorCampaniaID);

		if (parametros == null) {
			return ResponseEntity.noContent().build(); // Devuelve 204
		} else {
			return ResponseEntity.ok(parametros); // Devuelve JSON 200
		}

	}

	@GetMapping("/buscarCampaniasTemporales")
	@ResponseBody
	public List<TiposCampaniasDTO> obtCampanias(@RequestParam String valorBusqueda) {
		return tiposCampaniasService.buscarCampaniasTemporales(valorBusqueda);
	}

	public Usuario obtUsuario() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (!(principal instanceof UserDetails)) {
			logger.error(Constantes.USUARIO_NO_AUNTENTICADO);
		}

		UserDetails userDetails = (UserDetails) principal;
		usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
		return usuario.get(0);

	}
	
	public String guardarDatosTemporales(int campaniaID, String codCampania
			,String fechaRegistro,String fechaIncio, String fechaFin
			,String fechaEnvioMC, Integer idUsuario ,Integer tipoProceso) {
				
		
		// la variable tipo de proceso, permite identificar si se gnera un proceso o se relaciona los registros al proceso ya creado valores 1 o 2
		 Connection connection = null;
		 CallableStatement callableStatement = null;
		 String errorMessage="";
	    try {
	    
            connection = jdbcTemplate.getDataSource().getConnection();
            callableStatement = connection.prepareCall(Constantes.PROCESO_DATOS_CAM_TEMPORALES);

            callableStatement.registerOutParameter(1, Types.VARCHAR); // OUT error_message
            callableStatement.setInt(2, campaniaID);                   // campaniaID
            callableStatement.setString(3, codCampania);  			   // codCampania
            callableStatement.setString(4, fechaRegistro);  			   // fechaRegistro
            callableStatement.setString(5, fechaIncio);  			   // fechaIncio
            callableStatement.setString(6, fechaFin);  			   // fechaFin
            callableStatement.setString(7, fechaEnvioMC);  			   // fechaEnvioMC
            callableStatement.setInt(8, idUsuario);  			   // idUsuario
            callableStatement.setInt(9, tipoProceso); 			   // tipoProceso
        

            callableStatement.execute();

  
            errorMessage = callableStatement.getString(1);
	    } catch (Exception e) {
	        logger.error("Error al ejecutar SP_PROCESAR_INFORMACION_CAMPANIAS", e);
	        return e.getMessage();
	    }
		return errorMessage;
	}
}
