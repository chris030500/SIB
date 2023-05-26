package ahorrosbienestar.sib.std.autorizacion.controller;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.autorizacion.descargas.DescargaRestController;

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

import org.apache.log4j.Logger;

@RestController
@AllArgsConstructor
public class AutorizacionRestController {

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

	private static final Logger logger = Logger.getLogger(AutorizacionRestController.class);

	// Metodo para autorizar el estatus de una solicitud a autorizado/rechazado
	@RequestMapping(value = { "/autorizar_la_solicitud" }, method = RequestMethod.GET)
	public GeolocalizacionDTO cambiarEstatusSolicitud(@RequestParam("tipoEstatus") String tipoEstatus,
			@RequestParam("envioID") Integer envioID, @RequestParam("clienteID") Integer clienteID,
			@RequestParam("montoAutorizado") Float montoAutorizado, @RequestParam("producto") String producto,
			@RequestParam("observaciones") String observaciones, @RequestParam("solicitud") String solicitud)
			throws ParseException, IOException, org.json.simple.parser.ParseException {

		//
		GeolocalizacionDTO responseDTO = new GeolocalizacionDTO();

		// Autentificación para obtener el nombre del usuario logueado
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// Obtenemos el usuario logueado
		Usuario usuario = usuarioService.findByNombre(auth.getName());

		// Crear Fecha
		Date diaActual = new Date();

		// Formato fecha
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/mm/dd");

		// Fecha actual
		String fecha = formatoFecha.format(diaActual);
		Date fechaActual = formatoFecha.parse(fecha);
		LocalDate fechaLocal = fechaActual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		// Formato para hora
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

		// Hora actual
		String horaActual = formatoHora.format(diaActual);

		// Solicitud envio
		SolicitudEnvio solicitudEnvio = solicitudEnvioService.obtenerSolicitudEnvio(envioID);

		// si es autorizar
		if (tipoEstatus.equals("autorizar")) {

			responseDTO = descargaRestController.guardarDocto(envioID, clienteID, montoAutorizado, producto, solicitud);
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

			// Definimos los parámetros de espera
//			int intentosMaximos = 3;
//			int tiempoEspera = 2000; // 2 segundos
//			// int tiempoEspera = 120000; // 2 minutos
//			if(responseDTO == null) {
//				// Realizamos los intentos para encontrar el archivo
//				for (int intento = 1; intento <= intentosMaximos; intento++) {
//					responseDTO = descargaRestController.guardarDocto(envioID, clienteID, montoAutorizado, producto);
//				}
//			}

			// si es rechazar
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

	/*
	 * @RequestMapping(value = { "/api_geo" }, method = RequestMethod.GET) public
	 * CredencialesGeoDTO apiGeolocalizacion() { CredencialesGeoDTO respuestaDTO =
	 * new CredencialesGeoDTO(); Configuracion url =
	 * configService.obtenerConfiguracion(ID_GEOLOCALIZACION); Configuracion
	 * autentificacion =
	 * configService.obtenerConfiguracion(AUTENTIFICAION_GEOLOCALIZACION);
	 * respuestaDTO.setUrl(url.getVc_valor_propiedad());
	 * respuestaDTO.setAutentificacion(autentificacion.getVc_valor_propiedad());
	 * return respuestaDTO; }
	 */

	@RequestMapping(value = { "/api_geo" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void llamarApiGeolocalizacion(@RequestBody GeolocalizacionDTO geolocalizacionDTO) {

		Configuracion urlGeo = configService.obtenerConfiguracion(ID_GEOLOCALIZACION);
		Configuracion autentificacionGeo = configService.obtenerConfiguracion(AUTENTIFICAION_GEOLOCALIZACION);

		try {
			URL url = new URL(urlGeo.getVc_valor_propiedad());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("autentificacion", autentificacionGeo.getVc_valor_propiedad());
			connection.setDoOutput(true);

			String requestBody = geolocalizacionDTO.toString();

			OutputStream os = connection.getOutputStream();
			os.write(requestBody.getBytes());
			os.flush();
			os.close();

			int responseCode = connection.getResponseCode();
			logger.info("Response code: " + responseCode);
			// System.out.println("Response code: " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			logger.info("Response body: " + response.toString());
			// System.out.println("Response body: " + response.toString());

		} catch (Exception e) {
			logger.error(e);
			// e.printStackTrace();
		}

	}

}
