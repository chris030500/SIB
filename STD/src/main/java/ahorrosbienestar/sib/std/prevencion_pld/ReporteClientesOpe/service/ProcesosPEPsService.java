package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;
 
import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesosPEPsModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ProcesosPEPsRepository;
import lombok.RequiredArgsConstructor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.time.LocalDateTime;

 
@Service
@RequiredArgsConstructor
public class ProcesosPEPsService {

	
	private static final Logger logger = Logger.getLogger(ProcesosPEPsService.class);

    @Autowired
    private ProcesosPEPsRepository procesosPEPsRepository;
 
    @Transactional
    public ProcesosPEPsModel registrarProceso(int idUsuario) {
        logger.info(">>> Iniciando registrarProceso para usuario: " + idUsuario);

        try {
            logger.info(">>> Desactivando procesos activos...");
            procesosPEPsRepository.desactivarProcesosActivos();
            logger.info(">>> Procesos activos desactivados correctamente.");
        } catch (Exception e) {
            logger.error(">>> Error al desactivar procesos activos", e);
            throw new RuntimeException("Error al desactivar procesos activos", e);
        }

        try {
            ProcesosPEPsModel proceso = new ProcesosPEPsModel();
            proceso.setI_usuario_reg(idUsuario);
            proceso.setDt_fecha_registro(LocalDateTime.now());
            proceso.setI_estatus(1);

            ProcesosPEPsModel guardado = procesosPEPsRepository.save(proceso);
            logger.info(">>> Proceso registrado con ID: " + guardado.getPk_id_peps());
            return guardado;

        } catch (Exception e) {
            logger.error(">>> Error al registrar nuevo proceso", e);
            throw new RuntimeException("Error al registrar proceso", e);
        }
    }
    }


