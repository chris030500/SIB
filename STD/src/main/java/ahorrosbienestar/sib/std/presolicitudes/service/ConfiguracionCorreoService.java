package ahorrosbienestar.sib.std.presolicitudes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ahorrosbienestar.sib.std.presolicitudes.model.ConfiguracionCorreo;
import ahorrosbienestar.sib.std.presolicitudes.repository.ConfiguracionCorreoRepository;

@Service
public class ConfiguracionCorreoService {

    @Autowired
    private ConfiguracionCorreoRepository configuracionCorreoRepository;

    public void actualizarCorreo(ConfiguracionCorreo configuracionCorreo) {
    	// Obtener la configuración existente con ID = 1
        ConfiguracionCorreo configExistente = configuracionCorreoRepository.findById(1L).orElse(new ConfiguracionCorreo());
        
        // Establecer el ID a 1 para asegurar que siempre actualiza la misma fila
        configExistente.setId(1L);
        configExistente.setCorreoRemitente(configuracionCorreo.getCorreoRemitente());
        configExistente.setPassword(configuracionCorreo.getPassword());
        configExistente.setMensaje(configuracionCorreo.getMensaje());
        configExistente.setCronExpresion(configuracionCorreo.getCronExpresion());

        // Guardar la actualización en la base de datos
        configuracionCorreoRepository.save(configExistente);
    }
}
