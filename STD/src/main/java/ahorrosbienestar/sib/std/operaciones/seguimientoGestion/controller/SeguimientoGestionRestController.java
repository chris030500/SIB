package ahorrosbienestar.sib.std.operaciones.seguimientoGestion.controller;

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
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.model.AsignacionOfertas;
import ahorrosbienestar.sib.std.ofertasAutomaticas.gestion.service.AsignacionOfertasService;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.consultarRelaciones.model.ConsultarRelacionesModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.consultarRelaciones.service.ConsultarRelacionesService;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.model.RelacionCodigosModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.service.RelacionCodigosService;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.model.EstatusSeguimientoModel;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.model.ListaSeguimientoModel;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.service.EstatusSeguimientoService;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.service.ListaSeguimientoService;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.dto.RegistroSeguimientoDTO;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.model.SeguimientoGestionOaModel;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.service.SeguimientoGestionOaService;
import ahorrosbienestar.sib.std.usuarios.dto.MicrofinUsuarioDTO;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.MicrofinUsuarioService;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SeguimientoGestionRestController {

	@Autowired
	private ListaSeguimientoService listaSeguimientoService;

	@Autowired
	private SeguimientoGestionOaService seguimientoGestionOaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RelacionCodigosService relacionCodigosService;

	@Autowired
	private AsignacionOfertasService asignacionOfertasService;
	
	@Autowired
	private ConsultarRelacionesService consultarRelacionesService;
	
	@Autowired
	private MicrofinUsuarioService microfinUsuarioService;
	
	@Autowired
	private EstatusSeguimientoService estatusSeguimientoService;

	private static final Logger logger = Logger.getLogger(SeguimientoGestionRestController.class);

	private static final String accionRealizar = "MOSTRAR CALENDARIO";
	
	private static final String excluir = "EXCLUIR CARGA";
	private static final String bloqueo1 = "INDEFINIDO";
	private static final String bloqueo2 = "90 DIAS";

	/**
	 * Lista de las ofertas
	 * 
	 * @return
	 */
	@RequestMapping(value = {"/lista_seguimiento"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listaDeSeguimientoDeGestion() {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<ListaSeguimientoModel> listaSeguimiento = new ArrayList<ListaSeguimientoModel>();
		try {
			listaSeguimiento = listaSeguimientoService.listaSeguimiento();
			logger.info("se llenó la lista");
			respuesta.put("lista", listaSeguimiento);
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
	 * Parametros para la busqueda de una oferta
	 * 
	 * @param nombre
	 * @param oferta
	 * @param referencia
	 * @return
	 */
	@RequestMapping(value = {"/parametros_seguimiento"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametrosDelSeguimiento(@RequestParam("nombre") String nombre,
			@RequestParam("oferta") String oferta, @RequestParam("referencia") String referencia) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<ListaSeguimientoModel> listaSeguimiento = new ArrayList<ListaSeguimientoModel>();
		try {
			listaSeguimiento = listaSeguimientoService.busquedaPorSeguimiento(nombre, oferta, referencia, "");
			logger.info("se llenó la lista");
			respuesta.put("lista", listaSeguimiento);
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
	 * Registro de un seguimiento de gestión
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = {"/registro_seguimiento"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> registroSeguimientoGestionOa(@RequestBody RegistroSeguimientoDTO dto) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		SeguimientoGestionOaModel model = new SeguimientoGestionOaModel();
		LocalDate localDate = LocalDate.now();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("se encotró el usuario");
			//
			RelacionCodigosModel relacionCodigosModel = relacionCodigosService.buscarRelacionPorId(dto.getRelacionCodigos());
			logger.info("se encontró la relación");
			//
			AsignacionOfertas asignacionOfertas = asignacionOfertasService.findById(dto.getAsignacionOfertas());
			logger.info("Se encontró la asignacion");
			//
			model.setAsignacionOfertas(asignacionOfertas);
			model.setRelacionCodigosModel(relacionCodigosModel);
			model.setVc_no_referencia(dto.getReferencia());
			model.setVc_descripcion(dto.getDescripcion());
			model.setI_estatus(1);
			model.setDt_fecha_cita(dto.getFechaCita());
			model.setDt_fecha_gestion_realizada(dto.getFechaGestion());
			model.setDt_fecha_registro(localDate);
			model.setDt_fecha_modificacion(localDate);
			model.setI_usuario_registro(usuario);
			model.setI_usuario_modificacion(usuario);
			seguimientoGestionOaService.save(model);
			//
			int bloqueo = actualizarAsignacion(dto.getRelacionCodigos(), dto.getAsignacionOfertas());
			//
			logger.info("se registró el seguimiento");
			respuesta.put("valor", bloqueo);
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
	 * Editar una Gestión
	 * 
	 * @param idGestion
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = {"/editar_seguimiento"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> editarSeguimientoGestionOa(@RequestParam("idGestion") int idGestion,
			@RequestBody RegistroSeguimientoDTO dto) {
		String mensajeError = null;
		SeguimientoGestionOaModel model = new SeguimientoGestionOaModel();
		LocalDate localDate = LocalDate.now();
		Map<String, Object> respuesta = new HashMap<>();
		try {
			model = seguimientoGestionOaService.findById(idGestion);
			logger.info("se encotró la gestión");
			//
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario usuario = usuarioService.findByNombre(auth.getName());
			logger.info("se encotró el usuario");
			//
			RelacionCodigosModel relacionCodigosModel = relacionCodigosService.buscarRelacionPorId(dto.getRelacionCodigos());
			logger.info("se encontró la relación");
			//
			AsignacionOfertas asignacionOfertas = asignacionOfertasService.findById(dto.getAsignacionOfertas());
			logger.info("Se encontró la asignacion");
			//

			if (relacionCodigosModel.getAccionRealizar().getV_valor().equals(accionRealizar)) {
				model.setDt_fecha_cita(model.getDt_fecha_cita() == null || dto.getFechaCita() != model.getDt_fecha_cita() ? dto.getFechaCita() : model.getDt_fecha_cita());
				model.setDt_fecha_gestion_realizada(model.getDt_fecha_gestion_realizada() == null || dto.getFechaGestion() != model.getDt_fecha_gestion_realizada() ? dto.getFechaGestion() : model.getDt_fecha_gestion_realizada());
			}

			model.setAsignacionOfertas(asignacionOfertas != model.getAsignacionOfertas() ? asignacionOfertas : model.getAsignacionOfertas());
			model.setRelacionCodigosModel(relacionCodigosModel != model.getRelacionCodigosModel() ? relacionCodigosModel : model.getRelacionCodigosModel());
			model.setVc_no_referencia(dto.getReferencia() != model.getVc_no_referencia() ? dto.getReferencia() : model.getVc_no_referencia());
			model.setVc_descripcion(dto.getDescripcion() != model.getVc_descripcion() ? dto.getDescripcion() : model.getVc_descripcion());
			model.setDt_fecha_gestion_realizada(dto.getFechaGestion());
			model.setDt_fecha_modificacion(localDate);
			model.setI_usuario_modificacion(usuario);
			seguimientoGestionOaService.save(model);
			//
			int bloqueo = actualizarAsignacion(dto.getRelacionCodigos(), dto.getAsignacionOfertas());
			//
			logger.info("se actualizó el seguimiento");
			respuesta.put("valor", bloqueo);
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
	 * Mostrar la lista de las gestiones por cliente
	 * 
	 * @return
	 */
	@RequestMapping(value = {"/lista_gestiones_por_oferta"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> buscarGestionPorOferta(@RequestParam("asignacion") int asignacion) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<SeguimientoGestionOaModel> lista = new ArrayList<SeguimientoGestionOaModel>();
		try {
			lista = seguimientoGestionOaService.listaGestionPorOferta(asignacion);
			respuesta.put("lista", lista);
			logger.info("se llenó la lista");
			respuesta.put("lista", lista);
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
	 * Mostrar la información del cliente por la oferta
	 * 
	 * @param cliente
	 * @return
	 */
	@RequestMapping(value = {"/ver_cliente"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> recuperarInfoPorCliente(@RequestParam("cliente") String cliente) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<ListaSeguimientoModel> lista = new ArrayList<ListaSeguimientoModel>();
		try {
			lista = listaSeguimientoService.busquedaPorSeguimiento("", "", "", cliente);
			logger.info("se llenó la lista");
			respuesta.put("cliente", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	
	@RequestMapping(value = {"/listaRelaciones_activas"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> relacionCodigosActivos(@RequestParam("valor") String valor) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<ConsultarRelacionesModel> lista = new ArrayList<ConsultarRelacionesModel>();
		System.out.println(valor);
		try {
			lista = consultarRelacionesService.relacionesActivas(valor);
			respuesta.put("listaRelaciones", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
	
	
	@RequestMapping(value = {"/datos_gestion"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> BuscarUnaGestion(@RequestParam("idGestion") int idGestion) {
		//
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		//
		SeguimientoGestionOaModel model = new SeguimientoGestionOaModel();
		try {
			model = seguimientoGestionOaService.findById(idGestion);
			logger.info("se encontró la gestión");
			respuesta.put("model", model);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
	
	
	public int actualizarAsignacion(int idRelacion, int idAsignacion) {
		AsignacionOfertas asignacion = asignacionOfertasService.findById(idAsignacion);
		logger.info("Se encontró la asignación");
		System.out.println("Se encontró la asignación");
		//
		RelacionCodigosModel relacion = relacionCodigosService.buscarRelacionPorId(idRelacion);
		logger.info("Se encnotró la relación");
		System.out.println("Se encontró la relación");
		//
		if (relacion.getBloqueoListas() != null) {
			System.out.println("Paso 1");
			if (relacion.getAccionRealizar().getV_valor().equals(excluir)) {
				System.out.println("Paso 2");
				if (relacion.getBloqueoListas().getV_valor().equals(bloqueo1) || relacion.getBloqueoListas().getV_valor().equals(bloqueo2)) {
					System.out.println("SIIIII");
					asignacion.setI_bloqueo(2);
				}
			}
		}
		
		asignacion.setI_estatus(2);
		asignacionOfertasService.save(asignacion);

		return asignacion.getI_bloqueo();
	}
	
	
	
	@RequestMapping(value = {"/todos_los_estatus"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> estatusDeLasGestiones(@RequestParam("idSucursal") String idSucursal) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<EstatusSeguimientoModel> lista = new ArrayList<EstatusSeguimientoModel>();
		String sucursal;
		try {
			
			if (idSucursal.equals(""))
				sucursal = sucursalPorEmpleado();
			else
				sucursal = idSucursal;
			
			lista = estatusSeguimientoService.listaDeEstatus(Integer.parseInt(sucursal));
			logger.info("se llenó la lista");
			respuesta.put("lista", lista);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}
	
	
	
	public String sucursalPorEmpleado() {
		List<MicrofinUsuarioDTO> lista = new ArrayList<MicrofinUsuarioDTO>();
		//
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario = usuarioService.findByNombre(auth.getName());
		logger.info("se encotró el usuario");
		//
		lista = microfinUsuarioService.obtenerUsuario(usuario.getI_id_empleado());
		logger.info("se llenó la lista");
		return lista.get(0).getSucursalUsuario();
	}
	
}
