package ahorrosbienestar.sib.std.documentos.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

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

import ahorrosbienestar.sib.std.documentos.model.Documento;
import ahorrosbienestar.sib.std.documentos.service.DocumentoService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("documento")
public class DocumentoController {

	private DocumentoService documentoService;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	private int estatus = 1;
	
	@Autowired
	public DocumentoController (DocumentoService documentoService) {
		this.documentoService = documentoService;
	}
	
	
		@GetMapping("/index")
		public String InicioUsuario(Model model) {
			Documento documento = new Documento();
			model.addAttribute("documento", documento);
			model.addAttribute("documento", documentoService.findAll());
			return "documentos/index";
		}
		
		@PostMapping("/altaDocumento/{nombreDocumento}/{firma}")
		public ResponseEntity<?> altaDocumento(Model model,@PathVariable (name ="nombreDocumento") String nombreDocumento,
				@PathVariable (name ="firma") int firma){
			String mensaje ="Documento Registrado Correctamente.";
			//System.out.println("firma: " + firma);
			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				if (!(principal instanceof UserDetails)) {
				    return ResponseEntity.status(401).body("Usuario no autenticado.");
				}

				UserDetails userDetails = (UserDetails) principal;
				List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

				if (usuario == null || usuario.isEmpty()) {
				    return ResponseEntity.status(404).body("Usuario no encontrado.");
				}
			
				if(documentoService.existeDocumento(nombreDocumento) == 0) {
					LocalDate fecha = LocalDate.now();
					Documento altaDocumento = new Documento();
					
					altaDocumento.setDt_fecha_alta(fecha);
					altaDocumento.setI_estatus(estatus);
					altaDocumento.setUsr_registra(usuario.get(0).getPk_id_usuario());
					altaDocumento.setInd_doc_cliente(estatus); //revisar valor a insertar
					altaDocumento.setVc_nombre_doc(nombreDocumento.replace("|", "/"));
					altaDocumento.setI_requiere_firma(firma);
					
					documentoService.save(altaDocumento);
					respuesta.add(mensaje);
				}else {
					mensaje = "El Documento esta registrado.";
					respuesta.add(mensaje);
				}
				
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
			    return ResponseEntity.internalServerError().body("Ocurrió un error, intente más tarde.");
			}
		}
		
		@PostMapping("/eliminar/{id_documento}")
		public ResponseEntity<?> eliminarDocumento(@PathVariable (name ="id_documento") Integer id_documento) {
			String mensaje ="Documento Actualizado";
			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				documentoService.bajaDocumento(id_documento);
				respuesta.add(mensaje);
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				System.out.print("--"+e.getMessage());
				mensaje = "Ocurrio un Error, intente mas tarde";
				respuesta.add(mensaje);
				return ResponseEntity.ok(respuesta);
			}
		}
		
		@PostMapping("/reactivar/{id_documento}")
		public ResponseEntity<?> reactivarDocumento(@PathVariable (name ="id_documento") Integer id_documento) {
			String mensaje ="Documento Actualizado";
			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				documentoService.reactivarDocumento(id_documento);
				respuesta.add(mensaje);
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				System.out.print("--"+e.getMessage());
				mensaje = "Ocurrio un Error, intente mas tarde";
				respuesta.add(mensaje);
				return ResponseEntity.ok(respuesta);
			}
		}
		
		@PostMapping("/editar/{id_documento}/{nombreDocumento}/{firma}")
		public ResponseEntity<?> editarDocumento(@PathVariable (name ="id_documento") Integer id_documento,
				@PathVariable (name ="nombreDocumento") String nombreDocumento,
				@PathVariable (name ="firma") int firma) {
			String mensaje ="Documento Actualizado";
			ArrayList<String> respuesta = new ArrayList<String>();
			try {
				
				if(documentoService.existeDocumento(nombreDocumento) == 0) {
					documentoService.editarDocumento(id_documento,nombreDocumento.replace("|", "/"), firma);
					respuesta.add(mensaje);
					
				}else {
					mensaje = "El Documento esta registrado.";
					respuesta.add(mensaje);
				}
				return ResponseEntity.ok(respuesta);
			
			} catch (Exception e) {
				System.out.print("--"+e.getMessage());
				mensaje = "Ocurrio un Error, intente mas tarde";
				respuesta.add(mensaje);
				return ResponseEntity.ok(respuesta);
			}
		}
		
		
		@PostMapping
		public ResponseEntity<?> save(@RequestBody Documento documento){
			Documento d = new Documento();
			d = documentoService.save(documento);
			return ResponseEntity.ok(d);
		}
		
		@PutMapping
		public ResponseEntity<?> edit(@RequestBody @Valid Documento documento,
										@PathVariable(name = "id") Integer id){
			
			Documento d = new Documento();
		
			d = documentoService.findById(id).get();
			
			if(d != null) {
				return ResponseEntity.ok(documentoService.save(documento));
			}
			return ResponseEntity.notFound().build();
			
		}
		
		//Metodo para obtener listado de todos los documentos
		@GetMapping("/documentos")
		public ResponseEntity<?> findAll(){
			return ResponseEntity.ok(documentoService.findAll());
		}
		
		//Metodo para obtener un documento por Id
		@GetMapping("/{id}")
		public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id){
			return ResponseEntity.ok(documentoService.findById(id));
		}
}
