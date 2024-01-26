package ahorrosbienestar.sib.std.operaciones.configuracion.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.cat_catalogos.accion_realizar.model.AccionRealizarModel;
import ahorrosbienestar.sib.std.cat_catalogos.accion_realizar.service.AccionRealizarService;
import ahorrosbienestar.sib.std.cat_catalogos.bloqueo_listas_negras.model.BloqueoListasNegrasModel;
import ahorrosbienestar.sib.std.cat_catalogos.bloqueo_listas_negras.service.BloqueoListasNegrasService;
import ahorrosbienestar.sib.std.catalogos.model.Catalogo;
import ahorrosbienestar.sib.std.catalogos.service.CatalogoService;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.accion.dto.CodigoAccionDTO;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.accion.model.CodigoAccionModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.accion.service.CodigoAccionService;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.dto.RelacionDTO;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.model.RelacionCodigosModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.service.RelacionCodigosService;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.resultado.model.CodigoResultadoModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.resultado.service.CodigoResultadoService;
import ahorrosbienestar.sib.std.operaciones.configuracion.telefonos.dto.RegistroTelefonoDTO;
import ahorrosbienestar.sib.std.operaciones.configuracion.telefonos.model.TelefonoModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.telefonos.service.TelefonoService;
import ahorrosbienestar.sib.std.sucursales.model.SucursalesModel;
import ahorrosbienestar.sib.std.sucursales.service.SucursalesService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RestOpConfiguracionController {

	@Autowired
	private SucursalesService sucursalesService;

	@Autowired
	private TelefonoService telefonoService;

	@Autowired
	private CodigoAccionService codigoAccionService;

	@Autowired
	private CodigoResultadoService codigoResultadoService;

	@Autowired
	private RelacionCodigosService relacionCodigosService;

	@Autowired
	private AccionRealizarService accionRealizarService;

	@Autowired
	private BloqueoListasNegrasService bloqueoListasNegrasService;

	@Autowired
	private CatalogoService catalogoService;;

	@Autowired
	private UsuarioService usuarioService;

	private static final Logger logger = Logger.getLogger(RestOpConfiguracionController.class);

	private static final int idGrupo = 13;

	/**
	 * Buscar sucursal por caracter
	 * 
	 * @param sucursal
	 * @return
	 */
	@RequestMapping(value = { "/buscar_sucursal" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaDeSucursales(@RequestParam("sucursal") String sucursal) {
		Map<String, Object> respuesta = new HashMap<>();
		List<SucursalesModel> listaSucursales = new ArrayList<SucursalesModel>();
		String mensajeError = null;
		try {
			listaSucursales = sucursalesService.buscarSucursalPorNombre(sucursal);
			logger.info("se encontró la sucusal");
			respuesta.put("sucursal", listaSucursales);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	/**
	 * Lista de todos los telefonos
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/lista_telefonos" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaDeTelefonos() {

		Map<String, Object> response = new HashMap<>();
		List<TelefonoModel> listTel = new ArrayList<TelefonoModel>();
		String mensajeError = null;

		try {

			listTel = telefonoService.listaTelActivos();
			logger.info("se encontró la lista de telefonos");
			response.put("telefonos", listTel);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			response.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	/**
	 * Registrar telefonos
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = {"/registrar_telefono" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> agregarTelefono(@RequestBody RegistroTelefonoDTO dto) {

		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		TelefonoModel model = new TelefonoModel();
		// fecha
		LocalDate fechaLocal = LocalDate.now();

		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("se encotró el usuario");
			SucursalesModel sucursal = sucursalesService.sucursalPorNombre(dto.getNombreSucursal());
			logger.info("se encontró la sucursal: " + sucursal.getVc_nombresucursal());

			model.setFk_id_sucursal(sucursal);
			model.setVc_telefono(dto.getTelefono());
			model.setVc_extension(dto.getExtension());
			model.setVc_correo_electronico(dto.getCorreoElectronico());
			model.setVc_comentarios(dto.getComentarios());
			model.setI_estatus(1);
			model.setDt_fecha_registro(fechaLocal);
			model.setDt_fecha_modificacion(fechaLocal);
			model.setUsuario(usuario);
			telefonoService.save(model);
			respuesta.put("mensaje", "Teléfono registrado");
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Buscar telefono por id
	 * 
	 * @param idTelefono
	 * @return
	 */
	@RequestMapping(value = { "/buscar_telefono" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> buscarTelefono(@RequestParam("idTelefono") int idTelefono) {

		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		TelefonoModel model = null;

		try {

			model = telefonoService.buscarTelefonoPorId(idTelefono);
			logger.info("se encontró el telefono: " + idTelefono);
			respuesta.put("telefono", model);
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Actualización de los datos de un telefono
	 * 
	 * @param idTelefono
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = {"/editar_telefono" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> editarTelefono(
			@RequestParam("idTelefono") int idTelefono,
			@RequestBody RegistroTelefonoDTO dto) {

		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		TelefonoModel model = null;
		// fecha
		LocalDate fechaLocal = LocalDate.now();

		try {

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("se encotró el usuario");
			model = telefonoService.buscarTelefonoPorId(idTelefono);
			logger.info("se encontró el telefono: " + idTelefono);

			model.setVc_telefono(dto.getTelefono() != model.getVc_telefono() ? dto.getTelefono() : model.getVc_telefono());
			model.setVc_extension(dto.getExtension() != model.getVc_extension() ? dto.getExtension() : model.getVc_extension());
			model.setVc_correo_electronico(dto.getCorreoElectronico() != model.getVc_correo_electronico() ? dto.getCorreoElectronico() : model.getVc_correo_electronico());
			model.setVc_comentarios(dto.getComentarios() != model.getVc_comentarios() ? dto.getComentarios() : model.getVc_comentarios());
			model.setDt_fecha_modificacion(fechaLocal);
			model.setUsuario(usuario != model.getUsuario() ? usuario : model.getUsuario());
			telefonoService.save(model);
			respuesta.put("mensaje", "Teléfono actualizado");
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Cambiar el estatus de un telefono
	 * 
	 * @param idTelefono
	 * @param estatus
	 * @return
	 */
	@RequestMapping(value = { "/vigencia_telefono" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> vigenciaTelefono(
			@RequestParam("idTelefono") int idTelefono,
			@RequestParam("estatus") int estatus) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		TelefonoModel model = null;
		LocalDate fechaLocal = LocalDate.now();
		try {
			model = telefonoService.buscarTelefonoPorId(idTelefono);
			model.setI_estatus(estatus);
			model.setDt_fecha_modificacion(fechaLocal);
			telefonoService.save(model);
			respuesta.put("mensaje", "Vigencia actualizada");
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

// ******************************************************************************************** //

	/**
	 * Lista de códigos de acción activos
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/lista_codigosA_activos" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaCodigosAccionActivos() {

		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<CodigoAccionModel> lista = new ArrayList<CodigoAccionModel>();

		try {

			lista = codigoAccionService.listaCodigosAccion();
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Cambiar el estatus de un código de acción
	 * 
	 * @param idCodigoAccion
	 * @param estatus
	 * @return
	 */
	@RequestMapping(value = { "/estatus_codigoAccion" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> estatusCodigoAccion(
			@RequestParam("idCodigoAccion") int idCodigoAccion,
			@RequestParam("estatus") int estatus) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		CodigoAccionModel model = new CodigoAccionModel();
		LocalDate fechaLocal = LocalDate.now();
		try {
			model = codigoAccionService.buscarCodigoAccion(idCodigoAccion);
			model.setI_estatus(estatus);
			model.setDt_fecha_modificacion(fechaLocal);
			codigoAccionService.save(model);
			respuesta.put("mensaje", "estatus actualizado");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Lista de los códigos de acción activos
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/lista_codigosAccion" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaCodigosAccion() {

		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<CodigoAccionModel> lista = new ArrayList<CodigoAccionModel>();

		try {

			lista = codigoAccionService.listaCodigosAccion();
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Registro de un código de acción
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = {"/registro_codigosAccion" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> registrarCodigoAccion(@RequestBody CodigoAccionDTO dto) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		CodigoAccionModel model = new CodigoAccionModel();
		// fecha
		LocalDate fechaLocal = LocalDate.now();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("se encotró el usuario");
			model.setVc_codigo(dto.getCodigo());
			model.setVc_descripcion(dto.getDescripcion());
			model.setI_estatus(1);
			model.setDt_fecha_registro(fechaLocal);
			model.setDt_fecha_modificacion(fechaLocal);
			model.setUsuario(usuario);
			codigoAccionService.save(model);
			respuesta.put("mensaje", "Código registrado");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Buscar los datos de un código de acción por id
	 * 
	 * @param idCodigoAccion
	 * @return
	 */
	@RequestMapping(value = { "/buscar_codigoAccion" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> buscarCodigoAccion(@RequestParam("idCodigoAccion") int idCodigoAccion) {

		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		CodigoAccionModel model = new CodigoAccionModel();

		try {

			model = codigoAccionService.buscarCodigoAccion(idCodigoAccion);
			logger.info("se encontró el codigo: " + model.getVc_descripcion());
			respuesta.put("codigoAccion", model);
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Editar los datos de un código de acción
	 * 
	 * @param idCodigoAccion
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = {"/editar_codigoAccion" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> editarCodigoAccion(
			@RequestParam("idCodigoAccion") int idCodigoAccion,
			@RequestBody CodigoAccionDTO dto) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		CodigoAccionModel model = new CodigoAccionModel();
		// fecha
		LocalDate fechaLocal = LocalDate.now();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("se encotró el usuario");
			model = codigoAccionService.buscarCodigoAccion(idCodigoAccion);
			logger.info("se encontró el código: " + model.getVc_descripcion());
			model.setVc_codigo(dto.getCodigo() != model.getVc_codigo() ? dto.getCodigo() : model.getVc_codigo());
			model.setVc_descripcion(dto.getDescripcion() != model.getVc_descripcion() ? dto.getDescripcion() : model.getVc_descripcion());
			model.setDt_fecha_modificacion(fechaLocal);
			model.setUsuario(usuario != model.getUsuario() ? usuario : model.getUsuario());
			codigoAccionService.save(model);
			respuesta.put("mensaje", "Código actualizado");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	// ********************************************************************************************
	// //

	/**
	 * Lista de los codigos de acceso activos
	 * @return
	 */
	@RequestMapping(value = { "/lista_codigosR_activos" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaCodigosResultadoActivos() {

		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<CodigoResultadoModel> lista = new ArrayList<CodigoResultadoModel>();

		try {

			lista = codigoResultadoService.listaCodigoResultadoActivos();
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Cambiar el estatus de un código de resultado
	 * 
	 * @param idCodigoResultado
	 * @param estatus
	 * @return
	 */
	@RequestMapping(value = { "/estatus_codigoResultado" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> estatusCodigoResultado(
			@RequestParam("idCodigoResultado") int idCodigoResultado, 
			@RequestParam("estatus") int estatus) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		CodigoResultadoModel model = new CodigoResultadoModel();
		LocalDate fecha = LocalDate.now();
		try {
			model = codigoResultadoService.buscarCodigoResultado(idCodigoResultado);
			model.setI_estatus(estatus);
			model.setDt_fecha_modificacion(fecha);
			codigoResultadoService.save(model);
			respuesta.put("mensaje", "estatus actualizado");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Lista de los códigos de resultado activos
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/lista_codigosResultado" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaCodigosResultado() {

		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<CodigoResultadoModel> lista = new ArrayList<CodigoResultadoModel>();

		try {

			lista = codigoResultadoService.listaCodigoResultado();
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Registro de un código de resultado
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = {"/registro_codigoResultado" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> registrarCodigoResultado(@RequestBody CodigoAccionDTO dto) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		CodigoResultadoModel model = new CodigoResultadoModel();
		// fecha
		LocalDate fechaLocal = LocalDate.now();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("se encotró el usuario");
			model.setVc_codigo(dto.getCodigo());
			model.setVc_descripcion(dto.getDescripcion());
			model.setI_estatus(1);
			model.setDt_fecha_registro(fechaLocal);
			model.setDt_fecha_modificacion(fechaLocal);
			model.setUsuario(usuario);
			codigoResultadoService.save(model);
			respuesta.put("mensaje", "Código registrado");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Buscar los datos de un código de resultado por id
	 * 
	 * @param idCodigoResultado
	 * @return
	 */
	@RequestMapping(value = { "/buscar_codigoResultado" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> buscarCodigoResultado(
			@RequestParam("idCodigoResultado") int idCodigoResultado) {

		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		CodigoResultadoModel model = new CodigoResultadoModel();

		try {

			model = codigoResultadoService.buscarCodigoResultado(idCodigoResultado);
			logger.info("se encontró el codigo: " + model.getVc_descripcion());
			respuesta.put("codigoResultado", model);
			return ResponseEntity.ok(respuesta);

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Editar los datos de un código de resultado
	 * 
	 * @param idCodigoResultado
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = {"/editar_codigoResultado" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> editarCodigoResultado(
			@RequestParam("idCodigoResultado") int idCodigoResultado, 
			@RequestBody CodigoAccionDTO dto) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		CodigoResultadoModel model = new CodigoResultadoModel();
		// fecha
		LocalDate fechaLocal = LocalDate.now();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("se encotró el usuario");
			model = codigoResultadoService.buscarCodigoResultado(idCodigoResultado);
			logger.info("se encontró el código: " + model.getVc_descripcion());
			model.setVc_codigo(dto.getCodigo() != model.getVc_codigo() ? dto.getCodigo() : model.getVc_codigo());
			model.setVc_descripcion(dto.getDescripcion() != model.getVc_descripcion() ? dto.getDescripcion() : model.getVc_descripcion());
			model.setDt_fecha_modificacion(fechaLocal);
			model.setUsuario(usuario != model.getUsuario() ? usuario : model.getUsuario());
			codigoResultadoService.save(model);
			respuesta.put("mensaje", "Código actualizado");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	// ********************************************************************************************
	// //

	/**
	 * Lista de los valores de la ultima gestion vigente
	 * @return
	 */
	@RequestMapping(value = { "/lista_gestionVigente" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaGestionVigente() {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<Catalogo> lista = new ArrayList<Catalogo>();
		try {
			lista = catalogoService.generarOpciones(idGrupo);
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Lista de relaciones
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/lista_relacionCodigos" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaRelacionCodigos() {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<RelacionCodigosModel> lista = new ArrayList<RelacionCodigosModel>();
		try {
			lista = relacionCodigosService.listaRelaciones();
			logger.info("Se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Buscar una relación por id
	 * 
	 * @param idRelacion
	 * @return
	 */
	@RequestMapping(value = { "/buscar_relacionCodigo" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> buscarRelacion(@RequestParam("idRelacion") int idRelacion) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		RelacionCodigosModel model = new RelacionCodigosModel();
		try {
			model = relacionCodigosService.buscarRelacionPorId(idRelacion);
			respuesta.put("relacion", model);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Agregar una relación
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = {"/agregar_relacionCodigo" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> agregarRelacion(@RequestBody RelacionDTO dto) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		RelacionCodigosModel model = new RelacionCodigosModel();
		CodigoAccionModel codigoAccion = new CodigoAccionModel();
		CodigoResultadoModel codigoResultado = new CodigoResultadoModel();
		AccionRealizarModel accionRealizar = new AccionRealizarModel();
		BloqueoListasNegrasModel bloqueo = new BloqueoListasNegrasModel();
		// fecha
		LocalDate fechaLocal = LocalDate.now();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("se encotró el usuario");
			codigoAccion = codigoAccionService.buscarCodigoAccion(dto.getIdCodigoAccion());
			logger.info("código Acción: " + codigoAccion.getVc_descripcion());
			codigoResultado = codigoResultadoService.buscarCodigoResultado(dto.getIdCodigoResultado());
			logger.info("código Resultado: " + codigoResultado.getVc_descripcion());
			accionRealizar = accionRealizarService.buscarAccionRealizarId(dto.getIdAccionRealizar());
			logger.info("acción realizar: " + accionRealizar.getVc_nombre());
			bloqueo = bloqueoListasNegrasService.buscarBloqueoPorId(dto.getIdBloqueo());
			logger.info("bloqueo: " + bloqueo.getVc_nombre());
			Catalogo catalogo = catalogoService.buscarPorId(dto.getUltimaGestion());
			logger.info("ultima gestión encontrada");
			model.setCodigoAccionModel(codigoAccion);
			model.setCodigoResultadoModel(codigoResultado);
			model.setAccionRealizarModel(accionRealizar);
			model.setCatalogo(catalogo);
			model.setBloqueoListasNegrasModel(bloqueo);
			model.setI_estatus(1);
			model.setDt_fecha_registro(fechaLocal);
			model.setDt_fecha_modificacion(fechaLocal);
			model.setUsuario(usuario);
			relacionCodigosService.save(model);
			respuesta.put("mensaje", "Relación registrada");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

	/**
	 * Editar una relación
	 * 
	 * @param dto
	 * @param idRelacion
	 * @return
	 */
	@RequestMapping(value = {"/editar_relacionCodigo" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> editarRelacion(
			@RequestBody RelacionDTO dto,
			@RequestParam("idRelacion") int idRelacion) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		RelacionCodigosModel model = new RelacionCodigosModel();
		CodigoAccionModel codigoAccion = new CodigoAccionModel();
		CodigoResultadoModel codigoResultado = new CodigoResultadoModel();
		AccionRealizarModel accionRealizar = new AccionRealizarModel();
		BloqueoListasNegrasModel bloqueo = new BloqueoListasNegrasModel();
		// fecha
		LocalDate fechaLocal = LocalDate.now();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("se encotró el usuario");
			model = relacionCodigosService.buscarRelacionPorId(idRelacion);
			logger.info("relación");
			codigoAccion = codigoAccionService.buscarCodigoAccion(dto.getIdCodigoAccion());
			logger.info("código Acción: " + codigoAccion.getVc_descripcion());
			codigoResultado = codigoResultadoService.buscarCodigoResultado(dto.getIdCodigoResultado());
			logger.info("código Resultado: " + codigoResultado.getVc_descripcion());
			accionRealizar = accionRealizarService.buscarAccionRealizarId(dto.getIdAccionRealizar());
			logger.info("acción realizar: " + accionRealizar.getVc_nombre());
			bloqueo = bloqueoListasNegrasService.buscarBloqueoPorId(dto.getIdBloqueo());
			logger.info("bloqueo: " + bloqueo.getVc_nombre());
			Catalogo catalogo = catalogoService.buscarPorId(dto.getUltimaGestion());
			logger.info("ultima gestión encontrada");
			model.setCodigoAccionModel(model.getCodigoAccionModel() != codigoAccion ? codigoAccion : model.getCodigoAccionModel());
			model.setCodigoResultadoModel(model.getCodigoResultadoModel() != codigoResultado ? codigoResultado : model.getCodigoResultadoModel());
			model.setAccionRealizarModel(model.getAccionRealizarModel() != accionRealizar ? accionRealizar : model.getAccionRealizarModel());
			model.setCatalogo(catalogo != model.getCatalogo() ? catalogo : model.getCatalogo());
			model.setBloqueoListasNegrasModel(model.getBloqueoListasNegrasModel() != bloqueo ? bloqueo : model.getBloqueoListasNegrasModel());
			model.setDt_fecha_modificacion(fechaLocal);
			model.setUsuario(usuario != model.getUsuario() ? usuario : model.getUsuario());
			relacionCodigosService.save(model);
			respuesta.put("mensaje", "Relación actualizada");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}
	
	
	/**
	 * Actualizar el estatus de una relación
	 * @param idRleacion
	 * @param estatus
	 * @return
	 */
	@RequestMapping(value = { "/estatus_Relacion" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> estatusRelacion(
			@RequestParam("idRleacion") int idRleacion, 
			@RequestParam("estatus") int estatus) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		RelacionCodigosModel model = new RelacionCodigosModel();
		LocalDate fecha = LocalDate.now();
		try {
			model = relacionCodigosService.buscarRelacionPorId(idRleacion);
			model.setI_estatus(estatus);
			model.setDt_fecha_modificacion(fecha);
			relacionCodigosService.save(model);
			respuesta.put("mensaje", "estatus actualizado");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("error: " + e.getMessage());
			mensajeError = e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.ok(respuesta);
		}
	}

}
