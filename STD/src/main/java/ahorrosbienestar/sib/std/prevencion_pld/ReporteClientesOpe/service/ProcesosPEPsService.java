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
            // Desactiva el proceso anterior (estatus = 1)
            procesosPEPsRepository.desactivarProcesosActivos();
     
            //  Crea un nuevo proceso con estatus = 1
            ProcesosPEPsModel proceso = new ProcesosPEPsModel();
            proceso.setI_usuario_reg(idUsuario);
            proceso.setDt_fecha_registro(LocalDateTime.now());
            proceso.setI_estatus(1);
     
            return procesosPEPsRepository.save(proceso);
    	}catch (Exception e) {
    		logger.error("Error:"+e.getMessage());
			return null;
		}
      
    }
}
