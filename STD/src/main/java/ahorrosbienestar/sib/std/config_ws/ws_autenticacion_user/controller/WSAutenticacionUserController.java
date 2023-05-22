package ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
 
 
import org.apache.log4j.Logger;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import ahorrosbienestar.sib.std.config_ws.cat_conf_ws.model.CatConfWS;
import ahorrosbienestar.sib.std.config_ws.cat_conf_ws.service.CatConfWSService;
import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.model.WSAutenticacionUser;
import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.service.WSAutenticacionUserService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
 
@RestController
public class WSAutenticacionUserController {
 
    //protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger = Logger.getLogger(WSAutenticacionUserController.class);
 
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired 
    private CatConfWSService catConfWSService;
    
    @Autowired
    private SolicitudEnvioService solicitudEnvioService;
    
    @Autowired
    private WSAutenticacionUserService wsAutenticacionUserService;
 
    @PostMapping("/wsAutenticacionUser/guardarAutenticacion")
    public ResponseEntity<?> guardarAutenticacion(
            @RequestParam(name = "resultadoLogin", required = false) Boolean resultadoLogin,
            @RequestParam(name = "solicitudEnvio") Integer solicitudEnvio,
            @RequestParam(name = "idConfWS") Integer idConfWS,
            @RequestParam(name = "jsonEnvio") String jsonEnvio,
            @RequestParam(name = "tpProceso") String tpProceso
            ) {
 
        logger.info("Ejecutando guardarSolicitudEnvio");
        // System.out.println("Obtenemos el valor de la solicitud:" + solicitud);
        // Obtenemos el usuario autentificado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Obtenemos el nombre del usuario autentificado
        Usuario usu = usuarioService.findByNombre(auth.getName());
        // Establecemos el formato de la fecha
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        String hora = dateFormat2.format(new Date());
        //Date hora = new Date();
        
        // Obtenemos la fecha en String
        String fechaActual = dateFormat.format(Calendar.getInstance().getTime());
        // Obtenemos la fecha actual con el formato yyyy-MM-dd
        LocalDate localDateHoy = LocalDate.parse(fechaActual);
        
        //Buscamos el WS en TBL_CAT_CONF_WS
        CatConfWS confWS = catConfWSService.findById(idConfWS).get();
        
        //Buscamos el id de la solicitud de envio
        SolicitudEnvio sEnvio = solicitudEnvioService.recuperarModeloPorId(solicitudEnvio);
        
        WSAutenticacionUser wsAuteUsu = new WSAutenticacionUser();
        wsAuteUsu.setFk_usr_solicita(usu);
        wsAuteUsu.setB_resultado_login(resultadoLogin);
        wsAuteUsu.setDt_fecha_solicitud(localDateHoy);
        wsAuteUsu.setVc_hora_solicitud(hora);
        wsAuteUsu.setVc_json_envio(jsonEnvio);
        wsAuteUsu.setFk_id_conf_ws(confWS);
        wsAuteUsu.setFk_id_solicitud_envio(sEnvio);
        wsAuteUsu.setVc_tp_proceso_origen(tpProceso);
        
        //System.out.println("USUARIO: "+ usu.getPk_id_usuario());
        logger.info("USUARIO: "+ usu.getPk_id_usuario());
        //System.out.println("RESULTADO LOGIN: " + resultadoLogin);
        logger.info("RESULTADO LOGIN: " + resultadoLogin);
        //System.out.println("FECHA: " + localDateHoy);
        logger.info("FECHA: " + localDateHoy);
        //System.out.println("HORA: " +hora);
        logger.info("HORA: " +hora);
        //System.out.println("JSONENVIO: " + jsonEnvio);
        logger.info("JSONENVIO: " + jsonEnvio);
        //System.out.println("CONFWS: " + confWS.getPk_id_cat_conf_ws_seq());
        logger.info("CONFWS: " + confWS.getPk_id_cat_conf_ws_seq());
        //System.out.println("SOLICITUD ENVIO: " +sEnvio.getPk_id_solicitud_envio());
        logger.info("SOLICITUD ENVIO: " +sEnvio.getPk_id_solicitud_envio());
        
        //return ResponseEntity.ok("ok");
        return ResponseEntity.ok(wsAutenticacionUserService.save(wsAuteUsu));
    }
 
}
