package ahorrosbienestar.sib.std.menuProducto.administracionSMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.administracionSMS.model.ProcesoSMS;
import ahorrosbienestar.sib.std.menuProducto.administracionSMS.repository.IProcesoSMS;

@Service
public class ProcesoSMSService {
	
	@Autowired
    private IProcesoSMS iProcesoSMS;


    public ProcesoSMS saveProce() {
    	
    	ProcesoSMS proces = new ProcesoSMS();
        // Configura otros campos del procesoSMS según sea necesario
        ProcesoSMS savedProcesoSMS = iProcesoSMS.save(proces);
        // El ID autogenerado estará disponible en savedProcesoSMS.getId()
        return savedProcesoSMS;

    }
    
    public ProcesoSMS actualizarMensaje(int id) {
    	ProcesoSMS proceso = iProcesoSMS.findById(id)
                .orElseThrow(() -> new RuntimeException("Proceso no encontrado con id: " + id));

        proceso.setEstatus(1);

        return iProcesoSMS.save(proceso);
    }

}
