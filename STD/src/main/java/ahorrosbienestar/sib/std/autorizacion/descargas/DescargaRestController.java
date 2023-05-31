package ahorrosbienestar.sib.std.autorizacion.descargas;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JFileChooser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import ahorrosbienestar.sib.std.autorizacion.descargas.bitacora_zip.model.BitacoraZipModel;
import ahorrosbienestar.sib.std.autorizacion.descargas.bitacora_zip.service.BitacoraZipService;
import ahorrosbienestar.sib.std.autorizacion.descargas.docto_video.model.DoctoVideoModel;
import ahorrosbienestar.sib.std.autorizacion.descargas.docto_video.service.DoctoVideoService;
import ahorrosbienestar.sib.std.autorizacion.descargas.dto.GeolocalizacionDTO;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;

import org.apache.log4j.Logger;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@EnableScheduling
public class DescargaRestController {

	@Autowired
	private ConfigService configService;

	@Autowired
	private SolicitudEnvioService solicitudEnvioService;

	@Autowired
	private BitacoraZipService bitacoraZipService;

	@Autowired
	private DoctoVideoService doctoVideoService;
	
	// Variables geolocalizacion
	private final static String typeDevice = "M";
	private final static String operation = "Nueva disposición";
	private final static String typeOperation = "2";
	private final static String ip = "0.0.0.0";
	private final static String typeProduct = "C";

	// Variable ruta temporal windows
	private final static int RUTA_TEMPORAL_WS = 51;

	// Variable ruta temporal linux
	private final static int RUTA_TEMPORAL_LN = 56;
	
	// Variable ruta direccion
	private final static int RUTA_DIRECCIONES = 60;

	// Variable carpeta para los archivos del zip
	private final static String CARPETA_NUEVA = "Videograbacion";

	// Variable que utiliza el método de extraer archivos del zip
	private static final int BUFFER_SIZE = 4096;

	private static final Logger logger = Logger.getLogger(DescargaRestController.class);

	/**
	 * Método para crear guardar la documentacion
	 * 
	 * @param dEnvioID
	 * @throws IOException
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	public GeolocalizacionDTO guardarDocto(Integer solicitudEnvio, Integer clienteId, Float montoAutorizado,
			String nameProducto, String solicitud) throws IOException, ParseException, java.text.ParseException {

		try {
			GeolocalizacionDTO respuestaDTO = new GeolocalizacionDTO();
			GeolocalizacionDTO coordenadasDTO = new GeolocalizacionDTO();

			// Obtenemos la ruta temporal
			Configuracion rutaTemporal = configService.obtenerConfiguracion(RUTA_TEMPORAL_WS);

			// Definimos los parámetros de espera
			int intentosMaximos = 3;
			int tiempoEspera = 30000; // 30 segundos
			// int tiempoEspera = 120000; // 2 minutos

			// Realizamos los intentos para encontrar el archivo
			for (int intento = 1; intento <= intentosMaximos; intento++) {
				// Se obtiene la ubicación del archivo zip
				// File ubicacionZip = ubicacionZip();
				File ubicacionZip = new File(rutaTemporal.getVc_valor_propiedad());

				File archivoZip = new File(ubicacionZip + "\\" + solicitudEnvio + "-" + clienteId + ".zip");
				logger.info(" Existe archivo descargado zip: " + archivoZip.exists() + "\t"
						+ archivoZip.getAbsolutePath() + "\t" + archivoZip.getName());

				if (archivoZip.exists()) {
					// Se encontró el archivo, procedemos con el resto del proceso
					// Obtenemos las coordenadas
					coordenadasDTO = extaerJSON(ubicacionZip + "\\" + solicitudEnvio + "-" + clienteId + ".zip");

					// Movemos el archivo en la ruta temporal
//					Path origen = Paths.get(ubicacionZip + "\\" + solicitudEnvio + "-" + clienteId + ".zip");
//					Path destino = Paths.get(
//							rutaTemporal.getVc_valor_propiedad() + "\\" + solicitudEnvio + "-" + clienteId + ".zip");

					// logger.info("origen descarga: " + origen + "\ndestino descarga: " + destino);

					// Files.move(origen, destino, StandardCopyOption.REPLACE_EXISTING);
					// logger.info("El archivo ha sido movido exitosamente.");

					// Guardamos la bitacora
//					guardarBitacora(solicitudEnvio, destino.toString());
					guardarBitacora(solicitudEnvio, ubicacionZip.getAbsolutePath());
					extraerArchivos(archivoZip, solicitudEnvio, solicitud, clienteId);

					SolicitudEnvio soEnvio = solicitudEnvioService.obtenerSolicitudEnvio(solicitudEnvio);

					coordenadasDTO.setCustomerNumber(clienteId.toString());
					coordenadasDTO.setNumberProvisions(solicitudEnvio.toString());
					coordenadasDTO.setOperationAmount(montoAutorizado.toString());
					coordenadasDTO.setIdProduct(String.valueOf(soEnvio.getI_producto_id()));
					coordenadasDTO.setAccessSystem(nameProducto);
					respuestaDTO = llenarGeolocalizacionDTO(coordenadasDTO);
					return respuestaDTO;
				}

				// El archivo no se encontró, esperamos un tiempo antes de intentar nuevamente
				try {
					Thread.sleep(tiempoEspera);
				} catch (InterruptedException e) {
					logger.error("Error al esperar el tiempo de espera.", e);
				}
			}

			logger.error("No se pudo encontrar el archivo después de " + intentosMaximos + " intentos.");

		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * Método para obtener la ubicación del archivo zip
	 * 
	 * @throws IOException
	 */
	public File ubicacionZip() throws IOException {

		InetAddress ipInet = InetAddress.getLocalHost();
		String hostname = ipInet.getHostName();
		// System.out.println("IP de la máquina: " + ipInet.getHostAddress());
		logger.info("IP de la máquina: " + ipInet.getHostAddress());
		logger.info("hostname: " + hostname);
		String ipOrigen = ipInet.getHostAddress();

		// String ip = HttpUtils.getRequestIP(request);
//		System.out.println("ip origen ubicacionZip: " + ip);
//		logger.info("ip origen ubicacionZip: " + ip);

		// Obtener el sistema operativo actual
		String os = System.getProperty("os.name").toLowerCase();

		// Determinar la ruta de la carpeta de descargas
		String rutaDescargas = "";

		if (os.contains("win")) {
			// rutaDescargas = ipOrigen + "\\Downloads";
			rutaDescargas = "\\\\" + hostname + "\\" + CARPETA_NUEVA;
		} else if (os.contains("mac")) {
			rutaDescargas = ipOrigen + "/Downloads";
		} else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
			rutaDescargas = ipOrigen + "/Downloads";
		} else {
			// Sistema operativo desconocido
			logger.error("Sistema operativo no soportado.");
			// System.err.println("Sistema operativo no soportado.");
			System.exit(1);
		}

		// Imprimir la ruta de la carpeta de descargas
		logger.info("La carpeta de descargas está ubicada en: " + rutaDescargas);
		// System.out.println("La carpeta de descargas está ubicada en: " +
		// rutaDescargas);

		// Acceder a la carpeta de descargas
		File carpetaDescargas = new File(rutaDescargas);

		logger.info("La carpeta de descargas existe: " + carpetaDescargas.exists());

//		if (carpetaDescargas.exists())
//			logger.info("La carpeta de descargas existe.");
//			//System.out.println("La carpeta de descargas existe.");
//		else
//			logger.info("La carpeta de descargas no existe.");
//			System.out.println("La carpeta de descargas no existe.");

		return carpetaDescargas;

	}

	/**
	 * Método para guardar la bitacora
	 * 
	 * @param solicitudEnvio
	 * @param ruta
	 * @throws java.text.ParseException
	 */
	public void guardarBitacora(Integer solicitudEnvio, String ruta) throws java.text.ParseException {

		BitacoraZipModel model = new BitacoraZipModel();

		SolicitudEnvio soEnvio = solicitudEnvioService.obtenerSolicitudEnvio(solicitudEnvio);

		// Fecha de registro
		// Obtener la fecha actual como un objeto java.util.Date
		Date fechaActual = new Date();
		// Convertir el objeto java.util.Date a un objeto java.time.Instant
		Instant instant = fechaActual.toInstant();
		// Crear un objeto java.time.LocalDate usando el objeto java.time.Instant
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

		model.setDt_fecha_registro(localDate);
		model.setVc_ruta_zip(ruta);
		model.setI_estatus(0);
		model.setFk_solicitud_envio(soEnvio);
		bitacoraZipService.save(model);
	}

	/**
	 * Método para obtener los valores de un archivo .json
	 * 
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@SuppressWarnings("resource")
	public GeolocalizacionDTO extaerJSON(String filePath) throws IOException, ParseException {

		GeolocalizacionDTO responseDTO = new GeolocalizacionDTO();

		// Ruta del archivo ZIP
		String zipFilePath = filePath;
		// Nombre del archivo JSON dentro del ZIP
		String jsonFileName = "data.json";

		// Abre el archivo ZIP
		ZipFile zipFile = new ZipFile(zipFilePath);

		// Obtiene la entrada del archivo JSON dentro del ZIP
		ZipEntry jsonEntry = zipFile.getEntry(jsonFileName);

		// Verifica si el archivo JSON existe dentro del ZIP
		if (jsonEntry == null) {
			throw new IOException("El archivo JSON no existe dentro del archivo ZIP.");
		}

		// Abre el archivo JSON dentro del ZIP
		InputStream inputStream = zipFile.getInputStream(jsonEntry);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

		// Lee el contenido del archivo JSON
		String line;
		StringBuilder stringBuilder = new StringBuilder();
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line);
		}

		// Cierra los flujos de entrada
		bufferedReader.close();
		inputStream.close();

		// Imprime el contenido del archivo JSON
		// System.out.println(stringBuilder.toString());

		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) parser.parse(stringBuilder.toString());
		} catch (ParseException e) {
			logger.error(e);
			// e.printStackTrace();
		}

		String latitude = (String) jsonObj.get("latitude");
		String longitude = (String) jsonObj.get("longitude");
		responseDTO.setLatitude(latitude);
		responseDTO.setLongitude(longitude);

		// Cierra el archivo ZIP
		zipFile.close();

		return responseDTO;
	}

	public GeolocalizacionDTO llenarGeolocalizacionDTO(GeolocalizacionDTO llenarDTO) throws IOException {
		
		// Obtenemos la ruta temporal linux
		Configuracion rutaDirecciones = configService.obtenerConfiguracion(RUTA_DIRECCIONES);

		// Fecha
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = now.format(formatter);

		// Dirección
		String url = rutaDirecciones.getVc_valor_propiedad() + "&lat=" + llenarDTO.getLatitude() + "&lon=" + llenarDTO.getLongitude();
		URL requestUrl = new URL(url);
		InputStream is = requestUrl.openStream();
		InputStreamReader isr = new InputStreamReader(is);
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(isr, JsonObject.class);
		String address = json.get("display_name").getAsString();
		logger.info("Dirección: " + address);
		// System.out.println("Dirección: " + address);

		llenarDTO.setTypeDevice(typeDevice);
		llenarDTO.setRegistrationDataTime(formattedDateTime);
		llenarDTO.setAddressGeolocation(address);
		llenarDTO.setOperation(operation);
		llenarDTO.setTypeOperation(typeOperation);
		llenarDTO.setIp(ip);
		llenarDTO.setTypeProduct(typeProduct);
		llenarDTO.setIdState("22");
		return llenarDTO;
	}

	/**
	 * Método de extracción zip automático
	 * 
	 * @throws IOException
	 * @throws java.text.ParseException
	 */
	// @Scheduled(cron = "0 0 22 * * ?")
//	public void extraerArchivos() throws IOException, java.text.ParseException {
//
//		logger.info("Ejecutando extraerArchivos");
//
//		// Obtenemos la ruta temporal windows
//		Configuracion rutaWindows = configService.obtenerConfiguracion(RUTA_TEMPORAL_WS);
//
//		// Obtenemos la ruta temporal linux
//		Configuracion rutaFinal = configService.obtenerConfiguracion(RUTA_TEMPORAL_LN);
//
//		//
//		File carpetaEnvio = new File(rutaWindows.getVc_valor_propiedad());
//		logger.info("carpetaEnvio : " + carpetaEnvio);
//		File[] archivosEnvio = carpetaEnvio.listFiles();
//		//
//		File carpetaVideo = new File(rutaFinal.getVc_valor_propiedad());
//		File[] archivosVideo = carpetaVideo.listFiles();
//
//		if (carpetaEnvio.isDirectory()) {
//			String[] listFile = carpetaEnvio.list();
//			logger.info("listFile.length : " + listFile.length);
//			for (int i = 0; i < listFile.length; i++) {
//				String nombre = listFile[i];
//				logger.info("nombre " + i + " : " + nombre);
//
//			}
//
//		}
//
////		if (archivosVideo != null) {
////			for (File video : archivosVideo) {
////				if (video.isFile()) {
////					if (archivosEnvio != null) {
////						for (File envio : archivosEnvio) {
////							//
////							String inicioVideo = video.getName();
////							String[] partesVideo = inicioVideo.split("-");
////							//
////							int indicePunto = partesVideo[1].indexOf(".");
////							String clienteId = partesVideo[1].substring(0, indicePunto);
////							if (clienteId.equals(envio.getName())) {
////								// Obtener solicitud de envio
////								SolicitudEnvio soEnvio = solicitudEnvioService
////										.obtenerSolicitudEnvio(Integer.parseInt(partesVideo[0]));
////
////								// Aquí hace referencia al complemento de la ruta con el cliente
////								String rutaCliente = clienteId + "\\" + clienteId + "-"
////										+ soEnvio.getI_no_solicitud_core() + "-" + soEnvio.getI_identificador_ruta()
////										+ "\\";
////
////								logger.info("rutaCliente: " + rutaCliente.toString());
////
////								// Se arma la ruta en donde ira la carpeta nueva
////								String complemento = carpetaEnvio + "\\" + rutaCliente;
////
////								//
////								Path origen = Paths.get(video.toString());
////								Path destino = Paths.get(complemento + video.getName());
////
////								Files.move(origen, destino);
////
////								String zipDestino = complemento + "\\" + CARPETA_NUEVA;
////
////								// Descomprimir el archivo .zip
////								unzip(destino.toString(), zipDestino);
////
////								//
////								File fileArchivos = new File(zipDestino);
////								File[] files = fileArchivos.listFiles();
////								if (files != null) {
////									for (File nuevo : files) {
////										if (nuevo.isFile()) {
////											guardarDoctoVideo(soEnvio.getPk_id_solicitud_envio(),
////													rutaCliente + CARPETA_NUEVA + "\\" + nuevo.getName());
////										}
////									}
////								}
////							}
////						}
////					}
////				}
////			}
////		}
//	}

//	public void extraerArchivos(File archivoZip, Integer solicitudEnvio, String solicitud, Integer clienteId2)
//			throws IOException, java.text.ParseException {
//
//		// Obtenemos la ruta temporal windows
//		Configuracion rutaWindows = configService.obtenerConfiguracion(RUTA_TEMPORAL_WS);
//
//		// Obtenemos la ruta temporal linux
//		Configuracion rutaLinux = configService.obtenerConfiguracion(RUTA_TEMPORAL_LN);
//
//		//
//		File carpetaEnvio = new File(rutaWindows.getVc_valor_propiedad());
//		File[] archivosEnvio = carpetaEnvio.listFiles();
//		logger.info(carpetaEnvio);
//
//		//
//		SolicitudEnvio soEnvio = solicitudEnvioService.obtenerSolicitudEnvio(solicitudEnvio);
//
//		//
//		File carpetaVideo = new File(rutaLinux.getVc_valor_propiedad() + "\\" + clienteId2 + "\\" + clienteId2 + "-"
//				+ soEnvio.getI_no_solicitud_core() + "-" + soEnvio.getI_identificador_ruta() + "\\");
//		File[] archivosVideo = carpetaVideo.listFiles();
//		logger.info(carpetaVideo);
//
//		// Aquí hace referencia al complemento de la ruta con el cliente
//		String rutaCliente = clienteId2 + "\\" + clienteId2 + "-" + soEnvio.getI_no_solicitud_core() + "-"
//				+ soEnvio.getI_identificador_ruta() + "\\";
//
//		// Se arma la ruta en donde ira la carpeta nueva
//		String complemento = carpetaEnvio + "\\" + rutaCliente;
//
//		//
//		Path origen = Paths.get(carpetaEnvio.toString() + "\\" + archivoZip.getName());
//		Path destino = Paths.get(carpetaVideo.toString() + "\\" + CARPETA_NUEVA + "\\" + archivoZip.getName());
//
//		logger.info(origen);
//		logger.info(destino);
//
//		File carpetaVideograbación = new File(carpetaVideo + "\\" + CARPETA_NUEVA);
//
//		carpetaVideograbación.mkdirs();
//
//		Files.move(origen, destino);
//
//		String nombreCarpeta = archivoZip.getName().replaceAll("\\.zip$", "");
//
//		String zipDestino = carpetaVideo + "\\" + CARPETA_NUEVA + "\\" + nombreCarpeta;
//
//		// Descomprimir el archivo .zip
//		unzip(destino.toString(), zipDestino);
//
//		//
//		File fileArchivos = new File(zipDestino);
//		File[] files = fileArchivos.listFiles();
//		if (files != null) {
//			for (File nuevo : files) {
//				if (nuevo.isFile()) {
//					guardarDoctoVideo(soEnvio.getPk_id_solicitud_envio(),
//							rutaCliente + CARPETA_NUEVA + "\\" + nuevo.getName());
//				}
//			}
//		}
//	}

//	public void extraerArchivos(File archivoZip, Integer solicitudEnvio, String solicitud, Integer clienteId) throws IOException, java.text.ParseException {
//		try {
//
//			logger.info("Ejecutando extraerArchivos");
//
//			// Obtenemos la ruta temporal windows \\Cmv020063\std\Videogracion
//			Configuracion rutaWindows = configService.obtenerConfiguracion(RUTA_TEMPORAL_WS);
//
//			// Obtenemos la ruta temporal linux \\Cmv020063\std\Envio\**
//			Configuracion rutaFinal = configService.obtenerConfiguracion(RUTA_TEMPORAL_LN);
//
//			// Carpeta de videogravacion
//			File carpetaEnvio = new File(rutaWindows.getVc_valor_propiedad());
//			logger.info("carpetaEnvio : " + carpetaEnvio);
//			File[] archivosEnvio = carpetaEnvio.listFiles();
//			
//			//Carpeta std/Envio
//			File carpetaVideo = new File(rutaFinal.getVc_valor_propiedad());
//			logger.info("carpetaVideo : " + carpetaVideo);
//			File[] archivosVideo = carpetaVideo.listFiles();
//
////			if (carpetaEnvio.isDirectory()) {
////				String[] listFile = carpetaEnvio.list();
////				logger.info("listFile.length : " + listFile.length);
////				for (int i = 0; i < listFile.length; i++) {
////					String nombre = listFile[i];
////					logger.info("nombre " + i + " : " + nombre);
////
////				}
////
////			}
//
//			if (archivosVideo != null) {
//				logger.info("Entro en archivosVideo != null");
//				for (File video : archivosVideo) {
//					logger.info("Entro en for video");
//					logger.info("video.isFile: " + video.isFile());
//					logger.info("video :" + video.getName());
//					if (video.isFile()) {
//						logger.info("IF video es archivo");
//						logger.info("video: " + video.getAbsolutePath());
//						if (archivosEnvio != null) {
//							logger.info("archivosVideo es diferente a NULL");
//							for (File envio : archivosEnvio) {
//								logger.info("For envio");
//								//
//								String inicioVideo = video.getName();
//								String[] partesVideo = inicioVideo.split("-");
//								//
//								int indicePunto = partesVideo[1].indexOf(".");
//								String clienteId2 = partesVideo[1].substring(0, indicePunto);
//								logger.info("clienteID2: " + clienteId2);
//								if (clienteId2.equals(envio.getName())) {
//									
//									// Obtener solicitud de envio
//									SolicitudEnvio soEnvio = solicitudEnvioService
//											.obtenerSolicitudEnvio(Integer.parseInt(partesVideo[0]));
//
//									// Aquí hace referencia al complemento de la ruta con el cliente
//									String rutaCliente = clienteId2 + "\\" + clienteId + "-"
//											+ soEnvio.getI_no_solicitud_core() + "-" + soEnvio.getI_identificador_ruta()
//											+ "\\";
//
//									logger.info("rutaCliente: " + rutaCliente.toString());
//
//									// Se arma la ruta en donde ira la carpeta nueva
//									String complemento = carpetaEnvio + "\\" + rutaCliente;
//									
//									logger.info("complemento: " + complemento);
//
//									//
//									Path origen = Paths.get(video.toString());
//									Path destino = Paths.get(complemento + video.getName());
//									
//									logger.info("origen: " + origen);
//									logger.info("destino: " + origen);
//
//									Files.move(origen, destino);
//
//									String zipDestino = complemento + "\\" + CARPETA_NUEVA;
//
//									// Descomprimir el archivo .zip
//									unzip(destino.toString(), zipDestino);
//
//									//
//									File fileArchivos = new File(zipDestino);
//									File[] files = fileArchivos.listFiles();
//									if (files != null) {
//										for (File nuevo : files) {
//											if (nuevo.isFile()) {
//												logger.info("Entro en nuevo.isFile ");
//												guardarDoctoVideo(soEnvio.getPk_id_solicitud_envio(),
//														rutaCliente + CARPETA_NUEVA + "\\" + nuevo.getName());
//											}
//										}
//									}
//								}
//							}
//						} else {
//							logger.info("Video es null");
//						}
//					} else {
//						logger.info("Video no es file");
//					}
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.error(e);
//		}
//	}

	public void extraerArchivos(File archivoZip, Integer solicitudEnvio, String solicitud, Integer clienteId)
			throws IOException, java.text.ParseException {
		try {
			logger.info("Ejecutando extraerArchivos");

			// Obtenemos la ruta temporal windows
			Configuracion rutaWindows = configService.obtenerConfiguracion(RUTA_TEMPORAL_WS);

			// Obtenemos la ruta temporal linux
			Configuracion rutaFinal = configService.obtenerConfiguracion(RUTA_TEMPORAL_LN);

			SolicitudEnvio soEnvio = solicitudEnvioService.obtenerSolicitudEnvio(solicitudEnvio);

			int identificador = soEnvio.getI_identificador_ruta();

			//
			File carpetaEnvio = new File(rutaWindows.getVc_valor_propiedad());
			logger.info("carpetaEnvio : " + carpetaEnvio);
			File[] archivosEnvio = carpetaEnvio.listFiles();
			//
			File carpetaVideo = new File(rutaFinal.getVc_valor_propiedad());
			logger.info("carpetaVideo : " + carpetaVideo);
			File[] archivosVideo = carpetaVideo.listFiles();

			String carpetaFinal = carpetaVideo + "\\" + clienteId + "\\" + clienteId + "-" + solicitud + "-"
					+ identificador;
			logger.info("carpetaFinal :" + carpetaFinal);

			File carpetaVideograbación = new File(carpetaFinal + "\\" + CARPETA_NUEVA);

			logger.info("carpetaVideograbación: " + carpetaVideograbación);

			if (carpetaEnvio.isDirectory()) {
				String[] listFile = carpetaEnvio.list();
				logger.info("listFile.length : " + listFile.length);
				for (int i = 0; i < listFile.length; i++) {
					String nombre = listFile[i];
					logger.info("nombre " + i + " : " + nombre);

					if (archivoZip.getName().equals(nombre)) {

						logger.info("Se encontro el archivo: " + archivoZip);
						carpetaVideograbación.mkdirs();
						Path origen = Paths.get((archivoZip.getAbsolutePath()).toString());
						Path destino = Paths.get((carpetaVideograbación.getAbsolutePath()).toString() + "\\" + nombre);
						logger.info("origen move: " + origen);
						logger.info("destino move: " + destino);
						Files.move(origen, destino);

						String zipDestino = (carpetaVideograbación.getAbsolutePath()).toString();

						logger.info("zipDestino: " + zipDestino);
						// Descomprimir el archivo .zip
						unzip(destino.toString(), zipDestino);

						File fileArchivos = new File(zipDestino);
						logger.info("Total de archivos a descomprimir: " + fileArchivos.length());
						File[] files = fileArchivos.listFiles();
						if (files != null) {
							for (File nuevo : files) {
								logger.info("Archivo nuevo: " + nuevo);
								if (nuevo.isFile()) {
									logger.info("nuevo: " + nuevo.getName());
									logger.info("es archivo: " + nuevo.isFile());
									guardarDoctoVideo(soEnvio.getPk_id_solicitud_envio(),
											clienteId + "\\" + clienteId + "-" + solicitud + "-" + identificador + "\\"
													+ CARPETA_NUEVA + "\\" + nuevo.getName().toString());
								}
							}
						}

					}

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
		}

	}

	/**
	 * Método para extaer los archivos del zip
	 * 
	 * @param archivoZipPath
	 * @param destinoPath
	 * @throws IOException
	 */
	public void unzip(String archivoZipPath, String destinoPath) throws IOException {

		// System.out.println("comienza la extracción del .zip");
		logger.info("comienza la extracción del .zip");
		File Destination_Directory = new File(destinoPath);

		if (!Destination_Directory.exists()) {
			Destination_Directory.mkdir();
		}

		ZipInputStream Zip_Input_Stream = new ZipInputStream(new FileInputStream(archivoZipPath));
		ZipEntry Zip_Entry = Zip_Input_Stream.getNextEntry();

		while (Zip_Entry != null) {

			String File_Path = destinoPath + File.separator + Zip_Entry.getName();

			if (!Zip_Entry.isDirectory()) {
				extractFile(Zip_Input_Stream, File_Path);
			} else {
				File directory = new File(File_Path);
				directory.mkdirs();
			}

			Zip_Input_Stream.closeEntry();
			Zip_Entry = Zip_Input_Stream.getNextEntry();
		}

		Zip_Input_Stream.close();
	}

	/**
	 * Método para extaer los archivos del zip
	 * 
	 * @param Zip_Input_Stream
	 * @param File_Path
	 * @throws IOException
	 */
	private void extractFile(ZipInputStream Zip_Input_Stream, String File_Path) throws IOException {
		BufferedOutputStream Buffered_Output_Stream = new BufferedOutputStream(new FileOutputStream(File_Path));
		byte[] Bytes = new byte[BUFFER_SIZE];
		int Read_Byte = 0;
		while ((Read_Byte = Zip_Input_Stream.read(Bytes)) != -1) {
			Buffered_Output_Stream.write(Bytes, 0, Read_Byte);
		}
		Buffered_Output_Stream.close();
	}

	/**
	 * Método para guardar los documentos
	 * 
	 * @param solicitudEnvio
	 * @param ruta
	 * @throws java.text.ParseException
	 */
	public void guardarDoctoVideo(Integer solicitudEnvio, String ruta) {

		try {
			DoctoVideoModel model = new DoctoVideoModel();

			SolicitudEnvio soEnvio = solicitudEnvioService.obtenerSolicitudEnvio(solicitudEnvio);
			logger.info("Entro en guardarDoctoVideo");
			logger.info("ruta: " + ruta);

			// Fecha de registro
			// Obtener la fecha actual como un objeto java.util.Date
			Date fechaActual = new Date();
			// Convertir el objeto java.util.Date a un objeto java.time.Instant
			Instant instant = fechaActual.toInstant();
			// Crear un objeto java.time.LocalDate usando el objeto java.time.Instant
			LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

			model.setDt_fecha_envio(localDate);
			model.setVc_ruta_docto(ruta);
			model.setI_estatus(1);
			model.setFk_solicitud_envio(soEnvio);
			doctoVideoService.save(model);
			logger.info("Guardar model");
		}catch (Exception e) {
			// TODO: handle exception
			logger.error(e);
		}
	}

//	public void guardarDoctoVideo(Integer solicitudEnvio, String ruta) throws java.text.ParseException {
//		try {
//			SolicitudEnvio soEnvio = solicitudEnvioService.obtenerSolicitudEnvio(solicitudEnvio);
//			logger.info("Entro en guardarDoctoVideo");
//			logger.info("ruta: " + ruta);
//
//			// Verificar si ya existe un objeto DoctoVideoModel con el mismo identificador
//			DoctoVideoModel existingModel = doctoVideoService.findByIdentificador(solicitudEnvio);
//			// Obtener la fecha actual como un objeto java.util.Date
//			Date fechaActual = new Date();
//			Instant instant = fechaActual.toInstant();
//			LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
//			if (existingModel != null) {
//				logger.info("existingModel");
//				// Actualizar el objeto existente en lugar de crear uno nuevo
//				existingModel.setDt_fecha_envio(localDate);
//				existingModel.setVc_ruta_docto(ruta);
//				existingModel.setI_estatus(1);
//				existingModel.setFk_solicitud_envio(soEnvio);
//				doctoVideoService.save(existingModel);
//			} else {
//				// Crear un nuevo objeto DoctoVideoModel
//				logger.info("newModel");
//				DoctoVideoModel newModel = new DoctoVideoModel();
//				newModel.setDt_fecha_envio(localDate);
//				newModel.setVc_ruta_docto(ruta);
//				newModel.setI_estatus(1);
//				newModel.setFk_solicitud_envio(soEnvio);
//				doctoVideoService.save(newModel);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.error(e);
//		}
//
//	}

//	public void guardarDoctoVideo(SolicitudEnvio soEnvio, String ruta, String nombreArchivo) throws java.text.ParseException {
//	    logger.info("soEnvio: " + soEnvio);
//	    if (soEnvio != null) {
//	        // Verificar si ya existe un DoctoVideoModel con el mismo identificador
//	        DoctoVideoModel existingModel = doctoVideoService.findBySolicitudEnvio(soEnvio);
//	        if (existingModel != null) {
//	            // Actualizar el modelo existente en lugar de crear uno nuevo
//	            existingModel.setVc_ruta_docto(ruta + "\\" + nombreArchivo);
//	            existingModel.setDt_fecha_envio(LocalDate.now());
//	            doctoVideoService.save(existingModel);
//	            logger.info("Actualizado doctoVideoService");
//	        } else {
//	            // Crear un nuevo modelo si no existe uno con el mismo identificador
//	            DoctoVideoModel model = new DoctoVideoModel();
//	            model.setDt_fecha_envio(LocalDate.now());
//	            model.setVc_ruta_docto(ruta + "\\" + nombreArchivo);
//	            model.setI_estatus(1);
//	            model.setFk_solicitud_envio(soEnvio);
//	            doctoVideoService.save(model);
//	            logger.info("Guardado doctoVideoService");
//	        }
//	    } else {
//	        // Manejar el caso cuando no se encuentra la solicitud de envío
//	        logger.info("soEnvio es null");
//	    }
//	}

//	public void guardarDoctoVideo(SolicitudEnvio soEnvio, String ruta, String nombreArchivo) throws java.text.ParseException {
//	    logger.info("soEnvio: " + soEnvio);
//	    if (soEnvio != null) {
//	        DoctoVideoModel model = new DoctoVideoModel();
//	        String rutaCompleta = ruta + "\\" + nombreArchivo;
//	        logger.info("ruta: " + rutaCompleta);
//
//	        // Fecha de registro
//	        // Obtener la fecha actual como un objeto java.util.Date
//	        Date fechaActual = new Date();
//	        // Convertir el objeto java.util.Date a un objeto java.time.Instant
//	        Instant instant = fechaActual.toInstant();
//	        // Crear un objeto java.time.LocalDate usando el objeto java.time.Instant
//	        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
//
//	        model.setDt_fecha_envio(localDate);
//	        model.setVc_ruta_docto(rutaCompleta);
//	        model.setI_estatus(1);
//	        model.setFk_solicitud_envio(soEnvio);
//	        doctoVideoService.save(model);
//	        logger.info("Guardo doctoVideoService");
//	    } else {
//	        // Manejar el caso cuando no se encuentra la solicitud de envío
//	    	logger.info("soEnvio es null");
//	    }
//	}

}
