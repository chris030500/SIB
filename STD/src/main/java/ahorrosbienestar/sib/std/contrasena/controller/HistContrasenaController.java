package ahorrosbienestar.sib.std.contrasena.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;
import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaService;


@Controller
@RequestMapping("hist_contrasena")
public class HistContrasenaController {
	
private HistContrasenaService histContrasenaService;
	
	@Autowired
	public HistContrasenaController(HistContrasenaService histContrasenaService) {
		this.histContrasenaService = histContrasenaService;
	}
		
	
	//Dar de alta usuario utilizando metodo POST
	@PostMapping
	public ResponseEntity<?> save(@RequestBody HistContrasena histContrasena){
		HistContrasena contrasena = new HistContrasena();
		contrasena = histContrasenaService.save(histContrasena);
		return ResponseEntity.ok(contrasena);
	}
	
	@PutMapping
	public ResponseEntity<?> edit(@RequestBody @Valid HistContrasena histContrasena,
									@PathVariable(name = "id") Integer id){
		//Creamos Usuario
		HistContrasena contrasena = new HistContrasena();
		//Buscamos el Usuario 
		contrasena = histContrasenaService.findById(id).get();
		
		if(contrasena != null) {
			return ResponseEntity.ok(histContrasenaService.save(histContrasena));
		}
		return ResponseEntity.notFound().build();
		
	}
	
	//Metodo para obtener listado de todos los usuarios
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(histContrasenaService.findAll());
	}
	
	//Metodo para obtener un Usuario por Id
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id){
		return ResponseEntity.ok(histContrasenaService.findById(id));
	}

}
