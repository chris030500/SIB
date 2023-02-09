package ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.model.WSAutenticacionUser;
import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.repository.IWSAutenticacionUserRepository;
 
@Service
public class WSAutenticacionUserService {
 
    @Autowired
    private IWSAutenticacionUserRepository iwsAutenticacionUserRepository;
    
    @Autowired
    public WSAutenticacionUserService(IWSAutenticacionUserRepository iwsAutenticacionUserRepository) {
        this.iwsAutenticacionUserRepository = iwsAutenticacionUserRepository;
    }
    
    //Metodo para guardar en TBL_WS_AUTENTICACION_USER
    public WSAutenticacionUser save(WSAutenticacionUser wsAutenticacionUser) {
        return iwsAutenticacionUserRepository.save(wsAutenticacionUser);
    }
    
    //Metodo para buscar por id en TBL_WS_AUTENTICACION_USER
    public Optional<WSAutenticacionUser> findeById(Integer id){
        return iwsAutenticacionUserRepository.findById(id);
    }
    
}