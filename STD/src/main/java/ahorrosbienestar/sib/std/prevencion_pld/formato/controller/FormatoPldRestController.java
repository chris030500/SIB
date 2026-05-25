package ahorrosbienestar.sib.std.prevencion_pld.formato.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;

@RestController
public class FormatoPldRestController {

	private static final int REPOSITORIO_FORMATO = 64;
	private static final String NOMBRE_ARCHIVO = "FormatoTerceros.txt";
	private static final String ARCHIVO_FINAL = "FormatoTerceros";

	@Autowired
	private ConfigService configService;

	private static final Logger logger = Logger.getLogger(FormatoPldRestController.class);

	@RequestMapping(value = { "/crear_copia" }, method = RequestMethod.GET)
	public String copiarFormato() {

		try {

			LocalDate fechaLocal = LocalDate.now();
			Configuracion configuracionModel = configService.obtenerConfiguracion(REPOSITORIO_FORMATO);
			
			String targetFileName = ARCHIVO_FINAL + "-" + fechaLocal.toString() + ".txt";
			String sourceFilePath = System.getProperty("user.home") + "/Downloads/" + NOMBRE_ARCHIVO;
			String targetDirectoryPath = configuracionModel.getVc_valor_propiedad();
			
			// Crear una copia del archivo
			File sourceFile = new File(sourceFilePath);
			File targetFile = new File(targetDirectoryPath + targetFileName);
			copyFile(sourceFile, targetFile);

			// Mover la copia a una ubicación específica
			moveFile(targetFile, targetDirectoryPath);

			logger.info("Copia del archivo creada y movida exitosamente.");
			return "Copia guardada";
		} catch (IOException e) {
		    logger.error("Error al crear o mover la copia del formato PLD.", e);
		    return "Hubo un error";
		}
	}

	private static void copyFile(File sourceFile, File targetFile) throws IOException {
		try (FileInputStream fis = new FileInputStream(sourceFile);
				FileOutputStream fos = new FileOutputStream(targetFile)) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
		}
	}

	private static void moveFile(File fileToMove, String targetDirectory) throws IOException {
		Path targetPath = Paths.get(targetDirectory, fileToMove.getName());
		Files.move(fileToMove.toPath(), targetPath);
	}
}
