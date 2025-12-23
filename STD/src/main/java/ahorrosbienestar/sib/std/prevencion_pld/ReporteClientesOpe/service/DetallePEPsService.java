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

            AtomicInteger insertados = new AtomicInteger(0);
            AtomicInteger actualizados = new AtomicInteger(0);

            for (DetallePEPsModel fila : filas) {

                // 🔹 BUSCAR POR I_CLIENTEID (CORRECTO)
            	List<DetallePEPsModel> existentes =
            	        detallePEPsRepository.buscarPorClienteId(
            	                fila.getI_clienteid()
            	        );

            	if (!existentes.isEmpty()) {

            	    // 👉 Tomamos el más reciente (o el primero)
            	    DetallePEPsModel existente = existentes.get(0);

            	    if (!Objects.equals(
            	            existente.getVc_nombre_cliente(),
            	            fila.getVc_nombre_cliente())) {

            	        existente.setVc_nombre_cliente(
            	                fila.getVc_nombre_cliente()
            	        );
            	        detallePEPsRepository.save(existente);
            	        actualizados.incrementAndGet();
            	    }

            	} else {
            	    fila.setFk_proceso(idProceso);
            	    detallePEPsRepository.save(fila);
            	    insertados.incrementAndGet();
            	}

            }

            logger.info(
                "Proceso finalizado → Insertados: "
                + insertados.get()
                + " | Actualizados: "
                + actualizados.get()
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
