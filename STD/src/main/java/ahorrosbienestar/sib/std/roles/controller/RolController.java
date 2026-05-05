package ahorrosbienestar.sib.std.roles.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import ahorrosbienestar.sib.std.opcionMenu.model.RelRolOpciones;
import ahorrosbienestar.sib.std.opcionMenu.repository.IRelRolOpcionesRepository;
import ahorrosbienestar.sib.std.opcionMenu.service.OpcionesMenuService;
import ahorrosbienestar.sib.std.roles.model.Rol;
import ahorrosbienestar.sib.std.roles.service.RolService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("rol")
public class RolController {

	private static final Logger logger = Logger.getLogger(RolController.class);
	
	private RolService rolService;
	
	@Autowired
	private CoreBancarioService coreBancarioService;
	
	@Autowired
	private IRelRolOpcionesRepository iRelRolOpcionesRepository;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private OpcionesMenuService opcionesMenuService;
	

	private String estatusA = "ACTIVO";
	private String estatusI = "INACTIVO";
	private int estatus = 1; // activo
	
	private static final String nombrePantalla = "Roles";
	private static final String rutaError = "errores/error_403";
	private String rutaDireccion="";
	
	@Autowired
	public RolController (RolService rolService) {
		this.rolService = rolService;
	}
	
	private Usuario obtenerUsuarioAutenticado() {
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    if (!(principal instanceof UserDetails)) {
	        return null;
	    }

	    UserDetails userDetails = (UserDetails) principal;
	    List<Usuario> usuarios = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

	    if (usuarios == null || usuarios.isEmpty()) {
	        return null;
	    }

	    return usuarios.get(0);
	}
	
	//Inicio de roles
		@GetMapping("/index")
		public String InicioUsuario(Model model) {
			if(validarAcceso() == 1) {
				Rol rol = new Rol();
				model.addAttribute("rol", rol);
				//Mostramos todos los usuarios
				model.addAttribute("roles", rolService.findAll());
				
				model.addAttribute("listaMenu", coreBancarioService.listaMenu(2,0));
				model.addAttribute("listaOpciones", coreBancarioService.listaMenu(1,0));
				rutaDireccion = "roles/index";
			}else {
				rutaDireccion = rutaError;
			}
			
			return rutaDireccion;
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
					altaRol.setVc_nombre_rol(nombreRol);
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
		/**
		 * Relacion de Rol y opciones. 
		 */
		@PostMapping("/altaRelRolOpcion")
		public ResponseEntity<?> altaRelRolOpcion(Model model,	@RequestParam(name = "idsRolOpciones") ArrayList<Integer> idsRolOpciones,@RequestParam(name = "idRol") Integer idRol) {
			String mensaje = "";
			ArrayList<String> respuesta = new ArrayList<String>();
			
			try {
				mensaje = "Registro Correcto.";
				LocalDate fecha = LocalDate.now();
				
				Usuario usuarioAutenticado = obtenerUsuarioAutenticado();

				if (usuarioAutenticado == null) {
				    respuesta.add("Usuario no autenticado.");
				    return ResponseEntity.status(401).body(respuesta);
				}
				
				for (int idOpcion : idsRolOpciones) {
					if(iRelRolOpcionesRepository.existeOpcion(idRol,idOpcion) == 0) {
						RelRolOpciones registroRelRolOpcion = new RelRolOpciones();
						registroRelRolOpcion.setDt_fecha_registro(fecha);
						registroRelRolOpcion.setFk_opcion(idOpcion);
						registroRelRolOpcion.setFk_rol(idRol);
						registroRelRolOpcion.setI_usuarioid_registra(usuarioAutenticado.getPk_id_usuario());
						registroRelRolOpcion.setI_estatus(estatus);
						
						iRelRolOpcionesRepository.save(registroRelRolOpcion);
						
					}else {
						iRelRolOpcionesRepository.activarOpcion(idRol,idOpcion,usuarioAutenticado.getPk_id_usuario());
					}
				}
				respuesta.add(mensaje);
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				System.out.println("" + e.getMessage());
				mensaje = "Ocurrio un error. Intente mas tarde.";
				respuesta.add(mensaje);
				return ResponseEntity.ok(respuesta);
			}
		}
		@GetMapping("/consultaOpciones")
		public ResponseEntity<?> consultaDocumentos(@RequestParam(name = "idRol") Integer idRol) {
			try {
				List<RelRolOpciones> solicitudDatos = iRelRolOpcionesRepository.buscarOpciones(idRol);

				return ResponseEntity.ok(solicitudDatos);

			} catch (Exception e) {
				System.out.print("--" + e.getMessage());
			}
			return null;
		}
		
		@PostMapping("/actualizarOpciones")
		public ResponseEntity<?> actualizarOpciones(Model model, @RequestParam(name = "idRol") Integer idRol,
				@RequestParam(name = "idsOpciones") ArrayList<Integer> idsOpciones,
				@RequestParam(name = "idsAnteriores") ArrayList<Integer> idsAnteriores) {
			String mensaje = "";
			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				mensaje = "Menu Actualizado Correctamente.";
				LocalDate fecha = LocalDate.now();
				
				Usuario usuarioAutenticado = obtenerUsuarioAutenticado();

				if (usuarioAutenticado == null) {
				    respuesta.add("Usuario no autenticado.");
				    return ResponseEntity.status(401).body(respuesta);
				}
				

				// Alta de nuevos opciones.
				ArrayList<Integer> altaNewDocumentos = new ArrayList<Integer>();
				for (int element : idsOpciones) {
					if (!idsAnteriores.contains(element)) {
						altaNewDocumentos.add(element);
					}
				}

				// Actualizar estatus de opciones
				ArrayList<Integer> actualizarDocumentos = new ArrayList<Integer>();
				for (int element : idsAnteriores) {
					if (!idsOpciones.contains(element)) {
						actualizarDocumentos.add(element);
					}
				}

				if (altaNewDocumentos.size() > 0) {
					for (int idOpcion : altaNewDocumentos) {

						if(iRelRolOpcionesRepository.existeOpcion(idRol,idOpcion) == 0) {
							RelRolOpciones registroRelRolOpcion = new RelRolOpciones();
							registroRelRolOpcion.setDt_fecha_registro(fecha);
							registroRelRolOpcion.setFk_opcion(idOpcion);
							registroRelRolOpcion.setFk_rol(idRol);
							registroRelRolOpcion.setI_usuarioid_registra(usuarioAutenticado.getPk_id_usuario());
							registroRelRolOpcion.setI_estatus(estatus);
							
							iRelRolOpcionesRepository.save(registroRelRolOpcion);
							
						}else {
							iRelRolOpcionesRepository.activarOpcion(idRol,idOpcion,usuarioAutenticado.getPk_id_usuario());
						}
					}
				}

				if (actualizarDocumentos.size() > 0) {
					for (int idOpcion : actualizarDocumentos) {
						if (iRelRolOpcionesRepository.existeOpcion(idRol,idOpcion) != 0) {
							iRelRolOpcionesRepository.inactivarRelacion(idRol, idOpcion,usuarioAutenticado.getPk_id_usuario());
						}
					}
				}

				respuesta.add(mensaje);
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				System.out.println("" + e.getMessage());
				mensaje = "Ocurrio un error. Intente mas tarde.";
				respuesta.add(mensaje);
				return ResponseEntity.ok(respuesta);
			}
		}
		
		@PostMapping("/bajaOpciones")
		public ResponseEntity<?> bajaOpciones(Model model,  @RequestParam(name = "idRol") Integer idRol,
				@RequestParam(name = "idsAnteriores") ArrayList<Integer> idsAnteriores) {
			String mensaje = "";
			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				mensaje = "Menu Actualizado Correctamente.";
				Usuario usuarioAutenticado = obtenerUsuarioAutenticado();

				if (usuarioAutenticado == null) {
				    respuesta.add("Usuario no autenticado.");
				    return ResponseEntity.status(401).body(respuesta);
				}
				
				for (int idDocumento : idsAnteriores) {
					iRelRolOpcionesRepository.inactivarRelacion(idRol, idDocumento,usuarioAutenticado.getPk_id_usuario());
				}
				respuesta.add(mensaje);
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				System.out.println("" + e.getMessage());
				mensaje = "Ocurrio un error. Intente mas tarde.";
				respuesta.add(mensaje);
				return ResponseEntity.ok(respuesta);
			}
		}
		
		public int validarAcceso() {
		    try {
		        Usuario usuarioAutenticado = obtenerUsuarioAutenticado();

		        if (usuarioAutenticado == null) {
		            return 0;
		        }

		        return iRelRolOpcionesRepository.validarAcceso(
		                usuarioAutenticado.getRol_idRol().getPk_id_rol(),
		                opcionesMenuService.buscarRelacion(nombrePantalla)
		        );

		    } catch (Exception e) {
		        logger.error("Error al validar acceso.", e);
		        return 0;
		    }
		}
}
