package ahorrosbienestar.sib.std.menuProducto.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.correoSIB.service.EnvioCorreoService;
import ahorrosbienestar.sib.std.menuProducto.administracionSMS.service.MensajeSMSService;

@RestController
@RequestMapping("/api/respuestaSMS")
public class apiController {
	
	private static final Logger logger = Logger.getLogger(apiController.class);

    @Autowired
    private MensajeSMSService mensajeService;
    
    @Autowired
	private EnvioCorreoService envioCorreoService;

    @PostMapping
    public ResponseEntity<String> recibirRespuesta(@RequestBody apiModel response) {
        

        try {
            int extraParam = Integer.parseInt(response.getExtraParam());

            if(response.isSuccess()) {
                mensajeService.actualizarEstadoMensajesRecibidos(extraParam, 3);
                envioCorreoService.prepararCorreo(12, response.getExtraParam());
            } else {
                mensajeService.actualizarEstadoMensajesRecibidos(extraParam, 0);
                envioCorreoService.prepararCorreo(13, response.getExtraParam());
            }
        } catch (NumberFormatException e) {
            logger.error("El identificador recibido no tiene un formato válido.", e);
        } catch (Exception e) {
            logger.error("Error al actualizar el estado de los mensajes recibidos", e);
        }

        return new ResponseEntity<>("Respuesta recibida exitosamente", HttpStatus.OK);
    }
}

