package ahorrosbienestar.sib.std.config_ws.ws_respuesta_autenticacion.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import ahorrosbienestar.sib.std.config_ws.ws_respuesta_autenticacion.model.WSRespuestaAutenticacion;
import ahorrosbienestar.sib.std.config_ws.ws_respuesta_autenticacion.repository.IWSRespuestaAutenticacionRepository;
 
@Service
public class WSRespuestaAutenticacionService {
 
    @Autowired
    private IWSRespuestaAutenticacionRepository iwsRespuestaAutenticacionRepository;
    
    @Autowired
    public WSRespuestaAutenticacionService(IWSRespuestaAutenticacionRepository iwsRespuestaAutenticacionRepository) {
        this.iwsRespuestaAutenticacionRepository = iwsRespuestaAutenticacionRepository;
    }
    
    //Metodo para guardar en TBL_WS_RESPUESTA_AUTENTICACION
    public WSRespuestaAutenticacion save(WSRespuestaAutenticacion wsRespuestaAutenticacion) {
        return iwsRespuestaAutenticacionRepository.save(wsRespuestaAutenticacion);
    }
    
    //Metodo para buscar id en TBL_WS_RESPUESTA_AUTENTICACION
    public Optional<WSRespuestaAutenticacion> findById(Integer id){
        return iwsRespuestaAutenticacionRepository.findById(id);
    }
    
}