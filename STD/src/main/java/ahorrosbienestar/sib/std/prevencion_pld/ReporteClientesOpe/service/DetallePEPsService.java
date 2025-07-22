package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;
 
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
 
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DetallePEPsModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.DetallePEPsRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.util.ExcelPEPReader;
import lombok.AllArgsConstructor;
 
@Service
@AllArgsConstructor
public class DetallePEPsService {
    
     private static final Logger logger = Logger.getLogger(DetallePEPsService.class);
    @Autowired
    private DetallePEPsRepository  detallePEPsRepository;
    
    public DetallePEPsModel save(DetallePEPsModel model) {
        return detallePEPsRepository.save(model);
    }
 
    public void procesarCargaConUsuario(MultipartFile file, int idProceso) {
        try {
            logger.info("Iniciando lectura del archivo...");
            List<DetallePEPsModel> filas = ExcelPEPReader.parse(file);
            logger.info("Archivo leído. Total de filas: " + filas.size());

            if (filas.isEmpty()) {
                logger.warn("El archivo no contiene filas válidas.");
                return;
            }

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
                    fila.setFk_proceso(idProceso);
                    detallePEPsRepository.save(fila);
                    insertados.incrementAndGet();
                });
            }

            logger.info("Proceso finalizado. Insertados: " + insertados + ", Actualizados: " + actualizados);
        } catch (Exception e) {
            logger.error("Error en procesarCargaConUsuario: ", e);
            throw new RuntimeException("Error al procesar archivo: " + e.getMessage(), e);
        }
    }
}

