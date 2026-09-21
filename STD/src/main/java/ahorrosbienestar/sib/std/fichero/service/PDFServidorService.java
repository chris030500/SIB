package ahorrosbienestar.sib.std.fichero.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;

@Service
public class PDFServidorService {
	
	private IConfiguracionRepository iConfiguracionRepository;
	//protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger =
	        LoggerFactory.getLogger(PDFServidorService.class);
	
	@Autowired
	public PDFServidorService (IConfiguracionRepository iConfiguracionRepository) {
		this.iConfiguracionRepository = iConfiguracionRepository;
	}
	
	public List<Configuracion> obtenerUrlPdfFirma(){
		return iConfiguracionRepository.obtenerUrlPdfFirma();
	}
	
	public String guardarArchivo(String url, MultipartFile file){
		
		if(!file.isEmpty() || file != null) {
			try {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(url+file.getOriginalFilename());
				Files.write(path, bytes);
				logger.info("Archivo guardado");
			} catch (Exception e) {
				logger.error("Ocurrió un error durante el proceso", e);
				//e.printStackTrace();
			}
			
			
		}
		 
		return ("Archivo guardado correctamente");
	}
}
