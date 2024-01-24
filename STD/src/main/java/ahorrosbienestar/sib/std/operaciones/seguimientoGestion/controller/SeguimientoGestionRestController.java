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

import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.model.RelacionCodigosModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.codigos.relacion.service.RelacionCodigosService;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.model.ListaSeguimientoModel;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.listaSeguimiento.service.ListaSeguimientoService;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.dto.RegistroSeguimientoDTO;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.model.SeguimientoGestionOaModel;
import ahorrosbienestar.sib.std.operaciones.seguimientoGestion.registroSeguimiento.service.SeguimientoGestionOaService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
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

	private static final Logger logger = Logger.getLogger(SeguimientoGestionRestController.class);

	@RequestMapping(value = { "/lista_seguimiento" }, method = RequestMethod.GET)
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

	@RequestMapping(value = { "/parametros_seguimiento" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametrosDelSeguimiento(@RequestParam("nombre") String nombre,
			@RequestParam("oferta") String oferta, @RequestParam("referencia") String referencia) {
		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		List<ListaSeguimientoModel> listaSeguimiento = new ArrayList<ListaSeguimientoModel>();
		try {
			listaSeguimiento = listaSeguimientoService.busquedaPorSeguimiento(nombre, oferta, referencia);
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
			model.setRelacionCodigosModel(relacionCodigosModel);
			model.setVc_descripcion(dto.getDescripcion());
			model.setI_estatus(1);
			model.setDt_fecha_registro(localDate);
			model.setDt_fecha_modificacion(localDate);
			model.setI_usuario_registro(usuario);
			model.setI_usuario_modificacion(usuario);
			seguimientoGestionOaService.save(model);
			logger.info("se registró el seguimiento");
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

}
