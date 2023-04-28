package ahorrosbienestar.sib.std.autorizacion.descargas;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	// Variable ruta temporal windows
	private final static int RUTA_TEMPORAL_WS = 51;

	//
	private final static String CARPETA_NUEVA = "Videograbacion";

	// Variable que utiliza el método de extraer archivos del zip
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Método para crear guardar la documentacion
	 * 
	 * @param dEnvioID
	 * @throws IOException
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	public GeolocalizacionDTO guardarDocto(Integer solicitudEnvio, Integer clienteId, Float montoAutorizado, String nameProducto)
			throws IOException, ParseException, java.text.ParseException {

		GeolocalizacionDTO respuestaDTO = new GeolocalizacionDTO();
		GeolocalizacionDTO coordenadasDTO = new GeolocalizacionDTO();

		// Obtenemos la ruta temporal
		Configuracion rutaTemporal = configService.obtenerConfiguracion(RUTA_TEMPORAL_WS);

		// Se obtiene la ubicación del archivo zip
		File ubicacionZip = ubicacionZip();

		// Obtenemos las coordenadas
		coordenadasDTO = extaerJSON(ubicacionZip + "\\" + solicitudEnvio + "-" + clienteId + ".zip");

		// Movemos el archivo en la ruta temporal
		Path origen = Paths.get(ubicacionZip + "\\" + solicitudEnvio + "-" + clienteId + ".zip");
		Path destino = Paths.get(rutaTemporal.getVc_valor_propiedad() + "\\" + solicitudEnvio + "-" + clienteId + ".zip");

		Files.move(origen, destino);
		System.out.println("El archivo ha sido movido exitosamente.");

		// Guardamos la bitacora
		guardarBitacora(solicitudEnvio, destino.toString());
		
		SolicitudEnvio soEnvio = solicitudEnvioService.obtenerSolicitudEnvio(solicitudEnvio);
		
		coordenadasDTO.setCustomerNumber(clienteId.toString());
		coordenadasDTO.setOperationAmount(montoAutorizado.toString());
		coordenadasDTO.setIdProduct(String.valueOf(soEnvio.getI_producto_id()));
		coordenadasDTO.setAccessSystem(nameProducto);
		respuestaDTO = llenarGeolocalizacionDTO(coordenadasDTO);
		return respuestaDTO;
	}

	/**
	 * Método para obtener la ubicación del archivo zip
	 * 
	 * @throws IOException
	 */
	public File ubicacionZip() throws IOException {

		// Obtener el sistema operativo actual
		String os = System.getProperty("os.name").toLowerCase();

		// Determinar la ruta de la carpeta de descargas
		String rutaDescargas = "";

		if (os.contains("win")) {
			rutaDescargas = System.getProperty("user.home") + "\\Downloads";
		} else if (os.contains("mac")) {
			rutaDescargas = System.getProperty("user.home") + "/Downloads";
		} else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
			rutaDescargas = System.getProperty("user.home") + "/Downloads";
		} else {
			// Sistema operativo desconocido
			System.err.println("Sistema operativo no soportado.");
			System.exit(1);
		}

		// Imprimir la ruta de la carpeta de descargas
		System.out.println("La carpeta de descargas está ubicada en: " + rutaDescargas);

		// Acceder a la carpeta de descargas
		File carpetaDescargas = new File(rutaDescargas);

		if (carpetaDescargas.exists())
			System.out.println("La carpeta de descargas existe.");
		else
			System.out.println("La carpeta de descargas no existe.");

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
			e.printStackTrace();
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
		
		// Fecha
	    LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formattedDateTime = now.format(formatter);
	    
	    // Dirección
	    String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + llenarDTO.getLatitude() + "&lon=" + llenarDTO.getLongitude();
	    URL requestUrl = new URL(url);
	    InputStream is = requestUrl.openStream();
	    InputStreamReader isr = new InputStreamReader(is);
	    Gson gson = new Gson();
	    JsonObject json = gson.fromJson(isr, JsonObject.class);
	    String address = json.get("display_name").getAsString();
	    System.out.println("Dirección: " + address);
		
		llenarDTO.setNumberProvisions("1");
		llenarDTO.setTypeDevice("M");
		llenarDTO.setRegistrationDataTime(formattedDateTime);
		llenarDTO.setAddressGeolocation(address);
		llenarDTO.setOperation("Nueva disposición");
		llenarDTO.setTypeOperation("2");
		llenarDTO.setIp("0.0.0.0");
		llenarDTO.setTypeProduct("C");
		llenarDTO.setIdState("22");
		
		return llenarDTO;
	}

	/**
	 * Método de extracción zip automático
	 * 
	 * @throws IOException
	 * @throws java.text.ParseException 
	 */
	@Scheduled(cron = "0 0 22 * * ?")
	public void extraerArchivos() throws IOException, java.text.ParseException {
		//
		File carpetaEnvio = new File("C:\\Users\\jose.corona\\Documents\\pruebas\\STD\\Envio");
		File[] archivosEnvio = carpetaEnvio.listFiles();
		//
		File carpetaVideo = new File("C:\\Users\\jose.corona\\Documents\\pruebas\\STD\\Videograbaciones");
		File[] archivosVideo = carpetaVideo.listFiles();
		if (archivosVideo != null) {
			for (File video : archivosVideo) {
				if (video.isFile()) {
					if (archivosEnvio != null) {
						for (File envio : archivosEnvio) {
							//
							String inicioVideo = video.getName();
							String[] partesVideo = inicioVideo.split("-");
							//
							int indicePunto = partesVideo[1].indexOf(".");
							String clienteId = partesVideo[1].substring(0, indicePunto);
							if (clienteId.equals(envio.getName())) {
								// Obtener solicitud de envio
								SolicitudEnvio soEnvio = solicitudEnvioService
										.obtenerSolicitudEnvio(Integer.parseInt(partesVideo[0]));

								// Aquí hace referencia al complemento de la ruta con el cliente
								String rutaCliente = clienteId + "\\" + clienteId + "-"
										+ soEnvio.getI_no_solicitud_core() + "-" + soEnvio.getI_identificador_ruta()
										+ "\\";

								// Se arma la ruta en donde ira la carpeta nueva
								String complemento = carpetaEnvio + "\\" + rutaCliente;

								//
								Path origen = Paths.get(video.toString());
								Path destino = Paths.get(complemento + video.getName());

								Files.move(origen, destino);

								String zipDestino = complemento + "\\" + CARPETA_NUEVA;

								// Descomprimir el archivo .zip
								unzip(destino.toString(), zipDestino);
								
								//
								File fileArchivos = new File(zipDestino);
								File[] files = fileArchivos.listFiles();
								if (files != null) {
									for (File nuevo : files) {
										if (nuevo.isFile()) {
											guardarDoctoVideo(soEnvio.getPk_id_solicitud_envio(), rutaCliente + CARPETA_NUEVA + "\\" + nuevo.getName());
										}
									}
								}
							}
						}
					}
				}
			}
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

		System.out.println("comienza la extracción del .zip");

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
	 * @param solicitudEnvio
	 * @param ruta
	 * @throws java.text.ParseException
	 */
	public void guardarDoctoVideo(Integer solicitudEnvio, String ruta) throws java.text.ParseException {

		DoctoVideoModel model = new DoctoVideoModel();

		SolicitudEnvio soEnvio = solicitudEnvioService.obtenerSolicitudEnvio(solicitudEnvio);

		// Fecha de registro
        // Obtener la fecha actual como un objeto java.util.Date
        Date fechaActual = new Date();
        // Convertir el objeto java.util.Date a un objeto java.time.Instant
        Instant instant = fechaActual.toInstant();
        // Crear un objeto java.time.LocalDate usando el objeto java.time.Instant
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

		model.setDt_fecha_envio(localDate);
		model.setVc_ruta_docto(ruta);
		model.setI_estatus(0);
		model.setFk_solicitud_envio(soEnvio);
		doctoVideoService.save(model);
	}

}
