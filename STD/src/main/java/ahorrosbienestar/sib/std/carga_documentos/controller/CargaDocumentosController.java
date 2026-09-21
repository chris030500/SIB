package ahorrosbienestar.sib.std.carga_documentos.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.configuracion.login.controller.LoginController;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import ahorrosbienestar.sib.std.fichero.service.PDFServidorService;

@Controller
public class CargaDocumentosController {

	@Autowired
	private CoreBancarioService coreBancarioService;

	@Autowired
	PDFServidorService pdfServidorService;

	@Autowired
	private ConfigService configService;

	//protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger = Logger.getLogger(CargaDocumentosController.class);

	// Consultar la url para el almacenamiento para de los archivos pdf
	@GetMapping
	public String UrlPDFServidor() {
		String url = pdfServidorService.obtenerUrlPdfFirma().get(0).getVc_valor_propiedad();
		logger.info("Url: " + url);
		// System.out.println("URL: " + url);
		return url.toString();
	}

	@GetMapping("/carga_documentos")
	public String InicioUsuario() {

		return "documentos/carga_documentos";

	}

	@PostMapping("/cargar")
	public String carga(@RequestParam("file") MultipartFile file) {
		logger.info("Ejecutando carga");
		pdfServidorService.guardarArchivo(UrlPDFServidor(), file);
		return "exito";
	}

	@GetMapping("/obtenerDiferenciaDias")
	public ResponseEntity<?> obtenerDiferenciaDias() {
		try {
			logger.info("Ejecutando obtenerDiferenciaDias");
			String dias = configService.obtenerConfiguracion(50).getVc_valor_propiedad();
			// System.out.println("dias: " + dias);
			logger.info("DiferenciaDias" + dias);
			return ResponseEntity.ok(Integer.parseInt(dias));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.ok("Error interno al procesar la solicitud.");
		}

	}

}
