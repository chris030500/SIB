package ahorrosbienestar.sib.std.fichero.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ahorrosbienestar.sib.std.fichero.service.PDFServidorService;

@Controller
@RequestMapping("guardarPDFServidor")
public class PDFServidorRestController {

	@Autowired
	private PDFServidorService pdfServidorService;

	protected final Log logger = LogFactory.getLog(this.getClass());

	// Consultar la url para el almacenamiento para de los archivos pdf
	@GetMapping
	public String UrlPDFServidor() {
		String url = pdfServidorService.obtenerUrlPdfFirma().get(0).getVc_valor_propiedad();
		logger.info("Url: " + url);
		// System.out.println("URL: " + url);
		return url.toString();
	}

	@GetMapping("/almacenarPdf")
	public ResponseEntity<?> almacenarPDFServidor(HttpServletRequest request) throws IOException, ServletException {
		String description = request.getParameter("description");
		// String nombre = "AnexosContrato.pdf";
		System.out.println("LocalName: " + request.getLocalName());

		InputStream is = request.getInputStream();
		System.out.println("Is: " + is);
		File f = new File(UrlPDFServidor() + request.getLocalName());
		FileOutputStream ous = new FileOutputStream(f);
		int datoPdf = is.read();
		while (datoPdf != -1) {
			ous.write(datoPdf);
			datoPdf = is.read();
		}
		ous.close();
		is.close();
		return ResponseEntity.ok("Exito");
	}

	@RequestMapping(value = "/almacenarPdf2", headers=("content-type=multipart/*"), 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> almacenarPDFServidor2(@RequestPart("file") MultipartFile file ) throws IOException, ServletException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path path = Paths.get(UrlPDFServidor() + fileName);
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/files/download/")
				.path(fileName)
				.toUriString();
		return ResponseEntity.ok(fileDownloadUri);
	}
	
	@PostMapping("/cargar")
	public String carga(@RequestParam("file") MultipartFile file) {
		pdfServidorService.guardarArchivo(UrlPDFServidor(), file);
		return "exito";
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> fileUpload(@RequestParam(value = "file") MultipartFile file){
		String fileName = file.getOriginalFilename();
		
		try {
			file.transferTo(new File(UrlPDFServidor() + fileName));
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error: "+e);
		}
		
		return ResponseEntity.ok("Archivo subido correctamente");
	}
	
	


}
