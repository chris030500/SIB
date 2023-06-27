package ahorrosbienestar.sib.std.configuracion.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ahorrosbienestar.sib.std.configuracion.login.controller.LoginController;
import ahorrosbienestar.sib.std.configuracion.service.ConfigService;

@Controller
public class ConfiguracionMensajeController {

	@Autowired
	private ConfigService configService;
	
	//protected final Log logger = LogFactory.getLog(this.getClass());
	
	private static final Logger logger = Logger.getLogger(ConfiguracionMensajeController.class);
	
	//Mostramos el mensaje que valida las fechas
	@GetMapping("/mensajeValidaFecha")
	public ResponseEntity<?> mensajeValidaFecha() throws IOException {
		logger.info("Ejecutando mensajeValidaFecha");

		//Obtenemos el mensaje
		String mensaje = configService.obtenerConfiguracion(53).getVc_valor_propiedad();

		return ResponseEntity.ok(mensaje);
	}
}
