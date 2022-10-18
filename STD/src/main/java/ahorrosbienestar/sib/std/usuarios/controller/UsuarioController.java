package ahorrosbienestar.sib.std.usuarios.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

	private UsuarioService usuarioService;
	
	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	//Dar de alta usuario utilizando metodo POST
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Usuario usuario){
		Usuario usu = new Usuario();
		usu = usuarioService.save(usuario);
		return ResponseEntity.ok(usu);
	}
	
	@PutMapping
	public ResponseEntity<?> edit(@RequestBody @Valid Usuario usuario,
									@PathVariable(name = "id") Integer id){
		//Creamos Usuario
		Usuario u = new Usuario();
		//Buscamos el Usuario 
		u = usuarioService.findById(id).get();
		
		if(u != null) {
			return ResponseEntity.ok(usuarioService.save(usuario));
		}
		return ResponseEntity.notFound().build();
		
	}
	
	//Metodo para obtener listado de todos los usuarios
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(usuarioService.findAll());
	}
	
	//Metodo para obtener un Usuario por Id
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id){
		return ResponseEntity.ok(usuarioService.findById(id));
	}
	
}
