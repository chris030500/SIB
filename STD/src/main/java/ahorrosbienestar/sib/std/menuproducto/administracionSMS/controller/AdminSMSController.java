package ahorrosbienestar.sib.std.menuproducto.administracionSMS.controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.model.EnvioMensajes;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.model.MensajeSMS;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.model.Parametros;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.model.ProcesoAsignacion;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.model.adminSMS;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.service.MensajeSMSService;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.service.ProcesoAsignacionService;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.service.ProcesoSMSService;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.service.adminSMSService;
import ahorrosbienestar.sib.std.menuproducto.parametrosConfiguracion.model.ConfiguracionSistema;
import ahorrosbienestar.sib.std.menuproducto.parametrosConfiguracion.service.ConfiguracionSistemaService;
import ahorrosbienestar.sib.std.reportesJasper.model.ReporteJasper;
import ahorrosbienestar.sib.std.reportesJasper.repository.IReporteRepository;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.model.ProcesoSMS;

@RequestMapping("mensajes")
@RestController
public class AdminSMSController {

	@Autowired
	private adminSMSService mensajesService;

	@Autowired
	private ProcesoAsignacionService procesoService;

	@Autowired
	private MensajeSMSService mensajeService;

	@Autowired
	private ConfiguracionSistemaService configuracionService;
	
	@Autowired
	private IConfiguracionRepository iConfiguracionRepository;
	
	@Autowired 
	private IReporteRepository iReporteRepository;
	
	@Autowired
	private ProcesoSMSService procesoSMSService;

	private static final Logger logger = Logger.getLogger(AdminSMSController.class);
	private static final String nombreReporte = "rpt_sms";
	private static final String controllerReporte = "generarReporte";

	@RequestMapping(value = { "/obtSMS" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametrosDelSeguimiento(@RequestParam("cliente") String cliente,
			@RequestParam("sucur") String sucur, @RequestParam("proce") String proce) {

		Map<String, Object> respuesta = new HashMap<>();
		String mensajeError = null;
		try {
			List<adminSMS> mensajes = mensajesService.busquedaPorSeguimiento(cliente, sucur, proce);

			// Verificar si la lista de mensajes es nula o vacía
			if (mensajes != null && !mensajes.isEmpty()) {
				respuesta.put("lista", mensajes);
				return ResponseEntity.ok(respuesta);
			} else {
				// Devolver una lista vacía si no hay mensajes encontrados
				respuesta.put("lista", Collections.emptyList());
				return ResponseEntity.ok(respuesta);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			respuesta.put("error", mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
		}
	}

	@GetMapping("/obtProcesos")
	public ResponseEntity<?> obtProcesos() {
		try {
			logger.info("/obtProcesos");
			List<ProcesoAsignacion> procesos = procesoService.procesosId();
			return ResponseEntity.ok((procesos != null) ? procesos : Collections.emptyList());
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
		}

	}

	@RequestMapping(value = "/editSMS", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> editarMensaje(@RequestParam("id") int id,
			@RequestParam("mensaje") String mensaje) {

		MensajeSMS mensajeActualizado = mensajeService.editarMensaje(id, mensaje);
		if (mensajeActualizado != null) {
			Map<String, Object> response = new HashMap<>();
			response.put("mensaje", mensajeActualizado);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/inactivarSMS", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> inactivarMensaje(@RequestParam("id") int id,
			@RequestParam("estatus") int estatus) {

		MensajeSMS mensajeActualizado = mensajeService.inactivarMensaje(id, estatus);
		if (mensajeActualizado != null) {
			Map<String, Object> response = new HashMap<>();
			response.put("mensaje", mensajeActualizado);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/sendSMS", method = RequestMethod.GET)
	public ResponseEntity<String> sendMensaje(@RequestParam("mensaje") String mensaje,
			@RequestParam("telefono") String telefono) {

		RestTemplate restTemplate = new RestTemplate();

		String mensajeError = null;
		String server = "";

		try {
			List<ConfiguracionSistema> credenciales = configuracionService.obtenerConfiguraciones();

			ConfiguracionSistema conf9 = credenciales.get(9);
			ConfiguracionSistema conf10 = credenciales.get(10);
			ConfiguracionSistema conf11 = credenciales.get(11);
			ConfiguracionSistema conf12 = credenciales.get(12);

			server = conf11.getConfiguracion();

			Parametros parametros = new Parametros();
			parametros.setMail(conf9.getConfiguracion());
			parametros.setPassword(conf10.getConfiguracion());
			parametros.setId(conf12.getConfiguracion());

			// Configurar encabezados
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// Configurar la entidad de la solicitud con los parámetros como JSON
			HttpEntity<Parametros> requestEntity = new HttpEntity<>(parametros, headers);

			// Realizar la solicitud HTTP POST
			ResponseEntity<String> response = restTemplate.postForEntity(server, requestEntity, String.class);

			// Procesar la respuesta si es necesario
			String responseBody = response.getBody();
			//System.out.println("Respuesta del servidor: " + responseBody);
			return ResponseEntity.status(response.getStatusCode()).body(responseBody);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
		}
	}

	@PostMapping("/sendSMSMasivo")
	public ResponseEntity<String> manejarDatosDesdeFrontend(@RequestBody List<EnvioMensajes> envioMensajes, HttpServletRequest request) {
		

		RestTemplate restTemplate = new RestTemplate();

		String mensajeError = null;
		String server = "";

		try {

			ProcesoSMS savedProce = procesoSMSService.saveProce();
            int procesoId = savedProce.getProcesoID();
			
			List<ConfiguracionSistema> credenciales = configuracionService.obtenerConfiguraciones();

			ConfiguracionSistema conf9 = credenciales.get(9);
			ConfiguracionSistema conf10 = credenciales.get(10);
			ConfiguracionSistema conf11 = credenciales.get(11);
			ConfiguracionSistema conf12 = credenciales.get(12);

			server = conf11.getConfiguracion();
			InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
            System.out.println("La IP del servidor es: " + ipAddress);
            System.out.println("La IP del servidor es: " + request.getServerName());

			Parametros parametros = new Parametros();
			parametros.setMail(conf9.getConfiguracion());
			parametros.setPassword(conf10.getConfiguracion());
			parametros.setId(conf12.getConfiguracion());
			parametros.setMensajes(envioMensajes);
			parametros.setRespuesta("http://" + ipAddress + ":" + request.getServerPort() + "/STD/api/respuestaSMS");
			parametros.setProceso(procesoId);
			
			System.out.println("La IP del servidor es: " + parametros.getRespuesta());
			
			// Configurar encabezados
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// Configurar la entidad de la solicitud con los parámetros como JSON
			HttpEntity<Parametros> requestEntity = new HttpEntity<>(parametros, headers);

			// Realizar la solicitud HTTP POST
			ResponseEntity<String> response = restTemplate.postForEntity(server, requestEntity, String.class);

			// Procesar la respuesta si es necesario
			String responseBody = response.getBody();
			//System.out.println("Respuesta del servidor: " + responseBody);
			
			if (response.getStatusCode().is2xxSuccessful()) {
	            // Realizar otra acción aquí
				 // Actualizar el estado de los mensajes
				List<Integer> idsMensajes = envioMensajes.parallelStream()
                        .map(EnvioMensajes::getIdMensaje)
                        .collect(Collectors.toList());

                // Llamar al servicio para actualizar los mensajes con los IDs obtenidos
                mensajeService.actualizarEstadoMensajes(procesoId, idsMensajes);
                
                procesoSMSService.actualizarMensaje(procesoId);
                
                //System.out.println("La respuesta fue exitosa, los mensajes han sido actualizados...");
            
	        }
			
			return ResponseEntity.status(response.getStatusCode()).body(responseBody);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Error: " + e.getMessage());
			mensajeError = "Error: " + e.getMessage();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mensajeError);
		}
	}
	
	@RequestMapping(value = { "/reporteSMS" }, method = RequestMethod.GET)
	public ResponseEntity<?> parametrosReporte(@RequestParam("cliente") String cliente,
			@RequestParam("sucur") String sucur, @RequestParam("proce") String proce) {

		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			
			ReporteJasper datosReporte = iReporteRepository.buscarReporte(nombreReporte);
			
			String reporteURL = iConfiguracionRepository.buscarPropiedad(controllerReporte)+datosReporte.getPk_id_reporte()+"&parametros=?cliente="
					+cliente+"~sucur="+sucur+"~proce="+proce+"&tipoDocumento="+datosReporte.getVc_extension()+"&identificadorDoc=SMS";
					
			respuesta.add(reporteURL);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			respuesta.add(e.getMessage());
			return ResponseEntity.ok(respuesta);
		}
	}
	
	@PostMapping("/respuestaSMS")
    public ResponseEntity<String> receiveSoapResponse(@RequestBody String response) {
        try {
            // Aquí puedes procesar la respuesta recibida
            System.out.println("Respuesta SOAP recibida: " + response);
            
            // Realizar cualquier acción adicional según la respuesta recibida
            // Por ejemplo, actualizar el estado de la base de datos, enviar notificaciones, etc.

            return ResponseEntity.ok("Respuesta recibida y procesada.");
        } catch (Exception e) {
            logger.error("Error al procesar la respuesta SOAP: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la respuesta.");
        }
    }
	
}
