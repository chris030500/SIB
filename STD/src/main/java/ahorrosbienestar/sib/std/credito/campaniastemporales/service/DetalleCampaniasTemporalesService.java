package ahorrosbienestar.sib.std.credito.campaniastemporales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.comunes.paramConstantes.utilerias.ExcelCargar;
import ahorrosbienestar.sib.std.credito.campaniastemporales.model.DetalleCampaniasTemporalesModel;
import ahorrosbienestar.sib.std.credito.campaniastemporales.repository.DetalleCampaniasTemporalesRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DetalleCampaniasTemporalesService {

	@Autowired
	DetalleCampaniasTemporalesRepository detalleCampaniasTemporalesRepository;
	
	public DetalleCampaniasTemporalesModel save(DetalleCampaniasTemporalesModel model) {
		return detalleCampaniasTemporalesRepository.save(model);
	}
	
	public void detCampaniasTmp(MultipartFile file) {
	//  Parsear archivo Excel
		try {
			   List<DetalleCampaniasTemporalesModel> filas = ExcelCargar.parseDetalleTemporales(file);
			   if (filas.isEmpty()) return;
			    
	            for (DetalleCampaniasTemporalesModel fila : filas) {
	            	
	                	detalleCampaniasTemporalesRepository.save(fila);
	               
	            }
	 
	            //System.out.println("Insertados: " + insertados.get() + ", Actualizados: " + actualizados.get());
				
		} catch (Exception e) {
			// TODO: handle exception
		}
     
	}

}
