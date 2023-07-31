package ahorrosbienestar.sib.std.empleados.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ahorrosbienestar.sib.std.empleados.service.EmpleadoService;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService empleadoService;
	
	//protected final Log logger = LogFactory.getLog(this.getClass());
	
	private static final Logger logger = Logger.getLogger(EmpleadoController.class);
	
	@GetMapping("listado")
	public ResponseEntity<?> buscarNombreEmpleado(@RequestParam(name = "nombre") String nombre){
		logger.info("Ejecutando listado de empleados");
		return ResponseEntity.ok(empleadoService.obtenerEmpleado(nombre));
	}
}