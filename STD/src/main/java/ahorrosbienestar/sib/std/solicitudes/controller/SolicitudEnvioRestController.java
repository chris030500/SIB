package ahorrosbienestar.sib.std.solicitudes.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.autorizacion.controller.AutorizacionRestController;
import ahorrosbienestar.sib.std.docto_envio.controller.DoctoEnvioRestController;
import ahorrosbienestar.sib.std.docto_envio.model.DoctEnvio;
import ahorrosbienestar.sib.std.empleados.controller.EmpleadoController;
import ahorrosbienestar.sib.std.empleados.dto.EmpleadoDto;
import ahorrosbienestar.sib.std.empleados.service.EmpleadoService;
import ahorrosbienestar.sib.std.solicitudes.dto.SolicitudEnvioDTO;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SolicitudEnvioRestController {

	// variable buscar estatus a cancelar
	private final static int BUSCAR_PARA_CANCELAR = 1;

	// variable buscar estatus a autorizar
	private final static int BUSCAR_PARA_AUTORIZAR = 3;

	// variable solicitud cancelada manualmente
	private final static int CANCELACION_MANUAL = 4;

	// variable solicitud cancelada automáticamente
	private final static int CANCELACION_AUTO = 5;

	// variable solicitud rechazada
	private final static int RECHAZADA = 7;

	// variable solicitud autorizada
	private final static int AUTORIZADA = 6;

	@Autowired
	private SolicitudEnvioService solicitudEnvioService;

	@Autowired
	private EmpleadoService empleadoService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AutorizacionRestController aController;


	@PostMapping("/solicitudEnvio/guardarSolicitudEnvio")
	public ResponseEntity<?> guardarSolicitudEnvio(
			@RequestParam(name = "solicitud", required = false) Integer solicitud,
			@RequestParam(name = "producto", required = false) Integer producto,
			@RequestParam(name = "comentarioValidacion", required = false) String comentarioValidacion,
			@RequestParam(name = "fEnvio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fEnvio,
			@RequestParam(name = "hEnvio", required = false) String hEnvio,
			@RequestParam(name = "fCancelacion", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fCancelacion,
			@RequestParam(name = "hCancelacion", required = false) String hCancelacion) throws IOException {

		// System.out.println("Obtenemos el valor de la solicitud:" + solicitud);
		// Obtenemos el usuario autentificado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// Obtenemos el nombre del usuario autentificado
		Usuario usu = usuarioService.findByNombre(auth.getName());
		// Establecemos el formato de la fecha
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Obtenemos la fecha en String
		String fechaActual = dateFormat.format(Calendar.getInstance().getTime());
		// Obtenemos la fecha actual con el formato yyyy-MM-dd
		LocalDate localDateHoy = LocalDate.parse(fechaActual);

		SolicitudEnvio sEnvio = new SolicitudEnvio();
		sEnvio.setFk_usr_registra(usu);
		sEnvio.setDt_fecha_registro(localDateHoy);
		sEnvio.setI_no_solicitud_core(solicitud);
		sEnvio.setI_producto_id(producto);
		sEnvio.setVc_obser_peticion(comentarioValidacion);
		sEnvio.setDt_fecha_envio(fEnvio);
		sEnvio.setVc_hora_envio(hEnvio);
		sEnvio.setDt_fecha_cancelacion(fCancelacion);
		sEnvio.setVc_hora_cancelacion(hCancelacion);
		sEnvio.setI_estatus(1);

		sEnvio = solicitudEnvioService.save(sEnvio);

		// Aqui vamos a almacenar los archivos PDF para asocialos con la solicitud de
		// envio
		return ResponseEntity.ok(sEnvio);
	}

	@GetMapping("solicitudEnvio/verSolicitudes")
	public List<SolicitudEnvio> estatusSolicAutorizacion() {
		return solicitudEnvioService.estatusSolicAutorizacion();
	}

	@GetMapping("solicitudEnvio/visualizarArchivo/")
	public ResponseEntity<InputStreamResource> visualizarArchivo(
			@RequestParam(name = "nombreArchivo") String nombreArchivo) throws FileNotFoundException {
		// String nombreArchivo =
		// "C:/Users/osvaldo.escamilla/Documents/home/CoordFirma/AnexosContrato.pdf";
		System.out.println("Entro en visualizarArchivo");
		System.out.println("Archivo: " + nombreArchivo);
//		String filePath = "/path/to/file/";
//		String fileName = "fileName.pdf";
		File file = new File(nombreArchivo);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline;filename=" + nombreArchivo);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/pdf")).body(resource);
	}

	// Método que regresa los datos de la solicitud de envio
	@RequestMapping(value = { "/datos_solicitud" }, method = RequestMethod.GET)
	public SolicitudEnvioDTO obtenerSolicitudEnvio(@RequestParam("solicitudId") Integer solicitudId,
			@RequestParam("accion") String accion) {

		// DTO
		SolicitudEnvioDTO responseDTO = new SolicitudEnvioDTO();

		// Model
		SolicitudEnvio model = new SolicitudEnvio();

		// Preguntamos si es una consulta para cancelar
		if (accion.equals("cancelar")) {

			SolicitudEnvio modelNS = new SolicitudEnvio();
			SolicitudEnvio modelWS = new SolicitudEnvio();

			// Buscamos los dos tipos de datos
			modelNS = solicitudEnvioService.obtenerUltimaSolicitudFecha(solicitudId, BUSCAR_PARA_CANCELAR);
			modelWS = solicitudEnvioService.obtenerUltimaSolicitudFecha(solicitudId, BUSCAR_PARA_AUTORIZAR);

			if (modelNS != null && modelWS != null) {
				if (modelNS.getDt_fecha_registro().isAfter(modelWS.getDt_fecha_registro())) {
					System.out.println("NS es el más reciente");
					model = solicitudEnvioService.obtenerUltimaSolicitudFecha(solicitudId, BUSCAR_PARA_CANCELAR);
				} else if (modelWS.getDt_fecha_registro().isAfter(modelNS.getDt_fecha_registro())) {
					System.out.println("WS es el más reciente");
					model = solicitudEnvioService.obtenerUltimaSolicitudFecha(solicitudId, BUSCAR_PARA_AUTORIZAR);
				}
			} else if (modelNS != null) {
				System.out.println("NS es el único con registro");
				model = solicitudEnvioService.obtenerUltimaSolicitudFecha(solicitudId, BUSCAR_PARA_CANCELAR);
			} else if (modelWS != null) {
				System.out.println("WS es el {unico con registro");
				model = solicitudEnvioService.obtenerUltimaSolicitudFecha(solicitudId, BUSCAR_PARA_AUTORIZAR);
			}

			// Preguntamos si es una consulta para autorizar
		} else if (accion.equals("autorizar"))
			model = solicitudEnvioService.obtenerUltimaSolicitudFecha(solicitudId, BUSCAR_PARA_AUTORIZAR);

		if (model != null) {

			// Llenar DTO
			responseDTO = llenarDTO(model);

		}

		return responseDTO;
	}

	// Método que obtiene el listado de las solicitudes por estatus
	@RequestMapping(value = { "/solicitudes_autorizadas" }, method = RequestMethod.GET)
	public List<SolicitudEnvioDTO> listadoSolicitudesEnvio(@RequestParam("solicitudID") Integer solicitudID,
			@RequestParam("accion") String accion) {

		// respuesta
		List<SolicitudEnvioDTO> respuesta = new ArrayList<SolicitudEnvioDTO>();

		// listado de las solicitudes
		List<SolicitudEnvio> listaModel = solicitudEnvioService.obtenerTodasLasSolicitudes(solicitudID);

		listaModel.forEach(solicitud -> {

			if (accion.equals("cancelar")) {

				if (solicitud.getI_estatus() == CANCELACION_MANUAL || solicitud.getI_estatus() == CANCELACION_AUTO) {

					// DTO
					SolicitudEnvioDTO responseDTO = llenarDTO(solicitud);

					// llenamos el DTO
					respuesta.add(responseDTO);
				}
			} else if (accion.equals("autorizar")) {

				if (solicitud.getI_estatus() == AUTORIZADA || solicitud.getI_estatus() == RECHAZADA) {

					// DTO
					SolicitudEnvioDTO responseDTO = llenarDTO(solicitud);

					// llenamos el DTO
					respuesta.add(responseDTO);

				}
			}

		});

		return respuesta;
	}

	// Método que regresa el DTO de solicitudes
	public SolicitudEnvioDTO llenarDTO(SolicitudEnvio model) {

		// DTO
		SolicitudEnvioDTO responseDTO = new SolicitudEnvioDTO();

		// Llenar DTO
		responseDTO.setPk_id_solicitud_envio(model.getPk_id_solicitud_envio());
		responseDTO.setI_no_solicitud_core(model.getI_no_solicitud_core());
		responseDTO.setI_producto_id(model.getI_producto_id());
		responseDTO.setI_estatus(model.getI_estatus());
		responseDTO.setVc_obser_peticion(model.getVc_obser_peticion());
		responseDTO.setVc_obser_autoriza(model.getVc_obser_autoriza());
		responseDTO.setDt_fecha_registro(model.getDt_fecha_registro());
		responseDTO.setDt_fecha_autorizacion(model.getDt_fecha_autorizacion());
		responseDTO.setDt_fecha_envio(model.getDt_fecha_envio());
		responseDTO.setVc_hora_envio(model.getVc_hora_envio());
		responseDTO.setDt_fecha_cancelacion(model.getDt_fecha_cancelacion());
		responseDTO.setVc_hora_cancelacion(model.getVc_hora_cancelacion());
		responseDTO.setFk_usr_registra(model.getFk_usr_registra());
		responseDTO.setFk_usr_autoriza(model.getFk_usr_autoriza());
		responseDTO.setFk_usr_cancela(model.getFk_usr_cancela());
		responseDTO.setVc_obser_cancelacion(model.getVc_obser_cancelacion());
		responseDTO.setVc_requisition_id(model.getVc_requisition_id());

		// Validamos si cuenta con la autorización del usuario
		if (model.getFk_usr_autoriza() != null) {

			// Buscamos al empleado que autorizó
			empleadoService.buscarEmpleadoPorID(model.getFk_usr_autoriza().getI_id_empleado()).forEach(empleado -> {
				// Guardamos el nombre del empleado
				responseDTO.setEmpleado_autorizo(empleado.nombreCompleto);
			});

		}

		// Validamos si cuenta con la cancelación del usuario
		if (model.getFk_usr_cancela() != null) {
			// Buscamos al empleado que canceló
			empleadoService.buscarEmpleadoPorID(model.getFk_usr_cancela().getI_id_empleado()).forEach(empleado -> {
				// Guardamos el nombre del empleado
				responseDTO.setEmpleado_autorizo(empleado.nombreCompleto);
			});
		}

		return responseDTO;

	}

}
