package ahorrosbienestar.sib.std.contrasena.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ahorrosbienestar.sib.std.contrasena.model.HistContrasena;
import ahorrosbienestar.sib.std.contrasena.service.HistContrasenaService;


@RestController
@RequestMapping("hist_contrasena")
public class HistContrasenaController {
	
private HistContrasenaService histContrasenaService;
	
	@Autowired
	public HistContrasenaController(HistContrasenaService histContrasenaService) {
		this.histContrasenaService = histContrasenaService;
	}
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
		
	
	//Dar de alta usuario utilizando metodo POST
	@PostMapping
	public ResponseEntity<?> save(@RequestBody HistContrasena histContrasena,@RequestParam("contra")  String contra) throws IOException{
		HistContrasena contrasena = new HistContrasena();
		System.out.println("Ejecutando guardar historico");
//		Properties properties = new Properties();
//		InputStream inputStream = new FileInputStream("src/main/resources/contrasena.properties");
//		properties.load(inputStream);
//		String contrasenaAnterior = properties.getProperty("contrasena");
		
		histContrasena.setVc_contrasenia(passwordEncoder.encode(contra));
		
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
