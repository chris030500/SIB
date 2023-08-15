/**
 * 
 */
package ahorrosbienestar.sib.std.ofertasAutomaticas.bases.fidelidad.component;

import java.io.File;

import org.springframework.stereotype.Component;

/**
 * @author osvaldo.escamilla
 *
 */
@Component
public class ArchivoValidadorComponent {
	
	public boolean existeArchivo(String directorioPaht, String nombreArchivo) {
		System.out.println("existeArchivo");
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
	}

}
