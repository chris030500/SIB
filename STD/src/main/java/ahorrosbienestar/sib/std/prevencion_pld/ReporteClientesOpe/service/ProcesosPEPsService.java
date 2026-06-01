//ProcesosPEPsService
package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;
 
import java.time.LocalDateTime;
 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesosPEPsModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ProcesosPEPsRepository;
import lombok.RequiredArgsConstructor;
 
@Service
@RequiredArgsConstructor
public class ProcesosPEPsService {
     private static final Logger logger = Logger.getLogger(ProcesosPEPsService.class);
    @Autowired
    private ProcesosPEPsRepository procesosPEPsRepository;
 
    @Transactional
    public ProcesosPEPsModel registrarProceso(int idUsuario) {
        try {
            logger.info("Desactivando procesos activos...");
            procesosPEPsRepository.desactivarProcesosActivos();
            logger.info("Procesos desactivados correctamente.");

            logger.info("Creando nuevo proceso...");
            ProcesosPEPsModel proceso = new ProcesosPEPsModel();
            proceso.setI_usuario_reg(idUsuario);
            proceso.setDt_fecha_registro(LocalDateTime.now());
            proceso.setI_estatus(1);

            logger.info("Guardando proceso en base de datos...");
            ProcesosPEPsModel saved = procesosPEPsRepository.save(proceso);
            logger.info("Proceso guardado con ID: " + saved.getPk_id_peps());

            return saved;
        } catch (Exception e) {
            logger.error("Error registrando proceso: ", e);
            return null;
        }
    }

}


