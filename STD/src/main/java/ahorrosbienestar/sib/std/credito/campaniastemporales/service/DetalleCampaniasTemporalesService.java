package ahorrosbienestar.sib.std.credito.campaniastemporales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.comunes.paramConstantes.utilerias.ExcelCargar;
import ahorrosbienestar.sib.std.configuracion.model.Configuracion;
import ahorrosbienestar.sib.std.configuracion.repository.IConfiguracionRepository;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.credito.campaniastemporales.model.DetalleCampaniasTemporalesModel;
import ahorrosbienestar.sib.std.credito.campaniastemporales.repository.DetalleCampaniasTemporalesRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DetalleCampaniasTemporalesService<ObjectMapper> {

	@Autowired
	DetalleCampaniasTemporalesRepository detalleCampaniasTemporalesRepository;
	
	@Autowired
	ConfigService configService;
	
	public DetalleCampaniasTemporalesModel save(DetalleCampaniasTemporalesModel model) {
		return detalleCampaniasTemporalesRepository.save(model);
	}
	
	public void detCampaniasTmp(MultipartFile file) {
		
		 Configuracion configuracion = configService.valorPropiedad("campaniasTemporales");

		 ObjectMapper mapper = new ObjectMapper();

		Map<String, HeaderRule> headerRules =
		        mapper.readValue(
		            config.getHeaderConfig(),
		            new TypeReference<>() {}
		        );
		
		
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
