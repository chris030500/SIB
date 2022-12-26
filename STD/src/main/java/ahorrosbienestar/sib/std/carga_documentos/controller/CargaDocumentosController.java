package ahorrosbienestar.sib.std.carga_documentos.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.core_bancario.dto.ParticipantesDto;
import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import ahorrosbienestar.sib.std.fichero.service.PDFServidorService;


@Controller
public class CargaDocumentosController {
	
	@Autowired
	private CoreBancarioService coreBancarioService;
	
	@Autowired PDFServidorService pdfServidorService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

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
		pdfServidorService.guardarArchivo(UrlPDFServidor(), file);
		return "exito";
	}
	
}
