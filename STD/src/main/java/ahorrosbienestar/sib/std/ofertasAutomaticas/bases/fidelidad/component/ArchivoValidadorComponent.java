/**
 * 
 */
package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.component;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author osvaldo.escamilla
 *
 */
@Component
public class ArchivoValidadorComponent {
	

	private static final Logger logger =
	        LoggerFactory.getLogger(ArchivoValidadorComponent.class);
	
	public boolean existeArchivo(String directorioPaht, String nombreArchivo) {
		
		try {
			logger.info("existeArchivo");
			File directorio = new File(directorioPaht);
			if(!directorio.isDirectory()) {
				throw new IllegalArgumentException(directorioPaht + " no es un directorio valido");
			}
			
			File[] files = directorio.listFiles();
			if(files != null) {
				for(File file : files) {
					if(file.isFile() && file.getName().equals(nombreArchivo)) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			logger.error("Error:"+e.getMessage());
			return false;
		}
		
	
	}

}
