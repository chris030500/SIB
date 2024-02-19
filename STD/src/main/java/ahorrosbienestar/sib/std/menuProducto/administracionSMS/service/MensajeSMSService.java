package ahorrosbienestar.sib.std.menuProducto.administracionSMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.menuProducto.administracionSMS.model.MensajeSMS;
import ahorrosbienestar.sib.std.menuProducto.administracionSMS.repository.IMensajeSMSRepository;

@Service
public class MensajeSMSService {
	private final IMensajeSMSRepository mensajeSMSRepository;

    @Autowired
    public MensajeSMSService(IMensajeSMSRepository mensajeSMSRepository) {
        this.mensajeSMSRepository = mensajeSMSRepository;
    }
    
    public MensajeSMS editarMensaje(int id, String nuevoMensaje) {
        MensajeSMS mensajeExistente = mensajeSMSRepository.findById(id).orElse(null);
        if (mensajeExistente != null) {
            mensajeExistente.setDescripcionMensaje(nuevoMensaje); // Actualizar el campo de mensaje
            return mensajeSMSRepository.save(mensajeExistente); // Guardar el mensaje actualizado en la base de datos
        }
        return null; // Devolver null si no se encuentra el mensaje con el ID dado
    }
    
    public MensajeSMS inactivarMensaje(int id, int estatus) {
        MensajeSMS mensajeExistente = mensajeSMSRepository.findById(id).orElse(null);
        if (mensajeExistente != null) {
            mensajeExistente.setEstatus(estatus); // Actualizar el campo de mensaje
            return mensajeSMSRepository.save(mensajeExistente); // Guardar el mensaje actualizado en la base de datos
        }
        return null; // Devolver null si no se encuentra el mensaje con el ID dado
    }
}
