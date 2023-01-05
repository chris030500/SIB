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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ahorrosbienestar.sib.std.fichero.service.PDFServidorService;

@RestController
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

	
	@PostMapping("/upload")
	public ResponseEntity<?> fileUpload(@RequestParam(value = "file") MultipartFile file){
		
		String fileName = file.getOriginalFilename();
		logger.info("Nombre del archivo guardado: " + fileName);
		//System.out.println("FileName: " + fileName);
		
		try {
			file.transferTo(new File(UrlPDFServidor() + fileName));
			logger.info("Ubicación del archivo" + UrlPDFServidor() + fileName);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error: "+e);
		}
		//logger.info("Archivo subido correctamente");
		return ResponseEntity.ok("Archivo subido correctamente");
	}
	
	@PostMapping("/upload2")
    public String uploadFilez(@RequestParam("file") MultipartFile[] files) throws IllegalStateException, IOException {

		System.out.println("Entro en uploadFilez");
        if (files.length == 0) {
            return "Parece que no hay archivos subidos";
        }

        //Obtenemos los archivos seleccionados
            for (MultipartFile file : files) {
            	//System.out.println("Files: " + files.length);
            	//System.out.println("Ubicación: " + (UrlPDFServidor() + file.getOriginalFilename()));
            	file.transferTo(new File(UrlPDFServidor() + file.getOriginalFilename()));
//                byte[] bytes = file.getBytes();
//                Path path = Paths.get(UrlPDFServidor() + file.getOriginalFilename());
//                System.out.println("Url del servidor: " + path);
//                Files.write(path, bytes);
            }


        return "Subido de archivos correctamente";
    }
	
	


}
