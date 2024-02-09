package ahorrosbienestar.sib.std.menuProducto.administracionSMS.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping(value = {"/obtSMS"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> parametrosDelSeguimiento(@RequestParam("cliente") String cliente,
	        @RequestParam("sucur") String sucur, @RequestParam("proce") String proce) {

	    System.out.println(cliente);
	    System.out.println(sucur);
	    System.out.println(proce);

	    Map<String, Object> respuesta = new HashMap<>();
	    String mensajeError = null;
	    try {
	        List<adminSMS> mensajes = mensajesService.busquedaPorSeguimiento(cliente, sucur, proce);
	        
	        // Verificar si la lista de mensajes es nula o vacía
	        if (mensajes != null && !mensajes.isEmpty()) {
	            respuesta.put("lista", mensajes);
	            return ResponseEntity.ok(respuesta);
	        } else {
	            // Devolver una lista vacía si no hay mensajes encontrados
	            respuesta.put("lista", Collections.emptyList());
	            return ResponseEntity.ok(respuesta);
	        }
	    } catch (Exception e) {
	        // TODO: handle exception
	        logger.error("Error: " + e.getMessage());
	        mensajeError = "Error: " + e.getMessage();
	        respuesta.put("error", mensajeError);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
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
