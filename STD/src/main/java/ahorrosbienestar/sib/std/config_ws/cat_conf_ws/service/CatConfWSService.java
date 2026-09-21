package ahorrosbienestar.sib.std.config_ws.cat_conf_ws.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import ahorrosbienestar.sib.std.config_ws.cat_conf_ws.model.CatConfWS;
import ahorrosbienestar.sib.std.config_ws.cat_conf_ws.repository.ICatConfWSRepository;
import ahorrosbienestar.sib.std.configuracion.login.controller.LoginController;
 
@Service
public class CatConfWSService {
    
    @Autowired
    private ICatConfWSRepository iCatConfWSRepository;
    
    //protected final Log logger = LogFactory.getLog(this.getClass());
    private static final Logger logger =
            LoggerFactory.getLogger(CatConfWSService.class);
    
    @Autowired
    public CatConfWSService(ICatConfWSRepository iCatConfWSRepository) {
        this.iCatConfWSRepository = iCatConfWSRepository;
    }
    
    //Metodo para dar de alta en CAT_CONF_WS
    public CatConfWS save(CatConfWS catConfWS) {
        return iCatConfWSRepository.save(catConfWS);
    }
    
    //Metodo para buscar por id en CAT_CONF_WS
    public Optional<CatConfWS> findById(Integer id){
        return iCatConfWSRepository.findById(id);
    }
    
    
 
}
