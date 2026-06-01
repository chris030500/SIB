package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import java.util.List;
import java.util.Objects;

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

    public DetallePEPsModel save(DetallePEPsModel model) {
        return detallePEPsRepository.save(model);
    }

    public void procesarCargaConUsuario(MultipartFile file, int idProceso) {
        try {
            logger.info("Iniciando lectura del archivo Excel PEP...");

            List<DetallePEPsModel> filas = ExcelPEPReader.parse(file);

            if (filas == null || filas.isEmpty()) {
                logger.warn("El archivo no contiene filas validas.");
                return;
            }

            logger.info("Filas leidas: " + filas.size());

            int insertados = 0;
            int actualizados = 0;

            for (DetallePEPsModel fila : filas) {
                List<DetallePEPsModel> existentes =
                        detallePEPsRepository.buscarPorClienteId(fila.getIClienteId());

                if (!existentes.isEmpty()) {
                    DetallePEPsModel existing = existentes.get(0);
                    if (!Objects.equals(existing.getVcNombreCliente(), fila.getVcNombreCliente())) {
                        existing.setVcNombreCliente(fila.getVcNombreCliente());
                        detallePEPsRepository.save(existing);
                        actualizados++;
                    }
                } else {
                    fila.setFkProceso(idProceso);
                    detallePEPsRepository.save(fila);
                    insertados++;
                }
            }

            logger.info(
                    "Proceso finalizado -> Registros insertados: " + insertados
                    + ", actualizados: " + actualizados
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
