package ahorrosbienestar.sib.std.config_ws.conexion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
 
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;
 
@RestController
public class ConexionController {
    
    @Autowired
    private ConfigService configService;
    
    @GetMapping("conexionWS_IAD")
    public ResponseEntity<?> conexionWS(){
        
        String url = configService.findAll().get(48).getVc_valor_propiedad();
        
        return ResponseEntity.ok(url);
    }
 
}