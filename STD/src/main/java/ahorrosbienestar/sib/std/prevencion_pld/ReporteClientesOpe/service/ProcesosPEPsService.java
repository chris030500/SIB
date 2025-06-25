package ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.service;

import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.model.ProcesosPEPsModel;
import ahorrosbienestar.sib.std.prevencion_pld.ReporteClientesOpe.repository.ProcesosPEPsRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProcesosPEPsService {
	@Autowired
    private ProcesosPEPsRepository procesosPEPsRepository;

    @Transactional
    public ProcesosPEPsModel registrarProceso(int idUsuario) {
        // Desactiva el proceso anterior (estatus = 1)
        procesosPEPsRepository.desactivarProcesosActivos();

        //  Crea un nuevo proceso con estatus = 1
        ProcesosPEPsModel proceso = new ProcesosPEPsModel();
        proceso.setI_usuario_reg(idUsuario);
        proceso.setDt_fecha_registro(LocalDateTime.now());
        proceso.setI_estatus(1);

        return procesosPEPsRepository.save(proceso);
    }
}


