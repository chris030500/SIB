package ahorrosbienestar.sib.std.roles.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import ahorrosbienestar.sib.std.roles.model.Rol;
import ahorrosbienestar.sib.std.roles.service.RolService;

@Controller
@RequestMapping("rol")
public class RolController {

	
	private RolService rolService;
	
	@Autowired
	private CoreBancarioService coreBancarioService;
	
	private String estatusA = "ACTIVO";
	private String estatusI = "INACTIVO";
	
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
			model.addAttribute("roles", rolService.findAll());
			
			model.addAttribute("listaMenu", coreBancarioService.listaMenu(2,0));
			model.addAttribute("listaOpciones", coreBancarioService.listaMenu(1,0));
			return "roles/index";
		}
	
	//Dar de alta un rol utilizando metodo POST
		@PostMapping("/altaRoles")
		public ResponseEntity<?> altaRoles(@RequestParam(name = "nombreRol") String nombreRol, @RequestParam(name = "descripcionRol") String descripcionRol){
			String mensaje ="Registro Correctamente.";
			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				
				if(rolService.existeRol(nombreRol) == 0) {
					LocalDate fecha = LocalDate.now();
					Rol altaRol = new Rol();
					
					altaRol.setDt_fecha_registro(fecha);
					altaRol.setVc_descripcion_rol(descripcionRol);
					altaRol.setVc_nombre_rol(descripcionRol);
					altaRol.setVc_estatus(estatusA);
					
					rolService.save(altaRol);
					respuesta.add(mensaje);
					
				}else {
					mensaje = "El Rol ya existe";
					respuesta.add(mensaje);
				}
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				System.out.println(""+e.getMessage());
			}
			return null;
		}
		//Modificar Rol de usuario. 
		@PostMapping("/modificarRoles")
		public ResponseEntity<?> modificarRoles(@RequestParam(name = "idRol") Integer idRol, @RequestParam(name = "nombreRol") String nombreRol, @RequestParam(name = "descripcionRol") String descripcionRol){
			String mensaje ="Registro Correctamente.";
			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				
				if(rolService.modificarRoles(idRol, nombreRol, descripcionRol)>0) {
					respuesta.add(mensaje);
				}
			} catch (Exception e) {
				System.out.println(""+e.getMessage());
				mensaje = e.getMessage();
				respuesta.add(mensaje);
			}
			return ResponseEntity.ok(respuesta);
		}
		
		
		@PostMapping("/updateRoles")
		public ResponseEntity<?> updateRoles(@RequestParam(name = "idRol") Integer idRol, @RequestParam(name = "estatus") String estatus){
			String mensaje ="Actualizo estatus correctamente.";
			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				
				if(rolService.updateEstatus(idRol, estatus.equalsIgnoreCase(estatusA)?estatusI:estatusA) > 0) {
					respuesta.add(mensaje);
				}			
				
			} catch (Exception e) {
				System.out.println(""+e.getMessage());
				mensaje = e.getMessage();
				respuesta.add(mensaje);
			}
			return ResponseEntity.ok(respuesta);
		}
		
		@ResponseBody
		@RequestMapping(value = "/cargarRol/{id}", method = RequestMethod.GET)
		public Optional<Rol> loadEditarProducto(@PathVariable("id") Integer id) {
	
			return rolService.findById(id);
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
