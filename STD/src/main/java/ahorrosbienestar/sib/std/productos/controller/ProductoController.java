package ahorrosbienestar.sib.std.productos.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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

import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import ahorrosbienestar.sib.std.documentos.service.DocumentoService;
import ahorrosbienestar.sib.std.productodocumento.model.RelacionDocumentos;
import ahorrosbienestar.sib.std.productodocumento.repository.IProductoDocumentoRepository;
import ahorrosbienestar.sib.std.productos.model.Producto;
import ahorrosbienestar.sib.std.productos.service.ProductoService;
import ahorrosbienestar.sib.std.usuarios.model.Usuario;
import ahorrosbienestar.sib.std.usuarios.repository.IUsuarioRepository;

@Controller
@RequestMapping("producto")
public class ProductoController {

	private ProductoService productoService;
	
	@Autowired
	private CoreBancarioService coreBancarioService;
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private ICatalogoRepository iCatalogoRepository;
	
	@Autowired
	private IUsuarioRepository iUsuarioRepository;
	
	@Autowired
	private IProductoDocumentoRepository iProductoDocumento;
	
	private int grupo = 2;
	private int estatus = 1;
	
	@Autowired
	public ProductoController (ProductoService productoService) {
		this.productoService = productoService;
	}
	
	
		@GetMapping("/index")
		public String InicioUsuario(Model model) {
									
			model.addAttribute("cat_tipo_producto",iCatalogoRepository.generarOpciones(grupo));
			model.addAttribute("cat_prodcuto_colocacion",coreBancarioService.obtProductosCaptacion());	
			model.addAttribute("cat_prodcuto",coreBancarioService.obtProductos());
			model.addAttribute("listaProductos",productoService.todosProductos());
			model.addAttribute("listDocumentos",documentoService.findAll());
			
			return "productos/index";
		}
		
		@GetMapping("/eliminar/{id_producto}")
		public String agregarProducto(@PathVariable (name ="id_producto") Integer id_producto) {
			
			try {
				productoService.bajaProducto(id_producto);
			
				return "redirect:/producto/index";
			} catch (Exception e) {
				System.out.print("--"+e.getMessage());
			}
			return null;
		}
		
		@GetMapping("/reactivar/{id_producto}")
		public String reactivar(@PathVariable (name ="id_producto") Integer id_producto) {
			
			try {
				
				productoService.reactivar(id_producto);
				
				return "redirect:/producto/index";
			} catch (Exception e) {
				System.out.print("--"+e.getMessage());
			}
			return null;
		}
		
		@GetMapping("/alta/{idproducto}/{nombreProducto}/{tipoProducto}")
		public ResponseEntity<?> alta(Model model,@PathVariable (name ="idproducto") int idproducto,@PathVariable (name ="nombreProducto") String nombreProducto,@PathVariable (name ="tipoProducto") int tipoProducto) {
			String mensaje ="Producto Registrado Correctamente.";
			ArrayList<String> respuesta = new ArrayList<String>();
			
			int estatus = 1; //activo
			try {
				
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				UserDetails userDetails = null;
				if (principal instanceof UserDetails) {
				  userDetails = (UserDetails) principal;
				}
				List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());
			
				if(productoService.existeProducto(idproducto) == 0) {
					LocalDate fecha = LocalDate.now();
					
					Producto productoAlta = new Producto();
					productoAlta.setI_no_producto(idproducto);
					productoAlta.setVc_nombre_producto(nombreProducto);
					productoAlta.setDt_fecha_operacion(fecha);
					productoAlta.setI_estatus(estatus);
					productoAlta.setI_tipo_producto(tipoProducto);
					productoAlta.setI_clasificacion(tipoProducto);
					productoAlta.setI_usuarioid_registra(usuario.get(0).getPk_id_usuario());
					
					productoService.save(productoAlta);
					
					respuesta.add(mensaje);
				}else {
					mensaje = "El Producto esta registrado, si esta inactivo es posible activar nuevamente el producto.";
					respuesta.add(mensaje);
					
				}
				
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				System.out.println(""+e.getMessage());
			}
						
			return null;
			
		}		
		
		@PostMapping("/altaDocumentos/{idproducto}/{idsDocumentos}")
		public ResponseEntity<?> altaProducto(Model model,@PathVariable (name ="idproducto") int idproducto,@PathVariable (name ="idsDocumentos") ArrayList<Integer> idsDocumentos){
			String mensaje ="Documento Registrado Correctamente.";
			try {
				ArrayList<String> respuesta = new ArrayList<String>();
				LocalDate fecha = LocalDate.now();
				for(int idDocumento : idsDocumentos) {
					
					if(iProductoDocumento.existeDocumento(idproducto, idDocumento) == 0) {
						RelacionDocumentos registroDocumento = new RelacionDocumentos();
						registroDocumento.setFk_producto(idproducto);
						registroDocumento.setFk_documento(idDocumento);
						registroDocumento.setDt_fecha_operacion(fecha);
						registroDocumento.setI_estatus(estatus);
						
						iProductoDocumento.save(registroDocumento);
						respuesta.add(mensaje);
					}else {
						mensaje = "El Documento ya esta relacionado al producto.";
						respuesta.add(mensaje);
					}
				}
				
				return ResponseEntity.ok(respuesta);
			} catch (Exception e) {
				System.out.println(""+e.getMessage());
			}
			return null;
		}

		@GetMapping("/consultaDocumento/{id_producto}")
		public ResponseEntity<?> consultaDocumentos(@PathVariable (name ="id_producto") Integer id_producto) {
			try {
				List<RelacionDocumentos> solicitudDatos = iProductoDocumento.buscarDocumentos(id_producto);
				
				return ResponseEntity.ok(solicitudDatos);
				
			} catch (Exception e) {
				System.out.print("--"+e.getMessage());
			}
			return null;
		}
		
		
		@GetMapping("/index_val")
		public String InicioProductos(Model model, @RequestBody Producto producto) {

			model.addAttribute("producto", producto);
			
			model.addAttribute("producto", productoService.findAll());
			return "productos/index_val";
		}
	
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
