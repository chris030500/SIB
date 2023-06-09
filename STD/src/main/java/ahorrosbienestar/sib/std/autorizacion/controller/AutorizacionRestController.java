package ahorrosbienestar.sib.std.autorizacion.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import ahorrosbienestar.sib.std.autorizacion.descargas.DescargaRestController;
import ahorrosbienestar.sib.std.autorizacion.descargas.dto.DatosDTO;
import ahorrosbienestar.sib.std.autorizacion.descargas.dto.GeolocalizacionDTO;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.docto_envio.service.DoctEnvioService;
import ahorrosbienestar.sib.std.histo_notificacion.model.HistoricoNotificacion;
import ahorrosbienestar.sib.std.histo_notificacion.service.HistoricoNotificacionService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import lombok.AllArgsConstructor;
import ahorrosbienestar.sib.std.autorizacion.respuesta.ConsumirServicioResponse;
import ahorrosbienestar.sib.std.config_ws.bitacora_consumo.model.BitacoraConsumo;
import ahorrosbienestar.sib.std.config_ws.bitacora_consumo.service.BitacoraConsumoService;
import ahorrosbienestar.sib.std.config_ws.cat_conf_ws.model.CatConfWS;
import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.model.WSAutenticacionUser;
import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.service.WSAutenticacionUserService;

import org.apache.log4j.Logger;

@CrossOrigin
@RestController
@AllArgsConstructor
public class AutorizacionRestController {

	// variable proceso origen
	private final static String PROCESO_ORIGEN = "GEOLOCALIZACIÓN";

	// variable estatus de autorizar
	private final static int ESTATUS_AUTORIZAR = 6;

	// variable estatus de rechazar
	private final static int ESTATUS_RECHAZAR = 7;

	// variable para obtener los datos del api de geolocalización
	private final static int ID_GEOLOCALIZACION = 54;
	private final static int AUTENTIFICAION_GEOLOCALIZACION = 55;

	@Autowired
	private SolicitudEnvioService solicitudEnvioService;

	@Autowired
	private DoctEnvioService doctEnvioService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private HistoricoNotificacionService historicoNotificacionService;

	@Autowired
	private DescargaRestController descargaRestController;

	@Autowired
	private ConfigService configService;

	@Autowired
	private WSAutenticacionUserService wsAutenticacionUserService;

	@Autowired
	private BitacoraConsumoService bitacoraConsumoService;

	private static final Logger logger = Logger.getLogger(AutorizacionRestController.class);

	// Metodo para autorizar el estatus de una solicitud a autorizado/rechazado
	@RequestMapping(value = { "/autorizar_la_solicitud" }, method = RequestMethod.GET)
	public DatosDTO cambiarEstatusSolicitud(@RequestParam("tipoEstatus") String tipoEstatus,
			@RequestParam("envioID") Integer envioID, @RequestParam("clienteID") Integer clienteID,
			@RequestParam("montoAutorizado") Float montoAutorizado, @RequestParam("producto") String producto,
			@RequestParam("observaciones") String observaciones, @RequestParam("solicitud") String solicitud,
			@RequestParam("idWSAutenticacion") Integer idWSAutenticacion)
			throws ParseException, IOException, org.json.simple.parser.ParseException {

		//
		DatosDTO responseDTO = new DatosDTO();

		// Autentificación para obtener el nombre del usuario logueado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Obtenemos el usuario logueado
		Usuario usuario = usuarioService.findByNombre(auth.getName());

		// Hora
		Date diaActual = new Date();
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
		String horaActual = formatoHora.format(diaActual);
		
		// Fecha
		LocalDate fechaLocal = LocalDate.now();

		// Solicitud envio
		SolicitudEnvio solicitudEnvio = solicitudEnvioService.obtenerSolicitudEnvio(envioID);

		// si es autorizar
		if (tipoEstatus.equals("autorizar")) {

			responseDTO = descargaRestController.guardarDocto(envioID, clienteID, montoAutorizado, producto, solicitud, idWSAutenticacion);
			logger.info("responseDTO: " + responseDTO);

			if (responseDTO != null) {
				// Seteamos datos de la solicitud de envio
				solicitudEnvio.setI_estatus(ESTATUS_AUTORIZAR);
				solicitudEnvio.setVc_obser_autoriza(observaciones);
				solicitudEnvio.setFk_usr_autoriza(usuario);
				solicitudEnvio.setDt_fecha_autorizacion(fechaLocal);
				solicitudEnvioService.save(solicitudEnvio);

				// Seteamos datos de los documentos de envio
				doctEnvioService.listadoDoctEnvioActivos(solicitudEnvio.getPk_id_solicitud_envio()).forEach(doct -> {
					doct.setI_estatus(ESTATUS_AUTORIZAR);
					doctEnvioService.save(doct);
				});
			}

		} else if (tipoEstatus.equals("rechazar")) {

			// Seteamos datos de la solicitud de envio
			solicitudEnvio.setI_estatus(ESTATUS_RECHAZAR);
			solicitudEnvio.setVc_obser_cancelacion(observaciones);
			solicitudEnvio.setFk_usr_cancela(usuario);
			solicitudEnvio.setDt_fecha_cancelacion(fechaLocal);
			solicitudEnvio.setVc_hora_cancelacion(horaActual);
			solicitudEnvioService.save(solicitudEnvio);

			// Seteamos datos de los documentos de envio
			doctEnvioService.listadoDoctEnvioActivos(solicitudEnvio.getPk_id_solicitud_envio()).forEach(doct -> {
				doct.setI_estatus(ESTATUS_RECHAZAR);
				doct.setPk_usr_cancela(usuario);
				doct.setDt_fecha_cancelacion(fechaLocal);
				doctEnvioService.save(doct);
			});

		}

		// Llamamos el método para enviar el correo
		agregarNotificacion(tipoEstatus, fechaLocal, horaActual, envioID, observaciones);

		return responseDTO;
	}

	// Método que manda notificacion al correo
	public void agregarNotificacion(String tipo, LocalDate fecha, String hora, Integer envioID, String motivo) {

		HistoricoNotificacion model = new HistoricoNotificacion();

		model.setDt_fecha_registro(fecha);
		model.setVc_hora_registro(hora);
		// Seteamos el modelo de la solicitud de envio
		SolicitudEnvio solicitudEnvio = solicitudEnvioService.obtenerSolicitudEnvio(envioID);
		model.setFk_solicitud_envio(solicitudEnvio);

		if (tipo.equals("autorizar"))
			model.setVc_mensaje("Se ha autorizado la solicitud de envio núm " + envioID + ". Motivo:  " + motivo);
		else if (tipo.equals("rechazar"))
			model.setVc_mensaje("Se ha rechazado la solicitud de envio núm " + envioID + ". Motivo:  " + motivo);

		historicoNotificacionService.save(model);
	}

	@RequestMapping(value = { "/api_geo" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public HttpStatus llamarApiGeolocalizacion(@RequestBody DatosDTO datosDTO) throws ParseException {

		try {

			GeolocalizacionDTO respuestaDTO = new GeolocalizacionDTO();
			respuestaDTO.setCustomerNumber(datosDTO.getCustomerNumber());
			respuestaDTO.setNumberProvisions(datosDTO.getNumberProvisions());
			respuestaDTO.setOperationAmount(datosDTO.getOperationAmount());
			respuestaDTO.setTypeDevice(datosDTO.getTypeDevice());
			respuestaDTO.setRegistrationDataTime(datosDTO.getRegistrationDataTime());
			respuestaDTO.setLatitude(datosDTO.getLatitude());
			respuestaDTO.setLongitude(datosDTO.getLongitude());
			respuestaDTO.setAddressGeolocation(datosDTO.getAddressGeolocation());
			respuestaDTO.setOperation(datosDTO.getOperation());
			respuestaDTO.setTypeOperation(datosDTO.getTypeOperation());
			respuestaDTO.setIp(datosDTO.getIp());
			respuestaDTO.setTypeProduct(datosDTO.getTypeProduct());
			respuestaDTO.setIdProduct(datosDTO.getIdProduct());
			respuestaDTO.setIdState(datosDTO.getIdState());
			respuestaDTO.setAccessSystem(datosDTO.getAccessSystem());

			Configuracion urlGeo = configService.obtenerConfiguracion(ID_GEOLOCALIZACION);
			Configuracion autentificacionGeo = configService.obtenerConfiguracion(AUTENTIFICAION_GEOLOCALIZACION);

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.set("autentificacion", autentificacionGeo.getVc_valor_propiedad());
			headers.set("Content-Type", "application/json");

			Gson gson = new Gson();

			final String JSONEntrada = gson.toJson(respuestaDTO);
			logger.info(JSONEntrada);
			HttpEntity<GeolocalizacionDTO> request = new HttpEntity<>(respuestaDTO, headers);

			ConsumirServicioResponse respon = restTemplate.postForObject(urlGeo.getVc_valor_propiedad(), request,
					ConsumirServicioResponse.class);

			final String JSONSalida = gson.toJson(respon);

			bitacoraGeolocalizacion(JSONEntrada, JSONSalida, datosDTO.getIdWSAutenticacion(), datosDTO.getSolicitudEnvio());

			return HttpStatus.OK;

		} catch (Exception e) {
			logger.info(e.getMessage());
			bitacoraGeolocalizacion(e.getMessage(), e.getMessage(), datosDTO.getIdWSAutenticacion(), datosDTO.getSolicitudEnvio());
			return HttpStatus.BAD_REQUEST;
		}

	}

	public HttpStatus bitacoraGeolocalizacion(String jsonEntrada, String jsonSalida, Integer autenticacionUser, Integer solicitudEnvio) throws ParseException {

		// Autentificación para obtener el nombre del usuario logueado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Obtenemos el nombre del usuario autentificado
		Usuario usu = usuarioService.findByNombre(auth.getName());

		// Buscamos wsAutenticacionUser
		WSAutenticacionUser wsAutenticacionUser = wsAutenticacionUserService.findeById(autenticacionUser).get();

		// Buscamos wsRespuestaAutenticacion
		CatConfWS catConfWS = wsAutenticacionUser.getFk_id_conf_ws();

		// Buscamos la solicitud envio
		SolicitudEnvio solicitudModel = solicitudEnvioService.findById(solicitudEnvio).get();

		// Hora
		Date diaActual = new Date();
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
		String horaActual = formatoHora.format(diaActual);
		
		// Fecha
		LocalDate fechaLocal = LocalDate.now();

		// -------------------------- Inicia el guardado de informacion
		// -------------------------- //
		BitacoraConsumo model = new BitacoraConsumo();
		model.setDt_fecha_solicitud(fechaLocal);
		model.setVc_hora_solicitud(horaActual);
		model.setVc_json_envio(jsonEntrada);
		model.setVc_json_respuesta(jsonSalida);
		model.setVc_resultado_consumo(jsonSalida);
		model.setVc_tp_proceso_origen(PROCESO_ORIGEN);
		model.setFk_id_autenticacion_usr(wsAutenticacionUser);
		model.setFk_id_conf_ws(catConfWS);
		model.setFk_id_solicitud_envio(solicitudModel);
		model.setFk_usr_solicita(usu);
		bitacoraConsumoService.save(model);
		logger.info("Geolocalización terminada");
		return HttpStatus.OK;
	}

}
