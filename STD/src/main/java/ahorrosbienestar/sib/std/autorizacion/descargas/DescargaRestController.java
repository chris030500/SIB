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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JacksonInject.Value;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ahorrosbienestar.sib.std.autorizacion.descargas.bitacora_zip.model.BitacoraZipModel;
import ahorrosbienestar.sib.std.autorizacion.descargas.bitacora_zip.service.BitacoraZipService;
import ahorrosbienestar.sib.std.autorizacion.descargas.docto_video.model.DoctoVideoModel;
import ahorrosbienestar.sib.std.autorizacion.descargas.docto_video.service.DoctoVideoService;
import ahorrosbienestar.sib.std.autorizacion.descargas.dto.DatosDTO;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private final static String typeProduct = "A";
	private final static String idProduct = "2";

	private Map<String, String> estadosRepublica = new HashMap<String, String>();

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

	private static final Logger logger =
	        LoggerFactory.getLogger(DescargaRestController.class);

	/**
	 * Método para crear guardar la documentacion
	 * 
	 * @param dEnvioID
	 * @throws IOException
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	public DatosDTO guardarDocto(Integer solicitudEnvio, Integer clienteId, Float montoAutorizado, String nameProducto,
	        String solicitud, Integer idWSAutenticacion) throws IOException, ParseException, java.text.ParseException {

	    try {
	        DatosDTO respuestaDTO;
	        DatosDTO coordenadasDTO;

	        Configuracion rutaTemporal = configService.obtenerConfiguracion(RUTA_TEMPORAL_WS);

	        int intentosMaximos = 3;
	        int tiempoEspera = 30000;

	        for (int intento = 1; intento <= intentosMaximos; intento++) {

	            File ubicacionZip = new File(rutaTemporal.getVc_valor_propiedad());
	            File archivoZip = new File(ubicacionZip + "\\" + solicitudEnvio + "-" + clienteId + ".zip");

	            logger.info("Existe archivo descargado zip: " + archivoZip.exists() + "\t"
	                    + archivoZip.getAbsolutePath() + "\t" + archivoZip.getName());

	            if (archivoZip.exists()) {

	                SolicitudEnvio soEnvio = solicitudEnvioService.obtenerSolicitudEnvio(solicitudEnvio);

	                coordenadasDTO = extaerJSON(ubicacionZip + "\\" + solicitudEnvio + "-" + clienteId + ".zip");

	                guardarBitacora(solicitudEnvio, ubicacionZip.getAbsolutePath());
	                extraerArchivos(archivoZip, solicitudEnvio, solicitud, clienteId);

	                coordenadasDTO.setCustomerNumber(clienteId.toString());
	                coordenadasDTO.setNumberProvisions(solicitudEnvio.toString());
	                coordenadasDTO.setOperationAmount(montoAutorizado.toString());
	                coordenadasDTO.setIdProduct(String.valueOf(soEnvio.getI_producto_id()));
	                coordenadasDTO.setAccessSystem(nameProducto);

	                respuestaDTO = llenarGeolocalizacionDTO(coordenadasDTO);
	                respuestaDTO.setIdWSAutenticacion(idWSAutenticacion);
	                respuestaDTO.setSolicitudEnvio(solicitudEnvio);

	                return respuestaDTO;
	            }

	            try {
	                Thread.sleep(tiempoEspera);
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	                logger.error("Error al esperar el tiempo de espera.", e);
	                return null;
	            }
	        }

	        logger.error("No se pudo encontrar el archivo después de " + intentosMaximos + " intentos.");

	    } catch (Exception e) {
	        if (e instanceof InterruptedException) {
	            Thread.currentThread().interrupt();
	        }
	        logger.error("Error en guardarDocto", e);
	    }

	    return null;
	}
	
	private static void archivos(File ubicacionZip, Integer solicitudEnvio, Integer clienteId, Path destino) {
		try {
			File archivoPdf = new File(ubicacionZip + "\\" + solicitudEnvio + "-" + clienteId + ".pdf");
			File archivoKey = new File(ubicacionZip + "\\" + solicitudEnvio + "-" + clienteId + ".key");
			File archivoFad = new File(ubicacionZip + "\\" + solicitudEnvio + "-" + clienteId + ".fad");
			logger.info(" Existe archivo descargado pdf: " + archivoPdf.exists() + "\t"
					+ archivoPdf.getAbsolutePath() + "\t" + archivoPdf.getName());

			logger.info(" Existe archivo descargado key: " + archivoKey.exists() + "\t"
					+ archivoKey.getAbsolutePath() + "\t" + archivoKey.getName());

			logger.info(" Existe archivo descargado fad: " + archivoFad.exists() + "\t"
					+ archivoFad.getAbsolutePath() + "\t" + archivoFad.getName());
			File destinoCarpeta = new File(destino.toFile(), null);
			moveFile(archivoPdf, destinoCarpeta);
	        moveFile(archivoKey, destinoCarpeta);
	        moveFile(archivoFad, destinoCarpeta);
		}catch (Exception e) {
			// TODO: handle exception
			logger.error("Error al ejecutar el proceso", e);
		}
		
	}

	private static void moveFile(File sourceFile, File destinationFolder) throws IOException {
		Path sourcePath = sourceFile.toPath();
		Path destinationPath = new File(destinationFolder, sourceFile.getName()).toPath();

		Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
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
	public DatosDTO extaerJSON(String filePath) throws IOException, ParseException {

	    DatosDTO responseDTO = new DatosDTO();
	    String jsonFileName = "data.json";

	    try (ZipFile zipFile = new ZipFile(filePath)) {

	        ZipEntry jsonEntry = zipFile.getEntry(jsonFileName);

	        if (jsonEntry == null) {
	            throw new IOException("El archivo JSON no existe dentro del archivo ZIP.");
	        }

	        StringBuilder stringBuilder = new StringBuilder();

	        try (
	            InputStream inputStream = zipFile.getInputStream(jsonEntry);
	            BufferedReader bufferedReader = new BufferedReader(
	                new InputStreamReader(inputStream, StandardCharsets.UTF_8)
	            )
	        ) {
	            String line;
	            while ((line = bufferedReader.readLine()) != null) {
	                stringBuilder.append(line);
	            }
	        }

	        JSONParser parser = new JSONParser();
	        JSONObject jsonObj = (JSONObject) parser.parse(stringBuilder.toString());

	        if (jsonObj == null) {
	            throw new IOException("No se pudo leer el contenido del archivo JSON.");
	        }

	        String latitude = (String) jsonObj.get("latitude");
	        String longitude = (String) jsonObj.get("longitude");

	        responseDTO.setLatitude(latitude);
	        responseDTO.setLongitude(longitude);

	        return responseDTO;
	    }
	}


	public DatosDTO llenarGeolocalizacionDTO(DatosDTO llenarDTO) throws IOException {


		// Obtenemos la ruta temporal linux
		Configuracion rutaDirecciones = configService.obtenerConfiguracion(RUTA_DIRECCIONES);

		// Fecha
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = now.format(formatter);

		// Dirección
		String url = rutaDirecciones.getVc_valor_propiedad() + "&lat=" + llenarDTO.getLatitude() + "&lon="
				+ llenarDTO.getLongitude();
		URL requestUrl = new URL(url);
		InputStream is = requestUrl.openStream();
		InputStreamReader isr = new InputStreamReader(is);
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(isr, JsonObject.class);
		//String address = removerAcentosPalabra(json.get("display_name").getAsString());
		//logger.info("Dirección: " + address);
		JsonObject objetoDireccion = json.getAsJsonObject("address");
		
		
		logger.info("Estado: " + objetoDireccion.get("state").getAsString());

		// clave del estado
		String clave = claveDeEstado(objetoDireccion.get("state").getAsString());
		String road = new StringBuilder().append(objetoDireccion.get("road").getAsString()).append(" ").append(objetoDireccion.get("neighbourhood").getAsString()).append(" ").append(objetoDireccion.get("postcode").getAsString()).append(" ").append(objetoDireccion.get("county").getAsString()).append(" ").append(objetoDireccion.get("city").getAsString()).toString();
		logger.info("Direccion: " + road);
		llenarDTO.setTypeDevice(typeDevice);
		llenarDTO.setRegistrationDataTime(formattedDateTime);
		llenarDTO.setAddressGeolocation(road);
		llenarDTO.setOperation(operation);
		llenarDTO.setTypeOperation(typeOperation);
		llenarDTO.setIp(ip);
		llenarDTO.setTypeProduct(typeProduct);
		llenarDTO.setIdState(clave);
		return llenarDTO;
	}


	public static String removerAcentosPalabra(String valor) {
		// Cadena de caracteres original a sustituir.
		String original = "áàäéèëíìïóòöúùuÁÀÄÉÈËÍÌÏÓÒÖÚÙÜçÇ";
		// Cadena de caracteres ASCII que reemplazarán los originales.
		String ascii = "aaaeeeiiiooouuuAAAEEEIIIOOOUUUcC";
		String output = valor;
		for (int i = 0; i < original.length(); i++) {
			// Reemplazamos los caracteres especiales.
			output = output.replace(original.charAt(i), ascii.charAt(i));
		} // for i
		return output;
	}

	public String claveDeEstado(String estado) {

		// Creación de un objeto utilizando el constructor
		estadosRepublica.put("Aguascalientes", "01");
		estadosRepublica.put("Baja California", "02");
		estadosRepublica.put("Baja California Sur", "03");
		estadosRepublica.put("Campeche", "04");
		estadosRepublica.put("Coahuila", "05");
		estadosRepublica.put("Colima", "06");
		estadosRepublica.put("Chiapas", "07");
		estadosRepublica.put("Chihuahua", "08");
		estadosRepublica.put("Ciudad de México", "09");
		estadosRepublica.put("Durango", "10");
		estadosRepublica.put("Guanajuato", "11");
		estadosRepublica.put("Guerrero", "12");
		estadosRepublica.put("Hidalgo", "13");
		estadosRepublica.put("Jalisco", "14");
		estadosRepublica.put("México", "15");
		estadosRepublica.put("Michoacán", "16");
		estadosRepublica.put("Morelos", "17");
		estadosRepublica.put("Nayarit", "18");
		estadosRepublica.put("Nuevo León", "19");
		estadosRepublica.put("Oaxaca", "20");
		estadosRepublica.put("Puebla", "21");
		estadosRepublica.put("Querétaro", "22");
		estadosRepublica.put("Quintana Roo", "23");
		estadosRepublica.put("San Luis Potosí", "24");
		estadosRepublica.put("Sinaloa", "25");
		estadosRepublica.put("Sonora", "26");
		estadosRepublica.put("Tabasco", "27");
		estadosRepublica.put("Tamaulipas", "28");
		estadosRepublica.put("Tlaxcala", "29");
		estadosRepublica.put("Veracruz", "30");
		estadosRepublica.put("Yucatán", "31");
		estadosRepublica.put("Zacatecas", "32");

		if (estadosRepublica.containsKey(estado)) {
			return estadosRepublica.get(estado);
		}

		return "0";
	}



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
			logger.info("Error: {}", e.getMessage());
		}

	}

	/**
	 * Método para extaer los archivos del zip
	 * 
	 * @param archivoZipPath
	 * @param destinoPath
	 * @throws IOException
	 */
	private static final long MAX_ZIP_ENTRY_SIZE = 10L * 1024L * 1024L;
	private static final long MAX_ZIP_TOTAL_SIZE = 50L * 1024L * 1024L;

	public void unzip(String archivoZipPath, String destinoPath) throws IOException {

	    Path destinoDir = Paths.get(destinoPath).toAbsolutePath().normalize();

	    if (!Files.exists(destinoDir)) {
	        Files.createDirectories(destinoDir);
	    }

	    long totalExtraido = 0L;

	    try (ZipInputStream zipInputStream =
	            new ZipInputStream(new FileInputStream(archivoZipPath))) {

	        ZipEntry zipEntry = zipInputStream.getNextEntry();

	        while (zipEntry != null) {

	            Path archivoDestino = destinoDir.resolve(zipEntry.getName()).normalize();

	            if (!archivoDestino.startsWith(destinoDir)) {
	                throw new IOException("Entrada ZIP inválida: " + zipEntry.getName());
	            }

	            if (zipEntry.isDirectory()) {
	                Files.createDirectories(archivoDestino);
	            } else {
	                Path parent = archivoDestino.getParent();

	                if (parent != null && !Files.exists(parent)) {
	                    Files.createDirectories(parent);
	                }

	                totalExtraido += extractFile(zipInputStream, archivoDestino);

	                if (totalExtraido > MAX_ZIP_TOTAL_SIZE) {
	                    throw new IOException("El ZIP excede el tamaño máximo permitido.");
	                }
	            }

	            zipInputStream.closeEntry();
	            zipEntry = zipInputStream.getNextEntry();
	        }
	    }
	}

	/**
	 * Método para extaer los archivos del zip
	 * 
	 * @param Zip_Input_Stream
	 * @param File_Path
	 * @throws IOException
	 */
	private long extractFile(ZipInputStream zipInputStream, Path archivoDestino) throws IOException {

	    long totalArchivo = 0L;

	    try (BufferedOutputStream bos =
	            new BufferedOutputStream(new FileOutputStream(archivoDestino.toFile()))) {

	        byte[] buffer = new byte[BUFFER_SIZE];
	        int read;

	        while ((read = zipInputStream.read(buffer)) != -1) {
	            totalArchivo += read;

	            if (totalArchivo > MAX_ZIP_ENTRY_SIZE) {
	                throw new IOException("Archivo dentro del ZIP excede el tamaño máximo permitido.");
	            }

	            bos.write(buffer, 0, read);
	        }
	    }

	    return totalArchivo;
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
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Error: {}", e.getMessage());
		}
	}


}
