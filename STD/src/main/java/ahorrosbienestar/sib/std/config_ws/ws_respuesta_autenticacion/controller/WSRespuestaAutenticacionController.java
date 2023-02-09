package ahorrosbienestar.sib.std.config_ws.ws_respuesta_autenticacion.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.model.WSAutenticacionUser;
import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.service.WSAutenticacionUserService;
import ahorrosbienestar.sib.std.config_ws.ws_respuesta_autenticacion.model.WSRespuestaAutenticacion;
import ahorrosbienestar.sib.std.config_ws.ws_respuesta_autenticacion.service.WSRespuestaAutenticacionService;
 
 
@Controller
public class WSRespuestaAutenticacionController {
 
    protected final Log logger = LogFactory.getLog(this.getClass());
 
    
    @Autowired
    private WSAutenticacionUserService wsAutenticacionUserService;
    
    @Autowired
    private WSRespuestaAutenticacionService wsRespuestaAutenticacionService;
 
    @PostMapping("/wsResAutenticacionUser/guardarRespAutenticacion")
    public ResponseEntity<?> guardarRespAutenticacion( 
            @RequestParam(name = "jsonRespuesta", required = false) String jsonRespuesta,
            @RequestParam(name = "jti", required = false) String jti,
            @RequestParam(name = "scope", required = false) String scope,
            @RequestParam(name = "token", required = false) String token,
            @RequestParam(name = "idWSAutenticacion") Integer idWSAutenticacion,
            @RequestParam(name = "requisitionId", required = false) String requisitionId) {
 
        //System.out.println("JSONRESPUESTA: " + jsonRespuesta);
        String json = "{"+jsonRespuesta+"}";
        WSAutenticacionUser wsAutenticacionUser = wsAutenticacionUserService.findeById(idWSAutenticacion).get();
        
        WSRespuestaAutenticacion wsRespuestaAutenticacion = new WSRespuestaAutenticacion();
        wsRespuestaAutenticacion.setVc_json_respuesta(json);
        wsRespuestaAutenticacion.setVc_token(token);
        wsRespuestaAutenticacion.setVc_scope(scope);
        wsRespuestaAutenticacion.setVc_jti(jti);
        wsRespuestaAutenticacion.setVc_requisition_id(requisitionId);
        wsRespuestaAutenticacion.setFk_id_autenticacion_user(wsAutenticacionUser);
 
        
        System.out.println("JTI: " + jti);
        System.out.println("SCOPE: " + scope);
        System.out.println("token: " + token);
        System.out.println("AUTENTICACION USER: " + wsAutenticacionUser.getPk_id_ws_autenticacion());
        //return ResponseEntity.ok("OK");
        return ResponseEntity.ok(wsRespuestaAutenticacionService.save(wsRespuestaAutenticacion));
    }
 
}