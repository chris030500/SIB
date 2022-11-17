package ahorrosbienestar.sib.std.usuarios.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;

@RestController
@RequestMapping("usuario")
public class UsuarioRestController {

	private UsuarioService usuarioService;

	@Autowired
	public UsuarioRestController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	// Dar de alta usuario utilizando metodo POST
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Usuario usuario) {
		Usuario usu = new Usuario();
		usu = usuarioService.save(usuario);
		return ResponseEntity.ok(usu);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@RequestBody @Valid Usuario usuario, @PathVariable(name = "id") Integer id) {
		// Creamos Usuario
		Usuario u = new Usuario();
		// Buscamos el Usuario
		u = usuarioService.findById(id).get();

		if (u != null) {
			return ResponseEntity.ok(usuarioService.save(usuario));
		}
		return ResponseEntity.notFound().build();

	}
	
	//Metodo para desactivar al usuario (cambia estatus de sesion)
	@GetMapping("/activarUsuario/{id}")
	public ResponseEntity<?> activarUsuarioSesion(@PathVariable("id") Integer id) throws IOException{
		return ResponseEntity.ok(usuarioService.activarUsuarioSesion(id));			
	}

	// Metodo para obtener listado de todos los usuarios
	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(usuarioService.findAll());
	}

	// Metodo para obtener listado de todos los usuarios
	@GetMapping("/{nombreUsuario}")
	public ResponseEntity<?> findByNombreUsuario(@PathVariable (name ="nombreUsuario") String nombreUsuario) {
		return ResponseEntity.ok(usuarioService.findByNombreUsuario(nombreUsuario));
	}
	


	// Metodo para obtener un Usuario por Id
//	@GetMapping("/{id}")
//	public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id) {
//		return ResponseEntity.ok(usuarioService.findById(id));
//	}

}
