package ahorrosbienestar.sib.std.productos.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.jfree.util.Log;
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

import ahorrosbienestar.sib.std.catalogo_contratos.service.CatContratoService;
import ahorrosbienestar.sib.std.catalogos.repository.ICatalogoRepository;
import ahorrosbienestar.sib.std.core_bancario.service.CoreBancarioService;
import ahorrosbienestar.sib.std.documentos.service.DocumentoService;
import ahorrosbienestar.sib.std.productodocumento.model.RelacionDocumentos;
import ahorrosbienestar.sib.std.productodocumento.repository.IProductoDocumentoRepository;
import ahorrosbienestar.sib.std.productos.model.Producto;
import ahorrosbienestar.sib.std.productos.producto_contrato.service.ProductoContratoService;
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
	private CatContratoService catContratoService;

	@Autowired
	private ProductoContratoService productoContratoService;

	@Autowired
	private ICatalogoRepository iCatalogoRepository;

	@Autowired
	private IUsuarioRepository iUsuarioRepository;

	@Autowired
	private IProductoDocumentoRepository iProductoDocumento;

	private int grupo = 2;
	private int estatus = 1;

	@Autowired
	public ProductoController(ProductoService productoService) {
		this.productoService = productoService;
	}

	@GetMapping("/index")
	public String InicioUsuario(Model model) {
		try {

			model.addAttribute("cat_tipo_producto", iCatalogoRepository.generarOpciones(grupo));
			// cat_prodcuto_colocacion
			model.addAttribute("cat_prodcuto_captacion", coreBancarioService.obtProductosCaptacion());
			model.addAttribute("cat_prodcuto", coreBancarioService.obtProductos());
			model.addAttribute("listaProductos", productoContratoService.listaDeProductosConContrato());
			//model.addAttribute("listaProductos", productoService.listaDeProductosConContrato2());
			model.addAttribute("listDocumentos", documentoService.todosDocumentos());

			model.addAttribute("listadoContratos", catContratoService.listaContratosActivos());

			return "productos/index";
		} catch (Exception e) {
			Log.error("Vista Productos:" + e.getMessage());
			System.out.println("Error:" + e.getMessage());
		}
		return null;
	}

	@GetMapping("/eliminar/{id_producto}")
	public String agregarProducto(@PathVariable(name = "id_producto") Integer id_producto) {

		try {
			productoService.bajaProducto(id_producto);

			return "redirect:/producto/index";
		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
		}
		return null;
	}

	@GetMapping("/reactivar/{id_producto}")
	public String reactivar(@PathVariable(name = "id_producto") Integer id_producto) {

		try {

			productoService.reactivar(id_producto);

			return "redirect:/producto/index";
		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
		}
		return null;
	}

	@GetMapping("/alta/{idproducto}/{nombreProducto}/{tipoProducto}/{idContrato}/{identificador}/{descripcion}")
	public ResponseEntity<?> alta(Model model, @PathVariable(name = "idproducto") int idproducto,
			@PathVariable(name = "nombreProducto") String nombreProducto,
			@PathVariable(name = "tipoProducto") int tipoProducto, @PathVariable(name = "idContrato") int idContrato,
			@PathVariable(name = "identificador") String identificador,
			@PathVariable(name = "descripcion") String descripcion) {
		String mensaje = "";
		ArrayList<String> respuesta = new ArrayList<String>();

		int estatus = 1; // activo
		try {

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			List<Usuario> usuario = iUsuarioRepository.findByNombreUsuario(userDetails.getUsername());

			if (productoService.existeProducto(idproducto) == 0) {
				LocalDate fecha = LocalDate.now();

				Producto productoAlta = new Producto();
				productoAlta.setI_no_producto(idproducto);
				productoAlta.setVc_nombre_producto(nombreProducto);
				productoAlta.setDt_fecha_operacion(fecha);
				productoAlta.setI_estatus(estatus);
				productoAlta.setI_tipo_producto(tipoProducto);
				productoAlta.setI_clasificacion(tipoProducto == 1);
				productoAlta.setVc_identificador_kyc(identificador);
				productoAlta.setVc_descripcion_producto(descripcion);
				productoAlta.setI_usuarioid_registra(usuario.get(0).getPk_id_usuario());
				productoAlta.setFk_id_contrato(catContratoService.findById(idContrato));

				productoService.save(productoAlta);
				mensaje = "Producto Registrado Correctamente.";
				respuesta.add(mensaje);
			} else {
				mensaje = "El Producto ya esta registrado.";
				respuesta.add(mensaje);

			}

			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			System.out.println("" + e.getMessage());
			mensaje = "Ocurrio un error. Intente mas tarde.";
			respuesta.add(mensaje);
		}

		return null;

	}

	@ResponseBody
	@RequestMapping(value = "/cargarProducto/{id}", method = RequestMethod.GET)
	public Optional<Producto> loadEditarProducto(@PathVariable("id") Integer id) {
		Log.info("Ejecutando loadEditarProducto");
		// logger.info("Ejecutando loadEditarProducto");
		System.out.println("Entro en loadEditarProducto");
		System.out.println("productoService.findById(id) " + productoService.findById(id));
		return productoService.findById(id);
	}

	@RequestMapping(value = { "/editar_producto" }, method = RequestMethod.POST)
	public ResponseEntity<?> cambiarNombreUsuario(@RequestParam("kyc") String kyc,
			@RequestParam("descripcion") String descripcion, @RequestParam("idProducto") Integer idProducto) {
		String mensaje = "";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			if (idProducto != null) {
				// Producto producto = productoService.findById(idProducto).get();
				productoService.cambiarKycYDescripcion(kyc, descripcion, idProducto);
				mensaje = "Producto editado correctamente.";
				respuesta.add(mensaje);
			}

			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("" + e.getMessage());
			mensaje = "Ocurrio un error al editar el producto. Intente mas tarde.";
			respuesta.add(mensaje);
		}
		return null;

	}

	@PostMapping("/altaDocumentos/{idproducto}/{idsDocumentos}")
	public ResponseEntity<?> altaProducto(Model model, @PathVariable(name = "idproducto") int idproducto,
			@PathVariable(name = "idsDocumentos") ArrayList<Integer> idsDocumentos) {
		String mensaje = "";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			mensaje = "Registro Correcto.";
			LocalDate fecha = LocalDate.now();
			for (int idDocumento : idsDocumentos) {

				if (iProductoDocumento.existeDocumento(idproducto, idDocumento) == 0) {
					RelacionDocumentos registroDocumento = new RelacionDocumentos();
					registroDocumento.setFk_producto(idproducto);
					registroDocumento.setFk_documento(idDocumento);
					registroDocumento.setDt_fecha_operacion(fecha);
					registroDocumento.setI_estatus(estatus);

					iProductoDocumento.save(registroDocumento);

				} else {
					iProductoDocumento.activarDocumento(idproducto, idDocumento);
				}
			}
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			System.out.println("" + e.getMessage());
			mensaje = "Ocurrio un error. Intente mas tarde.";
			respuesta.add(mensaje);
		}
		return null;
	}

	@GetMapping("/consultaDocumento/{id_producto}")
	public ResponseEntity<?> consultaDocumentos(@PathVariable(name = "id_producto") Integer id_producto) {
		try {
			List<RelacionDocumentos> solicitudDatos = iProductoDocumento.buscarDocumentos(id_producto);

			return ResponseEntity.ok(solicitudDatos);

		} catch (Exception e) {
			System.out.print("--" + e.getMessage());
		}
		return null;
	}

	@PostMapping("/actualizarDocumentos/{idproducto}/{idsDocumentos}/{idsAnteriores}")
	public ResponseEntity<?> actualizarDocumentos(Model model, @PathVariable(name = "idproducto") int idproducto,
			@PathVariable(name = "idsDocumentos") ArrayList<Integer> idsDocumentos,
			@PathVariable(name = "idsAnteriores") ArrayList<Integer> idsAnteriores) {
		String mensaje = "";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			mensaje = "Documentos Actualizados Correctamente.";
			LocalDate fecha = LocalDate.now();

			// Alta de nuevos documentos.
			ArrayList<Integer> altaNewDocumentos = new ArrayList<Integer>();
			for (int element : idsDocumentos) {
				if (!idsAnteriores.contains(element)) {
					altaNewDocumentos.add(element);
				}
			}

			// Actualizar estatus de documentos
			ArrayList<Integer> actualizarDocumentos = new ArrayList<Integer>();
			for (int element : idsAnteriores) {
				if (!idsDocumentos.contains(element)) {
					actualizarDocumentos.add(element);
				}
			}

			if (altaNewDocumentos.size() > 0) {
				for (int idDocumento : altaNewDocumentos) {

					if (iProductoDocumento.existeDocumento(idproducto, idDocumento) == 0) {
						RelacionDocumentos registroDocumento = new RelacionDocumentos();
						registroDocumento.setFk_producto(idproducto);
						registroDocumento.setFk_documento(idDocumento);
						registroDocumento.setDt_fecha_operacion(fecha);
						registroDocumento.setI_estatus(estatus);

						iProductoDocumento.save(registroDocumento);

					} else {
						iProductoDocumento.activarDocumento(idproducto, idDocumento);
					}
				}
			}

			if (actualizarDocumentos.size() > 0) {
				for (int idDocumento : actualizarDocumentos) {
					if (iProductoDocumento.existeDocumento(idproducto, idDocumento) != 0) {
						iProductoDocumento.inactivarRelacion(idproducto, idDocumento);
					}
				}
			}

			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			System.out.println("" + e.getMessage());
			mensaje = "Ocurrio un error. Intente mas tarde.";
			respuesta.add(mensaje);
		}
		return null;
	}

	@PostMapping("/bajaDocumentos/{idproducto}/{idsAnteriores}")
	public ResponseEntity<?> bajaDocumentos(Model model, @PathVariable(name = "idproducto") int idproducto,
			@PathVariable(name = "idsAnteriores") ArrayList<Integer> idsAnteriores) {
		String mensaje = "";
		ArrayList<String> respuesta = new ArrayList<String>();
		try {
			mensaje = "Documentos Actualizados Correctamente.";
			for (int idDocumento : idsAnteriores) {
				iProductoDocumento.inactivarRelacion(idproducto, idDocumento);
			}
			respuesta.add(mensaje);
			return ResponseEntity.ok(respuesta);
		} catch (Exception e) {
			System.out.println("" + e.getMessage());
			mensaje = "Ocurrio un error. Intente mas tarde.";
			respuesta.add(mensaje);
		}
		return null;
	}

	@GetMapping("/index_val")
	public String InicioProductos(Model model, @RequestBody Producto producto) {
		try {
			model.addAttribute("producto", producto);
			model.addAttribute("producto", productoService.findAll());
			return "productos/index_val";
		} catch (Exception e) {
			System.out.println("" + e.getMessage());
		}
		return null;

	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody Producto producto) {
		try {
			Producto p = new Producto();
			p = productoService.save(producto);
			return ResponseEntity.ok(p);
		} catch (Exception e) {

			System.out.println("" + e.getMessage());
		}
		return null;

	}

	@PutMapping
	public ResponseEntity<?> edit(@RequestBody @Valid Producto producto, @PathVariable(name = "id") Integer id) {

		try {
			Producto p = new Producto();

			p = productoService.findById(id).get();

			if (p != null) {
				return ResponseEntity.ok(productoService.save(producto));
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {

			System.out.println("" + e.getMessage());
		}
		return null;

	}

	// Metodo para obtener listado de todos los productos
	@GetMapping("/productos")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(productoService.findAll());
	}

	// Metodo para obtener un producto por Id
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id) {
		return ResponseEntity.ok(productoService.findById(id));
	}
}

