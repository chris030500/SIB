package ahorrosbienestar.sib.std.autorizacion.descargas;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.autorizacion.descargas.dto.GeolocalizacionDTO;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.docto_envio.service.DoctEnvioService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class DescargaRestController {

	@Autowired
	private ConfigService configService;

	@Autowired
	private SolicitudEnvioService solicitudEnvioService;

	// Variable ruta de almacenamiento
	private final static int RUTA_CONFIGURACION = 14;

	// Variable nombre de la carpeta
	private final static String NOMBRE_CARPETA = "VIDEOGRABACION";

	// Variable que utiliza el método de extraer archivos del zip
	private static final int BUFFER_SIZE = 4096;

	
	/**
	 * Método para crear una carpeta nueva
	 * @param dEnvioID
	 * @throws IOException
	 */
	public GeolocalizacionDTO crearCarpeta(Integer dEnvioID) throws IOException {

		// Obtener configuración
		Configuracion rutaConfiguracion = configService.obtenerConfiguracion(RUTA_CONFIGURACION);

		// Obtener solicitud de envio
		SolicitudEnvio solicitudEnvioModel = solicitudEnvioService.obtenerSolicitudEnvio(dEnvioID);

		// Aquí hace referencia al complemento de la ruta con el cliente
		String rutaCliente = solicitudEnvioModel.getFk_usr_registra().getI_id_empleado() + "/"
				+ solicitudEnvioModel.getFk_usr_registra().getI_id_empleado() + "-"
				+ solicitudEnvioModel.getI_no_solicitud_core() + "-" + solicitudEnvioModel.getI_identificador_ruta()
				+ "/";

		// Se arma la ruta en donde ira la carpeta nueva
		String complemento = rutaConfiguracion.getVc_valor_propiedad() + rutaCliente;

		// Aqui se define la ubicación de la carpeta nueva
		File crearCarpeta = new File(complemento + NOMBRE_CARPETA);

		// Se obtiene la ubicación del archivo zip
		File origen = ubicacionDeArchivo();

		// Le agregamos a la ruta el nombre del archivo
		String rutaZip = origen.toString() + "\\videograbacion.zip";

		// Pregunta si existe la carpeta
		if (!crearCarpeta.exists()) {

			// Pregunta si se puede realizar la acción
			if (crearCarpeta.mkdirs()) {

				// Se crea la carpeta
				System.out.println("Carpeta creada");

				// Descomprimir el archivo .zip
				unzip(rutaZip, crearCarpeta.toString());

			} else {

				// Hubo un error
				System.out.println("Error al crear carpeta");
			}
		} else {

			// Descomprimir el archivo .zip
			unzip(rutaZip, crearCarpeta.toString());
		}
		
		String geolocalizacion = crearCarpeta.toString() + "\\data.json";
		
		// Recuperamos las coordenadas
		GeolocalizacionDTO respuestaDTO = extaerJSON(geolocalizacion);
		
		return respuestaDTO;
	}
	

	/**
	 * Método para obtener la ubicación del archivo zip
	 * 
	 * @throws IOException
	 */
	public File ubicacionDeArchivo() throws IOException {

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
	 * Método para mover los datos del archivo .zip
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
	 * Método para obtener los valores de un archivo .json
	 * @return
	 */
	public GeolocalizacionDTO extaerJSON(String filePath) {

		GeolocalizacionDTO responseDTO = new GeolocalizacionDTO();

		// parsing file "JSONExample.json"
		Object ob = null;

		try {
			ob = new JSONParser().parse(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// typecasting ob to JSONObject
		JSONObject js = (JSONObject) ob;

		String latitude = (String) js.get("latitude");
		String longitude = (String) js.get("longitude");

		responseDTO.setLatitud(latitude);
		responseDTO.setLongitud(longitude);

		return responseDTO;
	}

}
