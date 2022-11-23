package ahorrosbienestar.sib.std.roles.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ahorrosbienestar.sib.std.roles.model.Rol;
import ahorrosbienestar.sib.std.roles.service.RolService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Controller
@RequestMapping("rol")
public class RolController {

	private RolService rolService;
	
	@Autowired
	public RolController (RolService rolService) {
		this.rolService = rolService;
	}
	
	//Inicio de roles
		@GetMapping("/index")
		public String InicioUsuario(Model model) {
			Rol rol = new Rol();
			model.addAttribute("rol", rol);
			//Mostramos todos los usuarios
			model.addAttribute("rol", rolService.findAll());
			return "roles/index";
		}
	
	//Dar de alta un rol utilizando metodo POST
		@PostMapping
		public ResponseEntity<?> save(@RequestBody Rol rol){
			Rol r = new Rol();
			r = rolService.save(rol);
			return ResponseEntity.ok(r);
		}
		
		@PutMapping
		public ResponseEntity<?> edit(@RequestBody @Valid Rol rol,
										@PathVariable(name = "id") Integer id){
			//Creamos Rol
			Rol r = new Rol();
			//Buscamos el rol 
			r = rolService.findById(id).get();
			
			if(r != null) {
				return ResponseEntity.ok(rolService.save(rol));
			}
			return ResponseEntity.notFound().build();
			
		}
		
		//Metodo para obtener listado de todos los roles
		@GetMapping("/roles")
		public ResponseEntity<?> findAll(){
			return ResponseEntity.ok(rolService.findAll());
		}
		
		//Metodo para obtener un rol por Id
		@GetMapping("/{id}")
		public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id){
			return ResponseEntity.ok(rolService.findById(id));
		}
}
