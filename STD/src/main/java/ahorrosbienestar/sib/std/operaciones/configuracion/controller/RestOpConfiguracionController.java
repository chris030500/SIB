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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.operaciones.configuracion.telefonos.dto.RegistroTelefonoDTO;
import ahorrosbienestar.sib.std.operaciones.configuracion.telefonos.model.TelefonoModel;
import ahorrosbienestar.sib.std.operaciones.configuracion.telefonos.service.TelefonoService;
import ahorrosbienestar.sib.std.sucursales.model.SucursalesModel;
import ahorrosbienestar.sib.std.sucursales.service.SucursalesService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class RestOpConfiguracionController {

	@Autowired
	private SucursalesService sucursalesService;

	@Autowired
	private TelefonoService telefonoService;

	private static final Logger logger = Logger.getLogger(RestOpConfiguracionController.class);

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

	@RequestMapping(value = {"/registrar_telefono" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> agregarTelefono(@RequestBody RegistroTelefonoDTO dto) {

		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		TelefonoModel model = new TelefonoModel();
		// fecha
		LocalDate fechaLocal = LocalDate.now();

		try {

			SucursalesModel sucursal = sucursalesService.sucursalPorNombre(dto.getNombreSucursal());
			logger.info("se encontró la sucursal: " + sucursal.getVc_nombresucursal());

			model.setFk_id_sucursal(sucursal);
			model.setVc_telefono(dto.getTelefono());
			model.setVc_extension(dto.getExtension());
			model.setVc_comentarios(dto.getComentarios());
			model.setI_estatus(1);
			model.setDt_fecha_registro(fechaLocal);
			model.setDt_fecha_modificacion(fechaLocal);
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

			model = telefonoService.buscarTelefonoPorId(idTelefono);
			logger.info("se encontró el telefono: " + idTelefono);

			model.setVc_telefono(
					dto.getTelefono() != model.getVc_telefono() ? dto.getTelefono() : model.getVc_telefono());
			model.setVc_extension(
					dto.getExtension() != model.getVc_extension() ? dto.getExtension() : model.getVc_extension());
			model.setVc_comentarios(dto.getComentarios() != model.getVc_comentarios() ? dto.getComentarios()
					: model.getVc_comentarios());
			model.setDt_fecha_modificacion(fechaLocal);
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

	@RequestMapping(value = {"/vigencia_telefono"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> vigenciaTelefono(
			@RequestParam("idTelefono") int idTelefono,
			@RequestParam("estatus") int estatus) {

		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		TelefonoModel model = null;

		try {

			model = telefonoService.buscarTelefonoPorId(idTelefono);
			model.setI_estatus(estatus);
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
}
