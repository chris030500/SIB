package ahorrosbienestar.sib.std.usuarios.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ahorrosbienestar.sib.std.roles.model.Rol;
import ahorrosbienestar.sib.std.roles.service.RolService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.service.UsuarioService;
import groovyjarjarantlr4.v4.parse.ANTLRParser.sync_return;

@RestController
@RequestMapping("usuario")
public class UsuarioRestController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	public UsuarioRestController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@Autowired
	private RolService rolService;

	// Dar de alta usuario utilizando metodo POST
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Usuario usuario) {
		Usuario usu = new Usuario();
		usu = usuarioService.save(usuario);
		return ResponseEntity.ok(usu);
	}

//	@PutMapping("/{id}")
//	public ResponseEntity<?> edit(@RequestBody @Valid Usuario usuario, @PathVariable(name = "id") Integer id) {
//		// Creamos Usuario
//		Usuario u = new Usuario();
//		// Buscamos el Usuario
//		u = usuarioService.findById(id).get();
//
//		if (u != null) {
//			return ResponseEntity.ok(usuarioService.save(usuario));
//		}
//		return ResponseEntity.notFound().build();
//
//	}
	
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
	
	//Buscar usuario por nombre y trae toda su información
	@GetMapping("/nombreUsuario/{nombre}")
	public ResponseEntity<?> findByUsuarioNombre(@PathVariable (name ="nombre") String nombre) {
		return ResponseEntity.ok(usuarioService.findByUsuarioNombre(nombre));
	}
	
	//Buscar usuario por nombre (true = existe, false = no existe)
	@GetMapping("/nombre/{nombreUsuario}")
	public boolean findByNombreUsuarioBoolean(@PathVariable ("nombreUsuario") String nombreUsuario) {
		Usuario u = new Usuario();
		u = usuarioService.findByNombreUsuarioBoolean(nombreUsuario);
		if(u != null) {
			return true;
		} else if(u == null) {
			return false;
		}
		return false;
		
	}
	
	@GetMapping("/idEmpleado/{idEmpleado}")
	public boolean findByIdEmpleadoBoolean(@PathVariable ("idEmpleado") Integer idEmpleado) {
		Usuario u = new Usuario();
		u = usuarioService.findByIdEmpleadoBoolean(idEmpleado);
		if(u != null) {
			return true;
		} else if(u == null) {
			return false;
		}
		return false;
		
	}
	
	//Guardar Usuario
	@RequestMapping(value={"/guardar_usuario"}, method = RequestMethod.POST)
	public ResponseEntity<?> guardarUsuario( 
			@RequestParam("EmpleadoId") Integer EmpleadoId, 
			@RequestParam("nombreUsuario") String nombreUsuario,
			@RequestParam("roles") int rol) throws IOException  {
		InputStream inputStream = new FileInputStream("src/main/resources/confSesion.properties");
		
		Properties properties = new Properties();
		properties.load(inputStream);
		Usuario usuario = new Usuario();
		usuario.setI_estatus_usuario(1);
		usuario.setI_estatus_sesion(0);
		usuario.setI_estatus_cuenta(1);
		usuario.setVc_nombre_usuario(nombreUsuario);
		usuario.setI_intentos_fallidos(Integer.parseInt(properties.getProperty("intentos")));
		Rol r = rolService.findById(rol).get();
		usuario.setRol_idRol(r);
		usuario.setI_id_empleado(EmpleadoId);
		System.out.println("Datos usuario: " );
		System.out.println("Estatus cuenta: " +usuario.getI_estatus_cuenta());
		System.out.println("Estatus sesión: "+ usuario.getI_estatus_sesion());
		System.out.println("Estatus usuario: "+ usuario.getI_estatus_usuario());
		System.out.println("Estatus intentos fallidos: " + usuario.getI_intentos_fallidos());
		System.out.println("Usuario PK: " + usuario.getPk_id_usuario());
		System.out.println("Nombre Usuario: " + usuario.getVc_nombre_usuario());
		System.out.println("Rol: "+ usuario.getRol_idRol().getVc_nombre_rol());
		
		
		return ResponseEntity.ok(usuarioService.save(usuario));
	}
	
	@RequestMapping(value={"/editar_usuario"}, method = RequestMethod.POST)
	public ResponseEntity<?> cambiarNombreUsuario(@RequestParam("usuario") String usuario, @RequestParam("idUsuario") Integer idUsuario){
		System.out.println("Usuario: " + usuario);
		System.out.println("IdUsuario: " +idUsuario);
		if(usuario != null && idUsuario != null) {
			System.out.println("Puede realizar el cambio de usuario");
			return ResponseEntity.ok(usuarioService.cambiarNombreUsuario(usuario, idUsuario));
		}
		System.out.println("No puede realizar el cambio de usuario");
		return ResponseEntity.notFound().build();
		
	}
	
	@RequestMapping(value={"/baja_usuario"}, method = RequestMethod.POST)
	public ResponseEntity<?> bajaUsuario(@RequestParam("usuario") Integer usuario){
		System.out.println("Usuario: " + usuario);
		return ResponseEntity.ok(usuarioService.bajaUsuarioEstatus(usuario)); 
	}
	
	@RequestMapping(value={"/reactivar_usuario"}, method = RequestMethod.POST)
	public ResponseEntity<?> reactivarUsuario(@RequestParam("usuario") Integer usuario){
		System.out.println("Usuario: " + usuario);
		return ResponseEntity.ok(usuarioService.activarUsuarioEstatus(usuario)); 
	}
	
	

}
