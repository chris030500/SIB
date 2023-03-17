package ahorrosbienestar.sib.std.autorizacion.descargas;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

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
	private final static int RUTA_CONFIGURACION = 51;

	// Variable ruta del archivo zip
	private final static String RUTA_ZIP = "C:\\Users\\jose.corona\\Documents\\JACS-Documentos\\STD\\detail.zip";

	// Variable nombre de la carpeta
	private final static String NOMBRE_CARPETA = "VIDEOGRABACION";

	// Variable que utiliza el método de extraer archivos del zip
	private static final int BUFFER_SIZE = 4096;

	// Variable para obtener el .json
	private static final String filePath = "C:\\Users\\jose.corona\\Documents\\pruebas\\STD\\Envio\\4\\4-253188-6\\VIDEOGRABACION\\data.json";
	
	
	
	// Método para crear una carpeta nueva
	public void crearCarpeta(Integer dEnvioID) throws IOException {

		// Obtener configuración
		Configuracion rutaConfiguracion = configService.obtenerConfiguracion(RUTA_CONFIGURACION);

		// Obtener solicitud de envio
		SolicitudEnvio solicitudEnvioModel = solicitudEnvioService.obtenerSolicitudEnvio(dEnvioID);

		// Aquí hace referencia al complemento de la ruta con el cliente
		String rutaCliente = solicitudEnvioModel.getFk_usr_registra().getPk_id_usuario() + "/"
				+ solicitudEnvioModel.getFk_usr_registra().getPk_id_usuario() + "-"
				+ solicitudEnvioModel.getI_no_solicitud_core() + "-" + solicitudEnvioModel.getI_identificador_ruta()
				+ "/";

		// Se arma la ruta en donde ira la carpeta nueva
		String complemento = rutaConfiguracion.getVc_valor_propiedad() + rutaCliente;

		// Aqui se define la ubicación de la carpeta nueva
		File crearCarpeta = new File(complemento + NOMBRE_CARPETA);

		// Pregunta si existe la carpeta
		if (!crearCarpeta.exists()) {
			// Pregunta si se puede realizar la acción
			if (crearCarpeta.mkdirs()) {
				// Se crea la carpeta
				System.out.println("Carpeta creada");
				// Descomprimir el archivo .zip
				unzip(RUTA_ZIP, crearCarpeta.toString());
			} else {
				// Hubo un error
				System.out.println("Error al crear carpeta");
			}
		} else {
			// Descomprimir el archivo .zip
			unzip(RUTA_ZIP, crearCarpeta.toString());
		}
	}
	
	
	
	// Método para obtener el archivo .zip
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
	
	
	
	// Método para extaer los archivos del zip
	private static void extractFile(ZipInputStream Zip_Input_Stream, String File_Path) throws IOException {
		BufferedOutputStream Buffered_Output_Stream = new BufferedOutputStream(new FileOutputStream(File_Path));
		byte[] Bytes = new byte[BUFFER_SIZE];
		int Read_Byte = 0;
		while ((Read_Byte = Zip_Input_Stream.read(Bytes)) != -1) {
			Buffered_Output_Stream.write(Bytes, 0, Read_Byte);
		}
		Buffered_Output_Stream.close();
	}
	
	
	
	// Método para obtener los valores de un archivo .json
	public void extaerJSON() {
		
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

        System.out.println("Latitude: " + latitude);
        System.out.println("Longitude:  " + longitude);
	}
	
	
	
	// Método para mover archivos
	/*
	 * public void moverArchivos(Integer dEnvioID) throws IOException {
	 * 
	 * // Obtener configuración Configuracion configuracionModel =
	 * configService.obtenerConfiguracion(RUTA_CONFIGURACION);
	 * 
	 * // Obtener solicitud de envio SolicitudEnvio solicitudEnvioModel =
	 * solicitudEnvioService.obtenerSolicitudEnvio(dEnvioID);
	 * 
	 * // Aquí hace referencia al destino donde se van a mover los archivos String
	 * destino = configuracionModel.getVc_valor_propiedad() + "/" +
	 * solicitudEnvioModel.getFk_usr_registra().getPk_id_usuario() + "/" +
	 * solicitudEnvioModel.getFk_usr_registra().getPk_id_usuario() + "-" +
	 * solicitudEnvioModel.getPk_id_solicitud_envio() + "-" +
	 * solicitudEnvioModel.getI_identificador_ruta() + "/";
	 * 
	 * // Se recupera los documentos que pertenecen a la solicitud de envio
	 * doctEnvioService.listaDocumentosPorSolicitudEnvio(dEnvioID).forEach(doct -> {
	 * 
	 * try {
	 * 
	 * // Obtener el nombre del archivo URL origen = new
	 * URL(doct.getVc_ruta_docto());
	 * 
	 * // Aquí realiza el cambio entre carpetas/ubicación Path cambioDeCarpeta =
	 * Files.move(Paths.get(doct.getVc_ruta_docto()), Paths.get(destino +
	 * origen.getFile()));
	 * 
	 * // Se notifica si realizo el cambio if (cambioDeCarpeta != null)
	 * System.out.println("El archivo se movió correctamente"); else
	 * System.out.println("Hubo un error al mover el archivo");
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }); }
	 */

}
