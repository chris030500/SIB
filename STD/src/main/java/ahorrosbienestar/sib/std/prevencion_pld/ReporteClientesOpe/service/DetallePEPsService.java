package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    private static final Logger logger =
            Logger.getLogger(DetallePEPsService.class);

    @Autowired
    private DetallePEPsRepository detallePEPsRepository;

    // ===============================
    // SAVE SIMPLE
    // ===============================
    public DetallePEPsModel save(DetallePEPsModel model) {
        return detallePEPsRepository.save(model);
    }

    // ===============================
    // CARGA DE EXCEL PEP
    // ===============================
    public void procesarCargaConUsuario(MultipartFile file, int idProceso) {

        try {
            logger.info("Iniciando lectura del archivo Excel PEP...");

            List<DetallePEPsModel> filas = ExcelPEPReader.parse(file);

            if (filas == null || filas.isEmpty()) {
                logger.warn("El archivo no contiene filas válidas.");
                return;
            }

            logger.info("Filas leídas: " + filas.size());

            int insertados = 0;

            for (DetallePEPsModel fila : filas) {

                //  SIEMPRE INSERTAR
                fila.setFk_proceso(idProceso);
                detallePEPsRepository.save(fila);
                insertados++;
            }

            logger.info(
                "Proceso finalizado → Registros insertados: " + insertados
            );

        } catch (Exception e) {
            logger.error("Error en procesarCargaConUsuario", e);
            throw new RuntimeException(
                e.getMessage() != null
                    ? e.getMessage()
                    : "Error al procesar archivo Excel PEP"
            );
        }
    }

}
