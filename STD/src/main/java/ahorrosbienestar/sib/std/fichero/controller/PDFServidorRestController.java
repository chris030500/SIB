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
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.FileUtils;
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

import ahorrosbienestar.sib.std.configuracion.login.controller.LoginController;
import ahorrosbienestar.sib.std.fichero.service.PDFServidorService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;

@RestController
@RequestMapping("guardarPDFServidor")
public class PDFServidorRestController {

	@Autowired
	private PDFServidorService pdfServidorService;
	
	@Autowired
	private SolicitudEnvioService solicitudEnvioService;

	//protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger = Logger.getLogger(PDFServidorRestController.class);

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
			logger.info("UbicaciÃ³n del archivo" + UrlPDFServidor() + fileName);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error: "+e);
		}
		logger.info("Archivo subido correctamente");
		return ResponseEntity.ok("Archivo subido correctamente");
	}
	
	@PostMapping("/upload2")
    public String uploadFilez(@RequestParam("file") MultipartFile[] files, 
    		@RequestParam("solicitudCredito") String solicitudCredito,
    		@RequestParam("clienteId") String clienteId,
    		@RequestParam(name = "idEnvio") String idEnvio) throws IllegalStateException, IOException {
		logger.info("Ejecutando en uploadFilez");
		logger.info("solicitudCredito: " + solicitudCredito);
		logger.info("clienteId: " + clienteId);

        if (files.length == 0) {
            return "Parece que no hay archivos subidos";
        }
        
        //Obtenemos los archivos seleccionados
            for (MultipartFile file : files) {
            	
                byte[] bytes = file.getBytes();
                //Path path = Paths.get(UrlPDFServidor() +clienteId + "/"+ clienteId+"-"+solicitudCredito +"/"+ file.getOriginalFilename());
                
                Optional<SolicitudEnvio> sEnvio;
        		sEnvio = solicitudEnvioService.findById(Integer.parseInt(idEnvio));
        		int iDentificadorRuta = sEnvio.get().getI_identificador_ruta();
        		//System.out.println("Identificador: " +iDentificadorRuta);
        		logger.info("Identificador: " + iDentificadorRuta);
                //File directorioInicio = new File(UrlPDFServidor() +clienteId + "/"+ clienteId+"-"+solicitudCredito+"-"+iDentificadorRuta);
                File directorio2 = new File(UrlPDFServidor() +clienteId + "/"+ clienteId+"-"+solicitudCredito+"-"+iDentificadorRuta);
//                String[] filesNames = directorio2.list();
               
                //File directorioInicio = new File(directorio+"-"+iDentificadorRuta);
                
                //Sentencia donde el directorio no existe
                if (!directorio2.exists()) {
                	//System.out.println("Entro en if de no existe el directorio");
                	logger.info("Entro en if de no existe el directorio ");
                    if ((directorio2).mkdirs()) {
                    	String usuarioCarpeta = System.getProperty("user.name");
                    	logger.info("Usuario creaciÃ³n de carpeta: " + usuarioCarpeta);
                        //System.out.println("Usuario creaciÃ³n de carpeta: " + usuarioCarpeta);
                        
//                        Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
//                        Files.setPosixFilePermissions(directorio2, permissions);
//                    	directorio2.setReadable(true, false);
//                    	directorio2.setWritable(true, false);
//                    	directorio2.setExecutable(true, false);
//                        FileAttribute<?> permissions = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxrwxrwx"));
//                        Files.setPosixFilePermissions(directorio2.toPath(), PosixFilePermissions.fromString("rwxrwxrwx"));
                    	logger.info("Directorio"+ directorio2 +" creado correctamente");
                        //System.out.println("Directorio creado");
                        Path nuevoPath = Paths.get(UrlPDFServidor() +clienteId + "/"+ clienteId+"-"+solicitudCredito+"-"+iDentificadorRuta+"/"+ file.getOriginalFilename());
                        Files.write(nuevoPath, bytes);
                    } else {
                    	logger.error("Error al crear directorio: " + directorio2);
                        //System.out.println("Error al crear directorio");
                    }
                } else {
                		logger.info("Si existe" + directorio2);
                		//System.out.println("Si existe" + directorio2);
                    	Path nuevoPath = Paths.get(UrlPDFServidor() +clienteId + "/"+ clienteId+"-"+solicitudCredito+"-"+iDentificadorRuta+"/"+ file.getOriginalFilename());
                        Files.write(nuevoPath, bytes);
                	
                	
                }
//                } else if(directorioInicio.exists()) {
//                	
//    
//                	if(result == false) {
//                		int continuidad = filesNames.length + 1;
//                    	File nuevoDirectorio = new File(directorio+"-"+continuidad);
//                		//System.out.println("nuevoDirectorio: " + nuevoDirectorio);
//                		Path nuevoPath = Paths.get(UrlPDFServidor() +clienteId + "/"+ clienteId+"-"+solicitudCredito+"-"+continuidad+"/"+ file.getOriginalFilename());
//                    	//System.out.println("nuevoPath: " +nuevoPath);
//                		if(nuevoDirectorio.mkdirs()) {
//                    		Files.write(nuevoPath, bytes);
//                    	} else {
//                    		logger.error("Error al crear directorio: " + nuevoDirectorio);
//                            
//                        }
//                		result = true;
//                	} else if (result == true){
//                		int continuidad = filesNames.length;
//                		Path nuevoPath = Paths.get(UrlPDFServidor() +clienteId + "/"+ clienteId+"-"+solicitudCredito+"-"+continuidad+"/"+ file.getOriginalFilename());
//                		//System.out.println("nuevoPath: " + nuevoPath);
//                		try {
//                			Files.write(nuevoPath, bytes);
//                		} catch (Exception e) {
//							// TODO: handle exception
//                			logger.error("No se encontro la ubicacion" + e);
//                			//System.out.println("No se encontro la ubicacion: " + e);
//						}
//                		
//                		
//                	}
//                	
//                }
            }


        return "Subido de archivos correctamente";
    }
	
	


}

