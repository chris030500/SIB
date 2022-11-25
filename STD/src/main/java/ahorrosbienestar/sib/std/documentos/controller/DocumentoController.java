package ahorrosbienestar.sib.std.documentos.controller;

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

import ahorrosbienestar.sib.std.documentos.model.Documento;
import ahorrosbienestar.sib.std.documentos.service.DocumentoService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Controller
@RequestMapping("documento")
public class DocumentoController {

	private DocumentoService documentoService;
	
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
