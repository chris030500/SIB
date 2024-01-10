package ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.model.ReglasAsignacion;
import ahorrosbienestar.sib.std.menuProducto.reglasAsignacion.service.ReglasAsignacionService;

@Controller
@RequestMapping("reglas")
public class ReglasAsignacionController {

	private static final Logger logger = Logger.getLogger(ReglasAsignacionController.class);

	@Autowired
	private ReglasAsignacionService reglasAsignacionService;
	
	@GetMapping("/obtDatosReglasAsignacion")
	public ResponseEntity<List<ReglasAsignacion>> obtDatosReglasAsignacion(Model model) {
		 logger.info("/obtDatosReglasAsignacion");
		try {
			return ResponseEntity.ok(reglasAsignacionService.obtReglasAsignacion());
		} catch (Exception e) {
			logger.error("--" + e.getMessage());
			return null;
		}
	}
}
