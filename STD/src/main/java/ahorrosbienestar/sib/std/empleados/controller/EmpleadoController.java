package ahorrosbienestar.sib.std.empleados.controller;

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
	
	
	@GetMapping("listado")
	public ResponseEntity<?> buscarNombreEmpleado(@RequestParam(name = "nombre") String nombre){
		return ResponseEntity.ok(empleadoService.obtenerEmpleado(nombre));
	}
}