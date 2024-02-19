package ahorrosbienestar.sib.std.menuProducto.administracionSMS.controller;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.menuProducto.administracionSMS.model.ProcesoAsignacion;
import ahorrosbienestar.sib.std.menuProducto.administracionSMS.model.adminSMS;
import ahorrosbienestar.sib.std.menuProducto.administracionSMS.service.ProcesoAsignacionService;
import ahorrosbienestar.sib.std.menuProducto.administracionSMS.service.adminSMSService;

@Controller
@RequestMapping("mensajes")
public class AdminSMSController {

	@Autowired
	private adminSMSService mensajesService;

	@Autowired
	private ProcesoAsignacionService procesoService;

	private static final Logger logger = Logger.getLogger(AdminSMSController.class);

	@GetMapping("/obtSMS")
	public ResponseEntity<?> obtClasificacion() {
		try {
			logger.info("/obtSMS");
			List<adminSMS> mensajes = mensajesService.findAll();
			return ResponseEntity.ok((mensajes != null) ? mensajes : 0);
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return null;
		}

	}

	@GetMapping("/obtProcesos")
	public ResponseEntity<?> obtProcesos() {
		try {
			logger.info("/obtProcesos");
			List<ProcesoAsignacion> procesos = procesoService.procesosId();
			return ResponseEntity.ok((procesos != null) ? procesos : Collections.emptyList());
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
		}

	}
}
