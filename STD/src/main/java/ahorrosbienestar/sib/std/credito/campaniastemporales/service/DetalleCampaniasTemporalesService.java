package ahorrosbienestar.sib.std.credito.campaniastemporales.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.comunes.paramConstantes.utilerias.ExcelCargar;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
import ahorrosbienestar.sib.std.credito.campaniastemporales.dto.ResultadoCargaExcel;
import ahorrosbienestar.sib.std.credito.campaniastemporales.model.DetalleCampaniasTemporalesModel;
import ahorrosbienestar.sib.std.credito.campaniastemporales.repository.DetalleCampaniasTemporalesRepository;
import ahorrosbienestar.sib.std.credito.tiposCampanias.model.TiposCampaniasModel;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DetalleCampaniasTemporalesService {
	
	private static final Logger logger = Logger.getLogger(DetalleCampaniasTemporalesService.class);

	@Autowired
	DetalleCampaniasTemporalesRepository detalleCampaniasTemporalesRepository;
	
	@Autowired
	ConfigService configService;
	
	public DetalleCampaniasTemporalesModel save(DetalleCampaniasTemporalesModel model) {
		return detalleCampaniasTemporalesRepository.save(model);
	}
	
	public ResultadoCargaExcel detCampaniasTmp(MultipartFile file, Optional<TiposCampaniasModel>  campania, Usuario usuario) {

	    ResultadoCargaExcel resultado = new ResultadoCargaExcel();

	    try {

	        resultado = ExcelCargar.parseDetalleTemporales(file,campania,usuario);

	        // regresa errores de archivo
	        if (resultado.isTieneIncidencias()) {
	            return resultado;
	        }

	        List<DetalleCampaniasTemporalesModel> registros = resultado.getRegistros();
	        
	        //registros a insertar esta vacio
	        if (registros == null || registros.isEmpty()) {
	            return resultado;
	        }

	        
	        final int BATCH_SIZE = 100;
	        List<DetalleCampaniasTemporalesModel> batch = new ArrayList<>();

	        for (DetalleCampaniasTemporalesModel fila : registros) {

	            batch.add(fila);

	            if (batch.size() == BATCH_SIZE) {
	                detalleCampaniasTemporalesRepository.saveAll(batch);
	                batch.clear();
	            }
	        }

	        // guardar los archivos extra
	        if (!batch.isEmpty()) {
	            detalleCampaniasTemporalesRepository.saveAll(batch);
	        }

	    } catch (Exception e) {
	        logger.error("Error procesando archivo Excel", e);
	        resultado.agregarIncidencia("Error general al procesar el archivo: " + e.getMessage());
	    }

	    return resultado;
	}

}
