package ahorrosbienestar.sib.std.config_ws.bitacora_consumo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import ahorrosbienestar.sib.std.config_ws.bitacora_consumo.model.BitacoraConsumo;
import ahorrosbienestar.sib.std.config_ws.bitacora_consumo.service.BitacoraConsumoService;
import ahorrosbienestar.sib.std.config_ws.cat_conf_ws.model.CatConfWS;
import ahorrosbienestar.sib.std.config_ws.cat_conf_ws.service.CatConfWSService;
import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.model.WSAutenticacionUser;
import ahorrosbienestar.sib.std.config_ws.ws_autenticacion_user.service.WSAutenticacionUserService;
import ahorrosbienestar.sib.std.config_ws.ws_respuesta_autenticacion.service.WSRespuestaAutenticacionService;
import ahorrosbienestar.sib.std.solicitudes.model.SolicitudEnvio;
import ahorrosbienestar.sib.std.solicitudes.service.SolicitudEnvioService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
 
@RestController
public class BitacoraConsumoController {
 
    //protected final Log logger = LogFactory.getLog(this.getClass());
	private static final Logger logger =
	        LoggerFactory.getLogger(BitacoraConsumoController.class);

 
    @Autowired
    private UsuarioService usuarioService;
 
    @Autowired
    private WSAutenticacionUserService wsAutenticacionUserService;
 
    @Autowired
    private CatConfWSService catConfWSService;
 
    @Autowired
    private SolicitudEnvioService solicitudEnvioService;
 
    @Autowired
    private WSRespuestaAutenticacionService wsRespuestaAutenticacionService;
 
    @Autowired
    private BitacoraConsumoService bitacoraConsumoService;
 
    @PostMapping("/bitacoraConsumo")
    public ResponseEntity<?> guardarBitacora(
                @RequestParam(name="autenUser") Integer autenUser,
                @RequestParam(name="resAuten") Integer resAuten,
                @RequestParam(name="tpProceso") String tpProceso,
                @RequestParam(name="soliEnvio") Integer soliEnvio
            ) {
 
        logger.info("Ejecutando guardarBitacora");
 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Obtenemos el nombre del usuario autentificado
        Usuario usu = usuarioService.findByNombre(auth.getName());
        // Obtenemos la fecha actual
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Obtenemos la hora actual
        DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        String hora = dateFormat2.format(new Date());
 
        // Obtenemos la fecha en String
        String fechaActual = dateFormat.format(Calendar.getInstance().getTime());
        // Obtenemos la fecha actual con el formato yyyy-MM-dd
        LocalDate localDateHoy = LocalDate.parse(fechaActual);
        
        //Buscamos wsAutenticacionUser
        WSAutenticacionUser wsAutenticacionUser = wsAutenticacionUserService.findeById(autenUser).get();
        
        //Buscamos la solicitud envio
        SolicitudEnvio solicitudEnvio = solicitudEnvioService.findById(soliEnvio).get();
        
        //Buscamos el jsonEnvio de WSAutenticacionUser
        String jsonEnvio = wsAutenticacionUserService.findeById(autenUser).get().getVc_json_envio();
        
        //Buscamos wsRespuestaAutenticacion
        CatConfWS catConfWS  = wsAutenticacionUser.getFk_id_conf_ws();
        
        //Buscamos el jsonRespuesta de WSRespuestaAutenticacion
        String jsonRespuesta = wsRespuestaAutenticacionService.findById(resAuten).get().getVc_json_respuesta();
        
        BitacoraConsumo bConsumo = new BitacoraConsumo();
        
        bConsumo.setDt_fecha_solicitud(localDateHoy);
        bConsumo.setVc_hora_solicitud(hora);
        bConsumo.setVc_json_envio(jsonEnvio);
        bConsumo.setVc_json_respuesta(jsonRespuesta);
        bConsumo.setVc_resultado_consumo(jsonRespuesta);
        bConsumo.setVc_tp_proceso_origen(tpProceso);
        bConsumo.setFk_id_autenticacion_usr(wsAutenticacionUser);
        bConsumo.setFk_id_conf_ws(catConfWS);
        bConsumo.setFk_id_solicitud_envio(solicitudEnvio);
        bConsumo.setFk_usr_solicita(usu);
        
        
 
        //return ResponseEntity.ok("ok");
        return ResponseEntity.ok(bitacoraConsumoService.save(bConsumo));
    }
 
}
