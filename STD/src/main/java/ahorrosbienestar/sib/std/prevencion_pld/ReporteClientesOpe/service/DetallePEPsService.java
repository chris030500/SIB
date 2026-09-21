package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.DetallePEPsModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.DetallePEPsRepository;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.util.ExcelPEPReader;

@Service
public class DetallePEPsService {

	private static final Logger logger =
	        LoggerFactory.getLogger(DetallePEPsService.class);

    private final DetallePEPsRepository detallePEPsRepository;

    @Autowired
    public DetallePEPsService(
            DetallePEPsRepository detallePEPsRepository
    ) {
        this.detallePEPsRepository = detallePEPsRepository;
    }

    public DetallePEPsModel save(DetallePEPsModel model) {
        return detallePEPsRepository.save(model);
    }

    @Transactional
    public void procesarCargaConUsuario(
            MultipartFile file,
            int idProceso
    ) {

        try {

            logger.info("Iniciando lectura del archivo...");

            List<DetallePEPsModel> filas =
                    ExcelPEPReader.parse(file);

            logger.info(
                "Archivo leído. Total de registros válidos: "
                + filas.size()
            );

            if (filas.isEmpty()) {
                throw new IllegalArgumentException(
                    "El archivo no contiene registros válidos."
                );
            }

            /*
             * Cada registro debe insertarse como nuevo y quedar
             * relacionado con el proceso generado para la carga.
             */
            for (DetallePEPsModel fila : filas) {

                /*
                 * Se establece en NULL para que Hibernate genere
                 * un nuevo PK_ID_DETALLE.
                 */
                fila.setPkIdDetalle(null);

                /*
                 * Todos los registros del archivo pertenecen
                 * al proceso actual.
                 */
                fila.setFkProceso(idProceso);
            }

            List<DetallePEPsModel> guardados =
                    detallePEPsRepository.saveAll(filas);

            detallePEPsRepository.flush();

            if (guardados.size() != filas.size()) {

                throw new IllegalStateException(
                    "La cantidad guardada no coincide con la cantidad leída. "
                    + "Leídos: "
                    + filas.size()
                    + ", guardados: "
                    + guardados.size()
                );
            }

            logger.info(
                "Proceso finalizado. Registros leídos: "
                + filas.size()
                + ", registros insertados: "
                + guardados.size()
                + ", proceso: "
                + idProceso
            );

        } catch (Exception e) {

            logger.error(
                "Error en procesarCargaConUsuario: ",
                e
            );

            throw new RuntimeException(
                "No fue posible procesar el archivo PEP. "
                + "Verifica que el formato y las columnas sean correctos.",
                e
            );
        }
    }
}