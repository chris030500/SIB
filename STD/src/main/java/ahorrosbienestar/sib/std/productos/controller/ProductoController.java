package ahorrosbienestar.sib.std.productos.controller;

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

import ahorrosbienestar.sib.std.productos.model.Producto;
import ahorrosbienestar.sib.std.productos.service.ProductoService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;

@Controller
@RequestMapping("producto")
public class ProductoController {

	private ProductoService productoService;
	
	@Autowired
	public ProductoController (ProductoService productoService) {
		this.productoService = productoService;
	}
	
	
		@GetMapping("/index")
		public String InicioUsuario(Model model) {
			Producto producto = new Producto();
			model.addAttribute("producto", producto);
			
			model.addAttribute("producto", productoService.findAll());
			return "productos/index";
		}
		
		/*
		@GetMapping("/index_rel")
		public String InicioProductos(Model model) {
			Producto producto = new Producto();
			model.addAttribute("producto", producto);
			
			model.addAttribute("producto", productoService.findAll());
			return "productos/index_rel";
		}*/
	
	
		@PostMapping
		public ResponseEntity<?> save(@RequestBody Producto producto){
			Producto p = new Producto();
			p = productoService.save(producto);
			return ResponseEntity.ok(p);
		}
		
		@PutMapping
		public ResponseEntity<?> edit(@RequestBody @Valid Producto producto,
										@PathVariable(name = "id") Integer id){
			
			Producto p = new Producto();
		
			p = productoService.findById(id).get();
			
			if(p != null) {
				return ResponseEntity.ok(productoService.save(producto));
			}
			return ResponseEntity.notFound().build();
			
		}
		
		//Metodo para obtener listado de todos los productos
		@GetMapping("/productos")
		public ResponseEntity<?> findAll(){
			return ResponseEntity.ok(productoService.findAll());
		}
		
		//Metodo para obtener un producto por Id
		@GetMapping("/{id}")
		public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id){
			return ResponseEntity.ok(productoService.findById(id));
		}
}
