package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DetallePEPsModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesosPEPsModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ReporteClientesOpeModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.DetallePEPsRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ProcesosPEPsRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.util.ExcelPEPReader;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DetallePEPsService {
	@Autowired
	private DetallePEPsRepository  detallePEPsRepository;
	
	public DetallePEPsModel save(DetallePEPsModel model) {
		return detallePEPsRepository.save(model);
	}

	public void procesarArchivoPEP(MultipartFile file) throws Exception {
		  List<DetallePEPsModel> filas = ExcelPEPReader.parse(file);
	        if (filas.isEmpty()) return;

	        AtomicInteger insertados = new AtomicInteger(0);
	        AtomicInteger actualizados = new AtomicInteger(0);

	        for (DetallePEPsModel fila : filas) {
	        	detallePEPsRepository.findById(fila.getI_clienteid()).ifPresentOrElse(existing -> {
	                if (!Objects.equals(existing.getVc_nombre_cliente(), fila.getVc_nombre_cliente())) {
	                    existing.setVc_nombre_cliente(fila.getVc_nombre_cliente());
	                    detallePEPsRepository.save(existing);
	                    actualizados.incrementAndGet();
	                }
	            }, () -> {
	            	detallePEPsRepository.save(fila);
	                insertados.incrementAndGet();
	            });
	        }

	        System.out.println("Insertados: " + insertados.get() + ", Actualizados: " + actualizados.get());
		
	}
}
