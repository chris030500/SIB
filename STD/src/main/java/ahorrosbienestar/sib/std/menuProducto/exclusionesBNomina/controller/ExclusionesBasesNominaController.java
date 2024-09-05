package ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ahorrosbienestar.sib.std.comunes.Constantes;
import ahorrosbienestar.sib.std.menuProducto.exclusionesBNomina.service.EstadosRepubService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;


@Controller
@RequestMapping("exclusiones")
public class ExclusionesBasesNominaController {
	
	private static final Logger logger = Logger.getLogger(ExclusionesBasesNominaController.class);
	
	@Autowired
	private EstadosRepubService estadosRepubService;
	
	@RequestMapping(value = { "/getEstados" }, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getEstadosRepub() {
		Map<String, Object> res = new HashMap<>();
		String mensajeError = null;
		
		List<Map<String, Object>> estados = estadosRepubService.getAllEstadosRepub();
		
		try {
			if (estados != null && !estados.isEmpty()) {
				res.put("estados", estados);
				return ResponseEntity.ok(res);
			} else {
				res.put("estados", Collections.emptyList());
				return ResponseEntity.ok(res);
			}
		} catch (Exception e) {
			logger.error(Constantes.MENSAJE_DE_ERROR + e.getMessage());
			mensajeError = Constantes.MENSAJE_DE_ERROR + e.getMessage();
			res.put(Constantes.MENSAJE_ERROR, mensajeError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
		}
	}

}
