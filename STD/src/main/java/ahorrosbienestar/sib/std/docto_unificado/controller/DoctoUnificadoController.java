package ahorrosbienestar.sib.std.docto_unificado.controller;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.docto_unificado.model.DoctoUnificado;
import ahorrosbienestar.sib.std.docto_unificado.service.DoctoUnificadoService;
import ahorrosbienestar.sib.std.docto_unificado.service.HashService;
import ahorrosbienestar.sib.std.fichero.service.PDFServidorService;
import ahorrosbienestar.sib.std.firma_docto_unificado.model.FirmaDoctoUnificado;
import ahorrosbienestar.sib.std.firma_docto_unificado.service.FirmaDoctoUnificadoService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;

@RestController
public class DoctoUnificadoController {

	@Autowired
	private DoctoUnificadoService doctoUnificadoService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private HashService hashService;

	@Autowired
	private PDFServidorService pdfServidorService;

	@Autowired
	private SolicitudEnvioService solicitudEnvioService;

	//protected final Log logger = LogFactory.getLog(this.getClass());

	private static final Logger logger =
	        LoggerFactory.getLogger(DoctoUnificadoController.class);
	
	@GetMapping("archivos/obtenerArchivos")
	public ResponseEntity<?> obtenerArchivos(@RequestParam("soliEnvio") Integer soliEnvio) {

		try {

//			Optional<SolicitudEnvio> sEnvio;
//			sEnvio = solicitudEnvioService.findById(soliEnvio);
//
//			int iDentificadorRuta = sEnvio.get().getI_identificador_ruta();
			// System.out.println("identificardor: " + iDentificadorRuta);
//			String url = UrlServidor() + clienteId + "/" + clienteId + "-" + solicitudCredito + "-" + iDentificadorRuta
//					+ "/";
			// System.out.println("url carpeta: " + url);
			Optional<DoctoUnificado> archivosPdfXml = doctoUnificadoService.findByFkIdSolicituEnvio(soliEnvio);
			if (archivosPdfXml.isPresent()) {
				logger.info("Tiene información");
//				String validacionPDF = archivosPdfXml.get().getVc_ruta_archivo_pdf();
//				String validacionXML = archivosPdfXml.get().getVc_ruta_archivo_xml();
				System.out.println("archivoPdf: " + UrlServidor() + archivosPdfXml.get().getVc_ruta_archivo_pdf());
				System.out.println("archivoXML: " + UrlServidor() + archivosPdfXml.get().getVc_ruta_archivo_xml());
				logger.info("archivoPdf: " + UrlServidor() + archivosPdfXml.get().getVc_ruta_archivo_pdf());
				logger.info("archivoXML: " + UrlServidor() + archivosPdfXml.get().getVc_ruta_archivo_xml());
				String archivoPdf = UrlServidor() + archivosPdfXml.get().getVc_ruta_archivo_pdf();
				String archivoXml = UrlServidor() + archivosPdfXml.get().getVc_ruta_archivo_xml();

				//String[] archivosRequeridos = {archivoPdf, archivoXml };
				int tiempoEspera = 5000;
				int banderaArchivos = 0;
				while (true) {
					boolean archivosValidos = validarArchivos(archivoPdf, archivoXml);
					System.out.println("archivosValidos : " + archivosValidos);
					if (archivosValidos) {
						System.out.println("Todos los archivos existen.");
						banderaArchivos = 1;
						break;
					} else {
						banderaArchivos = 0;
						logger.info("Algunos archivos no existen. Esperando " + tiempoEspera + " milisegundos...");
						try {
						    Thread.sleep(tiempoEspera);
						} catch (InterruptedException e) {
						    Thread.currentThread().interrupt();
						    logger.error("Proceso interrumpido al esperar la validación de archivos.", e);
						    return ResponseEntity.internalServerError()
						            .body("Proceso interrumpido al validar archivos.");
						}
					}
				}
				
				if(banderaArchivos == 1) {
					// Obtenemos la ubicacion de los archivos.
					DoctoUnificado doctoUnificado = doctoUnificadoService.findByFkIdSolicituEnvio(soliEnvio).get();

					// Obtenemos la url donde se alojan los documentos
					String url_base = configService.findAll().get(13).getVc_valor_propiedad();

					String xml = doctoUnificado.getVc_ruta_archivo_xml();
					String pdf = doctoUnificado.getVc_ruta_archivo_pdf();

					String archivoPDF = url_base + pdf;

					File fiPDF = new File(archivoPDF);

					// Establecemos el formato del HASH
					MessageDigest md = MessageDigest.getInstance("SHA-256");

					String hashPdf = hashService.getFileChecksum(md, fiPDF);

					List<String> documentos = new ArrayList<>(Arrays.asList(xml, pdf, hashPdf));
					return ResponseEntity.ok(documentos);
				}

			} else {
				System.out.println("Es nulo");
				return ResponseEntity.ok("0");
			}

		} catch (Exception e) {
		    if (e instanceof InterruptedException) {
		        Thread.currentThread().interrupt();
		    }
		    logger.error("Error al obtener archivos unificados.", e);
		    return ResponseEntity.internalServerError()
		            .body("Error al obtener archivos unificados.");
		}
		return null;

	}

//	private static boolean validarArchivos(String[] archivosRequeridos) {
//		boolean todosExisten = true;
//		for (String archivo : archivosRequeridos) {
//			System.out.println("Entro en for validarArchivos");
//			System.out.println("archivosRequeridos: " +archivosRequeridos);
//			System.out.println("archivo: " +archivo);
//			File file = new File(archivo);
//			if (!file.exists()) {
//				todosExisten = false;
//				break;
//			}
//		}
//		return todosExisten;
//	}
	
	 private static boolean validarArchivos(String archivo1Url, String archivo2Url) {
		 System.out.println("archivo1Url: " + archivo1Url);
		 System.out.println("archivo2Url: " + archivo2Url);
	        boolean archivo1Existe = archivoExiste(archivo1Url);
	        boolean archivo2Existe = archivoExiste(archivo2Url);
	        System.out.println("archivo1Existe: " + archivo1Existe);
	        System.out.println("archivo2Existe: " + archivo2Existe);
	        return archivo1Existe && archivo2Existe;
	    }

	    private static boolean archivoExiste(String url) {
	        File archivo = new File(url);
			if (archivo.exists()) {
				return true;
			} else {
				return false;
			}
//	        	System.out.println("url: " + url);
//	            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//	            connection.setRequestMethod("HEAD");
//	            int responseCode = connection.getResponseCode();
//	            return responseCode == HttpURLConnection.HTTP_OK;
	    }

	@GetMapping("archivos/url")
	public String UrlServidor() {
		String url = pdfServidorService.obtenerUrlPdfFirma().get(0).getVc_valor_propiedad();
		logger.info("Url: " + url);
		// System.out.println("URL: " + url);
		return url.toString();
	}

	// Obtener la ruta de los archivos PDF y XML
	@GetMapping("archivos/validarArchivoPDFyXML")
	public ResponseEntity<?> validarArchivoPDFyXML(@RequestParam("soliEnvio") Integer soliEnvio,
			@RequestParam("solicitudCredito") String solicitudCredito, @RequestParam("clienteId") String clienteId) {
		// System.out.println("Entro en validarArchivoPDFyXML");
		logger.info("Entro en validarArchivoPDFyXML");
		logger.info("SoliEnvio: " + soliEnvio);

		// System.out.println("SoliEnvio: " +soliEnvio);

		Optional<SolicitudEnvio> sEnvio;
		sEnvio = solicitudEnvioService.findById(soliEnvio);

		int iDentificadorRuta = sEnvio.get().getI_identificador_ruta();
		// System.out.println("identificardor: " + iDentificadorRuta);
		String url = UrlServidor() + clienteId + "/" + clienteId + "-" + solicitudCredito + "-" + iDentificadorRuta
				+ "/";
		// System.out.println("url carpeta: " + url);
		Optional<DoctoUnificado> archivosPdfXml = doctoUnificadoService.findByFkIdSolicituEnvio(soliEnvio);
		if (archivosPdfXml.isPresent()) {
			logger.info("Tiene información");
			String validacionPDF = archivosPdfXml.get().getVc_ruta_archivo_pdf();
			String validacionXML = archivosPdfXml.get().getVc_ruta_archivo_xml();
			System.out.println("URL: " + url);
			System.out.println("UrlServidor: " +UrlServidor());
			System.out.println("archivoPdf: " + UrlServidor() + archivosPdfXml.get().getVc_ruta_archivo_pdf());
			System.out.println("archivoXML: " + UrlServidor() + archivosPdfXml.get().getVc_ruta_archivo_xml());
			logger.info("archivoPdf: " + UrlServidor() + archivosPdfXml.get().getVc_ruta_archivo_pdf());
			logger.info("archivoXML: " + UrlServidor() + archivosPdfXml.get().getVc_ruta_archivo_xml());
			String pdf = UrlServidor() + archivosPdfXml.get().getVc_ruta_archivo_pdf();
			String xml = UrlServidor() + archivosPdfXml.get().getVc_ruta_archivo_xml();

			String[] archivosRequeridos = { pdf, xml };
			return ResponseEntity.ok(archivosRequeridos);
		} else {
			System.out.println("Es nulo");
			return ResponseEntity.ok("0");
		}

	}

}
