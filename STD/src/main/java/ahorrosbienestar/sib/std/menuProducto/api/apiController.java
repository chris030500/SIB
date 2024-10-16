package ahorrosbienestar.sib.std.menuproducto.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.correosib.service.EnvioCorreoService;
import ahorrosbienestar.sib.std.menuproducto.administracionSMS.service.MensajeSMSService;

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
        logger.debug("Respuesta recibida: " + response.getResponse());
        logger.debug("Parametro adicional: " + response.getExtraParam());
        logger.debug("Éxito de la operación: " + response.isSuccess());
        
        System.out.println("Respuesta recibida: " + response.getResponse());
        System.out.println("Parametro adicional: " + response.getExtraParam());
        System.out.println("Éxito de la operación: " + response.isSuccess());

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
            logger.error("Error al convertir el parámetro adicional a entero: " + response.getExtraParam(), e);
            System.out.println("Error al convertir el parámetro adicional a entero: " + response.getExtraParam() + e);
        } catch (Exception e) {
            logger.error("Error al actualizar el estado de los mensajes recibidos", e);
            System.out.println("Error al actualizar el estado de los mensajes recibidos" + e);
        }

        return new ResponseEntity<>("Respuesta recibida exitosamente", HttpStatus.OK);
    }
}

